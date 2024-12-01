package com.incognito.gasstationpos.models;

public class GlobalData {
    // Private static instance of the singleton class
    private static GlobalData instance;
    private Receipt globalReceipt;
    private AppState appState;


    // Private constructor to prevent instantiation
    private GlobalData() {
        //TODO: FIX
        globalReceipt = new Receipt(System.currentTimeMillis());
        Item item1 = new Item("Coca Cola", "Coca Cola", 60,  100);
        Item item2 = new Item("Pepsi", "Pepsi", 50,  100);
        globalReceipt.addItem(item1);
        globalReceipt.addItem(item2);


        //INIT APP STATE
        appState = AppState.NORMAL;
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



    public AppState getAppState() {
        return appState;
    }

    public void setAppState(AppState appState) {
        this.appState = appState;
    }
}