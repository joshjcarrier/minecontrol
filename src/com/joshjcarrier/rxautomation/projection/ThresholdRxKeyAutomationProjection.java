package com.joshjcarrier.rxautomation.projection;

import javafx.util.Pair;
import rx.Observable;
import rx.util.functions.Func1;

public class ThresholdRxKeyAutomationProjection implements IRxKeyAutomationProjection {
    public Observable<Pair<Integer, Boolean>> map(final int keyEventId, Observable<Float> source) {
        return source.map(new Func1<Float, Pair<Integer, Boolean>>() {
            @Override
            public Pair<Integer, Boolean> call(Float aFloat) {
                return new Pair<Integer, Boolean>(keyEventId, aFloat > 0.5);
            }
        });
    }
}
