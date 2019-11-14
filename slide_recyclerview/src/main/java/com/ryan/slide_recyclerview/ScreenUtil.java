package com.ryan.slide_recyclerview;

import android.content.Context;
import android.util.TypedValue;

public class ScreenUtil {
    public static int dp2px(int value, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }
}
