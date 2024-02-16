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


package com.twistral.tests.stats;

import com.twistral.tephrium.core.functions.*;
import com.twistral.tephrium.stats.*;
import org.junit.jupiter.api.*;
import java.util.*;
import static com.twistral.tests.TephriumTestFramework.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class FreqDistTableTest {

    @Test
    @DisplayName("freqTableGenericTest")
    void freqTableGenericTest() {

        /*
        Sorted Data:
        158, 160, 162, 165, 166, 167, 170, 170, 171, 172, 173, 176,
        176, 176, 176, 177, 178, 180, 180, 180, 182, 183, 188, 190, 192, 203

        n = 26, range = 45, k = 6, c = ceil(45/6) = 8

        Expected Results:
           ci       xi     freq             relFreq                 incCumFreq       incRelFreq
        [158-166)    162      4         4/26 (0.153846153846 )           4                0.153846153846
        [166-174)    170      7         7/26 (0.269230769231 )           11               0.423076923077
        [174-182)    178      9         9/26 (0.346153846154 )           20               0.769230769231
        [182-190)    186      3         3/26 (0.115384615385 )           23               0.884615384616
        [190-198)    194      2         2/26 (0.0769230769231)           25               0.961538461539
        [198-206)    202      1         1/26 (0.0384615384615)           26               1

        */


        // PREPERATION OF DATA
        Person[] population = Person.createPopulation();
        double[] heights = new double[population.length];
        for (int i = 0; i < population.length; i++) {
            heights[i] = population[i].height;
        }
        Arrays.sort(heights);
        FrequencyDistTable table = new FrequencyDistTable(heights, 6);

        // FOR VISUALIZATION:
        if(PRINT_VISUALS)
            System.out.println(table);

        // TESTS

        assertTrue(TMath.equalsd(table.getIntervalLeft(0), 158));
        assertTrue(TMath.equalsd(table.getIntervalLeft(1), 166));
        assertTrue(TMath.equalsd(table.getIntervalLeft(2), 174));
        assertTrue(TMath.equalsd(table.getIntervalLeft(3), 182));
        assertTrue(TMath.equalsd(table.getIntervalLeft(4), 190));
        assertTrue(TMath.equalsd(table.getIntervalLeft(5), 198));

        assertTrue(TMath.equalsd(table.getIntervalRight(0), 166));
        assertTrue(TMath.equalsd(table.getIntervalRight(1), 174));
        assertTrue(TMath.equalsd(table.getIntervalRight(2), 182));
        assertTrue(TMath.equalsd(table.getIntervalRight(3), 190));
        assertTrue(TMath.equalsd(table.getIntervalRight(4), 198));
        assertTrue(TMath.equalsd(table.getIntervalRight(5), 206));

        assertTrue(TMath.equalsd(table.getMidpoint(0), 162));
        assertTrue(TMath.equalsd(table.getMidpoint(1), 170));
        assertTrue(TMath.equalsd(table.getMidpoint(2), 178));
        assertTrue(TMath.equalsd(table.getMidpoint(3), 186));
        assertTrue(TMath.equalsd(table.getMidpoint(4), 194));
        assertTrue(TMath.equalsd(table.getMidpoint(5), 202));

        assertTrue(TMath.equalsd(table.getFrequency(0), 4));
        assertTrue(TMath.equalsd(table.getFrequency(1), 7));
        assertTrue(TMath.equalsd(table.getFrequency(2), 9));
        assertTrue(TMath.equalsd(table.getFrequency(3), 3));
        assertTrue(TMath.equalsd(table.getFrequency(4), 2));
        assertTrue(TMath.equalsd(table.getFrequency(5), 1));

        assertTrue(TMath.equalsd(table.getRelativeFreq(0), 0.153846153846));
        assertTrue(TMath.equalsd(table.getRelativeFreq(1), 0.269230769231));
        assertTrue(TMath.equalsd(table.getRelativeFreq(2), 0.346153846154));
        assertTrue(TMath.equalsd(table.getRelativeFreq(3), 0.115384615385));
        assertTrue(TMath.equalsd(table.getRelativeFreq(4), 0.0769230769231));
        assertTrue(TMath.equalsd(table.getRelativeFreq(5), 0.0384615384615));

        assertTrue(TMath.equalsd(table.getIncCumFreq(0), 4));
        assertTrue(TMath.equalsd(table.getIncCumFreq(1), 11));
        assertTrue(TMath.equalsd(table.getIncCumFreq(2), 20));
        assertTrue(TMath.equalsd(table.getIncCumFreq(3), 23));
        assertTrue(TMath.equalsd(table.getIncCumFreq(4), 25));
        assertTrue(TMath.equalsd(table.getIncCumFreq(5), 26));

        assertTrue(TMath.equalsd(table.getIncRelFreq(0), 0.153846153846));
        assertTrue(TMath.equalsd(table.getIncRelFreq(1), 0.423076923077));
        assertTrue(TMath.equalsd(table.getIncRelFreq(2), 0.769230769231));
        assertTrue(TMath.equalsd(table.getIncRelFreq(3), 0.884615384616));
        assertTrue(TMath.equalsd(table.getIncRelFreq(4), 0.961538461539));
        assertTrue(TMath.equalsd(table.getIncRelFreq(5), 1));

    }


    @Test
    @DisplayName("freqTablePrimiteWithoutConversionTest")
    void freqTablePrimiteWithoutConversionTest() {
        double[] arr2 = new double[]{
                160, 172, 162, 176, 180, 176, 182, 176, 176, 166, 158, 183, 165, 188,
                177, 178, 170, 180, 170, 180, 190, 173, 192, 167, 203, 171
        };

        FrequencyDistTable table = new FrequencyDistTable(arr2, 6);
        assertTrue(TMath.equalsd(table.getIntervalLeft(0), 158));
        assertTrue(TMath.equalsd(table.getIntervalLeft(1), 166));
        assertTrue(TMath.equalsd(table.getIntervalLeft(2), 174));
        assertTrue(TMath.equalsd(table.getIntervalLeft(3), 182));
        assertTrue(TMath.equalsd(table.getIntervalLeft(4), 190));
        assertTrue(TMath.equalsd(table.getIntervalLeft(5), 198));
        assertTrue(TMath.equalsd(table.getIntervalRight(0), 166));
        assertTrue(TMath.equalsd(table.getIntervalRight(1), 174));
        assertTrue(TMath.equalsd(table.getIntervalRight(2), 182));
        assertTrue(TMath.equalsd(table.getIntervalRight(3), 190));
        assertTrue(TMath.equalsd(table.getIntervalRight(4), 198));
        assertTrue(TMath.equalsd(table.getIntervalRight(5), 206));
        assertTrue(TMath.equalsd(table.getMidpoint(0), 162));
        assertTrue(TMath.equalsd(table.getMidpoint(1), 170));
        assertTrue(TMath.equalsd(table.getMidpoint(2), 178));
        assertTrue(TMath.equalsd(table.getMidpoint(3), 186));
        assertTrue(TMath.equalsd(table.getMidpoint(4), 194));
        assertTrue(TMath.equalsd(table.getMidpoint(5), 202));

        System.out.println(table.getFrequency(0));
        assertTrue(TMath.equalsd(table.getFrequency(0), 4));

        assertTrue(TMath.equalsd(table.getFrequency(1), 7));
        assertTrue(TMath.equalsd(table.getFrequency(2), 9));
        assertTrue(TMath.equalsd(table.getFrequency(3), 3));
        assertTrue(TMath.equalsd(table.getFrequency(4), 2));
        assertTrue(TMath.equalsd(table.getFrequency(5), 1));
        assertTrue(TMath.equalsd(table.getRelativeFreq(0), 0.153846153846));
        assertTrue(TMath.equalsd(table.getRelativeFreq(1), 0.269230769231));
        assertTrue(TMath.equalsd(table.getRelativeFreq(2), 0.346153846154));
        assertTrue(TMath.equalsd(table.getRelativeFreq(3), 0.115384615385));
        assertTrue(TMath.equalsd(table.getRelativeFreq(4), 0.0769230769231));
        assertTrue(TMath.equalsd(table.getRelativeFreq(5), 0.0384615384615));
        assertTrue(TMath.equalsd(table.getIncCumFreq(0), 4));
        assertTrue(TMath.equalsd(table.getIncCumFreq(1), 11));
        assertTrue(TMath.equalsd(table.getIncCumFreq(2), 20));
        assertTrue(TMath.equalsd(table.getIncCumFreq(3), 23));
        assertTrue(TMath.equalsd(table.getIncCumFreq(4), 25));
        assertTrue(TMath.equalsd(table.getIncCumFreq(5), 26));
        assertTrue(TMath.equalsd(table.getIncRelFreq(0), 0.153846153846));
        assertTrue(TMath.equalsd(table.getIncRelFreq(1), 0.423076923077));
        assertTrue(TMath.equalsd(table.getIncRelFreq(2), 0.769230769231));
        assertTrue(TMath.equalsd(table.getIncRelFreq(3), 0.884615384616));
        assertTrue(TMath.equalsd(table.getIncRelFreq(4), 0.961538461539));
        assertTrue(TMath.equalsd(table.getIncRelFreq(5), 1));
    }


    @Test
    @DisplayName("freqTablePrimiteWithConversionTest")
    void freqTablePrimiteWithConversionTest() {

            double[] heights = new double[]{
                    160, 172, 162, 176, 180, 176, 182, 176, 176, 166, 158, 183, 165, 188,
                    177, 178, 170, 180, 170, 180, 190, 173, 192, 167, 203, 171
            };
            FrequencyDistTable freqDistTable = new FrequencyDistTable(heights, 6);

            // TESTS
            assertTrue(TMath.equalsd(freqDistTable.getIntervalLeft(0), 158));
            assertTrue(TMath.equalsd(freqDistTable.getIntervalLeft(1), 166));
            assertTrue(TMath.equalsd(freqDistTable.getIntervalLeft(2), 174));
            assertTrue(TMath.equalsd(freqDistTable.getIntervalLeft(3), 182));
            assertTrue(TMath.equalsd(freqDistTable.getIntervalLeft(4), 190));
            assertTrue(TMath.equalsd(freqDistTable.getIntervalLeft(5), 198));

            assertTrue(TMath.equalsd(freqDistTable.getIntervalRight(0), 166));
            assertTrue(TMath.equalsd(freqDistTable.getIntervalRight(1), 174));
            assertTrue(TMath.equalsd(freqDistTable.getIntervalRight(2), 182));
            assertTrue(TMath.equalsd(freqDistTable.getIntervalRight(3), 190));
            assertTrue(TMath.equalsd(freqDistTable.getIntervalRight(4), 198));
            assertTrue(TMath.equalsd(freqDistTable.getIntervalRight(5), 206));

            assertTrue(TMath.equalsd(freqDistTable.getMidpoint(0), 162));
            assertTrue(TMath.equalsd(freqDistTable.getMidpoint(1), 170));
            assertTrue(TMath.equalsd(freqDistTable.getMidpoint(2), 178));
            assertTrue(TMath.equalsd(freqDistTable.getMidpoint(3), 186));
            assertTrue(TMath.equalsd(freqDistTable.getMidpoint(4), 194));
            assertTrue(TMath.equalsd(freqDistTable.getMidpoint(5), 202));

            assertTrue(TMath.equalsd(freqDistTable.getFrequency(0), 4));
            assertTrue(TMath.equalsd(freqDistTable.getFrequency(1), 7));
            assertTrue(TMath.equalsd(freqDistTable.getFrequency(2), 9));
            assertTrue(TMath.equalsd(freqDistTable.getFrequency(3), 3));
            assertTrue(TMath.equalsd(freqDistTable.getFrequency(4), 2));
            assertTrue(TMath.equalsd(freqDistTable.getFrequency(5), 1));

            assertTrue(TMath.equalsd(freqDistTable.getRelativeFreq(0), 0.153846153846));
            assertTrue(TMath.equalsd(freqDistTable.getRelativeFreq(1), 0.269230769231));
            assertTrue(TMath.equalsd(freqDistTable.getRelativeFreq(2), 0.346153846154));
            assertTrue(TMath.equalsd(freqDistTable.getRelativeFreq(3), 0.115384615385));
            assertTrue(TMath.equalsd(freqDistTable.getRelativeFreq(4), 0.0769230769231));
            assertTrue(TMath.equalsd(freqDistTable.getRelativeFreq(5), 0.0384615384615));

            assertTrue(TMath.equalsd(freqDistTable.getIncCumFreq(0), 4));
            assertTrue(TMath.equalsd(freqDistTable.getIncCumFreq(1), 11));
            assertTrue(TMath.equalsd(freqDistTable.getIncCumFreq(2), 20));
            assertTrue(TMath.equalsd(freqDistTable.getIncCumFreq(3), 23));
            assertTrue(TMath.equalsd(freqDistTable.getIncCumFreq(4), 25));
            assertTrue(TMath.equalsd(freqDistTable.getIncCumFreq(5), 26));

            assertTrue(TMath.equalsd(freqDistTable.getIncRelFreq(0), 0.153846153846));
            assertTrue(TMath.equalsd(freqDistTable.getIncRelFreq(1), 0.423076923077));
            assertTrue(TMath.equalsd(freqDistTable.getIncRelFreq(2), 0.769230769231));
            assertTrue(TMath.equalsd(freqDistTable.getIncRelFreq(3), 0.884615384616));
            assertTrue(TMath.equalsd(freqDistTable.getIncRelFreq(4), 0.961538461539));
            assertTrue(TMath.equalsd(freqDistTable.getIncRelFreq(5), 1));

    }


    @Test
    @DisplayName("freqTableWithKnownFreqsTest")
    void freqTableWithKnownFreqsTest() {
        FrequencyDistTable table2 = new FrequencyDistTable(new double[]{ 94, 93, 112, 101, 104, 95, 100, 99, 108, 94 }, 0d, 1d);

        ArrayList<Double> terms = new ArrayList<>();
        for (int i = 0; i < 94; i++) terms.add(0d);
        for (int i = 0; i < 93; i++) terms.add(1d);
        for (int i = 0; i < 112; i++) terms.add(2d);
        for (int i = 0; i < 101; i++) terms.add(3d);
        for (int i = 0; i < 104; i++) terms.add(4d);
        for (int i = 0; i < 95; i++) terms.add(5d);
        for (int i = 0; i < 100; i++) terms.add(6d);
        for (int i = 0; i < 99; i++) terms.add(7d);
        for (int i = 0; i < 108; i++) terms.add(8d);
        for (int i = 0; i < 94; i++) terms.add(9d);

        double[] arr = new double[terms.size()];
        for (int i = 0; i < terms.size(); i++) {
            arr[i] = terms.get(i);
        }

        FrequencyDistTable table5 = new FrequencyDistTable(arr, 10);

        assertTrue(table2.equals(table5));
    }

}