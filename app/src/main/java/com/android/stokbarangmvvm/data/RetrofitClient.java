package com.android.stokbarangmvvm.data;

import android.content.Context;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    // Emulator: 10.0.2.2 = localhost komputer. HP asli: ganti dengan IP laptop, mis. http://192.168.1.10:8000/api/
    private static final String BASE_URL = "http://10.0.2.2:8000/api/";
    private static ApiService api;

    public static synchronized ApiService getApi(Context context) {
        if (api == null) {
            TokenManager tokens = new TokenManager(context);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request.Builder builder = chain.request().newBuilder()
                                .header("Accept", "application/json");
                        String token = tokens.getToken();
                        if (token != null) builder.header("Authorization", "Bearer " + token);
                        return chain.proceed(builder.build());
                    })
                    .build();

            api = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService.class);
        }
        return api;
    }
}