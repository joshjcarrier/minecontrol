package com.joshjcarrier.rxautomation.projection;

import com.joshjcarrier.rxautomation.methods.IAutomationMethod;
import com.joshjcarrier.rxautomation.persistence.IAutomationReader;
import com.joshjcarrier.rxautomation.persistence.IAutomationWriter;
import javafx.util.Pair;
import rx.Observable;
import rx.util.functions.Func1;
import rx.util.functions.Func2;

import java.util.Collections;
import java.util.List;

public class BufferedSwitchRxAutomationProjection implements IRxAutomationProjection {
    private static final String PROJECTION_ID = "buffered-switch";

    public static IRxAutomationProjection load(IAutomationReader automationReader) {
        try {
            String projectionId = automationReader.readProjection();
            if(projectionId.equalsIgnoreCase(PROJECTION_ID)) {
                return new BufferedSwitchRxAutomationProjection();
            }
        } catch (Exception e) {
            // do nothing
        }

        return null;
    }

    @Override
    public Observable<Pair<IAutomationMethod, Float>> map(final IAutomationMethod automationMethod, Observable<Float> source) {
        return source.buffer(2)
                .scan(0f, new Func2<Float, List<Float>, Float>() {
                    @Override
                    public Float call(Float aFloat, List<Float> floats) {
                        if(aFloat >= 0) {
                            return aFloat == 0 ? Collections.max(floats) : 0;
                        }

                        return aFloat == 0 ? Collections.min(floats) : 0;
                    }
                }).map(new Func1<Float, Pair<IAutomationMethod, Float>>() {
                    @Override
                    public Pair<IAutomationMethod, Float> call(Float aFloat) {
                        return new Pair<IAutomationMethod, Float>(automationMethod, aFloat);
                    }
                });
    }

    @Override
    public void write(IAutomationWriter automationWriter) {
        automationWriter.writeProjection(PROJECTION_ID);
    }
}
