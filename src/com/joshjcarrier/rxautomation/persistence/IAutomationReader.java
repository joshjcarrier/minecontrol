package com.joshjcarrier.rxautomation.persistence;

public interface IAutomationReader {
    String read(String key);
    String readMethod();
    Boolean readBoolean(String key);
    Integer readInt(String key);
}
