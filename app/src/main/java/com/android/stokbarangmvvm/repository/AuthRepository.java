package com.android.stokbarangmvvm.repository;

import android.content.Context;

import com.android.stokbarangmvvm.data.ApiService;
import com.android.stokbarangmvvm.data.RetrofitClient;
import com.android.stokbarangmvvm.data.TokenManager;
import com.android.stokbarangmvvm.model.LoginRequest;
import com.android.stokbarangmvvm.model.LoginResponse;

public class AuthRepository extends BaseRepository {
    private final ApiService api;
    private final TokenManager tokens;

    public AuthRepository(Context context) {
        api = RetrofitClient.getApi(context);
        tokens = new TokenManager(context);
    }

    public void login(String email, String password, RepoCallback<LoginResponse> callback) {
        api.login(new LoginRequest(email, password)).enqueue(handle(new RepoCallback<LoginResponse>() {
            @Override public void onSuccess(LoginResponse data) {
                tokens.save(data.getToken(), data.getName());
                callback.onSuccess(data);
            }
            @Override public void onError(String message) { callback.onError(message); }
        }));
    }

    public boolean isLoggedIn() { return tokens.isLoggedIn(); }
    public void logout() { tokens.clear(); }
}