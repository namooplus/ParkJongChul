package com.kmp.parkjongchul.ui;

import android.animation.Animator;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.kmp.parkjongchul.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.codetail.animation.ViewAnimationUtils;

public class NowActivity extends Activity
{
    @BindView(R.id.now_title_color) View colorView;

    @BindView(R.id.now_memorial_notification_state) ImageView memorialNotificationState;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now);

        ButterKnife.bind(this);

        setUpFlag();
        colorTitle();
        setUpState();
    }
    public void setUpFlag()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }
    public void colorTitle()
    {
        colorView.post(new Runnable()
        {
            @Override
            public void run()
            {
                // 중심
                int x = (colorView.getLeft() + colorView.getRight()) / 2;
                int y = (colorView.getTop() + colorView.getBottom()) / 2;

                // 최종 반지름
                int dx = Math.max(x, colorView.getWidth() - x);
                int dy = Math.max(y, colorView.getHeight() - y);
                float finalRadius = (float) Math.hypot(dx, dy);

                // 애니메이트
                Animator animator = ViewAnimationUtils.createCircularReveal(colorView, x, y, 0, finalRadius);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.setDuration(400);
                animator.start();
            }
        });
    }
    public void setUpState()
    {
        if (isMemorialNotificationEnabled())
            memorialNotificationState.setAlpha(0.9f);

        else
            memorialNotificationState.setAlpha(0.1f);
    }

    @OnClick (R.id.now_memorial_notification)
    public void toggleMemorialNotification(View v)
    {
        SharedPreferences sharedPreference = getSharedPreferences("prefrences", Context.MODE_PRIVATE);

        if (isMemorialNotificationEnabled())
        {
            SharedPreferences.Editor sharedPreferenceEditor = sharedPreference.edit();
            sharedPreferenceEditor.putBoolean("memorialNotificationEnabled", false);
            sharedPreferenceEditor.commit();

            showMemorialNotification(false);
        }
        else
        {
            SharedPreferences.Editor sharedPreferenceEditor = sharedPreference.edit();
            sharedPreferenceEditor.putBoolean("memorialNotificationEnabled", true);
            sharedPreferenceEditor.commit();

            showMemorialNotification(true);
        }

        setUpState();
    }
    @OnClick(R.id.now_write_memorial)
    public void writeMemorial(View v)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://prayforpjcapp.tistory.com/1"));
        startActivity(intent);
    }

    public boolean isMemorialNotificationEnabled()
    {
        SharedPreferences sharedPreference = getSharedPreferences("prefrences", Context.MODE_PRIVATE);

        return sharedPreference.getBoolean("memorialNotificationEnabled", false);
    }
    public void showMemorialNotification(boolean showing)
    {
        NotificationManager notification = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (showing)
        {
            Notification.Builder mBuilder = new Notification.Builder(this);
            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
            mBuilder.setTicker("항상 기억하겠습니다");
            mBuilder.setContentTitle("항상 기억하겠습니다");
            mBuilder.setContentText("6월 항쟁의 불씨를 지피신 박종철 열사를 추모하며...");
            mBuilder.setPriority(Notification.PRIORITY_MAX);
            mBuilder.setOngoing(true);

            notification.notify(610, mBuilder.build());

            Toast.makeText(this, "추모 알림을 띠우기 시작합니다.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            notification.cancel(610);

            Toast.makeText(this, "추모 알림이 이제 더 이상 표시되지 않습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
