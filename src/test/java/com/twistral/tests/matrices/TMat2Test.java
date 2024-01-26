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


package com.twistral.tests.matrices;

import com.twistral.tephrium.core.matrices.TMat2;
import com.twistral.tephrium.core.functions.*;
import org.apache.commons.math3.linear.FieldLUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.util.BigReal;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class TMat2Test {


    @Test
    @DisplayName("allOperationsTest")
    void allOperationsTest() {
        TMat2 mat1 = new TMat2();
        TMat2 mat2 = new TMat2(1, -1, -5, 1);
        TMat2 mat3 = new TMat2(5.25d);
        TMat2 mat4 = new TMat2(5, 7, 6, 8);
        TMat2 mat5 = mat1.copy();

        assertEquals(mat1, mat5);

        mat1.add(0d, -1d, -5d, 0d);
        assertEquals(mat1, mat2);
        assertEquals(mat1.sub(-4d, -8d, -11d, -7d), mat4);
        assertEquals(mat3.scale(1d / 5.25d).sub(0d, 1d, 1d, 0d), mat5);

        assertEquals(mat1.copy().add(new TMat2(13, 14, 15, 16)), mat1.copy().add(13, 14, 15, 16));
        assertEquals(mat1.copy().sub(new TMat2(13, 14, 15, 16)), mat1.copy().sub(13, 14, 15, 16));

        TMat2 t1 = new TMat2(15, 6, 7, 8);
        TMat2 t2 = new TMat2(6, 9, -4, 1);
        assertEquals(new TMat2(66, 141, 10, 71), t1.multiply(t2));

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
            TMat2 tm1 = new TMat2(m00, m01, m10, m11);
            m00 = random.nextDouble();
            m01 = random.nextDouble();
            m10 = random.nextDouble();
            m11 = random.nextDouble();
            RealMatrix am2 = MatrixUtils.createRealMatrix(new double[][]{
                    {m00, m01},
                    {m10, m11}
            });
            TMat2 tm2 = new TMat2(m00, m01, m10, m11);

            am1 = am1.multiply(am2);
            tm1.multiply(tm2);

            TMat2 res = new TMat2(am1.getRow(0)[0],am1.getRow(0)[1], am1.getRow(1)[0], am1.getRow(1)[1]);
            assertEquals(res, tm1);
        }

        mat1 = new TMat2(
                45540, 45605,
                5445, -1651
        );
        assertEquals(mat1.invert(), new TMat2(
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
            TMat2 t = new TMat2(m00, m01, m10, m11);
            double res = new FieldLUDecomposition<>(MatrixUtils.createFieldMatrix(data)).getDeterminant().doubleValue();
            assertTrue(TMath.equalsd(t.determinant(), res));
        }


        mat1 = new TMat2();
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


        mat1 = new TMat2(10, 20, 30, 40);
        /*
        10 20
        30 40
        */
        TMat2 mflipHorizontally = new TMat2(
                20, 10,
                40, 30
        );
        assertEquals(mflipHorizontally, mat1.copy().flipHorizontally());
        TMat2 mflipVertically = new TMat2(
                30, 40,
                10, 20
        );
        assertEquals(mflipVertically, mat1.copy().flipVertically());
        TMat2 mrotate90DegAntiClockwise = new TMat2(
                20, 40,
                10, 30
        );
        assertEquals(mrotate90DegAntiClockwise, mat1.copy().rotate90DegAntiClockwise());
        TMat2 mrotate90DegClockwise = new TMat2(
                30, 10,
                40, 20
        );
        assertEquals(mrotate90DegClockwise, mat1.copy().rotate90DegClockwise());
        TMat2 mrotate180Deg = new TMat2(
                40, 30,
                20, 10
        );
        assertEquals(mrotate180Deg, mat1.copy().rotate180Deg());
        TMat2 mtranspose = new TMat2(
                10, 30,
                20, 40
        );
        assertEquals(mtranspose, mat1.copy().transpose());


        assertTrue(new TMat2().isIdentity());
        assertTrue(new TMat2(9, 5, 5, 999).isSymmetrical());
        assertFalse(new TMat2(9, 5, 50, 999).isSymmetrical());
        assertFalse(new TMat2(9, 5, 50, 999).isSingular());
        assertTrue(new TMat2(5, 7, 10, 14).isSingular());





    }

}
