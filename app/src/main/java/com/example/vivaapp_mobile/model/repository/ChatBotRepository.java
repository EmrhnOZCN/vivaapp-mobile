package com.example.vivaapp_mobile.model.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.vivaapp_mobile.model.ChatBot;

import java.util.ArrayList;

public class ChatBotRepository {

    private DatabaseHelper dbHelper;
    private Context context;

    public ChatBotRepository(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    // ChatBot ekleme işlemi
    public long addChatBot(ChatBot chatBot) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_FOOD_NAME, chatBot.getFoodName());
        cv.put(DatabaseHelper.COLUMN_MATERIALS, String.join(",", chatBot.getMaterials()));
        cv.put(DatabaseHelper.COLUMN_VIDEO_URL, chatBot.getVideoUrl());
        long result = db.insert(DatabaseHelper.TABLE_CHATBOT, null, cv);
        if (result == -1) {
            Toast.makeText(context, "ChatBot ekleme başarısız oldu", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "ChatBot başarıyla eklendi", Toast.LENGTH_SHORT).show();
        }
        db.close();
        return result;
    }

    // ChatBot bilgilerini ID ile almak için metod
    public ChatBot getChatBotById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_CHATBOT + " WHERE " + DatabaseHelper.COLUMN_CHATBOT_ID + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor != null && cursor.moveToFirst()) {
            int foodNameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_NAME);
            int reciepIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_MATERIALS);
            int videoUrlIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_VIDEO_URL);

            if (foodNameIndex != -1 && reciepIndex != -1 && videoUrlIndex != -1) {
                String foodName = cursor.getString(foodNameIndex);
                String reciep = cursor.getString(reciepIndex);
                String videoUrl = cursor.getString(videoUrlIndex);

                cursor.close();

                ArrayList<String> reciepList = new ArrayList<>();
                if (reciep != null) {
                    for (String item : reciep.split(",")) {
                        reciepList.add(item);
                    }
                }

                return new ChatBot(id, foodName, reciepList, videoUrl);
            } else {
                cursor.close();
                throw new IllegalStateException("Sütun adları bulunamadı.");
            }
        }

        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    // Tüm ChatBot kayıtlarını almak için metod
    public ArrayList<ChatBot> getAllChatBots() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<ChatBot> chatBots = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_CHATBOT;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CHATBOT_ID);
                int foodNameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_NAME);
                int reciepIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_MATERIALS);
                int videoUrlIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_VIDEO_URL);

                if (idIndex != -1 && foodNameIndex != -1 && reciepIndex != -1 && videoUrlIndex != -1) {
                    int id = cursor.getInt(idIndex);
                    String foodName = cursor.getString(foodNameIndex);
                    String reciep = cursor.getString(reciepIndex);
                    String videoUrl = cursor.getString(videoUrlIndex);

                    ArrayList<String> reciepList = new ArrayList<>();
                    if (reciep != null) {
                        for (String item : reciep.split(",")) {
                            reciepList.add(item);
                        }
                    }

                    chatBots.add(new ChatBot(id, foodName, reciepList, videoUrl));
                }
            } while (cursor.moveToNext());

            cursor.close();
        }

        return chatBots;
    }

    // ChatBot güncelleme işlemi
    public int updateChatBot(ChatBot chatBot) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_FOOD_NAME, chatBot.getFoodName());
        cv.put(DatabaseHelper.COLUMN_MATERIALS, String.join(",", chatBot.getMaterials()));
        cv.put(DatabaseHelper.COLUMN_VIDEO_URL, chatBot.getVideoUrl());

        int result = db.update(DatabaseHelper.TABLE_CHATBOT, cv, DatabaseHelper.COLUMN_CHATBOT_ID + "=?", new String[]{String.valueOf(chatBot.getId())});
        db.close();
        return result;
    }

    // ChatBot silme işlemi
    public int deleteChatBot(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete(DatabaseHelper.TABLE_CHATBOT, DatabaseHelper.COLUMN_CHATBOT_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result;
    }

    // ChatBot yemeğini adına göre almak için metod
    public ChatBot getChatBotByFoodName(String foodName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_CHATBOT + " WHERE " + DatabaseHelper.COLUMN_FOOD_NAME + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{foodName});

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CHATBOT_ID);
            int foodNameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_NAME);
            int reciepIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_MATERIALS);
            int videoUrlIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_VIDEO_URL);

            if (idIndex != -1 && foodNameIndex != -1 && reciepIndex != -1 && videoUrlIndex != -1) {
                int id = cursor.getInt(idIndex);
                String reciep = cursor.getString(reciepIndex);
                String videoUrl = cursor.getString(videoUrlIndex);

                cursor.close();

                ArrayList<String> reciepList = new ArrayList<>();
                if (reciep != null) {
                    for (String item : reciep.split(",")) {
                        reciepList.add(item);
                    }
                }

                return new ChatBot(id, foodName, reciepList, videoUrl);
            } else {
                cursor.close();
                throw new IllegalStateException("Sütun adları bulunamadı.");
            }
        }

        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

}
