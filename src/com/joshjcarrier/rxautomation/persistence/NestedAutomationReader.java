package com.joshjcarrier.rxautomation.persistence;

public class NestedAutomationReader implements IAutomationReader {
    private final String sectionName;
    private final IAutomationReader automationReader;

    public NestedAutomationReader(String sectionName, IAutomationReader automationReader) {
        this.sectionName = sectionName;
        this.automationReader = automationReader;
    }

    @Override
    public String read(String key) {
        return this.automationReader.read(this.sectionName + "." + key);
    }

    @Override
    public String readMethod() {
        return this.automationReader.read(this.sectionName + ".method");
    }

    @Override
    public String readProjection() {
        return this.automationReader.read(this.sectionName + ".projection");
    }

    @Override
    public Boolean readBoolean(String key) {
        return this.automationReader.readBoolean(this.sectionName + "." + key);
    }

    @Override
    public Integer readInt(String key) {
        return this.automationReader.readInt(this.sectionName + "." + key);
    }

    @Override
    public Integer readInt(String key, Integer defaultValue) {
        return this.automationReader.readInt(this.sectionName + "." + key, defaultValue);
    }
}
