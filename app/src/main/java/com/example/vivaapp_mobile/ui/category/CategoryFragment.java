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
        binding.category1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DashboardFragment", "Clicked on category1");
                openProductActivity("category1");
            }
        });

        // Set onClickListener for category2
        binding.category2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DashboardFragment", "Clicked on category2");
                openProductActivity("category2");
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
