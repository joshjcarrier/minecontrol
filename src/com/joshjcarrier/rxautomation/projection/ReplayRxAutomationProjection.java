package com.joshjcarrier.rxautomation.projection;

import com.joshjcarrier.rxautomation.methods.IAutomationMethod;
import com.joshjcarrier.rxautomation.persistence.IAutomationReader;
import com.joshjcarrier.rxautomation.persistence.IAutomationWriter;
import javafx.util.Pair;
import rx.Observable;
import rx.util.functions.Func2;

import java.util.concurrent.TimeUnit;

public class ReplayRxAutomationProjection implements IRxAutomationProjection {
    private static final String PROJECTION_ID = "replay";

    public static IRxAutomationProjection load(IAutomationReader automationReader) {
        try {
            String projectionId = automationReader.readProjection();
            if(projectionId.equalsIgnoreCase(PROJECTION_ID)) {
                return new ReplayRxAutomationProjection();
            }
        } catch (Exception e) {
            // do nothing
        }

        return null;
    }

    @Override
    public Observable<Pair<IAutomationMethod, Float>> map(final IAutomationMethod automationMethod, Observable<Float> source) {
        return Observable.combineLatest(Observable.interval(10, TimeUnit.MILLISECONDS), source, new Func2<Long, Float, Pair<IAutomationMethod, Float>>() {
            @Override
            public Pair<IAutomationMethod, Float> call(Long aLong, Float o) {
                if(o >= 0) {
                    return new Pair<IAutomationMethod, Float>(automationMethod, o > 0 ? 1f : 0);
                }

                return new Pair<IAutomationMethod, Float>(automationMethod, -o > 0 ? -1f : 0);
            }
        }).distinctUntilChanged();
    }

    @Override
    public void write(IAutomationWriter automationWriter) {
        automationWriter.writeProjection(PROJECTION_ID);
    }
}
