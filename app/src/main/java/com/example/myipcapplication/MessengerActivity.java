package com.example.myipcapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



/**
 * Messager 是在不同进程中传递Message对象
 * 底层是用aidl实现的。
 * messenger和aidl的区别是messager是串行的，aidl是并发的。
 */
public class MessengerActivity extends AppCompatActivity {

    Button start;

    private Messenger mService ;
    private Messenger mGetReplyMessenger = new Messenger(new MyHandler(this));

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            Message msg = Message.obtain(null,1);
            Bundle bundle = new Bundle() ;
            bundle.putString("msg","client send to service");
            msg.setData(bundle);
            // ****************************************
            msg.replyTo = mGetReplyMessenger;
            try{
                mService.send(msg);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };



    private static class MyHandler extends Handler {
        Context context;
        MyHandler(Context ctx){
            context = ctx;
        };
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 2:
                    Toast.makeText(context , " client :"+msg.getData().get("reply"),Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_messenger);

        start = (Button)findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MessengerActivity.this,MessengerService.class);
                bindService(intent,mConnection,BIND_AUTO_CREATE);

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }


}
