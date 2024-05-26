package com.example.vivaapp_mobile.ui.product;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vivaapp_mobile.MainActivity;
import com.example.vivaapp_mobile.R;
import com.example.vivaapp_mobile.databinding.ActivityProductBinding;
import com.example.vivaapp_mobile.model.Product;
import com.example.vivaapp_mobile.model.repository.DatabaseHelper;
import com.example.vivaapp_mobile.model.repository.ProductRepository;
import com.example.vivaapp_mobile.ui.chatbot.ChatbotActivity;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityProductBinding binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseHelper = new DatabaseHelper(this);

      // addSampleProducts();

        String category = getIntent().getStringExtra("CATEGORY");



        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        List<Product> productList = getProductsByCategory(category);
        System.out.println(category);

        ProductAdapter adapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(adapter);


        binding.anasayfaDon.setOnClickListener(view -> {
            Intent intent = new Intent(ProductActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private List<Product> getProductsByCategory(String category) {
        ProductRepository productRepository = new ProductRepository(databaseHelper);
        return productRepository.getProductsByCategory(category);
    }



    private void addSampleProducts() {
        Product product1 = new Product(R.drawable.producttavuk, "Piliç Baget ", 119.99, "Et");
        Product product2 = new Product(R.drawable.productdanakuzu, "Dana Kıymalık  ", 419.99, "Et");
        Product product3 = new Product(R.drawable.productlevrek, "Levrek", 319.99, "Et");

        Product product4 = new Product(R.drawable.productseftali ,"Şeftali", 29.99, "Meyve");
        Product product5 = new Product(R.drawable.productlimon ,"Limon", 39.99, "Meyve");
        Product product6 = new Product(R.drawable.productbiber ,"Biber", 19.99, "Meyve");
        Product product20 = new Product(R.drawable.productsogan ,"Soğan", 29.99, "Meyve");
        Product product21 = new Product(R.drawable.productdomates ,"Domates", 23.99, "Meyve");

        Product product7 = new Product(R.drawable.producticetea ,"İce Tea", 36.50, "Icecek");
        Product product8 = new Product(R.drawable.productsu ,"Su", 8.95, "Icecek");
        Product product9 = new Product(R.drawable.productbeypazari ,"Beypazarı", 1.99, "Icecek");

        Product product10 = new Product(R.drawable.productun ,"Un", 33.50, "TemelGida");
        Product product11 = new Product(R.drawable.productpirinc ,"Pirinç", 18.45, "TemelGida");
        Product product12 = new Product(R.drawable.productmakarna ,"Makarna", 5.99, "TemelGida");
        Product product16 = new Product(R.drawable.tuzproduct ,"Tuz", 23.95, "TemelGida");
        Product product17 = new Product(R.drawable.productyag,"Yag", 179.95, "TemelGida");
        Product product18 = new Product(R.drawable.productyumurta ,"Yumurta", 50.00, "TemelGida");


        Product product13 = new Product(R.drawable.productyumasitici ,"Yumuşatıcı", 73.50, "Deterjan");
        Product product14 = new Product(R.drawable.productcamasirsuyu ,"Çamaşır Suyu", 28.45, "Deterjan");

        Product product15 = new Product(R.drawable.producttost ,"Tost Ekmeği", 12.50, "Firin");
        Product product19 = new Product(R.drawable.productekmekkirinti ,"Ekmek Kırıntısı", 20.50, "Firin");

        databaseHelper.addProduct(product1);
        databaseHelper.addProduct(product2);
        databaseHelper.addProduct(product3);
        databaseHelper.addProduct(product4);
        databaseHelper.addProduct(product5);
        databaseHelper.addProduct(product6);
        databaseHelper.addProduct(product7);
        databaseHelper.addProduct(product8);
        databaseHelper.addProduct(product9);
        databaseHelper.addProduct(product10);
        databaseHelper.addProduct(product11);
        databaseHelper.addProduct(product12);
        databaseHelper.addProduct(product13);
        databaseHelper.addProduct(product14);
        databaseHelper.addProduct(product15);
        databaseHelper.addProduct(product16);
        databaseHelper.addProduct(product17);
        databaseHelper.addProduct(product18);
        databaseHelper.addProduct(product19);
        databaseHelper.addProduct(product20);
        databaseHelper.addProduct(product21);
    }
}
