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

package com.twistral.tephrium.core.functions;


import com.twistral.tephrium.core.TephriumException;
import java.util.Objects;



/**
 * An immutable range/interval class, this class represents a mathematical interval (or a range of numbers).
 */
public class TRange {

    public static final TRange REEL_NUMBERS = new TRange(-Double.MAX_VALUE, Double.MAX_VALUE);

    public static final TRange ZERO_TO_ONE = new TRange(0d, 1d);

    public static final TRange MONE_TO_ONE = new TRange(-1d, 1d);


    ////////////////////
    /*  CONSTRUCTORS  */
    ////////////////////

    /**  <b>INCLUSIVE</b> END POINTS OF THIS RANGE  */
    public final double left, right;


    public TRange(double inclusiveLeft, double inclusiveRight){
        if(inclusiveLeft >= inclusiveRight)
            throw new TephriumException("[" + inclusiveLeft + ", " + inclusiveRight + "] is an invalid range.");

        this.left = inclusiveLeft;
        this.right = inclusiveRight;
    }


    ///////////////
    /*  METHODS  */
    ///////////////


    /**
     * Returns a new range by taking the square root of this range's left and right boundary values.
     * @return a new range whose boundaries are the square roots of this range's boundaries
     */
    public TRange sqrt(){
        return new TRange(TMath.sqrt(this.left), TMath.sqrt(this.right));
    }



    /**
     * Returns a new and scaled version of this range.
     * @param scale any double, the range will be multiplied by this value
     * @return A new and scaled TRange
     */
    public TRange scale(double scale){
        return new TRange(this.left * scale, this.right * scale);
    }



    /**
     * Returns a new and shifted version of this range.
     * @param shift any double, this value will be added to range's boundaries.
     * @return A new and shifted TRange
     */
    public TRange shift(double shift){
        return new TRange(this.left + shift, this.right + shift);
    }



    /**
     * @param x any value
     * @return true if the value is in this range
     */
    public boolean isInRange(double x){
        return left <= x && x <= right;
    }


    /**
     * @return For a range [a,b] it returns b-a
     */
    public double size() {
        return this.right - this.left;
    }


    /////////////////////////////////////////////////////////////////


    /**
     * Uses {@link TRange#size()} and returns the range with the bigger size.
     * @param range1 any range
     * @param range2 any range
     * @return the range with the bigger size
     */
    public static TRange getBiggerRange(TRange range1, TRange range2){
        return (range1.size() > range2.size()) ? range1 : range2;
    }


    /////////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        return String.format("[%f, %f]", this.left, this.right);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TRange other = (TRange) o;
        return TMath.areEqual(this.left, other.left) && TMath.areEqual(this.right, other.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }


}
