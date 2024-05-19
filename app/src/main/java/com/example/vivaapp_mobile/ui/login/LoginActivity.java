package com.example.vivaapp_mobile.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vivaapp_mobile.MainActivity;
import com.example.vivaapp_mobile.databinding.ActivityLoginBinding;
import com.example.vivaapp_mobile.model.repository.DatabaseHelper;
import com.example.vivaapp_mobile.ui.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        databaseHelper = new DatabaseHelper(this);

        binding.anasayfaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        binding.kayitOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(binding);
            }
        });
    }

    private void loginUser(ActivityLoginBinding binding) {
        String eposta = binding.editTextTextEmailAddress.getText().toString();
        String sifre = binding.editTextTextPassword.getText().toString();

        if (eposta.isEmpty() || sifre.isEmpty()) {
            Toast.makeText(this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
        } else {
            boolean isValid = databaseHelper.checkUser(eposta, sifre);
            if (isValid) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Geçersiz e-posta veya şifre", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
