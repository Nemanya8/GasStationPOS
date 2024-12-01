package com.incognito.gasstationpos.models;

public class Item {

    private String id;
    private String name;
    private String image;
    private double value;
    private int quantity;

    // Default no-argument constructor for Firestore deserialization
    public Item() {
        // No-argument constructor is required for Firebase deserialization
    }

    // Constructor with parameters
    public Item(String id, String name, String image, double value, int quantity) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.value = value;
        this.quantity = quantity;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
