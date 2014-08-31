package com.joshjcarrier.minecontrol.framework.input;

import com.joshjcarrier.rxautomation.methods.IAutomationMethod;

public class AutomationBinding {
    private final IAutomationMethod automationMethod;

    public AutomationBinding(IAutomationMethod automationMethod) {
        this.automationMethod = automationMethod;
    }

    public IAutomationMethod getAutomationMethod() {
        return this.automationMethod;
    }

    public String getName() {
        return this.automationMethod.getName();
    }
}
