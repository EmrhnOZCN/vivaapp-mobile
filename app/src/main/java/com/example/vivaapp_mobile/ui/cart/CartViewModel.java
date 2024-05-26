package com.example.vivaapp_mobile.ui.cart;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.vivaapp_mobile.model.CartItem;
import com.example.vivaapp_mobile.model.repository.OrderRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CartViewModel extends AndroidViewModel {

    private final MutableLiveData<List<CartItem>> cartItems;
    private final MutableLiveData<Double> totalPrice;

    public CartViewModel(@NonNull Application application) {
        super(application);
        cartItems = new MutableLiveData<>(getCartItemsFromSharedPreferences());
        totalPrice = new MutableLiveData<>(calculateTotalPrice(cartItems.getValue()));
    }

    public LiveData<List<CartItem>> getCartItems() {
        return cartItems;
    }

    public LiveData<Double> getTotalPrice() {
        return totalPrice;
    }

    public void updateItemQuantity(int position, int newQuantity) {
        List<CartItem> items = cartItems.getValue();
        if (items != null && position < items.size()) {
            items.get(position).setQuantity(newQuantity);
            cartItems.setValue(items);
            totalPrice.setValue(calculateTotalPrice(items));
        }
    }

    private double calculateTotalPrice(List<CartItem> items) {
        double total = 0;
        if (items != null) {
            for (CartItem item : items) {
                total += item.getPrice() * item.getQuantity();
            }
        }
        return total;
    }

    private List<CartItem> getCartItemsFromSharedPreferences() {
        List<CartItem> cartItems = new ArrayList<>();
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("cart_prefs", Context.MODE_PRIVATE);
        Set<String> productSet = sharedPreferences.getStringSet("products", new HashSet<>());

        if (productSet != null) {
            for (String productString : productSet) {
                String[] parts = productString.split(",");
                int imageResource = Integer.parseInt(parts[0]);
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);
                int quantity = Integer.parseInt(parts[3]);

                CartItem item = new CartItem(name, price, imageResource, quantity);
                cartItems.add(item);
            }
        }

        return cartItems;
    }
    private void clearCart() {
        // Sepetinizi temizlemek için SharedPreferences kullanın
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("cart_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("products");
        editor.apply();

        // LiveData'yı güncelleyerek sepeti boşaltın
        cartItems.setValue(new ArrayList<>());
        totalPrice.setValue(0.0);
    }


    public void confirmOrder(int userId, String[] productNames, int quantity, String orderDate) {
        // Siparişi veritabanına eklemek için OrderRepository sınıfını kullanın.
        OrderRepository orderRepository = new OrderRepository(getApplication());
        long result = orderRepository.addOrder(userId, productNames, quantity, orderDate);

        if (result != -1) {
            // Sepet başarıyla temizlendi ve sipariş alındı, bu yüzden Toast mesajı gösterin
            Toast.makeText(getApplication(), "Siparişiniz başarıyla alınmıştır.", Toast.LENGTH_SHORT).show();

            // Sepeti temizle
            clearCart();

            // Ana sayfaya geri dön

        }
    }


}
