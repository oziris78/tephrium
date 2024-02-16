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


package com.twistral.tephrium.prng;




public interface TRandomGenerator {


    /**
     * Generates the next long value using the RNG's sequence. <br><br>
     * <b>IMPORTANT: All of the generated 2^64 long values should/must have equal probability
     * for the other default functions in this interface to work properly. Also, they should/must
     * NOT have flaws in their upper bits. If any of these two conditions do NOT hold, that RNG
     * should/must override those default functions to work properly.</b>
     * @return the next long value generated by this RNG
     */
    long nextLong();


    /**
     * Generates the next int value using the RNG's sequence. All 2^32 int values will
     * have equal probability, as long as {@link #nextLong()} works properly.
     * @return the next generated int value
     */
    default int nextInt() { return (int) nextLong(); }


    default boolean nextBoolean() { return nextLong() < 0L; }


    /**
     * Generates the next float value in the [0.0f, 1.0f) range using the RNG's sequence.
     * @return the next generated float value
     */
    default float nextFloat() {
        // FLOAT_UNIT = 23 + 1 (mantissa + precision bit from exponent)
        return (nextLong() >>> 40) * 0x1p-24f;
    }


    /**
     * Generates the next double value in the [0.0d, 1.0d) range using the RNG's sequence.
     * @return the next generated double value
     */
    default double nextDouble() {
        // DOUBLE_UNIT = 53 mantissa bits in a double
        return (nextLong() >>> 11) * 0x1.0p-53;
    }


    default float nextFloat(float rangeLeftInc, float rangeRightExc) {
        return rangeLeftInc + nextFloat() * (rangeRightExc - rangeLeftInc);
    }


    default double nextDouble(double rangeLeftInc, double rangeRightExc) {
        return rangeLeftInc + nextDouble() * (rangeRightExc - rangeLeftInc);
    }


    default long nextLong(long rangeLeftInc, long rangeRightExc) {
        // If overflow didnt exist, we would do rangeLeft + x * rDiff, but sadly it does exist :P
        // So we apply unsigned-multiply-high on x and rangeDiff and add rangeLeft to it

        // Kinda re-inventing the wheel in this function... or pre-versioning with features the wheel?
        // While doing unsigned-multiply-high, we need the Math.multiplyHigh(long, long) function. So that
        // we can modify it and get an unsigned version of it. But this function is +SE9 and Tephrium
        // is SE8 for compability reasons so we get the higher and lower bits and manually perform a
        // multiplication on them like so:
        //     (l1 + h1)(l2 + h2) = l1*l2 + l1*h2 + h1*l2 + h1*h2 = LL + LH + HL + HH
        // We will only use the upper 64 bits of this sum (which will cause LL to be ignored)

        final long rDiff = rangeRightExc - rangeLeftInc;
        final long rLow = rDiff & 0xFFFFFFFFL;
        final long rHigh = (rDiff >>> 32);

        final long x = nextLong();
        final long xLow = x & 0xFFFFFFFFL;
        final long xHigh = (x >>> 32);

        final long xtimesrange = xHigh * rHigh +   // HH
                   (xHigh * rLow >>> 32) +         // HL
                   (xLow * rHigh >>> 32);          // LH
        //         (xLow * rLow >>> 64)            // LL (ignored since its always 0)

        return rangeLeftInc + xtimesrange;
    }


    default int nextInt(int rangeLeftInc, int rangeRightExc) {
        // Same as nextLong(long, long) but for integers
        final int rDiff = rangeRightExc - rangeLeftInc;
        final int rLow = rDiff & 0xFFFF;
        final int rHigh = (rDiff >>> 16);

        final int x = nextInt();
        final int xLow = x & 0xFFFF;
        final int xHigh = (x >>> 16);

        final int xtimesrange = xHigh * rHigh +    // HH
                  (xHigh * rLow >>> 16) +          // HL
                  (xLow * rHigh >>> 16);           // LH
        //        (xLow * rLow >>> 32)             // LL (ignored since its always 0)

        return rangeLeftInc + xtimesrange;
    }


}
