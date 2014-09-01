package com.joshjcarrier.rxautomation.projection;

import com.joshjcarrier.rxautomation.methods.IAutomationMethod;
import com.joshjcarrier.rxautomation.persistence.IAutomationWriter;
import javafx.util.Pair;
import rx.Observable;

public interface IRxAutomationProjection {
    Observable<Pair<IAutomationMethod, Float>> map(IAutomationMethod automationMethod, Observable<Float> source);

    void write(IAutomationWriter automationWriter);
}
