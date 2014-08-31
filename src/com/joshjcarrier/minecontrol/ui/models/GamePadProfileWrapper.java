package com.joshjcarrier.minecontrol.ui.models;

import com.joshjcarrier.minecontrol.framework.profiles.GamePadProfile;
import com.joshjcarrier.minecontrol.ui.ContentResources;
import net.java.games.input.Component;

import java.util.HashMap;

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

    public HashMap<Component.Identifier, String> getGamePadButtonLabels() {
        return this.gamePadProfile.getGamePadButtonLabels();
    }

    public HashMap<Component.Identifier, String> getGamePadButtonIcons() {
        return new HashMap<Component.Identifier, String>() {
            private static final long serialVersionUID = 3258388604108766926L;

            {
                put(Component.Identifier.Button._0, ContentResources.BUTTON_XBOX360_A);
                put(Component.Identifier.Button._1, ContentResources.BUTTON_XBOX360_B);
                put(Component.Identifier.Button._2, ContentResources.BUTTON_XBOX360_X);
                put(Component.Identifier.Button._3, ContentResources.BUTTON_XBOX360_Y);
                put(Component.Identifier.Button._4, ContentResources.BUTTON_XBOX360_LEFTSHOULDER);
                put(Component.Identifier.Button._5, ContentResources.BUTTON_XBOX360_RIGHTSHOULDER);
                put(Component.Identifier.Button._6, ContentResources.BUTTON_XBOX360_BACK);
                put(Component.Identifier.Button._7, ContentResources.BUTTON_XBOX360_START);
                put(Component.Identifier.Button._8, ContentResources.BUTTON_XBOX360_LEFTSTICK);
                put(Component.Identifier.Button._9, ContentResources.BUTTON_XBOX360_RIGHTSTICK);
            }
        };
    }

    public String getName() {
        return this.gamePadProfile.getName();
    }

    @Override
    public String toString() {
        return getName();
    }
}