package com.joshjcarrier.rxautomation;

import com.joshjcarrier.minecontrol.framework.input.ControllerProfile;
import com.joshjcarrier.minecontrol.framework.input.GamePadState;
import com.joshjcarrier.minecontrol.services.ReplayState;
import com.joshjcarrier.rxgamepad.RxGamePad;
import rx.util.functions.Action1;

import java.awt.*;

public class KeyboardRobot {
    private final RxGamePad rxGamePad;
    private Robot humanInterfaceDeviceService;

    public KeyboardRobot(RxGamePad rxGamePad)
    {
        this.rxGamePad = rxGamePad;
        try
        {
            this.humanInterfaceDeviceService = new Robot();

            // this prevents the OS from ignoring events generated too quickly in succession
            this.humanInterfaceDeviceService.setAutoDelay(10);

            this.rxGamePad.getAxisX().subscribe(new Action1<Float>() {
                @Override
                public void call(Float aFloat) {
                    System.out.println(aFloat);
                }
            });
        }
        catch (AWTException e)
        {
            // TODO 2.0+ throw exception
            e.printStackTrace();
        }
    }
}
