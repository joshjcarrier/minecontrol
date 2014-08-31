package com.joshjcarrier.minecontrol.framework.profiles;

import com.joshjcarrier.persistence.IStorage;
import com.joshjcarrier.rxgamepad.RxGamePad;
import rx.Observable;

import java.util.Arrays;

public class GamePadProfileList {
    private final IStorage storage;

    public GamePadProfileList(IStorage storage) {
        this.storage = storage;
    }

    public Observable<GamePadProfile> getAll(RxGamePad rxGamePad) {
        GamePadProfile profile = new GamePadProfile("default", rxGamePad, this.storage);
        profile.restore();
        return Observable.from(Arrays.asList(profile));
    }
}
