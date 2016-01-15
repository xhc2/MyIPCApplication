// IOnNewBookArrivedListener.aidl
package com.example.myipcapplication;
import com.example.myipcapplication.Book;
// Declare any non-default types here with import statements

interface IOnNewBookArrivedListener {

    void onNewBookArrived(in Book book);
}
