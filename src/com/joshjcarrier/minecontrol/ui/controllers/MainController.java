package com.joshjcarrier.minecontrol.ui.controllers;

import com.joshjcarrier.minecontrol.framework.input.ControllerProfile;
import com.joshjcarrier.minecontrol.framework.profiles.GamePadProfile;
import com.joshjcarrier.minecontrol.services.ProfileStorageService;
import com.joshjcarrier.minecontrol.ui.models.GamePadWrapper;
import com.joshjcarrier.minecontrol.ui.views.MainView;
import com.joshjcarrier.rxgamepad.RxGamePad;
import com.joshjcarrier.rxgamepad.RxGamePadList;
import rx.util.functions.Func1;

import java.util.List;

public class MainController {
    private final RxGamePadList rxGamePadList;
    private GamePadProfile activeProfile;
    private ControllerProfile profile;

    public MainController(RxGamePadList rxGamePadList) {
        this.rxGamePadList = rxGamePadList;

        ProfileStorageService profileStorageService = new ProfileStorageService();
        this.profile = profileStorageService.load("default");
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

    @Deprecated
    public ControllerProfile getControllerProfile() {
        return this.profile;
    }

    public void setActiveGamePad(GamePadWrapper gamePadWrapper) {
        if(this.activeProfile != null) {
            activeProfile.deactivate();
        }

        this.activeProfile = gamePadWrapper.getDefaultProfile();
        this.activeProfile.activate();
    }

    public MainView index() {
        return new MainView(this);
    }
}
