package com.joshjcarrier.rxautomation.projection;

import javafx.util.Pair;
import rx.Observable;

public interface IRxAutomationProjection {
    Observable<Pair<Integer, Float>> map(int keyEventId, Observable<Float> source);
}
