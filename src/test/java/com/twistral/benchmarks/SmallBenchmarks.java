
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



package com.twistral.benchmarks;


import com.twistral.tephrium.fwg.GibberishFWG;
import com.twistral.tephrium.prng.JavaSplittableRandom;
import com.twistral.tephrium.prng.RNGUtils;
import com.twistral.tephrium.prng.SplitMix64Random;
import com.twistral.tephrium.strings.FuzzyStringMatcher;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static com.twistral.TephriumTestFramework.benchmark;
import static com.twistral.TephriumTestFramework.*;



public class SmallBenchmarks {

    @Test
    @DisplayName("randomRNGSeeds")
    void randomRNGSeeds() {
        setBenchmarkIterationCount(1_000_000);
        benchmark(
            "randSeedFromTime", () -> RNGUtils.randSeedFromTime(),
            "randSeedFromMath", () -> RNGUtils.randSeedFromMath()
        );
    }


    @Test
    @DisplayName("splitMix64Benchmark")
    void splitMix64Benchmark() {
        SplitMix64Random r1 = new SplitMix64Random();
        JavaSplittableRandom r2 = new JavaSplittableRandom();

        setBenchmarkIterationCount(1_000_000);
        benchmark("SplitM64Rand nextInt", r1::nextInt, "JavaSplitRand nextInt", r2::nextInt);
        benchmark("SplitM64Rand nextLong", r1::nextLong, "JavaSplitRand nextLong", r2::nextLong);
        benchmark("SplitM64Rand nextFloat", r1::nextFloat, "JavaSplitRand nextFloat", r2::nextFloat);
        benchmark("SplitM64Rand nextDouble", r1::nextDouble, "JavaSplitRand nextDouble", r2::nextDouble);
        benchmark("SplitM64Rand nextBoolean", r1::nextBoolean, "JavaSplitRand nextBoolean", r2::nextBoolean);
    }


    @Test
    @DisplayName("fuzzyStringMatcherBenchmark")
    void fuzzyStringMatcherBenchmark() {
        LevenshteinDistance ld = new LevenshteinDistance();
        FuzzyStringMatcher fsm = new FuzzyStringMatcher(200);

        GibberishFWG gibberishFWG = new GibberishFWG();
        String[] randWords = new String[2];
        setBeforeEachIter(() -> {
            randWords[0] = gibberishFWG.generateFakeWord(20);
            randWords[1] = gibberishFWG.generateFakeWord(20);
        });

        setBenchmarkIterationCount(1_000_000);
        benchmark(
            "FuzzyStringMatcher", () -> fsm.levenshteinDistance(randWords[0], randWords[1]),
            "Apache LevenshteinDistance", () -> ld.apply(randWords[0], randWords[1])
        );
    }


}
