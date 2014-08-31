package com.joshjcarrier.rxautomation.methods;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MouseButtonAutomationMethod implements IAutomationMethod {
    private final int primaryMouseEventId;
    private final int secondaryMouseEventId;
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

    public MouseButtonAutomationMethod(int primaryMouseEventId) {
        this(primaryMouseEventId, primaryMouseEventId);
    }

    public MouseButtonAutomationMethod(int primaryMouseEventId, int secondaryMouseEventId) {
        this.primaryMouseEventId = primaryMouseEventId;
        this.secondaryMouseEventId = secondaryMouseEventId;
    }

    public void automate(Float value) {
        if(value == 1f) {
            humanInterfaceDeviceService.mousePress(this.primaryMouseEventId);
        }
        else if(value == -1f) {
            humanInterfaceDeviceService.mousePress(this.secondaryMouseEventId);
        }
        else {
            humanInterfaceDeviceService.mouseRelease(this.primaryMouseEventId);
            humanInterfaceDeviceService.mouseRelease(this.secondaryMouseEventId);
        }
    }

    @Override
    public String getName() {
        switch(this.primaryMouseEventId)
        {
            case MouseEvent.BUTTON1_MASK:
                return "Mouse left click (button 1)";
            case MouseEvent.BUTTON2_MASK:
                return "Mouse middle click (button 2)";
            case MouseEvent.BUTTON3_MASK:
                return "Mouse right click (button 3)";
            default:
                return "Mouse event unknown";
        }
    }
}
