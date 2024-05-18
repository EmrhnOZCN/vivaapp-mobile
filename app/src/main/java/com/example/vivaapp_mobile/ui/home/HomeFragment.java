package com.example.vivaapp_mobile.ui.home;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vivaapp_mobile.R;
import com.example.vivaapp_mobile.ui.login.LoginActivity;

import com.example.vivaapp_mobile.databinding.FragmentHomeBinding;
import com.example.vivaapp_mobile.ui.chatbot.ChatbotActivity;
import com.example.vivaapp_mobile.model.Product;
import com.example.vivaapp_mobile.ui.product.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        List<Product> productList = new ArrayList<>();

        productList.add(new Product(R.drawable.fo, "Product 1", 19.99));
        productList.add(new Product(R.drawable.ettavuk, "Product 2", 29.99));
        productList.add(new Product(R.drawable.fo, "Product 3", 19.99));
        productList.add(new Product(R.drawable.ettavuk, "Product 4", 29.99));
        productList.add(new Product(R.drawable.fo, "Product 5", 19.99));
        productList.add(new Product(R.drawable.ettavuk, "Product 6", 29.99));


        ProductAdapter adapter = new ProductAdapter(getContext(), productList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onAddToCartClick(int position) {
                // Handle add to cart action here
                Product clickedProduct = productList.get(position);
                // Örneğin, ürünü bir sepete ekleyebilirsiniz
                // Sepete ekleme işlemi burada gerçekleştirilir
                // Örnek olarak:
                addToCart(clickedProduct);
            }
        });

        // Set click listener for girisButon
        binding.girisButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch LoginActivity when the button is clicked
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        binding.chatbotButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch LoginActivity when the button is clicked
                Intent intent = new Intent(getActivity(), ChatbotActivity.class);
                startActivity(intent);
            }
        });




        return root;
    }
    private void addToCart(Product product) {
        // Burada ürünü sepete eklemek için gerekli işlemleri gerçekleştirin
        // Örneğin:
        // 1. Ürünü bir veritabanına ekleyin
        // 2. Bir alışveriş sepeti verisine ekleyin
        // 3. Alışveriş sepeti görünümünü güncelleyin
        // vb.
    }

}
