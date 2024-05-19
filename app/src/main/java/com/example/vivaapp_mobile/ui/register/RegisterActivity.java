package com.example.vivaapp_mobile.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vivaapp_mobile.MainActivity;
import com.example.vivaapp_mobile.R;
import com.example.vivaapp_mobile.databinding.ActivityRegisterBinding;
import com.example.vivaapp_mobile.model.User;
import com.example.vivaapp_mobile.model.repository.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRegisterBinding binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseHelper = new DatabaseHelper(this);

        binding.anasayfaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser(binding);
            }
        });
    }

    private void registerUser(ActivityRegisterBinding binding) {
        String ad = binding.editTextText.getText().toString();
        String soyad = binding.editTextText2.getText().toString();
        String dogumTarihi = binding.editTextDate.getText().toString();
        String telefonNo = binding.editTextPhone.getText().toString();
        String eposta = binding.editTextTextEmailAddress2.getText().toString();
        String sifre = binding.editTextTextPassword3.getText().toString();

        if (ad.isEmpty() || soyad.isEmpty() || dogumTarihi.isEmpty() || telefonNo.isEmpty() || eposta.isEmpty() || sifre.isEmpty()) {
            Toast.makeText(this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
        } else {
            User user = new User();
            user.setAd(ad);
            user.setSoyad(soyad);
            user.setDogumTarihi(dogumTarihi);
            user.setTelefonNo(telefonNo);
            user.setEposta(eposta);
            user.setSifre(sifre);

            long result = databaseHelper.addUser(user);
            if (result != -1) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
