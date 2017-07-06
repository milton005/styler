package com.richie.styler.UI.Models.Tribes;

/**
 * Created by User on 16-05-2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Male implements Serializable{

    @SerializedName("tribe_id")
    @Expose
    private String tribeId;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("tribe_title")
    @Expose
    private String tribeTitle;
    @SerializedName("tribe_img")
    @Expose
    private String tribeImg;
    @SerializedName("tribe_status")
    @Expose
    private String tribeStatus;
    @SerializedName("tribe_thumb")
    @Expose
    private String tribeThumb;

    public String getTribeId() {
        return tribeId;
    }

    public void setTribeId(String tribeId) {
        this.tribeId = tribeId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTribeTitle() {
        return tribeTitle;
    }

    public void setTribeTitle(String tribeTitle) {
        this.tribeTitle = tribeTitle;
    }

    public String getTribeImg() {
        return tribeImg;
    }

    public void setTribeImg(String tribeImg) {
        this.tribeImg = tribeImg;
    }

    public String getTribeStatus() {
        return tribeStatus;
    }

    public void setTribeStatus(String tribeStatus) {
        this.tribeStatus = tribeStatus;
    }

    public String getTribeThumb() {
        return tribeThumb;
    }

    public void setTribeThumb(String tribeThumb) {
        this.tribeThumb = tribeThumb;
    }

}