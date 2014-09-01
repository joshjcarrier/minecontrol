package com.joshjcarrier.minecontrol.ui.controllers;

import com.joshjcarrier.minecontrol.framework.input.AutomationBinding;
import com.joshjcarrier.minecontrol.ui.models.AutomationBindingWrapper;
import com.joshjcarrier.minecontrol.ui.models.GamePadProfileWrapper;
import com.joshjcarrier.rxautomation.methods.*;

import java.awt.event.KeyEvent;
import java.util.*;

public class GamePadProfileController {
    private final GamePadProfileWrapper gamePadProfile;

    public GamePadProfileController(GamePadProfileWrapper gamePadProfile) {
        this.gamePadProfile = gamePadProfile;
    }

    public List<AutomationBindingWrapper> getAutomationBindings() {
        List<AutomationBinding> automationBindings = new ArrayList<AutomationBinding>(){
            {
                add(new AutomationBinding(new NoOpAutomationMethod()));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_A)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_B)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_C)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_COMMA)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_CONTROL)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_D)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_DELETE)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_DOWN)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_E)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_END)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_ENTER)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_ESCAPE)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_F)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_F1)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_F2)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_F3)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_F4)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_F5)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_F6)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_F7)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_F8)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_F9)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_F10)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_F11)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_F12)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_G)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_H)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_HOME)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_I)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_INSERT)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_J)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_K)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_L)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_LEFT)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_M)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_N)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_O)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_P)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_PAGE_DOWN)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_PAGE_UP)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_PERIOD)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_Q)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_R)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_RIGHT)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_S)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_SHIFT)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_SPACE)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_T)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_TAB)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_U)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_UP)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_V)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_W)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_X)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_Y)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_Z)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_1)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_2)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_3)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_4)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_5)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_6)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_7)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_8)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_9)));
                add(new AutomationBinding(new KeyboardAutomationMethod(KeyEvent.VK_0)));
                add(new AutomationBinding(new MouseButtonAutomationMethod(KeyEvent.BUTTON1_MASK)));
                add(new AutomationBinding(new MouseButtonAutomationMethod(KeyEvent.BUTTON2_MASK)));
                add(new AutomationBinding(new MouseButtonAutomationMethod(KeyEvent.BUTTON3_MASK)));
                add(new AutomationBinding(new MouseWheelAutomationMethod(-1)));
                add(new AutomationBinding(new MouseWheelAutomationMethod(1)));
                add(new AutomationBinding(new SensitivityAppAutomationMethod()));
            }
        };

        List<AutomationBindingWrapper> automationBindingWrappers = new ArrayList<AutomationBindingWrapper>();
        for(AutomationBinding automationBinding : automationBindings) {
            automationBindingWrappers.add(new AutomationBindingWrapper(automationBinding));
        }

        return automationBindingWrappers;
    }

    public GamePadProfileWrapper getGamePadProfile() {
        return this.gamePadProfile;
    }

    public String getTitle() {
        return "Profile: " + this.gamePadProfile.getName();
    }
}
