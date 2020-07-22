package com.kmp.parkjongchul.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import com.kmp.parkjongchul.R;
import com.kmp.parkjongchul.fragment.MainPagerAdapter;
import com.kmp.parkjongchul.helper.BlurManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.psdev.licensesdialog.LicensesDialog;

public class MainActivity extends FragmentActivity
{
    MainPagerAdapter pagerAdapter;

    //배경
    @BindView(R.id.main_pager) ViewPager viewPager;

    @BindView(R.id.main_background_blur) ImageView backgroundBlur;

    @BindView(R.id.main_division_layer) View divisionLayer;

    @BindView(R.id.main_status_bar_replace) View statusBarReplace;
    @BindView(R.id.main_navigation_bar_replace) View navigationBarReplace;

    //인디케이터
    @BindView(R.id.main_left_indicator) ImageView leftIndicator;
    @BindView(R.id.main_right_indicator) ImageView rightIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setUpFlag();
        setUpVariables();
        setLayout();
        setUpPager();
    }
    public void setUpFlag()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }
    public void setUpVariables()
    {
        pagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), 5);
    }
    public void setLayout()
    {
        //블러
        backgroundBlur.setImageBitmap(new BlurManager().blur(this, R.drawable.background, 15));

        //상단 하단 여백 수정
        int statusBarHeight = getResources().getDimensionPixelSize(getResources().getIdentifier("status_bar_height", "dimen", "android"));
        int navigationBarHeight = getResources().getDimensionPixelSize(getResources().getIdentifier("navigation_bar_height", "dimen", "android"));

        statusBarReplace.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight));
        navigationBarReplace.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, navigationBarHeight));

        //색 필터
        leftIndicator.setColorFilter(0xffffffff);
        rightIndicator.setColorFilter(0xffffffff);
    }
    public void setUpPager()
    {
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
                //alpha
                if (position == 0)
                {
                    backgroundBlur.setAlpha(positionOffset);
                    divisionLayer.setAlpha(positionOffset);
                }
                else if (position == 1 && position + positionOffset > positionOffsetPixels)
                {
                    backgroundBlur.setAlpha(1f - positionOffset);
                    divisionLayer.setAlpha(1f - positionOffset);
                }

                //indicator
                if (position == 0)
                {
                    leftIndicator.setVisibility(View.INVISIBLE);
                    rightIndicator.setVisibility(View.INVISIBLE);
                }
                else if (position == 1)
                {
                    leftIndicator.setVisibility(View.INVISIBLE);
                    rightIndicator.setVisibility(View.VISIBLE);
                }
                else if (position == 2 || position == 3)
                {
                    leftIndicator.setVisibility(View.VISIBLE);
                    rightIndicator.setVisibility(View.VISIBLE);
                }
                else if (position == 4)
                {
                    leftIndicator.setVisibility(View.VISIBLE);
                    rightIndicator.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onPageSelected(int position)
            {

            }
            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
    }

    public void menu(View v)
    {
        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenuInflater().inflate(R.menu.main_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
        {
            public boolean onMenuItemClick(MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.main_menu_developer:

                        LayoutInflater inflater=getLayoutInflater();
                        final View dialogView= inflater.inflate(R.layout.dialog_developer, null);

                        AlertDialog.Builder buider= new AlertDialog.Builder(MainActivity.this);
                        buider.setView(dialogView);
                        buider.setPositiveButton("확인", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {

                            }
                        });
                        buider.show();

                        break;

                    case R.id.main_menu_opensource_license:

                        new LicensesDialog.Builder(MainActivity.this)
                                .setNotices(R.raw.license)
                                .build()
                                .show();

                        break;
                }
                return true;
            }
        });
        popup.show();
    }
}
