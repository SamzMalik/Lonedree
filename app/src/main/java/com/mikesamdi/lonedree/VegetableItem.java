package com.mikesamdi.lonedree;

public class VegetableItem {
    private String name;
    private String life;
    private double price;
    private String imageURL;

    public VegetableItem() {
        // Default constructor required for Firebase
    }

    public VegetableItem(String name, String life, double price, String imageURL) {
        this.name = name;
        this.life = life;
        this.price = price;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public String getLife() {
        return life;
    }

    public double getPrice() {
        return price;
    }

    public String getImageURL() {
        return imageURL;
    }
    // Add getters and setters as needed
}

