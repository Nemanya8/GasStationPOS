package com.incognito.gasstationpos.services;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.util.Log;

import com.incognito.gasstationpos.ecr.EcrJsonReq;
import com.incognito.gasstationpos.ecr.EcrRequestTransactionType;
import com.google.gson.Gson;
import com.incognito.gasstationpos.transaction.TransactionData;
import com.incognito.gasstationpos.utils.HashUtils;

import java.math.BigDecimal;

public class PosService {


    public static final String LANGUAGE =  "sr";
    public static final String CURRENCY =  "RSD";

    private String packageName;
    private Context appContext;

    public PosService(String packageName, Context appContext) {
        this.packageName = packageName;
        this.appContext = appContext;
    }

    public void Pay(BigDecimal billTotal) {
        performPayment(billTotal);
    }

    public void PrintReceipt() {
        String jsonRequest = "{\n" +
                "\t\"header\":{\n" +
                "\t\t\"length\":300,\n" +
                "\t\t\"hash\":\"9397e30d8f92da29d9c1016a3e0eab55c7874f5b1cc67e400ecfac233f82889234f41328b2df08066b3e692dcb6f028d56b0fd009fca18fdac9b5669f6775e71\",\n" +
                "\t\t\"version\":\"1\"\n" +
                "\t},\n" +
                "\t\n" +
                "\t\"request\":{\n" +
                "\t\t\"command\":{\n" +
                "\t\t\t\"printer\":{\n" +
                "\t\t\t\t\"type\":\"QR\",\n" +
                "\t\t\t\t\"data\":\"VGVzdCBRUiBjb2RlIHByaW50aW5nLiAKVGhpcyBpcyBzb21lIGxvbmcgdGVzdCB0aGF0IGlzIGdlbmVyYXRlZCBieSBkdW1teSBFQ1IgYXBwbGljYXRpb24uIApJdCBpcyB0aGVuIEJhc2U2NCBlbmNvZGVkIGFuZCBlbmNhcHN1bGF0ZWQgaW4gSlNPTiBFQ1IgcmVxdWVzdC4K\"\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";

        try {
            sendJsonStringToApos(jsonRequest, false);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    void performPayment(BigDecimal billTotal) {
        EcrJsonReq ecrJsonReq = new EcrJsonReq();
        ecrJsonReq.header = new EcrJsonReq.Header();
        ecrJsonReq.request = new EcrJsonReq.Request();
        ecrJsonReq.request.financial = new EcrJsonReq.Financial();
        ecrJsonReq.request.financial.id = new EcrJsonReq.Id();
        ecrJsonReq.request.financial.transaction = EcrRequestTransactionType.sale;
        ecrJsonReq.request.financial.amounts = new EcrJsonReq.Amounts();
        ecrJsonReq.request.financial.amounts.currencyCode = CURRENCY;
        ecrJsonReq.request.financial.amounts.base = formatAmount(billTotal, false);
        ecrJsonReq.request.financial.options = new EcrJsonReq.Options();
        ecrJsonReq.request.financial.options.language = LANGUAGE;
        ecrJsonReq.request.financial.options.print = "false";

        String tempRequest = "\"request\":"+new Gson().toJson(ecrJsonReq.request);
        String generatedSHA512 = HashUtils.performSHA512(tempRequest);

        ecrJsonReq.header.version = "01";
        ecrJsonReq.header.length = tempRequest.length();
        ecrJsonReq.header.hash = generatedSHA512;

        String jsonRequest = new Gson().toJson(ecrJsonReq);
        Log.d("ECR", "Request: " + jsonRequest);

        TransactionData newTrans = new TransactionData();
        newTrans.transaction = ecrJsonReq.request.financial.transaction;
        newTrans.base = ecrJsonReq.request.financial.amounts.base;
        newTrans.currencyCode = ecrJsonReq.request.financial.amounts.currencyCode;
        newTrans.pending = true;
        newTrans.voided = false;

        try {
            sendJsonStringToApos(jsonRequest, false);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendJsonStringToApos(String json, boolean fromAssets) throws RemoteException, InterruptedException {

            Intent intent = new Intent("com.payten.ecr.action");
            intent.setPackage("com.payten.paytenapos");
            if (fromAssets) {
            }
            else{
                intent.putExtra("ecrJson", json);
            }
            intent.putExtra("senderIntentFilter", "senderIntentFilter");
            intent.putExtra("senderPackage", packageName);
            intent.putExtra("senderClass", "com.payten.ecrdemo.MainActivity");
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            appContext.sendBroadcast(intent);
    }

    String formatAmount(BigDecimal d, boolean withCurrency){
        String s = String.valueOf(d);
        int decimalPosition = s.indexOf('.');
        if (decimalPosition < 0){
            // No decimals point
            s = s + ".00";
        }
        else if (decimalPosition == s.length() - 2) {
            // Only one decimal
            s = s + "0";
        }
        else if (decimalPosition == s.length() - 1) {
            // decimal point at the end without digits to follow
            s = s + "00";
        }
        if (withCurrency){
            s = s + " " + CURRENCY;
        }
        return s;
    }
}
