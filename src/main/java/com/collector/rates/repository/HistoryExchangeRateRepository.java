package com.collector.rates.repository;

import com.collector.rates.entity.HistoryExchangeRateEntity;
import com.collector.rates.entity.HistoryExchangeRateId;
import org.springframework.data.repository.CrudRepository;

public interface HistoryExchangeRateRepository extends CrudRepository<HistoryExchangeRateEntity, HistoryExchangeRateId> {
}
