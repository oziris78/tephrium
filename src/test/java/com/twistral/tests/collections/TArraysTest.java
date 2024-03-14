
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


import com.twistral.tephrium.collections.TArrays;
import com.twistral.tephrium.core.functions.TMath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TArraysTest {

    @Test
    @DisplayName("filledArrayTest")
    void filledArrayTest() {
        final int ARR_SIZE = 5000;
        Random random = new Random();

        for (int unused = 0; unused < 1000; unused++) {
            final int i = random.nextInt();
            final float f = random.nextFloat();
            final long l = random.nextLong();
            final double d = random.nextDouble();

            int[] arri = TArrays.intFilledArray(ARR_SIZE, i);
            float[] arrf = TArrays.floatFilledArray(ARR_SIZE, f);
            long[] arrl = TArrays.longFilledArray(ARR_SIZE, l);
            double[] arrd = TArrays.doubleFilledArray(ARR_SIZE, d);

            for (int j = 0; j < ARR_SIZE; j++) assertTrue(TMath.equalsd(arrd[j], d));
            for (int j = 0; j < ARR_SIZE; j++) assertTrue(TMath.equalsf(arrf[j], f));
            for (int j = 0; j < ARR_SIZE; j++) assertTrue(arri[j] == i);
            for (int j = 0; j < ARR_SIZE; j++) assertTrue(arrl[j] == l);
        }

        int[] arri = TArrays.intFilledArray(ARR_SIZE, 0);
        float[] arrf = TArrays.floatFilledArray(ARR_SIZE, 0);
        long[] arrl = TArrays.longFilledArray(ARR_SIZE, 0);
        double[] arrd = TArrays.doubleFilledArray(ARR_SIZE, 0);

        for (int j = 0; j < ARR_SIZE; j++) assertTrue(TMath.equalsd(arrd[j], 0));
        for (int j = 0; j < ARR_SIZE; j++) assertTrue(TMath.equalsf(arrf[j], 0));
        for (int j = 0; j < ARR_SIZE; j++) assertTrue(arri[j] == 0);
        for (int j = 0; j < ARR_SIZE; j++) assertTrue(arrl[j] == 0);
    }

}
