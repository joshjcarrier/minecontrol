package com.joshjcarrier.rxautomation.projection;

import javafx.util.Pair;
import rx.Observable;

public interface IRxKeyAutomationProjection {
    Observable<Pair<Integer, Boolean>> map(int keyEventId, Observable<Float> source);
}
