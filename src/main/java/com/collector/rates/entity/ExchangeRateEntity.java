package com.collector.rates.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "exchange_rate_data")
@Entity
@IdClass(ExchangeRateId.class)
public class ExchangeRateEntity {

    @Id
    @Column(name = "base")
    private String base;

    @Id
    @Column(name = "quote")
    private String quote;

    @Column(name = "rate")
    private String rate;

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
