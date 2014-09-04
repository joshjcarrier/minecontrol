package com.joshjcarrier.rxautomation.methods;

import com.joshjcarrier.minecontrol.framework.modes.MinecraftGameMode;
import com.joshjcarrier.rxautomation.persistence.IAutomationReader;
import com.joshjcarrier.rxautomation.persistence.IAutomationWriter;

import java.awt.*;
import java.awt.event.KeyEvent;

public class KeyboardAutomationMethod implements IAutomationMethod {
    private static Robot humanInterfaceDeviceService;
    private static final String METHOD_ID = "kbd-btn";
    private static final String PKEI_KEY = "pkei";
    private final int primaryKeyEventId;

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
        this.primaryKeyEventId = primaryKeyEventId;
    }

    public static IAutomationMethod load(IAutomationReader automationReader) {
        try{
            String methodId = automationReader.readMethod();
            if(!methodId.equalsIgnoreCase(METHOD_ID)) {
                return null;
            }

            Integer primaryKeyEventId = automationReader.readInt(PKEI_KEY);

            return new KeyboardAutomationMethod(primaryKeyEventId);
        } catch (Exception e) {
            return null;
        }
    }

    boolean isPrimaryKeyPressed;

    // TODO come and clean this up. have abstraction on Robot to do performance optimization
    public void automate(Float value) {
        if (value != 0) {
            if (!this.isPrimaryKeyPressed) {
                this.isPrimaryKeyPressed = true;
                humanInterfaceDeviceService.keyPress(this.primaryKeyEventId);
            }
        } else {
            if (this.isPrimaryKeyPressed) {
                humanInterfaceDeviceService.keyRelease(this.primaryKeyEventId);
                this.isPrimaryKeyPressed = false;
            }
        }
    }

    @Override
    public String getName() {
        String helperText = MinecraftGameMode.getKeyText(this.primaryKeyEventId);
        if (helperText != "") {
            helperText = " [" + helperText +"]";
        }
        return KeyEvent.getKeyText(this.primaryKeyEventId)  + helperText;
    }

    @Override
    public void write(IAutomationWriter automationWriter) {
        automationWriter.writeMethod(METHOD_ID);
        automationWriter.write(PKEI_KEY, this.primaryKeyEventId);
    }
}
