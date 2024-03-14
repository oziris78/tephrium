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


import com.twistral.tephrium.core.TephriumException;
import com.twistral.tephrium.core.functions.TMath;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.IntStream;


/**
 * A mutable and variable sized (NxN) double matrix class. <br>
 * This class is slower than {@link TMat2}, {@link TMat3}, {@link TMat4} classes
 * so if your matrix size is less than 5, use those classes instead. <br>
 * All methods either return a numeric value or this matrix for method chaining purposes.
 */
public class TMatN {

    private int N;
    private double[][] mat;


    //////////////////////////////////////////////////////////////////////////
    /////////////////////////////  CONSTRUCTORS  /////////////////////////////
    //////////////////////////////////////////////////////////////////////////


    /**
     * Creates I<sub>N</sub> which is the NxN identity matrix.
     * @param N matrix's size
     */
    public TMatN(int N){
        if(N <= 0) throw new TephriumException("Invalid matrix size: N=%d", N);

        this.N = N;
        this.mat = new double[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] = (i == j) ? 1d : 0d;
            }
        }
    }


    /**
     * Creates a TMatN instance using the given matrix. No copying happens since
     * the matrix only references the given double[][].
     * @param matrixToReference any square two-dimensional double array
     */
    public TMatN(double[][] matrixToReference){
        if(matrixToReference.length <= 0) 
            throw new TephriumException("Invalid matrix size: N=%d", matrixToReference.length);

        for (int i = 0; i < matrixToReference.length; i++) 
            if(matrixToReference.length != matrixToReference[i].length) 
                throw new TephriumException("This matrix is not a square matrix.");
        
        this.N = matrixToReference.length;
        this.mat = matrixToReference;
    }


    /**
     * Creates an NxN matrix where all the cells are filled with fillValue.
     * @param N matrix's size
     * @param fillValue any double value
     */
    public TMatN(int N, double fillValue) {
        if(N <= 0) throw new TephriumException("Invalid matrix size: N=%d", N);

        this.N = N;
        this.mat = new double[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] = fillValue;
            }
        }
    }


    /**
     * This method does not create a new double array unlike {@link TMat2#getAsArray()},
     * {@link TMat3#getAsArray()}, {@link TMat4#getAsArray()} methods. <br>
     * @return the matrix referenced or stored by this matrix
     */
    public double[][] getAsArray(){
        return this.mat;
    }


    public TMatN copy(){
        double[][] matCopy = new double[this.N][this.N];
        for (int i = 0; i < this.N; i++) {
            for (int j = 0; j < this.N; j++) {
                matCopy[i][j] = this.mat[i][j];
            }
        }

        return new TMatN(matCopy);
    }


    /**
     * Creates a new double[][] with the contents of the given matrix and inits this TMatN instance.
     * @param matrix any matrix
     */
    public TMatN(TMat2 matrix) {
        this(matrix.getAsArray());
    }


    /**
     * Creates a new double[][] with the contents of the given matrix and inits this TMatN instance.
     * @param matrix any matrix
     */
    public TMatN(TMat3 matrix) {
        this(matrix.getAsArray());
    }


    /**
     * Creates a new double[][] with the contents of the given matrix and inits this TMatN instance.
     * @param matrix any matrix
     */
    public TMatN(TMat4 matrix) {
        this(matrix.getAsArray());
    }


    ///////////////////////////////////////////////////////////////////////////////
    /////////////////////////////  GETTERS & SETTERS  /////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////


    public int getN(){
        return this.N;
    }


    /**
     * Sets matrix[row][col] to value if (row, col) is a valid cell. <br>
     * <b>DOES NOT THROW INDEX OUT OF BOUNDS ERROR, simply does nothing if it happens</b>
     * @param row any integer
     * @param col any integer
     * @param value any double
     * @return this matrix for method chaining
     */
    public TMatN setCell(int row, int col, double value){
        if(!(row < 0 || row >= N || col < 0 || col >= N)) // If valid;
            this.mat[row][col] = value;

        return this;
    }


    /**
     * @param row any integer
     * @param col any integer
     * @return matrix[row][col] if (row, col) is a valid cell, otherwise returns {@link Double#NaN}
     */
    public double getCell(int row, int col){
        if(row < 0 || row >= N || col < 0 || col >= N) {
            return Double.NaN;
        }

        return this.mat[row][col];
    }


    public double getMaxOfRow(int row) {
        if(row < 0 || row >= N) return Double.NaN;
        return TMath.max(this.mat[row]);
    }


    public double getMinOfRow(int row) {
        if(row < 0 || row >= N) return Double.NaN;
        return TMath.min(this.mat[row]);
    }


    public double getSumOfRow(int row) {
        if(row < 0 || row >= N) return Double.NaN;
        return TMath.sum(this.mat[row]);
    }


    public double getMaxOfCol(int col) {
        if(col < 0 || col >= N) return Double.NaN;
        double result = mat[0][col];
        for (int i = 1; i < this.N; i++) {
            double value = mat[i][col];
            if(value > result) result = value;
        }
        return result;
    }


    public double getMinOfCol(int col) {
        if(col < 0 || col >= N) return Double.NaN;
        double result = mat[0][col];
        for (int i = 1; i < this.N; i++) {
            double value = mat[i][col];
            if(value < result) result = value;
        }
        return result;
    }


    public double getSumOfCol(int col) {
        if(col < 0 || col >= N) return Double.NaN;
        double result = mat[0][col];
        for (int i = 1; i < this.N; i++) {
            result += mat[i][col];
        }
        return result;
    }


    ///////////////////////////////////////////////////////////////////////////
    /////////////////////////  ARITHMETIC OPERATIONS  /////////////////////////
    ///////////////////////////////////////////////////////////////////////////


    public TMatN add(double[][] other) {
        if(!parametersAreFine(this.N, other))
            return this;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] += other[i][j];
            }
        }

        return this;
    }


    public TMatN add(TMatN other){
        return this.add(other.getAsArray());
    }

    // ----------------------- //

    public TMatN sub(double[][] other){
        if(!parametersAreFine(this.N, other))
            return this;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] -= other[i][j];
            }
        }

        return this;
    }


    public TMatN sub(TMatN other){
        return this.sub(other.getAsArray());
    }

    // ----------------------- //

    public TMatN divide(double[][] other){
        if(!parametersAreFine(this.N, other))
            return this;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] /= other[i][j];
            }
        }

        return this;
    }


    /**
     * Performs element-wise division.
     * @param mat any matrix
     * @return this matrix for method chaining
     */
    public TMatN divide(TMatN mat){
        return this.divide(mat.getAsArray());
    }

    // ----------------------- //

    public TMatN scale(double scale){
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] *= scale;
            }
        }

        return this;
    }

    // ----------------------- //


    public TMatN multiply(double[][] other) {
        if(!parametersAreFine(this.N, other))
            return this;

        double[][] newMatrix = new double[N][N];
        for(int i = 0; i < N; i++) {
            for(int k = 0; k < N; k++) {
                double result = 0d;
                for(int j = 0; j < N; j++)
                    result += this.mat[i][j] * other[j][k];
                newMatrix[i][k] = result;
            }
        }

        this.mat = newMatrix;
        return this;
    }


    public TMatN multiply(TMatN other) {
        return this.multiply(other.getAsArray());
    }

    // ----------------------- //

    /**
     * Does thisMatrix = inverse(thisMatrix). <br>
     * Make sure to use {@link #copy()} method to keep the original matrix.
     * @return this matrix for method chaining
     */
    public TMatN invert() {
        double det = this.determinant();
        if (TMath.equalsd(det, 0d)) {
            return null; // Not invertable
        }

        TMatN matInverted = new TMatN(N);
        int[] index = IntStream.range(0, N).toArray(); // [0, 1, ..., N-1]
        double[] maxRowValues = IntStream.range(0, N).mapToDouble(i -> getMaxOfRow(i)).toArray();

        for (int col = 0; col < N - 1; col++) {
            int pivot = 0;
            double maxRatio = 0;

            for (int row = col; row < N; row++) {
                double ratio = TMath.abs(mat[index[row]][col]) / maxRowValues[index[row]];
                if (ratio > maxRatio) {
                    maxRatio = ratio;
                    pivot = row;
                }
            }

            // Swap rows
            int temp = index[col];
            index[col] = index[pivot];
            index[pivot] = temp;

            // Gaussian elimination
            for (int row = col + 1; row < N; row++) {
                double factor = mat[index[row]][col] / mat[index[col]][col];
                mat[index[row]][col] = factor;

                for (int l = col + 1; l < N; l++)
                    mat[index[row]][l] -= factor * mat[index[col]][l];
            }
        }

        // Back substitution
        for (int i = 0; i < N - 1; i++)
            for (int j = i + 1; j < N; j++)
                for (int k = 0; k < N; k++)
                    matInverted.mat[index[j]][k] -= mat[index[j]][i] * matInverted.mat[index[i]][k];

        double[][] resultMatrix = new double[N][N];
        for (int i = 0; i < N; i++) {
            resultMatrix[N - 1][i] = matInverted.mat[index[N - 1]][i] / mat[index[N - 1]][N - 1];
            for (int j = N - 2; j >= 0; j--) {
                resultMatrix[j][i] = matInverted.mat[index[j]][i];
                for (int k = j + 1; k < N; k++)
                    resultMatrix[j][i] -= mat[index[j]][k] * resultMatrix[k][i];
                resultMatrix[j][i] /= mat[index[j]][j];
            }
        }

        this.mat = resultMatrix;
        return this;
    }


    // ----------------------- //

    /**
     * Applies the given function to all elements. <br>
     * Example usage: myMatrix.applyFunctionElementWise(x -> x*2 + 2);
     * @param function any function that takes in a double and returns one
     * @return this matrix for method chaining
     */
    public TMatN applyFunctionElementWise(Function<Double, Double> function) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] = function.apply(this.mat[i][j]);
            }
        }

        return this;
    }

    public TMatN sin() { return applyFunctionElementWise(TMath::sin); }
    public TMatN cos() { return applyFunctionElementWise(TMath::cos); }
    public TMatN tan() { return applyFunctionElementWise(TMath::tan); }
    public TMatN arcsin() { return applyFunctionElementWise(TMath::arcsin); }
    public TMatN arccos() { return applyFunctionElementWise(TMath::arccos); }
    public TMatN arctan() { return applyFunctionElementWise(TMath::arctan); }
    public TMatN floor() { return applyFunctionElementWise(TMath::floor); }
    public TMatN ceil() { return applyFunctionElementWise(TMath::ceil); }
    public TMatN sqrt() { return applyFunctionElementWise(TMath::sqrt); }
    public TMatN square() { return applyFunctionElementWise(TMath::square); }
    public TMatN sinh() { return applyFunctionElementWise(TMath::sinh); }
    public TMatN cosh() { return applyFunctionElementWise(TMath::cosh); }
    public TMatN tanh() { return applyFunctionElementWise(TMath::tanh); }


    //////////////////////////////////////////////////////////////////////////
    /////////////////////////  GEOMETRIC OPERATIONS  /////////////////////////
    //////////////////////////////////////////////////////////////////////////

    public TMatN flipHorizontally(){
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N / 2; j++) {
                double temp = this.getCell(i,j);
                this.mat[i][j] = this.mat[i][N - (j + 1)];
                this.mat[i][N - (j + 1)] = temp;
            }
        }

        return this;
    }


    public TMatN flipVertically(){
        for (int i = 0; i < N / 2; i++) {
            for (int j = 0; j < N; j++) {
                double temp = this.mat[i][j];
                this.mat[i][j] = this.mat[N - (i + 1)][j];
                this.mat[N - (i + 1)][j] = temp;
            }
        }

        return this;
    }


    public TMatN rotate90DegClockwise() {
        for (int i = 0; i < N / 2; i++) {
            for (int j = i; j < N - i - 1; j++) {
                double temp = this.mat[i][j];
                this.mat[i][j] = this.mat[N - 1 - j][i];
                this.mat[N - 1 - j][i] = this.mat[N - 1 - i][N - 1 - j];
                this.mat[N - 1 - i][N - 1 - j] = this.mat[j][N - 1 - i];
                this.mat[j][N - 1 - i] = temp;
            }
        }
        return this;
    }


    public TMatN rotate90DegAntiClockwise() {
        for (int i = 0; i < N / 2; i++) {
            for (int j = i; j < N - i - 1; j++) {
                double temp = this.mat[i][j];
                this.mat[i][j] = this.mat[j][N - 1 - i];
                this.mat[j][N - 1 - i] = this.mat[N - 1 - i][N - 1 - j];
                this.mat[N - 1 - i][N - 1 - j] = this.mat[N - 1 - j][i];
                this.mat[N - 1 - j][i] = temp;
            }
        }
        return this;
    }


    public TMatN rotate180Deg(){
        final int N2 = N * N;
        for (int i = 0; i < N2 / 2; i++) {
            final int i1 = i / N;
            final int j1 = i % N;
            final int i2 = (N2 - 1 - i) / N;
            final int j2 = (N2 - 1 - i) % N;
            double temp = this.mat[i1][j1];
            this.mat[i1][j1] = this.mat[i2][j2];
            this.mat[i2][j2] = temp;
        }
        return this;
    }


    public TMatN transpose() {
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                double temp = this.mat[i][j];
                this.mat[i][j] = this.mat[j][i];
                this.mat[j][i] = temp;
            }
        }
        return this;
    }


    /////////////////////////////////////////////////////////////////////////
    /////////////////////////  SPECIAL VALUE FUNCS  /////////////////////////
    /////////////////////////////////////////////////////////////////////////

    public double trace() {
        double sum = 0d;
        for (int i = 0; i < N; i++)
            sum += this.mat[i][i];

        return sum;
    }

    // ----------------------- //

    // Gauss elimination method, O(N^3)
    private static double determinantInner(double[][] matrix, int size) {
        double[][] m = new double[size][size];
        for (int i = 0; i < size; i++) System.arraycopy(matrix[i], 0, m[i], 0, size);

        double determinant = 1.0;
        for (int i = 0; i < size; i++) {
            int k = i;
            for (int j = i + 1; j < size; j++) {
                if (TMath.abs(m[j][i]) > TMath.abs(m[k][i]))
                    k = j;
            }

            if (m[k][i] == 0) return 0;

            double[] temp = m[i];
            m[i] = m[k];
            m[k] = temp;

            if (i != k) determinant = -determinant;

            determinant *= m[i][i];

            for (int j = i + 1; j < size; j++)
                m[i][j] /= m[i][i];

            for (int j = 0; j < size; j++) {
                if (j != i) {
                    for (int l = i + 1; l < size; l++)
                        m[j][l] -= m[j][i] * m[i][l];
                }
            }
        }
        return determinant;
    }


    public static double determinant(double[][] mat) {
        if(mat == null) return Double.NaN;
        int len = mat.length;
        if(len <= 0) return Double.NaN;
        for (int i = 0; i < len; i++)
            if(len != mat[i].length) return Double.NaN;

        return determinantInner(mat, len);
    }


    public double determinant() {
        return determinantInner(this.mat, N);
    }


    ///////////////////////////////////////////////////////////////////
    /////////////////////////  BOOLEAN FUNCS  /////////////////////////
    ///////////////////////////////////////////////////////////////////


    public boolean isSymmetrical(){
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (!TMath.equalsd(mat[i][j], mat[j][i]))
                    return false;
        return true;
    }


    public boolean isSingular(){
        return TMath.equalsd(this.determinant(), 0d);
    }


    public boolean isIdentity(){
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if(i == j) {
                    if(!TMath.equalsd(this.mat[i][j], 1d))
                        return false;
                }
                else {
                    if(!TMath.equalsd(this.mat[i][j], 0d))
                        return false;
                }
            }
        }
        return true;
    }


    //////////////////////////////////////////////////////////////////////
    /////////////////////////  HELPER FUNCTIONS  /////////////////////////
    //////////////////////////////////////////////////////////////////////


    private static boolean parametersAreFine(int size1, double[][] mat2) {
        if(mat2 == null) return false; // null
        if(mat2.length <= 0) return false; // invalid size
        if(size1 != mat2.length) return false; // size mismatch

        for (int i = 0; i < mat2.length; i++) // mat2 is not a square matrix
            if(mat2.length != mat2[i].length) return false;

        return true; // everything is fine
    }


    ////////////////////////////////////////////////////////////////////
    /////////////////////////  OBJECT METHODS  /////////////////////////
    ////////////////////////////////////////////////////////////////////


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++)
            sb.append(Arrays.toString(mat[i]) + "\n");
        return sb.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        TMatN other = (TMatN) o;

        if (N != other.N)
            return false;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if(!TMath.equalsd(other.mat[i][j], this.mat[i][j]))
                    return false;
            }
        }

        return true;
    }


    @Override
    public int hashCode() {
        int result = Objects.hash(N);
        result = (31 * result) + Arrays.hashCode(mat);
        return result;
    }


}
