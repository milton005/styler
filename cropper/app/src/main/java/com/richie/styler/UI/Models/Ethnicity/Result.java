package com.richie.styler.UI.Models.Ethnicity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by User on 07-03-2017.
 */

public class Result implements Serializable {
    @SerializedName("ethnicity_id")
    @Expose
    private String ethnicityId;
    @SerializedName("ethnicity_title")
    @Expose
    private String ethnicityTitle;
    @SerializedName("ethnicity_status")
    @Expose
    private String ethnicityStatus;

    public String getEthnicityId() {
        return ethnicityId;
    }

    public void setEthnicityId(String ethnicityId) {
        this.ethnicityId = ethnicityId;
    }

    public String getEthnicityTitle() {
        return ethnicityTitle;
    }

    public void setEthnicityTitle(String ethnicityTitle) {
        this.ethnicityTitle = ethnicityTitle;
    }

    public String getEthnicityStatus() {
        return ethnicityStatus;
    }

    public void setEthnicityStatus(String ethnicityStatus) {
        this.ethnicityStatus = ethnicityStatus;
    }
}
