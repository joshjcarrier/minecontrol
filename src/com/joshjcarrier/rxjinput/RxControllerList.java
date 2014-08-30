package com.joshjcarrier.rxjinput;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import rx.Observable;
import rx.util.functions.Func1;

public class RxControllerList {

    public Observable<RxController> getAll() {
        return Observable.from(ControllerEnvironment.getDefaultEnvironment().getControllers())
                .map(new Func1<Controller, RxController>() {
                    @Override
                    public RxController call(Controller controller) {
                        return new RxController(controller);
                    }
                });
    }
}
