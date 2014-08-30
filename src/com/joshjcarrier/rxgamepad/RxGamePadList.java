package com.joshjcarrier.rxgamepad;

import com.joshjcarrier.rxjinput.RxController;
import com.joshjcarrier.rxjinput.RxControllerList;
import rx.Observable;
import rx.util.functions.Func1;

public class RxGamePadList {
    private final RxControllerList rxControllerList;

    public RxGamePadList() {
        this.rxControllerList = new RxControllerList();
    }

    public Observable<RxGamePad> getAll() {
        return this.rxControllerList.getAll().map(new Func1<RxController, RxGamePad>() {
            @Override
            public RxGamePad call(RxController rxController) {
                return new RxGamePad(rxController);
            }
        });
    }
}
