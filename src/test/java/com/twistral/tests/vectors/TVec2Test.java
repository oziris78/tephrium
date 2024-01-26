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


package com.twistral.tests.vectors;

import com.twistral.tephrium.core.functions.TMath;
import com.twistral.tephrium.core.vectors.TVec2;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TVec2Test {

    @Test
    @DisplayName("tvec2dTest")
    void tvec2DTest() {
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



    @Test
    @DisplayName("oldPoint2dTest")
    void oldPoint2dTest() {
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



}
