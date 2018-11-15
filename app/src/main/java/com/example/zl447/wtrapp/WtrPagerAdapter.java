package com.example.zl447.wtrapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import bean.FtrWeather;
import util.PinYin;

public class WtrPagerAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<FtrWeather> mData;

    public WtrPagerAdapter(Context context , ArrayList<FtrWeather> list) {
        mContext = context;
        mData = list;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(mContext, R.layout.vpview,null);
        TextView tv = (TextView) view.findViewById(R.id.ft_weather_date);
        TextView tv1 = (TextView) view.findViewById(R.id.ft_weather_wd);
        TextView tv2 = (TextView) view.findViewById(R.id.ft_weather_tq);
        TextView tv3 = (TextView) view.findViewById(R.id.ft_weather_fl);
        ImageView img =  (ImageView) view.findViewById(R.id.ft_weather_img);

        tv.setText(mData.get(position).getDate());
        tv1.setText(mData.get(position).getHigh() + "~" +mData.get(position).getLow());
        tv2.setText(mData.get(position).getType());
        tv3.setText(mData.get(position).getFengli());

        String typeImg = "biz_plugin_weather_" +
                PinYin.converterToSpell(mData.get(position).getType());
        Class aClass = R.drawable.class;
        int typeId = -1;
        try{
            //一般尽量采用这种形式
            Field field = aClass.getField(typeImg);
            Object value = field.get(new Integer(0));
            typeId = (int)value;
        }catch(Exception e){
            //e.printStackTrace();
            if ( -1 == typeId)
                typeId = R.drawable.biz_plugin_weather_qing;
        }finally {
            Drawable drawable = mContext.getResources().getDrawable(typeId);
            img.setImageDrawable(drawable);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container,position,object); 这一句要删除，否则报错
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public float getPageWidth(int position) {
        return (float) 0.33;
    }
}
