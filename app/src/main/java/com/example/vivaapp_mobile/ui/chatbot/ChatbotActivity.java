package com.example.vivaapp_mobile.ui.chatbot;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.vivaapp_mobile.MainActivity;
import com.example.vivaapp_mobile.databinding.ActivityChatbotBinding;
import com.example.vivaapp_mobile.model.ChatBot;
import com.example.vivaapp_mobile.model.ChatMessage;
import com.example.vivaapp_mobile.model.repository.ChatBotRepository;

import java.util.ArrayList;
import java.util.List;

public class ChatbotActivity extends AppCompatActivity {

    private ActivityChatbotBinding binding;
    private ChatAdapter adapter;
    private List<ChatMessage> messageList;
    private ChatBotRepository chatBotRepository; // ChatBotRepository örneği

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

        // ChatBotRepository örneği oluştur
        chatBotRepository = new ChatBotRepository(this);

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
                simulateBotResponse(messageText);
                // EditText'i temizle
                binding.editTextMessage.setText("");
            }
        });


        // Örnek ChatBot'ları ekleme
        //addSampleChatBot();
    }

    // Kullanıcı mesajını ekleme metodu
    private void addUserMessage(String messageText) {
        ChatMessage userMessage = new ChatMessage(messageText, ChatAdapter.TYPE_USER);
        messageList.add(userMessage);
        adapter.notifyDataSetChanged();

        binding.recyclerViewChat.scrollToPosition(messageList.size() - 1);
    }

    // Bot mesajını ekleme metodu
    private void addBotMessage(String messageText) {
        ChatMessage botMessage = new ChatMessage(messageText, ChatAdapter.TYPE_BOT);
        messageList.add(botMessage);
        adapter.notifyDataSetChanged();
        binding.recyclerViewChat.scrollToPosition(messageList.size() - 1);
    }

    // Bot tepkisini simüle etme metodu
    private void simulateBotResponse(String userMessage) {
        // Kullanıcının mesajında geçen yemeği veritabanından arayalım
        ChatBot chatBot = chatBotRepository.getChatBotByFoodName(userMessage);

        if (chatBot != null) {
            // Yemek bulunduysa, bot mesajında malzemeleri listele
            String botResponse = "İşte " + userMessage + " için gerekli malzemeler:\n\n";
            for (String material : chatBot.getMaterials()) {
                botResponse += "- " + material + "\n";
            }
            // Video URL'sini de ekle

            botResponse += "\nİşte yapılış videosu:\n " + chatBot.getVideoUrl();

          botResponse += "\n\nMalzemeler Sepete Eklensin mi?:\n " ;

            addBotMessage(botResponse);



        } else {
            // Yemek bulunamadıysa, uygun bir mesaj gönder
            addBotMessage("Üzgünüm, " + userMessage + " tarifini bulamadım.");
        }
    }


    // Örnek ChatBot'ları ekleyen metot
    private void addSampleChatBot() {
        ChatBot sampleChatBot = new ChatBot();
        sampleChatBot.setFoodName("Kıymalı Makarna");
        ArrayList<String> materials = new ArrayList<>();
        materials.add("Makarna");
        materials.add("Dana Kıymalık");
        materials.add("Soğan");
        materials.add("Domates");
        sampleChatBot.setMaterials(materials);
        sampleChatBot.setVideoUrl("http://www.youtube.com/watch?v=PIN91huI2L8");

        chatBotRepository.addChatBot(sampleChatBot);

        // Menemen
        ChatBot sampleChatBot5 = new ChatBot();
        sampleChatBot5.setFoodName("Menemen");
        ArrayList<String> reciep5 = new ArrayList<>();
        reciep5.add("Yumurta");
        reciep5.add("Domates");
        reciep5.add("Biber");
        reciep5.add("Soğan");
        sampleChatBot5.setMaterials(reciep5);
        sampleChatBot5.setVideoUrl("http://www.youtube.com/watch?v=teQLY4SV3qY");
        chatBotRepository.addChatBot(sampleChatBot5);
    }
}

