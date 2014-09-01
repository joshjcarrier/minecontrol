package com.joshjcarrier.rxautomation.methods;

import com.joshjcarrier.rxautomation.persistence.IAutomationReader;
import com.joshjcarrier.rxautomation.persistence.IAutomationWriter;

import java.awt.*;
import java.awt.event.KeyEvent;

public class KeyboardAutomationMethod implements IAutomationMethod {
    private static Robot humanInterfaceDeviceService;
    private static final String METHOD_ID = "kbd-btn";
    private static final String PKEI_KEY = "pkei";
    private static final String SKEI_KEY = "skei";
    private final int primaryKeyEventId;
    private final int secondaryKeyEventId;

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

    public static IAutomationMethod load(IAutomationReader automationReader) {
        try{
            String methodId = automationReader.readMethod();
            if(!methodId.equalsIgnoreCase(METHOD_ID)) {
                return null;
            }

            Integer primaryKeyEventId = automationReader.readInt(PKEI_KEY);
            Integer secondaryKeyEventId = automationReader.readInt(SKEI_KEY);

            return new KeyboardAutomationMethod(primaryKeyEventId, secondaryKeyEventId);
        } catch (Exception e) {
            return null;
        }
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

    @Override
    public String getName() {
        return KeyEvent.getKeyText(this.primaryKeyEventId);
    }

    @Override
    public void write(IAutomationWriter automationWriter) {
        automationWriter.writeMethod(METHOD_ID);
        automationWriter.write(PKEI_KEY, this.primaryKeyEventId);
        automationWriter.write(SKEI_KEY, this.secondaryKeyEventId);
    }
}
