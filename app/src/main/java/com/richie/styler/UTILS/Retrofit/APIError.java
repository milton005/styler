package com.richie.styler.UTILS.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class APIError {
    @SerializedName("result")
    @Expose
    private List<Result> result = new ArrayList<Result>();
    public APIError() {
    }

    /**
     *
     * @return
     * The result
     */
    public List<Result> getResult() {
        return result;
    }

    /**
     *
     * @param result
     * The result
     */
    public void setResult(List<Result> result) {
        this.result = result;}
    /*private int statusCode;
    private String message;

    public APIError() {
    }

    public int status() {
        return statusCode;
    }

    public String message() {
        return message;
    }*/

//    private int callbackCode;
//
//    @SerializedName("error")
//    @Expose
//    private Error error;
//
//    public int getCallbackCode() {
//        return callbackCode;
//    }
//
//    public void setCallbackCode(int callbackCode) {
//        this.callbackCode = callbackCode;
//    }
//
//    /**
//     *
//     * @return
//     * The error
//     */
//    public Error getError() {
//        return error;
//    }
//
//    /**
//     *
//     * @param error
//     * The error
//     */
//    public void setError(Error error) {
//        this.error = error;
//    }

}