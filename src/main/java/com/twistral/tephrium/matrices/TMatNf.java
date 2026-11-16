

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
import java.util.*;
import java.util.function.*;
import static com.twistral.tephrium.collections.TCollections.newArray;


/**
 * A mutable, variable sized (NxN), float valued, row major matrix class with method chaining support. <br>
 *
 * <b>NOTE: This class runs dynamic loops and performs heap allocations frequently so it is way slower
 * than other matrix classes. If your matrix size is less than 5, definitely use those classes instead.</b>
 */
public class TMatNf {

    private int N;
    private float[][] mat;

    // Actual constructors

    /**
     * Creates a matrix from the given 2D array.
     *
     * @param mat the 2D array that contains the matrix data
     * @param copy if true, the given 2D array will be copied, otherwise it will be referenced
     * @throws TephriumException if the 2D input array is null or not a square matrix
     */
    public TMatNf(float[][] mat, boolean copy) {
        Objects.requireNonNull(mat);
        checkLengthInput(mat.length);
        checkSquareMat(mat);

        this.N = mat.length;

        if (copy) {
            this.mat = new float[N][N];
            for (int i = 0; i < N; i++) {
                this.mat[i] = new float[N];
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
    public TMatNf(float[][] matReference) {
        this(matReference, false);
    }

    /**
     * Creates a matrix where all the cells are filled with the given value.
     *
     * @param N size of the matrix
     * @param fillValue any float value to fill the cells
     */
    public TMatNf(int N, float fillValue) {
        checkLengthInput(N);
        this.N = N;
        this.mat = newArray(N, N, fillValue);
    }

    /**
     * Creates I<sub>N</sub> which is the NxN identity matrix.
     *
     * @param N size of the matrix
     */
    public TMatNf(int N) {
        checkLengthInput(N);
        this.N = N;

        this.mat = new float[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] = (i == j) ? 1f : 0f;
            }
        }
    }

    // Copying constructors & functions

    public TMatNf(TMatNf other) {
        this(other.mat, true);
    }

    public TMatNf copy() {
        return new TMatNf(this.mat, true);
    }

    public TMatNf(TMat2f matrix) {
        this(matrix.toArray());
    }

    public TMatNf(TMat3f matrix) {
        this(matrix.toArray());
    }

    public TMatNf(TMat4f matrix) {
        this(matrix.toArray());
    }

    // Special static factory methods

    public static TMatNf zero(int N) {
        return new TMatNf(N, 0f);
    }

    /** Returns a diagonal matrix with the specified diagonal values */
    public static TMatNf diagonal(float[] diagonal) {
        Objects.requireNonNull(diagonal);
        checkLengthInput(diagonal.length);

        final int N = diagonal.length;
        float[][] arr = new float[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                arr[i][j] = (i == j) ? diagonal[i] : 0f;
            }
        }

        return new TMatNf(arr);
    }

    /** Returns an NxN scaling matrix */
    public static TMatNf scaling(float[] scalars) {
        return diagonal(scalars);
    }

    /** Returns a uniform NxN scaling matrix */
    public static TMatNf scaling(int N, float scale) {
        checkLengthInput(N);

        float[][] arr = new float[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                arr[i][j] = (i == j) ? scale : 0f;
            }
        }

        return new TMatNf(arr);
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*////////////////////  ARITHMETIC OPERATIONS  ////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    // Addition

    public TMatNf add(float[][] other) {
        checkForArithmeticOperation(other);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] += other[i][j];
            }
        }

        return this;
    }

    public TMatNf add(TMatNf other) {
        checkForArithmeticOperation(other);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] += other.mat[i][j];
            }
        }

        return this;
    }

    public TMatNf add(int row, int col, float x) {
        checkIndices(row, col);
        this.mat[row][col] += x;
        return this;
    }

    // Subtraction

    public TMatNf sub(float[][] other) {
        checkForArithmeticOperation(other);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] -= other[i][j];
            }
        }

        return this;
    }

    public TMatNf sub(TMatNf other) {
        checkForArithmeticOperation(other);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] -= other.mat[i][j];
            }
        }

        return this;
    }

    public TMatNf sub(int row, int col, float x) {
        checkIndices(row, col);
        this.mat[row][col] -= x;
        return this;
    }

    // (ELEMENT WISE) Scaling / Multiplication

    public TMatNf scale(float[][] other) {
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
     * For matrix multiplication use this method: {@link #mul(TMatNf)}
     *
     * @param other matrix to perform element-wise multiplication with
     * @return this matrix for method chaining
     */
    public TMatNf scale(TMatNf other) {
        checkForArithmeticOperation(other);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] *= other.mat[i][j];
            }
        }

        return this;
    }

    public TMatNf scale(float scale) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] *= scale;
            }
        }

        return this;
    }

    public TMatNf scale(int row, int col, float x) {
        checkIndices(row, col);
        this.mat[row][col] *= x;
        return this;
    }

    // (ELEMENT WISE) Division

    public TMatNf div(float[][] other) {
        checkForArithmeticOperation(other);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] /= other[i][j];
            }
        }

        return this;
    }

    public TMatNf div(TMatNf other) {
        checkForArithmeticOperation(other);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] /= other.mat[i][j];
            }
        }

        return this;
    }

    public TMatNf div(float scale) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] /= scale;
            }
        }

        return this;
    }

    public TMatNf div(int row, int col, float x) {
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
    public TMatNf mul(float[][] other) {
        checkForArithmeticOperation(other);

        float[][] newMatrix = new float[N][N];
        for (int i = 0; i < N; i++) {
            for (int k = 0; k < N; k++) {
                float result = 0f;
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
    public TMatNf mul(TMatNf other) {
        checkForArithmeticOperation(other);

        float[][] newMatrix = new float[N][N];
        for (int i = 0; i < N; i++) {
            for (int k = 0; k < N; k++) {
                float result = 0f;
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
    public TMatNf transpose() {
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                float temp = this.mat[i][j];
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
    public TMatNf invert() {
        int[] index = new int[N];
        for (int i = 0; i < N; i++) index[i] = i;

        float[] maxRowValues = new float[N];
        for (int i = 0; i < N; i++) maxRowValues[i] = getMaxOfRow(i);

        for (int col = 0; col < N - 1; col++) {
            int pivot = 0;
            float maxRatio = 0;

            for (int row = col; row < N; row++) {
                float ratio = TMath.abs(mat[index[row]][col]) / maxRowValues[index[row]];
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
                float factor = mat[index[row]][col] / mat[index[col]][col];
                mat[index[row]][col] = factor;

                for (int l = col + 1; l < N; l++) {
                    mat[index[row]][l] -= factor * mat[index[col]][l];
                }
            }
        }

        // Back substitution
        TMatNf matInverted = new TMatNf(N); // I_N
        for (int i = 0; i < N - 1; i++) {
            for (int j = i + 1; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    matInverted.mat[index[j]][k] -= mat[index[j]][i] * matInverted.mat[index[i]][k];
                }
            }
        }

        float[][] resultMatrix = new float[N][N];
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
     * @param function any float-to-float function
     * @return this matrix for method chaining
     */
    public TMatNf map(FloatUnaryOperator function) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] = function.applyAsFloat(this.mat[i][j]);
            }
        }

        return this;
    }

    public TMatNf sin() { return map(TMath::sin); }
    public TMatNf cos() { return map(TMath::cos); }
    public TMatNf tan() { return map(TMath::tan); }
    public TMatNf sinh() { return map(TMath::sinh); }
    public TMatNf cosh() { return map(TMath::cosh); }
    public TMatNf tanh() { return map(TMath::tanh); }
    public TMatNf arcsin() { return map(TMath::arcsin); }
    public TMatNf arccos() { return map(TMath::arccos); }
    public TMatNf arctan() { return map(TMath::arctan); }

    public TMatNf abs() { return map(TMath::abs); }
    public TMatNf floor() { return map(TMath::floor); }
    public TMatNf ceil() { return map(TMath::ceil); }
    public TMatNf sqrt() { return map(TMath::sqrt); }
    public TMatNf cbrt() { return map(TMath::cbrt); }
    public TMatNf square() { return map(TMath::square); }
    public TMatNf cube() { return map(TMath::cube); }


    /*/////////////////////////////////////////////////////////////////*/
    /*//////////////////  GRID ELEMENT PERMUTATIONS  //////////////////*/
    /*/////////////////////////////////////////////////////////////////*/


    /**
     * Horizontally flips the elements of this matrix grid.
     * <b>Keep in mind that this is NOT a geometric space transformation!</b>
     *
     * @return this matrix for method chaining
     */
    public TMatNf permuteGridFlipHorizontally() {
        for (int i = 0, N_2 = N / 2; i < N; i++) {
            for (int j = 0; j < N_2; j++) {
                final int j2 = N - (j + 1);
                final float temp = this.mat[i][j];
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
    public TMatNf permuteGridFlipVertically() {
        for (int i = 0, N_2 = N / 2; i < N_2; i++) {
            for (int j = 0; j < N; j++) {
                final int i2 = N - (i + 1);
                final float temp = this.mat[i][j];
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
    public TMatNf permuteGridRotate90Clockwise() {
        for (int i = 0, N_2 = N / 2; i < N_2; i++) {
            final int i2 = N - 1 - i;
            for (int j = i; j < i2; j++) {
                final int j2 = N - 1 - j;
                final float temp = this.mat[i][j];
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
    public TMatNf permuteGridRotate90AntiClockwise() {
        for (int i = 0, N_2 = N / 2; i < N_2; i++) {
            final int i2 = N - 1 - i;
            for (int j = i; j < i2; j++) {
                final int j2 = N - 1 - j;
                final float temp = this.mat[i][j];
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
    public TMatNf permuteGridRotate180() {
        for (int i = 0, N2 = N * N, N2_2 = N2 / 2; i < N2_2; i++) {
            final int i1 = i / N;
            final int j1 = i % N;
            final int i2 = (N2 - 1 - i) / N;
            final int j2 = (N2 - 1 - i) % N;
            final float temp = this.mat[i1][j1];
            this.mat[i1][j1] = this.mat[i2][j2];
            this.mat[i2][j2] = temp;
        }

        return this;
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  METRICS  ///////////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/


    public static float determinant(float[][] mat) {
        Objects.requireNonNull(mat);
        checkSquareMat(mat);
        return determinantInner(mat, mat.length);
    }

    public float determinant() {
        return determinantInner(this.mat, N);
    }

    // Gauss elimination method, O(N^3)
    private static float determinantInner(float[][] matrix, int N) {
        if (N == 0) return 0f;

        float[][] m = new float[N][N];
        for (int i = 0; i < N; i++) {
            System.arraycopy(matrix[i], 0, m[i], 0, N);
        }

        float determinant = 1f;
        for (int i = 0; i < N; i++) {
            int k = i;
            for (int j = i + 1; j < N; j++) {
                if (TMath.abs(m[j][i]) > TMath.abs(m[k][i])) {
                    k = j;
                }
            }

            if (m[k][i] == 0) return 0;

            float[] temp = m[i];
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


    public float trace() {
        float sum = 0f;
        for (int i = 0; i < N; i++) {
            sum += this.mat[i][i];
        }

        return sum;
    }


    /*///////////////////////////////////////////////////////////////*/
    /*/////////////////////  GETTERS & SETTERS  /////////////////////*/
    /*///////////////////////////////////////////////////////////////*/

    // Getters

    public float[][] getMatrix() { return mat; }
    public int getN() { return this.N; }

    public float[][] toArray(){
        float[][] copy = new float[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                copy[i][j] = this.mat[i][j];
            }
        }

        return copy;
    }

    // Setters

    public TMatNf set(float[][] other) {
        Objects.requireNonNull(other);
        checkLengthInput(other.length);
        checkSquareMat(other);

        // If size is different current array reference will be lost and the matrix will be resized
        if (other.length != this.N) {
            this.N = other.length;
            this.mat = new float[N][N];
        }

        // Synchonization with the other 2D array
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.mat[i][j] = other[i][j];
            }
        }

        return this;
    }

    public TMatNf set(TMatNf other) {
        Objects.requireNonNull(other);

        // If size is different current array reference will be lost and the matrix will be resized
        if (other.N != this.N) {
            this.N = other.N;
            this.mat = new float[N][N];
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

    public float get(int row, int col) {
        checkIndices(row, col);
        return this.mat[row][col];
    }

    public TMatNf set(int row, int col, float value) {
        checkIndices(row, col);
        this.mat[row][col] = value;
        return this;
    }

    public float getMinOfRow(int row) {
        checkRowIndex(row);
        return TMath.min(this.mat[row]);
    }

    public float getMaxOfRow(int row) {
        checkRowIndex(row);
        return TMath.max(this.mat[row]);
    }

    public float getSumOfRow(int row) {
        checkRowIndex(row);
        return TMath.sum(this.mat[row]);
    }

    public float getMinOfCol(int col) {
        checkColIndex(col);
        float min = this.mat[0][col];
        for (int i = 1; i < N; i++) {
            final float value = this.mat[i][col];
            min = TMath.min(min, value);
        }

        return min;
    }

    public float getMaxOfCol(int col) {
        checkColIndex(col);
        float max = this.mat[0][col];
        for (int i = 1; i < N; i++) {
            final float value = this.mat[i][col];
            max = TMath.max(max, value);
        }

        return max;
    }

    public float getSumOfCol(int col) {
        checkColIndex(col);
        float sum = 0f;
        for (int i = 0; i < N; i++) {
            final float value = this.mat[i][col];
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
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                final float expected = (i == j) ? 1f : 0f;
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
                if(!TMath.equals(this.mat[i][j], 0f)) {
                    return false;
                }
            }
        }

        return true;
    }


    /*//////////////////////////////////////////////////////////////////*/
    /*////////////////////////  OBJECT METHODS  ////////////////////////*/
    /*//////////////////////////////////////////////////////////////////*/


    public boolean equals(TMatNf other) {
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

    public boolean equals(float[][] other) {
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
                sb.append(String.format(Locale.ROOT, "%.5f", mat[i][j]));
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
        return equals((TMatNf) o);
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

    private static void checkSquareMat(float[][] mat) {
        for (int i = 0, len = mat.length; i < len; i++) {
            if (mat[i].length != len) {
                throw new TephriumException("Given matrix is not a square matrix.");
            }
        }
    }

    private void checkForArithmeticOperation(TMatNf other) {
        Objects.requireNonNull(other);
        if (this.N != other.N) {
            throw new TephriumException("Matrix lengths are not the same: %d != %d", this.N, other.N);
        }
    }

    private void checkForArithmeticOperation(float[][] mat2) {
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

