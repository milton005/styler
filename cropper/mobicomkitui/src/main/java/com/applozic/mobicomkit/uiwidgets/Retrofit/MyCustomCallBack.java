package com.applozic.mobicomkit.uiwidgets.Retrofit;


import retrofit2.Callback;

/**
 * Created by tony on 11/3/16.
 */
public abstract class MyCustomCallBack<T> implements Callback<T> {

    private int id;

    public MyCustomCallBack(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
