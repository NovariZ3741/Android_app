package com.android.stokbarangmvvm.model;

import com.google.gson.annotations.SerializedName;

public class Product {
    private Integer id;
    private String sku;
    private String name;
    private String category;
    private double price;
    @SerializedName("min_stock") private int minStock;
    private int stock;

    public Product() {}

    public Product(String sku, String name, String category, double price, int minStock) {
        this.sku = sku;
        this.name = name;
        this.category = category;
        this.price = price;
        this.minStock = minStock;
    }

    public Integer getId() { return id; }
    public String getSku() { return sku; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public int getMinStock() { return minStock; }
    public int getStock() { return stock; }
}