package com.richie.styler.UI.Models.ModelForgotpassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by User on 31-03-2017.
 */

public class Result implements Serializable{
    @SerializedName("user_status")
    @Expose
    private Integer userStatus;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
