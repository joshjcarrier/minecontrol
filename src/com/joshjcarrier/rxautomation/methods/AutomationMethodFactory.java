package com.joshjcarrier.rxautomation.methods;

import com.joshjcarrier.rxautomation.persistence.IAutomationReader;

public class AutomationMethodFactory {
    public static IAutomationMethod load(IAutomationReader automationReader) {
        // chain of command
        IAutomationMethod automationMethod = KeyboardAutomationMethod.load(automationReader);
        if(automationMethod == null) {
            automationMethod = DiscreteDelegateAutomationMethod.load(automationReader);
        }

        if(automationMethod == null) {
            automationMethod = MouseButtonAutomationMethod.load(automationReader);
        }

        if(automationMethod == null) {
            automationMethod = MouseWheelAutomationMethod.load(automationReader);
        }

        if(automationMethod == null) {
            automationMethod = SensitivityAppAutomationMethod.load(automationReader);
        }

        if(automationMethod == null) {
            automationMethod = NoOpAutomationMethod.load(automationReader);
        }

        return automationMethod;
    }
}
