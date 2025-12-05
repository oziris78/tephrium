// Copyright 2020-2025 Oğuzhan Topaloğlu
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
 * A fast and mutable 3x3 double matrix class. <br>
 * All methods either return a numeric value or this matrix for method chaining purposes. <br>
 * Also see {@link TMat2}, {@link TMat4}, {@link TMatN}
 */
public class TMat3 {

    private double m00, m01, m02,   /*    [ m00, m01, m02 ]    */
                   m10, m11, m12,   /*    [ m10, m11, m12 ]    */
                   m20, m21, m22;   /*    [ m20, m21, m22 ]    */


    /*//////////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  CONSTRUCTORS  ///////////////////////////*/
    /*//////////////////////////////////////////////////////////////////////*/

    public TMat3(double m00, double m01, double m02,
                 double m10, double m11, double m12,
                 double m20, double m21, double m22)
    {
        set(
                m00, m01, m02,
                m10, m11, m12,
                m20, m21, m22
        );
    }


    /**  Returns I<sub>3</sub> which is the 3x3 identity matrix.  */
    public TMat3(){
        set(
                1d, 0d, 0d,
                0d, 1d, 0d,
                0d, 0d, 1d
        );
    }


    public TMat3(double fillValue){
        set(
                fillValue, fillValue, fillValue,
                fillValue, fillValue, fillValue,
                fillValue, fillValue, fillValue
        );
    }


    public double[][] getAsArray(){
        return new double[][]{
                { m00, m01, m02 },
                { m10, m11, m12 },
                { m20, m21, m22 }
        };
    }


    public TMat3 copy(){
        return new TMat3(
                m00, m01, m02,
                m10, m11, m12,
                m20, m21, m22
        );
    }


    /*///////////////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  GETTERS & SETTERS  ///////////////////////////*/
    /*///////////////////////////////////////////////////////////////////////////*/


    public TMat3 set(double m00, double m01, double m02,
                     double m10, double m11, double m12,
                     double m20, double m21, double m22)
    {
        this.m00 = m00; this.m01 = m01; this.m02 = m02;
        this.m10 = m10; this.m11 = m11; this.m12 = m12;
        this.m20 = m20; this.m21 = m21; this.m22 = m22;
        return this;
    }


    public TMat3 setCell(int row, int col, double value){
        if(row == 0 && col == 0) m00 = value;
        else if(row == 0 && col == 1) m01 = value;
        else if(row == 0 && col == 2) m02 = value;
        else if(row == 1 && col == 0) m10 = value;
        else if(row == 1 && col == 1) m11 = value;
        else if(row == 1 && col == 2) m12 = value;
        else if(row == 2 && col == 0) m20 = value;
        else if(row == 2 && col == 1) m21 = value;
        else if(row == 2 && col == 2) m22 = value;
        return this;
    }


    public double getCell(int row, int col){
        if(row == 0 && col == 0) return this.m00;
        if(row == 0 && col == 1) return this.m01;
        if(row == 0 && col == 2) return this.m02;
        if(row == 1 && col == 0) return this.m10;
        if(row == 1 && col == 1) return this.m11;
        if(row == 1 && col == 2) return this.m12;
        if(row == 2 && col == 0) return this.m20;
        if(row == 2 && col == 1) return this.m21;
        if(row == 2 && col == 2) return this.m22;
        return Double.NaN;
    }


    public double getMaxOfRow(int row) {
        if(row == 0) return TMath.max(m00, m01, m02);
        if(row == 1) return TMath.max(m10, m11, m12);
        if(row == 2) return TMath.max(m20, m21, m22);
        return Double.NaN;
    }


    public double getMinOfRow(int row) {
        if(row == 0) return TMath.min(m00, m01, m02);
        if(row == 1) return TMath.min(m10, m11, m12);
        if(row == 2) return TMath.min(m20, m21, m22);
        return Double.NaN;
    }


    public double getSumOfRow(int row) {
        if(row == 0) return m00 + m01 + m02;
        if(row == 1) return m10 + m11 + m12;
        if(row == 2) return m20 + m21 + m22;
        return Double.NaN;
    }


    public double getMaxOfCol(int col) {
        if(col == 0) return TMath.max(m00, m10, m20);
        if(col == 1) return TMath.max(m01, m11, m21);
        if(col == 2) return TMath.max(m02, m12, m22);
        return Double.NaN;
    }


    public double getMinOfCol(int col) {
        if(col == 0) return TMath.min(m00, m10, m20);
        if(col == 1) return TMath.min(m01, m11, m21);
        if(col == 2) return TMath.min(m02, m12, m22);
        return Double.NaN;
    }


    public double getSumOfCol(int col) {
        if(col == 0) return m00 + m10 + m20;
        if(col == 1) return m01 + m11 + m21;
        if(col == 2) return m02 + m12 + m22;
        return Double.NaN;
    }


    /*///////////////////////////////////////////////////////////////////////*/
    /*///////////////////////  ARITHMETIC OPERATIONS  ///////////////////////*/
    /*///////////////////////////////////////////////////////////////////////*/


    public TMat3 add(double m00, double m01, double m02,
                     double m10, double m11, double m12,
                     double m20, double m21, double m22)
    {
        this.m00 += m00; this.m01 += m01; this.m02 += m02;
        this.m10 += m10; this.m11 += m11; this.m12 += m12;
        this.m20 += m20; this.m21 += m21; this.m22 += m22;
        return this;
    }


    public TMat3 add(TMat3 mat){
        return add(
                mat.m00, mat.m01, mat.m02,
                mat.m10, mat.m11, mat.m12,
                mat.m20, mat.m21, mat.m22
        );
    }

    // ----------------------- //

    public TMat3 sub(double m00, double m01, double m02,
                     double m10, double m11, double m12,
                     double m20, double m21, double m22)
    {
        this.m00 -= m00; this.m01 -= m01; this.m02 -= m02;
        this.m10 -= m10; this.m11 -= m11; this.m12 -= m12;
        this.m20 -= m20; this.m21 -= m21; this.m22 -= m22;
        return this;
    }


    public TMat3 sub(TMat3 mat){
        return sub(
                mat.m00, mat.m01, mat.m02,
                mat.m10, mat.m11, mat.m12,
                mat.m20, mat.m21, mat.m22
        );
    }

    // ----------------------- //

    public TMat3 scale(double scale){
        this.m00 *= scale; this.m01 *= scale; this.m02 *= scale;
        this.m10 *= scale; this.m11 *= scale; this.m12 *= scale;
        this.m20 *= scale; this.m21 *= scale; this.m22 *= scale;
        return this;
    }

    // ----------------------- //

    /**
     * Does thisMatrix = thisMatrix * parameterMatrix. <br>
     * Make sure to use {@link #copy()} method to keep the original matrix.
     * @return this matrix for method chaining
     */
    public TMat3 multiply(double m00, double m01, double m02,
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

        set(
            newM00, newM01, newM02,
            newM10, newM11, newM12,
            newM20, newM21, newM22
        );

        return this;
    }


    /**
     * Does thisMatrix = thisMatrix * parameterMatrix. <br>
     * Make sure to use {@link #copy()} method to keep the original matrix.
     * @return this matrix for method chaining
     */
    public TMat3 multiply(TMat3 mat) {
        return multiply(
                mat.m00, mat.m01, mat.m02,
                mat.m10, mat.m11, mat.m12,
                mat.m20, mat.m21, mat.m22
        );
    }

    // ----------------------- //

    public TMat3 divide(double m00, double m01, double m02,
                        double m10, double m11, double m12,
                        double m20, double m21, double m22)
    {
        this.m00 /= m00; this.m01 /= m01; this.m02 /= m02;
        this.m10 /= m10; this.m11 /= m11; this.m12 /= m12;
        this.m20 /= m20; this.m21 /= m21; this.m22 /= m22;
        return this;
    }


    /**
     * Performs element-wise division.
     * @param mat any matrix
     * @return this matrix for method chaining
     */
    public TMat3 divide(TMat3 mat){
        return divide(
                mat.m00, mat.m01, mat.m02,
                mat.m10, mat.m11, mat.m12,
                mat.m20, mat.m21, mat.m22
        );
    }

    // ----------------------- //

    /**
     * Does thisMatrix = inverse(thisMatrix). <br>
     * Make sure to use {@link #copy()} method to keep the original matrix.
     * @return this matrix for method chaining
     */
    public TMat3 invert(){
        double det = this.determinant();
        if(TMath.equalsd(det, 0d))
            return null;

        this.set(
                (this.m11 * this.m22 - this.m12 * this.m21) / det,
                (this.m02 * this.m21 - this.m01 * this.m22) / det,
                (this.m01 * this.m12 - this.m02 * this.m11) / det,
                (this.m12 * this.m20 - this.m10 * this.m22) / det,
                (this.m00 * this.m22 - this.m02 * this.m20) / det,
                (this.m02 * this.m10 - this.m00 * this.m12) / det,
                (this.m10 * this.m21 - this.m11 * this.m20) / det,
                (this.m01 * this.m20 - this.m00 * this.m21) / det,
                (this.m00 * this.m11 - this.m01 * this.m10) / det
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
    public TMat3 applyFunctionElementWise(Function<Double, Double> function) {
        set(
            function.apply(m00), function.apply(m01), function.apply(m02),
            function.apply(m10), function.apply(m11), function.apply(m12),
            function.apply(m20), function.apply(m21), function.apply(m22)
        );
        return this;
    }

    public TMat3 sin() { return applyFunctionElementWise(TMath::sin); }
    public TMat3 cos() { return applyFunctionElementWise(TMath::cos); }
    public TMat3 tan() { return applyFunctionElementWise(TMath::tan); }
    public TMat3 arcsin() { return applyFunctionElementWise(TMath::arcsin); }
    public TMat3 arccos() { return applyFunctionElementWise(TMath::arccos); }
    public TMat3 arctan() { return applyFunctionElementWise(TMath::arctan); }
    public TMat3 floor() { return applyFunctionElementWise(TMath::floor); }
    public TMat3 ceil() { return applyFunctionElementWise(TMath::ceil); }
    public TMat3 sqrt() { return applyFunctionElementWise(TMath::sqrt); }
    public TMat3 square() { return applyFunctionElementWise(TMath::square); }
    public TMat3 sinh() { return applyFunctionElementWise(TMath::sinh); }
    public TMat3 cosh() { return applyFunctionElementWise(TMath::cosh); }
    public TMat3 tanh() { return applyFunctionElementWise(TMath::tanh); }


    /*//////////////////////////////////////////////////////////////////////*/
    /*///////////////////////  GEOMETRIC OPERATIONS  ///////////////////////*/
    /*//////////////////////////////////////////////////////////////////////*/


    /**
     * Flips this matrix horizontally. <br>
     * Example: <br>
     * [1 2 3]  =>  [3 2 1]  <br>
     * [4 5 6]  =>  [6 5 4]  <br>
     * [7 8 9]  =>  [9 8 7]  <br>
     * @return this matrix for method chaining
     */
    public TMat3 flipHorizontally(){
        double temp;
        temp = this.m00;
        this.m00 = this.m02;
        this.m02 = temp;
        temp = this.m10;
        this.m10 = this.m12;
        this.m12 = temp;
        temp = this.m20;
        this.m20 = this.m22;
        this.m22 = temp;
        return this;
    }


    /**
     * Flips this matrix vertically. <br>
     * Example: <br>
     * [1 2 3]  =>  [7 8 9]  <br>
     * [4 5 6]  =>  [4 5 6]  <br>
     * [7 8 9]  =>  [1 2 3]  <br>
     * @return this matrix for method chaining
     */
    public TMat3 flipVertically(){
        double temp;
        temp = this.m00;
        this.m00 = this.m20;
        this.m20 = temp;
        temp = this.m01;
        this.m01 = this.m21;
        this.m21 = temp;
        temp = this.m02;
        this.m02 = this.m22;
        this.m22 = temp;
        return this;
    }


    /**
     * Rotates this matrix 90 degrees clockwise. <br>
     * Example: <br>
     * [1 2 3]  =>  [7 4 1]  <br>
     * [4 5 6]  =>  [8 5 2]  <br>
     * [7 8 9]  =>  [9 6 3]  <br>
     * @return this matrix for method chaining
     */
    public TMat3 rotate90DegClockwise(){
        double temp;
        temp = this.m20;
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
     * Rotates this matrix 90 degrees anti-clockwise. <br>
     * Example: <br>
     * [1 2 3]  =>  [3 6 9]  <br>
     * [4 5 6]  =>  [2 5 8]  <br>
     * [7 8 9]  =>  [1 4 7]  <br>
     * @return this matrix for method chaining
     */
    public TMat3 rotate90DegAntiClockwise(){
        double temp;
        temp = this.m20;
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
     * Rotates this matrix 180 degrees clockwise / anti-clockwise. <br>
     * Example: <br>
     * [1 2 3]  =>  [9 8 7]  <br>
     * [4 5 6]  =>  [6 5 4]  <br>
     * [7 8 9]  =>  [3 2 1]  <br>
     * @return this matrix for method chaining
     */
    public TMat3 rotate180Deg(){
        double temp;
        temp = this.m10;
        this.m10 = this.m12;
        this.m12 = temp;
        temp = this.m22;
        this.m22 = this.m00;
        this.m00 = temp;
        temp = this.m20;
        this.m20 = this.m02;
        this.m02 = temp;
        temp = this.m21;
        this.m21 = this.m01;
        this.m01 = temp;
        return this;
    }


    /**
     * Calculates the transpose of this matrix and assigns it to this matrix. <br>
     * @return this matrix for method chaining
     */
    public TMat3 transpose(){
        double temp;
        temp = this.m01;
        this.m01 = this.m10;
        this.m10 = temp;
        temp = this.m02;
        this.m02 = this.m20;
        this.m20 = temp;
        temp = this.m12;
        this.m12 = this.m21;
        this.m21 = temp;
        return this;
    }


    /*////////////////////////////////////////////////////////////////////*/
    /*//////////////////////  SPECIAL VALUE FUNCS  ///////////////////////*/
    /*////////////////////////////////////////////////////////////////////*/

    public double trace(){
        return m00 + m11 + m22;
    }

    // ------------- //

    public static double determinant(double m00, double m01, double m02,
                                     double m10, double m11, double m12,
                                     double m20, double m21, double m22)
    {
        return (m00 * m11 * m22) + (m10 * m21 * m02) + (m20 * m01 * m12)
                - (m20 * m11 * m02) - (m12 * m21 * m00) - (m10 * m01 * m22);
    }


    public double determinant() {
        return determinant(
                m00, m01, m02,
                m10, m11, m12,
                m20, m21, m22
        );
    }


    /*///////////////////////////////////////////////////////////////*/
    /*///////////////////////  BOOLEAN FUNCS  ///////////////////////*/
    /*///////////////////////////////////////////////////////////////*/


    public boolean isSymmetrical(){
        return TMath.equalsd(this.m01, this.m10) &&
                TMath.equalsd(this.m02, this.m20) &&
                TMath.equalsd(this.m21, this.m12);
    }


    public boolean isSingular(){
        return TMath.equalsd(this.determinant(), 0d);
    }


    public boolean isIdentity(){
        return TMath.equalsd(this.m00, 1d) && TMath.equalsd(this.m01, 0d) && TMath.equalsd(this.m02, 0d) &&
                TMath.equalsd(this.m10, 0d) && TMath.equalsd(this.m11, 1d) && TMath.equalsd(this.m12, 0d) &&
                TMath.equalsd(this.m20, 0d) && TMath.equalsd(this.m21, 0d) && TMath.equalsd(this.m22, 1d);
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*///////////////////////  OBJECT METHODS  ////////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/


    @Override
    public String toString() {
        return String.format("[%.5f, %.5f, %.5f]\n[%.5f, %.5f, %.5f]\n[%.5f, %.5f, %.5f]\n",
                m00, m01, m02, m10, m11, m12, m20, m21, m22);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        TMat3 other = (TMat3) o;
        return TMath.equalsd(other.m00, m00) && TMath.equalsd(other.m01, m01) && TMath.equalsd(other.m02, m02) &&
            TMath.equalsd(other.m10, m10) && TMath.equalsd(other.m11, m11) && TMath.equalsd(other.m12, m12) &&
            TMath.equalsd(other.m20, m20) && TMath.equalsd(other.m21, m21) && TMath.equalsd(other.m22, m22);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m00, m01, m02, m10, m11, m12, m20, m21, m22);
    }


}
