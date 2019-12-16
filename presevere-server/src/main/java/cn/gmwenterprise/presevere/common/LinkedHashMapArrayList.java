package cn.gmwenterprise.presevere.common;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LinkedHashMapArrayList<Key, Value> extends LinkedHashMap<Key, ArrayList<Value>> {

    private LinkedHashMapArrayList<Key, Value> instance;

    public LinkedHashMapArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    public LinkedHashMapArrayList() {
    }

    public void set(Key key, Value value) {
        if (containsKey(key)) {
            get(key).add(value);
        } else {
            ArrayList<Value> list = new ArrayList<>();
            list.add(value);
            put(key, list);
        }
    }

    public OfKey ofKey(Key key) {
        return new OfKey(key);
    }

    public class OfKey {
        private Key key;

        OfKey(Key key) {
            this.key = key;
        }

        public OfKey put(Value value) {
            LinkedHashMapArrayList.this.set(key, value);
            return this;
        }
    }
}
