package com.example.zl447.wtrapp;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import util.PreUtils;

public class SplashActivity extends BaseActivity {

    RelativeLayout rlRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        rlRoot = (RelativeLayout)findViewById(R.id.rl_root);

        startAnimation(); //设置动画；

    }

    private void startAnimation()
    {
        AnimationSet set = new AnimationSet(false); //设置动画集合；

        //旋转动画,0到360度旋转，自身围绕中心点（0.5f）旋转；
        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);                 //旋转事件；
        rotate.setFillAfter(true);                //保持动画状态；

        //缩放动画；
        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1,                             Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(1000);
        scale.setFillAfter(true);

        //淡入淡出动画；
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(1000);
        alpha.setFillAfter(true);

        set.addAnimation(rotate);
        set.addAnimation(scale);
        set.addAnimation(alpha);

        //设置动画监听；
        set.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {
            }
            //动画执行结束；
            @Override
            public void onAnimationEnd(Animation animation)
            {
                nextPage();
            }
        });
        rlRoot.startAnimation(set); //播放动画；
    }

    //不是第一次登陆就直接跳转页面；
    private void nextPage()
    {

        //以下为第一次进入的判断，该app不使用
        boolean userGuide = PreUtils.getBoolean(this, "guide_showed", false);
        if(!userGuide)
        {
            startActivity(SplashActivity.this, GuideActivity.class);
        }
        else
        {
            startActivity(SplashActivity.this, MainActivity.class);
        }
        finish();
    }
}
