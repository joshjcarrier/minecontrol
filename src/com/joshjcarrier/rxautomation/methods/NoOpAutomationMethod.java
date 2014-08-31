package com.joshjcarrier.rxautomation.methods;

import com.joshjcarrier.persistence.IStorage;

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
    public void save(IStorage configurationSettings, String rootName) {
        configurationSettings.write(rootName, "method", "noop");
    }
}
