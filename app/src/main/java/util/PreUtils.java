package util;

import android.content.Context;
import android.content.SharedPreferences;

/*
 * 当用户第一次登入时显示引导页，并通过SharePreferences记录状态，之后不再显示引导页（将缓存清除会再次显示）；
 */
public class PreUtils
{
    public static final String PREF_NAME = "config";    //文件名；记录用户是否初次登入；

    //根据键key获取value值（false/true）；
    public static boolean getBoolean(Context context, String key, boolean value)
    {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, value);
    }

    //根据键key设置value值（false/true）并且提交；
    public static void setBoolean(Context context, String key, boolean value)
    {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }
}
