

// Copyright 2020-2026 Oğuzhan Topaloğlu
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


import com.twistral.tephrium.*;
import java.util.*;


/**
 * Utility class for calculating descriptive statistics for any data.
 */
public final class DataDescription {

    /** The descriptive name of this dataset */
    public final String name;

    /** The number of values in this dataset */
    public final int count;

    /** The minimum value in the dataset */
    public final double min;

    /** The maximum value in the dataset */
    public final double max;

    /** The range of the dataset, defined as max-min */
    public final double range;

    /** The most frequent value (mode) or {@link Double#NaN} if none exists */
    public final double modeValue;

    /** The frequency/count of the mode value */
    public final int modeCount;

    /** The sum of all values */
    public final double sum;

    /** The arithmetic mean/average of all values */
    public final double mean;

    /** The population variance */
    public final double variance;

    /** The population standard deviation */
    public final double stddev;

    /** The sample variance using Bessel's N-1 correction */
    public final double sampleVariance;

    /** The sample standard deviation */
    public final double sampleStddev;

    /** The first quartile (25th percentile) of the dataset */
    public final double quartile1;

    /** The second quartile (50th percentile which is equal to the median) of the dataset */
    public final double quartile2;

    /** The third quartile (75th percentile) of the dataset */
    public final double quartile3;

    /** The interquartile range (IQR) which is equal to Q3-Q1 */
    public final double iqr;

    /** The median (50th percentile) of the dataset */
    public final double median;

    /**
     * The Pearson skewness coefficient which is a value in range [-1, 1] that can be
     * used to estimate the skewness of the data set like so:
     * <ul>
     *   <li>if skewness > 0 then the data is skewed to the right</li>
     *   <li>if skewness = 0 then the data is symmetrical</li>
     *   <li>if skewness < 0 then the data is skewed to the left</li>
     * </ul>
     */
    public final double pearsonSkewCoef;

    /**
     * The Bowley skewness coefficient which is a value in range [-1, 1] that can be
     * used to estimate the skewness of the data set like so:
     * <ul>
     *   <li>if skewness > 0 then the data is skewed to the right</li>
     *   <li>if skewness = 0 then the data is symmetrical</li>
     *   <li>if skewness < 0 then the data is skewed to the left</li>
     * </ul>
     */
    public final double bowleySkewCoef;


    /*//////////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  CONSTRUCTORS  ///////////////////////////*/
    /*//////////////////////////////////////////////////////////////////////*/


    public DataDescription(double[] sortedData, String dataName) {
        if (sortedData.length <= 3)
            throw new TephriumException("The size of your data array must be greater than 3.");

        // name, count, min, max, range
        this.name = (dataName != null) ? dataName : "Unnamed";
        this.count = sortedData.length;
        this.min = sortedData[0];
        this.max = sortedData[sortedData.length - 1];
        this.range = max - min;

        // modeValue, modeCount
        double curMode = Double.NaN, currentVal = sortedData[0];
        int maxModeFrequency = 1, currentCount = 1;
        boolean hasMode = false;

        for (int i = 1; i < count; i++) {
            if (sortedData[i] == currentVal) {
                currentCount++;
            }
            else {
                if (currentCount > maxModeFrequency) {
                    maxModeFrequency = currentCount;
                    curMode = currentVal;
                    hasMode = true;
                }
                currentVal = sortedData[i];
                currentCount = 1;
            }
        }
        if (currentCount > maxModeFrequency) {
            maxModeFrequency = currentCount;
            curMode = currentVal;
            hasMode = true;
        }

        this.modeValue = hasMode ? curMode : Double.NaN;
        this.modeCount = hasMode ? maxModeFrequency : 0;

        // sum, mean
        double nsum = 0d;
        for (int i = 0; i < count; i++) {
            nsum += sortedData[i];
        }
        this.sum = nsum;
        this.mean = sum / count;

        // variance, stddev, sampleVariance, sampleStddev
        double vsum = 0d;
        for (int i = 0; i < count; i++) {
            vsum += TMath.square(sortedData[i] - mean);
        }
        this.variance = vsum / count;
        this.stddev = TMath.sqrt(variance);
        this.sampleVariance = vsum / (count - 1);
        this.sampleStddev = TMath.sqrt(sampleVariance);

        // quartile1, quartile2, quartile3, iqr, median
        final double qInd1 = (1 * (count + 1)) / 4d;
        final double qInd2 = (2 * (count + 1)) / 4d;
        final double qInd3 = (3 * (count + 1)) / 4d;

        final int lInd1 = TMath.floorToInt(qInd1);
        final int lInd2 = TMath.floorToInt(qInd2);
        final int lInd3 = TMath.floorToInt(qInd3);

        this.quartile1 = sortedData[lInd1-1] + (sortedData[lInd1] - sortedData[lInd1-1]) * (qInd1 % 1);
        this.quartile2 = sortedData[lInd2-1] + (sortedData[lInd2] - sortedData[lInd2-1]) * (qInd2 % 1);
        this.quartile3 = sortedData[lInd3-1] + (sortedData[lInd3] - sortedData[lInd3-1]) * (qInd3 % 1);

        this.iqr = quartile3 - quartile1;
        this.median = quartile2;

        // pearsonSkewCoef, bowleySkewCoef
        this.pearsonSkewCoef = 3 * (mean - median) / stddev;
        this.bowleySkewCoef = (quartile3 + quartile1 - 2 * quartile2) / (quartile3 - quartile1);
    }


    // IMPL NOTE: Copy pasted from double[] version for maximum speed
    // Only curMode and currentVal definition lines in mode calculation is changed to float
    public DataDescription(float[] sortedData, String dataName) {
        if (sortedData.length <= 3)
            throw new TephriumException("The size of your data array must be greater than 3.");

        // name, count, min, max, range
        this.name = (dataName != null) ? dataName : "Unnamed";
        this.count = sortedData.length;
        this.min = sortedData[0];
        this.max = sortedData[sortedData.length - 1];
        this.range = max - min;

        // modeValue, modeCount
        float curMode = Float.NaN, currentVal = sortedData[0];
        int maxModeFrequency = 1, currentCount = 1;
        boolean hasMode = false;

        for (int i = 1; i < count; i++) {
            if (sortedData[i] == currentVal) {
                currentCount++;
            }
            else {
                if (currentCount > maxModeFrequency) {
                    maxModeFrequency = currentCount;
                    curMode = currentVal;
                    hasMode = true;
                }
                currentVal = sortedData[i];
                currentCount = 1;
            }
        }
        if (currentCount > maxModeFrequency) {
            maxModeFrequency = currentCount;
            curMode = currentVal;
            hasMode = true;
        }

        this.modeValue = hasMode ? curMode : Double.NaN;
        this.modeCount = hasMode ? maxModeFrequency : 0;

        // sum, mean
        double nsum = 0f;
        for (int i = 0; i < count; i++) {
            nsum += sortedData[i];
        }
        this.sum = nsum;
        this.mean = sum / count;

        // variance, stddev, sampleVariance, sampleStddev
        double vsum = 0f;
        for (int i = 0; i < count; i++) {
            vsum += TMath.square(sortedData[i] - mean);
        }
        this.variance = vsum / count;
        this.stddev = TMath.sqrt(variance);
        this.sampleVariance = vsum / (count - 1);
        this.sampleStddev = TMath.sqrt(sampleVariance);

        // quartile1, quartile2, quartile3, iqr, median
        final double qInd1 = (1 * (count + 1)) / 4d;
        final double qInd2 = (2 * (count + 1)) / 4d;
        final double qInd3 = (3 * (count + 1)) / 4d;

        final int lInd1 = TMath.floorToInt(qInd1);
        final int lInd2 = TMath.floorToInt(qInd2);
        final int lInd3 = TMath.floorToInt(qInd3);

        this.quartile1 = sortedData[lInd1-1] + (sortedData[lInd1] - sortedData[lInd1-1]) * (qInd1 % 1);
        this.quartile2 = sortedData[lInd2-1] + (sortedData[lInd2] - sortedData[lInd2-1]) * (qInd2 % 1);
        this.quartile3 = sortedData[lInd3-1] + (sortedData[lInd3] - sortedData[lInd3-1]) * (qInd3 % 1);

        this.iqr = quartile3 - quartile1;
        this.median = quartile2;

        // pearsonSkewCoef, bowleySkewCoef
        this.pearsonSkewCoef = 3 * (mean - median) / stddev;
        this.bowleySkewCoef = (quartile3 + quartile1 - 2 * quartile2) / (quartile3 - quartile1);
    }


    // IMPL NOTE: Copy pasted from double[] version for maximum speed
    // Only curMode and currentVal definition lines in mode calculation is changed to int
    public DataDescription(int[] sortedData, String dataName) {
        if (sortedData.length <= 3)
            throw new TephriumException("The size of your data array must be greater than 3.");

        // name, count, min, max, range
        this.name = (dataName != null) ? dataName : "Unnamed";
        this.count = sortedData.length;
        this.min = sortedData[0];
        this.max = sortedData[sortedData.length - 1];
        this.range = max - min;

        // modeValue, modeCount
        int curMode = Integer.MIN_VALUE, currentVal = sortedData[0];
        int maxModeFrequency = 1, currentCount = 1;
        boolean hasMode = false;

        for (int i = 1; i < count; i++) {
            if (sortedData[i] == currentVal) {
                currentCount++;
            }
            else {
                if (currentCount > maxModeFrequency) {
                    maxModeFrequency = currentCount;
                    curMode = currentVal;
                    hasMode = true;
                }
                currentVal = sortedData[i];
                currentCount = 1;
            }
        }
        if (currentCount > maxModeFrequency) {
            maxModeFrequency = currentCount;
            curMode = currentVal;
            hasMode = true;
        }

        this.modeValue = hasMode ? curMode : Double.NaN;
        this.modeCount = hasMode ? maxModeFrequency : 0;

        // sum, mean
        double nsum = 0d;
        for (int i = 0; i < count; i++) {
            nsum += sortedData[i];
        }
        this.sum = nsum;
        this.mean = sum / count;

        // variance, stddev, sampleVariance, sampleStddev
        double vsum = 0d;
        for (int i = 0; i < count; i++) {
            vsum += TMath.square(sortedData[i] - mean);
        }
        this.variance = vsum / count;
        this.stddev = TMath.sqrt(variance);
        this.sampleVariance = vsum / (count - 1);
        this.sampleStddev = TMath.sqrt(sampleVariance);

        // quartile1, quartile2, quartile3, iqr, median
        final double qInd1 = (1 * (count + 1)) / 4d;
        final double qInd2 = (2 * (count + 1)) / 4d;
        final double qInd3 = (3 * (count + 1)) / 4d;

        final int lInd1 = TMath.floorToInt(qInd1);
        final int lInd2 = TMath.floorToInt(qInd2);
        final int lInd3 = TMath.floorToInt(qInd3);

        this.quartile1 = sortedData[lInd1-1] + (sortedData[lInd1] - sortedData[lInd1-1]) * (qInd1 % 1);
        this.quartile2 = sortedData[lInd2-1] + (sortedData[lInd2] - sortedData[lInd2-1]) * (qInd2 % 1);
        this.quartile3 = sortedData[lInd3-1] + (sortedData[lInd3] - sortedData[lInd3-1]) * (qInd3 % 1);

        this.iqr = quartile3 - quartile1;
        this.median = quartile2;

        // pearsonSkewCoef, bowleySkewCoef
        this.pearsonSkewCoef = 3 * (mean - median) / stddev;
        this.bowleySkewCoef = (quartile3 + quartile1 - 2 * quartile2) / (quartile3 - quartile1);
    }


    public DataDescription(double[] sortedData) {
        this(sortedData, "Unnamed Data");
    }


    public DataDescription(float[] sortedData) {
        this(sortedData, "Unnamed Data");
    }


    public DataDescription(int[] sortedData) {
        this(sortedData, "Unnamed Data");
    }


    /*////////////////////////////////////////////////////////////////*/
    /*///////////////////////  OBJECT METHODS  ///////////////////////*/
    /*////////////////////////////////////////////////////////////////*/


    @Override
    public String toString() {
        return "DataDescription{" + "name='" + name + '\'' + ", count=" + count + ", min=" +
            min + ", max=" + max + ", range=" + range + ", modeValue=" + modeValue +
            ", modeCount=" + modeCount + ", sum=" + sum + ", mean=" + mean + ", variance=" +
            variance + ", stddev=" + stddev + ", sampleVariance=" + sampleVariance +
            ", sampleStddev=" + sampleStddev + ", quartile1=" + quartile1 + ", quartile2=" +
            quartile2 + ", quartile3=" + quartile3 + ", iqr=" + iqr + ", median=" + median +
            ", pearsonSkewCoef=" + pearsonSkewCoef + ", bowleySkewCoef=" + bowleySkewCoef + '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataDescription t = (DataDescription) o;
        return (count == t.count) && (modeCount == t.modeCount) && TMath.equals(mean, t.mean) &&
            TMath.equals(sum, t.sum) && TMath.equals(iqr, t.iqr) && TMath.equals(median, t.median) &&
            TMath.equals(sampleVariance, t.sampleVariance) && TMath.equals(variance, t.variance) &&
            TMath.equals(sampleStddev, t.sampleStddev) && TMath.equals(modeValue, t.modeValue) &&
            TMath.equals(quartile1, t.quartile1) && TMath.equals(quartile2, t.quartile2) &&
            TMath.equals(quartile3, t.quartile3) && TMath.equals(min, t.min) &&
            TMath.equals(max, t.max) && TMath.equals(range, t.range) &&
            TMath.equals(stddev, t.stddev) && TMath.equals(pearsonSkewCoef, t.pearsonSkewCoef) &&
            TMath.equals(bowleySkewCoef, t.bowleySkewCoef) && Objects.equals(name, t.name);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name, count, mean, sum, iqr, sampleVariance, variance,
            sampleStddev, modeValue, modeCount, median, quartile1, quartile2,
            quartile3, min, max, range, stddev, pearsonSkewCoef, bowleySkewCoef
        );
    }


}

