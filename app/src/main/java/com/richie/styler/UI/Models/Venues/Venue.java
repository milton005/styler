package com.richie.styler.UI.Models.Venues;

/**
 * Created by User on 24-05-2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Venue implements Serializable{

    @SerializedName("place")
    @Expose
    private String place;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("users")
    @Expose
    private Users users;

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

}