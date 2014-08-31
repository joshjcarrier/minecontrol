package com.joshjcarrier.minecontrol.ui.models;

import com.joshjcarrier.minecontrol.framework.input.AutomationBinding;

public class AutomationBindingWrapper {
    private final AutomationBinding automationBinding;
    private String name; // caches because the equals check is real slow

    public AutomationBindingWrapper(AutomationBinding automationBinding) {
        this.automationBinding = automationBinding;
    }

    public AutomationBinding getAutomationBinding() {
        return this.automationBinding;
    }

    public String getName() {
        if(this.name == null) {
            this.name = this.automationBinding.getName();
        }
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AutomationBindingWrapper)) {
            return false;
        }

        AutomationBindingWrapper otherAutomationBinding = (AutomationBindingWrapper)o;
        return getName().equals(otherAutomationBinding.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public String toString() {
        return "  " + this.getName();
    }
}
