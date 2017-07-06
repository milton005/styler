package com.richie.styler.UI.Models.StylerDetail;

/**
 * Created by User on 28-04-2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GalleryImage implements Serializable{

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