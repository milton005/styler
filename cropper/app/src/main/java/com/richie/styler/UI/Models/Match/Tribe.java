package com.richie.styler.UI.Models.Match;

/**
 * Created by User on 30-05-2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tribe {

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
