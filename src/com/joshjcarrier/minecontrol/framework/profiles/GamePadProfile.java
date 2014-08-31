package com.joshjcarrier.minecontrol.framework.profiles;

import com.joshjcarrier.rxautomation.projection.IRxAutomationProjection;
import com.joshjcarrier.rxautomation.projection.ReplayRxAutomationProjection;
import com.joshjcarrier.rxautomation.projection.ThresholdRxAutomationProjection;
import com.joshjcarrier.rxgamepad.RxGamePad;
import javafx.util.Pair;
import net.java.games.input.Component;
import rx.Observable;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GamePadProfile {
    private final RxGamePad rxGamePad;

    public GamePadProfile(RxGamePad rxGamePad){
        this.rxGamePad = rxGamePad;
    }

    public Observable<Pair<Integer, Float>> getKeyEvents() {
        ArrayList<Observable<Pair<Integer, Float>>> keyEvents = new ArrayList<Observable<Pair<Integer, Float>>>();

        for(Map.Entry<Component.Identifier, Integer> entry : identifierToKeyMap.entrySet()) {

            IRxAutomationProjection projector = identifierToProjectionMap.get(entry.getKey());
            Observable<Pair<Integer, Float>> keyEvent = projector.map(entry.getValue(), this.rxGamePad.getComponentById(entry.getKey()));
            keyEvents.add(keyEvent);
        }

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
        }
    };

    private HashMap<Component.Identifier, Integer> identifierToKeyMap = new HashMap<Component.Identifier, Integer>()
    {
        public static final long serialVersionUID = 8658388604108766926L;
        {
            put(Component.Identifier.Button._0, KeyEvent.VK_SPACE);
            put(Component.Identifier.Button._1, KeyEvent.VK_Q);
            put(Component.Identifier.Button._2, KeyEvent.VK_SPACE);
            put(Component.Identifier.Button._3, KeyEvent.VK_E);

            put(Component.Identifier.Axis.X, KeyEvent.VK_A);
        }
    };
}
