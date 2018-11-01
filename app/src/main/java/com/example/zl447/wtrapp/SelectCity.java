package com.example.zl447.wtrapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.MyApplication;
import bean.City;

import static android.content.ContentValues.TAG;

public class SelectCity extends Activity implements View.OnClickListener{


    private ImageView mBackBTM;
    private ListView mList;
    private TextView mTitle;
    private SearchView mSearchView;

    private ArrayAdapter mAdapter;

    private ArrayList<City> cityList ;
    private ArrayList<String> data;
    private String mNCC ="";

    private static MyApplication mApplication;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city);

        initViews();
    }
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back://返回按钮，直接返回
//                Intent i = new Intent();
//                i.putExtra("cityCode", "101160101");
//                setResult(RESULT_OK, i);
                finish();
                break;
             default:
                 break;
        }
    }
    private void initViews(){//初始化页面
        Intent intent = getIntent();//获取传来的intent对象
        String cityinfo = intent.getStringExtra("nowCity");//获取键值对的键名

        cityList = new ArrayList<City>(mApplication.getInstance().getCityList());//构建城市信息的一个列表
        data = new ArrayList<String>();//构建城市名的一个列表
        for(City s:cityList ){
            data.add(s.getCity());//通过citylist获取数据
        }

        mBackBTM = (ImageView) findViewById(R.id.title_back);
        mBackBTM.setOnClickListener(this);
        mList=(ListView)findViewById(R.id.list_city);
        mTitle=(TextView)findViewById(R.id.title_name);
        mSearchView = (SearchView) findViewById(R.id.search);
        mSearchView.setIconifiedByDefault(false);//搜索框样式
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {//搜索框监听器
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);//按文字过滤listview
                return false;
            }
        });
        mTitle.setText("当前城市："+cityinfo);//更新title
        Log.d(TAG, mList.toString());
        mAdapter = new ArrayAdapter<String>(SelectCity.this,android.R.layout.simple_list_item_1,data);//简单适配器，显示城市名列表/
        mList.setAdapter(mAdapter);//绑定
        mList.setTextFilterEnabled(true); // 开启过滤功能
        //listview点击事件
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //使用dialog 以防误触
                AlertDialog.Builder builder = new AlertDialog.Builder(SelectCity.this);
                builder.setTitle("提醒");
                String nowProv ="";
                String nowCit ="";
                for(City s:cityList ){//通过适配器获取的当前点击城市名，获取其相应的省、市、城市代码
                    if(s.getCity()==mAdapter.getItem(position)){
                        nowProv=s.getProvince();
                        nowCit=s.getCity();
                        mNCC=s.getNumber();
                    }
                }
                builder.setMessage("你确定要将城市切换到"+nowProv+" - "+nowCit+"吗?");//dialog提示信息
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        toast("取消");//直接消掉dialog
                    }
                });
                //点击弹出的dialog的确认键后发生的事件
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(SelectCity.this, "你点击的是" + mPos, Toast.LENGTH_SHORT).show();//activity会销毁，实际上用户看不到，用于判断当前标号，也可用log打印
                        Intent i = new Intent(SelectCity.this, MainActivity.class).putExtra("cityCode", mNCC);//将点击的城市代码传给主页面
                        setResult(10, i);
                        //一定要销毁当前Activity  不然你绝对跳不回去，
                        finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();//展示dialog

            }
        });

    }
}
