package com.mikesamdi.lonedree;

public class CartItem {
    private int count;
    private String imageURL;
    private String life;
    private double price;
    private String title;

    public CartItem() {
        // Default constructor required for Firebase
    }

    public CartItem(int count, String imageURL, String life, double price, String title) {
        this.count = count;
        this.imageURL = imageURL;
        this.life = life;
        this.price = price;
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getLife() {
        return life;
    }

    public double getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }
}
