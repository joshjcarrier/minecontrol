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

    boolean isPrimaryKeyPressed;
    boolean isSecondaryKeyPressed;
    boolean isTertiaryKeyPressed;
    boolean isQuaternaryKeyPressed;

    // TODO come and clean this up. have abstraction on Robot to do performance optimization
    public void automate(Float value) {
        if (value == 1f) {
            if (this.isSecondaryKeyPressed) {
                humanInterfaceDeviceService.keyRelease(this.secondaryKeyEventId);
                this.isSecondaryKeyPressed = false;
            }

            if (this.isTertiaryKeyPressed) {
                humanInterfaceDeviceService.keyRelease(this.tertiaryKeyEventId);
                this.isTertiaryKeyPressed = false;
            }

            if (this.isQuaternaryKeyPressed) {
                humanInterfaceDeviceService.keyRelease(this.quaternaryKeyEventId);
                this.isQuaternaryKeyPressed = false;
            }

            if (!this.isPrimaryKeyPressed) {
                this.isPrimaryKeyPressed = true;
                humanInterfaceDeviceService.keyPress(this.primaryKeyEventId);
            }
        } else if (value == -1f || (this.secondaryKeyEventId != this.primaryKeyEventId && value == 0.25f)) {
            if (this.isPrimaryKeyPressed) {
                humanInterfaceDeviceService.keyRelease(this.primaryKeyEventId);
                this.isPrimaryKeyPressed = false;
            }

            if (this.isTertiaryKeyPressed) {
                humanInterfaceDeviceService.keyRelease(this.tertiaryKeyEventId);
                this.isTertiaryKeyPressed = false;
            }

            if (this.isQuaternaryKeyPressed) {
                humanInterfaceDeviceService.keyRelease(this.quaternaryKeyEventId);
                this.isQuaternaryKeyPressed = false;
            }

            if (!this.isSecondaryKeyPressed) {
                this.isSecondaryKeyPressed = true;
                humanInterfaceDeviceService.keyPress(this.secondaryKeyEventId);
            }
        } else if (this.tertiaryKeyEventId != this.primaryKeyEventId && value == 0.5f) {
            if (this.isPrimaryKeyPressed) {
                humanInterfaceDeviceService.keyRelease(this.primaryKeyEventId);
                this.isPrimaryKeyPressed = false;
            }

            if (this.isSecondaryKeyPressed) {
                humanInterfaceDeviceService.keyRelease(this.secondaryKeyEventId);
                this.isSecondaryKeyPressed = false;
            }

            if (this.isQuaternaryKeyPressed) {
                humanInterfaceDeviceService.keyRelease(this.quaternaryKeyEventId);
                this.isQuaternaryKeyPressed = false;
            }

            if (!this.isTertiaryKeyPressed) {
                this.isTertiaryKeyPressed = true;
                humanInterfaceDeviceService.keyPress(this.tertiaryKeyEventId);
            }
        } else if (this.quaternaryKeyEventId != this.primaryKeyEventId && value == 0.75f) {
            if (this.isPrimaryKeyPressed) {
                humanInterfaceDeviceService.keyRelease(this.primaryKeyEventId);
                this.isPrimaryKeyPressed = false;
            }

            if (this.isSecondaryKeyPressed) {
                humanInterfaceDeviceService.keyRelease(this.secondaryKeyEventId);
                this.isSecondaryKeyPressed = false;
            }

            if (this.isTertiaryKeyPressed) {
                humanInterfaceDeviceService.keyRelease(this.tertiaryKeyEventId);
                this.isTertiaryKeyPressed = false;
            }

            if (!this.isQuaternaryKeyPressed) {
                this.isQuaternaryKeyPressed = true;
                humanInterfaceDeviceService.keyPress(this.quaternaryKeyEventId);
            }
        } else {
            if (this.isPrimaryKeyPressed) {
                humanInterfaceDeviceService.keyRelease(this.primaryKeyEventId);
                this.isPrimaryKeyPressed = false;
            }

            if (this.isSecondaryKeyPressed) {
                humanInterfaceDeviceService.keyRelease(this.secondaryKeyEventId);
                this.isSecondaryKeyPressed = false;
            }

            if (this.isTertiaryKeyPressed) {
                humanInterfaceDeviceService.keyRelease(this.tertiaryKeyEventId);
                this.isTertiaryKeyPressed = false;
            }

            if (this.isQuaternaryKeyPressed) {
                humanInterfaceDeviceService.keyRelease(this.quaternaryKeyEventId);
                this.isQuaternaryKeyPressed = false;
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
