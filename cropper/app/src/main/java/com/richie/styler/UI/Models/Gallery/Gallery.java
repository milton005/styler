package com.richie.styler.UI.Models.Gallery;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by User on 18-04-2017.
 */

public class Gallery implements Serializable {
    @SerializedName("image_id")
    @Expose
    private String imageId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
