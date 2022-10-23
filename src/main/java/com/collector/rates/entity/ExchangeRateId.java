package com.collector.rates.entity;

import java.io.Serializable;
import java.util.Objects;

public class ExchangeRateId implements Serializable {

    private String base;
    private String quote;

    public ExchangeRateId(){}

    public ExchangeRateId(String base, String quote) {
        this.base = base;
        this.quote = quote;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRateId that = (ExchangeRateId) o;
        return base.equals(that.base) &&
                quote.equals(that.quote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, quote);
    }
}
