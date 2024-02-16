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


import static com.twistral.tephrium.prng.RNGUtils.*;


/**
 * Implements this <a href="// https://prng.di.unimi.it/splitmix64.c">public domain source code</a>
 * written by Sebastiano Vigna, with a different mixer function (MX3).
 */
public class SplitMix64Random implements TRandomGenerator {

    private long state;

    public SplitMix64Random(long seed) {
        this.state = seed;
    }

    public SplitMix64Random() {
        this(randSeedFromMath());
    }


    @Override
    public long nextLong() {
        state += 0x9E3779B97F4A7C15L;
        return mix64MX3(state);
    }

}
