// Copyright 2024-2025 Oğuzhan Topaloğlu
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.



package com.twistral.tephrium.collections;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;


/**
 * {@link NoCollisionMap} is a type of map that satisfies the following properties: <br>
 * <ol>
 * <li>- Every key in the map is unique, meaning no two keys are equal to each other</li>
 * <li>- Every value in the map is unique, meaning no two value are equal to each other</li>
 * </ol>
 * These two properties make it so that, there are only 1-to-1 correspondence relations between
 * the keys and the values. This allows us to search by both keys and values in O(1) time.
 * @param <K> type of the keys
 * @param <V> type of the values
 */
public class NoCollisionMap<K, V> {

    private final HashMap<K, V> mapKeyToValue;
    private final HashMap<V, K> mapValueToKey;


    /*//////////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  CONSTRUCTORS  ///////////////////////////*/
    /*//////////////////////////////////////////////////////////////////////*/


    public NoCollisionMap(int initialCapacity, float loadFactor) {
        mapKeyToValue = new HashMap<>(initialCapacity, loadFactor);
        mapValueToKey = new HashMap<>(initialCapacity, loadFactor);
    }

    public NoCollisionMap(int initialCapacity) {
        this(initialCapacity, 0.75f);
    }

    public NoCollisionMap() {
        // Unlike Java's Hashmap, this class uses 32 instead of 16
        this(32, 0.75f);
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  METHODS  ///////////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/


    public void clear() {
        mapKeyToValue.clear();
        mapValueToKey.clear();
    }


    public boolean isEmpty() {
        return mapKeyToValue.isEmpty() && mapValueToKey.isEmpty();
    }


    public int size() {
        return mapKeyToValue.size();
    }


    /**
     * Checks if the specified key exists in the map.
     * @param key any key to search
     * @return true if the key exists in the map (both ways, key <-> value)
     */
    public boolean containsKey(K key) {
        if(!mapKeyToValue.containsKey(key))
            return false;

        final V respondingValue = mapKeyToValue.get(key);
        if(!mapValueToKey.containsKey(respondingValue))
            return false;

        return true;
    }


    /**
     * Checks if the specified value exists in the map.
     * @param value any value to search
     * @return true if the value exists in the map (both ways, key <-> value)
     */
    public boolean containsValue(V value) {
        if(!mapValueToKey.containsKey(value))
            return false;

        final K respondingKey = mapValueToKey.get(value);
        if(!mapKeyToValue.containsKey(respondingKey))
            return false;

        return true;
    }


    public Set<K> keySet() {
        return mapKeyToValue.keySet();
    }


    public Set<V> valueSet() {
        return mapValueToKey.keySet();
    }


    public Set<Map.Entry<K, V>> entrySet() {
        return mapKeyToValue.entrySet();
    }


    public void forEach(BiConsumer<K, V> function) {
        mapKeyToValue.entrySet().forEach(entry -> function.accept(entry.getKey(), entry.getValue()));
    }


    /**
     * Returns the key corresponding to the given value.
     * @param value any value to search
     * @return the key corresponding to the given value or <b>null</b> if theres no correspondence
     */
    public K getKey(final V value) {
        return mapValueToKey.get(value); // return key or null
    }


    /**
     * Returns the value corresponding to the given key.
     * @param key any key to search
     * @return the value corresponding to the given value or <b>null</b> if theres no correspondence
     */
    public V getValue(final K key) {
        return mapKeyToValue.get(key); // return value or null
    }


    /**
     * Tries to create a new entry in the map. If the given key or the
     * given value already exists in the map, it does nothing and returns false.
     * @param key any key to put in the map
     * @param value any value to put in the map
     * @return true if key and value both dont already exist in the map
     */
    public boolean put(final K key, final V value) {
        if(this.containsKey(key) || this.containsValue(value))
            return false;

        mapKeyToValue.put(key, value);
        mapValueToKey.put(value, key);
        return true;
    }


    /**
     * Returns the key corresponding to the given value.
     * @param value any value to search
     * @param defaultKey any key to return when a collision happens
     * @return the key corresponding to the given value or <b>defaultKey</b> if theres no correspondence
     */
    public K getKeyOrDefault(final V value, final K defaultKey) {
        K key = this.getKey(value);
        return (key == null) ? defaultKey : key;
    }


    /**
     * Returns the value corresponding to the given key.
     * @param key any key to search
     * @param defaultValue any value to return when a collision happens
     * @return the value corresponding to the given key or <b>defaultValue</b> if theres no correspondence
     */
    public V getValueOrDefault(final K key, final V defaultValue) {
        V value = this.getValue(key);
        return (value == null) ? defaultValue : value;
    }


    public boolean removeByKey(final K key) {
        if(!this.containsKey(key))
            return false;

        final V value = this.getValue(key);
        return mapKeyToValue.remove(key, value) && mapValueToKey.remove(value, key);
    }


    public boolean removeByValue(final V value) {
        if(!this.containsValue(value))
            return false;

        final K key = this.getKey(value);
        return mapKeyToValue.remove(key, value) && mapValueToKey.remove(value, key);
    }


    public boolean replaceValue(final K key, final V newValue) {
        if(!this.containsKey(key)) return false;
        if(!this.removeByKey(key)) return false;

        return this.put(key, newValue);
    }


    public boolean replaceKey(final V value, final K newKey) {
        if(!this.containsValue(value)) return false;
        if(!this.removeByValue(value)) return false;

        return this.put(newKey, value);
    }


}

