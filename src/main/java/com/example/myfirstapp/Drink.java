package com.example.myfirstapp;

public class Drink {
    private String id;
    private String name;
    private String description;
    private String price;
    private String emoji;
    private String category;

    public Drink() {
        // Required for Firebase
    }

    public Drink(String name, String description, String price, String emoji, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.emoji = emoji;
        this.category = category;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getEmoji() { return emoji; }
    public void setEmoji(String emoji) { this.emoji = emoji; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
