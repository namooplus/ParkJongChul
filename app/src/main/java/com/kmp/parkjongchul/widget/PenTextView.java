package com.kmp.parkjongchul.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

public class PenTextView extends TextView
{
    public PenTextView(Context context)
    {
        super(context);
        setType(context);
    }
    public PenTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setType(context);
    }
    public PenTextView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        setType(context);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PenTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        setType(context);
    }

    private void setType(Context context)
    {
        setTypeface(Typeface.createFromAsset(context.getAssets(), "NanumPen.ttf"));
    }
}
