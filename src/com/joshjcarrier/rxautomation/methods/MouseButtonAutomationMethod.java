package com.joshjcarrier.rxautomation.methods;

import com.joshjcarrier.minecontrol.framework.modes.MinecraftGameMode;
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
            humanInterfaceDeviceService.setAutoDelay(10);
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
            Integer secondaryMouseEventId = automationReader.readInt(SMEI_KEY);

            return new MouseButtonAutomationMethod(primaryMouseEventId, secondaryMouseEventId);
        } catch (Exception e) {
            return null;
        }
    }

    private boolean isPrimaryMousePressed;
    private boolean isSecondaryMousePressed;

    // TODO optimization in the mouse robot
    public void automate(Float value) {
        if(value == 1f) {
            if (this.isSecondaryMousePressed) {
                humanInterfaceDeviceService.mouseRelease(this.secondaryMouseEventId);
                this.isSecondaryMousePressed = false;
            }

            humanInterfaceDeviceService.mousePress(this.primaryMouseEventId);
            this.isPrimaryMousePressed = true;
        }
        else if(value == -1f) {
            if (this.isPrimaryMousePressed) {
                humanInterfaceDeviceService.mouseRelease(this.primaryMouseEventId);
                this.isPrimaryMousePressed = false;
            }

            humanInterfaceDeviceService.mousePress(this.secondaryMouseEventId);
            this.isSecondaryMousePressed = true;
        }
        else {
            if (this.isPrimaryMousePressed) {
                humanInterfaceDeviceService.mouseRelease(this.primaryMouseEventId);
                this.isPrimaryMousePressed = false;
            }

            if (this.isSecondaryMousePressed) {
                humanInterfaceDeviceService.mouseRelease(this.secondaryMouseEventId);
                this.isSecondaryMousePressed = false;
            }
        }
    }

    @Override
    public String getName() {
        String name;
        switch(this.primaryMouseEventId)
        {
            case MouseEvent.BUTTON1_MASK:
                name = "Mouse left click (button 1)";
                break;
            case MouseEvent.BUTTON2_MASK:
                name = "Mouse middle click (button 2)";
                break;
            case MouseEvent.BUTTON3_MASK:
                name = "Mouse right click (button 3)";
                break;
            default:
                name = "Mouse event unknown";
                break;
        }

        String helperText = MinecraftGameMode.getMouseButtonText(this.primaryMouseEventId);
        if (helperText != "") {
            helperText = " [" + helperText +"]";
        }

        return name + helperText;
    }

    @Override
    public void write(IAutomationWriter automationWriter) {
        automationWriter.writeMethod(METHOD_ID);
        automationWriter.write(PMEI_KEY, this.primaryMouseEventId);
        automationWriter.write(SMEI_KEY, this.secondaryMouseEventId);
    }
}
