
// Copyright 2024-2025 Oğuzhan Topaloğlu
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



package com.twistral.tests.core.vectors;


import com.twistral.tephrium.core.functions.TMath;
import com.twistral.tephrium.core.vectors.TVec2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TVec2Test {

    @Test
    @DisplayName("cnsAndCopyTest")
    void cnsAndCopyTest() {
        TVec2 vec1 = new TVec2(3.0, 4.0);
        assertTrue(TMath.equalsd(3.0, vec1.getX()));
        assertTrue(TMath.equalsd(4.0, vec1.getY()));

        TVec2 vec2 = new TVec2();
        assertTrue(TMath.equalsd(0, vec2.getX()));
        assertTrue(TMath.equalsd(0, vec2.getY()));

        TVec2 vec3 = vec1.copy();
        assertTrue(TMath.equalsd(3.0, vec3.getX()));
        assertTrue(TMath.equalsd(4.0, vec3.getY()));
    }

    @Test
    @DisplayName("setGetTests")
    void setGetTests() {
        TVec2 vec = new TVec2();
        vec.setX(5.0).setY(10.5);
        assertTrue(TMath.equalsd(5, vec.getX()));
        assertTrue(TMath.equalsd(10.5, vec.getY()));
        vec.set(0.5, -5);
        assertTrue(TMath.equalsd(0.5, vec.getX()));
        assertTrue(TMath.equalsd(-5, vec.getY()));
    }

    @Test
    @DisplayName("arithmeticOperationsTest")
    void arithmeticOperationsTest() {
        TVec2 vec1 = new TVec2(3.0, 4.0);
        TVec2 vec2 = new TVec2(1.0, 2.0);

        vec1.add(vec2);
        assertTrue(TMath.equalsd(4, vec1.getX()));
        assertTrue(TMath.equalsd(6, vec1.getY()));

        vec1.sub(vec2);
        assertTrue(TMath.equalsd(3, vec1.getX()));
        assertTrue(TMath.equalsd(4, vec1.getY()));

        vec1.scale(2.0);
        assertTrue(TMath.equalsd(6, vec1.getX()));
        assertTrue(TMath.equalsd(8, vec1.getY()));

        vec1.set(3.0, 4.0);
        vec1.scaleX(2.0);
        assertTrue(TMath.equalsd(6.0, vec1.getX()));
        assertTrue(TMath.equalsd(4.0, vec1.getY()));

        vec1.set(3.0, 4.0);
        vec1.scaleY(2.5);
        assertTrue(TMath.equalsd(3.0, vec1.getX()));
        assertTrue(TMath.equalsd(10.0, vec1.getY()));

        vec1.set(3.0, 4.0);
        vec1.unit();
        assertTrue(TMath.equalsd(0.6, vec1.getX()));
        assertTrue(TMath.equalsd(0.8, vec1.getY()));
    }

    @Test
    @DisplayName("geometricOperationsTest")
    void geometricOperationsTest() {
        TVec2 vec = new TVec2();

        vec.set(1.5456, 2.345);
        vec.rotate90DegClockwise();
        assertTrue(TMath.equalsd(2.345, vec.getX()));
        assertTrue(TMath.equalsd(-1.5456, vec.getY()));

        vec.set(1.5456, 2.345);
        vec.rotate180DegClockwise();
        assertTrue(TMath.equalsd(-1.5456, vec.getX()));
        assertTrue(TMath.equalsd(-2.345, vec.getY()));

        vec.set(1.5456, 2.345);
        vec.rotate90DegAntiClockwise();
        assertTrue(TMath.equalsd(-2.345, vec.getX()));
        assertTrue(TMath.equalsd(1.5456, vec.getY()));

        vec.set(1.5456, 2.345);
        vec.symmetricalVectorTo(new TVec2(1.5456, 2.345));
        assertTrue(TMath.equalsd(1.5456, vec.getX()));
        assertTrue(TMath.equalsd(2.345, vec.getY()));
    }

    @Test
    @DisplayName("importantValueTest")
    void importantValueTest() {
        TVec2 vec1 = new TVec2(3.0, 4.0);
        TVec2 vec2 = new TVec2(1.0, 2.0);

        assertTrue(TMath.equalsd(11.0, TVec2.dot(vec1, vec2)));
        assertTrue(TMath.equalsd(25.0, vec1.lengthSquared()));
        assertTrue(TMath.equalsd(5.0, vec1.length()));
        assertTrue(TMath.equalsd(0.17985349979247847, TVec2.angleBetween(vec1, vec2)));

    }

    @Test
    @DisplayName("booleanFuncsTest")
    void booleanFuncsTest() {
        TVec2 vec1 = new TVec2(0.0, 0.0);
        TVec2 vec2 = new TVec2(0.0, 3.0);
        TVec2 vec3 = new TVec2(4.0, 0.0);

        assertTrue(vec1.isZeroVector());
        assertFalse(vec2.isZeroVector());
        assertFalse(vec1.isUnitVector());
        assertFalse(vec2.isUnitVector());
        assertTrue(TVec2.areOrthogonal(vec1, vec2, vec3));
        assertTrue(TVec2.areOrthogonal(vec1, vec1, vec1));
        assertFalse(TVec2.areOrthonormal(vec1, vec2, vec3));
        assertFalse(TVec2.areOrthonormal(vec1, vec1, vec1));
    }

    @Test
    @DisplayName("someOldTest2")
    void someOldTest2() {

        TVec2 p1 = new TVec2(0, 10);
        TVec2 p2 = new TVec2(0, -10);
        TVec2 p3 = new TVec2(0, 0);
        TVec2 p4 = new TVec2(0.5, -0.5);
        TVec2 p5 = new TVec2(0.5, -0.5);

        assertNotEquals(p1, p2);
        assertNotEquals(p1, p3);
        assertNotEquals(p1, p4);
        assertNotEquals(p1, p5);
        assertEquals(p4, p5);

        assertEquals(p1.copy().add(0, -10), p3);

        assertEquals(p1.length(), p2.length());
        assertEquals(p2.length(), 10);
        assertEquals(p3.length(), 0);
        assertTrue(TMath.equalsd(p4.length(), 1d / TMath.SQRT2));


        assertEquals(p1.copy().rotate90DegClockwise().rotate90DegClockwise(), p1.copy().rotate180DegClockwise());

        assertEquals(p1.copy().rotate90DegClockwise().rotate90DegClockwise().rotate90DegClockwise(),
                p1.copy().rotate90DegAntiClockwise());


        assertEquals(p2.copy().symmetricalVectorTo(p1), new TVec2(0, 30));
        assertEquals(p4.copy().scale(2d), new TVec2(1, -1));
    }


    @Test
    @DisplayName("someOldTest1")
    void someOldTest1() {
        TVec2 v1 = new TVec2();
        TVec2 v2 = new TVec2(1, 1);

        assertNotEquals(v1, v2);
        assertEquals(v1, v1.copy());
        assertEquals(v1, v1);

        assertTrue(TMath.equalsd(v1.getX(), 0d));
        assertTrue(TMath.equalsd(v1.setX(10d).getX(), 10d));
        assertTrue(TMath.equalsd(v1.setY(-10d).getY(), -10d));
        assertTrue(TMath.equalsd(v1.set(0d, 0d).getY(), 0d));

        assertEquals(v1.add(v2), v2);
        assertEquals(v1.sub(v2), new TVec2());
        assertEquals(v1.add(1d, 1d), v2);
        assertEquals(v1.sub(1d, 1d), new TVec2());
        assertEquals(v1.set(10d, 1d).scale(2d), new TVec2(20d, 2d));
        assertEquals(v1.set(10d, 1d).scale(2d, 20d), new TVec2(20d, 20d));
        assertEquals(v1.set(50d, 0d).unit(), new TVec2(1d, 0d));
        assertEquals(v1.set(1d, 1d).unit(), new TVec2(1d / TMath.SQRT2, 1d / TMath.SQRT2));

        v1.set(10d, 20d);
        v2.set(-2d, 2d);
        assertTrue(TMath.equalsd(v1.dot(v2), TVec2.dot(v2.getX(), v2.getY(), 10d, 20d)));
        assertTrue(TMath.equalsd(v1.dot(v2), -20d + 40d));
        assertTrue(TMath.equalsd(v1.lengthSquared(), 500d));
        assertTrue(TMath.equalsd(v1.length(), TMath.sqrt(500d)));

        v1.set(1d, 1d);
        assertEquals(v1.copy().rotate90DegClockwise(), new TVec2(1d, -1d));
        assertEquals(v1.copy().rotate180DegClockwise(), new TVec2(-1d, -1d));
        assertEquals(v1.copy().rotate90DegAntiClockwise(), new TVec2(-1d, 1d));
        assertEquals(v1.copy().symmetricalVectorTo(new TVec2()), new TVec2(-1d, -1d));

        v1.set(0d, 0d);
        assertTrue(v1.isZeroVector());
        v1.set(0.001d, 0d);
        assertFalse(v1.isZeroVector());

        v1.set(1d, 0d);
        assertTrue(v1.isUnitVector());
        v1.set(1d, 1d);
        assertFalse(v1.isUnitVector());


        assertTrue(TMath.equalsd(TVec2.angleBetween(new TVec2(1d, 1d), new TVec2(0d, 1d)),
                TMath.PI_OVER_TWO / 2d));
    }


}
