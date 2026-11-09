

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


package com.twistral.tephrium.matrices;


import com.twistral.tephrium.*;
import com.twistral.tephrium.vectors.*;
import java.util.*;
import java.util.function.*;


/**
 * A fast, mutable, 2x2, double valued, row major matrix class with method chaining support.
 */
public class TMat2d {

    private double m00, m01,     // [ m00, m01 ]
                   m10, m11;     // [ m10, m11 ]

    // Actual constructors

    public TMat2d(double m00, double m01, double m10, double m11) {
        this.m00 = m00;  this.m01 = m01;  // [ m00, m01 ]
        this.m10 = m10;  this.m11 = m11;  // [ m10, m11 ]
    }

    public TMat2d(double fillValue) {
        this(fillValue, fillValue, fillValue, fillValue);
    }

    /** Returns I<sub>2</sub> which is the 2x2 identity matrix */
    public TMat2d() {
        this(1d, 0d, 0d, 1d);
    }

    // Copying constructors & functions

    public TMat2d(TMat2d other) {
        this(other.m00, other.m01, other.m10, other.m11);
    }

    public TMat2d copy() {
        return new TMat2d(this.m00, this.m01, this.m10, this.m11);
    }

    // Special static factory methods

    public static TMat2d zero() {
        return new TMat2d(
            0d, 0d,
            0d, 0d
        );
    }

    /** Returns a diagonal matrix with the specified diagonal values */
    public static TMat2d diagonal(double d0, double d1) {
        return new TMat2d(
            d0, 0d,
            0d, d1
        );
    }

    /** Returns a 2D scaling matrix */
    public static TMat2d scaling(double sx, double sy) {
        return new TMat2d(
            sx, 0d,
            0d, sy
        );
    }

    /** Returns a uniform 2D scaling matrix */
    public static TMat2d scaling(double scale) {
        return scaling(scale, scale);
    }

    /** Returns a 2D rotation matrix for the cos and sin values for a specific angle */
    public static TMat2d rotation(double cos, double sin) {
        return new TMat2d(
            cos, -sin,
            sin,  cos
        );
    }

    /** Returns a 2D rotation matrix for the given angle in radians */
    public static TMat2d rotationRad(double radians) {
        return rotation(TMath.cos(radians), TMath.sin(radians));
    }

    /** Returns a 2D rotation matrix for the given angle in degrees */
    public static TMat2d rotationDeg(double degrees) {
        return rotationRad(TMath.toRadians(degrees));
    }

    public static TMat2d fromRows(TVec2d row0, TVec2d row1) {
        return new TMat2d(
            row0.getX(), row0.getY(),
            row1.getX(), row1.getY()
        );
    }

    public static TMat2d fromCols(TVec2d col0, TVec2d col1) {
        return new TMat2d(
            col0.getX(), col1.getX(),
            col0.getY(), col1.getY()
        );
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*////////////////////  ARITHMETIC OPERATIONS  ////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    // Addition

    public TMat2d add(double m00, double m01, double m10, double m11) {
        this.m00 += m00; this.m01 += m01;
        this.m10 += m10; this.m11 += m11;
        return this;
    }

    public TMat2d add(TMat2d other) {
        this.m00 += other.m00; this.m01 += other.m01;
        this.m10 += other.m10; this.m11 += other.m11;
        return this;
    }

    public TMat2d addM00(double x) { this.m00 += x; return this; }
    public TMat2d addM01(double x) { this.m01 += x; return this; }
    public TMat2d addM10(double x) { this.m10 += x; return this; }
    public TMat2d addM11(double x) { this.m11 += x; return this; }

    // Subtraction

    public TMat2d sub(double m00, double m01, double m10, double m11) {
        this.m00 -= m00; this.m01 -= m01;
        this.m10 -= m10; this.m11 -= m11;
        return this;
    }

    public TMat2d sub(TMat2d other) {
        this.m00 -= other.m00; this.m01 -= other.m01;
        this.m10 -= other.m10; this.m11 -= other.m11;
        return this;
    }

    public TMat2d subM00(double x) { this.m00 -= x; return this; }
    public TMat2d subM01(double x) { this.m01 -= x; return this; }
    public TMat2d subM10(double x) { this.m10 -= x; return this; }
    public TMat2d subM11(double x) { this.m11 -= x; return this; }

    // (ELEMENT WISE) Scaling / Multiplication

    public TMat2d scale(double s00, double s01, double s10, double s11) {
        this.m00 *= s00; this.m01 *= s01;
        this.m10 *= s10; this.m11 *= s11;
        return this;
    }

    /**
     * Performs element-wise multiplication, NOT matrix multiplication.
     * For matrix multiplication use this method: {@link #mul(TMat2d)}
     *
     * @param other matrix to perform element-wise multiplication with
     * @return this matrix for method chaining
     */
    public TMat2d scale(TMat2d other) {
        this.m00 *= other.m00; this.m01 *= other.m01;
        this.m10 *= other.m10; this.m11 *= other.m11;
        return this;
    }

    public TMat2d scale(double scale) {
        this.m00 *= scale; this.m01 *= scale;
        this.m10 *= scale; this.m11 *= scale;
        return this;
    }

    public TMat2d scaleM00(double x) { this.m00 *= x; return this; }
    public TMat2d scaleM01(double x) { this.m01 *= x; return this; }
    public TMat2d scaleM10(double x) { this.m10 *= x; return this; }
    public TMat2d scaleM11(double x) { this.m11 *= x; return this; }

    // (ELEMENT WISE) Division

    public TMat2d div(double d00, double d01, double d10, double d11) {
        this.m00 /= d00; this.m01 /= d01;
        this.m10 /= d10; this.m11 /= d11;
        return this;
    }

    public TMat2d div(TMat2d other) {
        this.m00 /= other.m00; this.m01 /= other.m01;
        this.m10 /= other.m10; this.m11 /= other.m11;
        return this;
    }

    public TMat2d div(double scale) {
        this.m00 /= scale; this.m01 /= scale;
        this.m10 /= scale; this.m11 /= scale;
        return this;
    }

    public TMat2d divM00(double x) { this.m00 /= x; return this; }
    public TMat2d divM01(double x) { this.m01 /= x; return this; }
    public TMat2d divM10(double x) { this.m10 /= x; return this; }
    public TMat2d divM11(double x) { this.m11 /= x; return this; }


    /*/////////////////////////////////////////////////////////////////*/
    /*//////////////////  LINEAR ALGEBRA OPERATIONS  //////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    // Matrix multiplication

    /**
     * Performs in-place matrix multiplication on this matrix by the parameter matrix.
     * In other words it simply does {@code this = this * other}.
     *
     * @param m00 other.m00 value
     * @param m01 other.m01 value
     * @param m10 other.m10 value
     * @param m11 other.m11 value
     * @return this matrix for method chaining
     */
    public TMat2d mul(double m00, double m01, double m10, double m11) {
        final double newM00 = this.m00 * m00 + this.m01 * m10;
        final double newM01 = this.m00 * m01 + this.m01 * m11;
        final double newM10 = this.m10 * m00 + this.m11 * m10;
        final double newM11 = this.m10 * m01 + this.m11 * m11;

        this.m00 = newM00;
        this.m01 = newM01;
        this.m10 = newM10;
        this.m11 = newM11;

        return this;
    }

    /**
     * Performs in-place matrix multiplication on this matrix by the parameter matrix.
     * In other words it simply does {@code this = this * other}.
     *
     * @param other the other matrix
     * @return this matrix for method chaining
     */
    public TMat2d mul(TMat2d other) {
        return this.mul(other.m00, other.m01, other.m10, other.m11);
    }

    // Transposition

    /**
     * Performs in-place matrix transposition on this matrix like so: {@code this = transpose(this)}.
     *
     * @return this matrix for method chaining
     */
    public TMat2d transpose() {
        // Just swap m01 and m10 to transpose it
        final double temp = this.m01;
        this.m01 = this.m10;
        this.m10 = temp;
        return this;
    }

    // Inversion

    /**
     * Performs in-place matrix inversion on this matrix like so: {@code this = invert(this)}.
     *
     * @return this matrix for method chaining
     */
    public TMat2d invert() {
        final double determinant = this.determinant();
        final double s = 1d / determinant;

        final double temp = this.m00;
        this.m00 = this.m11 * s;
        this.m11 = temp * s;
        this.m01 = -this.m01 * s;
        this.m10 = -this.m10 * s;

        return this;
    }


    /*//////////////////////////////////////////////////////////////////*/
    /*///////////////////////  FUNCTION MAPPING  ///////////////////////*/
    /*//////////////////////////////////////////////////////////////////*/


    /**
     * Applies the given function to all elements.
     *
     * @param function any double-to-double function
     * @return this matrix for method chaining
     */
    public TMat2d map(DoubleUnaryOperator function) {
        this.m00 = function.applyAsDouble(this.m00);
        this.m01 = function.applyAsDouble(this.m01);
        this.m10 = function.applyAsDouble(this.m10);
        this.m11 = function.applyAsDouble(this.m11);
        return this;
    }

    public TMat2d sin() { return map(TMath::sin); }
    public TMat2d cos() { return map(TMath::cos); }
    public TMat2d tan() { return map(TMath::tan); }
    public TMat2d sinh() { return map(TMath::sinh); }
    public TMat2d cosh() { return map(TMath::cosh); }
    public TMat2d tanh() { return map(TMath::tanh); }
    public TMat2d arcsin() { return map(TMath::arcsin); }
    public TMat2d arccos() { return map(TMath::arccos); }
    public TMat2d arctan() { return map(TMath::arctan); }

    public TMat2d abs() { return this.map(TMath::abs); }
    public TMat2d floor() { return map(TMath::floor); }
    public TMat2d ceil() { return map(TMath::ceil); }
    public TMat2d sqrt() { return map(TMath::sqrt); }
    public TMat2d cbrt() { return this.map(TMath::cbrt); }
    public TMat2d square() { return map(TMath::square); }
    public TMat2d cube() { return this.map(TMath::cube); }


    /*/////////////////////////////////////////////////////////////////*/
    /*//////////////////  GRID ELEMENT PERMUTATIONS  //////////////////*/
    /*/////////////////////////////////////////////////////////////////*/


    /**
     * Horizontally flips the elements of this matrix grid.
     * <b>Keep in mind that this is NOT a geometric space transformation!</b><br>
     *
     * Example from left to right: <br>
     * {@code [1 5] => [5 1]} <br>
     * {@code [7 8] => [8 7]}
     *
     * @return this matrix for method chaining
     */
    public TMat2d permuteGridFlipHorizontally() {
        // Swap m00 and m01
        double temp = this.m00;
        this.m00 = this.m01;
        this.m01 = temp;
        // Swap m10 and m11
        temp = this.m10;
        this.m10 = this.m11;
        this.m11 = temp;
        return this;
    }

    /**
     * Vertically flips the elements of this matrix grid.
     * <b>Keep in mind that this is NOT a geometric space transformation!</b><br>
     *
     * Example from left to right: <br>
     * {@code [1 5] => [7 8]} <br>
     * {@code [7 8] => [1 5]}
     *
     * @return this matrix for method chaining
     */
    public TMat2d permuteGridFlipVertically() {
        // Swap m00 and m10
        double temp = this.m00;
        this.m00 = this.m10;
        this.m10 = temp;
        // Swap m01 and m11
        temp = this.m01;
        this.m01 = this.m11;
        this.m11 = temp;
        return this;
    }

    /**
     * Rotates the elements of this matrix grid by 90 degrees clockwise.
     * <b>Keep in mind that this is NOT a geometric space transformation!</b><br>
     *
     * Example from left to right: <br>
     * {@code [1 5] => [7 1]} <br>
     * {@code [7 8] => [8 5]}
     *
     * @return this matrix for method chaining
     */
    public TMat2d permuteGridRotate90Clockwise() {
        double temp = this.m10;
        this.m10 = this.m11;
        this.m11 = this.m01;
        this.m01 = this.m00;
        this.m00 = temp;
        return this;
    }

    /**
     * Rotates the elements of this matrix grid by 90 degrees anti-clockwise.
     * <b>Keep in mind that this is NOT a geometric space transformation!</b><br>
     *
     * Example from left to right: <br>
     * {@code [1 5] => [5 8]} <br>
     * {@code [7 8] => [1 7]}
     *
     * @return this matrix for method chaining
     */
    public TMat2d permuteGridRotate90AntiClockwise() {
        double temp;
        temp = this.m10;
        this.m10 = this.m00;
        this.m00 = this.m01;
        this.m01 = this.m11;
        this.m11 = temp;
        return this;
    }

    /**
     * Rotates the elements of this matrix grid by 180 degrees.
     * <b>Keep in mind that this is NOT a geometric space transformation!</b><br>
     *
     * Example from left to right: <br>
     * {@code [1 5] => [8 7]} <br>
     * {@code [7 8] => [5 1]}
     *
     * @return this matrix for method chaining
     */
    public TMat2d permuteGridRotate180() {
        // Swap m01 and m10
        double temp = this.m01;
        this.m01 = this.m10;
        this.m10 = temp;
        // Swap m00 and m11
        temp = this.m00;
        this.m00 = this.m11;
        this.m11 = temp;
        return this;
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  METRICS  ///////////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    public static double determinant(double m00, double m01, double m10, double m11) {
        return m00 * m11 - m01 * m10;
    }

    public double determinant() {
        return determinant(this.m00, this.m01, this.m10, this.m11);
    }

    public double trace() {
        return this.m00 + this.m11;
    }


    /*///////////////////////////////////////////////////////////////*/
    /*/////////////////////  GETTERS & SETTERS  /////////////////////*/
    /*///////////////////////////////////////////////////////////////*/

    // Getters

    public double getM00() { return this.m00; }
    public double getM01() { return this.m01; }
    public double getM10() { return this.m10; }
    public double getM11() { return this.m11; }

    public double[][] toArray(){
        return new double[][] {
            { m00, m01 },
            { m10, m11 }
        };
    }

    // Setters

    public TMat2d setM00(double m00) { this.m00 = m00; return this; }
    public TMat2d setM01(double m01) { this.m01 = m01; return this; }
    public TMat2d setM10(double m10) { this.m10 = m10; return this; }
    public TMat2d setM11(double m11) { this.m11 = m11; return this; }

    public TMat2d set(double m00, double m01, double m10, double m11) {
        this.m00 = m00; this.m01 = m01;
        this.m10 = m10; this.m11 = m11;
        return this;
    }

    public TMat2d set(TMat2d other) {
        this.m00 = other.m00; this.m01 = other.m01;
        this.m10 = other.m10; this.m11 = other.m11;
        return this;
    }

    // Min max sum getters for rows and cols

    public double getMinOfRow0() { return TMath.min(this.m00, this.m01); }
    public double getMaxOfRow0() { return TMath.max(this.m00, this.m01); }
    public double getSumOfRow0() { return this.m00 + this.m01; }

    public double getMinOfRow1() { return TMath.min(this.m10, this.m11); }
    public double getMaxOfRow1() { return TMath.max(this.m10, this.m11); }
    public double getSumOfRow1() { return this.m10 + this.m11; }

    public double getMinOfCol0() { return TMath.min(this.m00, this.m10); }
    public double getMaxOfCol0() { return TMath.max(this.m00, this.m10); }
    public double getSumOfCol0() { return this.m00 + this.m10; }

    public double getMinOfCol1() { return TMath.min(this.m01, this.m11); }
    public double getMaxOfCol1() { return TMath.max(this.m01, this.m11); }
    public double getSumOfCol1() { return this.m01 + this.m11; }

    // Indexed getters & setters

    public double get(int row, int col) {
        if (row == 0) {
            if (col == 0) return this.m00;
            if (col == 1) return this.m01;
        }
        if (row == 1) {
            if (col == 0) return this.m10;
            if (col == 1) return this.m11;
        }

        throw new TephriumException("2x2 Matrix indices out of range: row=%d, col=%d", row, col);
    }

    public TMat2d set(int row, int col, double value) {
        if (row == 0) {
            if (col == 0) { this.m00 = value; return this; }
            if (col == 1) { this.m01 = value; return this; }
        }
        if (row == 1) {
            if (col == 0) { this.m10 = value; return this; }
            if (col == 1) { this.m11 = value; return this; }
        }

        throw new TephriumException("2x2 Matrix indices out of range: row=%d, col=%d", row, col);
    }

    public double getMinOfRow(int row) {
        if (row == 0) return getMinOfRow0();
        if (row == 1) return getMinOfRow1();
        throw new TephriumException("2x2 Matrix row index out of range: %d", row);
    }

    public double getMaxOfRow(int row) {
        if (row == 0) return getMaxOfRow0();
        if (row == 1) return getMaxOfRow1();
        throw new TephriumException("2x2 Matrix row index out of range: %d", row);
    }

    public double getSumOfRow(int row) {
        if (row == 0) return getSumOfRow0();
        if (row == 1) return getSumOfRow1();
        throw new TephriumException("2x2 Matrix row index out of range: %d", row);
    }

    public double getMinOfCol(int col) {
        if (col == 0) return getMinOfCol0();
        if (col == 1) return getMinOfCol1();
        throw new TephriumException("2x2 Matrix column index out of range: %d", col);
    }

    public double getMaxOfCol(int col) {
        if (col == 0) return getMaxOfCol0();
        if (col == 1) return getMaxOfCol1();
        throw new TephriumException("2x2 Matrix column index out of range: %d", col);
    }

    public double getSumOfCol(int col) {
        if (col == 0) return getSumOfCol0();
        if (col == 1) return getSumOfCol1();
        throw new TephriumException("2x2 Matrix column index out of range: %d", col);
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*//////////////////////  BOOLEAN FUNCTIONS  //////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    public boolean isSymmetrical() {
        return TMath.equals(this.m01, this.m10);
    }

    public boolean isSingular(double det) {
        return TMath.equals(det, 0d);
    }

    public boolean isInvertable(double det) {
        return !this.isSingular(det);
    }

    public boolean isSingular() {
        return TMath.equals(this.determinant(), 0d);
    }

    public boolean isInvertable() {
        return !this.isSingular();
    }

    public boolean isIdentity() {
        return this.equals(
            1d, 0d,
            0d, 1d
        );
    }

    public boolean isZero() {
        return this.equals(
            0d, 0d,
            0d, 0d
        );
    }


    /*//////////////////////////////////////////////////////////////////*/
    /*////////////////////////  OBJECT METHODS  ////////////////////////*/
    /*//////////////////////////////////////////////////////////////////*/

    public boolean equals(TMat2d other) {
        return TMath.equals(this.m00, other.m00) && TMath.equals(this.m01, other.m01) &&
               TMath.equals(this.m10, other.m10) && TMath.equals(this.m11, other.m11);
    }

    public boolean equals(double m00, double m01, double m10, double m11) {
        return TMath.equals(this.m00, m00) && TMath.equals(this.m01, m01) &&
               TMath.equals(this.m10, m10) && TMath.equals(this.m11, m11);
    }

    @Override
    public String toString() {
        return String.format("[%.5f, %.5f]\n[%.5f, %.5f]\n", m00, m01, m10, m11);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        return this.equals((TMat2d) o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m00, m01, m10, m11);
    }


}

