package com.android.stokbarangmvvm.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.android.stokbarangmvvm.R;
import com.android.stokbarangmvvm.viewmodel.ProductViewModel;

public class AddProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        setTitle("Tambah Produk");

        ProductViewModel viewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        EditText etSku = findViewById(R.id.etSku);
        EditText etName = findViewById(R.id.etName);
        EditText etCategory = findViewById(R.id.etCategory);
        EditText etPrice = findViewById(R.id.etPrice);
        EditText etMinStock = findViewById(R.id.etMinStock);
        Button btnSave = findViewById(R.id.btnSave);
        ProgressBar progress = findViewById(R.id.progress);

        viewModel.getLoading().observe(this, isLoading -> {
            progress.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            btnSave.setEnabled(!isLoading);
        });
        viewModel.getError().observe(this, msg ->
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show());
        viewModel.getSaved().observe(this, ok -> {
            if (ok) {
                Toast.makeText(this, "Produk berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnSave.setOnClickListener(v -> viewModel.addProduct(
                etSku.getText().toString(), etName.getText().toString(),
                etCategory.getText().toString(), etPrice.getText().toString(),
                etMinStock.getText().toString()));
    }
}