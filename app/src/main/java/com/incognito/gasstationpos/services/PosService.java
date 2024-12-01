package com.incognito.gasstationpos.services;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.util.Base64;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.incognito.gasstationpos.ecr.EcrDef;
import com.incognito.gasstationpos.ecr.EcrJsonReq;
import com.incognito.gasstationpos.ecr.EcrRequestTransactionType;
import com.google.gson.Gson;
import com.incognito.gasstationpos.models.GlobalData;
import com.incognito.gasstationpos.models.Item;
import com.incognito.gasstationpos.models.Receipt;
import com.incognito.gasstationpos.transaction.TransactionData;
import com.incognito.gasstationpos.utils.HashUtils;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

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
        ArrayList<EcrJsonReq.PrintLines> lines = new ArrayList<>();
        prepareBillDataLines(lines);
//        prepareTransactionReceiptLines(lines,);

        printReceipt(lines);


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

    private void printReceipt(ArrayList<EcrJsonReq.PrintLines> lines) {
        EcrJsonReq ecrJsonReq = new EcrJsonReq();
        ecrJsonReq.header = new EcrJsonReq.Header();
        ecrJsonReq.request = new EcrJsonReq.Request();
        ecrJsonReq.request.command = new EcrJsonReq.Command();
        ecrJsonReq.request.command.printer = new EcrJsonReq.Printer();
        ecrJsonReq.request.command.printer.type = EcrDef.printerTypeJson;
        ecrJsonReq.request.command.printer.printLines = lines;

        String tempRequest = "\"request\":"+new GsonBuilder().disableHtmlEscaping().create().toJson(ecrJsonReq.request);
        String generatedSHA512 = HashUtils.performSHA512(tempRequest);

        ecrJsonReq.header.version = "01";
        ecrJsonReq.header.length = tempRequest.length();
        ecrJsonReq.header.hash = generatedSHA512;

        String jsonRequest = new GsonBuilder().disableHtmlEscaping().create().toJson(ecrJsonReq);

        try {
            sendJsonStringToApos(jsonRequest, false);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void addLine(ArrayList<EcrJsonReq.PrintLines> lines, String type, String style, String content){
        EcrJsonReq.PrintLines line = new EcrJsonReq.PrintLines();
        line.type = type;
        line.style = style;
        line.content = content;
        lines.add(line);
    }

    private void prepareBillDataLines(ArrayList<EcrJsonReq.PrintLines> lines) {
        addLine(lines, EcrDef.lineTypeText, EcrDef.lineStyleNormal, " ");
        addLine(lines, EcrDef.lineTypeText, EcrDef.lineStyleNormal, " ");

        addLine(lines, EcrDef.lineTypeText, EcrDef.lineStyleNormal, "================================");
        addLine(lines, EcrDef.lineTypeText, EcrDef.lineStyleNormal, "ITEM");
        addLine(lines, EcrDef.lineTypeText, EcrDef.lineStyleNormal, "  QUANTITY                AMOUNT");
        addLine(lines, EcrDef.lineTypeText, EcrDef.lineStyleNormal, "================================");
        GlobalData instance = GlobalData.getInstance();
        Receipt receipt = GlobalData.getInstance().getGlobalReceipt();
        for (Item item: GlobalData.getInstance().getGlobalReceipt().getItems()) {
            BigDecimal itemValue = new BigDecimal(item.getValue());
            BigDecimal itemQuantity = new BigDecimal(item.getQuantity());
            BigDecimal itemTotal =  itemValue.multiply(itemQuantity);

            addLine(lines, EcrDef.lineTypeText, EcrDef.lineStyleNormal, String.valueOf(item.getName()));
            addLine(lines, EcrDef.lineTypeText, EcrDef.lineStyleNormal, "  " + String.valueOf(item.getQuantity()) + "                                ".substring(0, 30 - String.valueOf(item.getQuantity()).length() - formatAmount(itemTotal, true).length()) + formatAmount(itemTotal, true));
        }
        BigDecimal billTotal = new BigDecimal(GlobalData.getInstance().getGlobalReceipt().getValue());
        addLine(lines, EcrDef.lineTypeText, EcrDef.lineStyleNormal, "____________");
        addLine(lines, EcrDef.lineTypeText, EcrDef.lineStyleNormal, "TOTAL                           ".substring(0, 32 - formatAmount(billTotal, true).length()) + formatAmount(billTotal, true));
        addLine(lines, EcrDef.lineTypeText, EcrDef.lineStyleNormal, " ");
        String qrData = Base64.encodeToString(GlobalData.getInstance().getGlobalReceipt().getId().getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
        addLine(lines, EcrDef.lineTypeQr, EcrDef.lineStyleCondensed, qrData);
    }

    void prepareTransactionReceiptLines(ArrayList<EcrJsonReq.PrintLines> lines, TransactionData transactionData){
        addLine(lines, EcrDef.lineTypeText, EcrDef.lineStyleNormal, " ");
        addLine(lines, EcrDef.lineTypeText, EcrDef.lineStyleNormal, "================================");
        addLine(lines, EcrDef.lineTypeText, EcrDef.lineStyleNormal, "          PAID BY CARD");
        addLine(lines, EcrDef.lineTypeText, EcrDef.lineStyleNormal, "================================");
        addLine(lines, EcrDef.lineTypeText, EcrDef.lineStyleNormal, "INVOICE: " + transactionData.invoice);
        addLine(lines, EcrDef.lineTypeText, EcrDef.lineStyleNormal, "PAN:     " + transactionData.pan);
        addLine(lines, EcrDef.lineTypeText, EcrDef.lineStyleNormal, "AMOUNT:  " + transactionData.base + " " + transactionData.currencyCode);
        addLine(lines, EcrDef.lineTypeText, EcrDef.lineStyleNormal, "AUTH #:  " + transactionData.authorization);
        addLine(lines, EcrDef.lineTypeText, EcrDef.lineStyleNormal, "____________");
        addLine(lines, EcrDef.lineTypeText, EcrDef.lineStyleNormal, " ");
    }


}



