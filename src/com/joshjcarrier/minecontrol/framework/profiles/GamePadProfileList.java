package com.joshjcarrier.minecontrol.framework.profiles;

import com.joshjcarrier.rxgamepad.RxGamePad;
import rx.Observable;

import java.util.Arrays;

public class GamePadProfileList {
    public GamePadProfileList() {

    }

    public Observable<GamePadProfile> getAll(RxGamePad rxGamePad) {
        return Observable.from(Arrays.asList(new GamePadProfile(rxGamePad)));
    }
}
