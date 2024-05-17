package com.example.vivaapp_mobile.ui.notifications;

import android.annotation.SuppressLint;
import android.content.Context;
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

import java.util.List;

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

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.quantity_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.quantitySpinner.setAdapter(adapter);
        holder.quantitySpinner.setSelection(cartItem.getQuantity() - 1); // Spinner index 0'dan başlar

        holder.quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                int newQuantity = Integer.parseInt(parent.getItemAtPosition(pos).toString());
                cartItem.setQuantity(newQuantity);
                quantityChangeListener.onItemQuantityChange(position, newQuantity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Hiçbir şey seçilmediğinde yapılacak işlemler
            }
        });

        holder.removeButton.setOnClickListener(view -> {
            // Silme işlemleri
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
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
