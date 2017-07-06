package com.richie.styler.UI.Models.Block;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by User on 18-04-2017.
 */

public class Result implements Serializable{
    @SerializedName("value")
    @Expose
    private Integer value;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
