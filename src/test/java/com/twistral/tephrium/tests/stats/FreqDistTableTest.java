// Copyright 2020-2026 Oğuzhan Topaloğlu
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


package com.twistral.tephrium.tests.stats;

import com.twistral.tephrium.TMath;
import com.twistral.tephrium.stats.*;
import org.junit.jupiter.api.*;
import java.util.*;
import com.twistral.tephrium.TestUtils.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class FreqDistTableTest {

    
    @Test
    @DisplayName("realisticExampleTest")
    void realisticExampleTest() {
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

        assertTrue(TMath.equals(table.interLefts[0], 158));
        assertTrue(TMath.equals(table.interLefts[1], 166));
        assertTrue(TMath.equals(table.interLefts[2], 174));
        assertTrue(TMath.equals(table.interLefts[3], 182));
        assertTrue(TMath.equals(table.interLefts[4], 190));
        assertTrue(TMath.equals(table.interLefts[5], 198));

        assertTrue(TMath.equals(table.interRights[0], 166));
        assertTrue(TMath.equals(table.interRights[1], 174));
        assertTrue(TMath.equals(table.interRights[2], 182));
        assertTrue(TMath.equals(table.interRights[3], 190));
        assertTrue(TMath.equals(table.interRights[4], 198));
        assertTrue(TMath.equals(table.interRights[5], 206));

        assertTrue(TMath.equals(table.interMids[0], 162));
        assertTrue(TMath.equals(table.interMids[1], 170));
        assertTrue(TMath.equals(table.interMids[2], 178));
        assertTrue(TMath.equals(table.interMids[3], 186));
        assertTrue(TMath.equals(table.interMids[4], 194));
        assertTrue(TMath.equals(table.interMids[5], 202));

        assertTrue(TMath.equals(table.freqs[0], 4));
        assertTrue(TMath.equals(table.freqs[1], 7));
        assertTrue(TMath.equals(table.freqs[2], 9));
        assertTrue(TMath.equals(table.freqs[3], 3));
        assertTrue(TMath.equals(table.freqs[4], 2));
        assertTrue(TMath.equals(table.freqs[5], 1));

        assertTrue(TMath.equals(table.relFreqs[0], 0.153846153846));
        assertTrue(TMath.equals(table.relFreqs[1], 0.269230769231));
        assertTrue(TMath.equals(table.relFreqs[2], 0.346153846154));
        assertTrue(TMath.equals(table.relFreqs[3], 0.115384615385));
        assertTrue(TMath.equals(table.relFreqs[4], 0.0769230769231));
        assertTrue(TMath.equals(table.relFreqs[5], 0.0384615384615));

        assertTrue(TMath.equals(table.incCumFreqs[0], 4));
        assertTrue(TMath.equals(table.incCumFreqs[1], 11));
        assertTrue(TMath.equals(table.incCumFreqs[2], 20));
        assertTrue(TMath.equals(table.incCumFreqs[3], 23));
        assertTrue(TMath.equals(table.incCumFreqs[4], 25));
        assertTrue(TMath.equals(table.incCumFreqs[5], 26));

        assertTrue(TMath.equals(table.incRelFreqs[0], 0.153846153846));
        assertTrue(TMath.equals(table.incRelFreqs[1], 0.423076923077));
        assertTrue(TMath.equals(table.incRelFreqs[2], 0.769230769231));
        assertTrue(TMath.equals(table.incRelFreqs[3], 0.884615384616));
        assertTrue(TMath.equals(table.incRelFreqs[4], 0.961538461539));
        assertTrue(TMath.equals(table.incRelFreqs[5], 1));

    }


    @Test
    @DisplayName("freqTableTest1")
    void freqTableTest1() {
        FrequencyDistTable table = new FrequencyDistTable(new double[] {
            160, 172, 162, 176, 180, 176, 182, 176, 176, 166, 158, 183, 165, 188,
            177, 178, 170, 180, 170, 180, 190, 173, 192, 167, 203, 171
        }, 6);
        assertTrue(TMath.equals(table.interLefts[0], 158));
        assertTrue(TMath.equals(table.interLefts[1], 166));
        assertTrue(TMath.equals(table.interLefts[2], 174));
        assertTrue(TMath.equals(table.interLefts[3], 182));
        assertTrue(TMath.equals(table.interLefts[4], 190));
        assertTrue(TMath.equals(table.interLefts[5], 198));
        assertTrue(TMath.equals(table.interRights[0], 166));
        assertTrue(TMath.equals(table.interRights[1], 174));
        assertTrue(TMath.equals(table.interRights[2], 182));
        assertTrue(TMath.equals(table.interRights[3], 190));
        assertTrue(TMath.equals(table.interRights[4], 198));
        assertTrue(TMath.equals(table.interRights[5], 206));
        assertTrue(TMath.equals(table.interMids[0], 162));
        assertTrue(TMath.equals(table.interMids[1], 170));
        assertTrue(TMath.equals(table.interMids[2], 178));
        assertTrue(TMath.equals(table.interMids[3], 186));
        assertTrue(TMath.equals(table.interMids[4], 194));
        assertTrue(TMath.equals(table.interMids[5], 202));
        assertTrue(TMath.equals(table.freqs[0], 4));
        assertTrue(TMath.equals(table.freqs[1], 7));
        assertTrue(TMath.equals(table.freqs[2], 9));
        assertTrue(TMath.equals(table.freqs[3], 3));
        assertTrue(TMath.equals(table.freqs[4], 2));
        assertTrue(TMath.equals(table.freqs[5], 1));
        assertTrue(TMath.equals(table.relFreqs[0], 0.153846153846));
        assertTrue(TMath.equals(table.relFreqs[1], 0.269230769231));
        assertTrue(TMath.equals(table.relFreqs[2], 0.346153846154));
        assertTrue(TMath.equals(table.relFreqs[3], 0.115384615385));
        assertTrue(TMath.equals(table.relFreqs[4], 0.0769230769231));
        assertTrue(TMath.equals(table.relFreqs[5], 0.0384615384615));
        assertTrue(TMath.equals(table.incCumFreqs[0], 4));
        assertTrue(TMath.equals(table.incCumFreqs[1], 11));
        assertTrue(TMath.equals(table.incCumFreqs[2], 20));
        assertTrue(TMath.equals(table.incCumFreqs[3], 23));
        assertTrue(TMath.equals(table.incCumFreqs[4], 25));
        assertTrue(TMath.equals(table.incCumFreqs[5], 26));
        assertTrue(TMath.equals(table.incRelFreqs[0], 0.153846153846));
        assertTrue(TMath.equals(table.incRelFreqs[1], 0.423076923077));
        assertTrue(TMath.equals(table.incRelFreqs[2], 0.769230769231));
        assertTrue(TMath.equals(table.incRelFreqs[3], 0.884615384616));
        assertTrue(TMath.equals(table.incRelFreqs[4], 0.961538461539));
        assertTrue(TMath.equals(table.incRelFreqs[5], 1));

        FrequencyDistTable freqDistTable2 = new FrequencyDistTable(new double[] {
            160, 172, 162, 176, 180, 176, 182, 176, 176, 166, 158, 183, 165, 188,
            177, 178, 170, 180, 170, 180, 190, 173, 192, 167, 203, 171
        }, 6);

        assertTrue(TMath.equals(freqDistTable2.interLefts[0], 158));
        assertTrue(TMath.equals(freqDistTable2.interLefts[1], 166));
        assertTrue(TMath.equals(freqDistTable2.interLefts[2], 174));
        assertTrue(TMath.equals(freqDistTable2.interLefts[3], 182));
        assertTrue(TMath.equals(freqDistTable2.interLefts[4], 190));
        assertTrue(TMath.equals(freqDistTable2.interLefts[5], 198));

        assertTrue(TMath.equals(freqDistTable2.interRights[0], 166));
        assertTrue(TMath.equals(freqDistTable2.interRights[1], 174));
        assertTrue(TMath.equals(freqDistTable2.interRights[2], 182));
        assertTrue(TMath.equals(freqDistTable2.interRights[3], 190));
        assertTrue(TMath.equals(freqDistTable2.interRights[4], 198));
        assertTrue(TMath.equals(freqDistTable2.interRights[5], 206));

        assertTrue(TMath.equals(freqDistTable2.interMids[0], 162));
        assertTrue(TMath.equals(freqDistTable2.interMids[1], 170));
        assertTrue(TMath.equals(freqDistTable2.interMids[2], 178));
        assertTrue(TMath.equals(freqDistTable2.interMids[3], 186));
        assertTrue(TMath.equals(freqDistTable2.interMids[4], 194));
        assertTrue(TMath.equals(freqDistTable2.interMids[5], 202));

        assertTrue(TMath.equals(freqDistTable2.freqs[0], 4));
        assertTrue(TMath.equals(freqDistTable2.freqs[1], 7));
        assertTrue(TMath.equals(freqDistTable2.freqs[2], 9));
        assertTrue(TMath.equals(freqDistTable2.freqs[3], 3));
        assertTrue(TMath.equals(freqDistTable2.freqs[4], 2));
        assertTrue(TMath.equals(freqDistTable2.freqs[5], 1));

        assertTrue(TMath.equals(freqDistTable2.relFreqs[0], 0.153846153846));
        assertTrue(TMath.equals(freqDistTable2.relFreqs[1], 0.269230769231));
        assertTrue(TMath.equals(freqDistTable2.relFreqs[2], 0.346153846154));
        assertTrue(TMath.equals(freqDistTable2.relFreqs[3], 0.115384615385));
        assertTrue(TMath.equals(freqDistTable2.relFreqs[4], 0.0769230769231));
        assertTrue(TMath.equals(freqDistTable2.relFreqs[5], 0.0384615384615));

        assertTrue(TMath.equals(freqDistTable2.incCumFreqs[0], 4));
        assertTrue(TMath.equals(freqDistTable2.incCumFreqs[1], 11));
        assertTrue(TMath.equals(freqDistTable2.incCumFreqs[2], 20));
        assertTrue(TMath.equals(freqDistTable2.incCumFreqs[3], 23));
        assertTrue(TMath.equals(freqDistTable2.incCumFreqs[4], 25));
        assertTrue(TMath.equals(freqDistTable2.incCumFreqs[5], 26));

        assertTrue(TMath.equals(freqDistTable2.incRelFreqs[0], 0.153846153846));
        assertTrue(TMath.equals(freqDistTable2.incRelFreqs[1], 0.423076923077));
        assertTrue(TMath.equals(freqDistTable2.incRelFreqs[2], 0.769230769231));
        assertTrue(TMath.equals(freqDistTable2.incRelFreqs[3], 0.884615384616));
        assertTrue(TMath.equals(freqDistTable2.incRelFreqs[4], 0.961538461539));
        assertTrue(TMath.equals(freqDistTable2.incRelFreqs[5], 1));
    }


    @Test
    @DisplayName("freqTableTest2")
    void freqTableTest2() {
        FrequencyDistTable table1 = new FrequencyDistTable(new double[]{ 94, 93, 112, 101, 104, 95, 100, 99, 108, 94 }, 0d, 1d);

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

        FrequencyDistTable table2 = new FrequencyDistTable(arr, 10);

        assertTrue(table1.equals(table2));
        assertEquals(table1.classCount, 10);
        assertEquals(table2.classCount, 10);
    }

}