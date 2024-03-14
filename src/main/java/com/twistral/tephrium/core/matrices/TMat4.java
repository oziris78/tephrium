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


package com.twistral.tephrium.core.matrices;


import com.twistral.tephrium.core.functions.TMath;
import java.util.Objects;
import java.util.function.Function;


/**
 * A fast and mutable 4x4 double matrix class. <br>
 * All methods either return a numeric value or this matrix for method chaining purposes. <br>
 * Also see {@link TMat2}, {@link TMat3}, {@link TMatN}
 */
public class TMat4 {

    private double m00, m01, m02, m03,   /*    [ m00, m01, m02, m03 ]    */
                   m10, m11, m12, m13,   /*    [ m10, m11, m12, m13 ]    */
                   m20, m21, m22, m23,   /*    [ m20, m21, m22, m23 ]    */
                   m30, m31, m32, m33;   /*    [ m30, m31, m32, m33 ]    */


    //////////////////////////////////////////////////////////////////////////
    /////////////////////////////  CONSTRUCTORS  /////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    public TMat4(double m00, double m01, double m02, double m03,
                 double m10, double m11, double m12, double m13,
                 double m20, double m21, double m22, double m23,
                 double m30, double m31, double m32, double m33)
    {
        set(
            m00, m01, m02, m03,
            m10, m11, m12, m13,
            m20, m21, m22, m23,
            m30, m31, m32, m33
        );
    }


    /**  Returns I<sub>4</sub> which is the 4x4 identity matrix.  */
    public TMat4(){
        set(
                1d, 0d, 0d, 0d,
                0d, 1d, 0d, 0d,
                0d, 0d, 1d, 0d,
                0d, 0d, 0d, 1d
        );
    }


    public TMat4(double fillValue){
        set(
                fillValue, fillValue, fillValue, fillValue,
                fillValue, fillValue, fillValue, fillValue,
                fillValue, fillValue, fillValue, fillValue,
                fillValue, fillValue, fillValue, fillValue
        );
    }


    public double[][] getAsArray(){
        return new double[][]{
                { m00, m01, m02, m03 },
                { m10, m11, m12, m13 },
                { m20, m21, m22, m23 },
                { m30, m31, m32, m33 }
        };
    }


    public TMat4 copy(){
        return new TMat4(
            m00, m01, m02, m03,
            m10, m11, m12, m13,
            m20, m21, m22, m23,
            m30, m31, m32, m33
        );
    }


    ///////////////////////////////////////////////////////////////////////////////
    /////////////////////////////  GETTERS & SETTERS  /////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////


    public TMat4 set(double m00, double m01, double m02, double m03,
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


    public TMat4 setCell(int row, int col, double value){
        if(row == 0 && col == 0) m00 = value;
        else if(row == 0 && col == 1) m01 = value;
        else if(row == 0 && col == 2) m02 = value;
        else if(row == 0 && col == 3) m03 = value;
        else if(row == 1 && col == 0) m10 = value;
        else if(row == 1 && col == 1) m11 = value;
        else if(row == 1 && col == 2) m12 = value;
        else if(row == 1 && col == 3) m13 = value;
        else if(row == 2 && col == 0) m20 = value;
        else if(row == 2 && col == 1) m21 = value;
        else if(row == 2 && col == 2) m22 = value;
        else if(row == 2 && col == 3) m23 = value;
        else if(row == 3 && col == 0) m30 = value;
        else if(row == 3 && col == 1) m31 = value;
        else if(row == 3 && col == 2) m32 = value;
        else if(row == 3 && col == 3) m33 = value;
        return this;
    }


    public double getCell(int row, int col){
        if(row == 0 && col == 0) return this.m00;
        if(row == 0 && col == 1) return this.m01;
        if(row == 0 && col == 2) return this.m02;
        if(row == 0 && col == 3) return this.m03;
        if(row == 1 && col == 0) return this.m10;
        if(row == 1 && col == 1) return this.m11;
        if(row == 1 && col == 2) return this.m12;
        if(row == 1 && col == 3) return this.m13;
        if(row == 2 && col == 0) return this.m20;
        if(row == 2 && col == 1) return this.m21;
        if(row == 2 && col == 2) return this.m22;
        if(row == 2 && col == 3) return this.m23;
        if(row == 3 && col == 0) return this.m30;
        if(row == 3 && col == 1) return this.m31;
        if(row == 3 && col == 2) return this.m32;
        if(row == 3 && col == 3) return this.m33;
        return Double.NaN;
    }


    public double getMaxOfRow(int row) {
        if(row == 0) return TMath.max(m00, m01, m02, m03);
        if(row == 1) return TMath.max(m10, m11, m12, m13);
        if(row == 2) return TMath.max(m20, m21, m22, m23);
        if(row == 3) return TMath.max(m30, m31, m32, m33);
        return Double.NaN;
    }


    public double getMinOfRow(int row) {
        if(row == 0) return TMath.min(m00, m01, m02, m03);
        if(row == 1) return TMath.min(m10, m11, m12, m13);
        if(row == 2) return TMath.min(m20, m21, m22, m23);
        if(row == 3) return TMath.min(m30, m31, m32, m33);
        return Double.NaN;
    }


    public double getSumOfRow(int row) {
        if(row == 0) return m00 + m01 + m02 + m03;
        if(row == 1) return m10 + m11 + m12 + m13;
        if(row == 2) return m20 + m21 + m22 + m23;
        if(row == 3) return m30 + m31 + m32 + m33;
        return Double.NaN;
    }


    public double getMaxOfCol(int col) {
        if(col == 0) return TMath.max(m00, m10, m20, m30);
        if(col == 1) return TMath.max(m01, m11, m21, m31);
        if(col == 2) return TMath.max(m02, m12, m22, m32);
        if(col == 3) return TMath.max(m03, m13, m23, m33);
        return Double.NaN;
    }


    public double getMinOfCol(int col) {
        if(col == 0) return TMath.min(m00, m10, m20, m30);
        if(col == 1) return TMath.min(m01, m11, m21, m31);
        if(col == 2) return TMath.min(m02, m12, m22, m32);
        if(col == 3) return TMath.min(m03, m13, m23, m33);
        return Double.NaN;
    }


    public double getSumOfCol(int col) {
        if(col == 0) return m00 + m10 + m20 + m30;
        if(col == 1) return m01 + m11 + m21 + m31;
        if(col == 2) return m02 + m12 + m22 + m32;
        if(col == 3) return m03 + m13 + m23 + m33;
        return Double.NaN;
    }


    ///////////////////////////////////////////////////////////////////////////
    /////////////////////////  ARITHMETIC OPERATIONS  /////////////////////////
    ///////////////////////////////////////////////////////////////////////////


    public TMat4 add(double m00, double m01, double m02, double m03,
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


    public TMat4 add(TMat4 mat){
        return add(
            mat.m00, mat.m01, mat.m02, mat.m03,
            mat.m10, mat.m11, mat.m12, mat.m13,
            mat.m20, mat.m21, mat.m22, mat.m23,
            mat.m30, mat.m31, mat.m32, mat.m33
        );
    }

    // ----------------------- //

    public TMat4 sub(double m00, double m01, double m02, double m03,
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


    public TMat4 sub(TMat4 mat){
        return sub(
                mat.m00, mat.m01, mat.m02, mat.m03,
                mat.m10, mat.m11, mat.m12, mat.m13,
                mat.m20, mat.m21, mat.m22, mat.m23,
                mat.m30, mat.m31, mat.m32, mat.m33
        );
    }

    // ----------------------- //

    public TMat4 scale(double scale) {
        return applyFunctionElementWise(x -> x * scale);
    }

    // ----------------------- //

    /**
     * Does thisMatrix = thisMatrix * parameterMatrix. <br>
     * Make sure to use {@link #copy()} method to keep the original matrix.
     * @return this matrix for method chaining
     */
    public TMat4 multiply(double m00, double m01, double m02, double m03,
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

        set(
                newM00, newM01, newM02, newM03,
                newM10, newM11, newM12, newM13,
                newM20, newM21, newM22, newM23,
                newM30, newM31, newM32, newM33
        );

        return this;
    }


    /**
     * Does thisMatrix = thisMatrix * parameterMatrix. <br>
     * Make sure to use {@link #copy()} method to keep the original matrix.
     * @return this matrix for method chaining
     */
    public TMat4 multiply(TMat4 mat) {
        return multiply(
                mat.m00, mat.m01, mat.m02, mat.m03,
                mat.m10, mat.m11, mat.m12, mat.m13,
                mat.m20, mat.m21, mat.m22, mat.m23,
                mat.m30, mat.m31, mat.m32, mat.m33
        );
    }

    // ----------------------- //

    public TMat4 divide(double m00, double m01, double m02, double m03,
                        double m10, double m11, double m12, double m13,
                        double m20, double m21, double m22, double m23,
                        double m30, double m31, double m32, double m33)
    {
        this.m00 /= m00; this.m01 /= m01; this.m02 /= m02; this.m03 /= m03;
        this.m10 /= m10; this.m11 /= m11; this.m12 /= m12; this.m13 /= m13;
        this.m20 /= m20; this.m21 /= m21; this.m22 /= m22; this.m23 /= m23;
        this.m30 /= m30; this.m31 /= m31; this.m32 /= m32; this.m33 /= m33;
        return this;
    }


    /**
     * Performs element-wise division.
     * @param mat any matrix
     * @return this matrix for method chaining
     */
    public TMat4 divide(TMat4 mat) {
        return divide(
                mat.m00, mat.m01, mat.m02, mat.m03,
                mat.m10, mat.m11, mat.m12, mat.m13,
                mat.m20, mat.m21, mat.m22, mat.m23,
                mat.m30, mat.m31, mat.m32, mat.m33
        );
    }

    // ----------------------- //

    /**
     * Does thisMatrix = inverse(thisMatrix). <br>
     * Make sure to use {@link #copy()} method to keep the original matrix.
     * @return this matrix for method chaining
     */
    public TMat4 invert(){
        double det = this.determinant();
        if(TMath.equalsd(det, 0d))
            return null;

        set( // definitely first try ;)
            (m11 * m22 * m33 + m12 * m23 * m31 + m13 * m21 * m32 - m13 * m22 * m31 - m12 * m21 * m33 - m11 * m23 * m32) / det,
            (m03 * m22 * m31 + m02 * m21 * m33 + m01 * m23 * m32 - m01 * m22 * m33 - m02 * m23 * m31 - m03 * m21 * m32) / det,
            (m01 * m12 * m33 + m02 * m13 * m31 + m03 * m11 * m32 - m03 * m12 * m31 - m02 * m11 * m33 - m01 * m13 * m32) / det,
            (m03 * m12 * m21 + m02 * m11 * m23 + m01 * m13 * m22 - m01 * m12 * m23 - m02 * m13 * m21 - m03 * m11 * m22) / det,
            (m13 * m22 * m30 + m12 * m20 * m33 + m10 * m23 * m32 - m10 * m22 * m33 - m12 * m23 * m30 - m13 * m20 * m32) / det,
            (m00 * m22 * m33 + m02 * m23 * m30 + m03 * m20 * m32 - m03 * m22 * m30 - m02 * m20 * m33 - m00 * m23 * m32) / det,
            (m03 * m12 * m30 + m02 * m10 * m33 + m00 * m13 * m32 - m00 * m12 * m33 - m02 * m13 * m30 - m03 * m10 * m32) / det,
            (m00 * m12 * m23 + m02 * m13 * m20 + m03 * m10 * m22 - m03 * m12 * m20 - m02 * m10 * m23 - m00 * m13 * m22) / det,
            (m10 * m21 * m33 + m11 * m23 * m30 + m13 * m20 * m31 - m13 * m21 * m30 - m11 * m20 * m33 - m10 * m23 * m31) / det,
            (m03 * m21 * m30 + m01 * m20 * m33 + m00 * m23 * m31 - m00 * m21 * m33 - m01 * m23 * m30 - m03 * m20 * m31) / det,
            (m00 * m11 * m33 + m01 * m13 * m30 + m03 * m10 * m31 - m03 * m11 * m30 - m01 * m10 * m33 - m00 * m13 * m31) / det,
            (m03 * m11 * m20 + m01 * m10 * m23 + m00 * m13 * m21 - m00 * m11 * m23 - m01 * m13 * m20 - m03 * m10 * m21) / det,
            (m12 * m21 * m30 + m11 * m20 * m32 + m10 * m22 * m31 - m10 * m21 * m32 - m11 * m22 * m30 - m12 * m20 * m31) / det,
            (m00 * m21 * m32 + m01 * m22 * m30 + m02 * m20 * m31 - m02 * m21 * m30 - m01 * m20 * m32 - m00 * m22 * m31) / det,
            (m02 * m11 * m30 + m01 * m10 * m32 + m00 * m12 * m31 - m00 * m11 * m32 - m01 * m12 * m30 - m02 * m10 * m31) / det,
            (m00 * m11 * m22 + m01 * m12 * m20 + m02 * m10 * m21 - m02 * m11 * m20 - m01 * m10 * m22 - m00 * m12 * m21) / det
        );

        return this;
    }

    // ----------------------- //

    /**
     * Applies the given function to all elements. <br>
     * Example usage: myMatrix.applyFunctionElementWise(x -> x*2 + 2);
     * @param function any function that takes in a double and returns one
     * @return this matrix for method chaining
     */
    public TMat4 applyFunctionElementWise(Function<Double, Double> function) {
        set(
                function.apply(m00), function.apply(m01), function.apply(m02), function.apply(m03),
                function.apply(m10), function.apply(m11), function.apply(m12), function.apply(m13),
                function.apply(m20), function.apply(m21), function.apply(m22), function.apply(m23),
                function.apply(m30), function.apply(m31), function.apply(m32), function.apply(m33)
        );
        return this;
    }

    public TMat4 sin() { return applyFunctionElementWise(TMath::sin); }
    public TMat4 cos() { return applyFunctionElementWise(TMath::cos); }
    public TMat4 tan() { return applyFunctionElementWise(TMath::tan); }
    public TMat4 arcsin() { return applyFunctionElementWise(TMath::arcsin); }
    public TMat4 arccos() { return applyFunctionElementWise(TMath::arccos); }
    public TMat4 arctan() { return applyFunctionElementWise(TMath::arctan); }
    public TMat4 floor() { return applyFunctionElementWise(TMath::floor); }
    public TMat4 ceil() { return applyFunctionElementWise(TMath::ceil); }
    public TMat4 sqrt() { return applyFunctionElementWise(TMath::sqrt); }
    public TMat4 square() { return applyFunctionElementWise(TMath::square); }
    public TMat4 sinh() { return applyFunctionElementWise(TMath::sinh); }
    public TMat4 cosh() { return applyFunctionElementWise(TMath::cosh); }
    public TMat4 tanh() { return applyFunctionElementWise(TMath::tanh); }


    //////////////////////////////////////////////////////////////////////////
    /////////////////////////  GEOMETRIC OPERATIONS  /////////////////////////
    //////////////////////////////////////////////////////////////////////////


    /**
     * Flips this matrix horizontally. <br>
     * Example: <br>
     * [10 11 12 13]   =>   [13 12 11 10]  <br>
     * [14 15 16 17]   =>   [17 16 15 14]  <br>
     * [18 19 20 21]   =>   [21 20 19 18]  <br>
     * [22 23 24 25]   =>   [25 24 23 22]  <br>
     * @return this matrix for method chaining
     */
    public TMat4 flipHorizontally(){
        double temp;

        temp = m00;
        m00 = m03;
        m03 = temp;

        temp = m10;
        m10 = m13;
        m13 = temp;

        temp = m20;
        m20 = m23;
        m23 = temp;

        temp = m30;
        m30 = m33;
        m33 = temp;

        temp = m01;
        m01 = m02;
        m02= temp;

        temp = m21;
        m21 = m22;
        m22 = temp;

        temp = m11;
        m11 = m12;
        m12 = temp;

        temp = m31;
        m31 = m32;
        m32 = temp;

        return this;
    }


    /**
     * Flips this matrix vertically. <br>
     * Example: <br>
     * [10 11 12 13]   =>   [22 23 24 25]  <br>
     * [14 15 16 17]   =>   [18 19 20 21]  <br>
     * [18 19 20 21]   =>   [14 15 16 17]  <br>
     * [22 23 24 25]   =>   [10 11 12 13]  <br>
     * @return this matrix for method chaining
     */
    public TMat4 flipVertically(){
        double temp;

        temp = m20;
        m20 = m10;
        m10 = temp;

        temp = m11;
        m11 = m21;
        m21 = temp;

        temp = m12;
        m12 = m22;
        m22 = temp;

        temp = m23;
        m23 = m13;
        m13 = temp;

        temp = m00;
        m00 = m30;
        m30 = temp;

        temp = m01;
        m01 = m31;
        m31 = temp;

        temp = m02;
        m02 = m32;
        m32 = temp;

        temp = m03;
        m03 = m33;
        m33 = temp;

        return this;
    }


    /**
     * Rotates this matrix 90 degrees clockwise. <br>
     * Example: <br>
     * [10 11 12 13]   =>   [22 18 14 10]  <br>
     * [14 15 16 17]   =>   [23 19 15 11]  <br>
     * [18 19 20 21]   =>   [24 20 16 12]  <br>
     * [22 23 24 25]   =>   [25 21 17 13]  <br>
     * @return this matrix for method chaining
     */
    public TMat4 rotate90DegClockwise(){
        double temp;

        temp = m12;
        m12 = m11;
        m11 = m21;
        m21 = m22;
        m22 = temp;

        temp = m02;
        m02 = m10;
        m10 = m31;
        m31 = m23;
        m23 = temp;

        temp = m01;
        m01 = m20;
        m20 = m32;
        m32 = m13;
        m13 = temp;

        temp = m03;
        m03 = m00;
        m00 = m30;
        m30 = m33;
        m33 = temp;

        return this;
    }


    /**
     * Rotates this matrix 90 degrees anti-clockwise. <br>
     * Example: <br>
     * [10 11 12 13]   =>   [13 17 21 25]  <br>
     * [14 15 16 17]   =>   [12 16 20 24]  <br>
     * [18 19 20 21]   =>   [11 15 19 23]  <br>
     * [22 23 24 25]   =>   [10 14 18 22]  <br>
     * @return this matrix for method chaining
     */
    public TMat4 rotate90DegAntiClockwise(){
        double temp;

        temp = m11;
        m11 = m12;
        m12 = m22;
        m22 = m21;
        m21 = temp;

        temp = m10;
        m10 = m02;
        m02 = m23;
        m23 = m31;
        m31 = temp;

        temp = m00;
        m00 = m03;
        m03 = m33;
        m33 = m30;
        m30 = temp;

        temp = m01;
        m01 = m13;
        m13 = m32;
        m32 = m20;
        m20 = temp;

        return this;
    }


    /**
     * Rotates this matrix 180 degrees clockwise / anti-clockwise. <br>
     * Example: <br>
     * [10 11 12 13]   =>   [25 24 23 22]  <br>
     * [14 15 16 17]   =>   [21 20 19 18]  <br>
     * [18 19 20 21]   =>   [17 16 15 14]  <br>
     * [22 23 24 25]   =>   [13 12 11 10]  <br>
     * @return this matrix for method chaining
     */
    public TMat4 rotate180Deg(){
        double temp;

        temp = m00;
        m00 = m33;
        m33 = temp;

        temp = m10;
        m10 = m23;
        m23 = temp;

        temp = m30;
        m30 = m03;
        m03 = temp;

        temp = m31;
        m31 = m02;
        m02 = temp;

        temp = m32;
        m32 = m01;
        m01 = temp;

        temp = m20;
        m20 = m13;
        m13 = temp;

        temp = m21;
        m21 = m12;
        m12 = temp;

        temp = m22;
        m22 = m11;
        m11 = temp;

        return this;
    }


    /**
     * Calculates the transpose of this matrix and assigns it to this matrix. <br>
     * @return this matrix for method chaining
     */
    public TMat4 transpose() {
        double temp;

        temp = this.m01;
        this.m01 = this.m10;
        this.m10 = temp;

        temp = this.m02;
        this.m02 = this.m20;
        this.m20 = temp;

        temp = this.m21;
        this.m21 = this.m12;
        this.m12 = temp;

        temp = this.m03;
        this.m03 = this.m30;
        this.m30 = temp;

        temp = this.m23;
        this.m23 = this.m32;
        this.m32 = temp;

        temp = this.m13;
        this.m13 = this.m31;
        this.m31 = temp;

        return this;
    }


    /////////////////////////////////////////////////////////////////////////
    /////////////////////////  SPECIAL VALUE FUNCS  /////////////////////////
    /////////////////////////////////////////////////////////////////////////

    public double trace(){
        return m00 + m11 + m22 + m33;
    }

    // ------------- //

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
            m00, m01, m02, m03,
            m10, m11, m12, m13,
            m20, m21, m22, m23,
            m30, m31, m32, m33
        );
    }

    public double determinant3x3UpperLeft() {
        return TMat3.determinant(
            m00, m01, m02,
            m10, m11, m12,
            m20, m21, m22
        );
    }


    public double determinant3x3UpperRight() {
        return TMat3.determinant(
            m01, m02, m03,
            m11, m12, m13,
            m21, m22, m23
        );
    }

    public double determinant3x3BottomLeft() {
        return TMat3.determinant(
            m10, m11, m12,
            m20, m21, m22,
            m30, m31, m32
        );
    }


    public double determinant3x3BottomRight() {
        return TMat3.determinant(
            m11, m12, m13,
            m21, m22, m23,
            m31, m32, m33
        );
    }


    ///////////////////////////////////////////////////////////////////
    /////////////////////////  BOOLEAN FUNCS  /////////////////////////
    ///////////////////////////////////////////////////////////////////


    public boolean isSymmetrical(){
        return TMath.equalsd(this.m01, this.m10) && TMath.equalsd(this.m02, this.m20) &&
                TMath.equalsd(this.m21, this.m12) && TMath.equalsd(this.m30, this.m03) &&
                TMath.equalsd(this.m23, this.m32) && TMath.equalsd(this.m13, this.m31);
    }


    public boolean isSingular(){
        return TMath.equalsd(this.determinant(), 0d);
    }


    public boolean isIdentity(){
        return  TMath.equalsd(m00, 1d) && TMath.equalsd(m01, 0d) && TMath.equalsd(m02, 0d) && TMath.equalsd(m03, 0d) &&
                TMath.equalsd(m10, 0d) && TMath.equalsd(m11, 1d) && TMath.equalsd(m12, 0d) && TMath.equalsd(m13, 0d) &&
                TMath.equalsd(m20, 0d) && TMath.equalsd(m21, 0d) && TMath.equalsd(m22, 1d) && TMath.equalsd(m23, 0d) &&
                TMath.equalsd(m30, 0d) && TMath.equalsd(m31, 0d) && TMath.equalsd(m32, 0d) && TMath.equalsd(m33, 1d);
    }


    ////////////////////////////////////////////////////////////////////
    /////////////////////////  OBJECT METHODS  /////////////////////////
    ////////////////////////////////////////////////////////////////////


    @Override
    public String toString() {
        return String.format(
            "[%.5f, %.5f, %.5f, %.5f]\n[%.5f, %.5f, %.5f, %.5f]\n[%.5f, %.5f, %.5f, %.5f]\n[%.5f, %.5f, %.5f, %.5f]\n",
            m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        TMat4 other = (TMat4) o;
        return TMath.equalsd(other.m00, m00) && TMath.equalsd(other.m01, m01) && TMath.equalsd(other.m02, m02) &&
            TMath.equalsd(other.m03, m03) && TMath.equalsd(other.m10, m10) && TMath.equalsd(other.m11, m11) &&
            TMath.equalsd(other.m12, m12) && TMath.equalsd(other.m13, m13) && TMath.equalsd(other.m20, m20) &&
            TMath.equalsd(other.m21, m21) && TMath.equalsd(other.m22, m22) && TMath.equalsd(other.m23, m23) &&
            TMath.equalsd(other.m30, m30) && TMath.equalsd(other.m31, m31) && TMath.equalsd(other.m32, m32) &&
            TMath.equalsd(other.m33, m33);
    }


    @Override
    public int hashCode() {
        return Objects.hash(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
    }


}
