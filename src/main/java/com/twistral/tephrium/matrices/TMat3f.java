

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
import com.twistral.tephrium.functions.*;
import com.twistral.tephrium.vectors.*;
import java.util.*;
import java.util.function.*;


/**
 * A fast, mutable, 3x3, float valued, row major matrix class with method chaining support.
 */
public class TMat3f {

    private float m00, m01, m02,     // [ m00, m01, m02 ]
                  m10, m11, m12,     // [ m10, m11, m12 ]
                  m20, m21, m22;     // [ m20, m21, m22 ]

    // Actual constructors

    public TMat3f(float m00, float m01, float m02,
                  float m10, float m11, float m12,
                  float m20, float m21, float m22)
    {
        this.m00 = m00;  this.m01 = m01;  this.m02 = m02;  // [ m00, m01, m02 ]
        this.m10 = m10;  this.m11 = m11;  this.m12 = m12;  // [ m10, m11, m12 ]
        this.m20 = m20;  this.m21 = m21;  this.m22 = m22;  // [ m20, m21, m22 ]
    }

    public TMat3f(float fillValue) {
        this(
            fillValue, fillValue, fillValue,
            fillValue, fillValue, fillValue,
            fillValue, fillValue, fillValue
        );
    }

    /** Returns I<sub>3</sub> which is the 3x3 identity matrix */
    public TMat3f() {
        this(
            1f, 0f, 0f,
            0f, 1f, 0f,
            0f, 0f, 1f
        );
    }

    // Copying constructors & functions

    public TMat3f(TMat3f other) {
        this(
            other.m00, other.m01, other.m02,
            other.m10, other.m11, other.m12,
            other.m20, other.m21, other.m22
        );
    }

    public TMat3f copy() {
        return new TMat3f(
            this.m00, this.m01, this.m02,
            this.m10, this.m11, this.m12,
            this.m20, this.m21, this.m22
        );
    }

    // Special static factory methods

    public static TMat3f zero() {
        return new TMat3f(0f);
    }

    /** Returns a diagonal matrix with the specified diagonal values */
    public static TMat3f diagonal(float d0, float d1, float d2) {
        return new TMat3f(
            d0, 0f, 0f,
            0f, d1, 0f,
            0f, 0f, d2
        );
    }

    /** Returns a 3D scaling matrix */
    public static TMat3f scaling(float sx, float sy, float sz) {
        return new TMat3f(
            sx, 0f, 0f,
            0f, sy, 0f,
            0f, 0f, sz
        );
    }

    /** Returns a uniform 3D scaling matrix */
    public static TMat3f scaling(float scale) {
        return scaling(scale, scale, scale);
    }

    /** Returns a 3D rotation matrix around the X-axis for the given cos and sin values. */
    public static TMat3f rotationX(float cos, float sin) { // "Pitch"
        return new TMat3f(
            1f, 0f, 0f,
            0f, cos, -sin,
            0f, sin, cos
        );
    }

    public static TMat3f rotationXRad(float radians) {
        return rotationX(TMath.cos(radians), TMath.sin(radians));
    }

    public static TMat3f rotationXDeg(float degrees) {
        return rotationXRad(TMath.toRadians(degrees));
    }

    /** Returns a 3D rotation matrix around the Y-axis for the given cos and sin values. */
    public static TMat3f rotationY(float cos, float sin) { // "Yaw"
        return new TMat3f(
            cos, 0f, sin,
            0f, 1f, 0f,
            -sin, 0f, cos
        );
    }

    public static TMat3f rotationYRad(float radians) {
        return rotationY(TMath.cos(radians), TMath.sin(radians));
    }

    public static TMat3f rotationYDeg(float degrees) {
        return rotationYRad(TMath.toRadians(degrees));
    }

    /** Returns a 3D rotation matrix around the Z-axis for the given cos and sin values. */
    public static TMat3f rotationZ(float cos, float sin) { // "Roll"
        return new TMat3f(
            cos, -sin, 0f,
            sin, cos, 0f,
            0f, 0f, 1f
        );
    }

    public static TMat3f rotationZRad(float radians) {
        return rotationZ(TMath.cos(radians), TMath.sin(radians));
    }

    public static TMat3f rotationZDeg(float degrees) {
        return rotationZRad(TMath.toRadians(degrees));
    }

    public static TMat3f translation(float x, float y) {
        return new TMat3f(
            1f, 0f, x,
            0f, 1f, y,
            0f, 0f, 1f
        );
    }

    public static TMat3f translation(TVec2f translation) {
        return translation(translation.getX(), translation.getY());
    }

    public static TMat3f fromRows(TVec3f row0, TVec3f row1, TVec3f row2) {
        return new TMat3f(
            row0.getX(), row0.getY(), row0.getZ(),
            row1.getX(), row1.getY(), row1.getZ(),
            row2.getX(), row2.getY(), row2.getZ()
        );
    }

    public static TMat3f fromCols(TVec3f col0, TVec3f col1, TVec3f col2) {
        return new TMat3f(
            col0.getX(), col1.getX(), col2.getX(),
            col0.getY(), col1.getY(), col2.getY(),
            col0.getZ(), col1.getZ(), col2.getZ()
        );
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*////////////////////  ARITHMETIC OPERATIONS  ////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    // Addition

    public TMat3f add(float m00, float m01, float m02,
                      float m10, float m11, float m12,
                      float m20, float m21, float m22)
    {
        this.m00 += m00; this.m01 += m01; this.m02 += m02;
        this.m10 += m10; this.m11 += m11; this.m12 += m12;
        this.m20 += m20; this.m21 += m21; this.m22 += m22;
        return this;
    }

    public TMat3f add(TMat3f other) {
        this.m00 += other.m00; this.m01 += other.m01; this.m02 += other.m02;
        this.m10 += other.m10; this.m11 += other.m11; this.m12 += other.m12;
        this.m20 += other.m20; this.m21 += other.m21; this.m22 += other.m22;
        return this;
    }

    public TMat3f addM00(float x) { this.m00 += x; return this; }
    public TMat3f addM01(float x) { this.m01 += x; return this; }
    public TMat3f addM02(float x) { this.m02 += x; return this; }
    public TMat3f addM10(float x) { this.m10 += x; return this; }
    public TMat3f addM11(float x) { this.m11 += x; return this; }
    public TMat3f addM12(float x) { this.m12 += x; return this; }
    public TMat3f addM20(float x) { this.m20 += x; return this; }
    public TMat3f addM21(float x) { this.m21 += x; return this; }
    public TMat3f addM22(float x) { this.m22 += x; return this; }

    // Subtraction

    public TMat3f sub(float m00, float m01, float m02,
                      float m10, float m11, float m12,
                      float m20, float m21, float m22)
    {
        this.m00 -= m00; this.m01 -= m01; this.m02 -= m02;
        this.m10 -= m10; this.m11 -= m11; this.m12 -= m12;
        this.m20 -= m20; this.m21 -= m21; this.m22 -= m22;
        return this;
    }

    public TMat3f sub(TMat3f other) {
        this.m00 -= other.m00; this.m01 -= other.m01; this.m02 -= other.m02;
        this.m10 -= other.m10; this.m11 -= other.m11; this.m12 -= other.m12;
        this.m20 -= other.m20; this.m21 -= other.m21; this.m22 -= other.m22;
        return this;
    }

    public TMat3f subM00(float x) { this.m00 -= x; return this; }
    public TMat3f subM01(float x) { this.m01 -= x; return this; }
    public TMat3f subM02(float x) { this.m02 -= x; return this; }
    public TMat3f subM10(float x) { this.m10 -= x; return this; }
    public TMat3f subM11(float x) { this.m11 -= x; return this; }
    public TMat3f subM12(float x) { this.m12 -= x; return this; }
    public TMat3f subM20(float x) { this.m20 -= x; return this; }
    public TMat3f subM21(float x) { this.m21 -= x; return this; }
    public TMat3f subM22(float x) { this.m22 -= x; return this; }

    // (ELEMENT WISE) Scaling / Multiplication

    public TMat3f scale(float s00, float s01, float s02,
                        float s10, float s11, float s12,
                        float s20, float s21, float s22)
    {
        this.m00 *= s00; this.m01 *= s01; this.m02 *= s02;
        this.m10 *= s10; this.m11 *= s11; this.m12 *= s12;
        this.m20 *= s20; this.m21 *= s21; this.m22 *= s22;
        return this;
    }

    /**
     * Performs element-wise multiplication, NOT matrix multiplication.
     * For matrix multiplication use this method: {@link #mul(TMat3f)}
     *
     * @param other matrix to perform element-wise multiplication with
     * @return this matrix for method chaining
     */
    public TMat3f scale(TMat3f other) {
        this.m00 *= other.m00; this.m01 *= other.m01; this.m02 *= other.m02;
        this.m10 *= other.m10; this.m11 *= other.m11; this.m12 *= other.m12;
        this.m20 *= other.m20; this.m21 *= other.m21; this.m22 *= other.m22;
        return this;
    }

    public TMat3f scale(float scale) {
        this.m00 *= scale; this.m01 *= scale; this.m02 *= scale;
        this.m10 *= scale; this.m11 *= scale; this.m12 *= scale;
        this.m20 *= scale; this.m21 *= scale; this.m22 *= scale;
        return this;
    }

    public TMat3f scaleM00(float x) { this.m00 *= x; return this; }
    public TMat3f scaleM01(float x) { this.m01 *= x; return this; }
    public TMat3f scaleM02(float x) { this.m02 *= x; return this; }
    public TMat3f scaleM10(float x) { this.m10 *= x; return this; }
    public TMat3f scaleM11(float x) { this.m11 *= x; return this; }
    public TMat3f scaleM12(float x) { this.m12 *= x; return this; }
    public TMat3f scaleM20(float x) { this.m20 *= x; return this; }
    public TMat3f scaleM21(float x) { this.m21 *= x; return this; }
    public TMat3f scaleM22(float x) { this.m22 *= x; return this; }

    // (ELEMENT WISE) Division

    public TMat3f div(float d00, float d01, float d02,
                      float d10, float d11, float d12,
                      float d20, float d21, float d22)
    {
        this.m00 /= d00; this.m01 /= d01; this.m02 /= d02;
        this.m10 /= d10; this.m11 /= d11; this.m12 /= d12;
        this.m20 /= d20; this.m21 /= d21; this.m22 /= d22;
        return this;
    }

    public TMat3f div(TMat3f other) {
        this.m00 /= other.m00; this.m01 /= other.m01; this.m02 /= other.m02;
        this.m10 /= other.m10; this.m11 /= other.m11; this.m12 /= other.m12;
        this.m20 /= other.m20; this.m21 /= other.m21; this.m22 /= other.m22;
        return this;
    }

    public TMat3f div(float scale) {
        this.m00 /= scale; this.m01 /= scale; this.m02 /= scale;
        this.m10 /= scale; this.m11 /= scale; this.m12 /= scale;
        this.m20 /= scale; this.m21 /= scale; this.m22 /= scale;
        return this;
    }

    public TMat3f divM00(float x) { this.m00 /= x; return this; }
    public TMat3f divM01(float x) { this.m01 /= x; return this; }
    public TMat3f divM02(float x) { this.m02 /= x; return this; }
    public TMat3f divM10(float x) { this.m10 /= x; return this; }
    public TMat3f divM11(float x) { this.m11 /= x; return this; }
    public TMat3f divM12(float x) { this.m12 /= x; return this; }
    public TMat3f divM20(float x) { this.m20 /= x; return this; }
    public TMat3f divM21(float x) { this.m21 /= x; return this; }
    public TMat3f divM22(float x) { this.m22 /= x; return this; }


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
    public TMat3f mul(float m00, float m01, float m02,
                      float m10, float m11, float m12,
                      float m20, float m21, float m22)
    {
        final float newM00 = this.m00 * m00 + this.m01 * m10 + this.m02 * m20;
        final float newM01 = this.m00 * m01 + this.m01 * m11 + this.m02 * m21;
        final float newM02 = this.m00 * m02 + this.m01 * m12 + this.m02 * m22;
        final float newM10 = this.m10 * m00 + this.m11 * m10 + this.m12 * m20;
        final float newM11 = this.m10 * m01 + this.m11 * m11 + this.m12 * m21;
        final float newM12 = this.m10 * m02 + this.m11 * m12 + this.m12 * m22;
        final float newM20 = this.m20 * m00 + this.m21 * m10 + this.m22 * m20;
        final float newM21 = this.m20 * m01 + this.m21 * m11 + this.m22 * m21;
        final float newM22 = this.m20 * m02 + this.m21 * m12 + this.m22 * m22;

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
    public TMat3f mul(TMat3f other) {
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
    public TMat3f transpose() {
        // Swap m01 and m10
        float temp = this.m01;
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
    public TMat3f invert() {
        final float determinant = this.determinant();
        final float s = 1f / determinant;

        final float newM00 = s * (this.m11 * this.m22 - this.m12 * this.m21);
        final float newM01 = s * (this.m02 * this.m21 - this.m01 * this.m22);
        final float newM02 = s * (this.m01 * this.m12 - this.m02 * this.m11);
        final float newM10 = s * (this.m12 * this.m20 - this.m10 * this.m22);
        final float newM11 = s * (this.m00 * this.m22 - this.m02 * this.m20);
        final float newM12 = s * (this.m02 * this.m10 - this.m00 * this.m12);
        final float newM20 = s * (this.m10 * this.m21 - this.m11 * this.m20);
        final float newM21 = s * (this.m01 * this.m20 - this.m00 * this.m21);
        final float newM22 = s * (this.m00 * this.m11 - this.m01 * this.m10);

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
     * @param function any float-to-float function
     * @return this matrix for method chaining
     */
    public TMat3f map(FloatUnaryOperator function) {
        this.m00 = function.applyAsFloat(this.m00);
        this.m01 = function.applyAsFloat(this.m01);
        this.m02 = function.applyAsFloat(this.m02);
        this.m10 = function.applyAsFloat(this.m10);
        this.m11 = function.applyAsFloat(this.m11);
        this.m12 = function.applyAsFloat(this.m12);
        this.m20 = function.applyAsFloat(this.m20);
        this.m21 = function.applyAsFloat(this.m21);
        this.m22 = function.applyAsFloat(this.m22);
        return this;
    }

    public TMat3f sin() { return map(TMath::sin); }
    public TMat3f cos() { return map(TMath::cos); }
    public TMat3f tan() { return map(TMath::tan); }
    public TMat3f sinh() { return map(TMath::sinh); }
    public TMat3f cosh() { return map(TMath::cosh); }
    public TMat3f tanh() { return map(TMath::tanh); }
    public TMat3f arcsin() { return map(TMath::arcsin); }
    public TMat3f arccos() { return map(TMath::arccos); }
    public TMat3f arctan() { return map(TMath::arctan); }

    public TMat3f abs() { return map(TMath::abs); }
    public TMat3f floor() { return map(TMath::floor); }
    public TMat3f ceil() { return map(TMath::ceil); }
    public TMat3f sqrt() { return map(TMath::sqrt); }
    public TMat3f cbrt() { return map(TMath::cbrt); }
    public TMat3f square() { return map(TMath::square); }
    public TMat3f cube() { return map(TMath::cube); }


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
    public TMat3f permuteGridFlipHorizontally() {
        // Swap m00 and m02
        float temp = this.m00;
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
    public TMat3f permuteGridFlipVertically() {
        // Swap m00 and m20
        float temp = this.m00;
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
    public TMat3f permuteGridRotate90Clockwise() {
        float temp = this.m20;
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
    public TMat3f permuteGridRotate90AntiClockwise() {
        float temp = this.m20;
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
    public TMat3f permuteGridRotate180() {
        // Swap m10 and m12
        float temp = this.m10;
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

    public static float determinant(float m00, float m01, float m02,
                                    float m10, float m11, float m12,
                                    float m20, float m21, float m22)
    {
        return (m00 * m11 * m22) + (m10 * m21 * m02) + (m20 * m01 * m12)
             - (m20 * m11 * m02) - (m12 * m21 * m00) - (m10 * m01 * m22);
    }

    public float determinant() {
        return determinant(
            this.m00, this.m01, this.m02,
            this.m10, this.m11, this.m12,
            this.m20, this.m21, this.m22
        );
    }

    public float trace() {
        return this.m00 + this.m11 + this.m22;
    }


    /*///////////////////////////////////////////////////////////////*/
    /*/////////////////////  GETTERS & SETTERS  /////////////////////*/
    /*///////////////////////////////////////////////////////////////*/

    // Getters

    public float getM00() { return this.m00; }
    public float getM01() { return this.m01; }
    public float getM02() { return this.m02; }
    public float getM10() { return this.m10; }
    public float getM11() { return this.m11; }
    public float getM12() { return this.m12; }
    public float getM20() { return this.m20; }
    public float getM21() { return this.m21; }
    public float getM22() { return this.m22; }

    public float[][] toArray(){
        return new float[][] {
            { this.m00, this.m01, this.m02 },
            { this.m10, this.m11, this.m12 },
            { this.m20, this.m21, this.m22 }
        };
    }

    // Setters

    public TMat3f setM00(float m00) { this.m00 = m00; return this; }
    public TMat3f setM01(float m01) { this.m01 = m01; return this; }
    public TMat3f setM02(float m02) { this.m02 = m02; return this; }
    public TMat3f setM10(float m10) { this.m10 = m10; return this; }
    public TMat3f setM11(float m11) { this.m11 = m11; return this; }
    public TMat3f setM12(float m12) { this.m12 = m12; return this; }
    public TMat3f setM20(float m20) { this.m20 = m20; return this; }
    public TMat3f setM21(float m21) { this.m21 = m21; return this; }
    public TMat3f setM22(float m22) { this.m22 = m22; return this; }

    public TMat3f set(float m00, float m01, float m02,
                      float m10, float m11, float m12,
                      float m20, float m21, float m22)
    {
        this.m00 = m00; this.m01 = m01; this.m02 = m02;
        this.m10 = m10; this.m11 = m11; this.m12 = m12;
        this.m20 = m20; this.m21 = m21; this.m22 = m22;
        return this;
    }

    public TMat3f set(TMat3f other) {
        this.m00 = other.m00; this.m01 = other.m01; this.m02 = other.m02;
        this.m10 = other.m10; this.m11 = other.m11; this.m12 = other.m12;
        this.m20 = other.m20; this.m21 = other.m21; this.m22 = other.m22;
        return this;
    }

    // Min max sum getters for rows and cols

    public float getMinOfRow0() { return TMath.min(this.m00, this.m01, this.m02); }
    public float getMaxOfRow0() { return TMath.max(this.m00, this.m01, this.m02); }
    public float getSumOfRow0() { return this.m00 + this.m01 + this.m02; }

    public float getMinOfRow1() { return TMath.min(this.m10, this.m11, this.m12); }
    public float getMaxOfRow1() { return TMath.max(this.m10, this.m11, this.m12); }
    public float getSumOfRow1() { return this.m10 + this.m11 + this.m12; }

    public float getMinOfRow2() { return TMath.min(this.m20, this.m21, this.m22); }
    public float getMaxOfRow2() { return TMath.max(this.m20, this.m21, this.m22); }
    public float getSumOfRow2() { return this.m20 + this.m21 + this.m22; }

    public float getMinOfCol0() { return TMath.min(this.m00, this.m10, this.m20); }
    public float getMaxOfCol0() { return TMath.max(this.m00, this.m10, this.m20); }
    public float getSumOfCol0() { return this.m00 + this.m10 + this.m20; }

    public float getMinOfCol1() { return TMath.min(this.m01, this.m11, this.m21); }
    public float getMaxOfCol1() { return TMath.max(this.m01, this.m11, this.m21); }
    public float getSumOfCol1() { return this.m01 + this.m11 + this.m21; }

    public float getMinOfCol2() { return TMath.min(this.m02, this.m12, this.m22); }
    public float getMaxOfCol2() { return TMath.max(this.m02, this.m12, this.m22); }
    public float getSumOfCol2() { return this.m02 + this.m12 + this.m22; }

    // Indexed getters & setters

    public float get(int row, int col) {
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

    public TMat3f set(int row, int col, float value) {
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

    public float getMinOfRow(int row) {
        if (row == 0) return getMinOfRow0();
        if (row == 1) return getMinOfRow1();
        if (row == 2) return getMinOfRow2();
        throw new TephriumException("3x3 Matrix row index out of range: %d", row);
    }

    public float getMaxOfRow(int row) {
        if (row == 0) return getMaxOfRow0();
        if (row == 1) return getMaxOfRow1();
        if (row == 2) return getMaxOfRow2();
        throw new TephriumException("3x3 Matrix row index out of range: %d", row);
    }

    public float getSumOfRow(int row) {
        if (row == 0) return getSumOfRow0();
        if (row == 1) return getSumOfRow1();
        if (row == 2) return getSumOfRow2();
        throw new TephriumException("3x3 Matrix row index out of range: %d", row);
    }

    public float getMinOfCol(int col) {
        if (col == 0) return getMinOfCol0();
        if (col == 1) return getMinOfCol1();
        if (col == 2) return getMinOfCol2();
        throw new TephriumException("3x3 Matrix column index out of range: %d", col);
    }

    public float getMaxOfCol(int col) {
        if (col == 0) return getMaxOfCol0();
        if (col == 1) return getMaxOfCol1();
        if (col == 2) return getMaxOfCol2();
        throw new TephriumException("3x3 Matrix column index out of range: %d", col);
    }

    public float getSumOfCol(int col) {
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
            1f, 0f, 0f,
            0f, 1f, 0f,
            0f, 0f, 1f
        );
    }

    public boolean isZero() {
        return this.equals(
            0f, 0f, 0f,
            0f, 0f, 0f,
            0f, 0f, 0f
        );
    }


    /*//////////////////////////////////////////////////////////////////*/
    /*////////////////////////  OBJECT METHODS  ////////////////////////*/
    /*//////////////////////////////////////////////////////////////////*/

    public boolean equals(TMat3f other) {
        if (other == null) return false;
        return TMath.equals(this.m00, other.m00) && TMath.equals(this.m01, other.m01) &&
               TMath.equals(this.m02, other.m02) && TMath.equals(this.m10, other.m10) &&
               TMath.equals(this.m11, other.m11) && TMath.equals(this.m12, other.m12) &&
               TMath.equals(this.m20, other.m20) && TMath.equals(this.m21, other.m21) &&
               TMath.equals(this.m22, other.m22);
    }

    public boolean equals(float m00, float m01, float m02,
                          float m10, float m11, float m12,
                          float m20, float m21, float m22)
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
            Locale.ROOT,
            "[%.5f, %.5f, %.5f]\n[%.5f, %.5f, %.5f]\n[%.5f, %.5f, %.5f]\n",
            this.m00, this.m01, this.m02,
            this.m10, this.m11, this.m12,
            this.m20, this.m21, this.m22
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        return this.equals((TMat3f) o);
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

