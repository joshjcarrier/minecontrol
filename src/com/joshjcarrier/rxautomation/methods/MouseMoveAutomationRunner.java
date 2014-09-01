package com.joshjcarrier.rxautomation.methods;

import java.awt.*;

public class MouseMoveAutomationRunner implements Runnable {
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

    public static boolean PRIMARY_SENSITIVITY_MODE = true;
    public static boolean INVERT_Y = false;
    public static int PRIMARY_SENSITIVITY_X = 10;
    public static int PRIMARY_SENSITIVITY_Y = 10;
    public static int SECONDARY_SENSITIVITY_X = 5;
    public static int SECONDARY_SENSITIVITY_Y = 5;
    public float xValue, yValue;

    public MouseMoveXAutomationMethod getXAutomationMethod() {
        return new MouseMoveXAutomationMethod(this);
    }

    public MouseMoveYAutomationMethod getYAutomationMethod() {
        return new MouseMoveYAutomationMethod(this);
    }

    @Override
    public void run() {
        while(true) {
            try {
                if (xValue != 0 || yValue != 0)
                {
                    PointerInfo info = MouseInfo.getPointerInfo();

                    // TODO 2.1+ smoothen mouse
                    humanInterfaceDeviceService.mouseMove(
                            (int) (info.getLocation().x + (xValue * (PRIMARY_SENSITIVITY_MODE ? PRIMARY_SENSITIVITY_X : SECONDARY_SENSITIVITY_X))),
                            (int) (info.getLocation().y  + (yValue * (PRIMARY_SENSITIVITY_MODE ? PRIMARY_SENSITIVITY_Y : SECONDARY_SENSITIVITY_Y) * (INVERT_Y ? -1 : 1))));
                }

                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
        }
    }
}
