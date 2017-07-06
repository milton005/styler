package com.richie.styler.UI.Models;

import java.io.Serializable;

/**
 * Created by user on 8/11/2016.
 */
public class SupportImages implements Serializable {
    private String images;
    private  String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getImages() {
        return images;
    }
}
