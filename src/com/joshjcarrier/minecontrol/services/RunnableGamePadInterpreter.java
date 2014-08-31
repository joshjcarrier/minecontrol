package com.joshjcarrier.minecontrol.services;

import com.joshjcarrier.minecontrol.framework.input.ControllerProfile;
import com.joshjcarrier.minecontrol.framework.input.GamePad;
import com.joshjcarrier.minecontrol.framework.profiles.GamePadProfile;
import com.joshjcarrier.minecontrol.ui.models.GamePadWrapper;
import com.joshjcarrier.rxautomation.methods.IAutomationMethod;
import com.joshjcarrier.rxgamepad.RxGamePad;
import com.joshjcarrier.rxgamepad.RxGamePadList;
import javafx.util.Pair;
import rx.Subscription;
import rx.util.functions.Action1;
import rx.util.functions.Func1;

import java.util.List;

/**
 * Manages the active game pad connection.
 * @author joshjcarrier
 *
 */
public class RunnableGamePadInterpreter implements Runnable
{
	private final RunnableHidReplayService replayService;
	private GamePad gamePad;
	private ControllerProfile profile;
	private Subscription gamePadSubscription;
    private final RxGamePadList rxGamePadList;
	
	public RunnableGamePadInterpreter()
	{
		ProfileStorageService profileStorageService = new ProfileStorageService();
		this.profile = profileStorageService.load("default");
		this.replayService = new RunnableHidReplayService(this.profile);
        this.rxGamePadList = new RxGamePadList();
	}
	
	public List<GamePadWrapper> getInputReaderDevices()
	{
        return this.rxGamePadList.getAll()
                .map(new Func1<RxGamePad, GamePadWrapper>() {
            @Override
            public GamePadWrapper call(RxGamePad rxController) {
                return new GamePadWrapper(rxController);
            }
        })
                .toList().toBlockingObservable().first(); // temporary to maintain interface
	}
	
	public ControllerProfile getControllerProfile() {
		return this.profile;
	}
		
	public void setInputReaderDevice(GamePadWrapper gamePadWrapper)
	{
		this.gamePad = new GamePad(gamePadWrapper.getGamePad());


		if(gamePadSubscription != null)
		{
			gamePadSubscription.unsubscribe();
		}
		
		if (this.gamePad == null)
		{
			replayService.update(null);
		}
		
//		gamePadSubscription = this.gamePad.getState().subscribe(new Action1<GamePadState>(){
//
//			public void call(GamePadState arg0) {
//				replayService.update(arg0);
//			}});

        GamePadProfile defaultProfile = new GamePadProfile(gamePadWrapper.getGamePad());

        this.gamePadSubscription = defaultProfile.getKeyEvents().subscribe(new Action1<Pair<IAutomationMethod, Float>>() {
            @Override
            public void call(Pair<IAutomationMethod, Float> iAutomationMethodFloatPair) {
                iAutomationMethodFloatPair.getKey().automate(iAutomationMethodFloatPair.getValue());
            }
        });
	}

	public void run()
	{	
		//final RunnableHidReplayService replayService = new RunnableHidReplayService(this.profile);
		//Thread replayServiceThread = new Thread(this.replayService);
		//replayServiceThread.start();
	}
}
