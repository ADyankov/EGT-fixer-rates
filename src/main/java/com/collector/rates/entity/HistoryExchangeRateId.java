package com.collector.rates.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class HistoryExchangeRateId implements Serializable {

    private String base;
    private String quote;
    private LocalDateTime timestamp;

    public HistoryExchangeRateId() {}

    public HistoryExchangeRateId(String base, String quote, LocalDateTime timestamp) {
        this.base = base;
        this.quote = quote;
        this.timestamp = timestamp;
    }

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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryExchangeRateId that = (HistoryExchangeRateId) o;
        return base.equals(that.base) &&
                quote.equals(that.quote) &&
                timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, quote, timestamp);
    }
}
