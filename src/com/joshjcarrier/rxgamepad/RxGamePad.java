package com.joshjcarrier.rxgamepad;

import com.joshjcarrier.rxjinput.RxController;
import net.java.games.input.Component;
import rx.Observable;

import java.util.HashMap;

public class RxGamePad {

    private final RxController rxController;

    public RxGamePad(RxController rxController) {
        this.rxController = rxController;
    }

    public Observable<Float> getComponentById(Component.Identifier componentId) {
        return this.rxController.getComponent(componentId);
    }

    public HashMap<Component.Identifier, String> getButtonLabels() {
        return new HashMap<Component.Identifier, String>() {
            private static final long serialVersionUID = 3658388604108766926L;

            {
                put(Component.Identifier.Button._0, "A");
                put(Component.Identifier.Button._1, "B");
                put(Component.Identifier.Button._2, "X");
                put(Component.Identifier.Button._3, "Y");
                put(Component.Identifier.Button._4, "Left shoulder");
                put(Component.Identifier.Button._5, "Right shoulder");
                put(Component.Identifier.Button._6, "Back");
                put(Component.Identifier.Button._7, "Start");
                put(Component.Identifier.Button._8, "Left joystick press");
                put(Component.Identifier.Button._9, "Right joystick press");

                put(Component.Identifier.Axis.POV, "D-Pad");
            }
        };
    }

    public String getName() {
        return this.rxController.getName();
    }
}
