package com.example.vivaapp_mobile.ui.category;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.vivaapp_mobile.databinding.FragmentCategoryBinding;
import com.example.vivaapp_mobile.ui.product.ProductActivity;

public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CategoryViewModel dashboardViewModel =
                new ViewModelProvider(this).get(CategoryViewModel.class);

        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set onClickListener for category1
        binding.Meyve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DashboardFragment", "Clicked on category1");
                openProductActivity("Meyve");
            }
        });

        // Set onClickListener for category2
        binding.Et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DashboardFragment", "Clicked on Et");
                openProductActivity("Et");
            }
        });
        binding.Icecek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DashboardFragment", "Clicked on Icecek");
                openProductActivity("Icecek");
            }
        });
        binding.TemelGida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DashboardFragment", "Clicked on TemelGida");
                openProductActivity("TemelGida");
            }
        });
        binding.Deterjan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DashboardFragment", "Clicked on Icecek");
                openProductActivity("Deterjan");
            }
        });

        binding.Firin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DashboardFragment", "Clicked on Firin");
                openProductActivity("Firin");
            }
        });

        // Add similar onClickListeners for other categories

        return root;
    }

    private void openProductActivity(String category) {
        Intent intent = new Intent(getActivity(), ProductActivity.class);
        intent.putExtra("CATEGORY", category);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
