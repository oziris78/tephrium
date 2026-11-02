

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


public final class FrequencyDistTable {

    /** Number of rows in the table */
    public final int classCount;

    /** Inclusive left values of each interval */
    public final double[] interLefts;

    /** Exclusive right values of each interval */
    public final double[] interRights;

    /** Midpoints of each interval */
    public final double[] interMids;

    /** Frequency of each row */
    public final double[] freqs;

    /** Relative frequency of each row */
    public final double[] relFreqs;

    /** Increasing cumulative distribution's frequency of each row */
    public final double[] incCumFreqs;

    /** Increasing relative distribution's frequency of each row */
    public final double[] incRelFreqs;


    /*//////////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  CONSTRUCTORS  ///////////////////////////*/
    /*//////////////////////////////////////////////////////////////////////*/


    // Internal main constructor
    private FrequencyDistTable(int classCount) {
        if (classCount <= 0)
            throw new TephriumException("Class count must be greater than zero.");

        this.classCount = classCount;
        this.interLefts = new double[classCount];
        this.interRights = new double[classCount];
        this.interMids = new double[classCount];
        this.freqs = new double[classCount];
        this.relFreqs = new double[classCount];
        this.incCumFreqs = new double[classCount];
        this.incRelFreqs = new double[classCount];
    }

    /*/////////// KNOWN FREQUENCY ///////////*/

    public FrequencyDistTable(double[] frequencies, double min, double classInterval) {
        this(frequencies.length);

        // Fill the frequency array
        for (int i = 0; i < this.classCount; i++)
            this.freqs[i] = frequencies[i];

        // Fill the frequency array
        fillRemainingTables(min, classInterval, TMath.sum(frequencies));
    }

    public FrequencyDistTable(float[] frequencies, float min, float classInterval) {
        this(frequencies.length);

        // Fill the frequency array
        for (int i = 0; i < this.classCount; i++)
            this.freqs[i] = frequencies[i];

        // Fill the frequency array
        fillRemainingTables(min, classInterval, TMath.sum(frequencies));
    }

    public FrequencyDistTable(int[] frequencies, int min, int classInterval) {
        this(frequencies.length);

        // Fill the frequency array
        for (int i = 0; i < this.classCount; i++)
            this.freqs[i] = frequencies[i];

        // Fill the frequency array
        fillRemainingTables(min, classInterval, TMath.sum(frequencies));
    }

    /*/////////// UNKNOWN FREQUENCY ///////////*/

    public FrequencyDistTable(double[] population, int classCount) {
        this(classCount);

        // Get the unknown population information
        double[] out = TMath.minMax(population);
        final double min = out[0], max = out[1];
        final double classInterval = TMath.ceil((max - min) / classCount);

        // Fill the frequency array
        for (int pi = 0, len = population.length; pi < len; pi++)
            addFrequency(population[pi], min, classInterval);

        // Fill the other arrays
        fillRemainingTables(min, classInterval, population.length);
    }

    public FrequencyDistTable(float[] population, int classCount) {
        this(classCount);

        // Get the unknown population information
        float[] out = TMath.minMax(population);
        final double min = out[0], max = out[1];
        final double classInterval = TMath.ceil((max - min) / classCount);

        // Fill the frequency array
        for (int pi = 0, len = population.length; pi < len; pi++)
            addFrequency(population[pi], min, classInterval);

        // Fill the other arrays
        fillRemainingTables(min, classInterval, population.length);
    }

    public FrequencyDistTable(int[] population, int classCount) {
        this(classCount);

        // Get the unknown population information
        int[] out = TMath.minMax(population);
        final double min = out[0], max = out[1];
        final double classInterval = TMath.ceil((max - min) / classCount);

        // Fill the frequency array
        for (int pi = 0, len = population.length; pi < len; pi++)
            addFrequency(population[pi], min, classInterval);

        // Fill the other arrays
        fillRemainingTables(min, classInterval, population.length);
    }


    /*//////////////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  HELPER FUNCTIONS  ///////////////////////////*/
    /*//////////////////////////////////////////////////////////////////////////*/


    private void addFrequency(double popValue, double min, double classInterval) {
        for (int i = 0; i < this.classCount; i++) {
            double interLeft = min + i * classInterval;
            double interRight = interLeft + classInterval;

            boolean matches = (i == this.classCount - 1)
                    ? (interLeft <= popValue && popValue <= interRight)
                    : (interLeft <= popValue && popValue < interRight);

            if (matches) {
                this.freqs[i] += 1; // cl <= val < cr
                break;
            }
        }
    }


    private void fillRemainingTables(double min, double classInterval, double totalFreqs) {
        for (int i = 0; i < classCount; i++) {
            interLefts[i] = min + i * classInterval;
            interMids[i] = interLefts[i] + (classInterval / 2d);
            interRights[i] = interLefts[i] + classInterval;
            relFreqs[i] = freqs[i] / totalFreqs;
            incCumFreqs[i] = freqs[i] + (i == 0 ? 0 : incCumFreqs[i-1]);
            incRelFreqs[i] = relFreqs[i] + (i == 0 ? 0 : incRelFreqs[i-1]);
        }
    }


    /*////////////////////////////////////////////////////////////////*/
    /*///////////////////////  OBJECT METHODS  ///////////////////////*/
    /*////////////////////////////////////////////////////////////////*/


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(
            "class intervals | midpoints | freq | relative freq | inc cum freq | inc rel freq\n"
        );

        final int lastIndex = classCount-1;
        for (int i = 0; i < classCount; i++) {
            sb.append(String.format(
                "[%.3f, %.3f)     %.3f     %.3f     %.3f     %.3f     %.3f",
                interLefts[i], interRights[i], interMids[i], freqs[i],
                relFreqs[i], incCumFreqs[i], incRelFreqs[i]
            ));
            if(i != lastIndex) sb.append("\n");
        }

        return sb.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FrequencyDistTable t = (FrequencyDistTable) o;
        return classCount == t.classCount && Objects.deepEquals(interLefts, t.interLefts)
            && Objects.deepEquals(interRights, t.interRights)
            && Objects.deepEquals(interMids, t.interMids)
            && Objects.deepEquals(freqs, t.freqs)
            && Objects.deepEquals(relFreqs, t.relFreqs)
            && Objects.deepEquals(incCumFreqs, t.incCumFreqs)
            && Objects.deepEquals(incRelFreqs, t.incRelFreqs);
    }


    @Override
    public int hashCode() {
        return Objects.hash(
            classCount, Arrays.hashCode(interLefts), Arrays.hashCode(interRights),
            Arrays.hashCode(interMids), Arrays.hashCode(freqs), Arrays.hashCode(relFreqs),
            Arrays.hashCode(incCumFreqs), Arrays.hashCode(incRelFreqs)
        );
    }


}

