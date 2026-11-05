package com.android.stokbarangmvvm.repository;

public interface RepoCallback<T> {
    void onSuccess(T data);
    void onError(String message);
}