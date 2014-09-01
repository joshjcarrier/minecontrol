package com.joshjcarrier.rxautomation.persistence;

public interface IAutomationReader {
    String read(String key);
    String readMethod();
    String readProjection();
    Boolean readBoolean(String key);
    Integer readInt(String key);
    Integer readInt(String key, Integer defaultValue);
}
