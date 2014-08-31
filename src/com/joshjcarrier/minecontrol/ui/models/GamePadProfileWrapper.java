package com.joshjcarrier.minecontrol.ui.models;

import com.joshjcarrier.minecontrol.framework.profiles.GamePadProfile;

public class GamePadProfileWrapper {
    private final GamePadProfile gamePadProfile;

    public GamePadProfileWrapper(GamePadProfile gamePadProfile) {
        this.gamePadProfile = gamePadProfile;
    }

    public void activate() {
        this.gamePadProfile.activate();
    }

    public void deactivate() {
        this.gamePadProfile.deactivate();
    }

    public String getName() {
        return this.gamePadProfile.getName();
    }

    @Override
    public String toString() {
        return getName();
    }
}
