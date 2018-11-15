package com.example.zl447.wtrapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class BaseActivity  extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //去掉标题栏；
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    //重写startActivity方法；
    public void startActivity(Context context1, Class<?> context2)
    {
        startActivity(new Intent(context1, context2));
    }
}
