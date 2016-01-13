package cn.go.lyqdh.carmap;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

import cn.bmob.v3.Bmob;

/**
 * Created by lyqdh on 2016/1/11.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 百度地图初始化
        SDKInitializer.initialize(this);
        // Bmob初始化
        Bmob.initialize(this,"4ef0568971c3a933e8d6c6c1037c4216");
    }
}
