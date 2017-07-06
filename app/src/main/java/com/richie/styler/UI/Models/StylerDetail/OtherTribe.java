package com.richie.styler.UI.Models.StylerDetail;

/**
 * Created by User on 28-04-2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OtherTribe implements Serializable {

    @SerializedName("tribe_id")
    @Expose
    private String tribeId;
    @SerializedName("tribe_title")
    @Expose
    private String tribeTitle;

    public String getTribeId() {
        return tribeId;
    }

    public void setTribeId(String tribeId) {
        this.tribeId = tribeId;
    }

    public String getTribeTitle() {
        return tribeTitle;
    }

    public void setTribeTitle(String tribeTitle) {
        this.tribeTitle = tribeTitle;
    }

}