package com.richie.styler.UI.Models.Likes;

/**
 * Created by User on 28-06-2017.
 */

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result implements Serializable{

    @SerializedName("likes")
    @Expose
    private List<Like> likes = null;

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

}