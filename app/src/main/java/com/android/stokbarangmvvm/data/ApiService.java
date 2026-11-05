package com.android.stokbarangmvvm.data;


 import com.android.stokbarangmvvm.model.LoginRequest;
 import com.android.stokbarangmvvm.model.LoginResponse;
 import com.android.stokbarangmvvm.model.MessageResponse;
 import com.android.stokbarangmvvm.model.Product;
 import com.android.stokbarangmvvm.model.StockRequest;
 import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {
    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @GET("products")
    Call<List<Product>> getProducts();

    @POST("products")
    Call<Product> addProduct(@Body Product product);

    @POST("stock-movements")
    Call<MessageResponse> addStock(@Body StockRequest request);
}