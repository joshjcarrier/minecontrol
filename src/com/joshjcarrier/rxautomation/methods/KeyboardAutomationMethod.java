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
    private static final String TKEI_KEY = "tkei";
    private static final String QKEI_KEY = "qkei";
    private final int primaryKeyEventId;
    private final int secondaryKeyEventId;
    private final int tertiaryKeyEventId;
    private final int quaternaryKeyEventId;

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
        this(primaryKeyEventId, secondaryKeyEventId, primaryKeyEventId, primaryKeyEventId);
    }

    public KeyboardAutomationMethod(int primaryKeyEventId, int secondaryKeyEventId, int tertiaryKeyEventId, int quaternaryKeyEventId) {
        this.primaryKeyEventId = primaryKeyEventId;
        this.secondaryKeyEventId = secondaryKeyEventId;
        this.tertiaryKeyEventId = tertiaryKeyEventId;
        this.quaternaryKeyEventId = quaternaryKeyEventId;
    }

    public static IAutomationMethod load(IAutomationReader automationReader) {
        try{
            String methodId = automationReader.readMethod();
            if(!methodId.equalsIgnoreCase(METHOD_ID)) {
                return null;
            }

            Integer primaryKeyEventId = automationReader.readInt(PKEI_KEY);
            Integer secondaryKeyEventId = automationReader.readInt(SKEI_KEY, primaryKeyEventId);
            Integer tertiaryKeyEventId = automationReader.readInt(TKEI_KEY, primaryKeyEventId);
            Integer quaternaryKeyEventId = automationReader.readInt(QKEI_KEY, primaryKeyEventId);

            return new KeyboardAutomationMethod(primaryKeyEventId, secondaryKeyEventId, tertiaryKeyEventId, quaternaryKeyEventId);
        } catch (Exception e) {
            return null;
        }
    }

    public void automate(Float value) {
        if(value == 1f) {
            humanInterfaceDeviceService.keyPress(this.primaryKeyEventId);
        }
        else if(value == -1f || value == 0.25f) {
            humanInterfaceDeviceService.keyPress(this.secondaryKeyEventId);
        }
        else if(value == 0.5f) {
            humanInterfaceDeviceService.keyPress(this.tertiaryKeyEventId);
        }
        else if(value == 0.75f) {
            humanInterfaceDeviceService.keyPress(this.quaternaryKeyEventId);
        }
        else {
            humanInterfaceDeviceService.keyRelease(this.primaryKeyEventId);

            if (this.secondaryKeyEventId != this.primaryKeyEventId) {
                humanInterfaceDeviceService.keyRelease(this.secondaryKeyEventId);
            }

            if (this.tertiaryKeyEventId != this.primaryKeyEventId) {
                humanInterfaceDeviceService.keyRelease(this.tertiaryKeyEventId);
            }

            if (this.quaternaryKeyEventId != this.primaryKeyEventId) {
                humanInterfaceDeviceService.keyRelease(this.quaternaryKeyEventId);
            }
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
        if(this.secondaryKeyEventId != this.primaryKeyEventId) {
            automationWriter.write(SKEI_KEY, this.secondaryKeyEventId);
        }
        if(this.tertiaryKeyEventId != this.primaryKeyEventId) {
            automationWriter.write(TKEI_KEY, this.tertiaryKeyEventId);
        }
        if(this.quaternaryKeyEventId != this.primaryKeyEventId) {
            automationWriter.write(QKEI_KEY, this.quaternaryKeyEventId);
        }
    }
}
