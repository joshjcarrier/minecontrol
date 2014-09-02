package com.joshjcarrier;

public class Pair<TKey, TValue> {
    private final TKey key;
    private final TValue value;

    public Pair(TKey key, TValue value) {
        this.key = key;
        this.value = value;
    }

    public TKey getKey() {
        return key;
    }

    public TValue getValue() {
        return value;
    }
}
