package com.example.myipcapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class BundleService extends Service {
    public BundleService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("xhc", "onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }

    //在onstartCommand中获取从activity中传递过来的intent数据
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("xhc","onStartCommand");
        Bundle bundle = intent.getExtras();
        String str = bundle.getString("msg");
        Toast.makeText(BundleService.this,str,Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }
}
