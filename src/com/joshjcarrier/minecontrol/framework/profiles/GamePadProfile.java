package com.joshjcarrier.minecontrol.framework.profiles;

import com.joshjcarrier.rxautomation.methods.*;
import com.joshjcarrier.rxautomation.projection.BimodalRxAutomationProjection;
import com.joshjcarrier.rxautomation.projection.IRxAutomationProjection;
import com.joshjcarrier.rxautomation.projection.ReplayRxAutomationProjection;
import com.joshjcarrier.rxautomation.projection.ThresholdRxAutomationProjection;
import com.joshjcarrier.rxgamepad.RxGamePad;
import javafx.util.Pair;
import net.java.games.input.Component;
import rx.Observable;
import rx.Subscription;
import rx.util.functions.Action1;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GamePadProfile {
    private final RxGamePad rxGamePad;
    private Subscription activeSubscription;
    MouseMoveAutomationRunner mouseMoveAutomationRunner = new MouseMoveAutomationRunner();

    public GamePadProfile(RxGamePad rxGamePad){
        this.rxGamePad = rxGamePad;

        // TODO move and terminate thread elsewhere
        Thread t = new Thread(mouseMoveAutomationRunner);
        t.start();
    }

    public Observable<Pair<IAutomationMethod, Float>> getKeyEvents() {
        ArrayList<Observable<Pair<IAutomationMethod, Float>>> keyEvents = new ArrayList<Observable<Pair<IAutomationMethod, Float>>>();

        for(Map.Entry<Component.Identifier, IAutomationMethod> entry : automationMethodHashMap.entrySet()) {

            IRxAutomationProjection projector = identifierToProjectionMap.get(entry.getKey());
            Observable<Pair<IAutomationMethod, Float>> keyEvent = projector.map(entry.getValue(), this.rxGamePad.getComponentById(entry.getKey()));
            keyEvents.add(keyEvent);
        }

        return Observable.merge(keyEvents);
    }

    public void activate(){
        if(this.activeSubscription != null && !this.activeSubscription.isUnsubscribed()) {
            return;
        }

        this.activeSubscription = getKeyEvents().subscribe(new Action1<Pair<IAutomationMethod, Float>>() {
            @Override
            public void call(Pair<IAutomationMethod, Float> iAutomationMethodFloatPair) {
                iAutomationMethodFloatPair.getKey().automate(iAutomationMethodFloatPair.getValue());
            }
        });
    }

    public void deactivate() {
        if(this.activeSubscription != null) {
            this.activeSubscription.unsubscribe();
        }
    }

    public String getName() {
        return "default";
    }

    public void save() {
        // TODO
    }

    private HashMap<Component.Identifier, IRxAutomationProjection> identifierToProjectionMap = new HashMap<Component.Identifier, IRxAutomationProjection>()
    {
        public static final long serialVersionUID = 8658388604108766926L;
        {
            put(Component.Identifier.Button._0, new ThresholdRxAutomationProjection());
            put(Component.Identifier.Button._1, new ThresholdRxAutomationProjection());
            put(Component.Identifier.Button._2, new ThresholdRxAutomationProjection());
            put(Component.Identifier.Button._3, new ThresholdRxAutomationProjection());
            put(Component.Identifier.Button._4, new ThresholdRxAutomationProjection());
            put(Component.Identifier.Button._5, new ThresholdRxAutomationProjection());
            put(Component.Identifier.Button._7, new ThresholdRxAutomationProjection());
            put(Component.Identifier.Button._8, new ThresholdRxAutomationProjection());
            put(Component.Identifier.Button._9, new ThresholdRxAutomationProjection());

            put(Component.Identifier.Axis.RX, new BimodalRxAutomationProjection());
            put(Component.Identifier.Axis.RY, new BimodalRxAutomationProjection());

            put(Component.Identifier.Axis.X, new ReplayRxAutomationProjection());
            put(Component.Identifier.Axis.Y, new ReplayRxAutomationProjection());
            put(Component.Identifier.Axis.Z, new ThresholdRxAutomationProjection());
        }
    };

    private HashMap<Component.Identifier, IAutomationMethod> automationMethodHashMap = new HashMap<Component.Identifier, IAutomationMethod>()
    {
        public static final long serialVersionUID = 4658388604108766926L;
        {
            put(Component.Identifier.Button._0, new KeyboardAutomationMethod(KeyEvent.VK_SPACE));
            put(Component.Identifier.Button._1, new KeyboardAutomationMethod(KeyEvent.VK_Q));
            put(Component.Identifier.Button._2, new MouseButtonAutomationMethod(KeyEvent.BUTTON2_MASK));
            put(Component.Identifier.Button._3, new KeyboardAutomationMethod(KeyEvent.VK_E));
            put(Component.Identifier.Button._4, new MouseWheelAutomationMethod(-1));
            put(Component.Identifier.Button._5, new MouseWheelAutomationMethod(1));
            put(Component.Identifier.Button._7, new KeyboardAutomationMethod(KeyEvent.VK_ESCAPE));
            put(Component.Identifier.Button._8, new KeyboardAutomationMethod(KeyEvent.VK_SHIFT));
            put(Component.Identifier.Button._9, new KeyboardAutomationMethod(KeyEvent.VK_SPACE));

            put(Component.Identifier.Axis.RX, mouseMoveAutomationRunner.getXAutomationMethod());
            put(Component.Identifier.Axis.RY, mouseMoveAutomationRunner.getYAutomationMethod());

            put(Component.Identifier.Axis.X, new KeyboardAutomationMethod(KeyEvent.VK_D, KeyEvent.VK_A));
            put(Component.Identifier.Axis.Y, new KeyboardAutomationMethod(KeyEvent.VK_S, KeyEvent.VK_W));
            put(Component.Identifier.Axis.Z, new MouseButtonAutomationMethod(KeyEvent.BUTTON3_MASK));
        }
    };
}
