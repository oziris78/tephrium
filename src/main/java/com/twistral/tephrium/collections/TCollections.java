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


package com.twistral.tephrium.collections;


import java.util.*;



public final class TCollections {


    // No Constructor
    private TCollections() {}


    /////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////  COLLECTION CREATION  /////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////


    public static <T> List<T> newArrayList(int initialCapacity, T... entries) {
        ArrayList<T> res = new ArrayList<>(initialCapacity);
        for (int i = 0; i < entries.length; i++) res.add(entries[i]);
        return res;
    }

    public static <T> List<T> newArrayList(T... entries) {
        return newArrayList(10*2, entries); // default 10 is too small imo
    }

    // ------------------- //

    public static <T> List<T> newLinkedList(T... entries) {
        LinkedList<T> res = new LinkedList<>();
        for (int i = 0; i < entries.length; i++) res.add(entries[i]);
        return res;
    }

    // ------------------- //

    public static <T> Set<T> newHashSet(int initialCapacity, T... entries) {
        HashSet<T> res = new HashSet<>(initialCapacity);
        for (int i = 0; i < entries.length; i++) res.add(entries[i]);
        return res;
    }

    public static <T> Set<T> newHashSet(T... entries) { return newHashSet(16, entries); }


}
