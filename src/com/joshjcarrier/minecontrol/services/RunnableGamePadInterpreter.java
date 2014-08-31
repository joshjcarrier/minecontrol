package com.joshjcarrier.minecontrol.services;

import com.joshjcarrier.minecontrol.framework.input.ControllerProfile;
import com.joshjcarrier.minecontrol.framework.input.GamePad;
import com.joshjcarrier.minecontrol.framework.input.GamePadState;
import com.joshjcarrier.minecontrol.ui.models.GamePadWrapper;
import com.joshjcarrier.rxautomation.KeyboardRobot;
import com.joshjcarrier.rxautomation.projection.IRxKeyAutomationProjection;
import com.joshjcarrier.rxautomation.projection.ThresholdRxKeyAutomationProjection;
import com.joshjcarrier.rxgamepad.RxGamePad;
import com.joshjcarrier.rxgamepad.RxGamePadList;
import javafx.util.Pair;
import net.java.games.input.Component;
import rx.Observable;
import rx.Subscription;
import rx.util.functions.Action1;
import rx.util.functions.Func1;

import java.awt.event.KeyEvent;
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
		
		gamePadSubscription = this.gamePad.getState().subscribe(new Action1<GamePadState>(){

			public void call(GamePadState arg0) {
				replayService.update(arg0);
			}});

        IRxKeyAutomationProjection projector = new ThresholdRxKeyAutomationProjection();
        Observable<Pair<Integer, Boolean>> keyEventA = projector.map(KeyEvent.VK_Q, gamePadWrapper.getGamePad().getComponentById(Component.Identifier.Button._0));
        Observable<Pair<Integer, Boolean>> keyEventB = projector.map(KeyEvent.VK_E, gamePadWrapper.getGamePad().getComponentById(Component.Identifier.Button._1));

        Observable<Pair<Integer, Boolean>> keyEvents = Observable.merge(keyEventA, keyEventB);
        if(keyboardRobot != null) {
            keyboardRobot.dispose();
        }

        this.keyboardRobot = new KeyboardRobot(keyEvents);
	}
    private KeyboardRobot keyboardRobot;

	public void run()
	{	
		//final RunnableHidReplayService replayService = new RunnableHidReplayService(this.profile);
		Thread replayServiceThread = new Thread(this.replayService);
		replayServiceThread.start();
	}
}
