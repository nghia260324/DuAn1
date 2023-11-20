package com.example.ungdungchiasecongthucnauan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Báo thức đã đến!", Toast.LENGTH_SHORT).show();
        Intent broadcastIntent = new Intent("ALARM_TRIGGERED");
        context.sendBroadcast(broadcastIntent);
    }
}
