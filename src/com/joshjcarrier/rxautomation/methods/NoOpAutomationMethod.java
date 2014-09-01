package com.joshjcarrier.rxautomation.methods;

import com.joshjcarrier.rxautomation.persistence.IAutomationReader;
import com.joshjcarrier.rxautomation.persistence.IAutomationWriter;

public class NoOpAutomationMethod implements IAutomationMethod {
    private static final String METHOD_ID = "noop";

    public static IAutomationMethod load(IAutomationReader automationReader) {
        String methodId = automationReader.readMethod();
        if(!methodId.equalsIgnoreCase(METHOD_ID)) {
            return null;
        }

        return new NoOpAutomationMethod();
    }

    @Override
    public void automate(Float value) {
        // No op by default
    }

    @Override
    public String getName() {
        return "<none>";
    }

    @Override
    public void write(IAutomationWriter configurationSettings) {
        configurationSettings.writeMethod(METHOD_ID);
    }
}
