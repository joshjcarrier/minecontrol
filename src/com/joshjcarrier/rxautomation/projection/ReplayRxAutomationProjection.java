package com.joshjcarrier.rxautomation.projection;

import com.joshjcarrier.rxautomation.methods.IAutomationMethod;
import javafx.util.Pair;
import rx.Observable;
import rx.util.functions.Func2;

import java.util.concurrent.TimeUnit;

public class ReplayRxAutomationProjection implements IRxAutomationProjection {
    @Override
    public Observable<Pair<IAutomationMethod, Float>> map(final IAutomationMethod automationMethod, Observable<Float> source) {
        return Observable.combineLatest(Observable.interval(15, TimeUnit.MILLISECONDS), source, new Func2<Long, Float, Pair<IAutomationMethod, Float>>() {
            @Override
            public Pair<IAutomationMethod, Float> call(Long aLong, Float o) {
                int ratio = (int) (aLong % 10);
                if(o >= 0) {
                    return new Pair<IAutomationMethod, Float>(automationMethod, o * 10 > 10 - ratio ? 1f : 0);
                }

                return new Pair<IAutomationMethod, Float>(automationMethod, -o * 10 > 10 - ratio ? -1f : 0);
            }
        }).distinctUntilChanged();
    }
}
