// Copyright 2024-2025 Oğuzhan Topaloğlu
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


public class RNGUtils {

    // No constructor
    private RNGUtils() {}


    /*//////////////////////////////////////////////////////////////////*/
    /*///////////////////////  MIXING FUNCTIONS  ///////////////////////*/
    /*//////////////////////////////////////////////////////////////////*/


    /**
     * Computes the 64-bit mixing function for MX3. Implements this
     * <a href="https://github.com/jonmaiga/mx3/blob/master/mx3.h">public domain (CC0) code</a>
     * written by Jon Maiga.
     * @param x any long value
     * @return MX3 mixing function result of the input
     */
    public static long mix64MX3(long x) {
        x ^= x >>> 32;
        x *= 0xBEA225F9EB34556DL;
        x ^= x >>> 29;
        x *= 0xBEA225F9EB34556DL;
        x ^= x >>> 32;
        x *= 0xBEA225F9EB34556DL;
        x ^= x >>> 29;
        return x;
    }


    /**
     * Computes the 64-bit mixing function for Murmur3. Implements this
     * <a href="https://github.com/aappleby/smhasher/blob/master/src/MurmurHash3.cpp">public domain code</a>
     * written by Austin Appleby.
     * @param x any long value
     * @return Murmur3 mixing function result of the input
     */
    public static long mix64Murmur3(long x) {
        x ^= x >>> 33;
        x *= 0xFF51AFD7ED558CCDL;
        x ^= x >>> 33;
        x *= 0xC4CEB9FE1A85EC53L;
        x ^= x >>> 33;
        return x;
    }


    /**
     * Computes the 32-bit mixing function for Murmur3. Implements this
     * <a href="https://github.com/aappleby/smhasher/blob/master/src/MurmurHash3.cpp">public domain code</a>
     * written by Austin Appleby.
     * @param x any int value
     * @return Murmur3 mixing function result of the input
     */
    public static int mix32Murmur3 (int x) {
        x ^= x >>> 16;
        x *= 0x85EBCA6B;
        x ^= x >>> 13;
        x *= 0xC2B2AE35;
        x ^= x >>> 16;
        return x;
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*///////////////////////  RAND INIT SEEDS  ///////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/


    /**
     * Generates a somewhat random long value using {@link System#nanoTime()} and
     * {@link System#currentTimeMillis()} values, along with the {@link #mix64MX3(long)}
     * mixing function.
     * @return a somewhat random long value for an RNG's initial seed
     * @implNote this method was completely mine (Oğuzhan Topaloğlu's) idea, I haven't tested it at all. So I
     *     don't know if it is even close to being good enough or something. Users/devs should NOT use this
     *     function to get random numbers. This function's sole purpose is to generate a somewhat okay,
     *     one-time-use, throw-away-after kind of seed.
     */
    public static long randSeedFromTime() {
        final long nt = System.nanoTime();
        final long ntHash = mix64MX3(nt);
        final long mt = System.currentTimeMillis();
        return mix64MX3(ntHash ^ mt);
    }


    /**
     * Generates a uniformly distributed random long value using {@link Math#random()} function twice for
     * both low and high bits. It also uses {@link #mix64Murmur3(long)} mixing function to mix the combined
     * seed value.
     * @return a random-ish long value for an RNG's initial seed
     */
    public static long randSeedFromMath() {
        // We subtract 0.5d from the generated random doubles to ensure that the resulting
        // values are somewhat distributed symmetrically around zero.
        final double r1 = Math.random() - 0.5d;
        final double r2 = Math.random() - 0.5d;

        // NOTE: overflow completely fine since we want every long to have equal probability
        final long combinedSeed = (long) (
                (r1 * 0x1p52) +    // for low bits:   2^52 since doubles in Java have 52 mantissa bits
                (r2 * 0x1p64) +    // for high bits:  2^64 since doubles in Java are 64-bit
                System.nanoTime()  // to increase the randomness by adding more sources
        );

        return mix64Murmur3(combinedSeed);
    }


}
