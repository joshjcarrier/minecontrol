package com.joshjcarrier.rxautomation.methods;

import java.awt.*;

public class MouseMoveYAutomationMethod implements IAutomationMethod {
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

    public void automate(Float value) {

        if (value != 0)
        {
            PointerInfo info = MouseInfo.getPointerInfo();

            // TODO 2.1+ smoothen mouse
            humanInterfaceDeviceService.mouseMove(
                    (int) (info.getLocation().x),
                    (int) (info.getLocation().y  + value * 10));
        }
    }
}
