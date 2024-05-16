package com.example.vivaapp_mobile.ui.chatbot;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vivaapp_mobile.R;

import java.util.ArrayList;
import java.util.List;

public class ChatbotActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private List<ChatMessage> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        recyclerView = findViewById(R.id.recyclerViewChat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        messageList = new ArrayList<>();

        // Örnek kullanıcı ve bot mesajları oluştur
        ChatMessage userMessage = new ChatMessage("Kıymalı makarna için sepet oluştur.", ChatAdapter.TYPE_USER);
        ChatMessage botMessage = new ChatMessage("Merhaba, size nasıl yardımcı olabilirim?", ChatAdapter.TYPE_BOT);

        messageList.add(botMessage);
        // Mesajları listeye ekle
        messageList.add(userMessage);
   ;

        // Mesaj listesini adaptöre ayarla
        adapter = new ChatAdapter(messageList);
        recyclerView.setAdapter(adapter);
    }
}
