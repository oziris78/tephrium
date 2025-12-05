
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



package com.twistral.tests.strings;


import com.twistral.tephrium.collections.TCollections;
import com.twistral.tephrium.strings.FuzzyStringMatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FuzzyStringMatcherTest {


    @Test
    @DisplayName("levenFunc")
    void levenFunc() {
        FuzzyStringMatcher fsm = new FuzzyStringMatcher(1);
        assertEquals(fsm.levenshteinDistance("apple", "fapple"), 1);
        assertEquals(fsm.levenshteinDistance("", "fapple"), 6);
        assertEquals(fsm.levenshteinDistance("apple", ""), 5);
        assertEquals(fsm.levenshteinDistance("apple", "apple"), 0);
        assertEquals(fsm.levenshteinDistance("apple", "appfe"), 1);

        fsm = new FuzzyStringMatcher(1000);
        assertEquals(fsm.levenshteinDistance("abcdklmnoprs", "klmnoprsabcd"), 8);
        assertEquals(fsm.levenshteinDistance("foo", "bar"), 3);
        assertEquals(fsm.levenshteinDistance("foo", null), Integer.MAX_VALUE);
        assertEquals(fsm.levenshteinDistance(null, null), Integer.MAX_VALUE);
        assertEquals(fsm.levenshteinDistance(null, "null"), Integer.MAX_VALUE);
    }


    @Test
    @DisplayName("closestWords")
    void closestWords() {
        FuzzyStringMatcher fsm = new FuzzyStringMatcher();

        String[] wordList = new String[] {"banana", "baba", "orange", "range", "angel",
                "gelatin", "blue", "horrified", "distance" };

        List<String> words = TCollections.newArrayList("banana", "horrified", "range",
                "orange", "angel", "distance", "gelatin", "blue", "baba");

        assertEquals(fsm.getClosestWords(wordList, "barana")[0], "banana");
        assertEquals(fsm.getClosestWords(wordList, "oranj")[0], "orange");
        assertEquals(fsm.getClosestWords(wordList, "angele")[0], "angel");
        assertEquals(fsm.getClosestWords(wordList, "lablue")[0], "blue");
        assertEquals(fsm.getClosestWords(wordList, "kistanse")[0], "distance");
        assertEquals(fsm.getClosestWords(wordList, "terrified")[0], "horrified");

        assertEquals(fsm.getClosestWords(words, "barana")[0], "banana");
        assertEquals(fsm.getClosestWords(words, "oranj")[0], "orange");
        assertEquals(fsm.getClosestWords(words, "angele")[0], "angel");
        assertEquals(fsm.getClosestWords(words, "lablue")[0], "blue");
        assertEquals(fsm.getClosestWords(words, "kistanse")[0], "distance");
        assertEquals(fsm.getClosestWords(words, "terrified")[0], "horrified");

        assertEquals(fsm.getClosestWords(words.stream(), "barana", 2, Integer.MAX_VALUE)[0], "banana");
        assertEquals(fsm.getClosestWords(words.stream(), "oranj", 3, Integer.MAX_VALUE)[0], "orange");
        assertEquals(fsm.getClosestWords(words.stream(), "angele", 4, Integer.MAX_VALUE)[0], "angel");
        assertEquals(fsm.getClosestWords(words.stream(), "lablue", 5, Integer.MAX_VALUE)[0], "blue");
        assertEquals(fsm.getClosestWords(words.stream(), "kistanse", 6, Integer.MAX_VALUE)[0], "distance");
        assertEquals(fsm.getClosestWords(words.stream(), "terrified", 7, Integer.MAX_VALUE)[0], "horrified");
    }


}
