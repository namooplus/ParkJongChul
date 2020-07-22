package com.kmp.parkjongchul.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MainPagerAdapter extends FragmentStatePagerAdapter
{
    private int tabNum;

    public MainPagerAdapter(FragmentManager fragmentManager, int tabNum)
    {
        super(fragmentManager);
        this.tabNum = tabNum;
    }
    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                return new InitialFragment();

            case 1:
                return new WhoFragment();

            case 2:
                return new MovementFragment();

            case 3:
                return new MemorialFragment();

            case 4:
                return new NowFragment();

            default:
                return null;
        }
    }
    @Override
    public int getCount()
    {
        return tabNum;
    }
}

