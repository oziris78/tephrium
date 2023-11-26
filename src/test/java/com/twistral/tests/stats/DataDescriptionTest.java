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


package com.twistral.tests.stats;

import com.twistral.tephrium.core.functions.TMath;
import com.twistral.tephrium.stats.DataDescription;
import com.twistral.tephrium.stats.DescStats;
import com.twistral.tephrium.utils.TArrays;
import com.twistral.tests.stats.exampledata.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;


public class DataDescriptionTest {



    @Test
    @DisplayName("dataDescGenericTest")
    void dataDescGenericTest() {
        // data
        Person[] population = Person.createPopulation();
        // get sorted data
        double[] sortedWithHeight = new double[population.length];
        double[] sortedWithAge = new double[population.length];
        for (int i = 0; i < population.length; i++) {
            sortedWithHeight[i] = population[i].height;
            sortedWithAge[i] = population[i].age;
        }
        Arrays.sort(sortedWithHeight);
        Arrays.sort(sortedWithAge);

        // get data sets and desc
        DataDescription heightDesc = DescStats.getDataDesc(sortedWithHeight);

        DataDescription ageDesc = DescStats.getDataDesc(sortedWithAge);

        // TESTS
        Assertions.assertTrue(TMath.areEqual(heightDesc.count, 26));
        Assertions.assertTrue(TMath.areEqual(heightDesc.min, 158));
        Assertions.assertTrue(TMath.areEqual(heightDesc.max, 203));
        Assertions.assertTrue(TMath.areEqual(heightDesc.range, 45));
        Assertions.assertTrue(TMath.areEqual(heightDesc.sum, 4571));
        Assertions.assertTrue(TMath.areEqual(heightDesc.mean, 175.80769230769));
        Assertions.assertTrue(TMath.areEqual(heightDesc.variance, 102.38609467456));
        Assertions.assertTrue(TMath.areEqual(heightDesc.stddev, 10.11860141889956));
        Assertions.assertTrue(TMath.areEqual(heightDesc.modeValue, 176));
        Assertions.assertTrue(TMath.areEqual(heightDesc.modeCount, 4));
        Assertions.assertTrue(TMath.areEqual(heightDesc.quartile1, 169.25));
        Assertions.assertTrue(TMath.areEqual(heightDesc.quartile2, 176));
        Assertions.assertTrue(TMath.areEqual(heightDesc.quartile3, 180.5));
        Assertions.assertTrue(TMath.areEqual(heightDesc.median, 176));
        Assertions.assertTrue(TMath.areEqual(heightDesc.interquartileRange, 11.25));
        Assertions.assertTrue(TMath.areEqual(heightDesc.bowleySkewCoef, -0.2));
        Assertions.assertTrue(TMath.areEqual(heightDesc.pearsonSkewCoef, -0.057));

        Assertions.assertTrue(TMath.areEqual(ageDesc.count, 26));
        Assertions.assertTrue(TMath.areEqual(ageDesc.min, 12));
        Assertions.assertTrue(TMath.areEqual(ageDesc.max, 75));
        Assertions.assertTrue(TMath.areEqual(ageDesc.range, 63));
        Assertions.assertTrue(TMath.areEqual(ageDesc.sum, 766));
        Assertions.assertTrue(TMath.areEqual(ageDesc.mean, 29.461538461538));
        Assertions.assertTrue(TMath.areEqual(ageDesc.variance, 345.01775147929));
        Assertions.assertTrue(TMath.areEqual(ageDesc.stddev, 18.574653468619));
        Assertions.assertTrue(TMath.areEqual(ageDesc.modeValue, 18));
        Assertions.assertTrue(TMath.areEqual(ageDesc.modeCount, 7));
        Assertions.assertTrue(TMath.areEqual(ageDesc.quartile1, 18));
        Assertions.assertTrue(TMath.areEqual(ageDesc.quartile2, 20));
        Assertions.assertTrue(TMath.areEqual(ageDesc.quartile3, 38));
        Assertions.assertTrue(TMath.areEqual(ageDesc.median, 20));
        Assertions.assertTrue(TMath.areEqual(ageDesc.interquartileRange, 20));
        Assertions.assertTrue(TMath.areEqual(ageDesc.bowleySkewCoef, 0.8));
        Assertions.assertTrue(TMath.areEqual(ageDesc.pearsonSkewCoef, 1.5281));
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Test
    @DisplayName("dataDescPrimitiveTest")
    void dataDescPrimitiveTest() {
        double[] population = new double[]{
                160, 172, 162, 176, 180, 176, 182, 176, 176, 166, 158, 183, 165, 188,
                177, 178, 170, 180, 170, 180, 190, 173, 192, 167, 203, 171
        };
        Arrays.sort(population);
        DataDescription heightDesc = DescStats.getDataDesc(population);

        // TESTS
        Assertions.assertTrue(TMath.areEqual(heightDesc.count, 26));
        Assertions.assertTrue(TMath.areEqual(heightDesc.min, 158));
        Assertions.assertTrue(TMath.areEqual(heightDesc.max, 203));
        Assertions.assertTrue(TMath.areEqual(heightDesc.range, 45));
        Assertions.assertTrue(TMath.areEqual(heightDesc.sum, 4571));
        Assertions.assertTrue(TMath.areEqual(heightDesc.mean, 175.80769230769));
        Assertions.assertTrue(TMath.areEqual(heightDesc.variance, 102.38609467456));
        Assertions.assertTrue(TMath.areEqual(heightDesc.stddev, 10.11860141889956));
        Assertions.assertTrue(TMath.areEqual(heightDesc.modeValue, 176));
        Assertions.assertTrue(TMath.areEqual(heightDesc.modeCount, 4));
        Assertions.assertTrue(TMath.areEqual(heightDesc.quartile1, 169.25));
        Assertions.assertTrue(TMath.areEqual(heightDesc.quartile2, 176));
        Assertions.assertTrue(TMath.areEqual(heightDesc.quartile3, 180.5));
        Assertions.assertTrue(TMath.areEqual(heightDesc.median, 176));
        Assertions.assertTrue(TMath.areEqual(heightDesc.interquartileRange, 11.25));
        Assertions.assertTrue(TMath.areEqual(heightDesc.bowleySkewCoef, -0.2));
        Assertions.assertTrue(TMath.areEqual(heightDesc.pearsonSkewCoef, -0.057));
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Test
    @DisplayName("dataDescPrimitiveWithoutConversionTest")
    void dataDescPrimitiveWithoutConversionTest() {
        double[] arr1 = TArrays.doubleArr(1, 2, 3, -2, -3, 5, 7, -9, 10, 100);
        Arrays.sort(arr1);

        // you need to sort it for some values
        Arrays.sort(arr1);

        double sum, count, mean;

        // TESTS
        count = DescStats.getCount(arr1);
        Assertions.assertTrue(TMath.areEqual(count, 10));
        Assertions.assertTrue(TMath.areEqual(DescStats.getMax(arr1), 100));
        Assertions.assertTrue(TMath.areEqual(DescStats.getMin(arr1), -9));
        sum = DescStats.getSum(arr1);
        Assertions.assertTrue(TMath.areEqual(sum, 114));
        mean = DescStats.getMean(sum, count);
        Assertions.assertTrue(TMath.areEqual(mean, 11.4));
        Assertions.assertTrue(TMath.areEqual(DescStats.getVariance(arr1, mean, false), 898.24));
        Assertions.assertTrue(TMath.areEqual(DescStats.getModeAndModeCount(arr1).getX(), Double.NaN));
        Assertions.assertTrue(TMath.areEqual(DescStats.getModeAndModeCount(arr1).getY(), Double.NaN));
        Assertions.assertTrue(TMath.areEqual(DescStats.getQuartile(arr1, 1), -2.25d));
        Assertions.assertTrue(TMath.areEqual(DescStats.getQuartile(arr1, 2), 2.5d));
        Assertions.assertTrue(TMath.areEqual(DescStats.getQuartile(arr1, 3), 7.75d));
    }





}
