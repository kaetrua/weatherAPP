package app;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import bean.City;
import db.CityDB;

public class MyApplication extends Application {
    private static final String TAG = "MyAPP";
    private static MyApplication mApplication;
    private CityDB mCityDB;
    private ArrayList<City> mCityList;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "myapplicationg->oncreate");

        mApplication = this;
        mCityDB = openCityDB();
        initCityList();
    }

    public static MyApplication getInstance() {
        return mApplication;
    }

    private CityDB openCityDB() {//打开城市数据库
        String path = "/data"
                + Environment.getDataDirectory().getAbsolutePath
                ()
                + File.separator + getPackageName()
                + File.separator + "databases1"
                + File.separator
                + CityDB.CITY_DB_NAME;//路径
        File db = new File(path);
        Log.d(TAG, db.toString());

        Log.d(TAG, path);
        if (!db.exists()) {//db文件不存在
            String pathfolder = "/data"
                    + Environment.getDataDirectory().getAbsolutePath()
                    + File.separator + getPackageName()
                    + File.separator + "databases1"
                    + File.separator;
            File dirFirstFolder = new File(pathfolder);
            if (!dirFirstFolder.exists()) {
                dirFirstFolder.mkdirs();//从上到下构建文件目录
                Log.i("MyApp", "mkdirs");
            }
            Log.i("MyApp", "db is not exists");
            try {
                InputStream is = getAssets().open("city.db");
                FileOutputStream fos = new FileOutputStream(db);
                int len = -1;
                byte[] buffer = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    fos.flush();
                }
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
        return new CityDB(this, path);
    }
    private void initCityList(){//初始化城市列表
        mCityList = new ArrayList<City>();
        new Thread(new Runnable() {
            @Override
            public void run() {
// TODO Auto-generated method stub
                prepareCityList();
            }
        }).start();
    }
    private boolean prepareCityList() {//db数据转化为可使用的列表
        mCityList = mCityDB.getAllCity();
        int i=0;
        for (City city : mCityList) {
            i++;
            String cityName = city.getCity();
            String cityCode = city.getNumber();
            Log.d(TAG,cityCode+":"+cityName);
        }
        Log.d(TAG,"i="+i);
        return true;
    }
    public ArrayList<City> getCityList() {
        return mCityList;
    }//供他人使用城市列表
}