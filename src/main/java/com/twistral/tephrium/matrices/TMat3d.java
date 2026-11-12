

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
 * A fast, mutable, 3x3, double valued, row major matrix class with method chaining support.
 */
public class TMat3d {

    private double m00, m01, m02,     // [ m00, m01, m02 ]
                   m10, m11, m12,     // [ m10, m11, m12 ]
                   m20, m21, m22;     // [ m20, m21, m22 ]

    // Actual constructors

    public TMat3d(double m00, double m01, double m02,
                  double m10, double m11, double m12,
                  double m20, double m21, double m22)
    {
        this.m00 = m00;  this.m01 = m01;  this.m02 = m02;  // [ m00, m01, m02 ]
        this.m10 = m10;  this.m11 = m11;  this.m12 = m12;  // [ m10, m11, m12 ]
        this.m20 = m20;  this.m21 = m21;  this.m22 = m22;  // [ m20, m21, m22 ]
    }

    public TMat3d(double fillValue) {
        this(
            fillValue, fillValue, fillValue,
            fillValue, fillValue, fillValue,
            fillValue, fillValue, fillValue
        );
    }

    /** Returns I<sub>3</sub> which is the 3x3 identity matrix */
    public TMat3d() {
        this(
            1d, 0d, 0d,
            0d, 1d, 0d,
            0d, 0d, 1d
        );
    }

    // Copying constructors & functions

    public TMat3d(TMat3d other) {
        this(
            other.m00, other.m01, other.m02,
            other.m10, other.m11, other.m12,
            other.m20, other.m21, other.m22
        );
    }

    public TMat3d copy() {
        return new TMat3d(
            this.m00, this.m01, this.m02,
            this.m10, this.m11, this.m12,
            this.m20, this.m21, this.m22
        );
    }

    // Special static factory methods

    public static TMat3d zero() {
        return new TMat3d(0d);
    }

    /** Returns a diagonal matrix with the specified diagonal values */
    public static TMat3d diagonal(double d0, double d1, double d2) {
        return new TMat3d(
            d0, 0d, 0d,
            0d, d1, 0d,
            0d, 0d, d2
        );
    }

    /** Returns a 3D scaling matrix */
    public static TMat3d scaling(double sx, double sy, double sz) {
        return new TMat3d(
            sx, 0d, 0d,
            0d, sy, 0d,
            0d, 0d, sz
        );
    }

    /** Returns a uniform 3D scaling matrix */
    public static TMat3d scaling(double scale) {
        return scaling(scale, scale, scale);
    }

    /** Returns a 3D rotation matrix around the X-axis for the given cos and sin values. */
    public static TMat3d rotationX(double cos, double sin) { // "Pitch"
        return new TMat3d(
            1d, 0d, 0d,
            0d, cos, -sin,
            0d, sin, cos
        );
    }

    public static TMat3d rotationXRad(double radians) {
        return rotationX(TMath.cos(radians), TMath.sin(radians));
    }

    public static TMat3d rotationXDeg(double degrees) {
        return rotationXRad(TMath.toRadians(degrees));
    }

    /** Returns a 3D rotation matrix around the Y-axis for the given cos and sin values. */
    public static TMat3d rotationY(double cos, double sin) { // "Yaw"
        return new TMat3d(
            cos, 0d, sin,
            0d, 1d, 0d,
            -sin, 0d, cos
        );
    }

    public static TMat3d rotationYRad(double radians) {
        return rotationY(TMath.cos(radians), TMath.sin(radians));
    }

    public static TMat3d rotationYDeg(double degrees) {
        return rotationYRad(TMath.toRadians(degrees));
    }

    /** Returns a 3D rotation matrix around the Z-axis for the given cos and sin values. */
    public static TMat3d rotationZ(double cos, double sin) { // "Roll"
        return new TMat3d(
            cos, -sin, 0d,
            sin, cos, 0d,
            0d, 0d, 1d
        );
    }

    public static TMat3d rotationZRad(double radians) {
        return rotationZ(TMath.cos(radians), TMath.sin(radians));
    }

    public static TMat3d rotationZDeg(double degrees) {
        return rotationZRad(TMath.toRadians(degrees));
    }

    public static TMat3d translation(double x, double y) {
        return new TMat3d(
            1d, 0d, x,
            0d, 1d, y,
            0d, 0d, 1d
        );
    }

    public static TMat3d translation(TVec2d translation) {
        return translation(translation.getX(), translation.getY());
    }

    public static TMat3d fromRows(TVec3d row0, TVec3d row1, TVec3d row2) {
        return new TMat3d(
            row0.getX(), row0.getY(), row0.getZ(),
            row1.getX(), row1.getY(), row1.getZ(),
            row2.getX(), row2.getY(), row2.getZ()
        );
    }

    public static TMat3d fromCols(TVec3d col0, TVec3d col1, TVec3d col2) {
        return new TMat3d(
            col0.getX(), col1.getX(), col2.getX(),
            col0.getY(), col1.getY(), col2.getY(),
            col0.getZ(), col1.getZ(), col2.getZ()
        );
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*////////////////////  ARITHMETIC OPERATIONS  ////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    // Addition

    public TMat3d add(double m00, double m01, double m02,
                      double m10, double m11, double m12,
                      double m20, double m21, double m22)
    {
        this.m00 += m00; this.m01 += m01; this.m02 += m02;
        this.m10 += m10; this.m11 += m11; this.m12 += m12;
        this.m20 += m20; this.m21 += m21; this.m22 += m22;
        return this;
    }

    public TMat3d add(TMat3d other) {
        this.m00 += other.m00; this.m01 += other.m01; this.m02 += other.m02;
        this.m10 += other.m10; this.m11 += other.m11; this.m12 += other.m12;
        this.m20 += other.m20; this.m21 += other.m21; this.m22 += other.m22;
        return this;
    }

    public TMat3d addM00(double x) { this.m00 += x; return this; }
    public TMat3d addM01(double x) { this.m01 += x; return this; }
    public TMat3d addM02(double x) { this.m02 += x; return this; }
    public TMat3d addM10(double x) { this.m10 += x; return this; }
    public TMat3d addM11(double x) { this.m11 += x; return this; }
    public TMat3d addM12(double x) { this.m12 += x; return this; }
    public TMat3d addM20(double x) { this.m20 += x; return this; }
    public TMat3d addM21(double x) { this.m21 += x; return this; }
    public TMat3d addM22(double x) { this.m22 += x; return this; }

    // Subtraction

    public TMat3d sub(double m00, double m01, double m02,
                      double m10, double m11, double m12,
                      double m20, double m21, double m22)
    {
        this.m00 -= m00; this.m01 -= m01; this.m02 -= m02;
        this.m10 -= m10; this.m11 -= m11; this.m12 -= m12;
        this.m20 -= m20; this.m21 -= m21; this.m22 -= m22;
        return this;
    }

    public TMat3d sub(TMat3d other) {
        this.m00 -= other.m00; this.m01 -= other.m01; this.m02 -= other.m02;
        this.m10 -= other.m10; this.m11 -= other.m11; this.m12 -= other.m12;
        this.m20 -= other.m20; this.m21 -= other.m21; this.m22 -= other.m22;
        return this;
    }

    public TMat3d subM00(double x) { this.m00 -= x; return this; }
    public TMat3d subM01(double x) { this.m01 -= x; return this; }
    public TMat3d subM02(double x) { this.m02 -= x; return this; }
    public TMat3d subM10(double x) { this.m10 -= x; return this; }
    public TMat3d subM11(double x) { this.m11 -= x; return this; }
    public TMat3d subM12(double x) { this.m12 -= x; return this; }
    public TMat3d subM20(double x) { this.m20 -= x; return this; }
    public TMat3d subM21(double x) { this.m21 -= x; return this; }
    public TMat3d subM22(double x) { this.m22 -= x; return this; }

    // (ELEMENT WISE) Scaling / Multiplication

    public TMat3d scale(double s00, double s01, double s02,
                        double s10, double s11, double s12,
                        double s20, double s21, double s22)
    {
        this.m00 *= s00; this.m01 *= s01; this.m02 *= s02;
        this.m10 *= s10; this.m11 *= s11; this.m12 *= s12;
        this.m20 *= s20; this.m21 *= s21; this.m22 *= s22;
        return this;
    }

    /**
     * Performs element-wise multiplication, NOT matrix multiplication.
     * For matrix multiplication use this method: {@link #mul(TMat3d)}
     *
     * @param other matrix to perform element-wise multiplication with
     * @return this matrix for method chaining
     */
    public TMat3d scale(TMat3d other) {
        this.m00 *= other.m00; this.m01 *= other.m01; this.m02 *= other.m02;
        this.m10 *= other.m10; this.m11 *= other.m11; this.m12 *= other.m12;
        this.m20 *= other.m20; this.m21 *= other.m21; this.m22 *= other.m22;
        return this;
    }

    public TMat3d scale(double scale) {
        this.m00 *= scale; this.m01 *= scale; this.m02 *= scale;
        this.m10 *= scale; this.m11 *= scale; this.m12 *= scale;
        this.m20 *= scale; this.m21 *= scale; this.m22 *= scale;
        return this;
    }

    public TMat3d scaleM00(double x) { this.m00 *= x; return this; }
    public TMat3d scaleM01(double x) { this.m01 *= x; return this; }
    public TMat3d scaleM02(double x) { this.m02 *= x; return this; }
    public TMat3d scaleM10(double x) { this.m10 *= x; return this; }
    public TMat3d scaleM11(double x) { this.m11 *= x; return this; }
    public TMat3d scaleM12(double x) { this.m12 *= x; return this; }
    public TMat3d scaleM20(double x) { this.m20 *= x; return this; }
    public TMat3d scaleM21(double x) { this.m21 *= x; return this; }
    public TMat3d scaleM22(double x) { this.m22 *= x; return this; }

    // (ELEMENT WISE) Division

    public TMat3d div(double d00, double d01, double d02,
                      double d10, double d11, double d12,
                      double d20, double d21, double d22)
    {
        this.m00 /= d00; this.m01 /= d01; this.m02 /= d02;
        this.m10 /= d10; this.m11 /= d11; this.m12 /= d12;
        this.m20 /= d20; this.m21 /= d21; this.m22 /= d22;
        return this;
    }

    public TMat3d div(TMat3d other) {
        this.m00 /= other.m00; this.m01 /= other.m01; this.m02 /= other.m02;
        this.m10 /= other.m10; this.m11 /= other.m11; this.m12 /= other.m12;
        this.m20 /= other.m20; this.m21 /= other.m21; this.m22 /= other.m22;
        return this;
    }

    public TMat3d div(double scale) {
        this.m00 /= scale; this.m01 /= scale; this.m02 /= scale;
        this.m10 /= scale; this.m11 /= scale; this.m12 /= scale;
        this.m20 /= scale; this.m21 /= scale; this.m22 /= scale;
        return this;
    }

    public TMat3d divM00(double x) { this.m00 /= x; return this; }
    public TMat3d divM01(double x) { this.m01 /= x; return this; }
    public TMat3d divM02(double x) { this.m02 /= x; return this; }
    public TMat3d divM10(double x) { this.m10 /= x; return this; }
    public TMat3d divM11(double x) { this.m11 /= x; return this; }
    public TMat3d divM12(double x) { this.m12 /= x; return this; }
    public TMat3d divM20(double x) { this.m20 /= x; return this; }
    public TMat3d divM21(double x) { this.m21 /= x; return this; }
    public TMat3d divM22(double x) { this.m22 /= x; return this; }


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
     * @param m02 other.m02 value
     * @param m10 other.m10 value
     * @param m11 other.m11 value
     * @param m12 other.m12 value
     * @param m20 other.m20 value
     * @param m21 other.m21 value
     * @param m22 other.m22 value
     * @return this matrix for method chaining
     */
    public TMat3d mul(double m00, double m01, double m02,
                      double m10, double m11, double m12,
                      double m20, double m21, double m22)
    {
        double newM00 = this.m00 * m00 + this.m01 * m10 + this.m02 * m20;
        double newM01 = this.m00 * m01 + this.m01 * m11 + this.m02 * m21;
        double newM02 = this.m00 * m02 + this.m01 * m12 + this.m02 * m22;
        double newM10 = this.m10 * m00 + this.m11 * m10 + this.m12 * m20;
        double newM11 = this.m10 * m01 + this.m11 * m11 + this.m12 * m21;
        double newM12 = this.m10 * m02 + this.m11 * m12 + this.m12 * m22;
        double newM20 = this.m20 * m00 + this.m21 * m10 + this.m22 * m20;
        double newM21 = this.m20 * m01 + this.m21 * m11 + this.m22 * m21;
        double newM22 = this.m20 * m02 + this.m21 * m12 + this.m22 * m22;

        this.m00 = newM00; this.m01 = newM01; this.m02 = newM02;
        this.m10 = newM10; this.m11 = newM11; this.m12 = newM12;
        this.m20 = newM20; this.m21 = newM21; this.m22 = newM22;

        return this;
    }

    /**
     * Performs in-place matrix multiplication on this matrix by the parameter matrix.
     * In other words it simply does {@code this = this * other}.
     *
     * @param other the other matrix
     * @return this matrix for method chaining
     */
    public TMat3d mul(TMat3d other) {
        return this.mul(
            other.m00, other.m01, other.m02,
            other.m10, other.m11, other.m12,
            other.m20, other.m21, other.m22
        );
    }

    // Transposition

    /**
     * Performs in-place matrix transposition on this matrix like so: {@code this = transpose(this)}.
     *
     * @return this matrix for method chaining
     */
    public TMat3d transpose() {
        // Swap m01 and m10
        double temp = this.m01;
        this.m01 = this.m10;
        this.m10 = temp;
        // Swap m02 and m20
        temp = this.m02;
        this.m02 = this.m20;
        this.m20 = temp;
        // Swap m12 and m21
        temp = this.m12;
        this.m12 = this.m21;
        this.m21 = temp;
        return this;
    }

    // Inversion

    /**
     * Performs in-place matrix inversion on this matrix like so: {@code this = invert(this)}.
     *
     * @return this matrix for method chaining
     */
    public TMat3d invert() {
        final double determinant = this.determinant();
        final double s = 1d / determinant;

        double newM00 = s * (this.m11 * this.m22 - this.m12 * this.m21);
        double newM01 = s * (this.m02 * this.m21 - this.m01 * this.m22);
        double newM02 = s * (this.m01 * this.m12 - this.m02 * this.m11);
        double newM10 = s * (this.m12 * this.m20 - this.m10 * this.m22);
        double newM11 = s * (this.m00 * this.m22 - this.m02 * this.m20);
        double newM12 = s * (this.m02 * this.m10 - this.m00 * this.m12);
        double newM20 = s * (this.m10 * this.m21 - this.m11 * this.m20);
        double newM21 = s * (this.m01 * this.m20 - this.m00 * this.m21);
        double newM22 = s * (this.m00 * this.m11 - this.m01 * this.m10);

        this.m00 = newM00; this.m01 = newM01; this.m02 = newM02;
        this.m10 = newM10; this.m11 = newM11; this.m12 = newM12;
        this.m20 = newM20; this.m21 = newM21; this.m22 = newM22;

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
    public TMat3d map(DoubleUnaryOperator function) {
        this.m00 = function.applyAsDouble(this.m00);
        this.m01 = function.applyAsDouble(this.m01);
        this.m02 = function.applyAsDouble(this.m02);
        this.m10 = function.applyAsDouble(this.m10);
        this.m11 = function.applyAsDouble(this.m11);
        this.m12 = function.applyAsDouble(this.m12);
        this.m20 = function.applyAsDouble(this.m20);
        this.m21 = function.applyAsDouble(this.m21);
        this.m22 = function.applyAsDouble(this.m22);
        return this;
    }

    public TMat3d sin() { return map(TMath::sin); }
    public TMat3d cos() { return map(TMath::cos); }
    public TMat3d tan() { return map(TMath::tan); }
    public TMat3d sinh() { return map(TMath::sinh); }
    public TMat3d cosh() { return map(TMath::cosh); }
    public TMat3d tanh() { return map(TMath::tanh); }
    public TMat3d arcsin() { return map(TMath::arcsin); }
    public TMat3d arccos() { return map(TMath::arccos); }
    public TMat3d arctan() { return map(TMath::arctan); }

    public TMat3d abs() { return map(TMath::abs); }
    public TMat3d floor() { return map(TMath::floor); }
    public TMat3d ceil() { return map(TMath::ceil); }
    public TMat3d sqrt() { return map(TMath::sqrt); }
    public TMat3d cbrt() { return map(TMath::cbrt); }
    public TMat3d square() { return map(TMath::square); }
    public TMat3d cube() { return map(TMath::cube); }


    /*/////////////////////////////////////////////////////////////////*/
    /*//////////////////  GRID ELEMENT PERMUTATIONS  //////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    /**
     * Horizontally flips the elements of this matrix grid.
     * <b>Keep in mind that this is NOT a geometric space transformation!</b><br>
     *
     * Example from left to right: <br>
     * {@code [1 2 3] => [3 2 1]} <br>
     * {@code [4 5 6] => [6 5 4]} <br>
     * {@code [7 8 9] => [9 8 7]}
     *
     * @return this matrix for method chaining
     */
    public TMat3d permuteGridFlipHorizontally() {
        // Swap m00 and m02
        double temp = this.m00;
        this.m00 = this.m02;
        this.m02 = temp;
        // Swap m10 and m12
        temp = this.m10;
        this.m10 = this.m12;
        this.m12 = temp;
        // Swap m20 and m22
        temp = this.m20;
        this.m20 = this.m22;
        this.m22 = temp;
        return this;
    }

    /**
     * Vertically flips the elements of this matrix grid.
     * <b>Keep in mind that this is NOT a geometric space transformation!</b><br>
     *
     * Example from left to right: <br>
     * {@code [1 2 3] => [7 8 9]} <br>
     * {@code [4 5 6] => [4 5 6]} <br>
     * {@code [7 8 9] => [1 2 3]}
     *
     * @return this matrix for method chaining
     */
    public TMat3d permuteGridFlipVertically() {
        // Swap m00 and m20
        double temp = this.m00;
        this.m00 = this.m20;
        this.m20 = temp;
        // Swap m01 and m21
        temp = this.m01;
        this.m01 = this.m21;
        this.m21 = temp;
        // Swap m02 and m22
        temp = this.m02;
        this.m02 = this.m22;
        this.m22 = temp;
        return this;
    }

    /**
     * Rotates the elements of this matrix grid by 90 degrees clockwise.
     * <b>Keep in mind that this is NOT a geometric space transformation!</b><br>
     *
     * Example from left to right: <br>
     * {@code [1 2 3] => [7 4 1]} <br>
     * {@code [4 5 6] => [8 5 2]} <br>
     * {@code [7 8 9] => [9 6 3]}
     *
     * @return this matrix for method chaining
     */
    public TMat3d permuteGridRotate90Clockwise() {
        double temp = this.m20;
        this.m20 = this.m22;
        this.m22 = this.m02;
        this.m02 = this.m00;
        this.m00 = temp;
        temp = this.m01;
        this.m01 = this.m10;
        this.m10 = this.m21;
        this.m21 = this.m12;
        this.m12 = temp;
        return this;
    }

    /**
     * Rotates the elements of this matrix grid by 90 degrees anti-clockwise.
     * <b>Keep in mind that this is NOT a geometric space transformation!</b><br>
     *
     * Example from left to right: <br>
     * {@code [1 2 3] => [3 6 9]} <br>
     * {@code [4 5 6] => [2 5 8]} <br>
     * {@code [7 8 9] => [1 4 7]}
     *
     * @return this matrix for method chaining
     */
    public TMat3d permuteGridRotate90AntiClockwise() {
        double temp = this.m20;
        this.m20 = this.m00;
        this.m00 = this.m02;
        this.m02 = this.m22;
        this.m22 = temp;
        temp = this.m10;
        this.m10 = this.m01;
        this.m01 = this.m12;
        this.m12 = this.m21;
        this.m21 = temp;
        return this;
    }

    /**
     * Rotates the elements of this matrix grid by 180 degrees.
     * <b>Keep in mind that this is NOT a geometric space transformation!</b><br>
     *
     * Example from left to right: <br>
     * {@code [1 2 3] => [9 8 7]} <br>
     * {@code [4 5 6] => [6 5 4]} <br>
     * {@code [7 8 9] => [3 2 1]}
     *
     * @return this matrix for method chaining
     */
    public TMat3d permuteGridRotate180() {
        // Swap m10 and m12
        double temp = this.m10;
        this.m10 = this.m12;
        this.m12 = temp;
        // Swap m22 and m00
        temp = this.m22;
        this.m22 = this.m00;
        this.m00 = temp;
        // Swap m20 and m02
        temp = this.m20;
        this.m20 = this.m02;
        this.m02 = temp;
        // Swap m21 and m01
        temp = this.m21;
        this.m21 = this.m01;
        this.m01 = temp;
        return this;
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  METRICS  ///////////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    public static double determinant(double m00, double m01, double m02,
                                     double m10, double m11, double m12,
                                     double m20, double m21, double m22)
    {
        return (m00 * m11 * m22) + (m10 * m21 * m02) + (m20 * m01 * m12)
             - (m20 * m11 * m02) - (m12 * m21 * m00) - (m10 * m01 * m22);
    }

    public double determinant() {
        return determinant(
            this.m00, this.m01, this.m02,
            this.m10, this.m11, this.m12,
            this.m20, this.m21, this.m22
        );
    }

    public double trace() {
        return this.m00 + this.m11 + this.m22;
    }


    /*///////////////////////////////////////////////////////////////*/
    /*/////////////////////  GETTERS & SETTERS  /////////////////////*/
    /*///////////////////////////////////////////////////////////////*/

    // Getters

    public double getM00() { return this.m00; }
    public double getM01() { return this.m01; }
    public double getM02() { return this.m02; }
    public double getM10() { return this.m10; }
    public double getM11() { return this.m11; }
    public double getM12() { return this.m12; }
    public double getM20() { return this.m20; }
    public double getM21() { return this.m21; }
    public double getM22() { return this.m22; }

    public double[][] toArray(){
        return new double[][] {
            { this.m00, this.m01, this.m02 },
            { this.m10, this.m11, this.m12 },
            { this.m20, this.m21, this.m22 }
        };
    }

    // Setters

    public TMat3d setM00(double m00) { this.m00 = m00; return this; }
    public TMat3d setM01(double m01) { this.m01 = m01; return this; }
    public TMat3d setM02(double m02) { this.m02 = m02; return this; }
    public TMat3d setM10(double m10) { this.m10 = m10; return this; }
    public TMat3d setM11(double m11) { this.m11 = m11; return this; }
    public TMat3d setM12(double m12) { this.m12 = m12; return this; }
    public TMat3d setM20(double m20) { this.m20 = m20; return this; }
    public TMat3d setM21(double m21) { this.m21 = m21; return this; }
    public TMat3d setM22(double m22) { this.m22 = m22; return this; }

    public TMat3d set(double m00, double m01, double m02,
                      double m10, double m11, double m12,
                      double m20, double m21, double m22)
    {
        this.m00 = m00; this.m01 = m01; this.m02 = m02;
        this.m10 = m10; this.m11 = m11; this.m12 = m12;
        this.m20 = m20; this.m21 = m21; this.m22 = m22;
        return this;
    }

    public TMat3d set(TMat3d other) {
        this.m00 = other.m00; this.m01 = other.m01; this.m02 = other.m02;
        this.m10 = other.m10; this.m11 = other.m11; this.m12 = other.m12;
        this.m20 = other.m20; this.m21 = other.m21; this.m22 = other.m22;
        return this;
    }

    // Min max sum getters for rows and cols

    public double getMinOfRow0() { return TMath.min(this.m00, this.m01, this.m02); }
    public double getMaxOfRow0() { return TMath.max(this.m00, this.m01, this.m02); }
    public double getSumOfRow0() { return this.m00 + this.m01 + this.m02; }

    public double getMinOfRow1() { return TMath.min(this.m10, this.m11, this.m12); }
    public double getMaxOfRow1() { return TMath.max(this.m10, this.m11, this.m12); }
    public double getSumOfRow1() { return this.m10 + this.m11 + this.m12; }

    public double getMinOfRow2() { return TMath.min(this.m20, this.m21, this.m22); }
    public double getMaxOfRow2() { return TMath.max(this.m20, this.m21, this.m22); }
    public double getSumOfRow2() { return this.m20 + this.m21 + this.m22; }

    public double getMinOfCol0() { return TMath.min(this.m00, this.m10, this.m20); }
    public double getMaxOfCol0() { return TMath.max(this.m00, this.m10, this.m20); }
    public double getSumOfCol0() { return this.m00 + this.m10 + this.m20; }

    public double getMinOfCol1() { return TMath.min(this.m01, this.m11, this.m21); }
    public double getMaxOfCol1() { return TMath.max(this.m01, this.m11, this.m21); }
    public double getSumOfCol1() { return this.m01 + this.m11 + this.m21; }

    public double getMinOfCol2() { return TMath.min(this.m02, this.m12, this.m22); }
    public double getMaxOfCol2() { return TMath.max(this.m02, this.m12, this.m22); }
    public double getSumOfCol2() { return this.m02 + this.m12 + this.m22; }

    // Indexed getters & setters

    public double get(int row, int col) {
        if (row == 0) {
            if (col == 0) return this.m00;
            if (col == 1) return this.m01;
            if (col == 2) return this.m02;
        }
        if (row == 1) {
            if (col == 0) return this.m10;
            if (col == 1) return this.m11;
            if (col == 2) return this.m12;
        }
        if (row == 2) {
            if (col == 0) return this.m20;
            if (col == 1) return this.m21;
            if (col == 2) return this.m22;
        }

        throw new TephriumException("3x3 Matrix indices out of range: row=%d, col=%d", row, col);
    }

    public TMat3d set(int row, int col, double value) {
        if (row == 0) {
            if (col == 0) { this.m00 = value; return this; }
            if (col == 1) { this.m01 = value; return this; }
            if (col == 2) { this.m02 = value; return this; }
        }
        if (row == 1) {
            if (col == 0) { this.m10 = value; return this; }
            if (col == 1) { this.m11 = value; return this; }
            if (col == 2) { this.m12 = value; return this; }
        }
        if (row == 2) {
            if (col == 0) { this.m20 = value; return this; }
            if (col == 1) { this.m21 = value; return this; }
            if (col == 2) { this.m22 = value; return this; }
        }

        throw new TephriumException("3x3 Matrix indices out of range: row=%d, col=%d", row, col);
    }

    public double getMinOfRow(int row) {
        if (row == 0) return getMinOfRow0();
        if (row == 1) return getMinOfRow1();
        if (row == 2) return getMinOfRow2();
        throw new TephriumException("3x3 Matrix row index out of range: %d", row);
    }

    public double getMaxOfRow(int row) {
        if (row == 0) return getMaxOfRow0();
        if (row == 1) return getMaxOfRow1();
        if (row == 2) return getMaxOfRow2();
        throw new TephriumException("3x3 Matrix row index out of range: %d", row);
    }

    public double getSumOfRow(int row) {
        if (row == 0) return getSumOfRow0();
        if (row == 1) return getSumOfRow1();
        if (row == 2) return getSumOfRow2();
        throw new TephriumException("3x3 Matrix row index out of range: %d", row);
    }

    public double getMinOfCol(int col) {
        if (col == 0) return getMinOfCol0();
        if (col == 1) return getMinOfCol1();
        if (col == 2) return getMinOfCol2();
        throw new TephriumException("3x3 Matrix column index out of range: %d", col);
    }

    public double getMaxOfCol(int col) {
        if (col == 0) return getMaxOfCol0();
        if (col == 1) return getMaxOfCol1();
        if (col == 2) return getMaxOfCol2();
        throw new TephriumException("3x3 Matrix column index out of range: %d", col);
    }

    public double getSumOfCol(int col) {
        if (col == 0) return getSumOfCol0();
        if (col == 1) return getSumOfCol1();
        if (col == 2) return getSumOfCol2();
        throw new TephriumException("3x3 Matrix column index out of range: %d", col);
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*//////////////////////  BOOLEAN FUNCTIONS  //////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    public boolean isSymmetrical() {
        return TMath.equals(this.m01, this.m10) &&
               TMath.equals(this.m02, this.m20) &&
               TMath.equals(this.m21, this.m12);
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
            1d, 0d, 0d,
            0d, 1d, 0d,
            0d, 0d, 1d
        );
    }

    public boolean isZero() {
        return this.equals(
            0d, 0d, 0d,
            0d, 0d, 0d,
            0d, 0d, 0d
        );
    }


    /*//////////////////////////////////////////////////////////////////*/
    /*////////////////////////  OBJECT METHODS  ////////////////////////*/
    /*//////////////////////////////////////////////////////////////////*/

    public boolean equals(TMat3d other) {
        if (other == null) return false;
        return TMath.equals(this.m00, other.m00) && TMath.equals(this.m01, other.m01) &&
               TMath.equals(this.m02, other.m02) && TMath.equals(this.m10, other.m10) &&
               TMath.equals(this.m11, other.m11) && TMath.equals(this.m12, other.m12) &&
               TMath.equals(this.m20, other.m20) && TMath.equals(this.m21, other.m21) &&
               TMath.equals(this.m22, other.m22);
    }

    public boolean equals(double m00, double m01, double m02,
                          double m10, double m11, double m12,
                          double m20, double m21, double m22)
    {
        return TMath.equals(this.m00, m00) && TMath.equals(this.m01, m01) &&
               TMath.equals(this.m02, m02) && TMath.equals(this.m10, m10) &&
               TMath.equals(this.m11, m11) && TMath.equals(this.m12, m12) &&
               TMath.equals(this.m20, m20) && TMath.equals(this.m21, m21) &&
               TMath.equals(this.m22, m22);
    }

    @Override
    public String toString() {
        return String.format(
            "[%.5f, %.5f, %.5f]\n[%.5f, %.5f, %.5f]\n[%.5f, %.5f, %.5f]\n",
            this.m00, this.m01, this.m02,
            this.m10, this.m11, this.m12,
            this.m20, this.m21, this.m22
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        return this.equals((TMat3d) o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            this.m00, this.m01, this.m02,
            this.m10, this.m11, this.m12,
            this.m20, this.m21, this.m22
        );
    }


}

