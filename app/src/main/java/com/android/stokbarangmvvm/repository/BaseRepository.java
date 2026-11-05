package com.android.stokbarangmvvm.repository;


import com.android.stokbarangmvvm.model.MessageResponse;
import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseRepository {

    protected <T> Callback<T> handle(RepoCallback<T> callback) {
        return new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(parseError(response));
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                callback.onError("Tidak dapat terhubung ke server: " + t.getMessage());
            }
        };
    }

    private String parseError(Response<?> response) {
        try {
            if (response.errorBody() != null) {
                MessageResponse m = new Gson().fromJson(response.errorBody().string(), MessageResponse.class);
                if (m != null && m.getMessage() != null) return m.getMessage();
            }
        } catch (Exception ignored) { }
        return "Terjadi kesalahan (kode " + response.code() + ")";
    }
}