package com.richie.styler.UI.Models.Myfavorites;

/**
 * Created by User on 28-04-2017.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("favorites")
    @Expose
    private List<Favorite> favorites = null;

    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }

}
