package util;

import android.content.Context;

/*
 * 尺寸适配；dp = px / 设备密度；
 */
public class DensityUtils
{
    public static int dp2px(Context ctx, float dp)
    {
        float density = ctx.getResources().getDisplayMetrics().density;
        int px = (int) (dp * density + 0.5f);
        // 2.8 + 0.5 = 3.3 ——> 3（2.8——3）;  2.2 + 0.5 = 2.7 ——>2（2.2——2）;
        return px;
    }

    public static float px2dp(Context ctx, int px)
    {
        float density = ctx.getResources().getDisplayMetrics().density;
        float dp = px / density;
        return dp;
    }
}
