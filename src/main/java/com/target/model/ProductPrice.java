package com.target.model;

public class ProductPrice {
    private String currency_code;
    private Float value;

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public ProductPrice() {
        super();
    }

    public ProductPrice(String currency_code, Float value) {
        super();
        this.currency_code = currency_code;
        this.value = value;
    }
}
