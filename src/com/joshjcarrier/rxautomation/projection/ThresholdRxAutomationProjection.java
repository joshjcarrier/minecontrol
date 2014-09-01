package com.joshjcarrier.rxautomation.projection;

import com.joshjcarrier.rxautomation.methods.IAutomationMethod;
import javafx.util.Pair;
import rx.Observable;
import rx.util.functions.Func1;

import java.util.concurrent.TimeUnit;

public class ThresholdRxAutomationProjection implements IRxAutomationProjection {
    public Observable<Pair<IAutomationMethod, Float>> map(final IAutomationMethod automationMethod, Observable<Float> source) {
        return source.map(new Func1<Float, Pair<IAutomationMethod, Float>>() {
            @Override
            public Pair<IAutomationMethod, Float> call(Float aFloat) {
                if(aFloat >= 0) {
                    return new Pair<IAutomationMethod, Float>(automationMethod, aFloat > 0.5 ? 1f : 0f);
                }

                return new Pair<IAutomationMethod, Float>(automationMethod, -aFloat > 0.5 ? -1f : 0f);
            }
        }).throttleWithTimeout(20, TimeUnit.MILLISECONDS);
    }
}
