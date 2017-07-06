package com.richie.styler.UI.Models;

/**
 * Created by User on 30-05-2017.
 */

public class Matchlistitem {
    String userid;
    String Imagepath;
    String username;
    String age;
    String onlinestatus;
    String distance;
    String brands;
    String tribes;
    String stylicons;
    String Otherinterests;
    String Iam;
    String ILIke;
    String nevergo;

    public String getIam() {
        return Iam;
    }

    public String getILIke() {
        return ILIke;
    }

    public String getNevergo() {
        return nevergo;
    }

    public String getOtherinterests() {
        return Otherinterests;
    }

    public void setIam(String iam) {
        Iam = iam;
    }

    public void setILIke(String ILIke) {
        this.ILIke = ILIke;
    }

    public void setNevergo(String nevergo) {
        this.nevergo = nevergo;
    }

    public void setOtherinterests(String otherinterests) {
        Otherinterests = otherinterests;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDistance() {
        return distance;
    }

    public String getAge() {
        return age;
    }

    public String getBrands() {
        return brands;
    }

    public String getImagepath() {
        return Imagepath;
    }

    public String getOnlinestatus() {
        return onlinestatus;
    }

    public String getTribes() {
        return tribes;
    }

    public String getUsername() {
        return username;
    }

    public String getStylicons() {
        return stylicons;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setBrands(String brands) {
        this.brands = brands;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setImagepath(String imagepath) {
        Imagepath = imagepath;
    }

    public void setOnlinestatus(String onlinestatus) {
        this.onlinestatus = onlinestatus;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setStylicons(String stylicons) {
        this.stylicons = stylicons;
    }

    public void setTribes(String tribes) {
        this.tribes = tribes;
    }
}
