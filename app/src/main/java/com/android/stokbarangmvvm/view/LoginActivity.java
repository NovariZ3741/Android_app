package com.android.stokbarangmvvm.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.android.stokbarangmvvm.R;
import com.android.stokbarangmvvm.viewmodel.LoginViewModel;


public class LoginActivity extends AppCompatActivity {
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        if (viewModel.isLoggedIn()) {
            openProductList();
            return;
        }

        setContentView(R.layout.activity_login);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        ProgressBar progress = findViewById(R.id.progress);

        viewModel.getLoading().observe(this, isLoading -> {
            progress.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            btnLogin.setEnabled(!isLoading);
        });
        viewModel.getError().observe(this, msg ->
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show());
        viewModel.getLoginSuccess().observe(this, ok -> {
            if (ok) openProductList();
        });

        btnLogin.setOnClickListener(v ->
                viewModel.login(etEmail.getText().toString(), etPassword.getText().toString()));
    }

    private void openProductList() {
        startActivity(new Intent(this, ProductListActivity.class));
        finish();
    }
}