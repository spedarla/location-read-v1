package com.target.model;

public class Product {
    private Long id;
    private String name;
    private ProductPrice current_price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductPrice getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(ProductPrice current_price) {
        this.current_price = current_price;
    }

    public Product() {
        super();
    }

    public Product(Long id, String name, ProductPrice current_price) {
        super();
        this.id = id;
        this.name = name;
        this.current_price = current_price;
    }
}
