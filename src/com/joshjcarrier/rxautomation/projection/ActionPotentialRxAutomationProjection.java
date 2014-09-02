package com.joshjcarrier.rxautomation.projection;

import com.joshjcarrier.Pair;
import com.joshjcarrier.rxautomation.methods.IAutomationMethod;
import com.joshjcarrier.rxautomation.persistence.IAutomationReader;
import com.joshjcarrier.rxautomation.persistence.IAutomationWriter;
import rx.Observable;
import rx.util.Timestamped;
import rx.util.functions.Func1;

public class ActionPotentialRxAutomationProjection implements IRxAutomationProjection {
    private static final String PROJECTION_ID = "action-potential";

    public static IRxAutomationProjection load(IAutomationReader automationReader) {
        try {
            String projectionId = automationReader.readProjection();
            if(projectionId.equalsIgnoreCase(PROJECTION_ID)) {
                return new ActionPotentialRxAutomationProjection();
            }
        } catch (Exception e) {
            // do nothing
        }

        return null;
    }

    public Observable<Pair<IAutomationMethod, Float>> map(final IAutomationMethod automationMethod, Observable<Float> source) {
        return source
                .map(new Func1<Float, Float>() {
                    @Override
                    public Float call(Float value) {
                        if (value >= 0) {
                            return value > 0.7 ? 1f : 0f;
                        } else {
                            return -value > 0.7 ? -1f : 0f;
                        }
                    }
                })
                .distinctUntilChanged()
                .timestamp()
                .flatMap(new Func1<Timestamped<Float>, Observable<Float>>() {
                    long lastNonZeroMillis = 0L;

                    @Override
                    public Observable<Float> call(Timestamped<Float> timestampedFloat) {
                        if (timestampedFloat.getValue() != 0) {
                            // past the refractory period, fire again
                            if (timestampedFloat.getTimestampMillis() - lastNonZeroMillis > 100) {
                                lastNonZeroMillis = timestampedFloat.getTimestampMillis();
                                return Observable.from(timestampedFloat.getValue(), 0f);
                            }
                        }

                        return Observable.just(0f);
                    }
                })
                .distinctUntilChanged()
                .map(new Func1<Float, Pair<IAutomationMethod, Float>>() {
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
