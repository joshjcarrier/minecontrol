package com.joshjcarrier.rxautomation.methods;

import com.joshjcarrier.persistence.IStorage;

public interface IAutomationMethod {
    void automate(Float value);

    String getName();

    void save(IStorage storage, String rootName);
}
