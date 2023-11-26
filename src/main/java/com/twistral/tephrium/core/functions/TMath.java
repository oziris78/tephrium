// Copyright 2020-2023 Oğuzhan Topaloğlu
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


package com.twistral.tephrium.core.functions;




/**
 * Tephrium's replacement class for {@link Math} with additional functionality.
 */
public class TMath {

    /////////////////
    /*  CONSTANTS  */
    /////////////////

    // Tephrium currently uses a bigger epsilon value, this value will be updated to 1E-6 or 1E-8 in the future
    public static final double EPSILON = 1E-4;

    public static final double PI = 3.14159265358979323846d;
    public static final double PI_OVER_TWO = PI / 2d;

    public static final double E = 2.7182818284590452354d;

    public static final double GOLDEN_RATIO = 1.61803398874989484820d;

    public static final double SQRT2 = 1.4142135623730950488d;
    public static final double SQRT3 = 1.732050807568877293527d;
    public static final double SQRT5 = 2.23606797749978969640d;


    /* No constructor */
    private TMath(){}



    /////////////////////
    /*  MIN, MAX, ABS  */
    /////////////////////

    public static double min(double a, double b) { return Math.min(a, b); }
    public static long min(long a, long b) { return Math.min(a, b); }
    public static float min(float a, float b) { return Math.min(a, b); }
    public static int min(int a, int b) { return Math.min(a, b); }

    public static double max(double a, double b) { return Math.max(a, b); }
    public static long max(long a, long b) { return Math.max(a, b); }
    public static float max(float a, float b) { return Math.max(a, b); }
    public static int max(int a, int b) { return Math.max(a, b); }

    public static double abs(double x) { return (x < 0.0) ? -x : (x == 0.0) ? 0.0 : x; }
    public static int abs(int x) { return Math.abs(x); }
    public static long abs(long x) { return Math.abs(x); }
    public static float abs(float x) { return Math.abs(x); }



    //////////////////////
    /*  EQUALITY FUNCS  */
    //////////////////////


    public static boolean areEqual(int i1, int i2){
        return i1 == i2;
    }

    public static boolean areEqual(double d1, double d2){
        boolean b1 = Double.isNaN(d1);
        boolean b2 = Double.isNaN(d2);
        if(b1 || b2)
            return b1 && b2;

        boolean b3 = Double.isInfinite(d1);
        boolean b4 = Double.isInfinite(d2);
        if(b3 || b4)
            return b3 && b4;

        return Math.abs(d1 - d2) < EPSILON;
    }


    /////////////////////////
    /*  FLOOR, CEIL FUNCS  */
    /////////////////////////

    /**
     * This method is A lot faster than using <code>(int) Math.floor(x)</code>
     * @param x any double value
     * @return floor(x) as int
     */
    public static int floorFast(double x) {
        int xi = (int) x;
        return (x < xi) ? (xi - 1) : xi;
    }

    public static double floor(double x) {
        return Math.floor(x);
    }

    public static double ceil(double x) {
        return Math.ceil(x);
    }


    /////////////////////
    /*  RANGE MAPPING  */
    /////////////////////


    /**
     * Takes oldValue from the range [oldA, oldB] and returns what it would represent if it was in range [newA, newB]
     * @param oldRangeLeft start value of old interval (left value)
     * @param oldRangeRight end value of old interval (right value)
     * @param newRangeLeft start value of new interval (left value)
     * @param newRangeRight end value of new interval (right value)
     * @param oldValue any value from the old interval aka [oldRangeLeft, oldRangeRight]
     * @return The new value of oldX in the new interval aka [newRangeLeft, newRangeRight]
     */
    public static double mapRange(double oldRangeLeft, double oldRangeRight,
                                  double newRangeLeft, double newRangeRight, double oldValue){
        return newRangeLeft + ( (oldValue-oldRangeLeft) * (newRangeRight-newRangeLeft) ) / (oldRangeRight - oldRangeLeft);
    }



    public static double mapRange(TRange oldRange, TRange newRange, double oldValue){
        return mapRange(oldRange.left, oldRange.right, newRange.left, newRange.right, oldValue);
    }



    /**
     * Takes oldValue from [-1,1] and returns what it would be in [0,1]
     * @param oldValue any value from [-1,1] interval
     * @return the value of oldX in [0,1] interval
     */
    public static double mapRange(double oldValue){
        return mapRange(TRange.MONE_TO_ONE, TRange.ZERO_TO_ONE, oldValue);
    }



    ///////////////////
    /*  OTHER FUNCS  */
    ///////////////////


    public static double pow(double x, double i) {
        return Math.pow(x, i);
    }

    public static double sqrt(double x) {
        return Math.sqrt(x);
    }


}