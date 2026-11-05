package com.android.stokbarangmvvm.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.stokbarangmvvm.model.Product;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.android.stokbarangmvvm.R;
public class ProductAdapter extends ArrayAdapter<Product> {
    private final NumberFormat rupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    public ProductAdapter(Context context) {
        super(context, 0, new ArrayList<>());
    }

    public void setData(List<Product> list) {
        clear();
        if (list != null) addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_product, parent, false);
        }
        Product p = getItem(position);

        ((TextView) convertView.findViewById(R.id.tvName)).setText(p.getName());
        ((TextView) convertView.findViewById(R.id.tvInfo)).setText(p.getSku() + " • " + p.getCategory());
        ((TextView) convertView.findViewById(R.id.tvPrice)).setText(rupiah.format(p.getPrice()));

        TextView tvStock = convertView.findViewById(R.id.tvStock);
        tvStock.setText(String.valueOf(p.getStock()));
        tvStock.setTextColor(p.getStock() <= p.getMinStock() ? Color.RED : Color.parseColor("#2E7D32"));
        return convertView;
    }
}