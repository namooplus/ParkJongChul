package com.kmp.parkjongchul.ui;

import android.animation.Animator;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kmp.parkjongchul.R;
import com.kmp.parkjongchul.helper.HeightAnimation;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.codetail.animation.ViewAnimationUtils;

public class MemorialActivity extends Activity
{
    @BindView(R.id.memorial_title_color) View colorView;

    @BindView(R.id.memorial_contents_layout) LinearLayout contentsLayout;

    @BindView(R.id.memorial_content_scrollview) ScrollView contentScrollView;

    @BindView(R.id.memorial_content_a) TextView contentA;
    @BindView(R.id.memorial_content_b) TextView contentB;
    @BindView(R.id.memorial_content_c) TextView contentC;
    @BindView(R.id.memorial_content_d) TextView contentD;

    @BindView(R.id.memorial_image_viewer_center_view) ImageView centerView;
    @BindView(R.id.memorial_image_viewer_center_structure) ImageView centerStructure;
    @BindView(R.id.memorial_image_viewer_center_hall) ImageView centerHall;
    @BindView(R.id.memorial_image_viewer_center_inside) ImageView centerInside;
    @BindView(R.id.memorial_image_viewer_center_museum) ImageView centerMuseum;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorial);

        ButterKnife.bind(this);

        setUpFlag();
        colorTitle();
        loadImage();
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
    public void loadImage()
    {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        imageLoader.displayImage("drawable://" + R.drawable.center_view, centerView);
        imageLoader.displayImage("drawable://" + R.drawable.center_structure, centerStructure);
        imageLoader.displayImage("drawable://" + R.drawable.center_hall, centerHall);
        imageLoader.displayImage("drawable://" + R.drawable.center_inside, centerInside);
        imageLoader.displayImage("drawable://" + R.drawable.center_museum, centerMuseum);
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
            HeightAnimation animation = new HeightAnimation(contentsLayout, 0, 50*4);
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
            HeightAnimation animation = new HeightAnimation(contentsLayout, 50*4, 0);
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
    public void moveToContentC(View v)
    {
        contentScrollView.smoothScrollTo(0, contentC.getTop() - (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()));
    }
    public void moveToContentD(View v)
    {
        contentScrollView.smoothScrollTo(0, contentD.getTop() - (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()));
    }
}
