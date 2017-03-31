package com.jal.util;

import android.content.Context;

/**
 * Created by SEELE on 2017/2/8.
 */

public class SizeUtil {

    /**
     * dp转px
     *
     * @param context
     * @param value
     * @return
     */
    public static int dp2px(Context context, float value) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (value * scale + 0.5f);
    }

    /**
     * sp转px
     * @param context
     * @param value
     * @return
     */
    public static int sp2px(Context context, float value) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (value*fontScale+0.5f);
    }
}
