package com.core.utils;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * File description.
 *
 * @author dsh
 * @date 2018/7/15
 */

public class ScreenUtils {
    public static float dip2px(int dp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,
                Resources.getSystem().getDisplayMetrics());
    }
}
