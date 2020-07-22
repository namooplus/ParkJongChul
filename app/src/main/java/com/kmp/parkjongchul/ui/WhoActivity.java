package com.kmp.parkjongchul.ui;

import android.animation.Animator;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kmp.parkjongchul.R;
import com.kmp.parkjongchul.helper.HeightAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.codetail.animation.ViewAnimationUtils;

public class WhoActivity extends Activity
{
    @BindView(R.id.who_title_color) View colorView;

    @BindView(R.id.who_contents_layout) LinearLayout contentsLayout;

    @BindView(R.id.who_content_scrollview) ScrollView contentScrollView;

    @BindView(R.id.who_content_a) TextView contentA;
    @BindView(R.id.who_content_b) TextView contentB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who);

        ButterKnife.bind(this);

        setUpFlag();
        colorTitle();
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

    public void showContents(View v)
    {
        if (contentsLayout.getAlpha() != 1f)
        {
            contentsLayout.animate()
                    .alpha(1f)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .setDuration(400)
                    .withLayer()
                    .withEndAction(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            contentsLayout.setAlpha(1f);
                        }
                    });
            HeightAnimation animation = new HeightAnimation(contentsLayout, 0, 50*2);
            animation.setInterpolator(new FastOutSlowInInterpolator());
            animation.setDuration(400);
            contentsLayout.startAnimation(animation);
        }
        else
        {
            contentsLayout.animate()
                    .alpha(0f)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .setDuration(400)
                    .withLayer()
                    .withEndAction(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            contentsLayout.setAlpha(0f);
                        }
                    });
            HeightAnimation animation = new HeightAnimation(contentsLayout, 50*2, 0);
            animation.setInterpolator(new FastOutSlowInInterpolator());
            animation.setDuration(400);
            contentsLayout.startAnimation(animation);
        }
    }
    public void moveToContentA(View v)
    {
        contentScrollView.smoothScrollTo(0, contentA.getTop() - (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()));
    }
    public void moveToContentB(View v)
    {
        contentScrollView.smoothScrollTo(0, contentB.getTop() - (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()));
    }
}
