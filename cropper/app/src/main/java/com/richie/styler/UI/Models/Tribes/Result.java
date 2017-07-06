package com.richie.styler.UI.Models.Tribes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 08-03-2017.
 */

public class Result implements Serializable{

    @SerializedName("male")
    @Expose
    private List<Male> male = null;
    @SerializedName("female")
    @Expose
    private List<Female> female = null;

    public List<Male> getMale() {
        return male;
    }

    public void setMale(List<Male> male) {
        this.male = male;
    }

    public List<Female> getFemale() {
        return female;
    }

    public void setFemale(List<Female> female) {
        this.female = female;
    }
}
