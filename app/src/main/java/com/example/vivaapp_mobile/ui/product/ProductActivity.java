package com.example.vivaapp_mobile.ui.product;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vivaapp_mobile.MainActivity;
import com.example.vivaapp_mobile.R;
import com.example.vivaapp_mobile.databinding.ActivityProductBinding;
import com.example.vivaapp_mobile.model.Product;
import com.example.vivaapp_mobile.ui.chatbot.ChatbotActivity;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityProductBinding binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get the selected category from the intent
        String category = getIntent().getStringExtra("CATEGORY");

        // Enable edge-to-edge (if applicable)
        // EdgeToEdge.enable(this);

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        // Load products based on the selected category
        List<Product> productList = getProductsByCategory(category);

        ProductAdapter adapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(adapter);


        binding.anasayfaDon.setOnClickListener(view -> {
            Intent intent = new Intent(ProductActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private List<Product> getProductsByCategory(String category) {
        List<Product> productList = new ArrayList<>();

        // Example data - replace with real product fetching logic
        if ("category1".equals(category)) {
            productList.add(new Product(R.drawable.fo, "Product 1", 19.99));
            productList.add(new Product(R.drawable.fo, "Product 3", 19.99));
            productList.add(new Product(R.drawable.fo, "Product 5", 19.99));
        } else if ("category2".equals(category)) {
            productList.add(new Product(R.drawable.ettavuk, "Product 2", 29.99));
            productList.add(new Product(R.drawable.ettavuk, "Product 4", 29.99));
            productList.add(new Product(R.drawable.ettavuk, "Product 6", 29.99));
            productList.add(new Product(R.drawable.ettavuk, "Product 2", 29.99));
            productList.add(new Product(R.drawable.ettavuk, "Product 4", 29.99));
            productList.add(new Product(R.drawable.ettavuk, "Product 6", 29.99));
            productList.add(new Product(R.drawable.ettavuk, "Product 2", 29.99));
            productList.add(new Product(R.drawable.ettavuk, "Product 4", 29.99));
            productList.add(new Product(R.drawable.ettavuk, "Product 6", 29.99));
            productList.add(new Product(R.drawable.ettavuk, "Product 2", 29.99));
            productList.add(new Product(R.drawable.ettavuk, "Product 4", 29.99));
            productList.add(new Product(R.drawable.ettavuk, "Product 6", 29.99));
            productList.add(new Product(R.drawable.ettavuk, "Product 2", 29.99));
            productList.add(new Product(R.drawable.ettavuk, "Product 4", 29.99));
            productList.add(new Product(R.drawable.ettavuk, "Product 6", 29.99));
        }

        // Add more categories as needed

        return productList;
    }
}
