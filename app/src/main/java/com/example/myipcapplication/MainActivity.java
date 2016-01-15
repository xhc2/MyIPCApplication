package com.example.myipcapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


/**
 * 这个demo主要写Android中的ipc就是进程间的通信方式
 * 1.文件共享数据（不确定写不）
 * 2.Messenger（会写）
 * 3.AIDL（会写）
 * 4.ContentProvider（不确定）
 * 5.Socket（不确定）
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btMessenger  ;
    Intent intent =  new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        btMessenger = (Button)findViewById(R.id.messenger);
        btMessenger.setOnClickListener(this);
        findViewById(R.id.aidl).setOnClickListener(this);
        findViewById(R.id.bundle).setOnClickListener(this);
        intent.setClass(MainActivity.this,BundleService.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.messenger:
                startActivity(new Intent(MainActivity.this,MessengerActivity.class));
                break;
            case R.id.aidl:
                startActivity(new Intent(MainActivity.this,BookManagerActivity.class));
                break;
            case R.id.bundle:
                Bundle bundle = new Bundle();
                bundle.putString("msg","bundle msg from client");
//                Intent intent =  new Intent(MainActivity.this,BundleService.class);
                intent.putExtras(bundle);
                startService(intent);
                break;
        }

    }
}
























