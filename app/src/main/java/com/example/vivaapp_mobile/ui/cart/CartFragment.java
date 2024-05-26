package com.example.vivaapp_mobile.ui.cart;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vivaapp_mobile.R;
import com.example.vivaapp_mobile.databinding.FragmentCardBinding;
import com.example.vivaapp_mobile.model.CartItem;
import com.example.vivaapp_mobile.model.OrderItem;
import com.example.vivaapp_mobile.model.repository.DatabaseHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CartFragment extends Fragment implements CartItemAdapter.OnItemQuantityChangeListener {

    private FragmentCardBinding binding;
    private RecyclerView recyclerView;
    private CartItemAdapter adapter;
    private TextView totalPriceTextView;
    private List<CartItem> cartItems;

    private DatabaseHelper databaseHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CartViewModel notificationsViewModel =
                new ViewModelProvider(this).get(CartViewModel.class);

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

        binding.confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kullanıcı bilgilerini SharedPreferences'ten alın
                SharedPreferences userPrefs = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                int userId = userPrefs.getInt("userId", -1);

                // Sepeti onaylayın ve siparişi gönderin
                List<CartItem> items = getCartItemsFromSharedPreferences();
                String[] productNames = new String[items.size()];
                for (int i = 0; i < items.size(); i++) {
                    productNames[i] = items.get(i).getName();
                }
                int quantity = (int) calculateTotalPrice();
                String orderDate = String.valueOf(new Date()); // Sipariş tarihini belirleyin



                notificationsViewModel.confirmOrder(userId, productNames, quantity, orderDate);

                requireActivity().getSupportFragmentManager().popBackStack();

            }
        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Kullanıcı çıkış yaptıysa sepet öğelerini temizle
        SharedPreferences userPrefs = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        boolean isLogin = userPrefs.getBoolean("isLogin", false);

        if (!isLogin) {
            clearCartItems();
        }
    }

    private void clearCartItems() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("cart_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("products");
        editor.apply();

        // RecyclerView'ı güncelle
        cartItems.clear();
        adapter.notifyDataSetChanged();

        // Toplam tutarı güncelle
        updateTotalPrice();
    }

    @Override
    public void onItemQuantityChange(int position, int newQuantity) {
        cartItems.get(position).setQuantity(newQuantity);
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        double totalPrice = calculateTotalPrice();
        String formattedTotalPrice = String.format("Toplam Tutar: %.2f TL", totalPrice);
        totalPriceTextView.setText(formattedTotalPrice);
    }

    private double calculateTotalPrice() {
        double totalPrice = 0;

        for (CartItem item : cartItems) {
            totalPrice += item.getPrice() * item.getQuantity();
        }

        return totalPrice;
    }

    private List<CartItem> getCartItemsFromSharedPreferences() {
        List<CartItem> cartItems = new ArrayList<>();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("cart_prefs", Context.MODE_PRIVATE);
        Set<String> productSet = sharedPreferences.getStringSet("products", new HashSet<>());

        for (String productString : productSet) {
            String[] parts = productString.split(",");
            int imageResource = Integer.parseInt(parts[0]);

            String name = parts[1];
            double price = Double.parseDouble(parts[2]);
            int quantity = Integer.parseInt(parts[3]);

            CartItem item = new CartItem(name, price, imageResource, quantity);
            cartItems.add(item);
        }


        return cartItems;
    }




}
