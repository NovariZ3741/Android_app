package com.android.stokbarangmvvm.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.android.stokbarangmvvm.R;
import com.android.stokbarangmvvm.viewmodel.ProductViewModel;
import com.android.stokbarangmvvm.model.Product;
public class ProductListActivity extends AppCompatActivity {
    private ProductViewModel viewModel;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        setTitle("Stok Barang");

        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        adapter = new ProductAdapter(this);

        ListView listView = findViewById(R.id.listProducts);
        ProgressBar progress = findViewById(R.id.progress);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) ->
                showStockDialog(adapter.getItem(position)));

        findViewById(R.id.btnAdd).setOnClickListener(v ->
                startActivity(new Intent(this, AddProductActivity.class)));
        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            viewModel.logout();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        viewModel.getProducts().observe(this, adapter::setData);
        viewModel.getLoading().observe(this, isLoading ->
                progress.setVisibility(isLoading ? View.VISIBLE : View.GONE));
        viewModel.getError().observe(this, msg ->
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show());
        viewModel.getMessage().observe(this, msg ->
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.loadProducts(); // refresh setelah kembali dari halaman tambah produk
    }

    private void showStockDialog(Product product) {
        EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setHint("Jumlah");

        new AlertDialog.Builder(this)
                .setTitle(product.getName())
                .setMessage("Stok saat ini: " + product.getStock())
                .setView(input)
                .setPositiveButton("Barang Masuk", (d, w) ->
                        viewModel.addStock(product.getId(), "in", input.getText().toString()))
                .setNegativeButton("Barang Keluar", (d, w) ->
                        viewModel.addStock(product.getId(), "out", input.getText().toString()))
                .setNeutralButton("Batal", null)
                .show();
    }
}