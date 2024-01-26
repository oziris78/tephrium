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

import com.twistral.tephrium.core.vectors.TVec3;
import com.twistral.tephrium.core.matrices.TMat3;
import static org.junit.jupiter.api.Assertions.*;
import com.twistral.tephrium.core.functions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TMat3Test {


    @Test
    @DisplayName("allOperationsTest")
    void allOperationsTest() {
        // constructors & getters & setters
        TMat3 mat1 = new TMat3();
        TMat3 mat2 = new TMat3(
                1, 2, 3,
                4, 5, 6,
                7, 8, 9
        );

        assertEquals(mat1, mat1.copy());

        TMat3 mat3 = new TMat3(50d);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(mat3.getCell(i, j), 50d);
                mat3.setCell(i, j, -50d);
                assertEquals(mat3.getCell(i, j), -50d);
            }
        }

        assertEquals(mat2.getCell(0, 1), 2d);
        assertEquals(mat2.getCell(1, 1), 5d);
        assertEquals(mat2.getCell(2, 2), 9d);
        assertEquals(mat2.getCell(20, 2), Double.NaN);

        // special values
        assertEquals(mat2.trace(), 1 + 5 + 9);
        assertEquals(mat1.trace(), 1 + 1 + 1);
        assertEquals(new TMat3(50d).trace(), 50d + 50d + 50d);

        assertTrue(TMath.equalsd(
            new TMat3(-9.5, 156, 100, 500, -15, 3.1415, 78, 99, 100).determinant(),
            -2677569.64725d
        ));

        // basic operations
        mat1.set(1, 2, 3, 1, 2, 3, 1, 2 ,3);
        mat1.sub(0, 0, 0, 1, 2, 3, 0, 0, 0);
        mat1.add(-1, -2, -3, 0, 0, 0, 0, 0, 0);
        mat1.scale(2d);
        assertEquals(mat1, new TMat3(0, 0, 0, 0, 0, 0, 2, 4, 6));
        assertEquals(mat1.add(mat2), new TMat3(1, 2, 3, 4, 5, 6, 9, 12, 15));

        mat1.set(1, 2, 3, 1, 2, 3, 1, 2 ,3);
        mat1.sub(new TMat3(0, 0, 0, 1, 2, 3, 0, 0, 0));
        mat1.add(new TMat3(-1, -2, -3, 0, 0, 0, 0, 0, 0));
        mat1.scale(2d);
        assertEquals(mat1, new TMat3(0, 0, 0, 0, 0, 0, 2, 4, 6));
        assertEquals(mat1.add(mat2), new TMat3(1, 2, 3, 4, 5, 6, 9, 12, 15));

        mat1.set(20, 30, 40, 50, 60, 70, 80, 80, 90);
        mat2.set(1, 2, 3, -1, -2, 3, 7, 8, 9);
        mat1.multiply(mat2);
        assertEquals(mat1, new TMat3(270, 300, 510, 480, 540, 960, 630, 720, 1290));

        mat1.set(20, 30, 40, 50, 60, 70, 80, 80, 90);
        mat2.set(1, 2, 3, -1, -2, 3, 7, 8, 9);
        mat2.multiply(mat1);
        assertEquals(mat2, new TMat3(360, 390, 450, 120, 90, 90, 1260, 1410, 1650));

        assertEquals(new TMat3(1, 2, 3, 4, 5, 6, 7, 8, 9).invert(), null);

        mat1.set(20, 30, 40, 50, 60, 70, 80, 80, 90);
        mat2.set(1, 2, 3, -1, -2, 3, 7, 8, 9);
        mat1.invert();
        mat2.invert();
        assertEquals(mat1, new TMat3(1d/15d, -1d/6d, 1d/10d, -11d/30d, 7d/15d, -1d/5d, 4d/15d, -4d/15d, 1d/10d));
        assertEquals(mat2, new TMat3(-7d/6d, 1d/6d, 1d/3d, 5d/6d, -1d/3d, -1d/6d, 1d/6d, 1d/6d, 0d));

        // geometric
        mat1.set(
            -1, 2, 3,
            4, -5, 6,
            7, 8, -9
        );
        TMat3 flipHorizontally = mat1.copy().flipHorizontally();
        TMat3 flipVertically = mat1.copy().flipVertically();
        TMat3 rotate90DegAntiClockwise = mat1.copy().rotate90DegAntiClockwise();
        TMat3 rotate90DegClockwise = mat1.copy().rotate90DegClockwise();
        TMat3 rotate180Deg = mat1.copy().rotate180Deg();
        TMat3 transpose = mat1.copy().transpose();

        TMat3 flipHorizontallyRes = new TMat3(
            3, 2, -1,
            6, -5, 4,
            -9, 8, 7
        );
        TMat3 flipVerticallyRes = new TMat3(
            7, 8, -9,
            4, -5, 6,
            -1, 2, 3
        );
        TMat3 rotate90DegAntiClockwiseRes = new TMat3(
            3, 6, -9,
            2, -5, 8,
            -1, 4, 7
        );
        TMat3 rotate90DegClockwiseRes = new TMat3(
            7, 4, -1,
            8, -5, 2,
            -9, 6, 3
        );
        TMat3 rotate180DegRes = new TMat3(
            -9, 8, 7,
            6, -5, 4,
            3, 2, -1
        );
        TMat3 transposeRes = new TMat3(
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
        assertTrue(new TMat3().isIdentity());
        assertTrue(new TMat3().isSymmetrical());
        assertTrue(!new TMat3().isSingular());

        mat1 = new TMat3(1, 2, 3, 4, 5, 6, 7, 8, 9);
        assertFalse(mat1.isIdentity());
        assertFalse(mat1.isSymmetrical());
        assertTrue(mat1.isSingular());



    }


}
