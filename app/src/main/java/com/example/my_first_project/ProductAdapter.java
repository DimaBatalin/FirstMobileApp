package com.example.my_first_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductAdapter extends ArrayAdapter<Product> {

    private final LayoutInflater inflater;
    private final NumberFormat priceFormat = NumberFormat.getIntegerInstance(Locale.forLanguageTag("ru-RU"));

    public ProductAdapter(@NonNull Context context) {
        super(context, R.layout.listview_item);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Product product = getItem(position);
        if (product != null) {
            holder.name.setText(product.getName());
            holder.description.setText(product.getDescription());
            holder.price.setText(getContext().getString(R.string.price_format,
                    priceFormat.format(product.getPrice())));
        }
        return convertView;
    }

    private static class ViewHolder {
        final TextView name;
        final TextView description;
        final TextView price;

        ViewHolder(View view) {
            name = view.findViewById(R.id.productName);
            description = view.findViewById(R.id.productDescription);
            price = view.findViewById(R.id.productPrice);
        }
    }
}
