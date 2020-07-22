package com.kmp.parkjongchul.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kmp.parkjongchul.R;
import com.kmp.parkjongchul.ui.MovementActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MovementFragment extends Fragment
{
    private Unbinder unbinder;

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_movement, container, false);

        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }
    @OnClick(R.id.movement_show_content_button)
    public void showContent(View v)
    {
        startActivity(new Intent(getActivity(), MovementActivity.class));
    }
    @Override
    public void onDestroyView()
    {
        super.onDestroyView();

        unbinder.unbind();
    }
}
