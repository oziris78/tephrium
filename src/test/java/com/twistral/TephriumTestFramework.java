
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



package com.twistral;


import com.twistral.tephrium.core.functions.TMath;
import com.twistral.tephrium.fwg.GibberishFWG;
import com.twistral.tephrium.stats.DataDescription;
import java.util.Arrays;


public class TephriumTestFramework {


    ////////////////////////////////////////////////////////////////////////
    /////////////////////////////  BENCHMARKS  /////////////////////////////
    ////////////////////////////////////////////////////////////////////////


    private static Runnable DO_NOTHING = () -> {};
    private static Runnable beforeEachIter = DO_NOTHING, afterEachIter = DO_NOTHING;
    private static int BENCHMARK_ITER_COUNT;

    public static void setBenchmarkIterationCount(int ic) { BENCHMARK_ITER_COUNT = ic; }
    public static void setAfterEachIter(Runnable aei) { afterEachIter = aei; }
    public static void setBeforeEachIter(Runnable bei) { beforeEachIter = bei; }


    public static void benchmark(String name1, Runnable act1, String name2, Runnable act2) {
        long t1, t2;

        double[] times1 = new double[BENCHMARK_ITER_COUNT];
        double[] times2 = new double[BENCHMARK_ITER_COUNT];
        int arrIndex = 0;

        for (int unused = 0; unused < BENCHMARK_ITER_COUNT; unused++) {
            beforeEachIter.run();

            t1 = System.nanoTime();
            act1.run();
            t2 = System.nanoTime();
            times1[arrIndex] = (t2- t1);

            t1 = System.nanoTime();
            act2.run();
            t2 = System.nanoTime();
            times2[arrIndex] = (t2- t1);

            arrIndex++;
            afterEachIter.run();
        }

        setAfterEachIter(DO_NOTHING);
        setBeforeEachIter(DO_NOTHING);

        Arrays.sort(times1);
        Arrays.sort(times2);
        DataDescription d1 = new DataDescription(times1, name1);
        DataDescription d2 = new DataDescription(times2, name2);

        System.out.println("----------------------------------------");
        System.out.println("Benchmark Results:");
        benchResult("Min", d1, d1.min, d2, d2.min);
        benchResult("Max", d1, d1.max, d2, d2.max);
        benchResult("Mean", d1, d1.mean, d2, d2.mean);
        benchResult("Sum", d1, d1.sum, d2, d2.sum);
        System.out.println("----------------------------------------");
    }


    private static void benchResult(String valStr, DataDescription d1, double v1, DataDescription d2, double v2) {
        if(!TMath.equalsd(v1, v2)) {
            final boolean oneWins = (v1 < v2);
            double winningValue = oneWins ? v1 : v2;
            double losingValue = oneWins ? v2 : v1;
            String winningName = oneWins ? d1.dataName : d2.dataName;
            String losingName = oneWins ? d2.dataName : d1.dataName;
            System.out.printf("%s winner: %s (%.2f ns < %.2f ns)\n", valStr, winningName, winningValue, losingValue);
        }
        else System.out.printf("%s winner: same\n", valStr);
    }


    //////////////////////////////////////////////////////////////////
    /////////////////////////////  DATA  /////////////////////////////
    //////////////////////////////////////////////////////////////////


    public static class Person {

        public String name;
        public int age, height;

        public Person(String name, int age, int height){
            this.name = name;
            this.age = age;
            this.height = height;
        }

        @Override
        public String toString() {
            return String.format("(%s, %d, %d)", name, age, height);
        }

        public static Person[] createPopulation() {
            int[] ages = new int[]{
                    12, 15, 21, 18, 19, 30, 50, 70, 50, 55, 34, 21, 12, 13,
                    18, 18, 18, 18, 18, 17, 18, 65, 75, 32, 24, 25
            };
            int[] heights = new int[]{
                    160, 172, 162, 176, 180, 176, 182, 176, 176, 166, 158, 183, 165, 188,
                    177, 178, 170, 180, 170, 180, 190, 173, 192, 167, 203, 171
            };
            GibberishFWG nameFWG = new GibberishFWG();
            Person[] population = new Person[ages.length];
            for (int i = 0; i < population.length; i++) {
                population[i] = new Person(nameFWG.generateFakeWord(7), ages[i], heights[i]);
            }
            return population;
        }

    }


}
