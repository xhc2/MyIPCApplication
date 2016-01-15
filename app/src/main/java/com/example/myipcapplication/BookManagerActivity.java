package com.example.myipcapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class BookManagerActivity extends AppCompatActivity {

    private IBookManager bookManager;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(BookManagerActivity.this,"看看log",Toast.LENGTH_SHORT).show();
            bookManager = IBookManager.Stub.asInterface(service);
            try{
                List<Book> list = bookManager.getBookList();
                Log.e("xhc",list.toString());

                Book book = new Book(3,"好好学习");
                bookManager.addBook(book);
                Book book2 = new Book(4,"天天向上");
                bookManager.addBook(book2);
                list = bookManager.getBookList();

                Log.e("xhc",list.toString());
                bookManager.registerLintener(iOnNewBookArrivedListener);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    private IOnNewBookArrivedListener iOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub(){
        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            Log.e("xhc","book "+book);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_book_manager);
        Intent intent = new Intent(this,BookManagerService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            if(bookManager != null && bookManager.asBinder().isBinderAlive()){
                bookManager.unregisterListener(iOnNewBookArrivedListener);
            }
        }catch(Exception e){

        }
        unbindService(serviceConnection);
    }
}




















