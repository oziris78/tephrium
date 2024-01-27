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


package com.twistral.tephrium.core.functions;


import java.util.Arrays;

/**
 * Tephrium's heart, containing a lot of alternatives to {@link Math} class' methods. <br>
 * It also contains a lot of extra functionality.
 */
public class TMath {

    ///////////////////////////////////////////////////////////////////////
    /////////////////////////////  CONSTANTS  /////////////////////////////
    ///////////////////////////////////////////////////////////////////////

    public static final double PI = 3.14159265358979323846d;
    public static final double PI_OVER_TWO = PI / 2d;
    public static final double E = 2.7182818284590452354d;
    public static final double GOLDEN_RATIO = 1.61803398874989484820d;
    public static final double SQRT2 = 1.4142135623730950488d;
    public static final double SQRT3 = 1.732050807568877293527d;
    public static final double SQRT5 = 2.23606797749978969640d;

    // No constructor
    private TMath(){}


    /////////////////////////////////////////////////////////////////////
    //////////////////////////  MIN, MAX, ABS  //////////////////////////
    /////////////////////////////////////////////////////////////////////


    public static long min(long a, long b) { return (a <= b) ? a : b; }
    public static int min(int a, int b) { return (a <= b) ? a : b; }
    public static double min(double a, double b) { return Math.min(a, b); }
    public static float min(float a, float b) { return Math.min(a, b); }
    public static float min(float... values) {
        float res = values[0];
        for (int i = 1; i < values.length; i++)
            if(values[i] < res) res = values[i];
        return res;
    }
    public static double min(double... values) {
        double res = values[0];
        for (int i = 1; i < values.length; i++)
            if(values[i] < res) res = values[i];
        return res;
    }
    public static int min(int... values) {
        int res = values[0];
        for (int i = 1; i < values.length; i++)
            if(values[i] < res) res = values[i];
        return res;
    }
    public static long min(long... values) {
        long res = values[0];
        for (int i = 1; i < values.length; i++)
            if(values[i] < res) res = values[i];
        return res;
    }


    public static long max(long a, long b) { return (a >= b) ? a : b; }
    public static int max(int a, int b) { return (a >= b) ? a : b; }
    public static double max(double a, double b) { return (a != a) ? a : ( (a >= b) ? a : b ); }
    public static float max(float a, float b) { return (a != a) ? a : ( (a >= b) ? a : b ); }
    public static float max(float... values) {
        float res = values[0];
        for (int i = 1; i < values.length; i++)
            if(values[i] > res) res = values[i];
        return res;
    }
    public static double max(double... values) {
        double res = values[0];
        for (int i = 1; i < values.length; i++)
            if(values[i] > res) res = values[i];
        return res;
    }
    public static int max(int... values) {
        int res = values[0];
        for (int i = 1; i < values.length; i++)
            if(values[i] > res) res = values[i];
        return res;
    }
    public static long max(long... values) {
        long res = values[0];
        for (int i = 1; i < values.length; i++)
            if(values[i] > res) res = values[i];
        return res;
    }


    public static double abs(double x) { return (x < 0.0) ? -x : (x == 0.0) ? 0.0 : x; }
    public static int abs(int x) { return (x < 0) ? -x : x; }
    public static long abs(long x) { return (x < 0) ? -x : x; }
    public static float abs(float x) { return (x <= 0.0f) ? 0.0f - x : x; }


    public static double sum(double... values) {
        double res = values[0];
        for (int i = 1; i < values.length; i++)
            res += values[i];
        return res;
    }
    public static float sum(float... values) {
        float res = values[0];
        for (int i = 1; i < values.length; i++)
            res += values[i];
        return res;
    }
    public static int sum(int... values) {
        int res = values[0];
        for (int i = 1; i < values.length; i++)
            res += values[i];
        return res;
    }



    //////////////////////////////////////////////////////////////////////
    //////////////////////////  EQUALITY FUNCS  //////////////////////////
    //////////////////////////////////////////////////////////////////////

    /**
     * Checks if both input values are actual numbers (not NaN or Infinity) and actually equal to each other. <br>
     * <b>Keep in mind that this function DOES NOT WORK with very small values.
     * For example, {@link #equalsf(1.4E-45f, 0f)} should return false but it returns true</b>
     * @param a first number
     * @param b second number
     * @return true if a and b are approximately equal
     */
    public static boolean equalsf(float a, float b) {
        return equalsf(a, b, 1E-8f, 1);
    }


    /**
     * Checks if both input values are actual numbers (not NaN or Infinity) and actually equal to each other. <br>
     * <b>Keep in mind that this function DOES NOT WORK with very small values.
     * For example, {@link #equalsd(4.9E-324d, 0d)} should return false but it returns true</b>
     * @param a first number
     * @param b second number
     * @return true if a and b are approximately equal
     */
    public static boolean equalsd(double a, double b) {
        return equalsd(a, b, 1E-8d, 1);
    }


    public static boolean equalsf(float a, float b, float epsilon, int maxUlps) {
        // Handle NAN values
        boolean b1 = Float.isNaN(a);
        boolean b2 = Float.isNaN(b);
        if(b1 || b2) return b1 && b2;

        // Handle infinities
        if(Float.isInfinite(a) || Float.isInfinite(b))
            return a == b;

        // Handle actual numbers
        int ai = Float.floatToIntBits(a);
        int bi = Float.floatToIntBits(b);

        if (ai < 0) ai = 0x80000000 - ai;
        if (bi < 0) bi = 0x80000000 - bi;

        return abs(b - a) <= epsilon || abs(bi - ai) <= maxUlps;
    }


    public static boolean equalsd(double a, double b, double epsilon, int maxUlps) {
        // Handle NAN values
        boolean b1 = Double.isNaN(a);
        boolean b2 = Double.isNaN(b);
        if(b1 || b2) return b1 && b2;

        // Handle infinities
        if(Double.isInfinite(a) || Double.isInfinite(b))
            return a == b;

        // Handle actual numbers (including very smol numbers)
        long ai = Double.doubleToLongBits(a);
        long bi = Double.doubleToLongBits(b);

        if (ai < 0) ai = 0x8000000000000000L - ai;
        if (bi < 0) bi = 0x8000000000000000L - bi;

        return abs(b - a) <= epsilon || abs(bi - ai) <= maxUlps;
    }



    ///////////////////////////////////////////////////////////////////////
    /////////////////////////  FLOOR, CEIL FUNCS  /////////////////////////
    ///////////////////////////////////////////////////////////////////////


    /**
     * This method is a lot faster than using <code>(int) Math.floor(x)</code>
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


    ///////////////////////////////////////////////////////////////////
    /////////////////////////  RANGE MAPPING  /////////////////////////
    ///////////////////////////////////////////////////////////////////

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


    //////////////////////////////////////////////////////////////////////////
    /////////////////////////////  TRIGONOMETRY  /////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    public static double cos(double x) { return Math.cos(x); }
    public static double sin(double x) { return Math.sin(x); }
    public static double tan(double x) { return Math.tan(x); }

    public static double arccos(double x) { return Math.acos(x); }
    public static double arcsin(double x) { return Math.asin(x); }
    public static double arctan(double x) { return Math.atan(x); }


    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////  HYPERBOLICS  /////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    public static double cosh(double x) { return Math.cosh(x); }
    public static double sinh(double x) { return Math.sinh(x); }
    public static double tanh(double x) { return Math.tanh(x); }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////  OTHER FUNCS  /////////////////////////////
    /////////////////////////////////////////////////////////////////////////


    public static double pow(double x, double i) {
        return Math.pow(x, i);
    }

    public static double sqrt(double x) {
        return Math.sqrt(x);
    }

    public static float ulp(float a) {
        return Math.ulp(a);
    }

    public static double ulp(double a) {
        return Math.ulp(a);
    }

    public static double square(double a) {
        return a * a;
    }

}