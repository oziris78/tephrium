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
import com.twistral.tephrium.utils.TArrays;
import java.util.*;



public class FrequencyDistTable {

    //////////////
    /*  FIELDS  */
    //////////////

    private FrequencyClass[] table;
    private final int classCount;


    ///////////////////////
    /*  FREQUENCY CLASS  */
    ///////////////////////

    public static class FrequencyClass {
        public final double cLeft, cRight, midpoint, freq, relFreq, incCumFreq, incRelFreq;

        FrequencyClass(double cl, double cr, double mid, double f, double rf, double icf, double irf) {
            this.cLeft = cl;
            this.cRight = cr;
            this.midpoint = mid;
            this.freq = f;
            this.relFreq = rf;
            this.incCumFreq = icf;
            this.incRelFreq = irf;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FrequencyClass t = (FrequencyClass) o;
            return Double.compare(t.cLeft, cLeft) == 0 && Double.compare(t.cRight, cRight) == 0 &&
                    Double.compare(t.midpoint, midpoint) == 0 && Double.compare(t.freq, freq) == 0 &&
                    Double.compare(t.relFreq, relFreq) == 0 && Double.compare(t.incCumFreq, incCumFreq) == 0 &&
                    Double.compare(t.incRelFreq, incRelFreq) == 0;
        }
    }


    ////////////////////
    /*  CONSTRUCTORS  */
    ////////////////////


    // KNOWN FREQUENCIES TO FREQTABLE
    public FrequencyDistTable(double[] frequencies, double min, double classInterval){
        // error checking & references
        this.classCount = frequencies.length;
        if(classCount <= 0) throw new TephriumException("classCount has to be greater than zero.");

        // create table
        table = new FrequencyClass[classCount];

        double totalFreqs = 0;
        for (int i = 0; i < classCount; i++)
            totalFreqs += frequencies[i];

        for (int i = 0; i < table.length; i++){
            double cLeft = min + i * classInterval;
            double cRight = min + (i+1) * classInterval;
            double midpoint = (cLeft + cRight) / 2d;
            // freq
            double freq = frequencies[i];
            double relFreq = freq / totalFreqs;
            double incCumFreq = freq + (i == 0 ? 0 : table[i-1].incCumFreq);
            double incRelFreq = relFreq + (i == 0 ? 0 : table[i-1].incRelFreq);
            table[i] = new FrequencyClass(cLeft, cRight, midpoint, freq, relFreq, incCumFreq, incRelFreq);
        }
    }



    /**
     * Creates a frequency distribution table.
     * @param population any population (can be unsorted)
     * @param classCount an integer specifying how many rows this frequency distribution table will have
     */
    public FrequencyDistTable(double[] population, int classCount) {
        // error checking & references
        if(classCount <= 0) throw new TephriumException("classCount has to be greater than zero.");
        this.classCount = classCount;

        // prepare min, max, range without
        double min = TArrays.getMin(population);
        double max = TArrays.getMax(population);
        double range = DescStats.getRange(min, max);
        double classInterval = Math.ceil(range / classCount);

        // create table
        table = new FrequencyClass[classCount];
        for (int i = 0; i < table.length; i++){
            double cLeft = min + i * classInterval;
            double cRight = min + (i+1) * classInterval;
            double midpoint = (cLeft + cRight) / 2d;
            // freq
            double freq = 0d;
            for (int j = 0; j < population.length; j++) {
                double number = population[j];
                if(cLeft <= number && number < cRight) // cl <= val < cr
                    freq++;
            }
            double relFreq = freq / population.length;
            double incCumFreq = freq + (i == 0 ? 0 : table[i-1].incCumFreq);
            double incRelFreq = relFreq + (i == 0 ? 0 : table[i-1].incRelFreq);
            table[i] = new FrequencyClass(cLeft, cRight, midpoint, freq, relFreq, incCumFreq, incRelFreq);
        }
    }



    ///////////////
    /*  METHODS  */
    ///////////////


    /**
     * Returns the sum of all frequencies (from every row)
     * @return the sum of all frequencies (n value in stats)
     */
    public double getTotalFrequency(){
        double totalFreqs = 0;
        final int size = this.getRowCount();
        for (int i = 0; i < size; i++)
            totalFreqs += this.getFrequency(i);
        return totalFreqs;
    }


    /**
     * Returns all frequencies as an array starting from the first class to
     * the last class like this: {f1, f2, f3, ...}
     * @return all frequencies as an array
     */
    public double[] getFrequenciesAsArray(){
        double[] arr = new double[this.getRowCount()];
        for (int i = 0; i < this.getRowCount(); i++) {
            arr[i] = this.getFrequency(i);
        }
        return arr;
    }


    public int getRowCount(){
        return table.length;
    }


    public FrequencyClass getTableRow(int index) {
        return table[index];
    }


    /**
     * @param row row index
     * @return the left side of this row's interval (inclusive)
     */
    public double getIntervalLeft(int row){
        return table[row].cLeft;
    }


    /**
     * @param row row index
     * @return the right side of this row's interval (exclusive)
     */
    public double getIntervalRight(int row){
        return table[row].cRight;
    }


    /**
     * @param row row index
     * @return the midpoint of this row
     */
    public double getMidpoint(int row){
        return table[row].midpoint;
    }


    /**
     * @param row row index
     * @return the frequency of this row
     */
    public double getFrequency(int row){
        return table[row].freq;
    }


    /**
     * @param row row index
     * @return the relative frequency of this row
     */
    public double getRelativeFreq(int row){
        return table[row].relFreq;
    }


    /**
     * @param row row index
     * @return the increasing cumulative distribution's frequency of this row
     */
    public double getIncCumFreq(int row){
        return table[row].incCumFreq;
    }


    /**
     * @param row row index
     * @return the increasing relative distribution's frequency of this row
     */
    public double getIncRelFreq(int row){
        return table[row].incRelFreq;
    }


    //////////////////////////////////////////////////////////////////////////////


    @Override
    public String toString() {
        String startStr = "class intervals | midpoints | freq. | relative freq. | inc. cum. freq. | inc. rel. freq.\n";
        StringBuilder sb = new StringBuilder(startStr);

        String tab = "     "; // tab string
        int lastIndex = classCount-1;
        for (int i = 0; i < classCount; i++) {
            sb.append(String.format("[%.3f, %.3f)%s%.3f%s%.3f%s%.3f%s%.3f%s%.3f",
                    getIntervalLeft(i), getIntervalRight(i), tab, getMidpoint(i), tab, getFrequency(i), tab, getRelativeFreq(i), tab,
                    getIncCumFreq(i), tab, getIncRelFreq(i)));
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
        FrequencyDistTable that = (FrequencyDistTable) o;
        return (classCount == that.classCount) && Arrays.equals(table, that.table);
    }


    @Override
    public int hashCode() {
        int result = Objects.hash(classCount);
        result = (31 * result) + Arrays.hashCode(table);
        return result;
    }

}