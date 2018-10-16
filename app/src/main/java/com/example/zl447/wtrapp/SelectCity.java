package com.example.zl447.wtrapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

import app.MyApplication;
import bean.City;

public class SelectCity extends Activity implements View.OnClickListener{


    private ImageView mBackBTM;
    private ClearEditText mClearEditText;
    private ListView mList;



    private List<City> cityList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city);

        initViews();
    }
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back:
                Intent i = new Intent();
                i.putExtra("cityCode", "101160101");
                setResult(RESULT_OK, i);
                finish();
                break;
             default:
                 break;
        }
    }
    private void initViews(){

        mBackBTM = (ImageView) findViewById(R.id.title_back);
        mBackBTM.setOnClickListener(this);
    }
}
