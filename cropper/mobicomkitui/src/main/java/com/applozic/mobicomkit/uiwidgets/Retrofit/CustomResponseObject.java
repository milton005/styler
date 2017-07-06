package com.applozic.mobicomkit.uiwidgets.Retrofit;

/**
 * Created by tony on 11/3/16.
 */
public class CustomResponseObject<T> {

    private T rsponse;
    private int callBackCode;
    private int retrofitCode;
    private int resultCode;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public int getRetrofitCode() {
        return retrofitCode;
    }

    public void setRetrofitCode(int retrofitCode) {
        this.retrofitCode = retrofitCode;
    }

    public T getRsponse() {
        return rsponse;
    }

    public void setRsponse(T rsponse) {
        this.rsponse = rsponse;
    }

    public int getCallBackCode() {
        return callBackCode;
    }

    public void setCallBackCode(int callBackCode) {
        this.callBackCode = callBackCode;
    }
}
