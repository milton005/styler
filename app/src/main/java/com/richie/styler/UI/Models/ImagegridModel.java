package com.richie.styler.UI.Models;

import java.io.Serializable;

/**
 * Created by User on 16-03-2017.
 */

public class ImagegridModel implements Serializable {
    String tribeName;
    String userid;
    String tribeImage;
    boolean Selected;

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public String getTribeImage() {
        return tribeImage;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }

    public boolean isSelected() {
        return Selected;
    }

    public void setTribeImage(String tribeImage) {
        this.tribeImage = tribeImage;
    }

    public void setTribeName(String tribeName) {
        this.tribeName = tribeName;
    }

    public String getTribeName() {
        return tribeName;
    }
}
