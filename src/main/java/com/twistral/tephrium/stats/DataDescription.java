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


package com.twistral.tephrium.stats;

import com.twistral.tephrium.core.TephriumException;
import com.twistral.tephrium.core.functions.TMath;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;


public class DataDescription {

    public final double count, mean, sum, interquartileRange, sampleVariance, variance, sampleStddev,
            modeValue, modeCount, median, quartile1, quartile2, quartile3, min, max, range, stddev;

    public final String dataName;

    /** A value in range [-1,1] to estimate the skewness of this data set. <br>
     * skewness > 0 means it's skewed to right. <br>
     * skewness = 0 means it's symmetrical. <br>
     * skewness < 0 means it's skewed to left.
     * */
    public final double pearsonSkewCoef, bowleySkewCoef;


    /*//////////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  CONSTRUCTORS  ///////////////////////////*/
    /*//////////////////////////////////////////////////////////////////////*/


    public DataDescription(double[] sortedData, String dataName) {
        if (sortedData.length <= 3)
            throw new TephriumException("The size of your data array must be greater than 3.");

        this.count = sortedData.length;
        this.min = sortedData[0];
        this.max = sortedData[sortedData.length - 1];
        this.range = max - min;
        this.sum = TMath.sum(sortedData);
        this.mean = sum / count;

        double vsum = Arrays.stream(sortedData).map(val -> TMath.square(val - mean)).sum();
        this.variance = vsum / sortedData.length;
        this.stddev = TMath.sqrt(variance);
        this.sampleVariance = vsum / sortedData.length - 1;
        this.sampleStddev = TMath.sqrt(sampleVariance);

        {
            HashMap<Double, Integer> frequencyMap = new HashMap<>();
            double maxModeFrequency = 1d, tempMode = 0d;
            boolean hasMode = false;

            for (double dbl : sortedData) {
                if (!frequencyMap.containsKey(dbl)) {
                    frequencyMap.put(dbl, 1);
                    continue;
                }

                int current = frequencyMap.get(dbl) + 1;
                frequencyMap.put(dbl, current);

                if (current > maxModeFrequency) {
                    maxModeFrequency = current;
                    tempMode = dbl;
                    hasMode = true;
                }
            }
            this.modeValue = hasMode ? tempMode : Double.NaN;
            this.modeCount = hasMode ? frequencyMap.get(this.modeValue) : Double.NaN;
        }

        this.quartile1 = getQuartile(sortedData, 1);
        this.quartile2 = getQuartile(sortedData, 2);
        this.quartile3 = getQuartile(sortedData, 3);
        this.median = quartile2;
        this.pearsonSkewCoef = 3 * (mean - median) / stddev;
        this.interquartileRange = quartile3 - quartile1;
        this.bowleySkewCoef = (quartile3 + quartile1 - 2 * quartile2) / (quartile3 - quartile1);
        this.dataName = (dataName != null) ? dataName : "Unnamed";
    }


    public DataDescription(double[] sortedData) {
        this(sortedData, "Unnamed");
    }


    /*//////////////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  HELPER FUNCTIONS  ///////////////////////////*/
    /*//////////////////////////////////////////////////////////////////////////*/


    private static double getQuartile(double[] sortedData, int nthQuartile) {
        double quartileIndex = (sortedData.length + 1d) * nthQuartile / 4d;
        int lowIndex = TMath.floorFast(quartileIndex);
        double lowValue = sortedData[lowIndex - 1];
        return lowValue + (sortedData[lowIndex] - lowValue) * (quartileIndex % 1);
    }


    /*////////////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  OBJECT METHODS  ///////////////////////////*/
    /*////////////////////////////////////////////////////////////////////////*/


    @Override
    public String toString() {
        return "DataDescription{" + "count=" + count + ", mean=" + mean + ", sum=" + sum +
            ", interquartileRange=" + interquartileRange + ", sampleVariance=" + sampleVariance +
            ", variance=" + variance + ", sampleStddev=" + sampleStddev + ", modeValue=" +
            modeValue + ", modeCount=" + modeCount + ", median=" + median + ", quartile1=" +
            quartile1 + ", quartile2=" + quartile2 + ", quartile3=" + quartile3 + ", min=" +
            min + ", max=" + max + ", range=" + range + ", stddev=" + stddev + ", dataName='" +
            dataName + '\'' + ", pearsonSkewCoef=" + pearsonSkewCoef + ", bowleySkewCoef=" +
            bowleySkewCoef + '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        DataDescription other = (DataDescription) o;
        return TMath.equalsd(other.count, count) && TMath.equalsd(other.mean, mean) &&
                TMath.equalsd(other.sum, sum) && TMath.equalsd(other.interquartileRange, interquartileRange) &&
                TMath.equalsd(other.sampleVariance, sampleVariance) && TMath.equalsd(other.variance, variance) &&
                TMath.equalsd(other.sampleStddev, sampleStddev) && TMath.equalsd(other.modeValue, modeValue) &&
                TMath.equalsd(other.modeCount, modeCount) && TMath.equalsd(other.median, median) &&
                TMath.equalsd(other.quartile1, quartile1) && TMath.equalsd(other.quartile2, quartile2) &&
                TMath.equalsd(other.quartile3, quartile3) && TMath.equalsd(other.min, min) &&
                TMath.equalsd(other.max, max) && TMath.equalsd(other.range, range) &&
                TMath.equalsd(other.stddev, stddev) && TMath.equalsd(other.pearsonSkewCoef, pearsonSkewCoef) &&
                TMath.equalsd(other.bowleySkewCoef, bowleySkewCoef) && dataName.equals(other.dataName);
    }


    @Override
    public int hashCode() {
        return Objects.hash(count, mean, sum, interquartileRange, sampleVariance, variance,
            sampleStddev, modeValue, modeCount, median, quartile1, quartile2, quartile3,
            min, max, range, stddev, dataName, pearsonSkewCoef, bowleySkewCoef);
    }


}
