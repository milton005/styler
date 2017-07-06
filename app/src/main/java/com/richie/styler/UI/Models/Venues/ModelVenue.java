package com.richie.styler.UI.Models.Venues;

/**
 * Created by User on 24-05-2017.
 */


import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelVenue implements Serializable{

    @SerializedName("venue")
    @Expose
    private List<Venue> venue = null;

    public List<Venue> getVenue() {
        return venue;
    }

    public void setVenue(List<Venue> venue) {
        this.venue = venue;
    }

}