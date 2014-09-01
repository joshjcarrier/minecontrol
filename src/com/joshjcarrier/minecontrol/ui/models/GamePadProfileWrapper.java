package com.joshjcarrier.minecontrol.ui.models;

import com.joshjcarrier.minecontrol.framework.input.AutomationBinding;
import com.joshjcarrier.minecontrol.framework.profiles.GamePadProfile;
import com.joshjcarrier.minecontrol.ui.ContentResources;
import com.joshjcarrier.rxautomation.methods.MouseMoveAutomationRunner;
import com.joshjcarrier.rxautomation.projection.BufferRxAutomationProjection;
import com.joshjcarrier.rxautomation.projection.ThresholdRxAutomationProjection;
import net.java.games.input.Component;
import net.java.games.input.Mouse;

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

    public AutomationBindingWrapper getAutomationBinding(Component.Identifier identifier) {
        return new AutomationBindingWrapper(new AutomationBinding(this.gamePadProfile.getAutomationMethod(identifier)));
    }

    @Deprecated // replace with info from projection strategy
    public boolean isBufferAutomationProjection(Component.Identifier identifier) {
        return this.gamePadProfile.getAutomationProjection(identifier) instanceof BufferRxAutomationProjection;
    }

    public void setAutomationBinding(Component.Identifier identifier, AutomationBindingWrapper automationBindingWrapper) {
        this.gamePadProfile.setAutomationMethod(identifier, automationBindingWrapper.getAutomationBinding().getAutomationMethod());
    }

    public void setThresholdAutomationProjection(Component.Identifier identifier) {
        this.gamePadProfile.setAutomationProjection(identifier, new ThresholdRxAutomationProjection());
    }

    public void setBufferAutomationProjection(Component.Identifier identifier) {
        this.gamePadProfile.setAutomationProjection(identifier, new BufferRxAutomationProjection());
    }

    public MouseProfileWrapper getPrimaryMouseProfile() {
        return new MouseProfileWrapper(this.gamePadProfile.getPrimaryMouseProfile());
    }

    public MouseProfileWrapper getSecondaryMouseProfile() {
        return new MouseProfileWrapper(this.gamePadProfile.getSecondaryMouseProfile());
    }

    @Override
    public String toString() {
        return getName();
    }
}
