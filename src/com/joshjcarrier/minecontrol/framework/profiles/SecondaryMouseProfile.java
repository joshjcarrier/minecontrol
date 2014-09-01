package com.joshjcarrier.minecontrol.framework.profiles;

import com.joshjcarrier.persistence.IStorage;
import com.joshjcarrier.rxautomation.methods.MouseMoveAutomationRunner;

public class SecondaryMouseProfile implements IMouseProfile {
    private final String SENSITIVITY_X_KEY = "mouse.secondary.x.sensitivity";
    private final String SENSITIVITY_Y_KEY = "mouse.secondary.y.sensitivity";
    private final String profileName;
    private final IStorage storage;

    public SecondaryMouseProfile(String profileName, IStorage storage) {
        this.profileName = profileName;
        this.storage = storage;

        restore();
    }

    public int getSensitivityX() {
        return MouseMoveAutomationRunner.SECONDARY_SENSITIVITY_X;
    }

    public int getSensitivityY() {
        return MouseMoveAutomationRunner.SECONDARY_SENSITIVITY_Y;
    }

    public boolean isInvertY() {
        return false;
    }

    public void setSensitivityX(int value) {
        MouseMoveAutomationRunner.SECONDARY_SENSITIVITY_X = value;
        write();
    }

    public void setSensitivityY(int value) {
        MouseMoveAutomationRunner.SECONDARY_SENSITIVITY_Y = value;
        write();
    }

    public void setInvertY(boolean value) {
    }

    private void restore() {
        MouseMoveAutomationRunner.PRIMARY_SENSITIVITY_X = this.storage.read(this.profileName, SENSITIVITY_X_KEY, MouseMoveAutomationRunner.PRIMARY_SENSITIVITY_X);
        MouseMoveAutomationRunner.PRIMARY_SENSITIVITY_X = this.storage.read(this.profileName, SENSITIVITY_Y_KEY, MouseMoveAutomationRunner.PRIMARY_SENSITIVITY_Y);
    }

    private void write() {
        this.storage.write(this.profileName, SENSITIVITY_X_KEY, getSensitivityX());
        this.storage.write(this.profileName, SENSITIVITY_Y_KEY, getSensitivityY());
        this.storage.commit();
    }
}
