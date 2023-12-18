package com.mikesamdi.lonedree;

public class GroceryItem {
    private String title;
    private String life;
    private int price;
    private String imageURL;
    private int count;


    public GroceryItem() {

    }
    public GroceryItem(String title, String life, int price, String imageURL) {
        this.title = title;
        this.life = life;
        this.price = price;
        this.imageURL = imageURL;
        this.count = 1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLife() {
        return life;
    }

    public void setLife(String life) {
        this.life = life;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
