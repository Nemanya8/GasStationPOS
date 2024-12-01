package com.incognito.gasstationpos.models;

import java.util.ArrayList;
import java.util.List;

public class Receipt {

    private String id;
    private List<Item> items; // This will hold the items for the receipt
    private double value;
    private long timestamp;

    // Constructor
    public Receipt(String id, double value, long timestamp) {
        this.id = id;
        this.items = new ArrayList<>(); // Initialize the list here
        this.value = value;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
