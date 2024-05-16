package com.example.vivaapp_mobile.ui.chatbot;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vivaapp_mobile.MainActivity;
import com.example.vivaapp_mobile.R;
import java.util.ArrayList;
import java.util.List;
import com.example.vivaapp_mobile.databinding.ActivityChatbotBinding;

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
        //layoutManager.setStackFromEnd(true); // Mesajları en alttan başlayarak göster
        binding.recyclerViewChat.setLayoutManager(layoutManager);


        messageList = new ArrayList<>();
        adapter = new ChatAdapter(messageList);
        binding.recyclerViewChat.setAdapter(adapter);


        ChatMessage userMessage = new ChatMessage("Kıymalı makarna için sepet oluştur.", ChatAdapter.TYPE_USER);
        ChatMessage botMessage = new ChatMessage("Merhaba, size nasıl yardımcı olabilirim?", ChatAdapter.TYPE_BOT);
        messageList.add(botMessage);
        messageList.add(userMessage);


        adapter.notifyDataSetChanged();


        binding.anasayfaDon.setOnClickListener(view -> {
            Intent intent = new Intent(ChatbotActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
