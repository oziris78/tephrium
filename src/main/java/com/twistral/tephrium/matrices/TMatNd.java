

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
import java.util.*;
import java.util.function.*;
import static com.twistral.tephrium.collections.TCollections.newArray;


/**
 * A mutable, variable sized (NxN), double valued, row major matrix class with method chaining support. <br>
 *
 * <b>NOTE: This class runs dynamic loops and performs heap allocations frequently so it is way slower
 * than other matrix classes. If your matrix size is less than 5, definitely use those classes instead.</b>
 */
public class TMatNd {

    private int N;
    private double[][] mat;

    // Actual constructors

    /**
     * Creates a matrix from the given 2D array.
     *
     * @param mat the 2D array that contains the matrix data
     * @param copy if true, the given 2D array will be copied, otherwise it will be referenced
     * @throws TephriumException if the 2D input array is null or not a square matrix
     */
    public TMatNd(double[][] mat, boolean copy) {
        Objects.requireNonNull(mat);
        checkLengthInput(mat.length);
        checkSquareMat(mat);

        this.N = mat.length;

        if (copy) {
            this.mat = new double[N][N];
            for (int i = 0; i < N; i++) {
                this.mat[i] = new double[N];
                System.arraycopy(mat[i], 0, this.mat[i], 0, N);
            }
        }
        else {
            this.mat = mat;
        }
    }

    /**
     * Creates a matrix that references the given 2D array.
     *
     * @param matReference the 2D array that contains the matrix data
     * @throws TephriumException if the 2D input array is null or not a square matrix
     */
    public TMatNd(double[][] matReference) {
        this(matReference, false);
    }

    /**
     * Creates a matrix where all the cells are filled with the given value.
     *
     * @param N size of the matrix
     * @param fillValue any double value to fill the cells
     */
    public TMatNd(int N, double fillValue) {
        checkLengthInput(N);
        this.N = N;
        this.mat = newArray(N, N, fillValue);
    }

    /**
     * Creates I<sub>N</sub> which is the NxN identity matrix.
     *
     * @param N size of the matrix
     */
    public TMatNd(int N) {
        checkLengthInput(N);
        this.N = N;

        this.mat = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] = (i == j) ? 1d : 0d;
            }
        }
    }

    // Copying constructors & functions

    public TMatNd(TMatNd other) {
        this(other.mat, true);
    }

    public TMatNd copy() {
        return new TMatNd(this.mat, true);
    }

    public TMatNd(TMat2d matrix) {
        this(matrix.toArray());
    }

    public TMatNd(TMat3d matrix) {
        this(matrix.toArray());
    }

    public TMatNd(TMat4d matrix) {
        this(matrix.toArray());
    }

    // Special static factory methods

    public static TMatNd zero(int N) {
        return new TMatNd(N, 0d);
    }

    /** Returns a diagonal matrix with the specified diagonal values */
    public static TMatNd diagonal(double[] diagonal) {
        Objects.requireNonNull(diagonal);
        checkLengthInput(diagonal.length);

        final int N = diagonal.length;
        double[][] arr = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                arr[i][j] = (i == j) ? diagonal[i] : 0d;
            }
        }

        return new TMatNd(arr);
    }

    /** Returns an NxN scaling matrix */
    public static TMatNd scaling(double[] scalars) {
        return diagonal(scalars);
    }

    /** Returns a uniform NxN scaling matrix */
    public static TMatNd scaling(int N, double scale) {
        checkLengthInput(N);

        double[][] arr = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                arr[i][j] = (i == j) ? scale : 0d;
            }
        }

        return new TMatNd(arr);
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*////////////////////  ARITHMETIC OPERATIONS  ////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    // Addition

    public TMatNd add(double[][] other) {
        checkForArithmeticOperation(other);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] += other[i][j];
            }
        }

        return this;
    }

    public TMatNd add(TMatNd other) {
        checkForArithmeticOperation(other);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] += other.mat[i][j];
            }
        }

        return this;
    }

    public TMatNd add(int row, int col, double x) {
        checkIndices(row, col);
        this.mat[row][col] += x;
        return this;
    }

    // Subtraction

    public TMatNd sub(double[][] other) {
        checkForArithmeticOperation(other);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] -= other[i][j];
            }
        }

        return this;
    }

    public TMatNd sub(TMatNd other) {
        checkForArithmeticOperation(other);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] -= other.mat[i][j];
            }
        }

        return this;
    }

    public TMatNd sub(int row, int col, double x) {
        checkIndices(row, col);
        this.mat[row][col] -= x;
        return this;
    }

    // (ELEMENT WISE) Scaling / Multiplication

    public TMatNd scale(double[][] other) {
        checkForArithmeticOperation(other);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] *= other[i][j];
            }
        }

        return this;
    }

    /**
     * Performs element-wise multiplication, NOT matrix multiplication.
     * For matrix multiplication use this method: {@link #mul(TMatNd)}
     *
     * @param other matrix to perform element-wise multiplication with
     * @return this matrix for method chaining
     */
    public TMatNd scale(TMatNd other) {
        checkForArithmeticOperation(other);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] *= other.mat[i][j];
            }
        }

        return this;
    }

    public TMatNd scale(double scale) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] *= scale;
            }
        }

        return this;
    }

    public TMatNd scale(int row, int col, double x) {
        checkIndices(row, col);
        this.mat[row][col] *= x;
        return this;
    }

    // (ELEMENT WISE) Division

    public TMatNd div(double[][] other) {
        checkForArithmeticOperation(other);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] /= other[i][j];
            }
        }

        return this;
    }

    public TMatNd div(TMatNd other) {
        checkForArithmeticOperation(other);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] /= other.mat[i][j];
            }
        }

        return this;
    }

    public TMatNd div(double scale) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] /= scale;
            }
        }

        return this;
    }

    public TMatNd div(int row, int col, double x) {
        checkIndices(row, col);
        this.mat[row][col] /= x;
        return this;
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*//////////////////  LINEAR ALGEBRA OPERATIONS  //////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    // Matrix multiplication

    /**
     * Performs in-place matrix multiplication on this matrix by the parameter matrix.
     * In other words it simply does {@code this = this * other}.
     *
     * @param other the other matrix
     * @return this matrix for method chaining
     */
    public TMatNd mul(double[][] other) {
        checkForArithmeticOperation(other);

        double[][] newMatrix = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int k = 0; k < N; k++) {
                double result = 0d;
                for (int j = 0; j < N; j++) {
                    result += this.mat[i][j] * other[j][k];
                }
                newMatrix[i][k] = result;
            }
        }

        // Synchronize this.mat with newMatrix
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] = newMatrix[i][j];
            }
        }

        return this;
    }

    /**
     * Performs in-place matrix multiplication on this matrix by the parameter matrix.
     * In other words it simply does {@code this = this * other}.
     *
     * @param other the other matrix
     * @return this matrix for method chaining
     */
    public TMatNd mul(TMatNd other) {
        checkForArithmeticOperation(other);

        double[][] newMatrix = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int k = 0; k < N; k++) {
                double result = 0d;
                for (int j = 0; j < N; j++) {
                    result += this.mat[i][j] * other.mat[j][k];
                }
                newMatrix[i][k] = result;
            }
        }

        // Synchronize this.mat with newMatrix
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] = newMatrix[i][j];
            }
        }

        return this;
    }

    // Transposition

    /**
     * Performs in-place matrix transposition on this matrix like so: {@code this = transpose(this)}.
     *
     * @return this matrix for method chaining
     */
    public TMatNd transpose() {
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                double temp = this.mat[i][j];
                this.mat[i][j] = this.mat[j][i];
                this.mat[j][i] = temp;
            }
        }

        return this;
    }

    // Inversion

    /**
     * Performs in-place matrix inversion on this matrix like so: {@code this = invert(this)}.
     *
     * @return this matrix for method chaining
     */
    public TMatNd invert() {
        int[] index = new int[N];
        for (int i = 0; i < N; i++) index[i] = i;

        double[] maxRowValues = new double[N];
        for (int i = 0; i < N; i++) maxRowValues[i] = getMaxOfRow(i);

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

                for (int l = col + 1; l < N; l++) {
                    mat[index[row]][l] -= factor * mat[index[col]][l];
                }
            }
        }

        // Back substitution
        TMatNd matInverted = new TMatNd(N); // I_N
        for (int i = 0; i < N - 1; i++) {
            for (int j = i + 1; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    matInverted.mat[index[j]][k] -= mat[index[j]][i] * matInverted.mat[index[i]][k];
                }
            }
        }

        double[][] resultMatrix = new double[N][N];
        for (int i = 0; i < N; i++) {
            resultMatrix[N - 1][i] = matInverted.mat[index[N - 1]][i] / mat[index[N - 1]][N - 1];
            for (int j = N - 2; j >= 0; j--) {
                resultMatrix[j][i] = matInverted.mat[index[j]][i];
                for (int k = j + 1; k < N; k++) {
                    resultMatrix[j][i] -= mat[index[j]][k] * resultMatrix[k][i];
                }
                resultMatrix[j][i] /= mat[index[j]][j];
            }
        }

        // Synchronize this.mat with resultMatrix
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] = resultMatrix[i][j];
            }
        }

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
    public TMatNd map(DoubleUnaryOperator function) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] = function.applyAsDouble(this.mat[i][j]);
            }
        }

        return this;
    }

    public TMatNd sin() { return map(TMath::sin); }
    public TMatNd cos() { return map(TMath::cos); }
    public TMatNd tan() { return map(TMath::tan); }
    public TMatNd sinh() { return map(TMath::sinh); }
    public TMatNd cosh() { return map(TMath::cosh); }
    public TMatNd tanh() { return map(TMath::tanh); }
    public TMatNd arcsin() { return map(TMath::arcsin); }
    public TMatNd arccos() { return map(TMath::arccos); }
    public TMatNd arctan() { return map(TMath::arctan); }

    public TMatNd abs() { return map(TMath::abs); }
    public TMatNd floor() { return map(TMath::floor); }
    public TMatNd ceil() { return map(TMath::ceil); }
    public TMatNd sqrt() { return map(TMath::sqrt); }
    public TMatNd cbrt() { return map(TMath::cbrt); }
    public TMatNd square() { return map(TMath::square); }
    public TMatNd cube() { return map(TMath::cube); }


    /*/////////////////////////////////////////////////////////////////*/
    /*//////////////////  GRID ELEMENT PERMUTATIONS  //////////////////*/
    /*/////////////////////////////////////////////////////////////////*/


    /**
     * Horizontally flips the elements of this matrix grid.
     * <b>Keep in mind that this is NOT a geometric space transformation!</b>
     *
     * @return this matrix for method chaining
     */
    public TMatNd permuteGridFlipHorizontally() {
        for (int i = 0, N_2 = N / 2; i < N; i++) {
            for (int j = 0; j < N_2; j++) {
                final int j2 = N - (j + 1);
                final double temp = this.mat[i][j];
                this.mat[i][j] = this.mat[i][j2];
                this.mat[i][j2] = temp;
            }
        }

        return this;
    }

    /**
     * Vertically flips the elements of this matrix grid.
     * <b>Keep in mind that this is NOT a geometric space transformation!</b>
     *
     * @return this matrix for method chaining
     */
    public TMatNd permuteGridFlipVertically() {
        for (int i = 0, N_2 = N / 2; i < N_2; i++) {
            for (int j = 0; j < N; j++) {
                final int i2 = N - (i + 1);
                final double temp = this.mat[i][j];
                this.mat[i][j] = this.mat[i2][j];
                this.mat[i2][j] = temp;
            }
        }

        return this;
    }

    /**
     * Rotates the elements of this matrix grid by 90 degrees clockwise.
     * <b>Keep in mind that this is NOT a geometric space transformation!</b>
     *
     * @return this matrix for method chaining
     */
    public TMatNd permuteGridRotate90Clockwise() {
        for (int i = 0, N_2 = N / 2; i < N_2; i++) {
            final int i2 = N - 1 - i;
            for (int j = i; j < i2; j++) {
                final int j2 = N - 1 - j;
                final double temp = this.mat[i][j];
                this.mat[i][j] = this.mat[j2][i];
                this.mat[j2][i] = this.mat[i2][j2];
                this.mat[i2][j2] = this.mat[j][i2];
                this.mat[j][i2] = temp;
            }
        }
        return this;
    }

    /**
     * Rotates the elements of this matrix grid by 90 degrees anti-clockwise.
     * <b>Keep in mind that this is NOT a geometric space transformation!</b>
     *
     * @return this matrix for method chaining
     */
    public TMatNd permuteGridRotate90AntiClockwise() {
        for (int i = 0, N_2 = N / 2; i < N_2; i++) {
            final int i2 = N - 1 - i;
            for (int j = i; j < i2; j++) {
                final int j2 = N - 1 - j;
                final double temp = this.mat[i][j];
                this.mat[i][j] = this.mat[j][i2];
                this.mat[j][i2] = this.mat[i2][j2];
                this.mat[i2][j2] = this.mat[j2][i];
                this.mat[j2][i] = temp;
            }
        }
        return this;
    }

    /**
     * Rotates the elements of this matrix grid by 180 degrees.
     * <b>Keep in mind that this is NOT a geometric space transformation!</b>
     *
     * @return this matrix for method chaining
     */
    public TMatNd permuteGridRotate180() {
        for (int i = 0, N2 = N * N, N2_2 = N2 / 2; i < N2_2; i++) {
            final int i1 = i / N;
            final int j1 = i % N;
            final int i2 = (N2 - 1 - i) / N;
            final int j2 = (N2 - 1 - i) % N;
            final double temp = this.mat[i1][j1];
            this.mat[i1][j1] = this.mat[i2][j2];
            this.mat[i2][j2] = temp;
        }

        return this;
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  METRICS  ///////////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/


    public static double determinant(double[][] mat) {
        Objects.requireNonNull(mat);
        checkSquareMat(mat);
        return determinantInner(mat, mat.length);
    }

    public double determinant() {
        return determinantInner(this.mat, N);
    }

    // Gauss elimination method, O(N^3)
    private static double determinantInner(double[][] matrix, int N) {
        if (N == 0) return 0;

        double[][] m = new double[N][N];
        for (int i = 0; i < N; i++) {
            System.arraycopy(matrix[i], 0, m[i], 0, N);
        }

        double determinant = 1d;
        for (int i = 0; i < N; i++) {
            int k = i;
            for (int j = i + 1; j < N; j++) {
                if (TMath.abs(m[j][i]) > TMath.abs(m[k][i])) {
                    k = j;
                }
            }

            if (m[k][i] == 0) return 0;

            double[] temp = m[i];
            m[i] = m[k];
            m[k] = temp;

            if (i != k) {
                determinant = -determinant;
            }

            determinant *= m[i][i];

            for (int j = i + 1; j < N; j++) {
                m[i][j] /= m[i][i];
            }

            for (int j = 0; j < N; j++) {
                if (i != j) {
                    for (int l = i + 1; l < N; l++) {
                        m[j][l] -= m[j][i] * m[i][l];
                    }
                }
            }
        }

        return determinant;
    }


    public double trace() {
        double sum = 0d;
        for (int i = 0; i < N; i++) {
            sum += this.mat[i][i];
        }

        return sum;
    }


    /*///////////////////////////////////////////////////////////////*/
    /*/////////////////////  GETTERS & SETTERS  /////////////////////*/
    /*///////////////////////////////////////////////////////////////*/

    // Getters

    public double[][] getMatrix() { return mat; }
    public int getN() { return this.N; }

    public double[][] toArray(){
        double[][] copy = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                copy[i][j] = this.mat[i][j];
            }
        }

        return copy;
    }

    // Setters

    public TMatNd set(double[][] other) {
        Objects.requireNonNull(other);
        checkLengthInput(other.length);
        checkSquareMat(other);

        // If size is different current array reference will be lost and the matrix will be resized
        if (other.length != this.N) {
            this.N = other.length;
            this.mat = new double[N][N];
        }

        // Synchonization with the other 2D array
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] = other[i][j];
            }
        }

        return this;
    }

    public TMatNd set(TMatNd other) {
        Objects.requireNonNull(other);

        // If size is different current array reference will be lost and the matrix will be resized
        if (other.N != this.N) {
            this.N = other.N;
            this.mat = new double[N][N];
        }

        // Synchonization with the other 2D array
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] = other.mat[i][j];
            }
        }

        return this;
    }

    // Indexed getters & setters

    public double get(int row, int col) {
        checkIndices(row, col);
        return this.mat[row][col];
    }

    public TMatNd set(int row, int col, double value) {
        checkIndices(row, col);
        this.mat[row][col] = value;
        return this;
    }

    public double getMinOfRow(int row) {
        checkRowIndex(row);
        return TMath.min(this.mat[row]);
    }

    public double getMaxOfRow(int row) {
        checkRowIndex(row);
        return TMath.max(this.mat[row]);
    }

    public double getSumOfRow(int row) {
        checkRowIndex(row);
        return TMath.sum(this.mat[row]);
    }

    public double getMinOfCol(int col) {
        checkColIndex(col);
        double min = this.mat[0][col];
        for (int i = 1; i < N; i++) {
            final double value = this.mat[i][col];
            min = TMath.min(min, value);
        }

        return min;
    }

    public double getMaxOfCol(int col) {
        checkColIndex(col);
        double max = this.mat[0][col];
        for (int i = 1; i < N; i++) {
            final double value = this.mat[i][col];
            max = TMath.max(max, value);
        }

        return max;
    }

    public double getSumOfCol(int col) {
        checkColIndex(col);
        double sum = 0d;
        for (int i = 0; i < N; i++) {
            final double value = this.mat[i][col];
            sum += value;
        }

        return sum;
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*//////////////////////  BOOLEAN FUNCTIONS  //////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/


    public boolean isSymmetrical() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!TMath.equals(mat[i][j], mat[j][i])) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isSingular(double det) {
        return TMath.equals(det, 0d);
    }

    public boolean isInvertible(double det) {
        return !this.isSingular(det);
    }

    public boolean isSingular() {
        return TMath.equals(this.determinant(), 0d);
    }

    public boolean isInvertible() {
        return !this.isSingular();
    }

    public boolean isIdentity() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                final double expected = (i == j) ? 1d : 0d;
                if(!TMath.equals(this.mat[i][j], expected)) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isZero() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if(!TMath.equals(this.mat[i][j], 0d)) {
                    return false;
                }
            }
        }

        return true;
    }


    /*//////////////////////////////////////////////////////////////////*/
    /*////////////////////////  OBJECT METHODS  ////////////////////////*/
    /*//////////////////////////////////////////////////////////////////*/


    public boolean equals(TMatNd other) {
        if (other == null) return false;
        if (this.N != other.N) return false;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!TMath.equals(this.mat[i][j], other.mat[i][j])) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean equals(double[][] other) {
        if (other == null) return false;
        if (this.N != other.length) return false;

        for (int i = 0; i < N; i++) {
            if (other[i] == null) return false;
            if (this.N != other[i].length) return false;

            for (int j = 0; j < N; j++) {
                if (!TMath.equals(this.mat[i][j], other[i][j])) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            sb.append('[');
            for (int j = 0; j < N; j++) {
                sb.append(String.format("%.5f", mat[i][j]));
                if (j < N - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]\n");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        return equals((TMatNd) o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(N, Arrays.deepHashCode(mat));
    }


    /*//////////////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  HELPER FUNCTIONS  ///////////////////////////*/
    /*//////////////////////////////////////////////////////////////////////////*/


    private static void checkLengthInput(int length) {
        if (length <= 0) {
            throw new TephriumException("Invalid matrix length: N=%d", length);
        }
    }

    private static void checkSquareMat(double[][] mat) {
        for (int i = 0, len = mat.length; i < len; i++) {
            if (mat[i].length != len) {
                throw new TephriumException("Given matrix is not a square matrix.");
            }
        }
    }

    private void checkForArithmeticOperation(TMatNd other) {
        Objects.requireNonNull(other);
        if (this.N != other.N) {
            throw new TephriumException("Matrix lengths are not the same: %d != %d", this.N, other.N);
        }
    }

    private void checkForArithmeticOperation(double[][] mat2) {
        final int n1 = this.N;
        Objects.requireNonNull(mat2);
        final int n2 = mat2.length;
        checkLengthInput(n2);
        if(n1 != n2) {
            throw new TephriumException("Matrix lengths are not the same: %d != %d", n1, n2);
        }
        checkSquareMat(mat2);
    }

    private void checkIndices(int row, int col) {
        if(row < 0 || row >= N || col < 0 || col >= N) {
            throw new TephriumException("NxN Matrix indices out of range: row=%d, col=%d", row, col);
        }
    }

    private void checkRowIndex(int row) {
        if(row < 0 || row >= N) {
            throw new TephriumException("NxN Matrix row index out of range: %d", row);
        }
    }

    private void checkColIndex(int col) {
        if(col < 0 || col >= N) {
            throw new TephriumException("NxN Matrix column index out of range: %d", col);
        }
    }


}

