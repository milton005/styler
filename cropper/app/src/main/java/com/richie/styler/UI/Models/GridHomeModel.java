package com.richie.styler.UI.Models;

import java.io.Serializable;

/**
 * Created by User on 04-04-2017.
 */

public class GridHomeModel implements Serializable {
    String imagename;
    String imagepath;
    int online;

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
}
