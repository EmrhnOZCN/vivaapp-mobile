package com.example.vivaapp_mobile.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vivaapp_mobile.R;
import com.example.vivaapp_mobile.databinding.FragmentCardBinding;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment implements CartItemAdapter.OnItemQuantityChangeListener {

    private FragmentCardBinding binding;
    private RecyclerView recyclerView;
    private CartItemAdapter adapter;
    private TextView totalPriceTextView;
    private List<CartItem> cartItems;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentCardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Sepet öğelerini al
        cartItems = getCartItemsFromSharedPreferences();

        // CartItemAdapter'ı oluştur ve RecyclerView'a ata
        adapter = new CartItemAdapter(getContext(), cartItems, this);
        recyclerView.setAdapter(adapter);

        // Sepet tutarını gösteren TextView'i bul
        totalPriceTextView = root.findViewById(R.id.orderSummaryText);

        // Sepet tutarını hesapla ve güncelle
        updateTotalPrice();

        return root;
    }

    @Override
    public void onItemQuantityChange(int position, int newQuantity) {
        cartItems.get(position).setQuantity(newQuantity);
        updateTotalPrice();
    }

    // Sepet tutarını hesaplamak ve güncellemek için yöntem
    private void updateTotalPrice() {
        double totalPrice = calculateTotalPrice();
        String formattedTotalPrice = String.format("Toplam Tutar: %.2f TL", totalPrice);
        totalPriceTextView.setText(formattedTotalPrice);
    }

    // Sepet tutarını hesaplamak için yöntem
    private double calculateTotalPrice() {
        double totalPrice = 0;

        for (CartItem item : cartItems) {
            totalPrice += item.getPrice() * item.getQuantity();
        }

        return totalPrice;
    }

    // Method to get cart items from SharedPreferences
    private List<CartItem> getCartItemsFromSharedPreferences() {
        List<CartItem> cartItems = new ArrayList<>();

        CartItem item = new CartItem("Ürün Adı", 24.99, "drawable/ettavuk.png.jpg", 4);

        cartItems.add(item);
        cartItems.add(item);
        cartItems.add(item);
        CartItem item2 = new CartItem("Ürün Adı", 24.99, "drawable/ettavuk.png.jpg", 8);
        cartItems.add(item2);
        cartItems.add(item2);

        return cartItems;
    }
}
