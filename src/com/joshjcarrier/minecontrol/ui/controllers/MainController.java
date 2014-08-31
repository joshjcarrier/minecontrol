package com.joshjcarrier.minecontrol.ui.controllers;

import com.joshjcarrier.minecontrol.framework.profiles.GamePadProfile;
import com.joshjcarrier.minecontrol.framework.profiles.GamePadProfileList;
import com.joshjcarrier.minecontrol.ui.models.GamePadProfileWrapper;
import com.joshjcarrier.minecontrol.ui.models.GamePadWrapper;
import com.joshjcarrier.minecontrol.ui.views.GamePadProfileView;
import com.joshjcarrier.minecontrol.ui.views.MainView;
import com.joshjcarrier.rxgamepad.RxGamePad;
import com.joshjcarrier.rxgamepad.RxGamePadList;
import rx.util.functions.Func1;

import java.util.List;

public class MainController {
    private final RxGamePadList rxGamePadList;
    private final GamePadProfileList gamePadProfileList;
    private GamePadWrapper activeGamePad;
    private GamePadProfileWrapper activeProfile;

    public MainController(RxGamePadList rxGamePadList, GamePadProfileList gamePadProfileList) {
        this.rxGamePadList = rxGamePadList;
        this.gamePadProfileList = gamePadProfileList;
    }

    public List<GamePadWrapper> getGamePads() {
        return this.rxGamePadList.getAll()
                .map(new Func1<RxGamePad, GamePadWrapper>() {
                    @Override
                    public GamePadWrapper call(RxGamePad rxGamePad) {
                        List<GamePadProfileWrapper> profiles = gamePadProfileList.getAll(rxGamePad).map(new Func1<GamePadProfile, GamePadProfileWrapper>() {
                                    @Override
                                    public GamePadProfileWrapper call(GamePadProfile gamePadProfile) {
                                        return new GamePadProfileWrapper(gamePadProfile);
                                    }
                                })
                                .toList()
                                .toBlockingObservable()
                                .first();
                        return new GamePadWrapper(rxGamePad, profiles);
                    }
                })
                .toList()
                .toBlockingObservable()
                .first();// temporary to maintain interface
    }

    public GamePadWrapper getActiveGamePad() {
        return this.activeGamePad;
    }

    public void setActiveGamePad(GamePadWrapper gamePadWrapper) {
        if(this.activeProfile != null) {
            activeProfile.deactivate();
        }

        this.activeGamePad = gamePadWrapper;
        this.activeProfile = gamePadWrapper.getProfiles().get(0);
        this.activeProfile.activate();
    }

    public GamePadProfileView navigateToGamePadProfile() {
        if (this.activeProfile == null) {
            return null;
        }

        GamePadProfileController gamePadProfileController = new GamePadProfileController(this.activeProfile);
        return new GamePadProfileView(gamePadProfileController);
    }

    public MainView index() {
        return new MainView(this);
    }
}
