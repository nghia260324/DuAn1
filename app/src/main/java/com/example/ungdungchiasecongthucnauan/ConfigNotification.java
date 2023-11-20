package com.example.ungdungchiasecongthucnauan;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

public class ConfigNotification extends Application {
    public static final String CHANNEL_ID = "MYAPP";

    @Override
    public void onCreate() {
        super.onCreate();
        config();
    }


    private void config() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Báo thức";
            String description = "Đã đến giờ hẹn !!!";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            Uri u = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION).build();
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,name,importance);
            channel.setDescription(description);
            channel.setSound(u,audioAttributes);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
