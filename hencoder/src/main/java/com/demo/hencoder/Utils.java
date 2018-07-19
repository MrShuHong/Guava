package com.demo.hencoder;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.util.TypedValue;

/**
 * File description.
 *
 * @author dsh
 * @date 2018/7/18
 */

public class Utils {
    public static float dip2px(int dp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,
                Resources.getSystem().getDisplayMetrics());
    }

    public static Bitmap getBitmap(Context context,@DrawableRes int drawableID, float width){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), drawableID,options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = (int)width;
        return BitmapFactory.decodeResource(context.getResources(), drawableID,options);

    }

    public static Bitmap getBitmap(Resources resources, float width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, R.drawable.timg,options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = (int)width;
        return BitmapFactory.decodeResource(resources, R.drawable.timg,options);
    }
}
