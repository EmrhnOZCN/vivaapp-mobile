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
import com.example.vivaapp_mobile.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private RecyclerView recyclerView;
    private CartItemAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Sepet öğelerini al
        List<CartItem> cartItems = getCartItemsFromSharedPreferences();

        // CartItemAdapter'ı oluştur ve RecyclerView'a ata
        adapter = new CartItemAdapter(getContext(), cartItems);
        recyclerView.setAdapter(adapter);

        // Sepet tutarını gösteren TextView'i bul
        TextView totalPriceTextView = root.findViewById(R.id.orderSummaryText);

        // Sepet tutarını hesapla
        double totalPrice = calculateTotalPrice(cartItems);

        // Sepet tutarını TextView'e ayarla
        totalPriceTextView.setText("Toplam Tutar: " + totalPrice + " TL");

        return root;
    }

    // Sepet tutarını hesaplamak için yöntem
    private double calculateTotalPrice(List<CartItem> cartItems) {
        double totalPrice = 0;

        for (CartItem item : cartItems) {
            totalPrice += item.getPrice();
        }

        return totalPrice;
    }





    // Method to get cart items from SharedPreferences
    private List<CartItem> getCartItemsFromSharedPreferences() {

        List<CartItem> cartItems = new ArrayList<>();


        CartItem item = new CartItem("Ürün Adı", 24.99, "@drawable/store.png");
        CartItem item2 = new CartItem("Ürün Adı", 125.99, "@drawable/worker.png");
        cartItems.add(item);
        cartItems.add(item2);

        return cartItems;
    }

}