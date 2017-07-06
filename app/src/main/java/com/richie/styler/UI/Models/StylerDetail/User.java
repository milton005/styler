package com.richie.styler.UI.Models.StylerDetail;

/**
 * Created by User on 28-04-2017.
 */

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Serializable {

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
    @SerializedName("user_styles_icons")
    @Expose
    private String userStylesIcons;
    @SerializedName("user_unliked")
    @Expose
    private String userUnliked;
    @SerializedName("online_status")
    @Expose
    private String onlineStatus;
    @SerializedName("other_interest")
    @Expose
    private String otherInterest;
    @SerializedName("visibility_status")
    @Expose
    private String visibilityStatus;
    @SerializedName("he_blocked_me_status")
    @Expose
    private Integer heBlockedMeStatus;
    @SerializedName("i_blocked_him_status")
    @Expose
    private Integer iBlockedHimStatus;
    @SerializedName("favorite_status")
    @Expose
    private Integer favoriteStatus;
    @SerializedName("like_status")
    @Expose
    private Integer likeStatus;
    @SerializedName("tribes")
    @Expose
    private List<Tribe> tribes = null;
    @SerializedName("other_tribes")
    @Expose
    private List<OtherTribe> otherTribes = null;
    @SerializedName("gallery_images")
    @Expose
    private List<GalleryImage> galleryImages = null;

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

    public String getUserStylesIcons() {
        return userStylesIcons;
    }

    public void setUserStylesIcons(String userStylesIcons) {
        this.userStylesIcons = userStylesIcons;
    }

    public String getUserUnliked() {
        return userUnliked;
    }

    public void setUserUnliked(String userUnliked) {
        this.userUnliked = userUnliked;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }
    public String getVisibilityStatus() {
        return visibilityStatus;
    }

    public void setVisibilityStatus(String visibilityStatus) {
        this.visibilityStatus = visibilityStatus;
    }

    public String getOtherInterest() {
        return otherInterest;
    }

    public void setOtherInterest(String otherInterest) {
        this.otherInterest = otherInterest;
    }
    public Integer getHeBlockedMeStatus() {
        return heBlockedMeStatus;
    }

    public void setHeBlockedMeStatus(Integer heBlockedMeStatus) {
        this.heBlockedMeStatus = heBlockedMeStatus;
    }

    public Integer getIBlockedHimStatus() {
        return iBlockedHimStatus;
    }

    public void setIBlockedHimStatus(Integer iBlockedHimStatus) {
        this.iBlockedHimStatus = iBlockedHimStatus;
    }

    public Integer getFavoriteStatus() {
        return favoriteStatus;
    }

    public void setFavoriteStatus(Integer favoriteStatus) {
        this.favoriteStatus = favoriteStatus;
    }

    public Integer getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(Integer likeStatus) {
        this.likeStatus = likeStatus;
    }

    public List<Tribe> getTribes() {
        return tribes;
    }

    public void setTribes(List<Tribe> tribes) {
        this.tribes = tribes;
    }

    public List<OtherTribe> getOtherTribes() {
        return otherTribes;
    }

    public void setOtherTribes(List<OtherTribe> otherTribes) {
        this.otherTribes = otherTribes;
    }

    public List<GalleryImage> getGalleryImages() {
        return galleryImages;
    }

    public void setGalleryImages(List<GalleryImage> galleryImages) {
        this.galleryImages = galleryImages;
    }

}