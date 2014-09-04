package com.joshjcarrier.rxautomation.persistence;

public class NestedAutomationWriter implements IAutomationWriter {
    private final String sectionName;
    private final IAutomationWriter automationWriter;

    public NestedAutomationWriter(String sectionName, IAutomationWriter automationWriter) {
        this.sectionName = sectionName;
        this.automationWriter = automationWriter;
    }

    @Override
    public void write(String key, Boolean value) {
        this.automationWriter.write(this.sectionName + "." + key, value);
    }

    @Override
    public void write(String key, Integer value) {
        this.automationWriter.write(this.sectionName + "." + key, value);
    }

    @Override
    public void write(String key, String value) {
        this.automationWriter.write(this.sectionName + "." + key, value);
    }

    @Override
    public void writeMethod(String value) {
        this.automationWriter.write(this.sectionName + ".method", value);
    }

    @Override
    public void writeProjection(String value) {
        this.automationWriter.write(this.sectionName + ".projection", value);
    }
}
