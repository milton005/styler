package com.richie.styler.UI.Models.Gallery;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 18-04-2017.
 */

public class ModelGallery implements Serializable {
    @SerializedName("gallery")
    @Expose
    private List<Gallery> gallery = null;

    public List<Gallery> getGallery() {
        return gallery;
    }

    public void setGallery(List<Gallery> gallery) {
        this.gallery = gallery;
    }

}
