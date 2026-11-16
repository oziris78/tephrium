

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


/**
 * A fast, mutable, 4x4, float valued, row major matrix class with method chaining support.
 */
public class TMat4f {

    private float m00, m01, m02, m03,     // [ m00, m01, m02, m03 ]
                  m10, m11, m12, m13,     // [ m10, m11, m12, m13 ]
                  m20, m21, m22, m23,     // [ m20, m21, m22, m23 ]
                  m30, m31, m32, m33;     // [ m30, m31, m32, m33 ]

    // Actual constructors

    public TMat4f(float m00, float m01, float m02, float m03,
                  float m10, float m11, float m12, float m13,
                  float m20, float m21, float m22, float m23,
                  float m30, float m31, float m32, float m33)
    {
        this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;  // [ m00, m01, m02, m03 ]
        this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;  // [ m10, m11, m12, m13 ]
        this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;  // [ m20, m21, m22, m23 ]
        this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;  // [ m30, m31, m32, m33 ]
    }

    public TMat4f(float fillValue) {
        this(
            fillValue, fillValue, fillValue, fillValue,
            fillValue, fillValue, fillValue, fillValue,
            fillValue, fillValue, fillValue, fillValue,
            fillValue, fillValue, fillValue, fillValue
        );
    }

    /** Returns I<sub>4</sub> which is the 4x4 identity matrix */
    public TMat4f() {
        this(
            1f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f
        );
    }

    // Copying constructors & functions

    public TMat4f(TMat4f other) {
        this(
            other.m00, other.m01, other.m02, other.m03,
            other.m10, other.m11, other.m12, other.m13,
            other.m20, other.m21, other.m22, other.m23,
            other.m30, other.m31, other.m32, other.m33
        );
    }

    public TMat4f copy() {
        return new TMat4f(
            this.m00, this.m01, this.m02, this.m03,
            this.m10, this.m11, this.m12, this.m13,
            this.m20, this.m21, this.m22, this.m23,
            this.m30, this.m31, this.m32, this.m33
        );
    }

    // Special static factory methods

    public static TMat4f zero() {
        return new TMat4f(0f);
    }

    /** Returns a diagonal matrix with the specified diagonal values */
    public static TMat4f diagonal(float d0, float d1, float d2, float d3) {
        return new TMat4f(
            d0, 0f, 0f, 0f,
            0f, d1, 0f, 0f,
            0f, 0f, d2, 0f,
            0f, 0f, 0f, d3
        );
    }

    /** Returns a 4D scaling matrix */
    public static TMat4f scaling(float sx, float sy, float sz, float sw) {
        return new TMat4f(
            sx, 0f, 0f, 0f,
            0f, sy, 0f, 0f,
            0f, 0f, sz, 0f,
            0f, 0f, 0f, sw
        );
    }

    /** Returns a uniform 4D scaling matrix */
    public static TMat4f scaling(float scale) {
        return scaling(scale, scale, scale, scale);
    }

    /** Returns a 4D rotation matrix around the X-axis for the given cos and sin values. */
    public static TMat4f rotationX(float cos, float sin) { // "Pitch"
        return new TMat4f(
            1f, 0f, 0f, 0f,
            0f, cos, -sin, 0f,
            0f, sin, cos, 0f,
            0f, 0f, 0f, 1f
        );
    }

    public static TMat4f rotationXRad(float radians) {
        return rotationX(TMath.cos(radians), TMath.sin(radians));
    }

    public static TMat4f rotationXDeg(float degrees) {
        return rotationXRad(TMath.toRadians(degrees));
    }

    /** Returns a 4D rotation matrix around the Y-axis for the given cos and sin values. */
    public static TMat4f rotationY(float cos, float sin) { // "Yaw"
        return new TMat4f(
            cos, 0f, sin, 0f,
            0f, 1f, 0f, 0f,
            -sin, 0f, cos, 0f,
            0f, 0f, 0f, 1f
        );
    }

    public static TMat4f rotationYRad(float radians) {
        return rotationY(TMath.cos(radians), TMath.sin(radians));
    }

    public static TMat4f rotationYDeg(float degrees) {
        return rotationYRad(TMath.toRadians(degrees));
    }

    /** Returns a 4D rotation matrix around the Z-axis for the given cos and sin values. */
    public static TMat4f rotationZ(float cos, float sin) { // "Roll"
        return new TMat4f(
            cos, -sin, 0f, 0f,
            sin, cos, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f
        );
    }

    public static TMat4f rotationZRad(float radians) {
        return rotationZ(TMath.cos(radians), TMath.sin(radians));
    }

    public static TMat4f rotationZDeg(float degrees) {
        return rotationZRad(TMath.toRadians(degrees));
    }

    public static TMat4f translation(float x, float y, float z) {
        return new TMat4f(
            1f, 0f, 0f, x,
            0f, 1f, 0f, y,
            0f, 0f, 1f, z,
            0f, 0f, 0f, 1f
        );
    }

    public static TMat4f translation(TVec3f translation) {
        return translation(translation.getX(), translation.getY(), translation.getZ());
    }

    public static TMat4f fromRows(TVec4f row0, TVec4f row1, TVec4f row2, TVec4f row3) {
        return new TMat4f(
            row0.getX(), row0.getY(), row0.getZ(), row0.getW(),
            row1.getX(), row1.getY(), row1.getZ(), row1.getW(),
            row2.getX(), row2.getY(), row2.getZ(), row2.getW(),
            row3.getX(), row3.getY(), row3.getZ(), row3.getW()
        );
    }

    public static TMat4f fromCols(TVec4f col0, TVec4f col1, TVec4f col2, TVec4f col3) {
        return new TMat4f(
            col0.getX(), col1.getX(), col2.getX(), col3.getX(),
            col0.getY(), col1.getY(), col2.getY(), col3.getY(),
            col0.getZ(), col1.getZ(), col2.getZ(), col3.getZ(),
            col0.getW(), col1.getW(), col2.getW(), col3.getW()
        );
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*////////////////////  ARITHMETIC OPERATIONS  ////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    // Addition

    public TMat4f add(float m00, float m01, float m02, float m03,
                      float m10, float m11, float m12, float m13,
                      float m20, float m21, float m22, float m23,
                      float m30, float m31, float m32, float m33)
    {
        this.m00 += m00; this.m01 += m01; this.m02 += m02; this.m03 += m03;
        this.m10 += m10; this.m11 += m11; this.m12 += m12; this.m13 += m13;
        this.m20 += m20; this.m21 += m21; this.m22 += m22; this.m23 += m23;
        this.m30 += m30; this.m31 += m31; this.m32 += m32; this.m33 += m33;
        return this;
    }

    public TMat4f add(TMat4f other) {
        this.m00 += other.m00; this.m01 += other.m01; this.m02 += other.m02; this.m03 += other.m03;
        this.m10 += other.m10; this.m11 += other.m11; this.m12 += other.m12; this.m13 += other.m13;
        this.m20 += other.m20; this.m21 += other.m21; this.m22 += other.m22; this.m23 += other.m23;
        this.m30 += other.m30; this.m31 += other.m31; this.m32 += other.m32; this.m33 += other.m33;
        return this;
    }

    public TMat4f addM00(float x) { this.m00 += x; return this; }
    public TMat4f addM01(float x) { this.m01 += x; return this; }
    public TMat4f addM02(float x) { this.m02 += x; return this; }
    public TMat4f addM03(float x) { this.m03 += x; return this; }
    public TMat4f addM10(float x) { this.m10 += x; return this; }
    public TMat4f addM11(float x) { this.m11 += x; return this; }
    public TMat4f addM12(float x) { this.m12 += x; return this; }
    public TMat4f addM13(float x) { this.m13 += x; return this; }
    public TMat4f addM20(float x) { this.m20 += x; return this; }
    public TMat4f addM21(float x) { this.m21 += x; return this; }
    public TMat4f addM22(float x) { this.m22 += x; return this; }
    public TMat4f addM23(float x) { this.m23 += x; return this; }
    public TMat4f addM30(float x) { this.m30 += x; return this; }
    public TMat4f addM31(float x) { this.m31 += x; return this; }
    public TMat4f addM32(float x) { this.m32 += x; return this; }
    public TMat4f addM33(float x) { this.m33 += x; return this; }

    // Subtraction

    public TMat4f sub(float m00, float m01, float m02, float m03,
                      float m10, float m11, float m12, float m13,
                      float m20, float m21, float m22, float m23,
                      float m30, float m31, float m32, float m33)
    {
        this.m00 -= m00; this.m01 -= m01; this.m02 -= m02; this.m03 -= m03;
        this.m10 -= m10; this.m11 -= m11; this.m12 -= m12; this.m13 -= m13;
        this.m20 -= m20; this.m21 -= m21; this.m22 -= m22; this.m23 -= m23;
        this.m30 -= m30; this.m31 -= m31; this.m32 -= m32; this.m33 -= m33;
        return this;
    }

    public TMat4f sub(TMat4f other) {
        this.m00 -= other.m00; this.m01 -= other.m01; this.m02 -= other.m02; this.m03 -= other.m03;
        this.m10 -= other.m10; this.m11 -= other.m11; this.m12 -= other.m12; this.m13 -= other.m13;
        this.m20 -= other.m20; this.m21 -= other.m21; this.m22 -= other.m22; this.m23 -= other.m23;
        this.m30 -= other.m30; this.m31 -= other.m31; this.m32 -= other.m32; this.m33 -= other.m33;
        return this;
    }

    public TMat4f subM00(float x) { this.m00 -= x; return this; }
    public TMat4f subM01(float x) { this.m01 -= x; return this; }
    public TMat4f subM02(float x) { this.m02 -= x; return this; }
    public TMat4f subM03(float x) { this.m03 -= x; return this; }
    public TMat4f subM10(float x) { this.m10 -= x; return this; }
    public TMat4f subM11(float x) { this.m11 -= x; return this; }
    public TMat4f subM12(float x) { this.m12 -= x; return this; }
    public TMat4f subM13(float x) { this.m13 -= x; return this; }
    public TMat4f subM20(float x) { this.m20 -= x; return this; }
    public TMat4f subM21(float x) { this.m21 -= x; return this; }
    public TMat4f subM22(float x) { this.m22 -= x; return this; }
    public TMat4f subM23(float x) { this.m23 -= x; return this; }
    public TMat4f subM30(float x) { this.m30 -= x; return this; }
    public TMat4f subM31(float x) { this.m31 -= x; return this; }
    public TMat4f subM32(float x) { this.m32 -= x; return this; }
    public TMat4f subM33(float x) { this.m33 -= x; return this; }

    // (ELEMENT WISE) Scaling / Multiplication

    public TMat4f scale(float s00, float s01, float s02, float s03,
                        float s10, float s11, float s12, float s13,
                        float s20, float s21, float s22, float s23,
                        float s30, float s31, float s32, float s33)
    {
        this.m00 *= s00; this.m01 *= s01; this.m02 *= s02; this.m03 *= s03;
        this.m10 *= s10; this.m11 *= s11; this.m12 *= s12; this.m13 *= s13;
        this.m20 *= s20; this.m21 *= s21; this.m22 *= s22; this.m23 *= s23;
        this.m30 *= s30; this.m31 *= s31; this.m32 *= s32; this.m33 *= s33;
        return this;
    }

    /**
     * Performs element-wise multiplication, NOT matrix multiplication.
     * For matrix multiplication use this method: {@link #mul(TMat4f)}
     *
     * @param other matrix to perform element-wise multiplication with
     * @return this matrix for method chaining
     */
    public TMat4f scale(TMat4f other) {
        this.m00 *= other.m00; this.m01 *= other.m01; this.m02 *= other.m02; this.m03 *= other.m03;
        this.m10 *= other.m10; this.m11 *= other.m11; this.m12 *= other.m12; this.m13 *= other.m13;
        this.m20 *= other.m20; this.m21 *= other.m21; this.m22 *= other.m22; this.m23 *= other.m23;
        this.m30 *= other.m30; this.m31 *= other.m31; this.m32 *= other.m32; this.m33 *= other.m33;
        return this;
    }

    public TMat4f scale(float scale) {
        this.m00 *= scale; this.m01 *= scale; this.m02 *= scale; this.m03 *= scale;
        this.m10 *= scale; this.m11 *= scale; this.m12 *= scale; this.m13 *= scale;
        this.m20 *= scale; this.m21 *= scale; this.m22 *= scale; this.m23 *= scale;
        this.m30 *= scale; this.m31 *= scale; this.m32 *= scale; this.m33 *= scale;
        return this;
    }

    public TMat4f scaleM00(float x) { this.m00 *= x; return this; }
    public TMat4f scaleM01(float x) { this.m01 *= x; return this; }
    public TMat4f scaleM02(float x) { this.m02 *= x; return this; }
    public TMat4f scaleM03(float x) { this.m03 *= x; return this; }
    public TMat4f scaleM10(float x) { this.m10 *= x; return this; }
    public TMat4f scaleM11(float x) { this.m11 *= x; return this; }
    public TMat4f scaleM12(float x) { this.m12 *= x; return this; }
    public TMat4f scaleM13(float x) { this.m13 *= x; return this; }
    public TMat4f scaleM20(float x) { this.m20 *= x; return this; }
    public TMat4f scaleM21(float x) { this.m21 *= x; return this; }
    public TMat4f scaleM22(float x) { this.m22 *= x; return this; }
    public TMat4f scaleM23(float x) { this.m23 *= x; return this; }
    public TMat4f scaleM30(float x) { this.m30 *= x; return this; }
    public TMat4f scaleM31(float x) { this.m31 *= x; return this; }
    public TMat4f scaleM32(float x) { this.m32 *= x; return this; }
    public TMat4f scaleM33(float x) { this.m33 *= x; return this; }

    // (ELEMENT WISE) Division

    public TMat4f div(float d00, float d01, float d02, float d03,
                      float d10, float d11, float d12, float d13,
                      float d20, float d21, float d22, float d23,
                      float d30, float d31, float d32, float d33)
    {
        this.m00 /= d00; this.m01 /= d01; this.m02 /= d02; this.m03 /= d03;
        this.m10 /= d10; this.m11 /= d11; this.m12 /= d12; this.m13 /= d13;
        this.m20 /= d20; this.m21 /= d21; this.m22 /= d22; this.m23 /= d23;
        this.m30 /= d30; this.m31 /= d31; this.m32 /= d32; this.m33 /= d33;
        return this;
    }

    public TMat4f div(TMat4f other) {
        this.m00 /= other.m00; this.m01 /= other.m01; this.m02 /= other.m02; this.m03 /= other.m03;
        this.m10 /= other.m10; this.m11 /= other.m11; this.m12 /= other.m12; this.m13 /= other.m13;
        this.m20 /= other.m20; this.m21 /= other.m21; this.m22 /= other.m22; this.m23 /= other.m23;
        this.m30 /= other.m30; this.m31 /= other.m31; this.m32 /= other.m32; this.m33 /= other.m33;
        return this;
    }

    public TMat4f div(float scale) {
        this.m00 /= scale; this.m01 /= scale; this.m02 /= scale; this.m03 /= scale;
        this.m10 /= scale; this.m11 /= scale; this.m12 /= scale; this.m13 /= scale;
        this.m20 /= scale; this.m21 /= scale; this.m22 /= scale; this.m23 /= scale;
        this.m30 /= scale; this.m31 /= scale; this.m32 /= scale; this.m33 /= scale;
        return this;
    }

    public TMat4f divM00(float x) { this.m00 /= x; return this; }
    public TMat4f divM01(float x) { this.m01 /= x; return this; }
    public TMat4f divM02(float x) { this.m02 /= x; return this; }
    public TMat4f divM03(float x) { this.m03 /= x; return this; }
    public TMat4f divM10(float x) { this.m10 /= x; return this; }
    public TMat4f divM11(float x) { this.m11 /= x; return this; }
    public TMat4f divM12(float x) { this.m12 /= x; return this; }
    public TMat4f divM13(float x) { this.m13 /= x; return this; }
    public TMat4f divM20(float x) { this.m20 /= x; return this; }
    public TMat4f divM21(float x) { this.m21 /= x; return this; }
    public TMat4f divM22(float x) { this.m22 /= x; return this; }
    public TMat4f divM23(float x) { this.m23 /= x; return this; }
    public TMat4f divM30(float x) { this.m30 /= x; return this; }
    public TMat4f divM31(float x) { this.m31 /= x; return this; }
    public TMat4f divM32(float x) { this.m32 /= x; return this; }
    public TMat4f divM33(float x) { this.m33 /= x; return this; }


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
     * @param m03 other.m03 value
     * @param m10 other.m10 value
     * @param m11 other.m11 value
     * @param m12 other.m12 value
     * @param m13 other.m13 value
     * @param m20 other.m20 value
     * @param m21 other.m21 value
     * @param m22 other.m22 value
     * @param m23 other.m23 value
     * @param m30 other.m30 value
     * @param m31 other.m31 value
     * @param m32 other.m32 value
     * @param m33 other.m33 value
     * @return this matrix for method chaining
     */
    public TMat4f mul(float m00, float m01, float m02, float m03,
                      float m10, float m11, float m12, float m13,
                      float m20, float m21, float m22, float m23,
                      float m30, float m31, float m32, float m33)
    {
        final float newM00 = this.m00 * m00 + this.m01 * m10 + this.m02 * m20 + this.m03 * m30;
        final float newM01 = this.m00 * m01 + this.m01 * m11 + this.m02 * m21 + this.m03 * m31;
        final float newM02 = this.m00 * m02 + this.m01 * m12 + this.m02 * m22 + this.m03 * m32;
        final float newM03 = this.m00 * m03 + this.m01 * m13 + this.m02 * m23 + this.m03 * m33;
        final float newM10 = this.m10 * m00 + this.m11 * m10 + this.m12 * m20 + this.m13 * m30;
        final float newM11 = this.m10 * m01 + this.m11 * m11 + this.m12 * m21 + this.m13 * m31;
        final float newM12 = this.m10 * m02 + this.m11 * m12 + this.m12 * m22 + this.m13 * m32;
        final float newM13 = this.m10 * m03 + this.m11 * m13 + this.m12 * m23 + this.m13 * m33;
        final float newM20 = this.m20 * m00 + this.m21 * m10 + this.m22 * m20 + this.m23 * m30;
        final float newM21 = this.m20 * m01 + this.m21 * m11 + this.m22 * m21 + this.m23 * m31;
        final float newM22 = this.m20 * m02 + this.m21 * m12 + this.m22 * m22 + this.m23 * m32;
        final float newM23 = this.m20 * m03 + this.m21 * m13 + this.m22 * m23 + this.m23 * m33;
        final float newM30 = this.m30 * m00 + this.m31 * m10 + this.m32 * m20 + this.m33 * m30;
        final float newM31 = this.m30 * m01 + this.m31 * m11 + this.m32 * m21 + this.m33 * m31;
        final float newM32 = this.m30 * m02 + this.m31 * m12 + this.m32 * m22 + this.m33 * m32;
        final float newM33 = this.m30 * m03 + this.m31 * m13 + this.m32 * m23 + this.m33 * m33;

        this.m00 = newM00; this.m01 = newM01; this.m02 = newM02; this.m03 = newM03;
        this.m10 = newM10; this.m11 = newM11; this.m12 = newM12; this.m13 = newM13;
        this.m20 = newM20; this.m21 = newM21; this.m22 = newM22; this.m23 = newM23;
        this.m30 = newM30; this.m31 = newM31; this.m32 = newM32; this.m33 = newM33;

        return this;
    }

    /**
     * Performs in-place matrix multiplication on this matrix by the parameter matrix.
     * In other words it simply does {@code this = this * other}.
     *
     * @param other the other matrix
     * @return this matrix for method chaining
     */
    public TMat4f mul(TMat4f other) {
        return this.mul(
            other.m00, other.m01, other.m02, other.m03,
            other.m10, other.m11, other.m12, other.m13,
            other.m20, other.m21, other.m22, other.m23,
            other.m30, other.m31, other.m32, other.m33
        );
    }

    // Transposition

    /**
     * Performs in-place matrix transposition on this matrix like so: {@code this = transpose(this)}.
     *
     * @return this matrix for method chaining
     */
    public TMat4f transpose() {
        // Swap m01 and m10
        float temp = this.m01;
        this.m01 = this.m10;
        this.m10 = temp;
        // Swap m02 and m20
        temp = this.m02;
        this.m02 = this.m20;
        this.m20 = temp;
        // Swap m21 and m12
        temp = this.m21;
        this.m21 = this.m12;
        this.m12 = temp;
        // Swap m03 and m30
        temp = this.m03;
        this.m03 = this.m30;
        this.m30 = temp;
        // Swap m23 and m32
        temp = this.m23;
        this.m23 = this.m32;
        this.m32 = temp;
        // Swap m13 and m31
        temp = this.m13;
        this.m13 = this.m31;
        this.m31 = temp;
        return this;
    }

    // Inversion

    /**
     * Performs in-place matrix inversion on this matrix like so: {@code this = invert(this)}.
     *
     * @return this matrix for method chaining
     */
    public TMat4f invert() {
        final float determinant = this.determinant();
        final float s = 1f / determinant;

        float newM00 = s * (m11 * m22 * m33 + m12 * m23 * m31 + m13 * m21 * m32 -
                             m13 * m22 * m31 - m12 * m21 * m33 - m11 * m23 * m32);
        float newM01 = s * (m03 * m22 * m31 + m02 * m21 * m33 + m01 * m23 * m32 -
                             m01 * m22 * m33 - m02 * m23 * m31 - m03 * m21 * m32);
        float newM02 = s * (m01 * m12 * m33 + m02 * m13 * m31 + m03 * m11 * m32 -
                             m03 * m12 * m31 - m02 * m11 * m33 - m01 * m13 * m32);
        float newM03 = s * (m03 * m12 * m21 + m02 * m11 * m23 + m01 * m13 * m22 -
                             m01 * m12 * m23 - m02 * m13 * m21 - m03 * m11 * m22);
        float newM10 = s * (m13 * m22 * m30 + m12 * m20 * m33 + m10 * m23 * m32 -
                             m10 * m22 * m33 - m12 * m23 * m30 - m13 * m20 * m32);
        float newM11 = s * (m00 * m22 * m33 + m02 * m23 * m30 + m03 * m20 * m32 -
                             m03 * m22 * m30 - m02 * m20 * m33 - m00 * m23 * m32);
        float newM12 = s * (m03 * m12 * m30 + m02 * m10 * m33 + m00 * m13 * m32 -
                             m00 * m12 * m33 - m02 * m13 * m30 - m03 * m10 * m32);
        float newM13 = s * (m00 * m12 * m23 + m02 * m13 * m20 + m03 * m10 * m22 -
                             m03 * m12 * m20 - m02 * m10 * m23 - m00 * m13 * m22);
        float newM20 = s * (m10 * m21 * m33 + m11 * m23 * m30 + m13 * m20 * m31 -
                             m13 * m21 * m30 - m11 * m20 * m33 - m10 * m23 * m31);
        float newM21 = s * (m03 * m21 * m30 + m01 * m20 * m33 + m00 * m23 * m31 -
                             m00 * m21 * m33 - m01 * m23 * m30 - m03 * m20 * m31);
        float newM22 = s * (m00 * m11 * m33 + m01 * m13 * m30 + m03 * m10 * m31 -
                             m03 * m11 * m30 - m01 * m10 * m33 - m00 * m13 * m31);
        float newM23 = s * (m03 * m11 * m20 + m01 * m10 * m23 + m00 * m13 * m21 -
                             m00 * m11 * m23 - m01 * m13 * m20 - m03 * m10 * m21);
        float newM30 = s * (m12 * m21 * m30 + m11 * m20 * m32 + m10 * m22 * m31 -
                             m10 * m21 * m32 - m11 * m22 * m30 - m12 * m20 * m31);
        float newM31 = s * (m00 * m21 * m32 + m01 * m22 * m30 + m02 * m20 * m31 -
                             m02 * m21 * m30 - m01 * m20 * m32 - m00 * m22 * m31);
        float newM32 = s * (m02 * m11 * m30 + m01 * m10 * m32 + m00 * m12 * m31 -
                             m00 * m11 * m32 - m01 * m12 * m30 - m02 * m10 * m31);
        float newM33 = s * (m00 * m11 * m22 + m01 * m12 * m20 + m02 * m10 * m21 -
                             m02 * m11 * m20 - m01 * m10 * m22 - m00 * m12 * m21);

        this.m00 = newM00; this.m01 = newM01; this.m02 = newM02; this.m03 = newM03;
        this.m10 = newM10; this.m11 = newM11; this.m12 = newM12; this.m13 = newM13;
        this.m20 = newM20; this.m21 = newM21; this.m22 = newM22; this.m23 = newM23;
        this.m30 = newM30; this.m31 = newM31; this.m32 = newM32; this.m33 = newM33;

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
    public TMat4f map(FloatUnaryOperator function) {
        this.m00 = function.applyAsFloat(this.m00);
        this.m01 = function.applyAsFloat(this.m01);
        this.m02 = function.applyAsFloat(this.m02);
        this.m03 = function.applyAsFloat(this.m03);
        this.m10 = function.applyAsFloat(this.m10);
        this.m11 = function.applyAsFloat(this.m11);
        this.m12 = function.applyAsFloat(this.m12);
        this.m13 = function.applyAsFloat(this.m13);
        this.m20 = function.applyAsFloat(this.m20);
        this.m21 = function.applyAsFloat(this.m21);
        this.m22 = function.applyAsFloat(this.m22);
        this.m23 = function.applyAsFloat(this.m23);
        this.m30 = function.applyAsFloat(this.m30);
        this.m31 = function.applyAsFloat(this.m31);
        this.m32 = function.applyAsFloat(this.m32);
        this.m33 = function.applyAsFloat(this.m33);
        return this;
    }

    public TMat4f sin() { return map(TMath::sin); }
    public TMat4f cos() { return map(TMath::cos); }
    public TMat4f tan() { return map(TMath::tan); }
    public TMat4f sinh() { return map(TMath::sinh); }
    public TMat4f cosh() { return map(TMath::cosh); }
    public TMat4f tanh() { return map(TMath::tanh); }
    public TMat4f arcsin() { return map(TMath::arcsin); }
    public TMat4f arccos() { return map(TMath::arccos); }
    public TMat4f arctan() { return map(TMath::arctan); }

    public TMat4f abs() { return map(TMath::abs); }
    public TMat4f floor() { return map(TMath::floor); }
    public TMat4f ceil() { return map(TMath::ceil); }
    public TMat4f sqrt() { return map(TMath::sqrt); }
    public TMat4f cbrt() { return map(TMath::cbrt); }
    public TMat4f square() { return map(TMath::square); }
    public TMat4f cube() { return map(TMath::cube); }


    /*/////////////////////////////////////////////////////////////////*/
    /*//////////////////  GRID ELEMENT PERMUTATIONS  //////////////////*/
    /*/////////////////////////////////////////////////////////////////*/


    /**
     * Horizontally flips the elements of this matrix grid.
     * <b>Keep in mind that this is NOT a geometric space transformation!</b><br>
     *
     * Example from left to right: <br>
     * {@code [10 11 12 13] => [13 12 11 10]} <br>
     * {@code [14 15 16 17] => [17 16 15 14]} <br>
     * {@code [18 19 20 21] => [21 20 19 18]} <br>
     * {@code [22 23 24 25] => [25 24 23 22]}
     *
     * @return this matrix for method chaining
     */
    public TMat4f permuteGridFlipHorizontally() {
        // Swap m00 and m03
        float temp = this.m00;
        this.m00 = this.m03;
        this.m03 = temp;
        // Swap m10 and m13
        temp = this.m10;
        this.m10 = this.m13;
        this.m13 = temp;
        // Swap m20 and m23
        temp = this.m20;
        this.m20 = this.m23;
        this.m23 = temp;
        // Swap m30 and m33
        temp = this.m30;
        this.m30 = this.m33;
        this.m33 = temp;
        // Swap m01 and m02
        temp = this.m01;
        this.m01 = this.m02;
        this.m02= temp;
        // Swap m21 and m22
        temp = this.m21;
        this.m21 = this.m22;
        this.m22 = temp;
        // Swap m11 and m12
        temp = this.m11;
        this.m11 = this.m12;
        this.m12 = temp;
        // Swap m31 and m32
        temp = this.m31;
        this.m31 = this.m32;
        this.m32 = temp;
        return this;
    }

    /**
     * Vertically flips the elements of this matrix grid.
     * <b>Keep in mind that this is NOT a geometric space transformation!</b><br>
     *
     * Example from left to right: <br>
     * {@code [10 11 12 13] => [22 23 24 25]} <br>
     * {@code [14 15 16 17] => [18 19 20 21]} <br>
     * {@code [18 19 20 21] => [14 15 16 17]} <br>
     * {@code [22 23 24 25] => [10 11 12 13]}
     *
     * @return this matrix for method chaining
     */
    public TMat4f permuteGridFlipVertically() {
        // Swap m20 and m10
        float temp = this.m20;
        this.m20 = this.m10;
        this.m10 = temp;
        // Swap m11 and m21
        temp = this.m11;
        this.m11 = this.m21;
        this.m21 = temp;
        // Swap m12 and m22
        temp = this.m12;
        this.m12 = this.m22;
        this.m22 = temp;
        // Swap m23 and m13
        temp = this.m23;
        this.m23 = this.m13;
        this.m13 = temp;
        // Swap m00 and m30
        temp = this.m00;
        this.m00 = this.m30;
        this.m30 = temp;
        // Swap m01 and m31
        temp = this.m01;
        this.m01 = this.m31;
        this.m31 = temp;
        // Swap m02 and m32
        temp = this.m02;
        this.m02 = this.m32;
        this.m32 = temp;
        // Swap m03 and m33
        temp = this.m03;
        this.m03 = this.m33;
        this.m33 = temp;
        return this;
    }

    /**
     * Rotates the elements of this matrix grid by 90 degrees clockwise.
     * <b>Keep in mind that this is NOT a geometric space transformation!</b><br>
     *
     * Example from left to right: <br>
     * {@code [10 11 12 13] => [22 18 14 10]} <br>
     * {@code [14 15 16 17] => [23 19 15 11]} <br>
     * {@code [18 19 20 21] => [24 20 16 12]} <br>
     * {@code [22 23 24 25] => [25 21 17 13]}
     *
     * @return this matrix for method chaining
     */
    public TMat4f permuteGridRotate90Clockwise() {
        float temp = this.m12;
        this.m12 = this.m11;
        this.m11 = this.m21;
        this.m21 = this.m22;
        this.m22 = temp;
        temp = this.m02;
        this.m02 = this.m10;
        this.m10 = this.m31;
        this.m31 = this.m23;
        this.m23 = temp;
        temp = this.m01;
        this.m01 = this.m20;
        this.m20 = this.m32;
        this.m32 = this.m13;
        this.m13 = temp;
        temp = this.m03;
        this.m03 = this.m00;
        this.m00 = this.m30;
        this.m30 = this.m33;
        this.m33 = temp;
        return this;
    }

    /**
     * Rotates the elements of this matrix grid by 90 degrees anti-clockwise.
     * <b>Keep in mind that this is NOT a geometric space transformation!</b><br>
     *
     * Example from left to right: <br>
     * {@code [10 11 12 13] => [13 17 21 25]} <br>
     * {@code [14 15 16 17] => [12 16 20 24]} <br>
     * {@code [18 19 20 21] => [11 15 19 23]} <br>
     * {@code [22 23 24 25] => [10 14 18 22]}
     *
     * @return this matrix for method chaining
     */
    public TMat4f permuteGridRotate90AntiClockwise() {
        float temp = this.m11;
        this.m11 = this.m12;
        this.m12 = this.m22;
        this.m22 = this.m21;
        this.m21 = temp;
        temp = this.m10;
        this.m10 = this.m02;
        this.m02 = this.m23;
        this.m23 = this.m31;
        this.m31 = temp;
        temp = this.m00;
        this.m00 = this.m03;
        this.m03 = this.m33;
        this.m33 = this.m30;
        this.m30 = temp;
        temp = this.m01;
        this.m01 = this.m13;
        this.m13 = this.m32;
        this.m32 = this.m20;
        this.m20 = temp;
        return this;
    }

    /**
     * Rotates the elements of this matrix grid by 180 degrees.
     * <b>Keep in mind that this is NOT a geometric space transformation!</b><br>
     *
     * Example from left to right: <br>
     * {@code [10 11 12 13] => [25 24 23 22]} <br>
     * {@code [14 15 16 17] => [21 20 19 18]} <br>
     * {@code [18 19 20 21] => [17 16 15 14]} <br>
     * {@code [22 23 24 25] => [13 12 11 10]}
     *
     * @return this matrix for method chaining
     */
    public TMat4f permuteGridRotate180() {
        // Swap m00 and m33
        float temp = this.m00;
        this.m00 = this.m33;
        this.m33 = temp;
        // Swap m10 and m23
        temp = this.m10;
        this.m10 = this.m23;
        this.m23 = temp;
        // Swap m30 and m03
        temp = this.m30;
        this.m30 = this.m03;
        this.m03 = temp;
        // Swap m31 and m02
        temp = this.m31;
        this.m31 = this.m02;
        this.m02 = temp;
        // Swap m32 and m01
        temp = this.m32;
        this.m32 = this.m01;
        this.m01 = temp;
        // Swap m20 and m13
        temp = this.m20;
        this.m20 = this.m13;
        this.m13 = temp;
        // Swap m21 and m12
        temp = this.m21;
        this.m21 = this.m12;
        this.m12 = temp;
        // Swap m22 and m11
        temp = this.m22;
        this.m22 = this.m11;
        this.m11 = temp;
        return this;
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  METRICS  ///////////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/


    public static float determinant(float m00, float m01, float m02, float m03,
                                    float m10, float m11, float m12, float m13,
                                    float m20, float m21, float m22, float m23,
                                    float m30, float m31, float m32, float m33)
    {
        return m00 * m11 * m22 * m33 + m00 * m12 * m23 * m31 + m00 * m13 * m21 * m32 +
               m03 * m10 * m22 * m31 + m02 * m10 * m21 * m33 + m01 * m10 * m23 * m32 +
               m01 * m12 * m20 * m33 + m02 * m13 * m20 * m31 + m03 * m11 * m20 * m32 +
               m03 * m12 * m21 * m30 + m02 * m11 * m23 * m30 + m01 * m13 * m22 * m30 -
               m00 * m13 * m22 * m31 - m00 * m12 * m21 * m33 - m00 * m11 * m23 * m32 -
               m01 * m10 * m22 * m33 - m02 * m10 * m23 * m31 - m03 * m10 * m21 * m32 -
               m03 * m12 * m20 * m31 - m02 * m11 * m20 * m33 - m01 * m13 * m20 * m32 -
               m01 * m12 * m23 * m30 - m02 * m13 * m21 * m30 - m03 * m11 * m22 * m30;
    }

    public float determinant() {
        return determinant(
            this.m00, this.m01, this.m02, this.m03,
            this.m10, this.m11, this.m12, this.m13,
            this.m20, this.m21, this.m22, this.m23,
            this.m30, this.m31, this.m32, this.m33
        );
    }

    public float determinant3x3UpperLeft() {
        return TMat3f.determinant(
            m00, m01, m02,
            m10, m11, m12,
            m20, m21, m22
        );
    }

    public float determinant3x3UpperRight() {
        return TMat3f.determinant(
            m01, m02, m03,
            m11, m12, m13,
            m21, m22, m23
        );
    }

    public float determinant3x3BottomLeft() {
        return TMat3f.determinant(
            m10, m11, m12,
            m20, m21, m22,
            m30, m31, m32
        );
    }

    public float determinant3x3BottomRight() {
        return TMat3f.determinant(
            m11, m12, m13,
            m21, m22, m23,
            m31, m32, m33
        );
    }

    public float trace() {
        return this.m00 + this.m11 + this.m22 + this.m33;
    }


    /*///////////////////////////////////////////////////////////////*/
    /*/////////////////////  GETTERS & SETTERS  /////////////////////*/
    /*///////////////////////////////////////////////////////////////*/

    // Getters

    public float getM00() { return this.m00; }
    public float getM01() { return this.m01; }
    public float getM02() { return this.m02; }
    public float getM03() { return this.m03; }
    public float getM10() { return this.m10; }
    public float getM11() { return this.m11; }
    public float getM12() { return this.m12; }
    public float getM13() { return this.m13; }
    public float getM20() { return this.m20; }
    public float getM21() { return this.m21; }
    public float getM22() { return this.m22; }
    public float getM23() { return this.m23; }
    public float getM30() { return this.m30; }
    public float getM31() { return this.m31; }
    public float getM32() { return this.m32; }
    public float getM33() { return this.m33; }

    public float[][] toArray(){
        return new float[][] {
            { this.m00, this.m01, this.m02, this.m03 },
            { this.m10, this.m11, this.m12, this.m13 },
            { this.m20, this.m21, this.m22, this.m23 },
            { this.m30, this.m31, this.m32, this.m33 }
        };
    }

    // Setters

    public TMat4f setM00(float m00) { this.m00 = m00; return this; }
    public TMat4f setM01(float m01) { this.m01 = m01; return this; }
    public TMat4f setM02(float m02) { this.m02 = m02; return this; }
    public TMat4f setM03(float m03) { this.m03 = m03; return this; }
    public TMat4f setM10(float m10) { this.m10 = m10; return this; }
    public TMat4f setM11(float m11) { this.m11 = m11; return this; }
    public TMat4f setM12(float m12) { this.m12 = m12; return this; }
    public TMat4f setM13(float m13) { this.m13 = m13; return this; }
    public TMat4f setM20(float m20) { this.m20 = m20; return this; }
    public TMat4f setM21(float m21) { this.m21 = m21; return this; }
    public TMat4f setM22(float m22) { this.m22 = m22; return this; }
    public TMat4f setM23(float m23) { this.m23 = m23; return this; }
    public TMat4f setM30(float m30) { this.m30 = m30; return this; }
    public TMat4f setM31(float m31) { this.m31 = m31; return this; }
    public TMat4f setM32(float m32) { this.m32 = m32; return this; }
    public TMat4f setM33(float m33) { this.m33 = m33; return this; }

    public TMat4f set(float m00, float m01, float m02, float m03,
                      float m10, float m11, float m12, float m13,
                      float m20, float m21, float m22, float m23,
                      float m30, float m31, float m32, float m33)
    {
        this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
        this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
        this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
        this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
        return this;
    }

    public TMat4f set(TMat4f other) {
        this.m00 = other.m00; this.m01 = other.m01; this.m02 = other.m02; this.m03 = other.m03;
        this.m10 = other.m10; this.m11 = other.m11; this.m12 = other.m12; this.m13 = other.m13;
        this.m20 = other.m20; this.m21 = other.m21; this.m22 = other.m22; this.m23 = other.m23;
        this.m30 = other.m30; this.m31 = other.m31; this.m32 = other.m32; this.m33 = other.m33;
        return this;
    }

    // Min max sum getters for rows and cols

    public float getMinOfRow0() { return TMath.min(this.m00, this.m01, this.m02, this.m03); }
    public float getMaxOfRow0() { return TMath.max(this.m00, this.m01, this.m02, this.m03); }
    public float getSumOfRow0() { return this.m00 + this.m01 + this.m02 + this.m03; }

    public float getMinOfRow1() { return TMath.min(this.m10, this.m11, this.m12, this.m13); }
    public float getMaxOfRow1() { return TMath.max(this.m10, this.m11, this.m12, this.m13); }
    public float getSumOfRow1() { return this.m10 + this.m11 + this.m12 + this.m13; }

    public float getMinOfRow2() { return TMath.min(this.m20, this.m21, this.m22, this.m23); }
    public float getMaxOfRow2() { return TMath.max(this.m20, this.m21, this.m22, this.m23); }
    public float getSumOfRow2() { return this.m20 + this.m21 + this.m22 + this.m23; }

    public float getMinOfRow3() { return TMath.min(this.m30, this.m31, this.m32, this.m33); }
    public float getMaxOfRow3() { return TMath.max(this.m30, this.m31, this.m32, this.m33); }
    public float getSumOfRow3() { return this.m30 + this.m31 + this.m32 + this.m33; }

    public float getMinOfCol0() { return TMath.min(this.m00, this.m10, this.m20, this.m30); }
    public float getMaxOfCol0() { return TMath.max(this.m00, this.m10, this.m20, this.m30); }
    public float getSumOfCol0() { return this.m00 + this.m10 + this.m20 + this.m30; }

    public float getMinOfCol1() { return TMath.min(this.m01, this.m11, this.m21, this.m31); }
    public float getMaxOfCol1() { return TMath.max(this.m01, this.m11, this.m21, this.m31); }
    public float getSumOfCol1() { return this.m01 + this.m11 + this.m21 + this.m31; }

    public float getMinOfCol2() { return TMath.min(this.m02, this.m12, this.m22, this.m32); }
    public float getMaxOfCol2() { return TMath.max(this.m02, this.m12, this.m22, this.m32); }
    public float getSumOfCol2() { return this.m02 + this.m12 + this.m22 + this.m32; }

    public float getMinOfCol3() { return TMath.min(this.m03, this.m13, this.m23, this.m33); }
    public float getMaxOfCol3() { return TMath.max(this.m03, this.m13, this.m23, this.m33); }
    public float getSumOfCol3() { return this.m03 + this.m13 + this.m23 + this.m33; }

    // Indexed getters & setters

    public float get(int row, int col) {
        if (row == 0) {
            if (col == 0) return this.m00;
            if (col == 1) return this.m01;
            if (col == 2) return this.m02;
            if (col == 3) return this.m03;
        }
        if (row == 1) {
            if (col == 0) return this.m10;
            if (col == 1) return this.m11;
            if (col == 2) return this.m12;
            if (col == 3) return this.m13;
        }
        if (row == 2) {
            if (col == 0) return this.m20;
            if (col == 1) return this.m21;
            if (col == 2) return this.m22;
            if (col == 3) return this.m23;
        }
        if (row == 3) {
            if (col == 0) return this.m30;
            if (col == 1) return this.m31;
            if (col == 2) return this.m32;
            if (col == 3) return this.m33;
        }

        throw new TephriumException("4x4 Matrix indices out of range: row=%d, col=%d", row, col);
    }

    public TMat4f set(int row, int col, float value) {
        if (row == 0) {
            if (col == 0) { this.m00 = value; return this; }
            if (col == 1) { this.m01 = value; return this; }
            if (col == 2) { this.m02 = value; return this; }
            if (col == 3) { this.m03 = value; return this; }
        }
        if (row == 1) {
            if (col == 0) { this.m10 = value; return this; }
            if (col == 1) { this.m11 = value; return this; }
            if (col == 2) { this.m12 = value; return this; }
            if (col == 3) { this.m13 = value; return this; }
        }
        if (row == 2) {
            if (col == 0) { this.m20 = value; return this; }
            if (col == 1) { this.m21 = value; return this; }
            if (col == 2) { this.m22 = value; return this; }
            if (col == 3) { this.m23 = value; return this; }
        }
        if (row == 3) {
            if (col == 0) { this.m30 = value; return this; }
            if (col == 1) { this.m31 = value; return this; }
            if (col == 2) { this.m32 = value; return this; }
            if (col == 3) { this.m33 = value; return this; }
        }

        throw new TephriumException("4x4 Matrix indices out of range: row=%d, col=%d", row, col);
    }

    public float getMinOfRow(int row) {
        if (row == 0) return getMinOfRow0();
        if (row == 1) return getMinOfRow1();
        if (row == 2) return getMinOfRow2();
        if (row == 3) return getMinOfRow3();
        throw new TephriumException("4x4 Matrix row index out of range: %d", row);
    }

    public float getMaxOfRow(int row) {
        if (row == 0) return getMaxOfRow0();
        if (row == 1) return getMaxOfRow1();
        if (row == 2) return getMaxOfRow2();
        if (row == 3) return getMaxOfRow3();
        throw new TephriumException("4x4 Matrix row index out of range: %d", row);
    }

    public float getSumOfRow(int row) {
        if (row == 0) return getSumOfRow0();
        if (row == 1) return getSumOfRow1();
        if (row == 2) return getSumOfRow2();
        if (row == 3) return getSumOfRow3();
        throw new TephriumException("4x4 Matrix row index out of range: %d", row);
    }

    public float getMinOfCol(int col) {
        if (col == 0) return getMinOfCol0();
        if (col == 1) return getMinOfCol1();
        if (col == 2) return getMinOfCol2();
        if (col == 3) return getMinOfCol3();
        throw new TephriumException("4x4 Matrix column index out of range: %d", col);
    }

    public float getMaxOfCol(int col) {
        if (col == 0) return getMaxOfCol0();
        if (col == 1) return getMaxOfCol1();
        if (col == 2) return getMaxOfCol2();
        if (col == 3) return getMaxOfCol3();
        throw new TephriumException("4x4 Matrix column index out of range: %d", col);
    }

    public float getSumOfCol(int col) {
        if (col == 0) return getSumOfCol0();
        if (col == 1) return getSumOfCol1();
        if (col == 2) return getSumOfCol2();
        if (col == 3) return getSumOfCol3();
        throw new TephriumException("4x4 Matrix column index out of range: %d", col);
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*//////////////////////  BOOLEAN FUNCTIONS  //////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    public boolean isSymmetrical() {
        return TMath.equals(this.m01, this.m10) && TMath.equals(this.m02, this.m20) &&
               TMath.equals(this.m21, this.m12) && TMath.equals(this.m30, this.m03) &&
               TMath.equals(this.m23, this.m32) && TMath.equals(this.m13, this.m31);
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
            1f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f
        );
    }

    public boolean isZero() {
        return this.equals(
            0f, 0f, 0f, 0f,
            0f, 0f, 0f, 0f,
            0f, 0f, 0f, 0f,
            0f, 0f, 0f, 0f
        );
    }


    /*//////////////////////////////////////////////////////////////////*/
    /*////////////////////////  OBJECT METHODS  ////////////////////////*/
    /*//////////////////////////////////////////////////////////////////*/


    public boolean equals(TMat4f other) {
        if (other == null) return false;
        return TMath.equals(this.m00, other.m00) && TMath.equals(this.m01, other.m01) &&
               TMath.equals(this.m02, other.m02) && TMath.equals(this.m03, other.m03) &&
               TMath.equals(this.m10, other.m10) && TMath.equals(this.m11, other.m11) &&
               TMath.equals(this.m12, other.m12) && TMath.equals(this.m13, other.m13) &&
               TMath.equals(this.m20, other.m20) && TMath.equals(this.m21, other.m21) &&
               TMath.equals(this.m22, other.m22) && TMath.equals(this.m23, other.m23) &&
               TMath.equals(this.m30, other.m30) && TMath.equals(this.m31, other.m31) &&
               TMath.equals(this.m32, other.m32) && TMath.equals(this.m33, other.m33);
    }

    public boolean equals(float m00, float m01, float m02, float m03,
                          float m10, float m11, float m12, float m13,
                          float m20, float m21, float m22, float m23,
                          float m30, float m31, float m32, float m33)
    {
        return TMath.equals(this.m00, m00) && TMath.equals(this.m01, m01) &&
               TMath.equals(this.m02, m02) && TMath.equals(this.m03, m03) &&
               TMath.equals(this.m10, m10) && TMath.equals(this.m11, m11) &&
               TMath.equals(this.m12, m12) && TMath.equals(this.m13, m13) &&
               TMath.equals(this.m20, m20) && TMath.equals(this.m21, m21) &&
               TMath.equals(this.m22, m22) && TMath.equals(this.m23, m23) &&
               TMath.equals(this.m30, m30) && TMath.equals(this.m31, m31) &&
               TMath.equals(this.m32, m32) && TMath.equals(this.m33, m33);
    }

    private static final String TMAT4F_STR_FORMAT = "[%.5f, %.5f, %.5f, %.5f]\n" +
                                                    "[%.5f, %.5f, %.5f, %.5f]\n" +
                                                    "[%.5f, %.5f, %.5f, %.5f]\n" +
                                                    "[%.5f, %.5f, %.5f, %.5f]\n";

    @Override
    public String toString() {
        return String.format(
            Locale.ROOT,
            TMAT4F_STR_FORMAT,
            this.m00, this.m01, this.m02, this.m03,
            this.m10, this.m11, this.m12, this.m13,
            this.m20, this.m21, this.m22, this.m23,
            this.m30, this.m31, this.m32, this.m33
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        return this.equals((TMat4f) o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            this.m00, this.m01, this.m02, this.m03,
            this.m10, this.m11, this.m12, this.m13,
            this.m20, this.m21, this.m22, this.m23,
            this.m30, this.m31, this.m32, this.m33
        );
    }


}

