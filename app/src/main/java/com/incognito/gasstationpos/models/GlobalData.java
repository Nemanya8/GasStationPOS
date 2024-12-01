package com.incognito.gasstationpos.models;

public class GlobalData {
    // Private static instance of the singleton class
    private static GlobalData instance;
    private Receipt globalReceipt;


    // Private constructor to prevent instantiation
    private GlobalData() {
        //TODO: FIX
        globalReceipt = new Receipt(System.currentTimeMillis());
        Item item1 = new Item("1", "Coca Cola", 60,  100);
        Item item2 = new Item("2", "Pepsi", 50,  100);
        globalReceipt.addItem(item1);
        globalReceipt.addItem(item2);
    }

    // Public static method to get the instance
    public static GlobalData getInstance() {
        if (instance == null) {
            instance = new GlobalData();
        }
        return instance;
    }


    // Getter for globalReceipt
    public Receipt getGlobalReceipt() {
        return globalReceipt;
    }

    // Setter for globalReceipt
    public void setGlobalReceipt(Receipt globalReceipt) {
        this.globalReceipt = globalReceipt;
    }
}