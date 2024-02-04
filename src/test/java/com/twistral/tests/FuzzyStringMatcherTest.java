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


package com.twistral.tests;

import com.twistral.tephrium.stats.DataDescription;
import com.twistral.tephrium.strings.FuzzyStringMatcher;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

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

        assertEquals(fsm.getClosestWords(wordList, "barana")[0], "banana");
        assertEquals(fsm.getClosestWords(wordList, "oranj")[0], "orange");
        assertEquals(fsm.getClosestWords(wordList, "angele")[0], "angel");
        assertEquals(fsm.getClosestWords(wordList, "lablue")[0], "blue");
        assertEquals(fsm.getClosestWords(wordList, "kistanse")[0], "distance");
        assertEquals(fsm.getClosestWords(wordList, "terrified")[0], "horrified");
    }



    @Test
    @DisplayName("apacheVsTephriumBenchmark")
    void apacheVsTephriumBenchmark() {
        LevenshteinDistance ld = new LevenshteinDistance();
        FuzzyStringMatcher fsm = new FuzzyStringMatcher(200);

        Random random = new Random();
        String[] randomWords = new String[250_000];
        for (int i = 0; i < randomWords.length; i++) {
            randomWords[i] = random.ints(48, 123)
                    .filter(num -> (num<58 || num>64) && (num<91 || num>96))
                    .limit(3 + random.nextInt(100))
                    .mapToObj(c -> (char)c).collect(StringBuffer::new, StringBuffer::append, StringBuffer::append)
                    .toString();
        }

        double[] timeTephrium = new double[randomWords.length-1];
        double[] timeApache = new double[randomWords.length-1];
        int tephriumIndex = 0;
        int apacheIndex = 0;

        for (int i = 0; i < randomWords.length-1; i++) {
            String left = randomWords[i];
            String right = randomWords[i+1];

            long start, end;
            start = System.nanoTime();
            int tephriumResult = fsm.levenshteinDistance(left, right);
            end = System.nanoTime();
            timeTephrium[tephriumIndex++] = end - start;

            start = System.nanoTime();
            int apacheResult = ld.apply(left, right);
            end = System.nanoTime();
            timeApache[apacheIndex++] = end - start;

            Assertions.assertEquals(tephriumResult, apacheResult);
        }

        Arrays.sort(timeTephrium);
        Arrays.sort(timeApache);
        DataDescription dsTe = new DataDescription(timeTephrium);
        DataDescription dsAp = new DataDescription(timeApache);

        System.out.println("Tephrium: ");
        System.out.println("Min: " + dsTe.min + " ns");
        System.out.println("Max: " + dsTe.max + " ns");
        System.out.println("Mean: " + dsTe.mean + " ns");
        System.out.println("Median: " + dsTe.median + " ns");
        System.out.println("Variance: " + dsTe.variance + " ns");
        System.out.println();

        System.out.println("Apache: ");
        System.out.println("Min: " + dsAp.min + " ns");
        System.out.println("Max: " + dsAp.max + " ns");
        System.out.println("Mean: " + dsAp.mean + " ns");
        System.out.println("Median: " + dsAp.median + " ns");
        System.out.println("Variance: " + dsAp.variance + " ns");
        System.out.println();

        BiFunction<Double, Double, String> getWinner = (ap, tep) -> {
            if(ap == tep) return "They are the same";
            if(ap < tep) return "Apache is better!";
            return "Tephrium is better!";
        };

        System.out.printf("Results for %d words:\n", randomWords.length);
        System.out.println("Min: " + getWinner.apply(dsAp.min, dsTe.min));
        System.out.println("Max: " + getWinner.apply(dsAp.max, dsTe.max));
        System.out.println("Mean: " + getWinner.apply(dsAp.mean, dsTe.mean));
        System.out.println("Median: " + getWinner.apply(dsAp.median, dsTe.median));
        System.out.println("Variance: " + getWinner.apply(dsAp.variance, dsTe.variance));
        System.out.println();

    }




}
