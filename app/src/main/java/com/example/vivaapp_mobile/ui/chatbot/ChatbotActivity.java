package com.example.vivaapp_mobile.ui.chatbot;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.vivaapp_mobile.MainActivity;
import com.example.vivaapp_mobile.databinding.ActivityChatbotBinding;

import java.util.ArrayList;
import java.util.List;

public class ChatbotActivity extends AppCompatActivity {

    private ActivityChatbotBinding binding;
    private ChatAdapter adapter;
    private List<ChatMessage> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatbotBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerViewChat.setLayoutManager(layoutManager);

        messageList = new ArrayList<>();
        adapter = new ChatAdapter(messageList);
        binding.recyclerViewChat.setAdapter(adapter);

        // İlk bot mesajını ekleyelim
        addBotMessage("Merhaba, size nasıl yardımcı olabilirim?");

        binding.anasayfaDon.setOnClickListener(view -> {
            Intent intent = new Intent(ChatbotActivity.this, MainActivity.class);
            startActivity(intent);
        });

        binding.buttonSend.setOnClickListener(view -> {
            // EditText'ten kullanıcının girdiği metni al
            String messageText = binding.editTextMessage.getText().toString().trim();

            // Kullanıcının girdiği metnin boş olup olmadığını kontrol et
            if (!messageText.isEmpty()) {
                // Kullanıcı mesajını RecyclerView'e ekle
                addUserMessage(messageText);
                // Bot'un cevabını burada ekleyelim
                simulateBotResponse();
                // EditText'i temizle
                binding.editTextMessage.setText("");
            }
        });

    }

    // Kullanıcı mesajını ekleme metodu
    private void addUserMessage(String messageText) {
        ChatMessage userMessage = new ChatMessage(messageText, ChatAdapter.TYPE_USER);
        messageList.add(userMessage);
        adapter.notifyDataSetChanged();
    }

    // Bot mesajını ekleme metodu
    private void addBotMessage(String messageText) {
        ChatMessage botMessage = new ChatMessage(messageText, ChatAdapter.TYPE_BOT);
        messageList.add(botMessage);
        adapter.notifyDataSetChanged();
    }

    // Bot tepkisini simüle etme metodu
    private void simulateBotResponse() {
        // Bot'un cevabını burada oluşturabilirsiniz
        // Örneğin, basit bir cevap:
        String botResponse = "Anladım, işte istediğiniz kıymalı makarna: ...";
        addBotMessage(botResponse);
    }
}
