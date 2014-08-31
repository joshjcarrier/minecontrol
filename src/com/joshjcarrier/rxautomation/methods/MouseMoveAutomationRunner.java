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
                            (int) (info.getLocation().x + (xValue * 10)),
                            (int) (info.getLocation().y  + (yValue * 10)));
                }

                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
        }
    }
}
