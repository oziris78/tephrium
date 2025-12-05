// Copyright 2020-2025 Oğuzhan Topaloğlu
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
import com.twistral.TephriumTestFramework.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;


public class DataDescriptionTest {


    @Test
    @DisplayName("multipleDataDescTests")
    void multipleDataDescTests() {
        Person[] population = Person.createPopulation();
        double[] sortedWithHeight = new double[population.length];
        double[] sortedWithAge = new double[population.length];
        for (int i = 0; i < population.length; i++) {
            sortedWithHeight[i] = population[i].height;
            sortedWithAge[i] = population[i].age;
        }
        Arrays.sort(sortedWithHeight);
        Arrays.sort(sortedWithAge);

        DataDescription heightDesc = new DataDescription(sortedWithHeight);
        DataDescription ageDesc = new DataDescription(sortedWithAge);

        assertTrue(TMath.equalsd(heightDesc.count, 26));
        assertTrue(TMath.equalsd(heightDesc.min, 158));
        assertTrue(TMath.equalsd(heightDesc.max, 203));
        assertTrue(TMath.equalsd(heightDesc.range, 45));
        assertTrue(TMath.equalsd(heightDesc.sum, 4571));
        assertTrue(TMath.equalsd(heightDesc.mean, 175.80769230769));
        assertTrue(TMath.equalsd(heightDesc.variance, 102.38609467456));
        assertTrue(TMath.equalsd(heightDesc.stddev, 10.11860141889956));
        assertTrue(TMath.equalsd(heightDesc.modeValue, 176));
        assertTrue(TMath.equalsd(heightDesc.modeCount, 4));
        assertTrue(TMath.equalsd(heightDesc.quartile1, 169.25));
        assertTrue(TMath.equalsd(heightDesc.quartile2, 176));
        assertTrue(TMath.equalsd(heightDesc.quartile3, 180.5));
        assertTrue(TMath.equalsd(heightDesc.median, 176));
        assertTrue(TMath.equalsd(heightDesc.interquartileRange, 11.25));
        assertTrue(TMath.equalsd(heightDesc.bowleySkewCoef, -0.2));
        assertTrue(TMath.equalsd(heightDesc.pearsonSkewCoef, -0.0570160887));

        assertTrue(TMath.equalsd(ageDesc.count, 26));
        assertTrue(TMath.equalsd(ageDesc.min, 12));
        assertTrue(TMath.equalsd(ageDesc.max, 75));
        assertTrue(TMath.equalsd(ageDesc.range, 63));
        assertTrue(TMath.equalsd(ageDesc.sum, 766));
        assertTrue(TMath.equalsd(ageDesc.mean, 29.461538461538));
        assertTrue(TMath.equalsd(ageDesc.variance, 345.01775147929));
        assertTrue(TMath.equalsd(ageDesc.stddev, 18.574653468619));
        assertTrue(TMath.equalsd(ageDesc.modeValue, 18));
        assertTrue(TMath.equalsd(ageDesc.modeCount, 7));
        assertTrue(TMath.equalsd(ageDesc.quartile1, 18));
        assertTrue(TMath.equalsd(ageDesc.quartile2, 20));
        assertTrue(TMath.equalsd(ageDesc.quartile3, 38));
        assertTrue(TMath.equalsd(ageDesc.median, 20));
        assertTrue(TMath.equalsd(ageDesc.interquartileRange, 20));
        assertTrue(TMath.equalsd(ageDesc.bowleySkewCoef, 0.8));
        assertTrue(TMath.equalsd(ageDesc.pearsonSkewCoef, 1.528137008));

        double[] population2 = new double[]{
                160, 172, 162, 176, 180, 176, 182, 176, 176, 166, 158, 183, 165, 188,
                177, 178, 170, 180, 170, 180, 190, 173, 192, 167, 203, 171
        };
        Arrays.sort(population2);
        DataDescription heightDesc2 = new DataDescription(population2);

        assertTrue(TMath.equalsd(heightDesc2.count, 26));
        assertTrue(TMath.equalsd(heightDesc2.min, 158));
        assertTrue(TMath.equalsd(heightDesc2.max, 203));
        assertTrue(TMath.equalsd(heightDesc2.range, 45));
        assertTrue(TMath.equalsd(heightDesc2.sum, 4571));
        assertTrue(TMath.equalsd(heightDesc2.mean, 175.80769230769));
        assertTrue(TMath.equalsd(heightDesc2.variance, 102.38609467456));
        assertTrue(TMath.equalsd(heightDesc2.stddev, 10.11860141889956));
        assertTrue(TMath.equalsd(heightDesc2.modeValue, 176));
        assertTrue(TMath.equalsd(heightDesc2.modeCount, 4));
        assertTrue(TMath.equalsd(heightDesc2.quartile1, 169.25));
        assertTrue(TMath.equalsd(heightDesc2.quartile2, 176));
        assertTrue(TMath.equalsd(heightDesc2.quartile3, 180.5));
        assertTrue(TMath.equalsd(heightDesc2.median, 176));
        assertTrue(TMath.equalsd(heightDesc2.interquartileRange, 11.25));
        assertTrue(TMath.equalsd(heightDesc2.bowleySkewCoef, -0.2));
        assertTrue(TMath.equalsd(heightDesc2.pearsonSkewCoef, -0.057016088));


        double[] arr1 = new double[]{1, 2, 3, -2, -3, 5, 7, -9, 10, 100};
        Arrays.sort(arr1);

        DataDescription desc = new DataDescription(arr1);
        assertTrue(TMath.equalsd(desc.count, 10));
        assertTrue(TMath.equalsd(desc.max, 100));
        assertTrue(TMath.equalsd(desc.min, -9));
        assertTrue(TMath.equalsd(desc.sum, 114));
        assertTrue(TMath.equalsd(desc.mean, 11.4));
        assertTrue(TMath.equalsd(desc.variance, 898.24));
        assertTrue(TMath.equalsd(desc.modeValue, Double.NaN));
        assertTrue(TMath.equalsd(desc.modeCount, Double.NaN));
        assertTrue(TMath.equalsd(desc.quartile1, -2.25d));
        assertTrue(TMath.equalsd(desc.quartile2, 2.5d));
        assertTrue(TMath.equalsd(desc.quartile3, 7.75d));
    }


}
