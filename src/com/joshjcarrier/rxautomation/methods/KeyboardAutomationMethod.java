package com.joshjcarrier.rxautomation.methods;

import java.awt.*;

public class KeyboardAutomationMethod implements IAutomationMethod {
    private final int primaryKeyEventId;
    private final int secondaryKeyEventId;
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

    public KeyboardAutomationMethod(int primaryKeyEventId) {
        this(primaryKeyEventId, primaryKeyEventId);
    }

    public KeyboardAutomationMethod(int primaryKeyEventId, int secondaryKeyEventId) {
        this.primaryKeyEventId = primaryKeyEventId;
        this.secondaryKeyEventId = secondaryKeyEventId;

    }

    public void automate(Float value) {
        if(value == 1f) {
            humanInterfaceDeviceService.keyPress(this.primaryKeyEventId);
        }
        else if(value == -1f) {
            humanInterfaceDeviceService.keyPress(this.secondaryKeyEventId);
        }
        else {
            humanInterfaceDeviceService.keyRelease(this.primaryKeyEventId);
            humanInterfaceDeviceService.keyRelease(this.secondaryKeyEventId);
        }
    }
}
