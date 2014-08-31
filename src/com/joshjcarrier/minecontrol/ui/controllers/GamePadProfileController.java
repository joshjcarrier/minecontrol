package com.joshjcarrier.minecontrol.ui.controllers;

import com.joshjcarrier.minecontrol.framework.profiles.GamePadProfile;

public class GamePadProfileController {
    private final GamePadProfile gamePadProfile;

    public GamePadProfileController(GamePadProfile gamePadProfile) {
        this.gamePadProfile = gamePadProfile;
    }

    public String getTitle() {
        return "default";
    }
}
