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


package com.twistral.tephrium.tests.core.matrices;

import com.twistral.tephrium.TephriumException;
import com.twistral.tephrium.TMath;
import com.twistral.tephrium.matrices.TMat2d;
import com.twistral.tephrium.matrices.TMat3d;
import com.twistral.tephrium.matrices.TMat4d;
import com.twistral.tephrium.matrices.TMatNd;
import org.apache.commons.math3.linear.FieldLUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.util.BigReal;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class TMatTests {


    @Test
    @DisplayName("tmat2Test")
    void tmat2Test() {
        TMat2d mat1 = new TMat2d();
        TMat2d mat2 = new TMat2d(1, -1, -5, 1);
        TMat2d mat3 = new TMat2d(5.25d);
        TMat2d mat4 = new TMat2d(5, 7, 6, 8);
        TMat2d mat5 = mat1.copy();

        assertEquals(mat1, mat5);
        assertArrayEquals(new double[][]{
                {1, -1},
                {-5, 1}
        }, mat2.toArray());

        mat1.add(0d, -1d, -5d, 0d);
        assertEquals(mat1, mat2);
        assertEquals(mat1.sub(-4d, -8d, -11d, -7d), mat4);
        assertEquals(mat3.scale(1d / 5.25d).sub(0d, 1d, 1d, 0d), mat5);

        assertEquals(mat1.copy().add(new TMat2d(13, 14, 15, 16)), mat1.copy().add(13, 14, 15, 16));
        assertEquals(mat1.copy().sub(new TMat2d(13, 14, 15, 16)), mat1.copy().sub(13, 14, 15, 16));

        TMat2d t1 = new TMat2d(15, 6, 7, 8);
        TMat2d t2 = new TMat2d(6, 9, -4, 1);
        assertEquals(new TMat2d(66, 141, 10, 71), t1.mul(t2));


        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            double m00 = random.nextDouble();
            double m01 = random.nextDouble();
            double m10 = random.nextDouble();
            double m11 = random.nextDouble();
            RealMatrix am1 = MatrixUtils.createRealMatrix(new double[][]{
                    {m00, m01},
                    {m10, m11}
            });
            TMat2d tm1 = new TMat2d(m00, m01, m10, m11);
            m00 = random.nextDouble();
            m01 = random.nextDouble();
            m10 = random.nextDouble();
            m11 = random.nextDouble();
            RealMatrix am2 = MatrixUtils.createRealMatrix(new double[][]{
                    {m00, m01},
                    {m10, m11}
            });
            TMat2d tm2 = new TMat2d(m00, m01, m10, m11);

            am1 = am1.multiply(am2);
            tm1.mul(m00, m01, m10, m11);

            TMat2d res = new TMat2d(am1.getRow(0)[0],am1.getRow(0)[1], am1.getRow(1)[0], am1.getRow(1)[1]);
            assertEquals(res, tm1);
        }

        mat1.set(
                45540, 45605,
                5445, -1651
        );
        assertEquals(mat1.invert(), new TMat2d(
                1651d / 323505765d, 9121d / 64701153d,
                11d / 653547d, -92d / 653547d
        ));

        for (int i = 0; i < 100000; i++) {
            double m00 = random.nextDouble();
            double m01 = random.nextDouble();
            double m10 = random.nextDouble();
            double m11 = random.nextDouble();
            BigReal[][] data = new BigReal[][]{
                    { new BigReal(m00), new BigReal(m01) },
                    { new BigReal(m10), new BigReal(m11) }
            };
            TMat2d t = new TMat2d(m00, m01, m10, m11);
            double res = new FieldLUDecomposition<>(MatrixUtils.createFieldMatrix(data)).getDeterminant().doubleValue();
            assertTrue(TMath.equals(t.determinant(), res));
        }

        mat1.set(2, 3, 4, 5);
        mat2.set(8, 3, 1, 5);
        mat1.divide(mat2);
        assertEquals(mat1.getCell(0, 0), 0.25d);
        assertEquals(mat1.getCell(1, 0), 4d);
        assertEquals(mat1.getCell(0, 1), 1);
        assertEquals(mat1.getCell(1, 1), 1);


        mat1.set(2, 3, 4, 5);
        mat1.map(x -> x*x+1);
        assertEquals(mat1, new TMat2d(5, 10, 17, 26));

        mat1.set(2, 3, 4, 5);
        mat1.divide(8, 3, 1, 1d/5d);
        assertEquals(mat1.getCell(0, 0), 0.25d);
        assertEquals(mat1.getCell(1, 0), 4d);
        assertEquals(mat1.getCell(0, 1), 1);
        assertEquals(mat1.getCell(1, 1), 25);

        mat1.set(100, -100,
                 200, 500);
        assertEquals(mat1.getMaxOfCol(0), 200);
        assertEquals(mat1.getMaxOfCol(1), 500);
        assertEquals(mat1.getMinOfCol(0), 100);
        assertEquals(mat1.getMinOfCol(1), -100);
        assertEquals(mat1.getSumOfCol(0), 300);
        assertEquals(mat1.getSumOfCol(1), 400);

        assertEquals(mat1.getMaxOfRow(0), 100);
        assertEquals(mat1.getMaxOfRow(1), 500);
        assertEquals(mat1.getMinOfRow(0), -100);
        assertEquals(mat1.getMinOfRow(1), 200);
        assertEquals(mat1.getSumOfRow(0), 0);
        assertEquals(mat1.getSumOfRow(1), 700);

        mat1 = new TMat2d();
        mat1.setCell(0, 0, 1);
        mat1.setCell(0, 1, -1);
        mat1.setCell(1, 0, 5);
        mat1.setCell(1, 1, 8);
        mat1.setCell(1, 2, 51561561561d);
        /*
        1 -1
        5 8
        */
        assertEquals(mat1.getCell(0, 0), 1);
        assertEquals(mat1.getCell(0, 1), -1);
        assertEquals(mat1.getCell(1, 0), 5);
        assertEquals(mat1.getCell(1, 1), 8);
        assertEquals(mat1.trace(), 9);


        mat1 = new TMat2d(10, 20, 30, 40);
        /*
        10 20
        30 40
        */
        TMat2d mflipHorizontally = new TMat2d(
                20, 10,
                40, 30
        );
        assertEquals(mflipHorizontally, mat1.copy().permuteGridFlipHorizontally());
        TMat2d mflipVertically = new TMat2d(
                30, 40,
                10, 20
        );
        assertEquals(mflipVertically, mat1.copy().permuteGridFlipVertically());
        TMat2d mrotate90DegAntiClockwise = new TMat2d(
                20, 40,
                10, 30
        );
        assertEquals(mrotate90DegAntiClockwise, mat1.copy().permuteGridRotate90AntiClockwise());
        TMat2d mrotate90DegClockwise = new TMat2d(
                30, 10,
                40, 20
        );
        assertEquals(mrotate90DegClockwise, mat1.copy().permuteGridRotate90Clockwise());
        TMat2d mrotate180Deg = new TMat2d(
                40, 30,
                20, 10
        );
        assertEquals(mrotate180Deg, mat1.copy().permuteGridRotate180());
        TMat2d mtranspose = new TMat2d(
                10, 30,
                20, 40
        );
        assertEquals(mtranspose, mat1.copy().transpose());


        assertTrue(new TMat2d().isIdentity());
        assertTrue(new TMat2d(9, 5, 5, 999).isSymmetrical());
        assertFalse(new TMat2d(9, 5, 50, 999).isSymmetrical());
        assertFalse(new TMat2d(9, 5, 50, 999).isSingular());
        assertTrue(new TMat2d(5, 7, 10, 14).isSingular());
    }


    @Test
    @DisplayName("tmat3Test")
    void tmat3Test() {
        // constructors & getters & setters
        TMat3d mat1 = new TMat3d();
        TMat3d mat2 = new TMat3d(
                1, 2, 3,
                4, 5, 6,
                7, 8, 9
        );

        assertEquals(mat1, mat1.copy());
        assertArrayEquals(mat2.getAsArray(), new double[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });

        TMat3d mat3 = new TMat3d(50d);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(mat3.get(i, j), 50d);
                mat3.set(i, j, -50d);
                assertEquals(mat3.get(i, j), -50d);
            }
        }

        assertEquals(mat2.get(0, 1), 2d);
        assertEquals(mat2.get(1, 1), 5d);
        assertEquals(mat2.get(2, 2), 9d);
        assertEquals(mat2.get(20, 2), Double.NaN);

        // special values
        assertEquals(mat2.trace(), 1 + 5 + 9);
        assertEquals(mat1.trace(), 1 + 1 + 1);
        assertEquals(new TMat3d(50d).trace(), 50d + 50d + 50d);

        assertTrue(TMath.equals(
                new TMat3d(-9.5, 156, 100, 500, -15, 3.1415, 78, 99, 100).determinant(),
                -2677569.64725d
        ));
        assertTrue(TMath.equals(
                TMat3d.determinant(-9.5, 156, 100, 500, -15, 3.1415, 78, 99, 100),
                -2677569.64725d
        ));

        mat1.set(
            100, 200, -100,
            0, 500, 600,
            700, -700, 50
        );
        assertEquals(mat1.getMaxOfCol(0), 700);
        assertEquals(mat1.getMaxOfCol(1), 500);
        assertEquals(mat1.getMaxOfCol(2), 600);
        assertEquals(mat1.getMinOfCol(0), 0);
        assertEquals(mat1.getMinOfCol(1), -700);
        assertEquals(mat1.getMinOfCol(2), -100);
        assertEquals(mat1.getSumOfCol(0), 800);
        assertEquals(mat1.getSumOfCol(1), 0);
        assertEquals(mat1.getSumOfCol(2), 550);

        assertEquals(mat1.getMaxOfRow(0), 200);
        assertEquals(mat1.getMaxOfRow(1), 600);
        assertEquals(mat1.getMaxOfRow(2), 700);
        assertEquals(mat1.getMinOfRow(0), -100);
        assertEquals(mat1.getMinOfRow(1), 0);
        assertEquals(mat1.getMinOfRow(2), -700);
        assertEquals(mat1.getSumOfRow(0), 200);
        assertEquals(mat1.getSumOfRow(1), 1100);
        assertEquals(mat1.getSumOfRow(2), 50);


        // basic operations
        mat1.set(1, 2, 3, 1, 2, 3, 1, 2 ,3);
        mat1.sub(0, 0, 0, 1, 2, 3, 0, 0, 0);
        mat1.add(-1, -2, -3, 0, 0, 0, 0, 0, 0);
        mat1.scale(2d);
        assertEquals(mat1, new TMat3d(0, 0, 0, 0, 0, 0, 2, 4, 6));
        assertEquals(mat1.add(mat2), new TMat3d(1, 2, 3, 4, 5, 6, 9, 12, 15));

        mat1.set(1, 2, 3, 1, 2, 3, 1, 2 ,3);
        mat1.sub(new TMat3d(0, 0, 0, 1, 2, 3, 0, 0, 0));
        mat1.add(new TMat3d(-1, -2, -3, 0, 0, 0, 0, 0, 0));
        mat1.scale(2d);
        assertEquals(mat1, new TMat3d(0, 0, 0, 0, 0, 0, 2, 4, 6));
        assertEquals(mat1.add(mat2), new TMat3d(1, 2, 3, 4, 5, 6, 9, 12, 15));

        mat1.set(20, 30, 40, 50, 60, 70, 80, 80, 90);
        mat2.set(1, 2, 3, -1, -2, 3, 7, 8, 9);
        mat1.multiply(mat2);
        assertEquals(mat1, new TMat3d(270, 300, 510, 480, 540, 960, 630, 720, 1290));

        mat2.set(1, 2, 3, -1, -2, 3, 7, 8, 9);
        mat2.multiply(20, 30, 40, 50, 60, 70, 80, 80, 90);
        assertEquals(mat2, new TMat3d(360, 390, 450, 120, 90, 90, 1260, 1410, 1650));

        assertEquals(new TMat3d(1, 2, 3, 4, 5, 6, 7, 8, 9).invert(), null);

        mat1.set(20, 30, 40, 50, 60, 70, 80, 80, 90);
        mat2.set(1, 2, 3, -1, -2, 3, 7, 8, 9);
        mat1.invert();
        mat2.invert();
        assertEquals(mat1, new TMat3d(1d/15d, -1d/6d, 1d/10d, -11d/30d, 7d/15d, -1d/5d, 4d/15d, -4d/15d, 1d/10d));
        assertEquals(mat2, new TMat3d(-7d/6d, 1d/6d, 1d/3d, 5d/6d, -1d/3d, -1d/6d, 1d/6d, 1d/6d, 0d));

        // geometric
        mat1.set(
                -1, 2, 3,
                4, -5, 6,
                7, 8, -9
        );
        TMat3d flipHorizontally = mat1.copy().flipHorizontally();
        TMat3d flipVertically = mat1.copy().flipVertically();
        TMat3d rotate90DegAntiClockwise = mat1.copy().rotate90DegAntiClockwise();
        TMat3d rotate90DegClockwise = mat1.copy().rotate90DegClockwise();
        TMat3d rotate180Deg = mat1.copy().rotate180Deg();
        TMat3d transpose = mat1.copy().transpose();

        TMat3d flipHorizontallyRes = new TMat3d(
                3, 2, -1,
                6, -5, 4,
                -9, 8, 7
        );
        TMat3d flipVerticallyRes = new TMat3d(
                7, 8, -9,
                4, -5, 6,
                -1, 2, 3
        );
        TMat3d rotate90DegAntiClockwiseRes = new TMat3d(
                3, 6, -9,
                2, -5, 8,
                -1, 4, 7
        );
        TMat3d rotate90DegClockwiseRes = new TMat3d(
                7, 4, -1,
                8, -5, 2,
                -9, 6, 3
        );
        TMat3d rotate180DegRes = new TMat3d(
                -9, 8, 7,
                6, -5, 4,
                3, 2, -1
        );
        TMat3d transposeRes = new TMat3d(
                -1, 4, 7,
                2, -5, 8,
                3, 6, -9
        );

        assertEquals(flipHorizontally, flipHorizontallyRes);
        assertEquals(flipVertically, flipVerticallyRes);
        assertEquals(rotate90DegAntiClockwise, rotate90DegAntiClockwiseRes);
        assertEquals(rotate90DegClockwise, rotate90DegClockwiseRes);
        assertEquals(rotate180Deg, rotate180DegRes);
        assertEquals(transpose, transposeRes);

        // special
        assertTrue(new TMat3d().isIdentity());
        assertTrue(new TMat3d().isSymmetrical());
        assertTrue(!new TMat3d().isSingular());

        mat1 = new TMat3d(1, 2, 3, 4, 5, 6, 7, 8, 9);
        assertFalse(mat1.isIdentity());
        assertFalse(mat1.isSymmetrical());
        assertTrue(mat1.isSingular());

        mat1.set(2, 3, 4, 5, 6, 7, 8, 9, 10);
        mat2.set(1, 3, 1, 5, 1, 7, 1, 9, 1);
        mat1.divide(mat2);
        assertEquals(mat1, new TMat3d(2, 1, 4, 1, 6, 1, 8, 1, 10));

        mat1.set(2, 3, 4, 5, 6, 7, 8, 9, 10);
        mat1.divide(1, 30, 1, 50, 1, 70, 1, 90, 1);
        assertEquals(mat1, new TMat3d(2, 0.1d, 4, 0.1d, 6, 0.1d, 8, 0.1d, 10));

        mat1.set(2, 3, 4, 5, 6, 7, 8, 9, 10);
        mat1.applyFunctionElementWise(x -> x*x+1);
        assertEquals(mat1, new TMat3d(5, 10, 17, 26, 37, 50, 65, 82, 101));


    }


    @Test
    @DisplayName("tmat4Test")
    void tmat4Test() {
        TMat4d mat = new TMat4d();
        TMat4d mat2 = new TMat4d(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        TMat4d mat3 = new TMat4d(50d);

        assertEquals(mat, mat.copy());
        assertEquals(mat, mat);
        assertEquals(mat, mat3.set(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1));
        assertEquals(mat,
                mat3.set(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1)
                        .setCell(0, 0, 20)
                        .sub(19, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        assertTrue(TMath.equals(mat2.getCell(0, 0), 1d));
        assertTrue(TMath.equals(mat2.getCell(3, 3), 16d));
        assertTrue(TMath.equals(mat2.getCell(2, 30), Double.NaN));
        assertTrue(TMath.equals(mat2.getCell(2, 3), 12d));


        mat2 = new TMat4d(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertTrue(TMath.equals(mat2.getCell(i, j), i * 4 + j + 1));
            }
        }
        mat2 = new TMat4d();
        mat2.set(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                assertTrue(TMath.equals(mat2.getCell(i, j), i * 4 + j + 1));

        mat2 = new TMat4d();
        mat2.set(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                assertTrue(TMath.equals(mat2.getCell(i, j), i * 4 + j + 1));

        mat2 = new TMat4d();
        mat2.setCell(0, 0, 1);
        mat2.setCell(0, 1, 2);
        mat2.setCell(0, 2, 3);
        mat2.setCell(0, 3, 4);
        mat2.setCell(1, 0, 5);
        mat2.setCell(1, 1, 6);
        mat2.setCell(1, 2, 7);
        mat2.setCell(1, 3, 8);
        mat2.setCell(2, 0, 9);
        mat2.setCell(2, 1, 10);
        mat2.setCell(2, 2, 11);
        mat2.setCell(2, 3, 12);
        mat2.setCell(3, 0, 13);
        mat2.setCell(3, 1, 14);
        mat2.setCell(3, 2, 15);
        mat2.setCell(3, 3, 16);
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                assertTrue(TMath.equals(mat2.getCell(i, j), i * 4 + j + 1));

        mat2.set(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        assertTrue(TMath.equals(mat2.determinant(), 0d));
        assertTrue(TMath.equals(mat2.trace(), 1d + 6d + 11d + 16d));

        mat2.set(5, 7, -8, 10, 6, 1, 2, 2, 3, 7, 8, 9, 0, 0, -90, 12);
        assertTrue(TMath.equals(mat2.determinant(), -5022d));
        assertTrue(TMath.equals(mat2.trace(), 26d));


        mat2.set(5, 7, -8, 10,
                6, 1, 2, 2,
                3, 7, 8, 9,
                0, 0, -90, 12).scale(2d);
        assertTrue(TMath.equals(mat2.trace(), 26d * 2d));
        assertTrue(TMath.equals(mat2.getCell(0, 0), 10d));
        assertTrue(TMath.equals(mat2.getCell(0, 2), -16d));
        assertTrue(TMath.equals(mat2.getCell(2, 2), 16d));

        mat2.set(5, 7, -8, 10, 6, 1, 2, 2, 3, 7, 8, 9, 0, 0, -90, 12).sub(
                new TMat4d(
                        1, 2, 7, -9,
                        4, 7, 89, 0
                        , 0, 0, 0, 0,
                        6, 7, 8, 9
                )
        );
        assertEquals(mat2, new TMat4d(4,5,-15,19,2,-6,-87,2,3,7,8,9,-6,-7,-98,3));

        mat2 = new TMat4d();
        mat2.add(new TMat4d());
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if(i == j)
                    assertEquals(mat2.getCell(i, j), 2d);
                else
                    assertEquals(mat2.getCell(i, j), 0d);
            }
        }

        mat2 = new TMat4d();
        mat2.add(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if(i == j)
                    assertEquals(mat2.getCell(i, j), 2d);
                else
                    assertEquals(mat2.getCell(i, j), 0d);
            }
        }

        TMat4d res;

        mat.set(1, 4, 7, -9, 2, 0, 4, 7, 0, 0, 15, 61, 84, 10, 20, -30);
        mat2.set(7, 8, 1, 4, 6, 0, 7, 9, 2, 1, 7, 2, 0, 41, 91, -16);
        res = new TMat4d(45,-354,-741,198,22,307,667,-96,30,2516,5656,-946,688,-538,-2436,946);
        assertEquals(mat.multiply(mat2), res);

        mat.set(1, 4, 7, -9, 2, 0, 4, 7, 0, 0, 15, 61, 84, 10, 20, -30);
        res = new TMat4d(45,-354,-741,198,22,307,667,-96,30,2516,5656,-946,688,-538,-2436,946);
        assertEquals(mat.multiply(7, 8, 1, 4, 6, 0, 7, 9, 2, 1, 7, 2, 0, 41, 91, -16), res);

        mat.set(
                30, 2, 3, 10,
                -40, 6, 7, -20,
                99, 10, 11, 30,
                66, 14, 15, -40
        );
        res.set(
                3d / 283d, -4d / 283d, -1d / 283d, 2d / 283d,
                -775d / 566d , -129d / 4528d, 939d / 2264d, -77d / 4528d,
                337d / 283d, 273d / 2264d, -355d / 1132d, 5d / 2264d,
                -43d / 2830d, 27d / 2264d, 123d / 5660d, -209d / 11320d
        );
        assertEquals(mat.invert(), res);

        mat.set(
                1, -1, 1, 1,
                2, 7, 1, 5,
                2, 7, 9, 3,
                1, 1, -1, 1
        );
        res.set(
                0d, -2d / 5d, 1d / 5d, 7d / 5d,
                -3d / 8d, -1d / 40d, 3d / 40d, 11d / 40d,
                1d / 8d, -1d / 40d, 3d / 40d, -9d / 40d,
                1d / 2d, 2d / 5d, -1d / 5d, -9d / 10d
        );
        assertEquals(mat.invert(), res);

        mat.set(
                1, 1, 1, 1,
                1, 1, 1, 1,
                1, 1, 1, 2,
                1, 1, 2, 1
        );
        assertTrue(mat.isSymmetrical());
        assertFalse(mat.isIdentity());
        assertTrue(new TMat4d().isIdentity());
        mat.setCell(0, 1, 50);
        assertFalse(mat.isSymmetrical());
        mat.set(
                1, 2, 3, 4,
                5, 6, 7, 8,
                9, 10, 11, 12,
                13, 14, 15, 16
        );
        assertTrue(mat.isSingular());
        mat.set(5, 7, -8, 10, 6, 1, 2, 2, 3, 7, 8, 9, 0, 0, -90, 12);
        assertFalse(mat.isSingular());


        mat.set(
                10, 11, 12, 13,
                14, 15, 16, 17,
                18, 19, 20, 21,
                22, 23, 24, 25
        );
        TMat4d rotate90DegClockwise = new TMat4d(
                22, 18, 14, 10,
                23, 19, 15, 11,
                24, 20, 16, 12,
                25, 21, 17, 13
        );
        TMat4d rotate180Deg = new TMat4d(
                25, 24, 23, 22,
                21, 20, 19, 18,
                17, 16, 15, 14,
                13, 12, 11, 10
        );
        TMat4d rotate90DegAntiClockwise = new TMat4d(
                13, 17, 21, 25,
                12, 16, 20, 24,
                11, 15, 19, 23,
                10, 14, 18, 22
        );
        TMat4d flipHorizontally = new TMat4d(
                13, 12, 11, 10,
                17, 16, 15, 14,
                21, 20, 19, 18,
                25, 24, 23, 22
        );
        TMat4d flipVertically = new TMat4d(
                22, 23, 24, 25,
                18, 19, 20, 21,
                14, 15, 16, 17,
                10, 11, 12, 13
        );
        TMat4d transpose = new TMat4d(
                10, 14, 18, 22,
                11, 15, 19, 23,
                12, 16, 20, 24,
                13, 17, 21, 25
        );
        assertEquals(mat.copy().flipHorizontally(), flipHorizontally);
        assertEquals(mat.copy().flipVertically(), flipVertically);
        assertEquals(mat.copy().transpose(), transpose);
        assertEquals(mat.copy().rotate90DegAntiClockwise(), rotate90DegAntiClockwise);
        assertEquals(mat.copy().rotate90DegClockwise(), rotate90DegClockwise);
        assertEquals(mat.copy().rotate180Deg(), rotate180Deg);

        mat.set(
                100, 200, 600, -100,
                500, 900, -400, 500,
                0, -100, -200, -300,
                -1000, 200, 300, 400
        );
        assertArrayEquals(mat.getAsArray(), new double[][]{
                {100, 200, 600, -100},
                {500, 900, -400, 500},
                {0, -100, -200, -300},
                {-1000, 200, 300, 400}
        });

        mat.set(
                100, 200, 600, -100,
                500, 900, -400, 500,
                0, -100, -200, -300,
                -1000, 200, 300, 400
        );
        assertEquals(mat.getMaxOfRow(0), 600);
        assertEquals(mat.getMaxOfRow(1), 900);
        assertEquals(mat.getMaxOfRow(2), 0);
        assertEquals(mat.getMaxOfRow(3), 400);
        assertEquals(mat.getMinOfRow(0), -100);
        assertEquals(mat.getMinOfRow(1), -400);
        assertEquals(mat.getMinOfRow(2), -300);
        assertEquals(mat.getMinOfRow(3), -1000);
        assertEquals(mat.getSumOfRow(0), 800);
        assertEquals(mat.getSumOfRow(1), 1500);
        assertEquals(mat.getSumOfRow(2), -600);
        assertEquals(mat.getSumOfRow(3), -100);
        assertEquals(mat.getMaxOfCol(0), 500);
        assertEquals(mat.getMaxOfCol(1), 900);
        assertEquals(mat.getMaxOfCol(2), 600);
        assertEquals(mat.getMaxOfCol(3), 500);
        assertEquals(mat.getMinOfCol(0), -1000);
        assertEquals(mat.getMinOfCol(1), -100);
        assertEquals(mat.getMinOfCol(2), -400);
        assertEquals(mat.getMinOfCol(3), -300);
        assertEquals(mat.getSumOfCol(0), -400);
        assertEquals(mat.getSumOfCol(1), 1200);
        assertEquals(mat.getSumOfCol(2), 300);
        assertEquals(mat.getSumOfCol(3), 500);

        assertEquals(TMat4d.determinant(
                100, 200, 600, -100,
                500, 900, -400, 500,
                0, -100, -200, -300,
                -1000, 200, 300, 400
        ), 205_700_000_000d);

        assertEquals(mat.determinant3x3UpperLeft(), -32000000);
        assertEquals(mat.determinant3x3UpperRight(), 198000000);
        assertEquals(mat.determinant3x3BottomLeft(), 225000000);
        assertEquals(mat.determinant3x3BottomRight(), 22000000);

        mat.set(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        mat.applyFunctionElementWise(x -> x*x+1);
        int v = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(mat.getCell(i, j), v*v+1);
                v++;
            }
        }

        mat.set(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        mat2.set(10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 11, 120, 130, 140, 150, 160);
        mat.divide(mat2);
        assertEquals(mat, new TMat4d(0.1d,0.1d,0.1d,0.1d,0.1d,0.1d,0.1d,0.1d,0.1d,0.1d,1,0.1d,0.1d,0.1d,0.1d,0.1d));

        mat.set(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        mat.divide(10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 11, 120, 130, 140, 150, 160);
        assertEquals(mat, new TMat4d(0.1d,0.1d,0.1d,0.1d,0.1d,0.1d,0.1d,0.1d,0.1d,0.1d,1,0.1d,0.1d,0.1d,0.1d,0.1d));
    }


    @Test
    @DisplayName("tmatNTest")
    void tmatNTest() {
        assertEquals(new TMatNd(4), new TMatNd(new TMat4d()));
        assertEquals(new TMatNd(3), new TMatNd(new TMat3d()));
        assertEquals(new TMatNd(2), new TMatNd(new TMat2d()));
        assertThrows(TephriumException.class, () -> new TMatNd(0, 500d));
        assertThrows(TephriumException.class, () -> new TMatNd(-1, 500d));
        assertThrows(TephriumException.class, () -> new TMatNd(-2, 500d));
        assertThrows(TephriumException.class, () -> new TMatNd(0, 500d));
        assertThrows(TephriumException.class, () -> new TMatNd(-1, 500d));
        assertThrows(TephriumException.class, () -> new TMatNd(-2, 500d));

        TMatNd mat = new TMatNd(7, 500d);
        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 7; j++)
                assertEquals(mat.getCell(i, j), 500d);

        TMatNd mat1 = new TMatNd(new double[][]{
                { 1, 2, 3, 4 },
                { 5, 6, 7, 8 },
                { 9, 10, 11, 12 },
                { 13, 14, 15, 16 }
        });
        TMatNd mat3 = mat1.copy();
        assertEquals(mat1, mat3);
        assertEquals(mat3, mat1);

        // getters and setters
        mat1 = new TMatNd(new double[][]{
                { 1, 2, 3, 4 },
                { 5, 6, 7, 80 },
                { 9, 10, 11, 12 },
                { 13, 14, 15, 16 }
        });
        assertTrue(TMath.equals(mat1.getCell(0, 0), 1));
        assertTrue(TMath.equals(mat1.getCell(0, 1), 2));
        assertTrue(TMath.equals(mat1.getCell(2, 2), 11));
        assertTrue(TMath.equals(mat1.getCell(0, 200), Double.NaN));
        assertTrue(TMath.equals(mat1.setCell(0, 0, -20).getCell(0, 0), -20));
        assertTrue(TMath.equals(mat1.getN(), 4));
        mat1 = new TMatNd(new double[][]{ {100, 20}, {3, 4} });
        assertTrue(TMath.equals(mat1.getN(), 2));
        assertTrue(TMath.equals(mat1.getCell(0, 0), 100));

        // special value methods
        mat1 = new TMatNd(new double[][]{
                {10, 2, 5, 7, 8, 9},
                {0, 7, 9, -9, 9, 9},
                {0, 0, 1, 4, 7, 1},
                {10, -10, 9, 7, 2, 4},
                {1, 7, 0, 9, 0, 1},
                {1, 1, 1, 7, 8.5d, 9.5d}
        });
        TMatNd mat2 = mat1.copy();
        assertTrue(TMath.equals(mat1.determinant(), 929005.5d));

        assertTrue(TMath.equals(mat1.trace(), 34.50000000000000000000d));
        assertEquals(mat1, mat2);
        mat1 = new TMatNd(new double[][]{
                {0,2,5,7,8,4,1},
                {0,7,1,-2,2,3,2},
                {0,0,1,4,7,1,3},
                {0,0,9,7,2,4,4},
                {1,7,0,7,0,1,5},
                {1,1,1,7,0,3,6},
                {7,0,0,0,0,0,2}
        });
        mat2 = mat1.copy();
        assertTrue(TMath.equals(mat1.determinant(), 493174));
        assertTrue(TMath.equals(mat1.trace(), 20d));
        assertEquals(mat1, mat2);

        assertTrue(TMath.equals(TMatNd.determinant(new double[][]{
                {0,2,5,7,8,4,1},
                {0,7,1,-2,2,3,2},
                {0,0,1,4,7,1,3},
                {0,0,9,7,2,4,4},
                {1,7,0,7,0,1,5},
                {1,1,1,7,0,3,6},
                {7,0,0,0,0,0,2}
        }), 493174));

        // basic methods
        mat1 = new TMatNd(new double[][]{
                {10, 11, 12, 13, 14},
                {15, 16, 17, 18, 19},
                {20, 21, 22, 23, 24},
                {25, 26, 27, 28, 29},
                {30, 31, 32, 33, 34}
        });
        mat2 = new TMatNd(new double[][]{
                {1, 0, -1, 1, -2},
                {1, 0, -1, 1, -2},
                {1, 0, -1, 1, -2},
                {1, 0, -1, 1, -2},
                {1, 0, -1, 1, -2}
        });
        mat3 = mat1.copy();
        mat1.add(new double[][]{
                {0, 0, -3, 0, 2},
                {0, 0, -3, 0, 2},
                {0, 0, -3, 0, 2},
                {0, 0, -3, 0, 2},
                {0, 0, -3, 0, 2}
        });
        mat1.add(mat2);
        assertEquals(mat1, new TMatNd(new double[][]{
                {11, 11, 8, 14, 14},
                {16, 16, 13, 19, 19},
                {21, 21, 18, 24, 24},
                {26, 26, 23, 29, 29},
                {31, 31, 28, 34, 34}
        }));

        assertArrayEquals(mat1.getAsArray(), (new double[][]{
                {11, 11, 8, 14, 14},
                {16, 16, 13, 19, 19},
                {21, 21, 18, 24, 24},
                {26, 26, 23, 29, 29},
                {31, 31, 28, 34, 34}
        }));
        mat1.sub(new double[][]{
                {0, 0, -3, 0, 2},
                {0, 0, -3, 0, 2},
                {0, 0, -3, 0, 2},
                {0, 0, -3, 0, 2},
                {0, 0, -3, 0, 2}
        });
        mat1.sub(mat2);
        assertEquals(mat3, mat1);
        mat1.scale(0d);
        mat1.setCell(0, 0, 1);
        mat1.setCell(1, 1, 1);
        mat1.setCell(2, 2, 1);
        mat1.setCell(3, 3, 1);
        mat1.setCell(4, 4, 1);
        assertEquals(mat1, new TMatNd(5));


        mat1 = new TMatNd(new double[][]{
                {1, 2, 1, 1, 1},
                {0, 1, -1, 1, 2},
                {2, 2, 2, 1, 1},
                {1, 0, 1, 1, 1},
                {0, 2, 1, 1, 2}
        });
        assertEquals(mat1.copy().invert(), new TMatNd(new double[][]{
                        {-3d/4d, 1d/2d, 1d, -1d/4d, -1d/2d},
                        {1d/2d, 0d, 0d, -1d/2d, 0d},
                        {-1d/4d, -1d/2d, 0d, 1d/4d, 1d/2d},
                        {11d/4d, -1d/2d, -2d, 5d/4d, -1d/2d},
                        {-7d/4d, 1d/2d, 1d, -1d/4d, 1d/2d}
                })
        );
        mat1.multiply(mat1); // A =  A * A = A^2
        mat1.multiply(mat1); // A = A^2 * A^2 = A^4
        // mat1 = mat1^4
        assertEquals(mat1, new TMatNd(new double[][]{
                        {80, 200, 87, 150, 237},
                        {23, 67, 30, 51, 84},
                        {123, 300, 130, 224, 351},
                        {76, 174, 77, 128, 197},
                        {79, 200, 87, 150, 238}
                })
        );


        mat1 = new TMatNd(new double[][]{
                {1, 2, 1, 1, 1},
                {0, 1, -1, 1, 2},
                {2, 2, 2, 1, 1},
                {1, 0, 1, 1, 1},
                {0, 2, 1, 1, 2}
        });
        mat1.multiply(new double[][]{
                {1, 2, 1, 1, 1},
                {0, 1, -1, 1, 2},
                {2, 2, 2, 1, 1},
                {1, 0, 1, 1, 1},
                {0, 2, 1, 1, 2}
        }); // A =  A * A = A^2
        mat1.multiply(mat1); // A = A^2 * A^2 = A^4
        // mat1 = mat1^4
        assertEquals(mat1, new TMatNd(new double[][]{
                        {80, 200, 87, 150, 237},
                        {23, 67, 30, 51, 84},
                        {123, 300, 130, 224, 351},
                        {76, 174, 77, 128, 197},
                        {79, 200, 87, 150, 238}
                })
        );


        mat1 = new TMatNd(new double[][]{
                {1, 2, 1, 1, 1},
                {0, 1, -1, 1, 2},
                {2, 2, 2, 1, 1},
                {1, 0, 1, 1, 1},
                {0, 2, 1, 1, 2}
        });
        mat2 = new TMatNd(new double[][]{
                {1, 2, 1, 1, 1},
                {0, 1, -1, 1, 2},
                {10, 10, 10, 1, 1},
                {1, 0, 1, 1, 1},
                {0, 2, 1, 1, 2}
        });
        assertEquals(mat1.multiply(mat2), new TMatNd(new double[][]{
                        {12, 16, 11, 6, 9},
                        {-9, -5, -8, 3, 6},
                        {23, 28, 22, 8, 11},
                        {12, 14, 13, 4, 5},
                        {11, 16, 11, 6, 10}
                })
        );

        // geometric methods
        mat1 = new TMatNd(new double[][]{
                {10, 11, 12, 13, 14},
                {15, 16, 17, 18, 19},
                {20, 21, 22, 23, 24},
                {25, 26, 27, 28, 29},
                {30, 31, 32, 33, 34}
        });
        TMatNd rotate90DegClockwise1 = new TMatNd(new double[][]{
                {30, 25, 20, 15, 10},
                {31, 26, 21, 16, 11},
                {32, 27, 22, 17, 12},
                {33, 28, 23, 18, 13},
                {34, 29, 24, 19, 14}
        });
        TMatNd rotate90DegAntiClockwise1 = new TMatNd(new double[][]{
                {14, 19, 24, 29, 34},
                {13, 18, 23, 28, 33},
                {12, 17, 22, 27, 32},
                {11, 16, 21, 26, 31},
                {10, 15, 20, 25, 30}
        });
        TMatNd rotate180Deg1 = new TMatNd(new double[][]{
                {34, 33, 32, 31, 30},
                {29, 28, 27, 26, 25},
                {24, 23, 22, 21, 20},
                {19, 18, 17, 16, 15},
                {14, 13, 12, 11, 10}
        });
        TMatNd flipVertically1 = new TMatNd(new double[][]{
                {30, 31, 32, 33, 34},
                {25, 26, 27, 28, 29},
                {20, 21, 22, 23, 24},
                {15, 16, 17, 18, 19},
                {10, 11, 12, 13, 14}
        });
        TMatNd flipHorizontally1 = new TMatNd(new double[][]{
                {14, 13, 12, 11, 10},
                {19, 18, 17, 16, 15},
                {24, 23, 22, 21, 20},
                {29, 28, 27, 26, 25},
                {34, 33, 32, 31, 30}
        });
        TMatNd transpose1 = new TMatNd(new double[][]{
                {10, 15, 20, 25, 30},
                {11, 16, 21, 26, 31},
                {12, 17, 22, 27, 32},
                {13, 18, 23, 28, 33},
                {14, 19, 24, 29, 34}
        });
        assertEquals(mat1.copy().transpose(), transpose1);
        assertEquals(mat1.copy().flipHorizontally(), flipHorizontally1);
        assertEquals(mat1.copy().flipVertically(), flipVertically1);
        assertEquals(mat1.copy().rotate90DegAntiClockwise(), rotate90DegAntiClockwise1);
        assertEquals(mat1.copy().rotate90DegClockwise(), rotate90DegClockwise1);
        assertEquals(mat1.copy().rotate180Deg(), rotate180Deg1);

        mat1 = new TMatNd(new double[][]{
                {10, 11, 12, 13, 14, 15},
                {16, 17, 18, 19, 20, 21},
                {22, 23, 24, 25, 26, 27},
                {28, 29, 30, 31, 32, 33},
                {34, 35, 36, 37, 38, 39},
                {40, 41, 42, 43, 44, 45}
        });
        rotate90DegClockwise1 = new TMatNd(new double[][]{
                {40, 34, 28, 22, 16, 10},
                {41, 35, 29, 23, 17, 11},
                {42, 36, 30, 24, 18, 12},
                {43, 37, 31, 25, 19, 13},
                {44, 38, 32, 26, 20, 14},
                {45, 39, 33, 27, 21, 15}
        });
        rotate90DegAntiClockwise1 = new TMatNd(new double[][]{
                {15, 21, 27, 33, 39, 45},
                {14, 20, 26, 32, 38, 44},
                {13, 19, 25, 31, 37, 43},
                {12, 18, 24, 30, 36, 42},
                {11, 17, 23, 29, 35, 41},
                {10, 16, 22, 28, 34, 40}
        });
        rotate180Deg1 = new TMatNd(new double[][]{
                {45, 44, 43, 42, 41, 40},
                {39, 38, 37, 36, 35, 34},
                {33, 32, 31, 30, 29, 28},
                {27, 26, 25, 24, 23, 22},
                {21, 20, 19, 18, 17, 16},
                {15, 14, 13, 12, 11, 10}
        });
        flipVertically1 = new TMatNd(new double[][]{
                {40, 41, 42, 43, 44, 45},
                {34, 35, 36, 37, 38, 39},
                {28, 29, 30, 31, 32, 33},
                {22, 23, 24, 25, 26, 27},
                {16, 17, 18, 19, 20, 21},
                {10, 11, 12, 13, 14, 15}
        });
        flipHorizontally1 = new TMatNd(new double[][]{
                {15, 14, 13, 12, 11, 10},
                {21, 20, 19, 18, 17, 16},
                {27, 26, 25, 24, 23, 22},
                {33, 32, 31, 30, 29, 28},
                {39, 38, 37, 36, 35, 34},
                {45, 44, 43, 42, 41, 40}
        });
        transpose1 = new TMatNd(new double[][]{
                {10, 16, 22, 28, 34, 40},
                {11, 17, 23, 29, 35, 41},
                {12, 18, 24, 30, 36, 42},
                {13, 19, 25, 31, 37, 43},
                {14, 20, 26, 32, 38, 44},
                {15, 21, 27, 33, 39, 45}
        });
        assertEquals(mat1.copy().transpose(), transpose1);
        assertEquals(mat1.copy().flipHorizontally(), flipHorizontally1);
        assertEquals(mat1.copy().flipVertically(), flipVertically1);
        assertEquals(mat1.copy().rotate90DegAntiClockwise(), rotate90DegAntiClockwise1);
        assertEquals(mat1.copy().rotate90DegClockwise(), rotate90DegClockwise1);
        assertEquals(mat1.copy().rotate180Deg(), rotate180Deg1);


        // special matrix methods
        mat1 = new TMatNd(new double[][]{
                {10, 2, 2, 2},
                {2, 94, 2, 2},
                {2, 2, 165651, 2},
                {2, 2, 2, 515050}
        });
        assertTrue(mat1.isSymmetrical());
        mat1.setCell(0, 1, 50);
        assertFalse(mat1.isSymmetrical());

        for (int i = 1; i < 200; i++)
            assertTrue(new TMatNd(i).isIdentity());
        assertTrue(new TMatNd(new TMat2d()).isIdentity());
        assertTrue(new TMatNd(new TMat3d()).isIdentity());
        assertTrue(new TMatNd(new TMat4d()).isIdentity());
        assertFalse(new TMatNd(new TMat2d(1, 0, 0, 2)).isIdentity());

        mat1 = new TMatNd(new double[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        });
        assertTrue(mat1.isSingular());
        mat1 = new TMatNd(new double[][]{
                {100, 2, 56413, 4},
                {5, 600, 7, 68},
                {9, 100, 11, 12},
                {13, 14, 20015, 16}
        });
        assertFalse(mat1.isSingular());

        mat = new TMatNd(new double[][]{
                {1, 6, 5, 9, 8, 7, -6},
                {-8, 9, 7, 1, 0, -4, 9},
                {9, -8, 2, 0, -8, 4, 6},
                {1, 5, -6, -8, 7, 5, 1},
                {0, 0, 2, -8, 5, 4, 0},
                {0, 0, 0, 9, 7, 8, -6},
                {9, -8, 7, -6, 5, -4, 3}
        });
        assertEquals(mat.getMaxOfRow(0), 9);
        assertEquals(mat.getMaxOfRow(1), 9);
        assertEquals(mat.getMaxOfRow(2), 9);
        assertEquals(mat.getMaxOfRow(3), 7);
        assertEquals(mat.getMaxOfRow(4), 5);
        assertEquals(mat.getMaxOfRow(5), 9);
        assertEquals(mat.getMaxOfRow(6), 9);
        assertEquals(mat.getMinOfRow(0), -6);
        assertEquals(mat.getMinOfRow(1), -8);
        assertEquals(mat.getMinOfRow(2), -8);
        assertEquals(mat.getMinOfRow(3), -8);
        assertEquals(mat.getMinOfRow(4), -8);
        assertEquals(mat.getMinOfRow(5), -6);
        assertEquals(mat.getMinOfRow(6), -8);
        assertEquals(mat.getSumOfRow(0), 30);
        assertEquals(mat.getSumOfRow(1), 14);
        assertEquals(mat.getSumOfRow(2), 5);
        assertEquals(mat.getSumOfRow(3), 5);
        assertEquals(mat.getSumOfRow(4), 3);
        assertEquals(mat.getSumOfRow(5), 18);
        assertEquals(mat.getSumOfRow(6), 6);
        assertEquals(mat.getMaxOfCol(0), 9);
        assertEquals(mat.getMaxOfCol(1), 9);
        assertEquals(mat.getMaxOfCol(2), 7);
        assertEquals(mat.getMaxOfCol(3), 9);
        assertEquals(mat.getMaxOfCol(4), 8);
        assertEquals(mat.getMaxOfCol(5), 8);
        assertEquals(mat.getMaxOfCol(6), 9);
        assertEquals(mat.getMinOfCol(0), -8);
        assertEquals(mat.getMinOfCol(1), -8);
        assertEquals(mat.getMinOfCol(2), -6);
        assertEquals(mat.getMinOfCol(3), -8);
        assertEquals(mat.getMinOfCol(4), -8);
        assertEquals(mat.getMinOfCol(5), -4);
        assertEquals(mat.getMinOfCol(6), -6);
        assertEquals(mat.getSumOfCol(0), 12);
        assertEquals(mat.getSumOfCol(1), 4);
        assertEquals(mat.getSumOfCol(2), 17);
        assertEquals(mat.getSumOfCol(3), -3);
        assertEquals(mat.getSumOfCol(4), 24);
        assertEquals(mat.getSumOfCol(5), 20);
        assertEquals(mat.getSumOfCol(6), 7);


        mat = new TMatNd(20);
        int v = 1;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                mat.setCell(i, j, v++);
            }
        }
        mat.applyFunctionElementWise(x -> x*x*x + 5);
        v = 1;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                assertEquals(mat.getCell(i, j), v*v*v + 5);
                v++;
            }
        }

        mat = new TMatNd(new double[][]{
                {1, 20, 3, 4, 50},
                {10, 2, 3, 40, 5},
                {1, 20, 3, 4, 5},
                {1, 2, 30, 4, 5},
                {1, 2, 3, 40, 5}
        });
        mat2 = new TMatNd(new double[][]{
                {1, 20, 3, 4, 50},
                {1, 1, 1, 1, 5},
                {1, 4, 1, 1, 5},
                {1, 1, 1, 1, 5},
                {1, 1, 1, 1, 5}
        });
        mat.divide(mat2);
        assertEquals(mat, new TMatNd(new double[][]{
                {1, 1, 1, 1, 1},
                {10, 2, 3, 40, 1},
                {1, 5, 3, 4, 1},
                {1, 2, 30, 4, 1},
                {1, 2, 3, 40, 1}
        }));


        mat = new TMatNd(new double[][]{
                {1, 20, 3, 4, 50},
                {10, 2, 3, 40, 5},
                {1, 20, 3, 4, 5},
                {1, 2, 30, 4, 5},
                {1, 2, 3, 40, 5}
        });
        mat.divide(new double[][]{
                {1, 20, 3, 4, 50},
                {1, 1, 1, 1, 5},
                {1, 4, 1, 1, 5},
                {1, 1, 1, 1, 5},
                {1, 1, 1, 1, 5}
        });
        assertEquals(mat, new TMatNd(new double[][]{
                {1, 1, 1, 1, 1},
                {10, 2, 3, 40, 1},
                {1, 5, 3, 4, 1},
                {1, 2, 30, 4, 1},
                {1, 2, 3, 40, 1}
        }));

    }


}
