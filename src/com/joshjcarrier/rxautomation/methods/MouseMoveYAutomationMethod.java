package com.joshjcarrier.rxautomation.methods;

import com.joshjcarrier.rxautomation.persistence.IAutomationWriter;

public class MouseMoveYAutomationMethod implements IAutomationMethod {
    private final MouseMoveAutomationRunner mouseMoveAutomationRunner;

    public MouseMoveYAutomationMethod(MouseMoveAutomationRunner mouseMoveAutomationRunner) {
        this.mouseMoveAutomationRunner = mouseMoveAutomationRunner;
    }

    public void automate(Float value) {
        this.mouseMoveAutomationRunner.yValue = value;
    }

    @Override
    public String getName() {
        return "Mouse move Y axis";
    }

    @Override
    public void save(IAutomationWriter automationWriter) {
        automationWriter.write("method", "mouse-mv-y");
    }
}
