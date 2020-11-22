package com.adobe.phonegap.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.trusted.care.local.MainActivity;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getExtras() != null) {
            String action = intent.getStringExtra(ConstantApp.CALL_RESPONSE_ACTION_KEY);

            // Close the notification after the click action is performed.
            performClickAction(context, action, intent.getStringExtra(ConstantApp.CALLBACK_URL), intent.getStringExtra(ConstantApp.CALL_ID));
            Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            context.sendBroadcast(it);
            context.stopService(new Intent(context, FCMService.class));
        }
        // assumes WordService is a registered service
//        String callbackUrl = intent.getStringExtra(ConstantApp.CALLBACK_URL);
//        String callId = intent.getStringExtra(ConstantApp.CALL_ID);
//        FCMService.callWebhook(callbackUrl, callId,"declined_callee");
//        Intent pintent = new Intent(context, MainActivity.class);
//        context.startService(pintent);
    }

    private void performClickAction(Context context, String action, String callbackUrl, String callId) {
        if (action.equals(ConstantApp.CALL_RECEIVE_ACTION)) {
            FCMService.callWebhook(callbackUrl, callId,"pickup");
            Intent openIntent = null;
            openIntent = new Intent(context, MainActivity.class);
            openIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(openIntent);
        } else if (action.equals(ConstantApp.CALL_CANCEL_ACTION)) {
            FCMService.callWebhook(callbackUrl, callId,"declined_callee");
            context.stopService(new Intent(context, FCMService.class));
            Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            context.sendBroadcast(it);
        }
    }
}
