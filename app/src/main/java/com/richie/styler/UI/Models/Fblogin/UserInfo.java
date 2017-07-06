package com.richie.styler.UI.Models.Fblogin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by User on 11-04-2017.
 */

public class UserInfo implements Serializable{
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("fb_id")
    @Expose
    private String fbId;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("user_password")
    @Expose
    private String userPassword;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("ethnicity_id")
    @Expose
    private String ethnicityId;
    @SerializedName("other_ethinicity")
    @Expose
    private String otherEthinicity;
    @SerializedName("looking_for")
    @Expose
    private String lookingFor;
    @SerializedName("user_photo")
    @Expose
    private String userPhoto;
    @SerializedName("male_preference")
    @Expose
    private String malePreference;
    @SerializedName("female_preference")
    @Expose
    private String femalePreference;
    @SerializedName("registered_date")
    @Expose
    private String registeredDate;
    @SerializedName("user_status")
    @Expose
    private String userStatus;
    @SerializedName("userbrands")
    @Expose
    private String userbrands;
    @SerializedName("user_unliked")
    @Expose
    private String userUnliked;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getEthnicityId() {
        return ethnicityId;
    }

    public void setEthnicityId(String ethnicityId) {
        this.ethnicityId = ethnicityId;
    }

    public String getOtherEthinicity() {
        return otherEthinicity;
    }

    public void setOtherEthinicity(String otherEthinicity) {
        this.otherEthinicity = otherEthinicity;
    }

    public String getLookingFor() {
        return lookingFor;
    }

    public void setLookingFor(String lookingFor) {
        this.lookingFor = lookingFor;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getMalePreference() {
        return malePreference;
    }

    public void setMalePreference(String malePreference) {
        this.malePreference = malePreference;
    }

    public String getFemalePreference() {
        return femalePreference;
    }

    public void setFemalePreference(String femalePreference) {
        this.femalePreference = femalePreference;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserbrands() {
        return userbrands;
    }

    public void setUserbrands(String userbrands) {
        this.userbrands = userbrands;
    }

    public String getUserUnliked() {
        return userUnliked;
    }

    public void setUserUnliked(String userUnliked) {
        this.userUnliked = userUnliked;
    }

}
