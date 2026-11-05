package com.android.stokbarangmvvm.model;

import com.google.gson.annotations.SerializedName;

public class StockRequest {
    @SerializedName("product_id") private final int productId;
    private final String type;
    private final int quantity;
    public StockRequest(int productId, String type, int quantity) {
        this.productId = productId;
        this.type = type;
        this.quantity = quantity;
    }
}