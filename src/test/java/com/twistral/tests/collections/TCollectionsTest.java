
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



package com.twistral.tests.collections;


import com.twistral.tephrium.collections.TCollections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


public class TCollectionsTest {

    @Test
    @DisplayName("newHashMapTest")
    void newHashMapTest() {
        HashMap<Integer, String> map0 = TCollections.newHashMap(
                new Integer[] {0, 1, 2, 3},
                "a", "b", "c", "d123124125412"
        );

        HashMap<Integer, String> map1 = TCollections.newHashMap(
                new Integer[] {0, 1, 2, 3},
                "a", "b", "c", "d"
        );

        HashMap<Integer, String> map2 = new HashMap<>();
        map2.put(0, "a");
        map2.put(1, "b");
        map2.put(2, "c");
        map2.put(3, "d");

        assertNotEquals(map1, map0);
        assertNotEquals(map2, map0);
        assertEquals(map1, map2);
    }


    @Test
    @DisplayName("newArrayListTest")
    void newArrayListTest() {
        ArrayList<Integer> list0 = TCollections.newArrayList(1, 2, 3, 4, 5125);
        ArrayList<Integer> list1 = TCollections.newArrayList(1, 2, 3, 4, 5);

        ArrayList<Integer> list2 = new ArrayList<>();
        for(int i = 1; i <= 5; i++) list2.add(i);

        assertEquals(5, list1.size());
        assertTrue(list1.contains(1));
        assertTrue(list1.contains(2));
        assertTrue(list1.contains(3));
        assertTrue(list1.contains(4));
        assertTrue(list1.contains(5));

        assertNotEquals(list1, list0);
        assertNotEquals(list2, list0);
        assertEquals(list1, list2);
    }


    @Test
    @DisplayName("newLinkedListTest")
    void newLinkedListTest() {
        LinkedList<String> list0 = TCollections.newLinkedList("apple", "bananagdhk", "cherry");
        LinkedList<String> list1 = TCollections.newLinkedList("apple", "banana", "cherry");

        LinkedList<String> list2 = new LinkedList<>();
        list2.add("apple");
        list2.add("banana");
        list2.add("cherry");

        assertEquals(3, list1.size());
        assertTrue(list1.contains("apple"));
        assertTrue(list1.contains("banana"));
        assertTrue(list1.contains("cherry"));

        assertNotEquals(list1, list0);
        assertNotEquals(list2, list0);
        assertEquals(list1, list2);
    }


    @Test
    @DisplayName("newHashSetTest")
    void newHashSetTest() {
        HashSet<Character> set0 = TCollections.newHashSet('a', 'b', 'c', 'd', '?');
        HashSet<Character> set1 = TCollections.newHashSet('a', 'b', 'c', 'd', 'e');

        HashSet<Character> set2 = new HashSet<>();
        set2.add('a');
        set2.add('b');
        set2.add('c');
        set2.add('d');
        set2.add('e');

        assertEquals(5, set1.size());
        assertTrue(set1.contains('a'));
        assertTrue(set1.contains('b'));
        assertTrue(set1.contains('c'));
        assertTrue(set1.contains('d'));
        assertTrue(set1.contains('e'));

        assertNotEquals(set1, set0);
        assertNotEquals(set2, set0);
        assertEquals(set1, set2);
    }

}
