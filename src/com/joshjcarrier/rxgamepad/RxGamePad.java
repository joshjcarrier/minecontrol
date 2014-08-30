package com.joshjcarrier.rxgamepad;

import com.joshjcarrier.rxjinput.RxController;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import rx.Observable;
import rx.util.functions.Action1;
import rx.util.functions.Func1;

public class RxGamePad {

    private final RxController rxController;

    public RxGamePad(RxController rxController) {
        this.rxController = rxController;
    }

    @Deprecated // only for transition purposes
    public Controller getInternalController() {
        return this.rxController.getInternalController();
    }

    @Deprecated // only for transition purposes
    public RxController getInternalRxController() {
        return this.rxController;
    }

    public Observable<Float> getAxisX() {
        return this.rxController.getComponent(Component.Identifier.Axis.X);
    }
}
