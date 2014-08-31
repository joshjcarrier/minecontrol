package com.joshjcarrier.rxautomation.projection;

import javafx.util.Pair;
import rx.Observable;
import rx.util.functions.Func1;

public class ThresholdRxAutomationProjection implements IRxAutomationProjection {
    public Observable<Pair<Integer, Float>> map(final int keyEventId, Observable<Float> source) {
        return source.map(new Func1<Float, Pair<Integer, Float>>() {
            @Override
            public Pair<Integer, Float> call(Float aFloat) {
                return new Pair<Integer, Float>(keyEventId, aFloat > 0.5  ? 1f : 0f);
            }
        });
    }
}
