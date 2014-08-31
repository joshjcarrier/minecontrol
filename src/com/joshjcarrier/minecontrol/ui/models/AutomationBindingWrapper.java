package com.joshjcarrier.minecontrol.ui.models;

import com.joshjcarrier.minecontrol.framework.input.AutomationBinding;

public class AutomationBindingWrapper {
    private final AutomationBinding automationBinding;

    public AutomationBindingWrapper(AutomationBinding automationBinding) {
        this.automationBinding = automationBinding;
    }

    public String getName() {
        return this.automationBinding.getName();
    }

    @Override
    public String toString() {
        return "  " + this.getName();
    }
}
