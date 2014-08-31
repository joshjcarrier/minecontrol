package com.joshjcarrier.rxautomation.methods;

import com.joshjcarrier.persistence.IStorage;

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
    public void save(IStorage storage, String rootName) {
        storage.write(rootName, "method", "mouse-mv-y");
    }
}
