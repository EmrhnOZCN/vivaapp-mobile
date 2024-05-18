package com.example.vivaapp_mobile.ui.product;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vivaapp_mobile.databinding.ItemProductBinding;
import com.example.vivaapp_mobile.model.Product;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context context;
    private List<Product> productList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onAddToCartClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding binding = ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.binding.productImage.setImageResource(product.getImageResource());
        holder.binding.productName.setText(product.getName());
        holder.binding.productPrice.setText("$" + product.getPrice());

        String[] quantities = {"0", "1", "2", "3", "4", "5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, quantities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.binding.quantitySpinner.setAdapter(adapter);

        int savedQuantity = getSavedQuantity(product);
        if (savedQuantity > 0) {
            holder.binding.addToCartButton.setVisibility(View.GONE);
            holder.binding.quantitySpinner.setVisibility(View.VISIBLE);
            holder.binding.quantitySpinner.setSelection(savedQuantity);
        } else {
            holder.binding.addToCartButton.setVisibility(View.VISIBLE);
            holder.binding.quantitySpinner.setVisibility(View.GONE);
        }

        holder.binding.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.binding.quantitySpinner.setVisibility(View.VISIBLE);
                holder.binding.quantitySpinner.setSelection(1);
                holder.binding.addToCartButton.setVisibility(View.GONE);

                saveProductToSharedPreferences(product, 1);
            }
        });

        holder.binding.quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int selectedPosition, long id) {
                if (selectedPosition == 0) {
                    holder.binding.addToCartButton.setVisibility(View.VISIBLE);
                    holder.binding.quantitySpinner.setVisibility(View.GONE);
                    removeProductFromSharedPreferences(product);
                } else {
                    holder.binding.addToCartButton.setVisibility(View.GONE);
                    holder.binding.quantitySpinner.setVisibility(View.VISIBLE);

                    saveProductToSharedPreferences(product, selectedPosition);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    private void saveProductToSharedPreferences(Product product, int quantity) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("cart_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        Set<String> productSet = sharedPreferences.getStringSet("products", new HashSet<>());


        String newProductString = product.getImageResource() + "," + product.getName() + "," + product.getPrice() + "," + quantity;


        Set<String> updatedProductSet = new HashSet<>();
        boolean productExists = false;
        for (String productString : productSet) {
            String[] parts = productString.split(",");
            String productName = parts[1];
            if (productName.equals(product.getName())) {

                updatedProductSet.add(newProductString);
                productExists = true;
            } else {
                updatedProductSet.add(productString);
            }
        }

        if (!productExists) {

            updatedProductSet.add(newProductString);
        }


        editor.putStringSet("products", updatedProductSet);
        editor.apply();
    }

    private void removeProductFromSharedPreferences(Product product) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("cart_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        Set<String> productSet = sharedPreferences.getStringSet("products", new HashSet<>());


        String productStringToRemove = product.getImageResource() + "," + product.getName() + "," + product.getPrice() + "," + 0;


        Set<String> updatedProductSet = new HashSet<>(productSet);
        updatedProductSet.remove(productStringToRemove);


        editor.putStringSet("products", updatedProductSet);
        editor.apply();
    }

    private int getSavedQuantity(Product product) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("cart_prefs", Context.MODE_PRIVATE);
        Set<String> productSet = sharedPreferences.getStringSet("products", new HashSet<>());

        for (String productString : productSet) {
            String[] parts = productString.split(",");
            String productName = parts[1];
            if (productName.equals(product.getName())) {
                return Integer.parseInt(parts[3]);
            }
        }

        return 0;
    }




    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ItemProductBinding binding;

        public ProductViewHolder(@NonNull ItemProductBinding binding, final OnItemClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;

            binding.addToCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onAddToCartClick(position);
                        }
                    }
                }
            });
        }
    }
}
