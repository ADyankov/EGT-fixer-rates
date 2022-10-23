package com.collector.rates.repository;

import com.collector.rates.entity.ExchangeRateEntity;
import com.collector.rates.entity.ExchangeRateId;
import org.springframework.data.repository.CrudRepository;

public interface ExchangeRateRepository extends CrudRepository<ExchangeRateEntity, ExchangeRateId> {
}
