package com.example.vivaapp_mobile.ui.cart;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.vivaapp_mobile.R;
import com.example.vivaapp_mobile.model.CartItem;
import com.example.vivaapp_mobile.model.repository.OrderRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CartViewModel extends AndroidViewModel {

    private final MutableLiveData<List<CartItem>> cartItems;
    private final MutableLiveData<Double> totalPrice;

    private static final String CHANNEL_ID = "order_notification_channel";

    public CartViewModel(@NonNull Application application) {
        super(application);
        cartItems = new MutableLiveData<>(getCartItemsFromSharedPreferences());
        totalPrice = new MutableLiveData<>(calculateTotalPrice(cartItems.getValue()));
        createNotificationChannel();
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
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("cart_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("products");
        editor.apply();

        cartItems.setValue(new ArrayList<>());
        totalPrice.setValue(0.0);
    }

    public void confirmOrder(int userId, String[] productNames, int quantity, String orderDate) {
        OrderRepository orderRepository = new OrderRepository(getApplication());
        long result = orderRepository.addOrder(userId, productNames, quantity, orderDate);

        if (result != -1) {
            Toast.makeText(getApplication(), "Siparişiniz başarıyla alınmıştır.", Toast.LENGTH_SHORT).show();

            clearCart();

            // Sipariş onaylandığında bildirim gönder
            sendOrderNotification();

            // Ana sayfaya geri dön (bu kısmı implement etmelisiniz)
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Order Notifications";
            String description = "Notifications for order confirmations";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getApplication().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void sendOrderNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplication(), CHANNEL_ID)
                .setSmallIcon(R.drawable.noticon)
                .setContentTitle("Sipariş Onayı")
                .setContentText("Siparişiniz başarıyla alınmıştır.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) getApplication().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}
