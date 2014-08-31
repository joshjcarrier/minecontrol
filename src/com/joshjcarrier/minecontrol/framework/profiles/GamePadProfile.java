package com.joshjcarrier.minecontrol.framework.profiles;

import com.joshjcarrier.rxautomation.methods.IAutomationMethod;
import com.joshjcarrier.rxautomation.methods.KeyboardAutomationMethod;
import com.joshjcarrier.rxautomation.projection.IRxAutomationProjection;
import com.joshjcarrier.rxautomation.projection.ReplayRxAutomationProjection;
import com.joshjcarrier.rxautomation.projection.ThresholdRxAutomationProjection;
import com.joshjcarrier.rxgamepad.RxGamePad;
import javafx.util.Pair;
import net.java.games.input.Component;
import rx.Observable;
import rx.util.functions.Func1;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GamePadProfile {
    private final RxGamePad rxGamePad;

    public GamePadProfile(RxGamePad rxGamePad){
        this.rxGamePad = rxGamePad;
    }

    public Observable<Pair<IAutomationMethod, Float>> getKeyEvents() {
        ArrayList<Observable<Pair<IAutomationMethod, Float>>> keyEvents = new ArrayList<Observable<Pair<IAutomationMethod, Float>>>();

        for(Map.Entry<Component.Identifier, IAutomationMethod> entry : identifierToKeyMap.entrySet()) {

            IRxAutomationProjection projector = identifierToProjectionMap.get(entry.getKey());
            Observable<Pair<IAutomationMethod, Float>> keyEvent = projector.map(entry.getValue(), this.rxGamePad.getComponentById(entry.getKey()));
            keyEvents.add(keyEvent);
        }
//        for(Map.Entry<Component.Identifier, IAutomationMethod> entry : identifierToAutomationMethodMap.entrySet()) {
//
//            final IRxAutomationProjection projector = identifierToProjectionMap.get(entry.getKey());
//            Observable<Pair<Integer, Float>> keyEvent = this.rxGamePad.getComponentById(entry.getKey())
//                    .flatMap(new Func1<Float, Observable<Pair<Integer, Float>>>() {
//                        @Override
//                        public Observable<Pair<Integer, Float>> call(Float aFloat) {
//                            return projector.map(aFloat, );
//                        }
//                    });
//
//            keyEvents.add(keyEvent);
//        }

        return Observable.merge(keyEvents);
    }

    private HashMap<Component.Identifier, IRxAutomationProjection> identifierToProjectionMap = new HashMap<Component.Identifier, IRxAutomationProjection>()
    {
        public static final long serialVersionUID = 8658388604108766926L;
        {
            put(Component.Identifier.Button._0, new ThresholdRxAutomationProjection());
            put(Component.Identifier.Button._1, new ThresholdRxAutomationProjection());
            put(Component.Identifier.Button._2, new ThresholdRxAutomationProjection());
            put(Component.Identifier.Button._3, new ThresholdRxAutomationProjection());

            put(Component.Identifier.Axis.X, new ReplayRxAutomationProjection());
            put(Component.Identifier.Axis.Y, new ReplayRxAutomationProjection());
        }
    };

    private HashMap<Component.Identifier, IAutomationMethod> identifierToKeyMap = new HashMap<Component.Identifier, IAutomationMethod>()
    {
        public static final long serialVersionUID = 8658388604108766926L;
        {
            put(Component.Identifier.Button._0, new KeyboardAutomationMethod(KeyEvent.VK_SPACE));
            put(Component.Identifier.Button._1, new KeyboardAutomationMethod(KeyEvent.VK_Q));
            put(Component.Identifier.Button._2, new KeyboardAutomationMethod(KeyEvent.VK_SPACE));
            put(Component.Identifier.Button._3, new KeyboardAutomationMethod(KeyEvent.VK_E));

            put(Component.Identifier.Axis.X, new KeyboardAutomationMethod(KeyEvent.VK_D, KeyEvent.VK_A));
            put(Component.Identifier.Axis.Y, new KeyboardAutomationMethod(KeyEvent.VK_S, KeyEvent.VK_W));
        }
    };
}
