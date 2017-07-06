package com.richie.styler.UI.Models;

import java.io.Serializable;

/**
 * Created by User on 18-05-2017.
 */

public class GridhomemodelwithStatus implements Serializable {
    String imagename;
    String imagepath;
    String chathelpid;
    int online;
    int messagecount;
    public String getChathelpid() {
        return chathelpid;
    }

    public void setChathelpid(String chathelpid) {
        this.chathelpid = chathelpid;
    }

    public int getOnline() {
        return online;
    }

    public String getImagename() {
        return imagename;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public int getMessagecount() {
        return messagecount;
    }

    public void setMessagecount(int messagecount) {
        this.messagecount = messagecount;
    }
}
