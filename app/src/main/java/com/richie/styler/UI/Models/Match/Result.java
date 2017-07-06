package com.richie.styler.UI.Models.Match;

/**
 * Created by User on 30-05-2017.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

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