package com.joshjcarrier.minecontrol.ui.models;

import com.joshjcarrier.minecontrol.framework.profiles.IMouseProfile;

public class MouseProfileWrapper {
    private final IMouseProfile mouseProfile;

    public MouseProfileWrapper(IMouseProfile mouseProfile) {
        this.mouseProfile = mouseProfile;
    }

    public int getSensitivityX() {
        return this.mouseProfile.getSensitivityX();
    }

    public int getSensitivityY() {
        return this.mouseProfile.getSensitivityY();
    }

    public boolean isInvertY() {
        return this.mouseProfile.isInvertY();
    }

    public void setSensitivityX(int value) {
        this.mouseProfile.setSensitivityX(value);
    }

    public void setSensitivityY(int value) {
        this.mouseProfile.setSensitivityY(value);
    }

    public void setInvertY(boolean value) {
        this.mouseProfile.setInvertY(value);
    }
}
