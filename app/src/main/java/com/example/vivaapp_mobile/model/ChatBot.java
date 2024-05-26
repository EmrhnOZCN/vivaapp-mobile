package com.example.vivaapp_mobile.model;

import java.util.ArrayList;

public class ChatBot {

    private int id;

    private String foodName;

    private ArrayList<String> materials;

    private String videoUrl;

    public ChatBot(int id, String foodName, ArrayList<String> materials, String videoUrl) {
        this.id = id;
        this.foodName = foodName;
        this.materials = materials;
        this.videoUrl = videoUrl;
    }

    public ChatBot() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public ArrayList<String> getMaterials() {
        return materials;
    }

    public void setMaterials(ArrayList<String> materials) {
        this.materials = materials;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
