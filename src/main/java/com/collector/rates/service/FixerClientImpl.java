package com.collector.rates.service;

import com.collector.rates.config.ConfigurationResolver;
import com.collector.rates.entity.ExchangeRateEntity;
import com.collector.rates.entity.HistoryExchangeRateEntity;
import com.collector.rates.model.FixerResponse;
import com.collector.rates.repository.ExchangeRateRepository;
import com.collector.rates.repository.HistoryExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
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

    @Scheduled(fixedDelayString = "${rates.poll.rate}")
    public void syncLatestRatesByCurrency() {
        HttpEntity<Void> requestEntity = buildHttpEntity();
        String[] bases = configurationResolver.getBaseCurrencies().split(",");
        ExecutorService pool = Executors.newFixedThreadPool(128);

        Arrays.stream(bases).map(String::trim)
                .filter(b -> b.length() > 0)
                .forEach(base ->
                        pool.execute(() -> {
                                    ResponseEntity<FixerResponse> response = restTemplate.exchange(FIXER_URL + base, HttpMethod.GET, requestEntity, FixerResponse.class);
                                    if (response.getBody() != null) {
                                        saveRates(response.getBody());
                                    }
                                }
                        ));
    }

    private HttpEntity<Void> buildHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", configurationResolver.getApiKey());
        return new HttpEntity<>(headers);
    }

    @Transactional
    private void saveRateData(ExchangeRateEntity exRate) {
        exchangeRateRepository.save(exRate);
        historyRepository.save(new HistoryExchangeRateEntity(exRate));
    }

    private void saveRates(FixerResponse response) {
        response.getRates()
                .forEach((key, value) -> {
                    ExchangeRateEntity exRate = new ExchangeRateEntity();
                    exRate.setBase(response.getBase());
                    exRate.setRate(value.toString());
                    exRate.setQuote(key);
                    exRate.setTimestamp(LocalDateTime.ofInstant(Instant.ofEpochSecond(response.getTimestamp()), TimeZone.getDefault().toZoneId()));
                    saveRateData(exRate);
                });
    }
}
