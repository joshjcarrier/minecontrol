package com.joshjcarrier.rxautomation.persistence;

public interface IAutomationWriter {
    public void write(String key, Integer value);

    public void write(String key, String value);
}
