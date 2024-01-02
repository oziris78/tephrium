// Copyright 2020-2024 Oğuzhan Topaloğlu
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


package com.twistral.tephrium.utils;


import com.twistral.tephrium.core.TephriumException;

import java.util.*;



/**
 * Tephrium's replacement class for {@link Collections} with additional functionality.
 */
public final class TCollections {

    /* No Constructor */
    private TCollections(){}



    public static <T1, T2> HashMap<T1,T2> newHashMap(T1[] keys, T2[] values) {
        HashMap<T1, T2> newMap = new HashMap<>();
        try{
            if(keys.length != values.length)
                throw new TephriumException("Number of keys and values aren't equal");
            for(int i=0; i<keys.length; i++) newMap.put( keys[i], values[i] );
        }
        catch (Exception e) { e.printStackTrace(); }
        return newMap;
    }


    public static <T> ArrayList<T> newArrayList(T... entries) {
        ArrayList<T> newArrList = new ArrayList<>();
        for(int i=0; i<entries.length; i++) newArrList.add(entries[i]);
        return newArrList;
    }


    public static <T> LinkedList<T> newLinkedList(T... entries) {
        LinkedList<T> newLinkedList = new LinkedList<>();
        for(int i=0; i<entries.length; i++) newLinkedList.add(entries[i]);
        return newLinkedList;
    }


    public static <T> HashSet<T> newHashSet(T... entries) {
        HashSet<T> newHashSet = new HashSet<>();
        for(int i=0; i<entries.length; i++) newHashSet.add(entries[i]);
        return newHashSet;
    }



}
