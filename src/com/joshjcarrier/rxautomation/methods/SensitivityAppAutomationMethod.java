package com.joshjcarrier.rxautomation.methods;

import com.joshjcarrier.rxautomation.persistence.IAutomationReader;
import com.joshjcarrier.rxautomation.persistence.IAutomationWriter;

public class SensitivityAppAutomationMethod implements IAutomationMethod {
    private final static String METHOD_ID = "app-sensitivity";

    public static IAutomationMethod load(IAutomationReader automationReader) {
        try {
            String methodId = automationReader.readMethod();
            if(!methodId.equalsIgnoreCase(METHOD_ID)) {
                return null;
            }

            return new SensitivityAppAutomationMethod();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void automate(Float value) {
        if (value == 1f) {
            MouseMoveAutomationRunner.PRIMARY_SENSITIVITY_MODE = !MouseMoveAutomationRunner.PRIMARY_SENSITIVITY_MODE;
        }
    }

    @Override
    public String getName() {
        return "Toggle sensitivity (Minecontrol)";
    }

    @Override
    public void write(IAutomationWriter automationWriter) {
        automationWriter.writeMethod(METHOD_ID);
    }
}
