
// Copyright 2024 Oğuzhan Topaloğlu
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


import com.twistral.tephrium.collections.NoCollisionMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class NoCollisionMapTest {

    @Test
    @DisplayName("noCollisionMapTest")
    void noCollisionMapTest() {
        NoCollisionMap<String, Integer> map = new NoCollisionMap<>();
        assertTrue(map.put("Key1", 1));
        assertTrue(map.put("Key2", 2));

        assertEquals(1, map.getValue("Key1"));
        assertEquals(2, map.getValue("Key2"));

        assertEquals("Key1", map.getKey(1));
        assertEquals("Key2", map.getKey(2));

        map.clear();
        assertTrue(map.put("Key1", 1));
        assertTrue(map.put("Key2", 2));

        assertTrue(map.removeByKey("Key1"));
        assertFalse(map.containsKey("Key1"));
        assertFalse(map.containsValue(1));

        assertEquals("Key2", map.getKey(2));
        assertEquals(2, map.getValue("Key2"));

        map.clear();
        assertTrue(map.put("Key1", 1));
        assertTrue(map.put("Key2", 2));

        assertTrue(map.removeByValue(1));
        assertFalse(map.containsKey("Key1"));
        assertFalse(map.containsValue(1));

        assertEquals("Key2", map.getKey(2));
        assertEquals(2, map.getValue("Key2"));

        map.clear();
        assertTrue(map.put("Key1", 1));
        assertTrue(map.put("Key2", 2));

        assertTrue(map.replaceKey(1, "NewKey1"));
        assertFalse(map.containsKey("Key1"));
        assertTrue(map.containsValue(1));

        assertEquals("NewKey1", map.getKey(1));
        assertEquals(2, map.getValue("Key2"));

        map.clear();
        assertTrue(map.put("Key1", 1));
        assertTrue(map.put("Key2", 2));

        assertTrue(map.replaceValue("Key1", 10));
        assertTrue(map.containsKey("Key1"));
        assertFalse(map.containsValue(1));

        assertEquals(10, map.getValue("Key1"));
        assertEquals("Key2", map.getKey(2));

        map.clear();
        assertTrue(map.isEmpty());
        map.put("Key1", 1);
        assertFalse(map.isEmpty());
        map.removeByKey("Key1");
        assertTrue(map.isEmpty());

        map.clear();
        assertEquals(0, map.size());
        map.put("Key1", 1);
        assertEquals(1, map.size());
        map.put("Key2", 2);
        assertEquals(2, map.size());
        map.removeByKey("Key1");
        assertEquals(1, map.size());

        map.clear();
        map.put("Key1", 1);
        map.put("Key2", 2);
        map.put("Key3", 3);

        map.keySet().forEach(str -> assertTrue(str.matches("Key[0-9]")));
        map.valueSet().forEach(i -> assertTrue(0 < i && i < 4));
        map.entrySet().forEach(entry -> {
            assertTrue(entry.getKey().matches("Key[0-9]"));
            int i = entry.getValue();
            assertTrue(0 < i && i < 4);
        });

        map.clear();
        map.put("Key1", 1);
        map.put("Key2", 2);
        map.put("Key3", 57);
        map.put("Key6", 20);
        map.put("Key45", -20);

        int[] sum = new int[1];
        StringBuilder sb = new StringBuilder();
        map.forEach((k, v) -> {
            sum[0] += v;
            assertTrue(k.startsWith("Key"));
        });
        assertTrue(sum[0] == 60);

        map.clear();
        assertTrue(map.put("Key1", 1));
        assertTrue(map.put("Key2", 2));

        assertEquals("Key1", map.getKeyOrDefault(1, "DefaultKey"));
        assertEquals("Key2", map.getKeyOrDefault(2, "DefaultKey"));
        assertEquals("DefaultKey", map.getKeyOrDefault(3, "DefaultKey"));

        map.clear();
        assertTrue(map.put("Key1", 1));
        assertTrue(map.put("Key2", 2));

        assertEquals(1, map.getValueOrDefault("Key1", 10));
        assertEquals(2, map.getValueOrDefault("Key2", 10));
        assertEquals(10, map.getValueOrDefault("Key3", 10));

        map.clear();
        assertTrue(map.put("Key1", 1));
        assertFalse(map.put("Key1", 1));
        assertFalse(map.put("Key1", 10250));
        assertFalse(map.put("Key123124", 1));
    }

}
