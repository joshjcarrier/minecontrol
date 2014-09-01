package com.joshjcarrier.rxautomation.methods;

import com.joshjcarrier.rxautomation.persistence.IAutomationWriter;

public interface IAutomationMethod {
    void automate(Float value);

    String getName();

    void write(IAutomationWriter automationWriter);
}
