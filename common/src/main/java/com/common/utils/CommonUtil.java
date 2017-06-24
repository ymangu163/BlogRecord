package com.common.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;

/**
 * Created by gao on 2017/6/21.
 */

public class CommonUtil {

    public static Typeface iconfont;
    /**
     * 测量View的宽高
     *
     * @param view View
     */
    public static void measureWidthAndHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
    }

    /**
     *
     * @param context
     * @return
     */
    public static Typeface getIconfont(Context context) {
        if (iconfont == null) {
            iconfont = Typeface.createFromAsset(context.getAssets(), "iconfont.ttf");
        }
        return iconfont;
    }
}
