// Copyright 2020-2025 Oğuzhan Topaloğlu
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



public final class TArrays {

    // From https://docs.oracle.com/javase/specs/jls/se21/html/jls-4.html#jls-4.12.5
    private static final double DOUBLE_ARR_DEF_VALUE = 0.0d;
    private static final float FLOAT_ARR_DEF_VALUE = 0.0f;
    private static final int INT_ARR_DEF_VALUE = 0;
    private static final long LONG_ARR_DEF_VALUE = 0L;


    // No Constructor
    private TArrays() {}


    /*////////////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  ARRAY CREATION  ///////////////////////////*/
    /*////////////////////////////////////////////////////////////////////////*/


    public static int[] intFilledArray(int size, int fillValue) {
        if(fillValue == INT_ARR_DEF_VALUE) return new int[size];
        int[] arr = new int[size];
        for(int i = 0; i < size; i++) arr[i] = fillValue;
        return arr;
    }

    public static float[] floatFilledArray(int size, float fillValue) {
        if(fillValue == FLOAT_ARR_DEF_VALUE) return new float[size];
        float[] arr = new float[size];
        for(int i = 0; i < size; i++) arr[i] = fillValue;
        return arr;
    }

    public static double[] doubleFilledArray(int size, double fillValue) {
        if(fillValue == DOUBLE_ARR_DEF_VALUE) return new double[size];
        double[] arr = new double[size];
        for(int i = 0; i < size; i++) arr[i] = fillValue;
        return arr;
    }

    public static long[] longFilledArray(int size, long fillValue) {
        if(fillValue == LONG_ARR_DEF_VALUE) return new long[size];
        long[] arr = new long[size];
        for(int i = 0; i < size; i++) arr[i] = fillValue;
        return arr;
    }

}
