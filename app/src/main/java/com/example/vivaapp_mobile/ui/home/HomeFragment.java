package com.example.vivaapp_mobile.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vivaapp_mobile.R;
import com.example.vivaapp_mobile.databinding.FragmentHomeBinding;
import com.example.vivaapp_mobile.model.Product;
import com.example.vivaapp_mobile.ui.chatbot.ChatbotActivity;
import com.example.vivaapp_mobile.ui.login.LoginActivity;
import com.example.vivaapp_mobile.ui.product.ProductAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private List<Product> productList;
    private ProductAdapter adapter;
    private List<Product> popularProducts;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        productList = new ArrayList<>();
        popularProducts = new ArrayList<>();
        initializeProductList();
        showRandomPopularProducts();

        adapter = new ProductAdapter(getContext(), popularProducts);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onAddToCartClick(int position) {
                Product clickedProduct = popularProducts.get(position);
                addToCart(clickedProduct);
            }
        });

        // SearchView listener ayarları
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterProducts(newText);
                return false;
            }
        });

        binding.searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && binding.searchView.getQuery().toString().isEmpty()) {
                    binding.textView3.setText("En popüler ürünler");
                    adapter.filterList(popularProducts);
                }
            }
        });

        checkUserLoginStatus();

        binding.cikisButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        binding.girisButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        binding.chatbotButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChatbotActivity.class);
                startActivity(intent);
            }
        });

        binding.searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.searchView.setIconified(false);
            }
        });

        return root;
    }

    private void initializeProductList() {
        productList.add(new Product(R.drawable.producttavuk, "Piliç Baget", 119.99, "Et"));
        productList.add(new Product(R.drawable.productlevrek, "Levrek", 319.99, "Et"));
        productList.add(new Product(R.drawable.productseftali, "Şeftali", 29.99, "Meyve"));
        productList.add(new Product(R.drawable.productlimon, "Limon", 39.99, "Meyve"));
        productList.add(new Product(R.drawable.productbiber, "Biber", 19.99, "Meyve"));
        productList.add(new Product(R.drawable.productsogan, "Soğan", 29.99, "Meyve"));
        productList.add(new Product(R.drawable.productdomates, "Domates", 23.99, "Meyve"));
        productList.add(new Product(R.drawable.producticetea, "İce Tea", 36.50, "İçecek"));
        productList.add(new Product(R.drawable.productsu, "Su", 8.95, "İçecek"));
        productList.add(new Product(R.drawable.productbeypazari, "Beypazarı", 1.99, "İçecek"));
        productList.add(new Product(R.drawable.productun, "Un", 33.50, "Temel Gıda"));
        productList.add(new Product(R.drawable.productpirinc, "Pirinç", 18.45, "Temel Gıda"));
        productList.add(new Product(R.drawable.productmakarna, "Makarna", 5.99, "Temel Gıda"));
        productList.add(new Product(R.drawable.tuzproduct, "Tuz", 23.95, "Temel Gıda"));
        productList.add(new Product(R.drawable.productyag, "Yağ", 179.95, "Temel Gıda"));
        productList.add(new Product(R.drawable.productyumurta, "Yumurta", 50.00, "Temel Gıda"));
        productList.add(new Product(R.drawable.productyumasitici, "Yumuşatıcı", 73.50, "Deterjan"));
        productList.add(new Product(R.drawable.productcamasirsuyu, "Çamaşır Suyu", 28.45, "Deterjan"));
        productList.add(new Product(R.drawable.producttost, "Tost Ekmeği", 12.50, "Fırın"));
        productList.add(new Product(R.drawable.productekmekkirinti, "Ekmek Kırıntısı", 20.50, "Fırın"));
    }

    private void showRandomPopularProducts() {
        // Rastgele 6 popüler ürünü seç
        List<Product> tempList = new ArrayList<>(productList);
        Collections.shuffle(tempList);
        popularProducts.clear();
        popularProducts.add(new Product(R.drawable.producttavuk, "Piliç Baget", 119.99, "Et"));
        popularProducts.add(new Product(R.drawable.productlevrek, "Levrek", 319.99, "Et"));
        popularProducts.add(new Product(R.drawable.productseftali, "Şeftali", 29.99, "Meyve"));
        popularProducts.add(new Product(R.drawable.productlimon, "Limon", 39.99, "Meyve"));
        popularProducts.add(new Product(R.drawable.productbiber, "Biber", 19.99, "Meyve"));
        popularProducts.add(new Product(R.drawable.productsogan, "Soğan", 29.99, "Meyve"));
        popularProducts.add(new Product(R.drawable.productdomates, "Domates", 23.99, "Meyve"));
    }

    private void filterProducts(String query) {
        if (query.isEmpty()) {
            binding.textView3.setText("En popüler ürünler");
            adapter.filterList(popularProducts);
        } else {
            List<Product> filteredList = new ArrayList<>();
            for (Product product : productList) {
                if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(product);
                }
            }
            adapter.filterList(filteredList);
            if (filteredList.isEmpty()) {
                binding.textView3.setText("Arama Sonucu");
            } else {
                binding.textView3.setText("Arama Sonucu");
            }
        }
    }

    private void checkUserLoginStatus() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        boolean isLogin = sharedPreferences.getBoolean("isLogin", false);

        if (isLogin) {
            binding.girisButon.setVisibility(View.GONE);
            binding.cikisButon.setVisibility(View.VISIBLE);
        } else {
            binding.girisButon.setVisibility(View.VISIBLE);
            binding.cikisButon.setVisibility(View.GONE);
        }
    }

    private void logoutUser() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLogin", false);
        editor.remove("userId");
        editor.remove("userName");
        editor.remove("userSurname");
        editor.remove("userEmail");
        editor.apply();

        binding.girisButon.setVisibility(View.VISIBLE);
        binding.cikisButon.setVisibility(View.GONE);
        Toast.makeText(getContext(), "Çıkış yapıldı", Toast.LENGTH_SHORT).show();
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
