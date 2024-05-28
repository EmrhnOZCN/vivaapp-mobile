package com.example.vivaapp_mobile.ui.chatbot;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vivaapp_mobile.R;
import com.example.vivaapp_mobile.model.ChatMessage;
import com.example.vivaapp_mobile.model.Product;
import com.example.vivaapp_mobile.model.repository.DatabaseHelper;
import com.example.vivaapp_mobile.model.repository.ProductRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ChatMessage> messages;
    static final int TYPE_USER = 1;
    static final int TYPE_BOT = 2;

    public ChatAdapter(List<ChatMessage> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case TYPE_USER:
                view = inflater.inflate(R.layout.item_user_message_chatbot, parent, false);
                return new UserMessageViewHolder(view);
            case TYPE_BOT:
                view = inflater.inflate(R.layout.item_bot_message_chatbot, parent, false);
                return new BotMessageViewHolder(view);
            default:
                throw new IllegalArgumentException("Invalid view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage message = messages.get(position);
        switch (holder.getItemViewType()) {
            case TYPE_USER:
                ((UserMessageViewHolder) holder).bind(message);
                break;
            case TYPE_BOT:
                ((BotMessageViewHolder) holder).bind(message);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).getType();
    }

    static class UserMessageViewHolder extends RecyclerView.ViewHolder {
        private TextView messageTextView;

        UserMessageViewHolder(View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.textViewMessage);
        }

        void bind(ChatMessage message) {
            messageTextView.setText(message.getText());
        }
    }

    static class BotMessageViewHolder extends RecyclerView.ViewHolder {
        private TextView messageTextView;
        private Button buttonYes;
        private Button buttonNo;
        private Context context;

        BotMessageViewHolder(View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.textViewMessage);
            buttonYes = itemView.findViewById(R.id.buttonYes);
            buttonNo = itemView.findViewById(R.id.buttonNo);
            context = itemView.getContext();
        }

        void bind(ChatMessage message) {
            messageTextView.setText(message.getText());

            if (message.getText().contains("Malzemeler Sepete Eklensin mi?")) {
                buttonYes.setVisibility(View.VISIBLE);
                buttonNo.setVisibility(View.VISIBLE);

                buttonYes.setOnClickListener(v -> {
                    saveProductsToSharedPreferences(context, message.getText());
                });

                buttonNo.setOnClickListener(v -> {
                    // Handle "Hayır" button click
                });
            } else {
                buttonYes.setVisibility(View.GONE);
                buttonNo.setVisibility(View.GONE);
            }
        }

        private void saveProductsToSharedPreferences(Context context, String message) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("cart_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            // Extract product names from the message
            Set<String> productSet = sharedPreferences.getStringSet("products", new HashSet<>());

            // Assuming the message contains product names separated by newline
            String[] lines = message.split("\n");
            for (String line : lines) {
                if (line.startsWith("- ")) {
                    String productName = line.substring(2); // Remove "- " from the beginning
                    List<Product> products = handleProductSearch(productName);
                    for (Product product : products) {
                        // Adjust the product details to include imageResource and "1"
                        String productDetails = product.getImageResource() + "," +
                                product.getName() + "," +
                                product.getPrice() + "," +
                                "1";
                        productSet.add(productDetails);
                    }
                }
            }

            editor.putStringSet("products", productSet);
            editor.apply();

            // Log productSet to console
            for (String product : productSet) {
                Log.d("ProductSet", product);
            }

            Toast.makeText(context, "Ürünler sepete eklendi", Toast.LENGTH_SHORT).show();
        }


        private List<Product> handleProductSearch(String productName) {
            DatabaseHelper dbHelper = new DatabaseHelper(context);
            ProductRepository productRepository = new ProductRepository(dbHelper);
            List<Product> products = productRepository.getProductsByName(productName);

            if (products.isEmpty()) {
                addBotMessage("Üzgünüm, " + productName + " adlı ürünü bulamadım.");
            } else {
                StringBuilder botResponse = new StringBuilder("İşte " + productName + " ile ilgili ürünler:\n\n");
                for (Product product : products) {
                    botResponse.append("Ürün Adı: ").append(product.getName()).append("\n");
                    botResponse.append("Fiyat: ").append(product.getPrice()).append("\n");
                    botResponse.append("Kategori: ").append(product.getCategoryName()).append("\n\n");
                }
                addBotMessage(botResponse.toString());
            }
            return products;
        }

        private void addBotMessage(String messageText) {
            // Assuming there is a method in ChatAdapter to add a new message
            ((ChatAdapter) ((RecyclerView) itemView.getParent()).getAdapter()).addMessage(new ChatMessage(messageText, TYPE_BOT));
        }
    }

    // Add this method to your ChatAdapter class
    public void addMessage(ChatMessage message) {
        messages.add(message);
        notifyItemInserted(messages.size() - 1);
    }
}
