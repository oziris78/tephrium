

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
 * A fast, mutable, 4x4, double valued, row major matrix class with method chaining support.
 */
public class TMat4d {

    private double m00, m01, m02, m03,     // [ m00, m01, m02, m03 ]
                   m10, m11, m12, m13,     // [ m10, m11, m12, m13 ]
                   m20, m21, m22, m23,     // [ m20, m21, m22, m23 ]
                   m30, m31, m32, m33;     // [ m30, m31, m32, m33 ]

    // Actual constructors

    public TMat4d(double m00, double m01, double m02, double m03,
                  double m10, double m11, double m12, double m13,
                  double m20, double m21, double m22, double m23,
                  double m30, double m31, double m32, double m33)
    {
        this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;  // [ m00, m01, m02, m03 ]
        this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;  // [ m10, m11, m12, m13 ]
        this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;  // [ m20, m21, m22, m23 ]
        this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;  // [ m30, m31, m32, m33 ]
    }

    public TMat4d(double fillValue) {
        this(
            fillValue, fillValue, fillValue, fillValue,
            fillValue, fillValue, fillValue, fillValue,
            fillValue, fillValue, fillValue, fillValue,
            fillValue, fillValue, fillValue, fillValue
        );
    }

    /** Returns I<sub>4</sub> which is the 4x4 identity matrix */
    public TMat4d() {
        this(
            1d, 0d, 0d, 0d,
            0d, 1d, 0d, 0d,
            0d, 0d, 1d, 0d,
            0d, 0d, 0d, 1d
        );
    }

    // Copying constructors & functions

    public TMat4d(TMat4d other) {
        this(
            other.m00, other.m01, other.m02, other.m03,
            other.m10, other.m11, other.m12, other.m13,
            other.m20, other.m21, other.m22, other.m23,
            other.m30, other.m31, other.m32, other.m33
        );
    }

    public TMat4d copy() {
        return new TMat4d(
            this.m00, this.m01, this.m02, this.m03,
            this.m10, this.m11, this.m12, this.m13,
            this.m20, this.m21, this.m22, this.m23,
            this.m30, this.m31, this.m32, this.m33
        );
    }

    // Special static factory methods

    public static TMat4d zero() {
        return new TMat4d(0d);
    }

    /** Returns a diagonal matrix with the specified diagonal values */
    public static TMat4d diagonal(double d0, double d1, double d2, double d3) {
        return new TMat4d(
            d0, 0d, 0d, 0d,
            0d, d1, 0d, 0d,
            0d, 0d, d2, 0d,
            0d, 0d, 0d, d3
        );
    }

    /** Returns a 4D scaling matrix */
    public static TMat4d scaling(double sx, double sy, double sz, double sw) {
        return new TMat4d(
            sx, 0d, 0d, 0d,
            0d, sy, 0d, 0d,
            0d, 0d, sz, 0d,
            0d, 0d, 0d, sw
        );
    }

    /** Returns a uniform 4D scaling matrix */
    public static TMat4d scaling(double scale) {
        return scaling(scale, scale, scale, scale);
    }

    /** Returns a 4D rotation matrix around the X-axis for the given cos and sin values. */
    public static TMat4d rotationX(double cos, double sin) { // "Pitch"
        return new TMat4d(
            1d, 0d, 0d, 0d,
            0d, cos, -sin, 0d,
            0d, sin, cos, 0d,
            0d, 0d, 0d, 1d
        );
    }

    public static TMat4d rotationXRad(double radians) {
        return rotationX(TMath.cos(radians), TMath.sin(radians));
    }

    public static TMat4d rotationXDeg(double degrees) {
        return rotationXRad(TMath.toRadians(degrees));
    }

    /** Returns a 4D rotation matrix around the Y-axis for the given cos and sin values. */
    public static TMat4d rotationY(double cos, double sin) { // "Yaw"
        return new TMat4d(
            cos, 0d, sin, 0d,
            0d, 1d, 0d, 0d,
            -sin, 0d, cos, 0d,
            0d, 0d, 0d, 1d
        );
    }

    public static TMat4d rotationYRad(double radians) {
        return rotationY(TMath.cos(radians), TMath.sin(radians));
    }

    public static TMat4d rotationYDeg(double degrees) {
        return rotationYRad(TMath.toRadians(degrees));
    }

    /** Returns a 4D rotation matrix around the Z-axis for the given cos and sin values. */
    public static TMat4d rotationZ(double cos, double sin) { // "Roll"
        return new TMat4d(
            cos, -sin, 0d, 0d,
            sin, cos, 0d, 0d,
            0d, 0d, 1d, 0d,
            0d, 0d, 0d, 1d
        );
    }

    public static TMat4d rotationZRad(double radians) {
        return rotationZ(TMath.cos(radians), TMath.sin(radians));
    }

    public static TMat4d rotationZDeg(double degrees) {
        return rotationZRad(TMath.toRadians(degrees));
    }

    public static TMat4d translation(double x, double y, double z) {
        return new TMat4d(
            1d, 0d, 0d, x,
            0d, 1d, 0d, y,
            0d, 0d, 1d, z,
            0d, 0d, 0d, 1d
        );
    }

    public static TMat4d translation(TVec3d translation) {
        return translation(translation.getX(), translation.getY(), translation.getZ());
    }

    public static TMat4d fromRows(TVec4d row0, TVec4d row1, TVec4d row2, TVec4d row3) {
        return new TMat4d(
            row0.getX(), row0.getY(), row0.getZ(), row0.getW(),
            row1.getX(), row1.getY(), row1.getZ(), row1.getW(),
            row2.getX(), row2.getY(), row2.getZ(), row2.getW(),
            row3.getX(), row3.getY(), row3.getZ(), row3.getW()
        );
    }

    public static TMat4d fromCols(TVec4d col0, TVec4d col1, TVec4d col2, TVec4d col3) {
        return new TMat4d(
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

    public TMat4d add(double m00, double m01, double m02, double m03,
                      double m10, double m11, double m12, double m13,
                      double m20, double m21, double m22, double m23,
                      double m30, double m31, double m32, double m33)
    {
        this.m00 += m00; this.m01 += m01; this.m02 += m02; this.m03 += m03;
        this.m10 += m10; this.m11 += m11; this.m12 += m12; this.m13 += m13;
        this.m20 += m20; this.m21 += m21; this.m22 += m22; this.m23 += m23;
        this.m30 += m30; this.m31 += m31; this.m32 += m32; this.m33 += m33;
        return this;
    }

    public TMat4d add(TMat4d other) {
        this.m00 += other.m00; this.m01 += other.m01; this.m02 += other.m02; this.m03 += other.m03;
        this.m10 += other.m10; this.m11 += other.m11; this.m12 += other.m12; this.m13 += other.m13;
        this.m20 += other.m20; this.m21 += other.m21; this.m22 += other.m22; this.m23 += other.m23;
        this.m30 += other.m30; this.m31 += other.m31; this.m32 += other.m32; this.m33 += other.m33;
        return this;
    }

    public TMat4d addM00(double x) { this.m00 += x; return this; }
    public TMat4d addM01(double x) { this.m01 += x; return this; }
    public TMat4d addM02(double x) { this.m02 += x; return this; }
    public TMat4d addM03(double x) { this.m03 += x; return this; }
    public TMat4d addM10(double x) { this.m10 += x; return this; }
    public TMat4d addM11(double x) { this.m11 += x; return this; }
    public TMat4d addM12(double x) { this.m12 += x; return this; }
    public TMat4d addM13(double x) { this.m13 += x; return this; }
    public TMat4d addM20(double x) { this.m20 += x; return this; }
    public TMat4d addM21(double x) { this.m21 += x; return this; }
    public TMat4d addM22(double x) { this.m22 += x; return this; }
    public TMat4d addM23(double x) { this.m23 += x; return this; }
    public TMat4d addM30(double x) { this.m30 += x; return this; }
    public TMat4d addM31(double x) { this.m31 += x; return this; }
    public TMat4d addM32(double x) { this.m32 += x; return this; }
    public TMat4d addM33(double x) { this.m33 += x; return this; }

    // Subtraction

    public TMat4d sub(double m00, double m01, double m02, double m03,
                      double m10, double m11, double m12, double m13,
                      double m20, double m21, double m22, double m23,
                      double m30, double m31, double m32, double m33)
    {
        this.m00 -= m00; this.m01 -= m01; this.m02 -= m02; this.m03 -= m03;
        this.m10 -= m10; this.m11 -= m11; this.m12 -= m12; this.m13 -= m13;
        this.m20 -= m20; this.m21 -= m21; this.m22 -= m22; this.m23 -= m23;
        this.m30 -= m30; this.m31 -= m31; this.m32 -= m32; this.m33 -= m33;
        return this;
    }

    public TMat4d sub(TMat4d other) {
        this.m00 -= other.m00; this.m01 -= other.m01; this.m02 -= other.m02; this.m03 -= other.m03;
        this.m10 -= other.m10; this.m11 -= other.m11; this.m12 -= other.m12; this.m13 -= other.m13;
        this.m20 -= other.m20; this.m21 -= other.m21; this.m22 -= other.m22; this.m23 -= other.m23;
        this.m30 -= other.m30; this.m31 -= other.m31; this.m32 -= other.m32; this.m33 -= other.m33;
        return this;
    }

    public TMat4d subM00(double x) { this.m00 -= x; return this; }
    public TMat4d subM01(double x) { this.m01 -= x; return this; }
    public TMat4d subM02(double x) { this.m02 -= x; return this; }
    public TMat4d subM03(double x) { this.m03 -= x; return this; }
    public TMat4d subM10(double x) { this.m10 -= x; return this; }
    public TMat4d subM11(double x) { this.m11 -= x; return this; }
    public TMat4d subM12(double x) { this.m12 -= x; return this; }
    public TMat4d subM13(double x) { this.m13 -= x; return this; }
    public TMat4d subM20(double x) { this.m20 -= x; return this; }
    public TMat4d subM21(double x) { this.m21 -= x; return this; }
    public TMat4d subM22(double x) { this.m22 -= x; return this; }
    public TMat4d subM23(double x) { this.m23 -= x; return this; }
    public TMat4d subM30(double x) { this.m30 -= x; return this; }
    public TMat4d subM31(double x) { this.m31 -= x; return this; }
    public TMat4d subM32(double x) { this.m32 -= x; return this; }
    public TMat4d subM33(double x) { this.m33 -= x; return this; }

    // (ELEMENT WISE) Scaling / Multiplication

    public TMat4d scale(double s00, double s01, double s02, double s03,
                        double s10, double s11, double s12, double s13,
                        double s20, double s21, double s22, double s23,
                        double s30, double s31, double s32, double s33)
    {
        this.m00 *= s00; this.m01 *= s01; this.m02 *= s02; this.m03 *= s03;
        this.m10 *= s10; this.m11 *= s11; this.m12 *= s12; this.m13 *= s13;
        this.m20 *= s20; this.m21 *= s21; this.m22 *= s22; this.m23 *= s23;
        this.m30 *= s30; this.m31 *= s31; this.m32 *= s32; this.m33 *= s33;
        return this;
    }

    /**
     * Performs element-wise multiplication, NOT matrix multiplication.
     * For matrix multiplication use this method: {@link #mul(TMat4d)}
     *
     * @param other matrix to perform element-wise multiplication with
     * @return this matrix for method chaining
     */
    public TMat4d scale(TMat4d other) {
        this.m00 *= other.m00; this.m01 *= other.m01; this.m02 *= other.m02; this.m03 *= other.m03;
        this.m10 *= other.m10; this.m11 *= other.m11; this.m12 *= other.m12; this.m13 *= other.m13;
        this.m20 *= other.m20; this.m21 *= other.m21; this.m22 *= other.m22; this.m23 *= other.m23;
        this.m30 *= other.m30; this.m31 *= other.m31; this.m32 *= other.m32; this.m33 *= other.m33;
        return this;
    }

    public TMat4d scale(double scale) {
        this.m00 *= scale; this.m01 *= scale; this.m02 *= scale; this.m03 *= scale;
        this.m10 *= scale; this.m11 *= scale; this.m12 *= scale; this.m13 *= scale;
        this.m20 *= scale; this.m21 *= scale; this.m22 *= scale; this.m23 *= scale;
        this.m30 *= scale; this.m31 *= scale; this.m32 *= scale; this.m33 *= scale;
        return this;
    }

    public TMat4d scaleM00(double x) { this.m00 *= x; return this; }
    public TMat4d scaleM01(double x) { this.m01 *= x; return this; }
    public TMat4d scaleM02(double x) { this.m02 *= x; return this; }
    public TMat4d scaleM03(double x) { this.m03 *= x; return this; }
    public TMat4d scaleM10(double x) { this.m10 *= x; return this; }
    public TMat4d scaleM11(double x) { this.m11 *= x; return this; }
    public TMat4d scaleM12(double x) { this.m12 *= x; return this; }
    public TMat4d scaleM13(double x) { this.m13 *= x; return this; }
    public TMat4d scaleM20(double x) { this.m20 *= x; return this; }
    public TMat4d scaleM21(double x) { this.m21 *= x; return this; }
    public TMat4d scaleM22(double x) { this.m22 *= x; return this; }
    public TMat4d scaleM23(double x) { this.m23 *= x; return this; }
    public TMat4d scaleM30(double x) { this.m30 *= x; return this; }
    public TMat4d scaleM31(double x) { this.m31 *= x; return this; }
    public TMat4d scaleM32(double x) { this.m32 *= x; return this; }
    public TMat4d scaleM33(double x) { this.m33 *= x; return this; }

    // (ELEMENT WISE) Division

    public TMat4d div(double d00, double d01, double d02, double d03,
                      double d10, double d11, double d12, double d13,
                      double d20, double d21, double d22, double d23,
                      double d30, double d31, double d32, double d33)
    {
        this.m00 /= d00; this.m01 /= d01; this.m02 /= d02; this.m03 /= d03;
        this.m10 /= d10; this.m11 /= d11; this.m12 /= d12; this.m13 /= d13;
        this.m20 /= d20; this.m21 /= d21; this.m22 /= d22; this.m23 /= d23;
        this.m30 /= d30; this.m31 /= d31; this.m32 /= d32; this.m33 /= d33;
        return this;
    }

    public TMat4d div(TMat4d other) {
        this.m00 /= other.m00; this.m01 /= other.m01; this.m02 /= other.m02; this.m03 /= other.m03;
        this.m10 /= other.m10; this.m11 /= other.m11; this.m12 /= other.m12; this.m13 /= other.m13;
        this.m20 /= other.m20; this.m21 /= other.m21; this.m22 /= other.m22; this.m23 /= other.m23;
        this.m30 /= other.m30; this.m31 /= other.m31; this.m32 /= other.m32; this.m33 /= other.m33;
        return this;
    }

    public TMat4d div(double scale) {
        this.m00 /= scale; this.m01 /= scale; this.m02 /= scale; this.m03 /= scale;
        this.m10 /= scale; this.m11 /= scale; this.m12 /= scale; this.m13 /= scale;
        this.m20 /= scale; this.m21 /= scale; this.m22 /= scale; this.m23 /= scale;
        this.m30 /= scale; this.m31 /= scale; this.m32 /= scale; this.m33 /= scale;
        return this;
    }

    public TMat4d divM00(double x) { this.m00 /= x; return this; }
    public TMat4d divM01(double x) { this.m01 /= x; return this; }
    public TMat4d divM02(double x) { this.m02 /= x; return this; }
    public TMat4d divM03(double x) { this.m03 /= x; return this; }
    public TMat4d divM10(double x) { this.m10 /= x; return this; }
    public TMat4d divM11(double x) { this.m11 /= x; return this; }
    public TMat4d divM12(double x) { this.m12 /= x; return this; }
    public TMat4d divM13(double x) { this.m13 /= x; return this; }
    public TMat4d divM20(double x) { this.m20 /= x; return this; }
    public TMat4d divM21(double x) { this.m21 /= x; return this; }
    public TMat4d divM22(double x) { this.m22 /= x; return this; }
    public TMat4d divM23(double x) { this.m23 /= x; return this; }
    public TMat4d divM30(double x) { this.m30 /= x; return this; }
    public TMat4d divM31(double x) { this.m31 /= x; return this; }
    public TMat4d divM32(double x) { this.m32 /= x; return this; }
    public TMat4d divM33(double x) { this.m33 /= x; return this; }


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
    public TMat4d mul(double m00, double m01, double m02, double m03,
                      double m10, double m11, double m12, double m13,
                      double m20, double m21, double m22, double m23,
                      double m30, double m31, double m32, double m33)
    {
        double newM00 = this.m00 * m00 + this.m01 * m10 + this.m02 * m20 + this.m03 * m30;
        double newM01 = this.m00 * m01 + this.m01 * m11 + this.m02 * m21 + this.m03 * m31;
        double newM02 = this.m00 * m02 + this.m01 * m12 + this.m02 * m22 + this.m03 * m32;
        double newM03 = this.m00 * m03 + this.m01 * m13 + this.m02 * m23 + this.m03 * m33;
        double newM10 = this.m10 * m00 + this.m11 * m10 + this.m12 * m20 + this.m13 * m30;
        double newM11 = this.m10 * m01 + this.m11 * m11 + this.m12 * m21 + this.m13 * m31;
        double newM12 = this.m10 * m02 + this.m11 * m12 + this.m12 * m22 + this.m13 * m32;
        double newM13 = this.m10 * m03 + this.m11 * m13 + this.m12 * m23 + this.m13 * m33;
        double newM20 = this.m20 * m00 + this.m21 * m10 + this.m22 * m20 + this.m23 * m30;
        double newM21 = this.m20 * m01 + this.m21 * m11 + this.m22 * m21 + this.m23 * m31;
        double newM22 = this.m20 * m02 + this.m21 * m12 + this.m22 * m22 + this.m23 * m32;
        double newM23 = this.m20 * m03 + this.m21 * m13 + this.m22 * m23 + this.m23 * m33;
        double newM30 = this.m30 * m00 + this.m31 * m10 + this.m32 * m20 + this.m33 * m30;
        double newM31 = this.m30 * m01 + this.m31 * m11 + this.m32 * m21 + this.m33 * m31;
        double newM32 = this.m30 * m02 + this.m31 * m12 + this.m32 * m22 + this.m33 * m32;
        double newM33 = this.m30 * m03 + this.m31 * m13 + this.m32 * m23 + this.m33 * m33;

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
    public TMat4d mul(TMat4d other) {
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
    public TMat4d transpose() {
        // Swap m01 and m10
        double temp = this.m01;
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
    public TMat4d invert() {
        final double determinant = this.determinant();
        final double s = 1d / determinant;

        double newM00 = s * (m11 * m22 * m33 + m12 * m23 * m31 + m13 * m21 * m32 -
                             m13 * m22 * m31 - m12 * m21 * m33 - m11 * m23 * m32);
        double newM01 = s * (m03 * m22 * m31 + m02 * m21 * m33 + m01 * m23 * m32 -
                             m01 * m22 * m33 - m02 * m23 * m31 - m03 * m21 * m32);
        double newM02 = s * (m01 * m12 * m33 + m02 * m13 * m31 + m03 * m11 * m32 -
                             m03 * m12 * m31 - m02 * m11 * m33 - m01 * m13 * m32);
        double newM03 = s * (m03 * m12 * m21 + m02 * m11 * m23 + m01 * m13 * m22 -
                             m01 * m12 * m23 - m02 * m13 * m21 - m03 * m11 * m22);
        double newM10 = s * (m13 * m22 * m30 + m12 * m20 * m33 + m10 * m23 * m32 -
                             m10 * m22 * m33 - m12 * m23 * m30 - m13 * m20 * m32);
        double newM11 = s * (m00 * m22 * m33 + m02 * m23 * m30 + m03 * m20 * m32 -
                             m03 * m22 * m30 - m02 * m20 * m33 - m00 * m23 * m32);
        double newM12 = s * (m03 * m12 * m30 + m02 * m10 * m33 + m00 * m13 * m32 -
                             m00 * m12 * m33 - m02 * m13 * m30 - m03 * m10 * m32);
        double newM13 = s * (m00 * m12 * m23 + m02 * m13 * m20 + m03 * m10 * m22 -
                             m03 * m12 * m20 - m02 * m10 * m23 - m00 * m13 * m22);
        double newM20 = s * (m10 * m21 * m33 + m11 * m23 * m30 + m13 * m20 * m31 -
                             m13 * m21 * m30 - m11 * m20 * m33 - m10 * m23 * m31);
        double newM21 = s * (m03 * m21 * m30 + m01 * m20 * m33 + m00 * m23 * m31 -
                             m00 * m21 * m33 - m01 * m23 * m30 - m03 * m20 * m31);
        double newM22 = s * (m00 * m11 * m33 + m01 * m13 * m30 + m03 * m10 * m31 -
                             m03 * m11 * m30 - m01 * m10 * m33 - m00 * m13 * m31);
        double newM23 = s * (m03 * m11 * m20 + m01 * m10 * m23 + m00 * m13 * m21 -
                             m00 * m11 * m23 - m01 * m13 * m20 - m03 * m10 * m21);
        double newM30 = s * (m12 * m21 * m30 + m11 * m20 * m32 + m10 * m22 * m31 -
                             m10 * m21 * m32 - m11 * m22 * m30 - m12 * m20 * m31);
        double newM31 = s * (m00 * m21 * m32 + m01 * m22 * m30 + m02 * m20 * m31 -
                             m02 * m21 * m30 - m01 * m20 * m32 - m00 * m22 * m31);
        double newM32 = s * (m02 * m11 * m30 + m01 * m10 * m32 + m00 * m12 * m31 -
                             m00 * m11 * m32 - m01 * m12 * m30 - m02 * m10 * m31);
        double newM33 = s * (m00 * m11 * m22 + m01 * m12 * m20 + m02 * m10 * m21 -
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
     * @param function any double-to-double function
     * @return this matrix for method chaining
     */
    public TMat4d map(DoubleUnaryOperator function) {
        this.m00 = function.applyAsDouble(this.m00);
        this.m01 = function.applyAsDouble(this.m01);
        this.m02 = function.applyAsDouble(this.m02);
        this.m03 = function.applyAsDouble(this.m03);
        this.m10 = function.applyAsDouble(this.m10);
        this.m11 = function.applyAsDouble(this.m11);
        this.m12 = function.applyAsDouble(this.m12);
        this.m13 = function.applyAsDouble(this.m13);
        this.m20 = function.applyAsDouble(this.m20);
        this.m21 = function.applyAsDouble(this.m21);
        this.m22 = function.applyAsDouble(this.m22);
        this.m23 = function.applyAsDouble(this.m23);
        this.m30 = function.applyAsDouble(this.m30);
        this.m31 = function.applyAsDouble(this.m31);
        this.m32 = function.applyAsDouble(this.m32);
        this.m33 = function.applyAsDouble(this.m33);

        return this;
    }

    public TMat4d sin() { return map(TMath::sin); }
    public TMat4d cos() { return map(TMath::cos); }
    public TMat4d tan() { return map(TMath::tan); }
    public TMat4d sinh() { return map(TMath::sinh); }
    public TMat4d cosh() { return map(TMath::cosh); }
    public TMat4d tanh() { return map(TMath::tanh); }
    public TMat4d arcsin() { return map(TMath::arcsin); }
    public TMat4d arccos() { return map(TMath::arccos); }
    public TMat4d arctan() { return map(TMath::arctan); }

    public TMat4d abs() { return map(TMath::abs); }
    public TMat4d floor() { return map(TMath::floor); }
    public TMat4d ceil() { return map(TMath::ceil); }
    public TMat4d sqrt() { return map(TMath::sqrt); }
    public TMat4d cbrt() { return map(TMath::cbrt); }
    public TMat4d square() { return map(TMath::square); }
    public TMat4d cube() { return map(TMath::cube); }


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
    public TMat4d permuteGridFlipHorizontally() {
        // Swap m00 and m03
        double temp = this.m00;
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
    public TMat4d permuteGridFlipVertically() {
        // Swap m20 and m10
        double temp = this.m20;
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
    public TMat4d permuteGridRotate90Clockwise() {
        double temp = this.m12;
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
    public TMat4d permuteGridRotate90AntiClockwise() {
        double temp = this.m11;
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
    public TMat4d permuteGridRotate180() {
        // Swap m00 and m33
        double temp = this.m00;
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


    public static double determinant(double m00, double m01, double m02, double m03,
                                     double m10, double m11, double m12, double m13,
                                     double m20, double m21, double m22, double m23,
                                     double m30, double m31, double m32, double m33)
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

    public double determinant() {
        return determinant(
            this.m00, this.m01, this.m02, this.m03,
            this.m10, this.m11, this.m12, this.m13,
            this.m20, this.m21, this.m22, this.m23,
            this.m30, this.m31, this.m32, this.m33
        );
    }

    public double determinant3x3UpperLeft() {
        return TMat3d.determinant(
            m00, m01, m02,
            m10, m11, m12,
            m20, m21, m22
        );
    }

    public double determinant3x3UpperRight() {
        return TMat3d.determinant(
            m01, m02, m03,
            m11, m12, m13,
            m21, m22, m23
        );
    }

    public double determinant3x3BottomLeft() {
        return TMat3d.determinant(
            m10, m11, m12,
            m20, m21, m22,
            m30, m31, m32
        );
    }

    public double determinant3x3BottomRight() {
        return TMat3d.determinant(
            m11, m12, m13,
            m21, m22, m23,
            m31, m32, m33
        );
    }

    public double trace() {
        return this.m00 + this.m11 + this.m22 + this.m33;
    }


    /*///////////////////////////////////////////////////////////////*/
    /*/////////////////////  GETTERS & SETTERS  /////////////////////*/
    /*///////////////////////////////////////////////////////////////*/

    // Getters

    public double getM00() { return this.m00; }
    public double getM01() { return this.m01; }
    public double getM02() { return this.m02; }
    public double getM03() { return this.m03; }
    public double getM10() { return this.m10; }
    public double getM11() { return this.m11; }
    public double getM12() { return this.m12; }
    public double getM13() { return this.m13; }
    public double getM20() { return this.m20; }
    public double getM21() { return this.m21; }
    public double getM22() { return this.m22; }
    public double getM23() { return this.m23; }
    public double getM30() { return this.m30; }
    public double getM31() { return this.m31; }
    public double getM32() { return this.m32; }
    public double getM33() { return this.m33; }

    public double[][] toArray(){
        return new double[][] {
            { this.m00, this.m01, this.m02, this.m03 },
            { this.m10, this.m11, this.m12, this.m13 },
            { this.m20, this.m21, this.m22, this.m23 },
            { this.m30, this.m31, this.m32, this.m33 }
        };
    }

    // Setters

    public TMat4d setM00(double m00) { this.m00 = m00; return this; }
    public TMat4d setM01(double m01) { this.m01 = m01; return this; }
    public TMat4d setM02(double m02) { this.m02 = m02; return this; }
    public TMat4d setM03(double m03) { this.m03 = m03; return this; }
    public TMat4d setM10(double m10) { this.m10 = m10; return this; }
    public TMat4d setM11(double m11) { this.m11 = m11; return this; }
    public TMat4d setM12(double m12) { this.m12 = m12; return this; }
    public TMat4d setM13(double m13) { this.m13 = m13; return this; }
    public TMat4d setM20(double m20) { this.m20 = m20; return this; }
    public TMat4d setM21(double m21) { this.m21 = m21; return this; }
    public TMat4d setM22(double m22) { this.m22 = m22; return this; }
    public TMat4d setM23(double m23) { this.m23 = m23; return this; }
    public TMat4d setM30(double m30) { this.m30 = m30; return this; }
    public TMat4d setM31(double m31) { this.m31 = m31; return this; }
    public TMat4d setM32(double m32) { this.m32 = m32; return this; }
    public TMat4d setM33(double m33) { this.m33 = m33; return this; }

    public TMat4d set(double m00, double m01, double m02, double m03,
                      double m10, double m11, double m12, double m13,
                      double m20, double m21, double m22, double m23,
                      double m30, double m31, double m32, double m33)
    {
        this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
        this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
        this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
        this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
        return this;
    }

    public TMat4d set(TMat4d other) {
        this.m00 = other.m00; this.m01 = other.m01; this.m02 = other.m02; this.m03 = other.m03;
        this.m10 = other.m10; this.m11 = other.m11; this.m12 = other.m12; this.m13 = other.m13;
        this.m20 = other.m20; this.m21 = other.m21; this.m22 = other.m22; this.m23 = other.m23;
        this.m30 = other.m30; this.m31 = other.m31; this.m32 = other.m32; this.m33 = other.m33;
        return this;
    }

    // Min max sum getters for rows and cols

    public double getMinOfRow0() { return TMath.min(this.m00, this.m01, this.m02, this.m03); }
    public double getMaxOfRow0() { return TMath.max(this.m00, this.m01, this.m02, this.m03); }
    public double getSumOfRow0() { return this.m00 + this.m01 + this.m02 + this.m03; }

    public double getMinOfRow1() { return TMath.min(this.m10, this.m11, this.m12, this.m13); }
    public double getMaxOfRow1() { return TMath.max(this.m10, this.m11, this.m12, this.m13); }
    public double getSumOfRow1() { return this.m10 + this.m11 + this.m12 + this.m13; }

    public double getMinOfRow2() { return TMath.min(this.m20, this.m21, this.m22, this.m23); }
    public double getMaxOfRow2() { return TMath.max(this.m20, this.m21, this.m22, this.m23); }
    public double getSumOfRow2() { return this.m20 + this.m21 + this.m22 + this.m23; }

    public double getMinOfRow3() { return TMath.min(this.m30, this.m31, this.m32, this.m33); }
    public double getMaxOfRow3() { return TMath.max(this.m30, this.m31, this.m32, this.m33); }
    public double getSumOfRow3() { return this.m30 + this.m31 + this.m32 + this.m33; }

    public double getMinOfCol0() { return TMath.min(this.m00, this.m10, this.m20, this.m30); }
    public double getMaxOfCol0() { return TMath.max(this.m00, this.m10, this.m20, this.m30); }
    public double getSumOfCol0() { return this.m00 + this.m10 + this.m20 + this.m30; }

    public double getMinOfCol1() { return TMath.min(this.m01, this.m11, this.m21, this.m31); }
    public double getMaxOfCol1() { return TMath.max(this.m01, this.m11, this.m21, this.m31); }
    public double getSumOfCol1() { return this.m01 + this.m11 + this.m21 + this.m31; }

    public double getMinOfCol2() { return TMath.min(this.m02, this.m12, this.m22, this.m32); }
    public double getMaxOfCol2() { return TMath.max(this.m02, this.m12, this.m22, this.m32); }
    public double getSumOfCol2() { return this.m02 + this.m12 + this.m22 + this.m32; }

    public double getMinOfCol3() { return TMath.min(this.m03, this.m13, this.m23, this.m33); }
    public double getMaxOfCol3() { return TMath.max(this.m03, this.m13, this.m23, this.m33); }
    public double getSumOfCol3() { return this.m03 + this.m13 + this.m23 + this.m33; }

    // Indexed getters & setters

    public double get(int row, int col) {
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

    public TMat4d set(int row, int col, double value) {
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

    public double getMinOfRow(int row) {
        if (row == 0) return getMinOfRow0();
        if (row == 1) return getMinOfRow1();
        if (row == 2) return getMinOfRow2();
        if (row == 3) return getMinOfRow3();
        throw new TephriumException("4x4 Matrix row index out of range: %d", row);
    }

    public double getMaxOfRow(int row) {
        if (row == 0) return getMaxOfRow0();
        if (row == 1) return getMaxOfRow1();
        if (row == 2) return getMaxOfRow2();
        if (row == 3) return getMaxOfRow3();
        throw new TephriumException("4x4 Matrix row index out of range: %d", row);
    }

    public double getSumOfRow(int row) {
        if (row == 0) return getSumOfRow0();
        if (row == 1) return getSumOfRow1();
        if (row == 2) return getSumOfRow2();
        if (row == 3) return getSumOfRow3();
        throw new TephriumException("4x4 Matrix row index out of range: %d", row);
    }

    public double getMinOfCol(int col) {
        if (col == 0) return getMinOfCol0();
        if (col == 1) return getMinOfCol1();
        if (col == 2) return getMinOfCol2();
        if (col == 3) return getMinOfCol3();
        throw new TephriumException("4x4 Matrix column index out of range: %d", col);
    }

    public double getMaxOfCol(int col) {
        if (col == 0) return getMaxOfCol0();
        if (col == 1) return getMaxOfCol1();
        if (col == 2) return getMaxOfCol2();
        if (col == 3) return getMaxOfCol3();
        throw new TephriumException("4x4 Matrix column index out of range: %d", col);
    }

    public double getSumOfCol(int col) {
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
            1d, 0d, 0d, 0d,
            0d, 1d, 0d, 0d,
            0d, 0d, 1d, 0d,
            0d, 0d, 0d, 1d
        );
    }

    public boolean isZero() {
        return this.equals(
            0d, 0d, 0d, 0d,
            0d, 0d, 0d, 0d,
            0d, 0d, 0d, 0d,
            0d, 0d, 0d, 0d
        );
    }


    /*//////////////////////////////////////////////////////////////////*/
    /*////////////////////////  OBJECT METHODS  ////////////////////////*/
    /*//////////////////////////////////////////////////////////////////*/


    public boolean equals(TMat4d other) {
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

    public boolean equals(double m00, double m01, double m02, double m03,
                          double m10, double m11, double m12, double m13,
                          double m20, double m21, double m22, double m23,
                          double m30, double m31, double m32, double m33)
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

    private static final String TMAT4D_STR_FORMAT = "[%.5f, %.5f, %.5f, %.5f]\n" +
                                                    "[%.5f, %.5f, %.5f, %.5f]\n" +
                                                    "[%.5f, %.5f, %.5f, %.5f]\n" +
                                                    "[%.5f, %.5f, %.5f, %.5f]\n";

    @Override
    public String toString() {
        return String.format(
            TMAT4D_STR_FORMAT,
            this.m00, this.m01, this.m02, this.m03,
            this.m10, this.m11, this.m12, this.m13,
            this.m20, this.m21, this.m22, this.m23,
            this.m30, this.m31, this.m32, this.m33
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        return this.equals((TMat4d) o);
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

