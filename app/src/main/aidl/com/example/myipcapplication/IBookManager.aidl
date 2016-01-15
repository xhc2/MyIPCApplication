// IBookManager.aidl
package com.example.myipcapplication;
import com.example.myipcapplication.Book;
import com.example.myipcapplication.IOnNewBookArrivedListener;
// Declare any non-default types here with import statements

interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

            List<Book> getBookList();
            void addBook(in Book book);

            void registerLintener(IOnNewBookArrivedListener lis);
            void unregisterListener(IOnNewBookArrivedListener lis);

}