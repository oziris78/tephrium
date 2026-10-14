

// Copyright 2024-2026 Oğuzhan Topaloğlu
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


import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;


/**
 * {@link BijectiveMap} (also known as 
 * <a href="https://en.wikipedia.org/wiki/Bidirectional_map">Bidirectional map</a>) is a 
 * special type of map that satisfies the following properties: <br>
 * <ol>
 * <li>- Every key in the map is unique, meaning no two keys are equal to each other</li>
 * <li>- Every value in the map is unique, meaning no two values are equal to each other</li>
 * </ol>
 *
 * These two properties make it so that, there are only 1-to-1 correspondence relations between
 * the keys and the values. This allows us to search by both keys and values in O(1) time. <br>
 *
 * NOTE: Methods of this class ensures that the map is always valid and consistent. Invalid/wrong
 * operations will be ignored. <br>
 *
 * @param <K> type of the keys
 * @param <V> type of the values
 */
public class BijectiveMap<K, V> {

    private final HashMap<K, V> mapKeyToValue;
    private final HashMap<V, K> mapValueToKey;


    public BijectiveMap(int initialCapacity, float loadFactor) {
        mapKeyToValue = new HashMap<>(initialCapacity, loadFactor);
        mapValueToKey = new HashMap<>(initialCapacity, loadFactor);
    }

    public BijectiveMap(int initialCapacity) {
        this(initialCapacity, 0.75f);
    }

    public BijectiveMap() {
        this(32);
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  METHODS  ///////////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/


    public void clear() { mapKeyToValue.clear(); mapValueToKey.clear(); }

    public boolean isEmpty() { return mapKeyToValue.isEmpty(); }
    public int size() { return mapKeyToValue.size(); }

    public boolean containsKey(K key) { return this.mapKeyToValue.containsKey(key); }
    public boolean containsValue(V value) { return this.mapValueToKey.containsKey(value); }


    /**
     * Returns the key corresponding to the given value. <br>
     *
     * NOTE: A return value of null does always mean that the value is unmapped in the map. It's
     * also possible that the value may be mapping to null itself. The
     * {@link BijectiveMap#containsKey(Object)} method can be used to separate these two cases.
     *
     * @param value any value to search
     * @return the corresponding key or null
     */
    public K getKey(V value) {
        return mapValueToKey.get(value);
    }


    /**
     * Returns the value corresponding to the given key. <br>
     *
     * NOTE: A return value of null does always mean that the key is unmapped in the map. It's
     * also possible that the key may be mapping to null itself. The
     * {@link BijectiveMap#containsValue(Object)} method can be used to separate these two cases.
     *
     * @param key any key to search
     * @return the corresponding value or null
     */
    public V getValue(K key) {
        return mapKeyToValue.get(key);
    }


    /**
     * Fail-safed version of {@link BijectiveMap#getKey(Object)}.
     *
     * @param value any value to search
     * @param defaultKey the default key to return if no mapping exists
     * @return key corresponding to the given value OR defaultKey if no mapping exists
     */
    public K getKey(V value, K defaultKey) {
        return this.containsValue(value) ? this.getKey(value) : defaultKey;
    }


    /**
     * Fail-safed version of {@link BijectiveMap#getValue(Object)}.
     *
     * @param key any key to search
     * @param defaultValue the default value to return if no mapping exists
     * @return value corresponding to the given key OR defaultValue if no mapping exists
     */
    public V getValue(K key, V defaultValue) {
        return this.containsKey(key) ? this.getValue(key) : defaultValue;
    }


    /**
     * Executes the given function for all keys.
     * @param keyFunc any function to consume a key
     */
    public void forEachKey(Consumer<K> keyFunc) {
        Objects.requireNonNull(keyFunc);

        final Set<K> keys = this.mapKeyToValue.keySet();
        keys.forEach(keyFunc);
    }


    /**
     * Executes the given function for all values.
     * @param valueFunc any function to consume a value
     */
    public void forEachValue(Consumer<V> valueFunc) {
        Objects.requireNonNull(valueFunc);

        final Set<V> values = this.mapValueToKey.keySet();
        values.forEach(valueFunc);
    }


    /**
     * Executes the given function for all entries.
     * @param entryFunc any function to consume an entry
     */
    public void forEachEntry(BiConsumer<K, V> entryFunc) {
        Objects.requireNonNull(entryFunc);

        final Set<K> keys = this.mapKeyToValue.keySet();
        for (K key : keys) {
            final V value = this.getValue(key);
            entryFunc.accept(key, value);
        }
    }


    /**
     * Tries to add a new entry to this map. If the given key or the given value already
     * exists in the map, it does nothing and returns false. This behavior is to ensure that
     * the map always stays valid and consistent.
     *
     * @param key any key to put in the map
     * @param value any value to put in the map
     * @return true if a mapping was added, false if it was rejected
     */
    public boolean put(K key, V value) {
        if (this.containsKey(key)) return false;
        if (this.containsValue(value)) return false;

        mapKeyToValue.put(key, value);
        mapValueToKey.put(value, key);
        return true;
    }


    /**
     * Replaces the mapped key for the given value with a new key. If no mapping is found
     * for the given value OR if the given new key is already mapped to a value, it does
     * nothing and returns false.
     *
     * @param value any value to search
     * @param newKey the new key to map to the given value
     * @return true if a replacement was made
     */
    public boolean replaceKey(V value, K newKey) {
        if (!this.containsValue(value)) return false; // no mapping was found
        if (this.containsKey(newKey)) return false; // prevent key collision

        final K oldKey = this.getKey(value);
        mapKeyToValue.remove(oldKey);
        mapKeyToValue.put(newKey, value);
        mapValueToKey.replace(value, newKey);
        return true;
    }


    /**
     * Replaces the mapped value for the given key with a new value. If no mapping is found
     * for the given key OR if the given new value is already mapped to a key, it does
     * nothing and returns false.
     *
     * @param key any key to search
     * @param newValue the new value to map to the given key
     * @return true if a replacement was made
     */
    public boolean replaceValue(K key, V newValue) {
        if (!this.containsKey(key)) return false; // no mapping was found
        if (this.containsValue(newValue)) return false; // prevent value collision

        final V oldValue = this.getValue(key);
        mapValueToKey.remove(oldValue);
        mapValueToKey.put(newValue, key);
        mapKeyToValue.replace(key, newValue);
        return true;
    }


    /**
     * Tries to remove the mapping that has the specified key. If no mapping
     * was found, it does nothing and returns false.
     *
     * @param key any key to search for a mapping
     * @return true if a removal was done, false if no mapping was found
     */
    public boolean removeByKey(K key) {
        if (!this.containsKey(key)) return false; // no mapping was found

        final V value = this.getValue(key);
        mapKeyToValue.remove(key);
        mapValueToKey.remove(value);
        return true;
    }


    /**
     * Tries to remove the mapping that has the specified value. If no mapping
     * was found, it does nothing and returns false.
     *
     * @param value any value to search for a mapping
     * @return true if a removal was done, false if no mapping was found
     */
    public boolean removeByValue(V value) {
        if (!this.containsValue(value)) return false; // no mapping was found

        final K key = this.getKey(value);
        mapKeyToValue.remove(key);
        mapValueToKey.remove(value);
        return true;
    }


}


