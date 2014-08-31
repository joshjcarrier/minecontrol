package com.joshjcarrier.rxautomation.methods;

import com.joshjcarrier.rxautomation.persistence.IAutomationWriter;

public class MouseMoveXAutomationMethod implements IAutomationMethod {
    private final MouseMoveAutomationRunner mouseMoveAutomationRunner;

    public MouseMoveXAutomationMethod(MouseMoveAutomationRunner mouseMoveAutomationRunner) {
        this.mouseMoveAutomationRunner = mouseMoveAutomationRunner;
    }

    public void automate(Float value) {
        this.mouseMoveAutomationRunner.xValue = value;
    }

    @Override
    public String getName() {
        return "Mouse move X axis";
    }

    @Override
    public void save(IAutomationWriter automationWriter) {
        automationWriter.write("method", "mouse-mv-x");
    }
}
