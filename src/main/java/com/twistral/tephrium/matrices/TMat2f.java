

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
import com.twistral.tephrium.functions.FloatUnaryOperator;
import com.twistral.tephrium.vectors.*;
import java.util.*;
import java.util.function.*;


/**
 * A fast, mutable, 2x2, float valued, row major matrix class with method chaining support.
 */
public class TMat2f {

    private float m00, m01,     // [ m00, m01 ]
                  m10, m11;     // [ m10, m11 ]

    // Actual constructors

    public TMat2f(float m00, float m01, float m10, float m11) {
        this.m00 = m00;  this.m01 = m01;  // [ m00, m01 ]
        this.m10 = m10;  this.m11 = m11;  // [ m10, m11 ]
    }

    public TMat2f(float fillValue) {
        this(
            fillValue, fillValue,
            fillValue, fillValue
        );
    }

    /** Returns I<sub>2</sub> which is the 2x2 identity matrix */
    public TMat2f() {
        this(
            1f, 0f,
            0f, 1f
        );
    }

    // Copying constructors & functions

    public TMat2f(TMat2f other) {
        this(other.m00, other.m01, other.m10, other.m11);
    }

    public TMat2f copy() {
        return new TMat2f(this.m00, this.m01, this.m10, this.m11);
    }

    // Special static factory methods

    public static TMat2f zero() {
        return new TMat2f(0f);
    }

    /** Returns a diagonal matrix with the specified diagonal values */
    public static TMat2f diagonal(float d0, float d1) {
        return new TMat2f(
            d0, 0f,
            0f, d1
        );
    }

    /** Returns a 2D scaling matrix */
    public static TMat2f scaling(float sx, float sy) {
        return new TMat2f(
            sx, 0f,
            0f, sy
        );
    }

    /** Returns a uniform 2D scaling matrix */
    public static TMat2f scaling(float scale) {
        return scaling(scale, scale);
    }

    /** Returns a 2D rotation matrix for the cos and sin values for a specific angle */
    public static TMat2f rotation(float cos, float sin) {
        return new TMat2f(
            cos, -sin,
            sin,  cos
        );
    }

    /** Returns a 2D rotation matrix for the given angle in radians */
    public static TMat2f rotationRad(float radians) {
        return rotation(TMath.cos(radians), TMath.sin(radians));
    }

    /** Returns a 2D rotation matrix for the given angle in degrees */
    public static TMat2f rotationDeg(float degrees) {
        return rotationRad(TMath.toRadians(degrees));
    }

    public static TMat2f fromRows(TVec2f row0, TVec2f row1) {
        return new TMat2f(
            row0.getX(), row0.getY(),
            row1.getX(), row1.getY()
        );
    }

    public static TMat2f fromCols(TVec2f col0, TVec2f col1) {
        return new TMat2f(
            col0.getX(), col1.getX(),
            col0.getY(), col1.getY()
        );
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*////////////////////  ARITHMETIC OPERATIONS  ////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    // Addition

    public TMat2f add(float m00, float m01, float m10, float m11) {
        this.m00 += m00; this.m01 += m01;
        this.m10 += m10; this.m11 += m11;
        return this;
    }

    public TMat2f add(TMat2f other) {
        this.m00 += other.m00; this.m01 += other.m01;
        this.m10 += other.m10; this.m11 += other.m11;
        return this;
    }

    public TMat2f addM00(float x) { this.m00 += x; return this; }
    public TMat2f addM01(float x) { this.m01 += x; return this; }
    public TMat2f addM10(float x) { this.m10 += x; return this; }
    public TMat2f addM11(float x) { this.m11 += x; return this; }

    // Subtraction

    public TMat2f sub(float m00, float m01, float m10, float m11) {
        this.m00 -= m00; this.m01 -= m01;
        this.m10 -= m10; this.m11 -= m11;
        return this;
    }

    public TMat2f sub(TMat2f other) {
        this.m00 -= other.m00; this.m01 -= other.m01;
        this.m10 -= other.m10; this.m11 -= other.m11;
        return this;
    }

    public TMat2f subM00(float x) { this.m00 -= x; return this; }
    public TMat2f subM01(float x) { this.m01 -= x; return this; }
    public TMat2f subM10(float x) { this.m10 -= x; return this; }
    public TMat2f subM11(float x) { this.m11 -= x; return this; }

    // (ELEMENT WISE) Scaling / Multiplication

    public TMat2f scale(float s00, float s01, float s10, float s11) {
        this.m00 *= s00; this.m01 *= s01;
        this.m10 *= s10; this.m11 *= s11;
        return this;
    }

    /**
     * Performs element-wise multiplication, NOT matrix multiplication.
     * For matrix multiplication use this method: {@link #mul(TMat2f)}
     *
     * @param other matrix to perform element-wise multiplication with
     * @return this matrix for method chaining
     */
    public TMat2f scale(TMat2f other) {
        this.m00 *= other.m00; this.m01 *= other.m01;
        this.m10 *= other.m10; this.m11 *= other.m11;
        return this;
    }

    public TMat2f scale(float scale) {
        this.m00 *= scale; this.m01 *= scale;
        this.m10 *= scale; this.m11 *= scale;
        return this;
    }

    public TMat2f scaleM00(float x) { this.m00 *= x; return this; }
    public TMat2f scaleM01(float x) { this.m01 *= x; return this; }
    public TMat2f scaleM10(float x) { this.m10 *= x; return this; }
    public TMat2f scaleM11(float x) { this.m11 *= x; return this; }

    // (ELEMENT WISE) Division

    public TMat2f div(float d00, float d01, float d10, float d11) {
        this.m00 /= d00; this.m01 /= d01;
        this.m10 /= d10; this.m11 /= d11;
        return this;
    }

    public TMat2f div(TMat2f other) {
        this.m00 /= other.m00; this.m01 /= other.m01;
        this.m10 /= other.m10; this.m11 /= other.m11;
        return this;
    }

    public TMat2f div(float scale) {
        this.m00 /= scale; this.m01 /= scale;
        this.m10 /= scale; this.m11 /= scale;
        return this;
    }

    public TMat2f divM00(float x) { this.m00 /= x; return this; }
    public TMat2f divM01(float x) { this.m01 /= x; return this; }
    public TMat2f divM10(float x) { this.m10 /= x; return this; }
    public TMat2f divM11(float x) { this.m11 /= x; return this; }


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
    public TMat2f mul(float m00, float m01, float m10, float m11) {
        final float newM00 = this.m00 * m00 + this.m01 * m10;
        final float newM01 = this.m00 * m01 + this.m01 * m11;
        final float newM10 = this.m10 * m00 + this.m11 * m10;
        final float newM11 = this.m10 * m01 + this.m11 * m11;

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
    public TMat2f mul(TMat2f other) {
        return this.mul(other.m00, other.m01, other.m10, other.m11);
    }

    // Transposition

    /**
     * Performs in-place matrix transposition on this matrix like so: {@code this = transpose(this)}.
     *
     * @return this matrix for method chaining
     */
    public TMat2f transpose() {
        // Just swap m01 and m10 to transpose it
        final float temp = this.m01;
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
    public TMat2f invert() {
        final float determinant = this.determinant();
        final float s = 1f / determinant;

        final float temp = this.m00;
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
     * @param function any float-to-float function
     * @return this matrix for method chaining
     */
    public TMat2f map(FloatUnaryOperator function) {
        this.m00 = function.applyAsFloat(this.m00);
        this.m01 = function.applyAsFloat(this.m01);
        this.m10 = function.applyAsFloat(this.m10);
        this.m11 = function.applyAsFloat(this.m11);
        return this;
    }

    public TMat2f sin() { return map(TMath::sin); }
    public TMat2f cos() { return map(TMath::cos); }
    public TMat2f tan() { return map(TMath::tan); }
    public TMat2f sinh() { return map(TMath::sinh); }
    public TMat2f cosh() { return map(TMath::cosh); }
    public TMat2f tanh() { return map(TMath::tanh); }
    public TMat2f arcsin() { return map(TMath::arcsin); }
    public TMat2f arccos() { return map(TMath::arccos); }
    public TMat2f arctan() { return map(TMath::arctan); }

    public TMat2f abs() { return map(TMath::abs); }
    public TMat2f floor() { return map(TMath::floor); }
    public TMat2f ceil() { return map(TMath::ceil); }
    public TMat2f sqrt() { return map(TMath::sqrt); }
    public TMat2f cbrt() { return map(TMath::cbrt); }
    public TMat2f square() { return map(TMath::square); }
    public TMat2f cube() { return map(TMath::cube); }


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
    public TMat2f permuteGridFlipHorizontally() {
        // Swap m00 and m01
        float temp = this.m00;
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
    public TMat2f permuteGridFlipVertically() {
        // Swap m00 and m10
        float temp = this.m00;
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
    public TMat2f permuteGridRotate90Clockwise() {
        float temp = this.m10;
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
    public TMat2f permuteGridRotate90AntiClockwise() {
        float temp = this.m10;
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
    public TMat2f permuteGridRotate180() {
        // Swap m01 and m10
        float temp = this.m01;
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

    public static float determinant(float m00, float m01, float m10, float m11) {
        return m00 * m11 - m01 * m10;
    }

    public float determinant() {
        return determinant(this.m00, this.m01, this.m10, this.m11);
    }

    public float trace() {
        return this.m00 + this.m11;
    }


    /*///////////////////////////////////////////////////////////////*/
    /*/////////////////////  GETTERS & SETTERS  /////////////////////*/
    /*///////////////////////////////////////////////////////////////*/

    // Getters

    public float getM00() { return this.m00; }
    public float getM01() { return this.m01; }
    public float getM10() { return this.m10; }
    public float getM11() { return this.m11; }

    public float[][] toArray(){
        return new float[][] {
            { this.m00, this.m01 },
            { this.m10, this.m11 }
        };
    }

    // Setters

    public TMat2f setM00(float m00) { this.m00 = m00; return this; }
    public TMat2f setM01(float m01) { this.m01 = m01; return this; }
    public TMat2f setM10(float m10) { this.m10 = m10; return this; }
    public TMat2f setM11(float m11) { this.m11 = m11; return this; }

    public TMat2f set(float m00, float m01, float m10, float m11) {
        this.m00 = m00; this.m01 = m01;
        this.m10 = m10; this.m11 = m11;
        return this;
    }

    public TMat2f set(TMat2f other) {
        this.m00 = other.m00; this.m01 = other.m01;
        this.m10 = other.m10; this.m11 = other.m11;
        return this;
    }

    // Min max sum getters for rows and cols

    public float getMinOfRow0() { return TMath.min(this.m00, this.m01); }
    public float getMaxOfRow0() { return TMath.max(this.m00, this.m01); }
    public float getSumOfRow0() { return this.m00 + this.m01; }

    public float getMinOfRow1() { return TMath.min(this.m10, this.m11); }
    public float getMaxOfRow1() { return TMath.max(this.m10, this.m11); }
    public float getSumOfRow1() { return this.m10 + this.m11; }

    public float getMinOfCol0() { return TMath.min(this.m00, this.m10); }
    public float getMaxOfCol0() { return TMath.max(this.m00, this.m10); }
    public float getSumOfCol0() { return this.m00 + this.m10; }

    public float getMinOfCol1() { return TMath.min(this.m01, this.m11); }
    public float getMaxOfCol1() { return TMath.max(this.m01, this.m11); }
    public float getSumOfCol1() { return this.m01 + this.m11; }

    // Indexed getters & setters

    public float get(int row, int col) {
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

    public TMat2f set(int row, int col, float value) {
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

    public float getMinOfRow(int row) {
        if (row == 0) return getMinOfRow0();
        if (row == 1) return getMinOfRow1();
        throw new TephriumException("2x2 Matrix row index out of range: %d", row);
    }

    public float getMaxOfRow(int row) {
        if (row == 0) return getMaxOfRow0();
        if (row == 1) return getMaxOfRow1();
        throw new TephriumException("2x2 Matrix row index out of range: %d", row);
    }

    public float getSumOfRow(int row) {
        if (row == 0) return getSumOfRow0();
        if (row == 1) return getSumOfRow1();
        throw new TephriumException("2x2 Matrix row index out of range: %d", row);
    }

    public float getMinOfCol(int col) {
        if (col == 0) return getMinOfCol0();
        if (col == 1) return getMinOfCol1();
        throw new TephriumException("2x2 Matrix column index out of range: %d", col);
    }

    public float getMaxOfCol(int col) {
        if (col == 0) return getMaxOfCol0();
        if (col == 1) return getMaxOfCol1();
        throw new TephriumException("2x2 Matrix column index out of range: %d", col);
    }

    public float getSumOfCol(int col) {
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

    public boolean isSingular(float det) {
        return TMath.equals(det, 0f);
    }

    public boolean isInvertible(float det) {
        return !this.isSingular(det);
    }

    public boolean isSingular() {
        return TMath.equals(this.determinant(), 0f);
    }

    public boolean isInvertible() {
        return !this.isSingular();
    }

    public boolean isIdentity() {
        return this.equals(
            1f, 0f,
            0f, 1f
        );
    }

    public boolean isZero() {
        return this.equals(
            0f, 0f,
            0f, 0f
        );
    }


    /*//////////////////////////////////////////////////////////////////*/
    /*////////////////////////  OBJECT METHODS  ////////////////////////*/
    /*//////////////////////////////////////////////////////////////////*/

    public boolean equals(TMat2f other) {
        if (other == null) return false;
        return TMath.equals(this.m00, other.m00) && TMath.equals(this.m01, other.m01) &&
               TMath.equals(this.m10, other.m10) && TMath.equals(this.m11, other.m11);
    }

    public boolean equals(float m00, float m01, float m10, float m11) {
        return TMath.equals(this.m00, m00) && TMath.equals(this.m01, m01) &&
               TMath.equals(this.m10, m10) && TMath.equals(this.m11, m11);
    }

    @Override
    public String toString() {
        return String.format(
            Locale.ROOT,
            "[%.5f, %.5f]\n[%.5f, %.5f]\n",
            this.m00, this.m01,
            this.m10, this.m11
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        return this.equals((TMat2f) o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            this.m00, this.m01,
            this.m10, this.m11
        );
    }


}

