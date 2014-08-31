package com.joshjcarrier.rxautomation.projection;

import javafx.util.Pair;
import rx.Observable;
import rx.util.functions.Func2;

import java.util.concurrent.TimeUnit;

public class ReplayRxAutomationProjection implements IRxAutomationProjection {
    @Override
    public Observable<Pair<Integer, Float>> map(final int keyEventId, Observable<Float> source) {
        return Observable.combineLatest(Observable.interval(5, TimeUnit.MILLISECONDS), source, new Func2<Long, Float, Pair<Integer, Float>>() {
            @Override
            public Pair<Integer, Float> call(Long aLong, Float o) {
                int ratio = (int) (aLong % 10);
                return new Pair<Integer, Float>(keyEventId, o * 10 > 10 - ratio ? 1f : 0);
            }
        });
    }
}
