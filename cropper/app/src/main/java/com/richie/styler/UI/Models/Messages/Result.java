package com.richie.styler.UI.Models.Messages;

/**
 * Created by User on 17-05-2017.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("receivedusers")
    @Expose
    private List<Receiveduser> receivedusers = null;
    @SerializedName("sentusers")
    @Expose
    private List<Sentuser> sentusers = null;

    public List<Receiveduser> getReceivedusers() {
        return receivedusers;
    }

    public void setReceivedusers(List<Receiveduser> receivedusers) {
        this.receivedusers = receivedusers;
    }

    public List<Sentuser> getSentusers() {
        return sentusers;
    }

    public void setSentusers(List<Sentuser> sentusers) {
        this.sentusers = sentusers;
    }

}