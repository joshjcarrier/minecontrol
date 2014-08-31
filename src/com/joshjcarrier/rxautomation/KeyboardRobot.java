package com.joshjcarrier.rxautomation;

import javafx.util.Pair;
import rx.Observable;
import rx.Subscription;
import rx.util.functions.Action1;

import java.awt.*;

public class KeyboardRobot {
    private Robot humanInterfaceDeviceService;
    Subscription subscribe;

    public KeyboardRobot(Observable<Pair<Integer, Float>> keyEventIdStates)
    {
        try
        {
            this.humanInterfaceDeviceService = new Robot();

            // this prevents the OS from ignoring events generated too quickly in succession
            this.humanInterfaceDeviceService.setAutoDelay(10);

            subscribe = keyEventIdStates.subscribe(new Action1<Pair<Integer, Float>>() {
                @Override
                public void call(Pair<Integer, Float> keyEventIdState) {
                    if(keyEventIdState.getValue() == 1f) {
                        humanInterfaceDeviceService.keyPress(keyEventIdState.getKey());
                    }
                    else {
                        humanInterfaceDeviceService.keyRelease(keyEventIdState.getKey());
                    }
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
