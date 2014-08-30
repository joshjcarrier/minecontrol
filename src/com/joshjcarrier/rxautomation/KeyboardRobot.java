package com.joshjcarrier.rxautomation;

import com.joshjcarrier.minecontrol.framework.input.ControllerProfile;
import com.joshjcarrier.minecontrol.framework.input.GamePadState;
import com.joshjcarrier.minecontrol.services.ReplayState;
import com.joshjcarrier.rxgamepad.RxGamePad;
import rx.Observable;
import rx.Subscription;
import rx.util.functions.Action1;

import java.awt.*;
import java.awt.event.KeyEvent;

public class KeyboardRobot {
    private Robot humanInterfaceDeviceService;
    Subscription subscribe;

    public KeyboardRobot(Observable<Integer> keyEventIds)
    {
        try
        {
            this.humanInterfaceDeviceService = new Robot();

            // this prevents the OS from ignoring events generated too quickly in succession
            this.humanInterfaceDeviceService.setAutoDelay(10);

            subscribe = keyEventIds.subscribe(new Action1<Integer>() {
                @Override
                public void call(Integer keyEventId) {
                    humanInterfaceDeviceService.keyPress(keyEventId);
                    humanInterfaceDeviceService.keyRelease(keyEventId);
                }
            });
        }
        catch (AWTException e)
        {
            // TODO 2.0+ throw exception
            e.printStackTrace();
        }
    }

    public void dispose() {
        this.subscribe.unsubscribe();
    }
}
