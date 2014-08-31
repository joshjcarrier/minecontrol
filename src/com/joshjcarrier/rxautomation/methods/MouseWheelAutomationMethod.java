package com.joshjcarrier.rxautomation.methods;

import java.awt.*;

public class MouseWheelAutomationMethod implements IAutomationMethod {
    private static Robot humanInterfaceDeviceService;

    static {

        try
        {
            humanInterfaceDeviceService = new Robot();

            // this prevents the OS from ignoring events generated too quickly in succession
            humanInterfaceDeviceService.setAutoDelay(5);
        }
        catch (AWTException e)
        {
            // TODO 2.0+ throw exception
            e.printStackTrace();
        }
    }

    private final int scrollAmount;

    public MouseWheelAutomationMethod(int scrollAmount) {

        this.scrollAmount = scrollAmount;
    }

    public void automate(Float value) {
        if(value == 1f) {
            humanInterfaceDeviceService.mouseWheel(scrollAmount);
        }
        else if(value == -1f) {
            humanInterfaceDeviceService.mouseWheel(-scrollAmount);
        }
    }

    @Override
    public String getName() {
        return scrollAmount > 0 ? "Mouse scroll down" : "Mouse scroll up";
    }
}
