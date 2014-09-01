package com.joshjcarrier.rxautomation.projection;

import com.joshjcarrier.rxautomation.methods.IAutomationMethod;
import javafx.util.Pair;
import rx.Observable;
import rx.util.functions.Func1;

public class BimodalRxAutomationProjection implements IRxAutomationProjection {
    public Observable<Pair<IAutomationMethod, Float>> map(final IAutomationMethod automationMethod, Observable<Float> source) {
        return source.map(new Func1<Float, Pair<IAutomationMethod, Float>>() {
            @Override
            public Pair<IAutomationMethod, Float> call(Float aFloat) {
                if(aFloat >= 0) {
                    return new Pair<IAutomationMethod, Float>(automationMethod, aFloat > 0.85 ? 1f : (aFloat < 0.07 ? 0 : aFloat));
                }

                return new Pair<IAutomationMethod, Float>(automationMethod, -aFloat > 0.85 ? -1f : (-aFloat < 0.07 ? 0 : aFloat));
            }
        });
    }
}
