package com.joshjcarrier.rxautomation.methods;

public class NoOpAutomationMethod implements IAutomationMethod {
    @Override
    public void automate(Float value) {
        // No op by default
    }

    @Override
    public String getName() {
        return "<none>";
    }
}
