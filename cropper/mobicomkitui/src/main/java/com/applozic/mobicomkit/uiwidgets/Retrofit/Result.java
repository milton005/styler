package com.applozic.mobicomkit.uiwidgets.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by User on 07-10-2016.
 */

public class Result implements Serializable {
    @SerializedName("action")
    @Expose
    private Integer action;
    @SerializedName("user_status")
    @Expose
    private Integer userStatus;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     *
     * @return
     * The action
     */
    public Integer getAction() {
        return action;
    }

    /**
     *
     * @param action
     * The action
     */
    public void setAction(Integer action) {
        this.action = action;
    }

    /**
     *
     * @return
     * The userStatus
     */
    public Integer getUserStatus() {
        return userStatus;
    }

    /**
     *
     * @param userStatus
     * The user_status
     */
    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
