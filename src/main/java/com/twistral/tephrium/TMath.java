

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


package com.twistral.tephrium;


/**
 * Tephrium's heart, containing a lot of alternatives to Java's {@link Math} functions and more.
 */
public final class TMath {

    // [CONSTANTS] Double support (up to 20 significant digits even tho 16 would be enough)
    public static final double PI = 3.14159265358979323846d;
    public static final double PI_OVER_TWO = PI / 2d;
    public static final double E = 2.71828182845904523536d;
    public static final double GOLDEN_RATIO = 1.61803398874989484820d;
    public static final double SQRT2 = 1.41421356237309504880d;
    public static final double SQRT3 = 1.73205080756887729352d;
    public static final double SQRT5 = 2.23606797749978969640d;
    public static final double EPS = 1E-8d;
    public static final double RAD_TO_DEG = 57.29577951308232087679d;
    public static final double DEG_TO_RAD = 0.01745329251994329576d;

    // [CONSTANTS] Float support
    public static final float PI_F = (float) PI;
    public static final float PI_OVER_TWO_F = (float) PI_OVER_TWO;
    public static final float E_F = (float) E;
    public static final float GOLDEN_RATIO_F = (float) GOLDEN_RATIO;
    public static final float SQRT2_F = (float) SQRT2;
    public static final float SQRT3_F = (float) SQRT3;
    public static final float SQRT5_F = (float) SQRT5;
    public static final float EPS_F = (float) EPS;
    public static final float RAD_TO_DEG_F = (float) RAD_TO_DEG;
    public static final float DEG_TO_RAD_F = (float) DEG_TO_RAD;

    // No constructor
    private TMath(){}

    /*///////////////////////////////////////////////////////////////////*/
    /*///////////////////////  MIN-MAX FUNCTIONS  ///////////////////////*/
    /*///////////////////////////////////////////////////////////////////*/

    // Int support

    public static int min(int a, int b) { return (a <= b) ? a : b; }
    public static int min(int a, int b, int c) { return min(a, min(b, c)); }
    public static int min(int a, int b, int c, int d) { return min(a, min(b, min(c, d))); }

    public static int max(int a, int b) { return (a >= b) ? a : b; }
    public static int max(int a, int b, int c) { return max(a, max(b, c)); }
    public static int max(int a, int b, int c, int d) { return max(a, max(b, max(c, d))); }

    public static int min(int... values) {
        int res = values[0];
        for (int i = 1, len = values.length; i < len; i++)
            res = min(res, values[i]);
        return res;
    }

    public static int max(int... values) {
        int res = values[0];
        for (int i = 1, len = values.length; i < len; i++)
            res = max(res, values[i]);
        return res;
    }

    // Long support

    public static long min(long a, long b) { return (a <= b) ? a : b; }
    public static long min(long a, long b, long c) { return min(a, min(b, c)); }
    public static long min(long a, long b, long c, long d) { return min(a, min(b, min(c, d))); }

    public static long max(long a, long b) { return (a >= b) ? a : b; }
    public static long max(long a, long b, long c) { return max(a, max(b, c)); }
    public static long max(long a, long b, long c, long d) { return max(a, max(b, max(c, d))); }

    public static long min(long... values) {
        long res = values[0];
        for (int i = 1, len = values.length; i < len; i++)
            res = min(res, values[i]);
        return res;
    }

    public static long max(long... values) {
        long res = values[0];
        for (int i = 1, len = values.length; i < len; i++)
            res = max(res, values[i]);
        return res;
    }

    // Float support

    public static float min(float a, float b) { return Math.min(a, b); }
    public static float min(float a, float b, float c) { return min(a, min(b, c)); }
    public static float min(float a, float b, float c, float d) { return min(a, min(b, min(c, d))); }

    public static float max(float a, float b) { return Math.max(a, b); }
    public static float max(float a, float b, float c) { return max(a, max(b, c)); }
    public static float max(float a, float b, float c, float d) { return max(a, max(b, max(c, d))); }

    public static float min(float... values) {
        float res = values[0];
        for (int i = 1, len = values.length; i < len; i++)
            res = min(res, values[i]);
        return res;
    }

    public static float max(float... values) {
        float res = values[0];
        for (int i = 1, len = values.length; i < len; i++)
            res = max(res, values[i]);
        return res;
    }

    // Double support

    public static double min(double a, double b) { return Math.min(a, b); }
    public static double min(double a, double b, double c) { return min(a, min(b, c)); }
    public static double min(double a, double b, double c, double d) { return min(a, min(b, min(c, d))); }

    public static double max(double a, double b) { return Math.max(a, b); }
    public static double max(double a, double b, double c) { return max(a, max(b, c)); }
    public static double max(double a, double b, double c, double d) { return max(a, max(b, max(c, d))); }

    public static double min(double... values) {
        double res = values[0];
        for (int i = 1, len = values.length; i < len; i++)
            res = min(res, values[i]);
        return res;
    }

    public static double max(double... values) {
        double res = values[0];
        for (int i = 1, len = values.length; i < len; i++)
            res = max(res, values[i]);
        return res;
    }


    /*///////////////////////////////////////////////////////////////*/
    /*///////////////////////  RANGE MAPPING  ///////////////////////*/
    /*///////////////////////////////////////////////////////////////*/

    // Map Range functions


    /**
     * Maps any value from the range [a1, b1] to the range [a2, b2].
     * For example x=3 in range [0, 10] would be x=50 in range [20, 120].
     *
     * @param x any value from the old range [a1, a2]
     * @param a1 start value of old range (inclusive left value)
     * @param b1 end value of old range (inclusive right value)
     * @param a2 start value of new range (inclusive left value)
     * @param b2 end value of new range (inclusive right value)
     * @return the new value in the new range [a2, b2]
     */
    public static float mapRange(float x, float a1, float b1, float a2, float b2){
        return a2 + ((x - a1) * (b2 - a2)) / (b1 - a1);
    }


    /**
     * Maps values from [-1 ,1] to [0, 1].
     *
     * @param x any value from the range [-1, 1]
     * @return the new value in the range [0, 1]
     */
    public static float mapRange(float x){
        return mapRange(x, -1f, 1f, 0f, 1f);
    }


    /**
     * Maps any value from the range [a1, b1] to the range [a2, b2].
     * For example x=3 in range [0, 10] would be x=50 in range [20, 120].
     *
     * @param x any value from the old range [a1, a2]
     * @param a1 start value of old range (inclusive left value)
     * @param b1 end value of old range (inclusive right value)
     * @param a2 start value of new range (inclusive left value)
     * @param b2 end value of new range (inclusive right value)
     * @return the new value in the new range [a2, b2]
     */
    public static double mapRange(double x, double a1, double b1, double a2, double b2){
        return a2 + ((x - a1) * (b2 - a2)) / (b1 - a1);
    }


    /**
     * Maps values from [-1 ,1] to [0, 1].
     *
     * @param x any value from the range [-1, 1]
     * @return the new value in the range [0, 1]
     */
    public static double mapRange(double x){
        return mapRange(x, -1d, 1d, 0d, 1d);
    }


    // Clamp functions

    public static int clamp(int x, int min, int max) {
        return (x < min) ? min : ((x > max) ? max : x);
    }

    public static long clamp(long x, long min, long max) {
        return (x < min) ? min : ((x > max) ? max : x);
    }

    public static float clamp(float x, float min, float max) {
        return (x < min) ? min : ((x > max) ? max : x);
    }

    public static double clamp(double x, double min, double max) {
        return (x < min) ? min : ((x > max) ? max : x);
    }


    // Floor functions

    public static float floor(float x) { return (float) Math.floor(x); }
    public static double floor(double x) { return Math.floor(x); }

    public static int floorToInt(float x) {
        int xi = (int) x; // This method is faster than (int) Math.floor(x)
        return (x < xi) ? (xi - 1) : xi;
    }

    public static int floorToInt(double x) {
        int xi = (int) x; // This method is faster than (int) Math.floor(x)
        return (x < xi) ? (xi - 1) : xi;
    }


    // Ceil functions

    public static float ceil(float x) { return (float) Math.ceil(x); }
    public static double ceil(double x) { return Math.ceil(x); }

    public static int ceilToInt(float x) {
        int xi = (int) x; // This method is faster than (int) Math.ceil(x)
        return (x > xi) ? (xi + 1) : xi;
    }

    public static int ceilToInt(double x) {
        int xi = (int) x; // This method is faster than (int) Math.ceil(x)
        return (x > xi) ? (xi + 1) : xi;
    }


    // Abs functions

    public static int abs(int x) { return (x < 0) ? -x : x; }
    public static long abs(long x) { return (x < 0) ? -x : x; }
    public static float abs(float x) { return (x <= 0f) ? 0f - x : x; }
    public static double abs(double x) { return (x <= 0d) ? 0d - x : x; }


    // Lerp functions

    public static float lerp(float a, float b, float t) { return a + t * (b - a); }
    public static double lerp(double a, double b, double t) { return a + t * (b - a); }

    public static float invLerp(float a, float b, float value) { return (value - a) / (b - a); }
    public static double invLerp(double a, double b, double value) { return (value - a) / (b - a); }


    /*//////////////////////////////////////////////////////////////////////*/
    /*///////////////////////  TRIG AND HYPERBOLICS  ///////////////////////*/
    /*//////////////////////////////////////////////////////////////////////*/

    // Float support

    public static float cos(float x) { return (float) Math.cos(x); }
    public static float sin(float x) { return (float) Math.sin(x); }
    public static float tan(float x) { return (float) Math.tan(x); }

    public static float arccos(float x) { return (float) Math.acos(x); }
    public static float arcsin(float x) { return (float) Math.asin(x); }
    public static float arctan(float x) { return (float) Math.atan(x); }

    public static float cosh(float x) { return (float) Math.cosh(x); }
    public static float sinh(float x) { return (float) Math.sinh(x); }
    public static float tanh(float x) { return (float) Math.tanh(x); }

    // Double support

    public static double cos(double x) { return Math.cos(x); }
    public static double sin(double x) { return Math.sin(x); }
    public static double tan(double x) { return Math.tan(x); }

    public static double arccos(double x) { return Math.acos(x); }
    public static double arcsin(double x) { return Math.asin(x); }
    public static double arctan(double x) { return Math.atan(x); }

    public static double cosh(double x) { return Math.cosh(x); }
    public static double sinh(double x) { return Math.sinh(x); }
    public static double tanh(double x) { return Math.tanh(x); }


    /*//////////////////////////////////////////////////////////////////*/
    /*////////////////////////  EQUALITY FUNCS  ////////////////////////*/
    /*//////////////////////////////////////////////////////////////////*/

    // Float support


    /**
     * Checks if two float values are approximately equal using both an absolute
     * epsilon threshold and Units in the Last Place (ULP) distance. <br>
     * It also handles NaN (returns true only if both are NaN) and infinite values correctly.
     *
     * @param a first number
     * @param b second number
     * @param epsilon the maximum allowable absolute difference
     * @param maxUlps the maximum allowable unit difference in the last place representation
     * @return true if the inputs are approximately equal within the given tolerances
     */
    public static boolean equals(float a, float b, float epsilon, int maxUlps) {
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


    /**
     * Checks if two float values are approximately equal using default tolerances. <br>
     *
     * <b>Keep in mind that this function DOES NOT WORK with very small values.
     * For example equals(1.4E-45f, 0f) should return false but it returns true</b>
     *
     * @param a first number
     * @param b second number
     * @return true if the inputs are approximately equal within the default tolerances
     * @see #equals(float, float, float, int)
     */
    public static boolean equals(float a, float b) {
        return equals(a, b, EPS_F, 1);
    }


    // Double support


    /**
     * Checks if two double values are approximately equal using both an absolute
     * epsilon threshold and Units in the Last Place (ULP) distance. <br>
     * It also handles NaN (returns true only if both are NaN) and infinite values correctly.
     *
     * @param a first number
     * @param b second number
     * @param epsilon the maximum allowable absolute difference
     * @param maxUlps the maximum allowable unit difference in the last place representation
     * @return true if the inputs are approximately equal within the given tolerances
     */
    public static boolean equals(double a, double b, double epsilon, int maxUlps) {
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


    /**
     * Checks if two double values are approximately equal using default tolerances. <br>
     *
     * <b>Keep in mind that this function DOES NOT WORK with very small values near zero.
     * For example, equals(4.9E-324d, 0d) should return false but it returns true</b>
     *
     * @param a first number
     * @param b second number
     * @return true if the inputs are approximately equal within the default tolerances
     * @see #equals(double, double, double, int)
     */
    public static boolean equals(double a, double b) {
        return equals(a, b, EPS, 1);
    }


    /*////////////////////////////////////////////////////////////////*/
    /*///////////////////////  SUM-PROD FUNCS  ///////////////////////*/
    /*////////////////////////////////////////////////////////////////*/

    // Sum functions

    public static int sum(int... values) {
        int sum = 0;
        for (int i = 0, len = values.length; i < len; i++)
            sum += values[i];
        return sum;
    }

    public static long sum(long... values) {
        long sum = 0;
        for (int i = 0, len = values.length; i < len; i++)
            sum += values[i];
        return sum;
    }

    public static float sum(float... values) {
        float sum = 0;
        for (int i = 0, len = values.length; i < len; i++)
            sum += values[i];
        return sum;
    }

    public static double sum(double... values) {
        double sum = 0;
        for (int i = 0, len = values.length; i < len; i++)
            sum += values[i];
        return sum;
    }

    // Product functions

    public static int prod(int... values) {
        int prod = 1;
        for (int i = 0, len = values.length; i < len; i++)
            prod *= values[i];
        return prod;
    }

    public static long prod(long... values) {
        long prod = 1L;
        for (int i = 0, len = values.length; i < len; i++)
            prod *= values[i];
        return prod;
    }

    public static float prod(float... values) {
        float prod = 1f;
        for (int i = 0, len = values.length; i < len; i++)
            prod *= values[i];
        return prod;
    }

    public static double prod(double... values) {
        double prod = 1d;
        for (int i = 0, len = values.length; i < len; i++)
            prod *= values[i];
        return prod;
    }


    /*//////////////////////////////////////////////////////////////////*/
    /*////////////////////////  MISC FUNCTIONS  ////////////////////////*/
    /*//////////////////////////////////////////////////////////////////*/


    public static float square(float a) { return a * a; }
    public static double square(double a) { return a * a; }

    public static float cube(float a) { return a * a * a; }
    public static double cube(double a) { return a * a * a; }

    public static float sqrt(float x) { return (float) Math.sqrt(x); }
    public static double sqrt(double x) { return Math.sqrt(x); }

    public static float cbrt(float x) { return (float) Math.cbrt(x); }
    public static double cbrt(double x) { return Math.cbrt(x); }

    public static float toDegrees(float radians) { return radians * RAD_TO_DEG_F; }
    public static double toDegrees(double radians) { return radians * RAD_TO_DEG; }

    public static float toRadians(float degrees) { return degrees * DEG_TO_RAD_F; }
    public static double toRadians(double degrees) { return degrees * DEG_TO_RAD; }

    public static float pow(float x, float y) { return (float) Math.pow(x, y); }
    public static double pow(double x, double y) { return Math.pow(x, y); }


}

