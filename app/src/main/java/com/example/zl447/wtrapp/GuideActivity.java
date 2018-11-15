package com.example.zl447.wtrapp;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import util.DensityUtils;
import util.PreUtils;

public class GuideActivity extends BaseActivity
{
    private static final int[] mImgIds = new int[]
            {R.drawable.biz_plugin_weather_qing, R.drawable.biz_plugin_weather_zhongxue, R.drawable.biz_plugin_weather_zhongyu};
    private ArrayList<ImageView> mImgViewList; //引导页图片集合；
    private ViewPager vpGuide;
    private LinearLayout llPointGroup;         //引导页圆点；
    private int mPointWidth;                   //两点间的距离；
    private View viewRedPoint;                 //底部跟随图片而动的红色point；
    private Button btnStart;                   //开始体验按钮；

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        vpGuide = (ViewPager)findViewById(R.id.guide_vp);
        llPointGroup = (LinearLayout)findViewById(R.id.guide_ll_point);
        viewRedPoint = (View)findViewById(R.id.view_red_point);
        btnStart = (Button)findViewById(R.id.guide_start_btn);

        btnStart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                PreUtils.setBoolean(GuideActivity.this, "guide_showed", true);//将值设置为true后不再显示引导页；

                startActivity(GuideActivity.this, MainActivity.class);
                finish();
            }
        });

        initViews();                                              //初始化界面；
        vpGuide.setAdapter(new GuideAdapter());                   //设置适配器；

        vpGuide.setOnPageChangeListener(new GuidePageListener()); //ViewPager的滑动监听；
    }



    //初始化界面；
    private void initViews()
    {
        mImgViewList = new ArrayList<ImageView>();
        for(int i=0; i<mImgIds.length; i++)
        {
            ImageView img = new ImageView(this);
            img.setBackgroundResource(mImgIds[i]); //设置引导页的背景；
            mImgViewList.add(img);                 //将背景添加进集合中；
        }

        //初始化灰色小圆点；
        for(int i=0; i<mImgIds.length; i++)
        {
            View point = new View(this);
            point.setBackgroundResource(R.drawable.shape_point_grey);//设置引导页默认圆点；
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                    (DensityUtils.dp2px(this, 10), DensityUtils.dp2px(this, 10)); //设置圆点的大小1；
            if(i>0)
            {
                params.leftMargin = DensityUtils.dp2px(this, 10);    //设置圆点的间距；
            }
            point.setLayoutParams(params); //设置圆点的大小2；
            llPointGroup.addView(point);   //将圆点添加给线性布局；
        }

        //获取视图树； 当layout执行结束后回调此方法；
        llPointGroup.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                llPointGroup.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//              使用下行代码代替上行，但要改变版本号为14以上；
//              llPointGroup.getViewTreeObserver().removeOnGlobalFocusChangeListener(this);
//              获取两个
                mPointWidth = llPointGroup.getChildAt(1).getLeft() - llPointGroup.getChildAt(0).getLeft();
            }
        });
    }

    //自定义适配器；
    class GuideAdapter extends PagerAdapter
    {

        //获取页卡数量；
        @Override
        public int getCount()
        {
            return mImgIds.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1)
        {
            return arg0 == arg1;
        }

        //初始化界面；
        @Override
        public Object instantiateItem(View container, int position)
        {
            ((ViewGroup) container).addView(mImgViewList.get(position));
            return mImgViewList.get(position);
        }

        //移除页卡；
        @Override
        public void destroyItem(View container, int position, Object object)
        {
            ((ViewPager) container).removeView((View)object);
        }

    }

    //ViewPager的滑动监听；
    class GuidePageListener implements ViewPager.OnPageChangeListener
    {


        //滑动状态变化；
        @Override
        public void onPageScrollStateChanged(int arg0)
        {

        }

        //滑动事件；1、 当前位置（0,1,2）；2、偏移的百分比；3、偏移的距离；
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2)
        {
            int lenth = (int) (mPointWidth * arg1 + arg0 * mPointWidth);
            //获取当前红点的布局参数，该view的父布局是relativelayout；
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewRedPoint.getLayoutParams();
            params.leftMargin = lenth;           //实时更新红点与第一个灰点的间距；
            viewRedPoint.setLayoutParams(params);//重新给小红点设置布局参数，效果是可以看到红点在两个灰点之间移动；
        }

        //被选中页面，当滑动到最后一个页面时才显示“开始体验”按钮；
        @Override
        public void onPageSelected(int arg0)
        {
            if(arg0 == mImgIds.length - 1)
            {
                btnStart.setVisibility(View.VISIBLE);
            }
            else
            {
                btnStart.setVisibility(View.INVISIBLE);
            }
        }

    }
}
