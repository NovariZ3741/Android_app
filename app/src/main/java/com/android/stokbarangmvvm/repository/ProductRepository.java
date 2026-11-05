package com.android.stokbarangmvvm.repository;

import android.content.Context;

import com.android.stokbarangmvvm.data.ApiService;
import com.android.stokbarangmvvm.data.RetrofitClient;
import com.android.stokbarangmvvm.model.MessageResponse;
import com.android.stokbarangmvvm.model.Product;
import com.android.stokbarangmvvm.model.StockRequest;

import java.util.List;

public class ProductRepository extends BaseRepository {
    private final ApiService api;

    public ProductRepository(Context context) {
        api = RetrofitClient.getApi(context);
    }

    public void getProducts(RepoCallback<List<Product>> callback) {
        api.getProducts().enqueue(handle(callback));
    }

    public void addProduct(Product product, RepoCallback<Product> callback) {
        api.addProduct(product).enqueue(handle(callback));
    }

    public void addStock(StockRequest request, RepoCallback<MessageResponse> callback) {
        api.addStock(request).enqueue(handle(callback));
    }
}