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


package com.twistral.tephrium.stats;

import com.twistral.tephrium.core.TephriumException;
import com.twistral.tephrium.core.functions.TMath;
import com.twistral.tephrium.core.vectors.TVec2;

import java.util.HashMap;



public class DescStats {

    /* No constructor and fields */
    private DescStats(){}



    /////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////    GENERAL METHODS    ///////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////


    public static double getRange(double min, double max){
        return max - min;
    }

    public static double getMean(double sum, double count){
        return sum / count;
    }

    /**
     * @param variance the variance of this data set
     * @return the standard deviation of this data set
     */
    public static double getStddev(double variance) {
        return TMath.sqrt(variance);
    }

    public static double getInterquartileRange(double quartile1, double quartile3) {
        return quartile3 - quartile1;
    }

    public static double getPearsonSkewnessCoefficient(double median, double mean, double stddev) {
        return 3 * (mean - median) / stddev;
    }

    public static double getBowleySkewnessCoefficient(double quartile1, double quartile2, double quartile3) {
        return (quartile3 + quartile1 - 2 * quartile2) / (quartile3 - quartile1);
    }


    public static double getCount(double[] data){
        return data.length;
    }


    public static double getMin(double[] sortedData){
        return sortedData[0];
    }


    public static double getMax(double[] sortedData){
        return sortedData[sortedData.length - 1];
    }

    public static double getSum(double[] data){
        double sum = 0;
        int len = data.length;
        for (int i = 0; i < len; i++)
            sum += data[i];
        return sum;
    }


    public static double getVariance(double[] data, double mean, boolean isSample){
        double variance = 0;
        int len = data.length;
        for (int i = 0; i < len; i++) {
            double val = data[i];
            double ximean = val - mean;
            variance += ximean * ximean;
        }
        variance /= isSample ? len - 1 : len;
        return variance;
    }


    public static TVec2 getModeAndModeCount(double[] data){
        HashMap<Double, Integer> frequencyMap = new HashMap<>();
        double maxModeFrequency  = 1, mode = 0;
        boolean hasMode = false;
        int len = data.length;

        for (int i = 0; i < len; i++) {
            double dbl = data[i];

            if (frequencyMap.get(dbl) != null) {
                int current = frequencyMap.get(dbl) + 1;
                frequencyMap.put(dbl, current);

                if(current > maxModeFrequency) {
                    maxModeFrequency  = current;
                    mode = dbl;
                    hasMode = true;
                }
            }
            else frequencyMap.put(dbl, 1);
        }
        mode = hasMode ? mode : Double.NaN;
        double modeCount = hasMode ? frequencyMap.get(mode) : Double.NaN;
        return new TVec2(mode, modeCount);
    }


    /**
     * Uses linear interpolation to calculate the quartiles. <br>
     * Same as QUARTILE.EXC in Excel.
     * @param sortedData the sorted data set
     * @param nthQuartile an integer (1,2 or 3)
     * @return the nth quartile
     */
    public static double getQuartile(double[] sortedData, int nthQuartile) {
        if(sortedData.length <= 3)
            throw new TephriumException("The size of your data array must be greater than 3.");

        double quartileIndex = (sortedData.length + 1d) * nthQuartile / 4d;
        double percentage = quartileIndex % 1;
        int lowIndex = TMath.floorFast(quartileIndex);
        // -1 because in statistics we have 1-based indexes but in Java it's 0-based
        double lowValue = sortedData[lowIndex - 1];
        // also -1 here...
        double highValue = sortedData[lowIndex];
        return lowValue + (highValue - lowValue) * percentage;
    }



    ////////////////////////////////////////////////////////////////////////////////////////////
    ////////    METHOD THAT CALCULATE EVERYTHING AND RETURN A DATADESCRIPTION OBJECT    ////////
    ////////////////////////////////////////////////////////////////////////////////////////////


    public static DataDescription getDataDesc(double[] sortedData){
        if(sortedData.length <= 3)
            throw new TephriumException("The size of your data array must be greater than 3.");

        double count = getCount(sortedData);
        double min = getMin(sortedData);
        double max = getMax(sortedData);
        double range = getRange(min, max);
        double sum = getSum(sortedData);
        double mean = getMean(sum, count);
        double variance = getVariance(sortedData, mean, false);
        double sampleVariance = getVariance(sortedData, mean, true);
        double stddev = getStddev(variance);
        TVec2 mode = getModeAndModeCount(sortedData);
        double quartile1 = getQuartile(sortedData, 1);
        double quartile2 = getQuartile(sortedData, 2);
        double quartile3 = getQuartile(sortedData, 3);
        double median = quartile2;
        double interquartileRange = getInterquartileRange(quartile1, quartile3);
        double pearsonSkewCoef = getPearsonSkewnessCoefficient(median, mean, stddev);
        double bowleySkewCoef = getBowleySkewnessCoefficient(quartile1, quartile2, quartile3);

        return new DataDescription(variance, sampleVariance,
                mean, sum, interquartileRange, count,
                quartile1, mode.getX(), mode.getY(), median, quartile2, quartile3, min, max,
                range, stddev, pearsonSkewCoef, bowleySkewCoef);
    }


}