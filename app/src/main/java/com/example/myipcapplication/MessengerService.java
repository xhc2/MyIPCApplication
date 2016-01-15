package com.example.myipcapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.widget.Toast;

/**
 * 这个后台服务是运行在另个进程中
 */
public class MessengerService extends Service {
    public MessengerService() {
    }



    private Messenger MyMessager = new Messenger(new MessengerHandler(MessengerService.this));

    private static class MessengerHandler extends Handler {
        Context context;
        MessengerHandler(Context ctx){
            context = ctx;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    Toast.makeText(context,"MessengerService : "+msg.getData().get("msg") , Toast.LENGTH_SHORT).show();

                    Messenger client = msg.replyTo;
                    Message replyMessage = Message.obtain(null,2);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply","我是服务端,消息收到");
                    replyMessage.setData(bundle);
                    try{
                        client.send(replyMessage);
                    }catch(Exception e){

                    }
                    break;
                default:
                super.handleMessage(msg);
                    break;
            }

        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return MyMessager.getBinder();
    }
}
