package com.richie.styler.UI.Models;

/**
 * Created by User on 22-05-2017.
 */

public class ModelCheckin {
    String Name;
    String Distance;
    String checkedinNumber;
    String ImagePath;
    String lat;
    String lng;
    String country;
    String userphotos;

    public String getUserphotos() {
        return userphotos;
    }

    public void setUserphotos(String userphotos) {
        this.userphotos = userphotos;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLat() {
        return lat;
    }

    public String getCountry() {
        return country;
    }

    public String getLng() {
        return lng;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getCheckedinNumber() {
        return checkedinNumber;
    }

    public String getDistance() {
        return Distance;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public String getName() {
        return Name;
    }

    public void setCheckedinNumber(String checkedinNumber) {
        this.checkedinNumber = checkedinNumber;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public void setName(String name) {
        Name = name;
    }
}
