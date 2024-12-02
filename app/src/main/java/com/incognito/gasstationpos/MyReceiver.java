package com.incognito.gasstationpos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.incognito.gasstationpos.models.AppState;
import com.incognito.gasstationpos.models.GlobalData;
import com.incognito.gasstationpos.services.PosService;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("RECEIVER", "Intent received (including ECR response): " + intent.getAction());

        if(MyApp.currentActivity instanceof MainActivity){
            Log.d("RECEIVER", "Main activity already started");
            if(GlobalData.getInstance().getAppState() == AppState.PAYMENT_STARTED){
                Log.d("RECEIVER", "Payment finished, printing receipt");

                PosService posService = new PosService(context.getPackageName(), context.getApplicationContext());
                posService.PrintReceipt();
                GlobalData.getInstance().setAppState(AppState.NORMAL);
            }
            Intent i = new Intent(context, MainActivity.class);
            i.setAction(intent.getAction());
            if (intent.getStringExtra("ResponseResult") != null && intent.getStringExtra("ResponseResult") != "") {
                i.putExtra("ResponseResult", intent.getStringExtra("ResponseResult"));
            }
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(i);
        }
        else if(MyApp.currentActivity == null){
            if(GlobalData.getInstance().getAppState() == AppState.PAYMENT_STARTED){
                Log.d("RECEIVER", "Payment finished, printing receipt");

                PosService posService = new PosService(context.getPackageName(), context.getApplicationContext());
                posService.PrintReceipt();
                GlobalData.getInstance().setAppState(AppState.NORMAL);
            }
            Log.d("RECEIVER", "Start main activity");
            Intent i = new Intent(context, MainActivity.class);
            i.setAction(intent.getAction());
            if (intent.getStringExtra("ResponseResult") != null && intent.getStringExtra("ResponseResult") != "") {
                i.putExtra("ResponseResult", intent.getStringExtra("ResponseResult"));
            }
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}
