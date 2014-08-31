package com.joshjcarrier.rxautomation.persistence;

import com.joshjcarrier.persistence.IStorage;

public class AutomationWriter implements IAutomationWriter {
    private final String rootName;
    private final String sectionName;
    private final IStorage storage;

    public AutomationWriter(String rootName, String sectionName, IStorage storage) {
        this.rootName = rootName;
        this.sectionName = sectionName;
        this.storage = storage;
    }

    @Override
    public void write(String key, Integer value) {
        this.storage.write(this.rootName, this.sectionName + "." + key, value);
    }

    @Override
    public void write(String key, String value) {
        this.storage.write(this.rootName, this.sectionName + "." + key, value);
    }
}
