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
 * A fast and mutable 2x2 double matrix class. <br>
 * All methods either return a numeric value or this matrix for method chaining purposes. <br>
 * Also see {@link TMat3}, {@link TMat4}, {@link TMatN}
 */
public class TMat2 {

    private double m00, m01,   /*    [ m00, m01 ]    */
                   m10, m11;   /*    [ m10, m11 ]    */

    //////////////////////////////////////////////////////////////////////////
    /////////////////////////////  CONSTRUCTORS  /////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    public TMat2(double m00, double m01, double m10, double m11) {
        set(m00, m01, m10, m11);
    }


    /**  Returns I<sub>2</sub> which is the 2x2 identity matrix.  */
    public TMat2() {
        set(1d, 0d, 0d, 1d);
    }


    public TMat2(double fillValue) {
        set(fillValue, fillValue, fillValue, fillValue);
    }


    public double[][] getAsArray(){
        return new double[][]{ { m00, m01 }, { m10, m11 } };
    }


    public TMat2 copy(){
        return new TMat2(m00, m01, m10, m11);
    }


    ///////////////////////////////////////////////////////////////////////////////
    /////////////////////////////  GETTERS & SETTERS  /////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////


    public TMat2 set(double m00, double m01, double m10, double m11) {
        this.m00 = m00; this.m01 = m01; this.m10 = m10; this.m11 = m11;
        return this;
    }


    public TMat2 setCell(int row, int col, double value){
        if(row == 0 && col == 0) m00 = value;
        else if(row == 0 && col == 1) m01 = value;
        else if(row == 1 && col == 0) m10 = value;
        else if(row == 1 && col == 1) m11 = value;
        return this;
    }


    public double getCell(int row, int col){
        if(row == 0 && col == 0) return this.m00;
        if(row == 0 && col == 1) return this.m01;
        if(row == 1 && col == 0) return this.m10;
        if(row == 1 && col == 1) return this.m11;
        return Double.NaN;
    }


    public double getMaxOfRow(int row) {
        if(row == 0) return TMath.max(m00, m01);
        if(row == 1) return TMath.max(m10, m11);
        return Double.NaN;
    }


    public double getMinOfRow(int row) {
        if(row == 0) return TMath.min(m00, m01);
        if(row == 1) return TMath.min(m10, m11);
        return Double.NaN;
    }


    public double getSumOfRow(int row) {
        if(row == 0) return m00 + m01;
        if(row == 1) return m10 + m11;
        return Double.NaN;
    }


    public double getMaxOfCol(int col) {
        if(col == 0) return TMath.max(m00, m10);
        if(col == 1) return TMath.max(m01, m11);
        return Double.NaN;
    }


    public double getMinOfCol(int col) {
        if(col == 0) return TMath.min(m00, m10);
        if(col == 1) return TMath.min(m01, m11);
        return Double.NaN;
    }


    public double getSumOfCol(int col) {
        if(col == 0) return m00 + m10;
        if(col == 1) return m01 + m11;
        return Double.NaN;
    }


    ///////////////////////////////////////////////////////////////////////////
    /////////////////////////  ARITHMETIC OPERATIONS  /////////////////////////
    ///////////////////////////////////////////////////////////////////////////


    public TMat2 add(double m00, double m01, double m10, double m11) {
        this.m00 += m00; this.m01 += m01; this.m10 += m10; this.m11 += m11;
        return this;
    }


    public TMat2 add(TMat2 mat){
        return add(mat.m00, mat.m01, mat.m10, mat.m11);
    }

    // ----------------------- //

    public TMat2 sub(double m00, double m01, double m10, double m11) {
        this.m00 -= m00; this.m01 -= m01; this.m10 -= m10; this.m11 -= m11;
        return this;
    }


    public TMat2 sub(TMat2 mat){
        return sub(mat.m00, mat.m01, mat.m10, mat.m11);
    }

    // ----------------------- //

    public TMat2 scale(double scale){
        this.m00 *= scale; this.m01 *= scale; this.m10 *= scale; this.m11 *= scale;
        return this;
    }

    // ----------------------- //

    /**
     * Does thisMatrix = thisMatrix * parameterMatrix. <br>
     * Make sure to use {@link #copy()} method to keep the original matrix.
     * @return this matrix for method chaining
     */
    public TMat2 multiply(double m00, double m01, double m10, double m11) {
        double newM00 = this.m00 * m00 + this.m01 * m10;
        double newM01 = this.m00 * m01 + this.m01 * m11;
        double newM10 = this.m10 * m00 + this.m11 * m10;
        double newM11 = this.m10 * m01 + this.m11 * m11;

        set(newM00, newM01, newM10, newM11);

        return this;
    }


    /**
     * Does thisMatrix = thisMatrix * parameterMatrix. <br>
     * Make sure to use {@link #copy()} method to keep the original matrix.
     * @return this matrix for method chaining
     */
    public TMat2 multiply(TMat2 mat) {
        return multiply(mat.m00, mat.m01, mat.m10, mat.m11);
    }

    // ----------------------- //

    public TMat2 divide(double m00, double m01, double m10, double m11) {
        this.m00 /= m00; this.m01 /= m01; this.m10 /= m10; this.m11 /= m11;
        return this;
    }


    /**
     * Performs element-wise division.
     * @param mat any matrix
     * @return this matrix for method chaining
     */
    public TMat2 divide(TMat2 mat){
        return divide(mat.m00, mat.m01, mat.m10, mat.m11);
    }

    // ----------------------- //

    /**
     * Does thisMatrix = inverse(thisMatrix). <br>
     * Make sure to use {@link #copy()} method to keep the original matrix.
     * @return this matrix for method chaining
     */
    public TMat2 invert(){
        double det = this.determinant();
        if(TMath.equalsd(det, 0d))
            return null;

        double temp = this.m00;
        this.m00 = this.m11 / det;
        this.m11 = temp / det;
        this.m01 = -this.m01 / det;
        this.m10 = -this.m10 / det;

        return this;
    }

    // ----------------------- //

    /**
     * Applies the given function to all elements. <br>
     * Example usage: myMatrix.applyFunctionElementWise(x -> x*2 + 2);
     * @param function any function that takes in a double and returns one
     * @return this matrix for method chaining
     */
    public TMat2 applyFunctionElementWise(Function<Double, Double> function) {
        set(function.apply(m00), function.apply(m01), function.apply(m10), function.apply(m11));
        return this;
    }

    public TMat2 sin() { return applyFunctionElementWise(TMath::sin); }
    public TMat2 cos() { return applyFunctionElementWise(TMath::cos); }
    public TMat2 tan() { return applyFunctionElementWise(TMath::tan); }
    public TMat2 arcsin() { return applyFunctionElementWise(TMath::arcsin); }
    public TMat2 arccos() { return applyFunctionElementWise(TMath::arccos); }
    public TMat2 arctan() { return applyFunctionElementWise(TMath::arctan); }
    public TMat2 floor() { return applyFunctionElementWise(TMath::floor); }
    public TMat2 ceil() { return applyFunctionElementWise(TMath::ceil); }
    public TMat2 sqrt() { return applyFunctionElementWise(TMath::sqrt); }
    public TMat2 square() { return applyFunctionElementWise(TMath::square); }
    public TMat2 sinh() { return applyFunctionElementWise(TMath::sinh); }
    public TMat2 cosh() { return applyFunctionElementWise(TMath::cosh); }
    public TMat2 tanh() { return applyFunctionElementWise(TMath::tanh); }


    //////////////////////////////////////////////////////////////////////////
    /////////////////////////  GEOMETRIC OPERATIONS  /////////////////////////
    //////////////////////////////////////////////////////////////////////////


    /**
     * Flips this matrix horizontally. <br>
     * Example: <br>
     * [1 5]  =>  [5 1]  <br>
     * [7 8]  =>  [8 7]  <br>
     * @return this matrix for method chaining
     */
    public TMat2 flipHorizontally(){
        double temp;
        temp = this.m00;
        this.m00 = this.m01;
        this.m01 = temp;
        temp = this.m10;
        this.m10 = this.m11;
        this.m11 = temp;
        return this;
    }


    /**
     * Flips this matrix vertically. <br>
     * Example: <br>
     * [1 5]  =>  [7 8]  <br>
     * [7 8]  =>  [1 5]  <br>
     * @return this matrix for method chaining
     */
    public TMat2 flipVertically(){
        double temp;
        temp = this.m00;
        this.m00 = this.m10;
        this.m10 = temp;
        temp = this.m01;
        this.m01 = this.m11;
        this.m11 = temp;
        return this;
    }


    /**
     * Rotates this matrix 90 degrees clockwise. <br>
     * Example: <br>
     * [1 5]  =>  [7 1]  <br>
     * [7 8]  =>  [8 5]  <br>
     * @return this matrix for method chaining
     */
    public TMat2 rotate90DegClockwise(){
        double temp;
        temp = this.m10;
        this.m10 = this.m11;
        this.m11 = this.m01;
        this.m01 = this.m00;
        this.m00 = temp;
        return this;
    }


    /**
     * Rotates this matrix 90 degrees anti-clockwise. <br>
     * Example: <br>
     * [1 5]  =>  [5 8]  <br>
     * [7 8]  =>  [1 7]  <br>
     * @return this matrix for method chaining
     */
    public TMat2 rotate90DegAntiClockwise(){
        double temp;
        temp = this.m10;
        this.m10 = this.m00;
        this.m00 = this.m01;
        this.m01 = this.m11;
        this.m11 = temp;
        return this;
    }


    /**
     * Rotates this matrix 180 degrees clockwise / anti-clockwise. <br>
     * Example: <br>
     * [1 5]  =>  [8 7]  <br>
     * [7 8]  =>  [5 1]  <br>
     * @return this matrix for method chaining
     */
    public TMat2 rotate180Deg(){
        double temp = this.m01;
        this.m01 = this.m10;
        this.m10 = temp;
        temp = this.m00;
        this.m00 = this.m11;
        this.m11 = temp;
        return this;
    }


    /**
     * Calculates the transpose of this matrix and assigns it to this matrix. <br>
     * @return this matrix for method chaining
     */
    public TMat2 transpose(){
        double temp = this.m01;
        this.m01 = this.m10;
        this.m10 = temp;
        return this;
    }


    /////////////////////////////////////////////////////////////////////////
    /////////////////////////  SPECIAL VALUE FUNCS  /////////////////////////
    /////////////////////////////////////////////////////////////////////////

    public double trace(){
        return m00 + m11;
    }

    // ------------- //

    public static double determinant(double m00, double m01, double m10, double m11) {
        return m00 * m11 - m01 * m10;
    }


    public double determinant() {
        return determinant(m00, m01, m10, m11);
    }


    ///////////////////////////////////////////////////////////////////
        /////////////////////////  BOOLEAN FUNCS  /////////////////////////
    ///////////////////////////////////////////////////////////////////


    public boolean isSymmetrical() {
        return TMath.equalsd(this.m01, this.m10);
    }


    public boolean isSingular(){
        return TMath.equalsd(this.determinant(), 0d);
    }


    public boolean isIdentity(){
        return TMath.equalsd(this.m00, 1d) && TMath.equalsd(this.m01, 0d) &&
                TMath.equalsd(this.m10, 0d) && TMath.equalsd(this.m11, 1d);
    }


    ////////////////////////////////////////////////////////////////////
    /////////////////////////  OBJECT METHODS  /////////////////////////
    ////////////////////////////////////////////////////////////////////


    @Override
    public String toString() {
        return String.format("[%.5f, %.5f]\n[%.5f, %.5f]\n", m00, m01, m10, m11);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        TMat2 other = (TMat2) o;
        return TMath.equalsd(other.m00, m00) && TMath.equalsd(other.m01, m01) &&
                TMath.equalsd(other.m10, m10) && TMath.equalsd(other.m11, m11);
    }


    @Override
    public int hashCode() {
        return Objects.hash(m00, m01, m10, m11);
    }


}
