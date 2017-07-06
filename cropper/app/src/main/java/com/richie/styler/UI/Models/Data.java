package com.richie.styler.UI.Models;

/**
 * Created by User on 25-05-2017.
 */

public class Data {

    private String description;

    private String imagePath;


    public Data(String imagePath, String description) {
        this.imagePath = imagePath;
        this.description = description;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

}
