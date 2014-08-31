package com.joshjcarrier.minecontrol.ui.controllers;

import com.joshjcarrier.minecontrol.framework.profiles.GamePadProfile;
import com.joshjcarrier.minecontrol.ui.models.GamePadWrapper;
import com.joshjcarrier.minecontrol.ui.views.GamePadProfileView;
import com.joshjcarrier.minecontrol.ui.views.MainView;
import com.joshjcarrier.rxgamepad.RxGamePad;
import com.joshjcarrier.rxgamepad.RxGamePadList;
import rx.util.functions.Func1;

import java.util.List;

public class MainController {
    private final RxGamePadList rxGamePadList;
    private GamePadProfile activeProfile;

    public MainController(RxGamePadList rxGamePadList) {
        this.rxGamePadList = rxGamePadList;
    }

    public List<GamePadWrapper> getGamePads() {
        return this.rxGamePadList.getAll()
                .map(new Func1<RxGamePad, GamePadWrapper>() {
                    @Override
                    public GamePadWrapper call(RxGamePad rxController) {
                        return new GamePadWrapper(rxController);
                    }
                })
                .toList().toBlockingObservable().first();// temporary to maintain interface
    }

    public void setActiveGamePad(GamePadWrapper gamePadWrapper) {
        if(this.activeProfile != null) {
            activeProfile.deactivate();
        }

        this.activeProfile = gamePadWrapper.getDefaultProfile();
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
