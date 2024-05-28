package com.example.vivaapp_mobile.ui.cart;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vivaapp_mobile.R;
import com.example.vivaapp_mobile.model.CartItem;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {

    public interface OnItemQuantityChangeListener {
        void onItemQuantityChange(int position, int newQuantity);
    }

    private List<CartItem> cartItemList;
    private Context context;
    private OnItemQuantityChangeListener quantityChangeListener;

    public CartItemAdapter(Context context, List<CartItem> cartItemList, OnItemQuantityChangeListener quantityChangeListener) {
        this.context = context;
        this.cartItemList = cartItemList;
        this.quantityChangeListener = quantityChangeListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem cartItem = cartItemList.get(position);
        holder.productName.setText(cartItem.getName());
        holder.productPrice.setText(String.format("%.2f", cartItem.getPrice()));
        holder.productImage.setImageResource(Integer.parseInt(cartItem.getImageUrl()));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.quantity_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.quantitySpinner.setAdapter(adapter);
        holder.quantitySpinner.setSelection(cartItem.getQuantity() - 1);

        holder.quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                int newQuantity = Integer.parseInt(parent.getItemAtPosition(pos).toString());
                cartItem.setQuantity(newQuantity);
                saveProductToSharedPreferences(cartItem);
                quantityChangeListener.onItemQuantityChange(position, newQuantity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Hiçbir şey seçilmediğinde yapılacak işlemler
            }
        });

        holder.removeButton.setOnClickListener(view -> {
            showRemoveConfirmationDialog(cartItem);

        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    private void saveProductToSharedPreferences(CartItem cartItem) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("cart_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        Set<String> productSet = sharedPreferences.getStringSet("products", new HashSet<>());


        String newProductString = cartItem.getImageUrl() + "," + cartItem.getName() + "," + cartItem.getPrice() + "," + cartItem.getQuantity();


        Set<String> updatedProductSet = new HashSet<>();
        boolean productExists = false;
        for (String productString : productSet) {
            String[] parts = productString.split(",");
            String productName = parts[1];
            if (productName.equals(cartItem.getName())) {
                // Update the product quantity if it exists
                updatedProductSet.add(newProductString);
                productExists = true;
            } else {
                updatedProductSet.add(productString);
            }
        }

        if (!productExists) {
            // Add the new product if it didn't exist
            updatedProductSet.add(newProductString);
        }

        // Save the updated set back to SharedPreferences
        editor.putStringSet("products", updatedProductSet);
        editor.apply();
    }
    private void showRemoveConfirmationDialog(CartItem cartItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Ürünü Kaldır");
        builder.setMessage("Bu ürünü sepetten kaldırmak istediğinizden emin misiniz?");
        builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                removeProductFromSharedPreferences(cartItem);
                cartItemList.remove(cartItem);
                notifyDataSetChanged(); // Adaptera değişiklik olduğunu bildir
            }
        });
        builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Kullanıcı "Hayır"ı seçtiğinde herhangi bir işlem yapmıyoruz, sadece dialogu kapatıyoruz
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void removeProductFromSharedPreferences(CartItem cartItem) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("cart_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Get existing set of products
        Set<String> productSet = sharedPreferences.getStringSet("products", new HashSet<>());

        // Create a string representation of the product to be removed
        String productStringToRemove = cartItem.getImageUrl() + "," + cartItem.getName() + "," + cartItem.getPrice() + "," + cartItem.getQuantity();

        // Remove the produxct string from the set
        Set<String> updatedProductSet = new HashSet<>(productSet);
        updatedProductSet.remove(productStringToRemove);

        // Save the updated set back to SharedPreferences
        editor.putStringSet("products", updatedProductSet);
        editor.apply();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice;
        ImageView productImage;
        Spinner quantitySpinner;
        Button removeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productImage = itemView.findViewById(R.id.productImage);
            quantitySpinner = itemView.findViewById(R.id.quantitySpinner);
            removeButton = itemView.findViewById(R.id.removeButton);
        }
    }
}
