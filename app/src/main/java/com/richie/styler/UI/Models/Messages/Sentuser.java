package com.richie.styler.UI.Models.Messages;

/**
 * Created by User on 17-05-2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sentuser implements Serializable {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("user_photo")
    @Expose
    private String userPhoto;
    @SerializedName("online_status")
    @Expose
    private String onlineStatus;
    @SerializedName("chathelp_id")
    @Expose
    private String chathelpId;
    @SerializedName("block_status")
    @Expose
    private Integer blockStatus;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getChathelpId() {
        return chathelpId;
    }

    public void setChathelpId(String chathelpId) {
        this.chathelpId = chathelpId;
    }

    public Integer getBlockStatus() {
        return blockStatus;
    }

    public void setBlockStatus(Integer blockStatus) {
        this.blockStatus = blockStatus;
    }
}
