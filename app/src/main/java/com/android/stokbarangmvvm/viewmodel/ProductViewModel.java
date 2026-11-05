package com.android.stokbarangmvvm.viewmodel;


import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.stokbarangmvvm.model.MessageResponse;
import com.android.stokbarangmvvm.model.Product;
import com.android.stokbarangmvvm.model.StockRequest;
import com.android.stokbarangmvvm.repository.AuthRepository;
import com.android.stokbarangmvvm.repository.ProductRepository;
import com.android.stokbarangmvvm.repository.RepoCallback;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private final ProductRepository repository;
    private final AuthRepository authRepository;

    private final MutableLiveData<List<Product>> products = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<String> message = new MutableLiveData<>();
    private final MutableLiveData<Boolean> saved = new MutableLiveData<>();

    public ProductViewModel(@NonNull Application application) {
        super(application);
        repository = new ProductRepository(application);
        authRepository = new AuthRepository(application);
    }

    public LiveData<List<Product>> getProducts() { return products; }
    public LiveData<Boolean> getLoading() { return loading; }
    public LiveData<String> getError() { return error; }
    public LiveData<String> getMessage() { return message; }
    public LiveData<Boolean> getSaved() { return saved; }

    // GET
    public void loadProducts() {
        loading.setValue(true);
        repository.getProducts(new RepoCallback<List<Product>>() {
            @Override public void onSuccess(List<Product> data) {
                loading.setValue(false);
                products.setValue(data);
            }
            @Override public void onError(String msg) {
                loading.setValue(false);
                error.setValue(msg);
            }
        });
    }

    // POST: tambah produk
    public void addProduct(String sku, String name, String category, String price, String minStock) {
        if (sku.trim().isEmpty() || name.trim().isEmpty() || price.trim().isEmpty() || minStock.trim().isEmpty()) {
            error.setValue("SKU, nama, harga, dan stok minimum wajib diisi");
            return;
        }
        double priceValue;
        int minStockValue;
        try {
            priceValue = Double.parseDouble(price.trim());
            minStockValue = Integer.parseInt(minStock.trim());
        } catch (NumberFormatException e) {
            error.setValue("Harga dan stok minimum harus berupa angka");
            return;
        }

        loading.setValue(true);
        Product product = new Product(sku.trim(), name.trim(), category.trim(), priceValue, minStockValue);
        repository.addProduct(product, new RepoCallback<Product>() {
            @Override public void onSuccess(Product data) {
                loading.setValue(false);
                saved.setValue(true);
            }
            @Override public void onError(String msg) {
                loading.setValue(false);
                error.setValue(msg);
            }
        });
    }

    // POST: barang masuk / keluar
    public void addStock(int productId, String type, String quantity) {
        int qty;
        try {
            qty = Integer.parseInt(quantity.trim());
        } catch (NumberFormatException e) {
            error.setValue("Jumlah harus berupa angka");
            return;
        }
        if (qty <= 0) {
            error.setValue("Jumlah harus lebih dari 0");
            return;
        }

        loading.setValue(true);
        repository.addStock(new StockRequest(productId, type, qty), new RepoCallback<MessageResponse>() {
            @Override public void onSuccess(MessageResponse data) {
                message.setValue(data.getMessage());
                loadProducts();
            }
            @Override public void onError(String msg) {
                loading.setValue(false);
                error.setValue(msg);
            }
        });
    }

    public void logout() { authRepository.logout(); }
}