package com.richie.styler.UI.Models.StylerSearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 05-04-2017.
 */

public class Result implements Serializable {
    @SerializedName("stylers")
    @Expose
    private List<Styler> stylers = null;

    public List<Styler> getStylers() {
        return stylers;
    }

    public void setStylers(List<Styler> stylers) {
        this.stylers = stylers;
    }
}
