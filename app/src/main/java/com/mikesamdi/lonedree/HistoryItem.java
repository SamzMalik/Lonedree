package com.mikesamdi.lonedree;

public class HistoryItem {
    private String title;
    private String imageURL;
    private String life;
    private double price;
    private int count;

    public HistoryItem() {

    }

    public HistoryItem(String title, String imageURL, String life, double price, int count) {
        this.title = title;
        this.imageURL = imageURL;
        this.life = life;
        this.price = price;
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getLife() {
        return life;
    }

    public void setLife(String life) {
        this.life = life;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    // Getters for all fields
}

