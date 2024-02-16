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


import java.util.SplittableRandom;


/**
 * A wrapper for {@link SplittableRandom} class to work with Tephrium's {@link TRandomGenerator} interface.
 */
public class JavaSplittableRandom implements TRandomGenerator {

    private final SplittableRandom random;
    public JavaSplittableRandom(final SplittableRandom random) { this.random = random; }

    /////////////////////////////////////////////////////////////////////
    /////////////////////////////  METHODS  /////////////////////////////
    /////////////////////////////////////////////////////////////////////

    // -------------- WRAPPERS -------------- //

    @Override public long nextLong() { return random.nextLong(); }
    @Override public int nextInt() { return random.nextInt(); }
    @Override public boolean nextBoolean() { return random.nextBoolean(); }
    @Override public double nextDouble() { return random.nextDouble(); }
    @Override public double nextDouble(double rangeLeftInc, double rangeRightExc)
    { return random.nextDouble(rangeLeftInc, rangeRightExc); }
    @Override public long nextLong(long rangeLeftInc, long rangeRightExc)
    { return random.nextLong(rangeLeftInc, rangeRightExc); }
    @Override public int nextInt(int rangeLeftInc, int rangeRightExc)
    { return random.nextInt(rangeLeftInc, rangeRightExc); }

    // -------------- FOLLOWING FUNCS DONT EXIST IN SPLITTABLERANDOM -------------- //

    @Override
    public float nextFloat() {
        return (float) random.nextDouble();
    }

    @Override
    public float nextFloat(float rangeLeftInc, float rangeRightExc) {
        final float f = (float) random.nextDouble();
        return rangeLeftInc + f * (rangeRightExc - rangeLeftInc);
    }

}
