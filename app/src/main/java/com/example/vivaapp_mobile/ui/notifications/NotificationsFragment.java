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

        List<CartItem> cartItems = getCartItemsFromSharedPreferences();


        adapter = new CartItemAdapter(getContext(), cartItems);
        recyclerView.setAdapter(adapter);

        return root;
    }




    // Method to get cart items from SharedPreferences
    private List<CartItem> getCartItemsFromSharedPreferences() {

        List<CartItem> cartItems = new ArrayList<>();


        CartItem item = new CartItem("Ürün Adı", 24.99, "@drawable/store.png");
        CartItem item2 = new CartItem("Ürün Adı", 25.99, "@drawable/worker.png");
        cartItems.add(item);
        cartItems.add(item2);

        return cartItems;
    }

}