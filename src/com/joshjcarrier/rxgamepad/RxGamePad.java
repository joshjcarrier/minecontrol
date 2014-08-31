package com.joshjcarrier.rxgamepad;

import com.joshjcarrier.minecontrol.framework.input.Buttons;
import com.joshjcarrier.rxjinput.RxController;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import rx.Observable;

import java.util.HashMap;

public class RxGamePad {

    private final RxController rxController;

    public RxGamePad(RxController rxController) {
        this.rxController = rxController;
    }

    @Deprecated // only for transition purposes
    public RxController getInternalRxController() {
        return this.rxController;
    }

    public Observable<Float> getComponentById(Component.Identifier componentId) {
        return this.rxController.getComponent(componentId);
    }

    public HashMap<Component.Identifier, String> getComponentLabels() {
        return new HashMap<Component.Identifier, String>() {
            private static final long serialVersionUID = 3658388604108766926L;

            {
                put(Component.Identifier.Button._0, "A");
                put(Component.Identifier.Button._1, "B");
                put(Component.Identifier.Button._2, "X");
                put(Component.Identifier.Button._3, "Y");
                put(Component.Identifier.Button._4, "LEFT_SHOULDER");
                put(Component.Identifier.Button._5, "RIGHT_SHOULDER");
                put(Component.Identifier.Button._6, "BACK");
                put(Component.Identifier.Button._7, "START");
                put(Component.Identifier.Button._8, "LEFT_STICK");
                put(Component.Identifier.Button._9, "RIGHT_STICK");
            }
        };
    }

    public String getName() {
        return this.rxController.getName();
    }
}
