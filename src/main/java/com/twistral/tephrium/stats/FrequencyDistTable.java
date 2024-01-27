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


import com.twistral.tephrium.core.TephriumException;
import com.twistral.tephrium.core.functions.TMath;
import com.twistral.tephrium.utils.TArrays;

import java.util.*;


public class FrequencyDistTable {

    private final int classCount;
    private double[] cLefts; // inclusive
    private double[] cRights; // exclusive
    private double[] midpoints;
    private double[] freqs;
    private double[] relFreqs;
    private double[] incCumFreqs; // increasing cumulative distribution's frequency
    private double[] incRelFreqs; // increasing relative distribution's frequency

    //////////////////////////////////////////////////////////////////////////
    /////////////////////////////  CONSTRUCTORS  /////////////////////////////
    //////////////////////////////////////////////////////////////////////////


    public FrequencyDistTable(double[] frequencies, double min, double classInterval){
        if(frequencies.length <= 0) {
            throw new TephriumException("Invalid classCount value (must be >0): %d", frequencies.length);
        }

        classCount = frequencies.length;
        cLefts = new double[classCount];
        cRights = new double[classCount];
        midpoints = new double[classCount];
        relFreqs = new double[classCount];
        incCumFreqs = new double[classCount];
        incRelFreqs = new double[classCount];
        freqs = new double[classCount];

        double totalFreqs = TMath.sum(frequencies);
        System.arraycopy(frequencies, 0, freqs, 0, classCount);

        for (int i = 0; i < classCount; i++) {
            cLefts[i] = min + i * classInterval;
            midpoints[i] = cLefts[i] + classInterval / 2d;
            cRights[i] = cLefts[i] + classInterval;
            relFreqs[i] = freqs[i] / totalFreqs;
            incCumFreqs[i] = freqs[i] + (i == 0 ? 0 : incCumFreqs[i-1]);
            incRelFreqs[i] = relFreqs[i] + (i == 0 ? 0 : incRelFreqs[i-1]);
        }
    }


    /**
     * Creates a frequency distribution table.
     * @param population any population (can be unsorted)
     * @param classCount an integer specifying how many rows this frequency distribution table will have
     */
    public FrequencyDistTable(double[] population, int classCount) {
        if(classCount <= 0) {
            throw new TephriumException("Invalid classCount value (must be >0): %d", classCount);
        }

        this.classCount = classCount;
        this.cLefts = new double[classCount];
        this.cRights = new double[classCount];
        this.midpoints = new double[classCount];
        this.relFreqs = new double[classCount];
        this.incCumFreqs = new double[classCount];
        this.incRelFreqs = new double[classCount];
        this.freqs = TArrays.doubleFilledArr(classCount, 0d);

        final double min = TMath.min(population);
        final double max = TMath.max(population);
        final double classInterval = TMath.ceil((max - min) / classCount);

        for (int i = 0; i < classCount; i++) {
            cLefts[i] = min + i * classInterval;
            midpoints[i] = cLefts[i] + classInterval / 2d;
            cRights[i] = cLefts[i] + classInterval;
            for (int j = 0; j < population.length; j++) { // cl <= val < cr
                if(cLefts[i] <= population[j] && population[j] < cRights[i])
                    freqs[i] += 1;
            }
            relFreqs[i] = freqs[i] / population.length;
            incCumFreqs[i] = freqs[i] + (i == 0 ? 0 : incCumFreqs[i-1]);
            incRelFreqs[i] = relFreqs[i] + (i == 0 ? 0 : incRelFreqs[i-1]);
        }
    }

    /////////////////////////////////////////////////////////////////////
    /////////////////////////////  METHODS  /////////////////////////////
    /////////////////////////////////////////////////////////////////////


    public double getIntervalLeft(int row) { return cLefts[row]; }
    public double getIntervalRight(int row) { return cRights[row]; }
    public double getMidpoint(int row) { return midpoints[row]; }
    public double getFrequency(int row) { return freqs[row]; }
    public double getRelativeFreq(int row) { return relFreqs[row]; }
    public int getRowCount() { return classCount; }
    public double getIncCumFreq(int row) { return incCumFreqs[row]; }
    public double getIncRelFreq(int row) { return incRelFreqs[row]; }


    ////////////////////////////////////////////////////////////////////////////
    /////////////////////////////  OBJECT METHODS  /////////////////////////////
    ////////////////////////////////////////////////////////////////////////////


    @Override
    public String toString() {
        String startStr = "class intervals | midpoints | freq. | relative freq. | inc. cum. freq. | inc. rel. freq.\n";
        StringBuilder sb = new StringBuilder(startStr);

        String tab = "     "; // tab string
        int lastIndex = classCount-1;
        for (int i = 0; i < classCount; i++) {
            sb.append(String.format("[%.3f, %.3f)%s%.3f%s%.3f%s%.3f%s%.3f%s%.3f",
                    getIntervalLeft(i), getIntervalRight(i), tab, getMidpoint(i), tab, getFrequency(i),
                    tab, getRelativeFreq(i), tab, getIncCumFreq(i), tab, getIncRelFreq(i)));
            if(i != lastIndex) sb.append("\n");
        }

        return sb.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        FrequencyDistTable other = (FrequencyDistTable) o;
        return (classCount == other.classCount) && Arrays.equals(cLefts, other.cLefts) &&
            Arrays.equals(cRights, other.cRights) && Arrays.equals(midpoints, other.midpoints) &&
            Arrays.equals(freqs, other.freqs) && Arrays.equals(relFreqs, other.relFreqs) &&
            Arrays.equals(incCumFreqs, other.incCumFreqs) && Arrays.equals(incRelFreqs, other.incRelFreqs);
    }


    @Override
    public int hashCode() {
        int result = Objects.hash(classCount);
        result = (31 * result) + Arrays.hashCode(cLefts);
        result = (31 * result) + Arrays.hashCode(cRights);
        result = (31 * result) + Arrays.hashCode(midpoints);
        result = (31 * result) + Arrays.hashCode(freqs);
        result = (31 * result) + Arrays.hashCode(relFreqs);
        result = (31 * result) + Arrays.hashCode(incCumFreqs);
        result = (31 * result) + Arrays.hashCode(incRelFreqs);
        return result;
    }


}