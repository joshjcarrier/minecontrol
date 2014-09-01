package com.joshjcarrier.rxautomation.methods;

import com.joshjcarrier.rxautomation.persistence.IAutomationReader;
import com.joshjcarrier.rxautomation.persistence.IAutomationWriter;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MouseButtonAutomationMethod implements IAutomationMethod {
    private static final String METHOD_ID = "mouse-btn";
    private static final String PMEI_KEY = "pmei";
    private static final String SMEI_KEY = "smei";
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

    public static IAutomationMethod load(IAutomationReader automationReader) {
        try {
            String methodId = automationReader.readMethod();
            if(!methodId.equalsIgnoreCase(METHOD_ID)) {
                return null;
            }

            Integer primaryMouseEventId = automationReader.readInt(PMEI_KEY);
            Integer secondaryMouseEventId = automationReader.readInt(PMEI_KEY);

            return new MouseButtonAutomationMethod(primaryMouseEventId, secondaryMouseEventId);
        } catch (Exception e) {
            return null;
        }
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

    @Override
    public void write(IAutomationWriter automationWriter) {
        automationWriter.writeMethod(METHOD_ID);
        automationWriter.write(PMEI_KEY, this.primaryMouseEventId);
        automationWriter.write(SMEI_KEY, this.secondaryMouseEventId);
    }
}
