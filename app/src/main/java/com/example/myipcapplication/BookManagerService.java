package com.example.myipcapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookManagerService extends Service {

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<Book>();
    private RemoteCallbackList<IOnNewBookArrivedListener> listenerLists = new RemoteCallbackList<IOnNewBookArrivedListener>();
    private Binder mBinder = new IBookManager.Stub(){

        public List<Book> getBookList() {
            return mBookList;
        }

        public void addBook(Book book){
            mBookList.add(book);
        }

       public void registerLintener(IOnNewBookArrivedListener lis){
           listenerLists.register(lis);
           final int count = listenerLists.beginBroadcast();
           Log.e("xhc","listenerLists.size "+count);
           listenerLists.finishBroadcast();
       }
       public void unregisterListener(IOnNewBookArrivedListener lis){
           listenerLists.unregister(lis);
           final int count = listenerLists.beginBroadcast();
           Log.e("xhc","listenerLists.size "+count);

           listenerLists.finishBroadcast();
       }
    };

    //新书到了， 添加一个观察者模式
    private void onNewBookArrived(Book book) throws RemoteException{
        mBookList.add(book);
        final int count = listenerLists.beginBroadcast();
        for(int i = 0 ; i < count ; ++i){
            IOnNewBookArrivedListener l = listenerLists.getBroadcastItem(i);
            if(l != null){
                try{
                    l.onNewBookArrived(book);
                }catch(Exception e){

                }
            }
        }
        listenerLists.finishBroadcast();
    }

    private class ServiceWorker implements Runnable{
        @Override
        public void run() {
            while(true){


                int bookId = mBookList.size() +1;
                Book book = new Book(bookId,"asdf book"+bookId);
                try {
                    Thread.sleep(2000);
                    onNewBookArrived(book);
                }catch (Exception e){

                }
            }


        }
    }
    public BookManagerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("xhc", "oncreate");
        mBookList.add(new Book(1, "android"));
        mBookList.add(new Book(2,"java"));
        new Thread(new ServiceWorker()).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("xhc","onBind");
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("xhc","service ondestroy");
    }
}
