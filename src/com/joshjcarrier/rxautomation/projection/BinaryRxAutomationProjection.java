package com.joshjcarrier.rxautomation.projection;

import com.joshjcarrier.rxautomation.methods.IAutomationMethod;
import com.joshjcarrier.rxautomation.persistence.IAutomationReader;
import com.joshjcarrier.rxautomation.persistence.IAutomationWriter;
import com.joshjcarrier.Pair;
import rx.Observable;
import rx.util.functions.Func1;

public class BinaryRxAutomationProjection implements IRxAutomationProjection {
    private static final String PROJECTION_ID = "binary";

    public static IRxAutomationProjection load(IAutomationReader automationReader) {
        try {
            String projectionId = automationReader.readProjection();
            if(projectionId.equalsIgnoreCase(PROJECTION_ID)) {
                return new BinaryRxAutomationProjection();
            }
        } catch (Exception e) {
            // do nothing
        }

        return null;
    }

    public Observable<Pair<IAutomationMethod, Float>> map(final IAutomationMethod automationMethod, Observable<Float> source) {
        return source.map(new Func1<Float, Pair<IAutomationMethod, Float>>() {
            @Override
            public Pair<IAutomationMethod, Float> call(Float aFloat) {
                if(aFloat >= 0) {
                    return new Pair<IAutomationMethod, Float>(automationMethod, aFloat > 0 ? 1f : 0f);
                }

                return new Pair<IAutomationMethod, Float>(automationMethod, -aFloat > 0 ? -1f : 0f);
            }
        }).distinctUntilChanged();
    }

    @Override
    public void write(IAutomationWriter automationWriter) {
        automationWriter.writeProjection(PROJECTION_ID);
    }
}
