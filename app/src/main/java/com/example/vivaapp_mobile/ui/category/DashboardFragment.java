package com.example.vivaapp_mobile.ui.category;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.vivaapp_mobile.databinding.FragmentCategoryBinding;

public class DashboardFragment extends Fragment {

    private FragmentCategoryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // CardView'lara onClickListener'ları ekleme
        binding.category1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Konsola bastırılacak ID
                Log.d("DashboardFragment", "Clicked on category1");
            }
        });

        binding.category2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Konsola bastırılacak ID
                Log.d("DashboardFragment", "Clicked on category2");
            }
        });

        // Diğer CardView'lara da aynı şekilde onClickListener ekleyebilirsiniz.

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}