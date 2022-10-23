package com.collector.rates.service;

import com.collector.rates.config.ConfigurationResolver;
import com.collector.rates.entity.ExchangeRateEntity;
import com.collector.rates.entity.HistoryExchangeRateEntity;
import com.collector.rates.repository.ExchangeRateRepository;
import com.collector.rates.repository.HistoryExchangeRateRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class FixerClientImpl implements FixerClient {

    private static final String FIXER_URL = "https://api.apilayer.com/fixer/latest?base=";

    private RestTemplate restTemplate;
    private ExchangeRateRepository exchangeRateRepository;
    private HistoryExchangeRateRepository historyRepository;
    private ConfigurationResolver configurationResolver;

    @Autowired
    public FixerClientImpl(RestTemplate restTemplate,
                           ExchangeRateRepository exchangeRateRepository,
                           HistoryExchangeRateRepository historyRepository,
                           ConfigurationResolver configurationResolver) {
        this.restTemplate = restTemplate;
        this.exchangeRateRepository = exchangeRateRepository;
        this.historyRepository = historyRepository;
        this.configurationResolver = configurationResolver;
    }

    @Scheduled(fixedRate = 60000)
    public void syncLatestRatesByCurrency() {
        HttpEntity<Void> requestEntity = buildHttpEntity();
        String[] bases = configurationResolver.getBaseCurrencies().split(",");
        int threads = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(threads);

        Arrays.stream(bases).forEach(base ->
                pool.execute(() -> {
                            ResponseEntity<String> response = restTemplate.exchange(FIXER_URL + base, HttpMethod.GET, requestEntity, String.class);
                            JSONObject responseJson = new JSONObject(response.getBody());
                            buildExchangeRateRecordFromJson(base, responseJson);
                        }
                ));
    }

    private HttpEntity<Void> buildHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", configurationResolver.getApiKey());
        return new HttpEntity<>(headers);
    }

    private void buildExchangeRateRecordFromJson(String base, JSONObject exchangeData) {
        JSONObject ratesJson = exchangeData.getJSONObject("rates");
        String longTimestamp = exchangeData.get("timestamp").toString();
        long ts = Long.parseLong(longTimestamp);
        Iterator<String> keys = ratesJson.keys();

        while (keys.hasNext()) {
            String quote = keys.next();
            String rate = ratesJson.get(quote).toString();
            ExchangeRateEntity exRate = new ExchangeRateEntity();
            exRate.setBase(base);
            exRate.setQuote(quote);
            exRate.setRate(rate);
            LocalDateTime timestamp = LocalDateTime.ofInstant(Instant.ofEpochSecond(ts), TimeZone.getDefault().toZoneId());
            exRate.setTimestamp(timestamp);
            exchangeRateRepository.save(exRate);
            historyRepository.save(new HistoryExchangeRateEntity(exRate));
        }
    }
}
