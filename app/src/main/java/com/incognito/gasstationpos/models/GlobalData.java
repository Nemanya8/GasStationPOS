package com.incognito.gasstationpos.models;

public class GlobalData {

    public static Receipt globalReceipt;

    private GlobalData(){
        globalReceipt = new Receipt(System.currentTimeMillis());
    }

    public static Receipt getGlobalReceipt(){
        return globalReceipt;
    }

}
