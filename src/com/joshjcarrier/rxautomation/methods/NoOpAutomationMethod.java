package com.joshjcarrier.rxautomation.methods;

import com.joshjcarrier.rxautomation.persistence.IAutomationWriter;

public class NoOpAutomationMethod implements IAutomationMethod {
    @Override
    public void automate(Float value) {
        // No op by default
    }

    @Override
    public String getName() {
        return "<none>";
    }

    @Override
    public void save(IAutomationWriter configurationSettings) {
        configurationSettings.write("method", "noop");
    }
}
