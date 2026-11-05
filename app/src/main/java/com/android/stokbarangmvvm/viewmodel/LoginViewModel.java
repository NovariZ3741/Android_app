package com.android.stokbarangmvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.android.stokbarangmvvm.model.LoginResponse;
import com.android.stokbarangmvvm.repository.AuthRepository;
import com.android.stokbarangmvvm.repository.RepoCallback;

public class LoginViewModel extends AndroidViewModel {
    private final AuthRepository repository;
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = new AuthRepository(application);
    }

    public LiveData<Boolean> getLoading() { return loading; }
    public LiveData<String> getError() { return error; }
    public LiveData<Boolean> getLoginSuccess() { return loginSuccess; }
    public boolean isLoggedIn() { return repository.isLoggedIn(); }

    public void login(String email, String password) {
        if (email.trim().isEmpty() || password.isEmpty()) {
            error.setValue("Email dan password wajib diisi");
            return;
        }
        loading.setValue(true);
        repository.login(email.trim(), password, new RepoCallback<LoginResponse>() {
            @Override public void onSuccess(LoginResponse data) {
                loading.setValue(false);
                loginSuccess.setValue(true);
            }
            @Override public void onError(String message) {
                loading.setValue(false);
                error.setValue(message);
            }
        });
    }
}