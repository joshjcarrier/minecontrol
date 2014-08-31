package com.joshjcarrier.rxautomation.methods;

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
}
