package com.kmp.parkjongchul.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.kmp.parkjongchul.R;

public class BootReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            if (isMemorialNotificationEnabled(context))
            {
                NotificationManager notification = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                Notification.Builder mBuilder = new Notification.Builder(context);

                mBuilder.setSmallIcon(R.mipmap.ic_launcher);
                mBuilder.setTicker("항상 기억하겠습니다");
                mBuilder.setContentTitle("항상 기억하겠습니다");
                mBuilder.setContentText("6월 항쟁의 불씨를 지피신 박종철 열사를 추모하며...");
                mBuilder.setPriority(Notification.PRIORITY_MAX);
                mBuilder.setOngoing(true);

                notification.notify(610, mBuilder.build());
            }
        }
    }

    public boolean isMemorialNotificationEnabled(Context context)
    {
        SharedPreferences sharedPreference = context.getSharedPreferences("prefrences", Context.MODE_PRIVATE);

        return sharedPreference.getBoolean("memorialNotificationEnabled", false);
    }
}
