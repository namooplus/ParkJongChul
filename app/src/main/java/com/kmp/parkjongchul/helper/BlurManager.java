package com.kmp.parkjongchul.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

public class BlurManager
{
    public static Bitmap blur(Context context, int drawble, int radius)
    {
        Bitmap original = BitmapFactory.decodeResource(context.getResources(), drawble);
        Bitmap bitmap = original.copy(original.getConfig(), true);

        RenderScript renderScript = RenderScript.create(context);

        Allocation input = Allocation.createFromBitmap(renderScript, original, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
        Allocation output = Allocation.createTyped(renderScript, input.getType());

        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        script.setRadius(radius);
        script.setInput(input);
        script.forEach(output);

        output.copyTo(bitmap);

        return bitmap;
    }
}
