package com.collector.rates.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationResolver {

    @Value("${rates.api-key}")
    private String apiKey;
    @Value("${rates.latest.base}")
    private String baseCurrencies;

    public String getApiKey() {
        return apiKey;
    }

    public String getBaseCurrencies() {
        return baseCurrencies;
    }
}
