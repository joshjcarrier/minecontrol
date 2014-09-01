package com.joshjcarrier.rxautomation.persistence;

public interface IAutomationReader {
    String read(String key);
    String readMethod();
    Integer readInt(String key);
}
