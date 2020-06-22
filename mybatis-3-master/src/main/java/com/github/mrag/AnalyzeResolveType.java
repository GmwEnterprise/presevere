package com.github.mrag;

import java.util.Map;

public class AnalyzeResolveType {
    SubClassA<Long> sa = new SubClassA<>();
}

class ClassA<K, V> {
    protected Map<K, V> map;

    public Map<K, V> getMap() {
        return map;
    }

    public ClassA<K, V> setMap(Map<K, V> map) {
        this.map = map;
        return this;
    }
}

class SubClassA<T> extends ClassA<T, T> {
}