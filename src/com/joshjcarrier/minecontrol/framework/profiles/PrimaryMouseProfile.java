package com.joshjcarrier.minecontrol.framework.profiles;

import com.joshjcarrier.persistence.IStorage;
import com.joshjcarrier.rxautomation.methods.MouseMoveAutomationRunner;

public class PrimaryMouseProfile implements IMouseProfile {
    private final String SENSITIVITY_X_KEY = "mouse.primary.x.sensitivity";
    private final String SENSITIVITY_Y_KEY = "mouse.primary.y.sensitivity";
    private final String INVERT_Y_KEY = "mouse.primary.y.invert";
    private final String profileName;
    private final IStorage storage;

    public PrimaryMouseProfile(String profileName, IStorage storage) {
        this.profileName = profileName;
        this.storage = storage;
        restore();
    }

    public int getSensitivityX() {
        return MouseMoveAutomationRunner.PRIMARY_SENSITIVITY_X;
    }

    public int getSensitivityY() {
        return MouseMoveAutomationRunner.PRIMARY_SENSITIVITY_Y;
    }

    public boolean isInvertY() {
        return MouseMoveAutomationRunner.INVERT_Y;
    }

    public void setSensitivityX(int value) {
        MouseMoveAutomationRunner.PRIMARY_SENSITIVITY_X = value;
        write();
    }

    public void setSensitivityY(int value) {
        MouseMoveAutomationRunner.PRIMARY_SENSITIVITY_Y = value;
        write();
    }

    public void setInvertY(boolean value) {
        MouseMoveAutomationRunner.INVERT_Y = value;
        write();
    }

    private void restore() {
        MouseMoveAutomationRunner.PRIMARY_SENSITIVITY_X = this.storage.read(this.profileName, SENSITIVITY_X_KEY, MouseMoveAutomationRunner.PRIMARY_SENSITIVITY_X);
        MouseMoveAutomationRunner.PRIMARY_SENSITIVITY_Y = this.storage.read(this.profileName, SENSITIVITY_Y_KEY, MouseMoveAutomationRunner.PRIMARY_SENSITIVITY_Y);
        MouseMoveAutomationRunner.INVERT_Y = this.storage.readBoolean(this.profileName, INVERT_Y_KEY);
    }

    private void write() {
        this.storage.write(this.profileName, SENSITIVITY_X_KEY, getSensitivityX());
        this.storage.write(this.profileName, SENSITIVITY_Y_KEY, getSensitivityY());
        this.storage.write(this.profileName, INVERT_Y_KEY, isInvertY());
        this.storage.commit();
    }
}
