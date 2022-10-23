//package com.collector.rates.service;
//
//import com.collector.rates.config.ConfigurationResolver;
//import com.collector.rates.entity.ExchangeRateEntity;
//import com.collector.rates.repository.ExchangeRateRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class FixerClientImplTest {
//
//    @Mock
//    private RestTemplate restTemplate;
//    @Mock
//    private ExchangeRateRepository exchangeRateRepository;
//    @Mock
//    private ConfigurationResolver configurationResolver;
//
//    @InjectMocks
//    private FixerClientImpl sut;
//
//    @Test
//    void shouldGetLatestRatesByCurrency() {
//        final String sampleResponse = "{\"base\":\"USD\",\"date\":\"2022-04-14\",\"rates\":{\"EUR\":0.813399,\"GBP\":0.72007,\"JPY\":107.346001},\"success\":true,\"timestamp\":1519296206}";
//        when(configurationResolver.getApiKey()).thenReturn("foo");
//        when(configurationResolver.getBaseCurrencies()).thenReturn("USD");
//        ResponseEntity<String> responseEntity = new ResponseEntity<>(sampleResponse, HttpStatus.OK);
//        when(this.restTemplate.exchange(contains("/fixer/latest?base="), eq(HttpMethod.GET), any(), eq(String.class))).thenReturn(responseEntity);
//
//        sut.syncLatestRatesByCurrency();
//
//        verify(exchangeRateRepository.save(any(ExchangeRateEntity.class)));
//    }
//}