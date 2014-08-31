package com.joshjcarrier.minecontrol.ui.controllers;

import com.joshjcarrier.minecontrol.ui.models.GamePadProfileWrapper;

public class GamePadProfileController {
    private final GamePadProfileWrapper gamePadProfile;

    public GamePadProfileController(GamePadProfileWrapper gamePadProfile) {
        this.gamePadProfile = gamePadProfile;
    }

    public String getTitle() {
        return this.gamePadProfile.getName();
    }
}
