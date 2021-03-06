package com.joshjcarrier.rxautomation.persistence;

import com.joshjcarrier.persistence.IStorage;

public class AutomationReader implements IAutomationReader {
    private final String rootName;
    private final String sectionName;
    private final IStorage storage;

    public AutomationReader(String rootName, String sectionName, IStorage storage) {
        this.rootName = rootName;
        this.sectionName = sectionName;
        this.storage = storage;
    }

    public String read(String key) {
        return this.storage.read(this.rootName, this.sectionName + "." + key);
    }

    @Override
    public String readMethod() {
        return this.read("method");
    }

    @Override
    public String readProjection() {
        return this.read("projection");
    }

    @Override
    public Boolean readBoolean(String key) {
        return Boolean.parseBoolean(read(key));
    }

    @Override
    public Integer readInt(String key) {
        return Integer.parseInt(read(key));
    }

    @Override
    public Integer readInt(String key, Integer defaultValue) {
        return this.storage.read(this.rootName, this.sectionName + "." + key, defaultValue);
    }
}
