package com.richie.styler.UI.Models;

/**
 * Created by User on 19-05-2017.
 */

public class FoursquareVenue {
    private String name;
    private String distance;
    private String prefix;
    private String postfix;

    private String category;

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistance() {
        return distance;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getPostfix() {
        return postfix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
