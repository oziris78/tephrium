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


package com.twistral.tests;


import com.twistral.tephrium.core.functions.TMath;
import com.twistral.tephrium.utils.TArrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class TArraysTest {

    @Test
    @DisplayName("copyOfTest")
    void copyOfTest() {
        double[][] arr21 = new double[][]{{1, 2, 3}, {10, 20, 30}};
        int[][] arr22 = new int[][]{{1, 2, 3}, {10, 20, 30}};
        float[][] arr23 = new float[][]{{1, 2, 3}, {10, 20, 30}};
        Number[][] arr24 = new Number[][]{{1, 2, 3}, {10, 20, 30}};

        double[][] arr21copy = TArrays.getCopyOf(arr21);
        int[][] arr22copy = TArrays.getCopyOf(arr22);
        float[][] arr23copy = TArrays.getCopyOf(arr23);
        Number[][] arr24copy = TArrays.getCopyOf(arr24);

        arr21copy[0][1] = 999;
        arr22copy[0][1] = 999;
        arr23copy[0][1] = 999;
        arr24copy[0][1] = 999;

        Assertions.assertFalse(TMath.equalsd(arr21[0][1], arr21copy[0][1]));
        Assertions.assertFalse(TMath.equalsd(arr22[0][1], arr22copy[0][1]));
        Assertions.assertFalse(TMath.equalsd(arr23[0][1], arr23copy[0][1]));
        Assertions.assertFalse(TMath.equalsd(arr24[0][1].doubleValue(), arr24copy[0][1].doubleValue()));
    }

    @Test
    @DisplayName("castTest")
    void castTest() {
        int[] arr12 = new int[]{1, 2, 3};
        float[] arr13 = new float[]{1, 2, 3};
        Number[] arr14 = new Number[]{1, 2, 3};

        double[] cast1 = TArrays.getCastedDoubleCopyOf(arr12);
        double[] cast2 = TArrays.getCastedDoubleCopyOf(arr13);
        double[] cast3 = TArrays.getCastedDoubleCopyOf(arr14);

        cast1[1] = -999;
        cast2[1] = -999;
        cast3[1] = -999;

        Assertions.assertFalse(TMath.equalsd(arr12[1], cast1[1]));
        Assertions.assertFalse(TMath.equalsd(arr13[1], cast2[1]));
        Assertions.assertFalse(TMath.equalsd(arr14[1].doubleValue(), cast3[1]));


        int[][] arr22 = new int[][]{{1, 2, 3}, {10, 20, 30}};
        float[][] arr23 = new float[][]{{1, 2, 3}, {10, 20, 30}};
        Number[][] arr24 = new Number[][]{{1, 2, 3}, {10, 20, 30}};

        double[][] cast21 = TArrays.getCastedDouble2CopyOf(arr22);
        double[][] cast22 = TArrays.getCastedDouble2CopyOf(arr23);
        double[][] cast23 = TArrays.getCastedDouble2CopyOf(arr24);

        cast21[0][1] = -999;
        cast22[0][1] = -999;
        cast23[0][1] = -999;

        Assertions.assertFalse(TMath.equalsd(arr22[0][1], cast21[0][1]));
        Assertions.assertFalse(TMath.equalsd(arr23[0][1], cast22[0][1]));
        Assertions.assertFalse(TMath.equalsd(arr24[0][1].doubleValue(), cast23[0][1]));
    }

}
