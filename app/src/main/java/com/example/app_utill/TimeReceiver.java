package com.example.app_utill;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TimeReceiver extends BroadcastReceiver {


        @Override
    public void onReceive(Context context, Intent intent) {

        final String action = intent.getAction();

        Intent.ACTION_TIME_CHANGED.equals(action);

        if (Intent.ACTION_TIME_CHANGED.equals(action)) {
            // 시간이 변경된 경우 해야 될 작업을 한다.
            intent.putExtra("Change", Intent.ACTION_TIME_CHANGED);
        }
    }
}