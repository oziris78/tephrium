// Copyright 2020-2024 Oğuzhan Topaloğlu
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


import com.twistral.tephrium.core.functions.TMath;
import java.util.Objects;


/**
 * A class containing all descriptive statistics measures inside it.
 */
public class DataDescription {

    // values without javadocs (self-explanatory variable names)
    public final double count, mean, sum, interquartileRange, sampleVariance,
            variance, sampleStddev, modeValue, modeCount;

    /** The value that divides the whole data set into 2 equal parts, also it's equal to {@link #quartile2}. */
    public final double median;

    /** Quartiles are calculated with exclusive percentages. <br> Same as QUARTILE.EXC(arr, i) in Excel. */
    public final double quartile1, quartile2, quartile3;

    /** Maximum value */
    public final double min;

    /** Minimum value */
    public final double max;

    /** Specifies (maxValue - minValue) value. */
    public final double range;

    /** Standard deviation */
    public final double stddev;


    /** A value in range [-1,1] to estimate the skewness of this data set. <br>
     * skewness > 0 means it's skewed to right. <br>
     * skewness = 0 means it's symmetrical. <br>
     * skewness < 0 means it's skewed to left.
     * */
    public final double pearsonSkewCoef, bowleySkewCoef;



    DataDescription(double variance, double sampleVariance, double mean,
                    double sum, double interquartileRange, double count,
                    double quartile1, double modeValue, double modeCount, double median,
                    double quartile2, double quartile3, double min, double max,
                    double range, double stddev, double pearsonSkewCoef, double bowleySkewCoef)
    {
        this.interquartileRange = interquartileRange;
        this.pearsonSkewCoef = pearsonSkewCoef;
        this.bowleySkewCoef = bowleySkewCoef;
        this.sampleVariance = sampleVariance;
        this.sampleStddev = TMath.sqrt(sampleVariance);
        this.quartile1 = quartile1;
        this.quartile2 = quartile2;
        this.quartile3 = quartile3;
        this.variance = variance;
        this.median = median;
        this.stddev = stddev;
        this.range = range;
        this.count = count;
        this.mean = mean;
        this.modeValue = modeValue;
        this.modeCount = modeCount;
        this.sum = sum;
        this.min = min;
        this.max = max;
    }

    /////////////////////////////////////////////////////////////////////////////////


    @Override
    public String toString() {
        return "DataDescription{" + "modeValue=" + modeValue + ", modeCount=" + modeCount +
                ", count=" + count + ", mean=" + mean + ", sum=" + sum +
                ", interquartileRange=" + interquartileRange + ", sampleVariance=" + sampleVariance +
                ", variance=" + variance + ", sampleStddev=" + sampleStddev + ", median=" + median +
                ", quartile1=" + quartile1 + ", quartile2=" + quartile2 + ", quartile3=" + quartile3 +
                ", min=" + min + ", max=" + max + ", range=" + range + ", stddev=" + stddev +
                ", pearsonSkewCoef=" + pearsonSkewCoef + ", bowleySkewCoef=" + bowleySkewCoef + '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        DataDescription that = (DataDescription) o;
        return (Double.compare(that.modeValue, modeValue) == 0) &&
                (Double.compare(that.modeCount, modeCount) == 0) &&
                (Double.compare(that.count, count) == 0) &&
                (Double.compare(that.mean, mean) == 0) &&
                (Double.compare(that.sum, sum) == 0) &&
                (Double.compare(that.interquartileRange, interquartileRange) == 0)
                && (Double.compare(that.sampleVariance, sampleVariance) == 0) &&
                (Double.compare(that.variance, variance) == 0) &&
                (Double.compare(that.sampleStddev, sampleStddev) == 0)
                && (Double.compare(that.median, median) == 0)
                && (Double.compare(that.quartile1, quartile1) == 0)
                && (Double.compare(that.quartile2, quartile2) == 0)
                && (Double.compare(that.quartile3, quartile3) == 0)
                && (Double.compare(that.min, min) == 0)
                && (Double.compare(that.max, max) == 0)
                && (Double.compare(that.range, range) == 0)
                && (Double.compare(that.stddev, stddev) == 0)
                && (Double.compare(that.pearsonSkewCoef, pearsonSkewCoef) == 0)
                && (Double.compare(that.bowleySkewCoef, bowleySkewCoef) == 0);
    }


    @Override
    public int hashCode() {
        return Objects.hash(modeValue, modeCount, count, mean, sum,
                interquartileRange, sampleVariance, variance, sampleStddev,
                median, quartile1, quartile2, quartile3, min, max, range,
                stddev, pearsonSkewCoef, bowleySkewCoef);
    }


}