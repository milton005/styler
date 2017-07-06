package com.applozic.mobicomkit.uiwidgets.Retrofit;

/**
 * Created by User on 22-05-2017.
 */

public class ModelCheckin {
    String Name;
    String Lat;
    String Lon;
    String ImagePath;


    public String getLat() {
        return Lat;
    }

    public String getLon() {
        return Lon;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public void setLon(String lon) {
        Lon = lon;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public String getName() {
        return Name;
    }





    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public void setName(String name) {
        Name = name;
    }
}
