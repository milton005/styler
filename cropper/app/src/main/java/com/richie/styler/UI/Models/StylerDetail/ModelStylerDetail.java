package com.richie.styler.UI.Models.StylerDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 28-04-2017.
 */

public class ModelStylerDetail implements Serializable{
    @SerializedName("user")
    @Expose
    private List<User> user = null;

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
}
