package com.joshjcarrier.rxgamepad;

import com.joshjcarrier.rxjinput.RxController;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import rx.Observable;

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

    public Observable<Float> getComponentById(Component.Identifier componentId) {
        return this.rxController.getComponent(componentId);
    }
}
