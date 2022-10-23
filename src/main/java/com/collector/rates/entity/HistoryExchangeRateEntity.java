package com.collector.rates.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "history_exchange_rate_data", schema = "exchange_rates")
@IdClass(HistoryExchangeRateId.class)
public class HistoryExchangeRateEntity {

    public HistoryExchangeRateEntity() {}

    public HistoryExchangeRateEntity(ExchangeRateEntity exRate) {
        this.base = exRate.getBase();
        this.quote = exRate.getQuote();
        this.rate = exRate.getRate();
        this.timestamp = exRate.getTimestamp();
    }

    @Id
    @Column(name = "base")
    private String base;

    @Id
    @Column(name = "quote")
    private String quote;

    @Column(name = "rate")
    private String rate;

    @Id
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
