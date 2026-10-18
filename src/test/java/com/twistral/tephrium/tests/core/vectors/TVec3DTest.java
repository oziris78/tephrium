
// Copyright 2024-2026 Oğuzhan Topaloğlu
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



package com.twistral.tephrium.tests.core.vectors;


import com.twistral.tephrium.TMath;
import com.twistral.tephrium.vectors.TVec3d;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TVec3DTest {


    @Test
    @DisplayName("newTest")
    void newTest() {
        TVec3d vec = new TVec3d(1.5456, 2.345, 3.456);
        TVec3d copy = vec.copy();

        assertTrue(TMath.equals(1.5456, copy.getX()));
        assertTrue(TMath.equals(2.345, copy.getY()));
        assertTrue(TMath.equals(3.456, copy.getZ()));

        vec.set(1.5456, 2.345, 3.456);
        assertTrue(TMath.equals(1.5456, vec.getX()));
        assertTrue(TMath.equals(2.345, vec.getY()));
        assertTrue(TMath.equals(3.456, vec.getZ()));

        vec.setX(1.5456).setY(2.345).setZ(3.456);
        vec.add(1.3, 2.0, 3.0);

        assertTrue(TMath.equals(2.8456, vec.getX()));
        assertTrue(TMath.equals(4.345, vec.getY()));
        assertTrue(TMath.equals(6.456, vec.getZ()));

        vec.set(5d, 7d, 6d);
        vec.scaleX(1d/5d);
        vec.scaleY(2d);
        vec.scaleZ(1d/3d);
        assertTrue(TMath.equals(vec.getX(), 1d));
        assertTrue(TMath.equals(vec.getY(), 14d));
        assertTrue(TMath.equals(vec.getZ(), 2d));


        assertTrue(TMath.equals(TVec3d.dot(2, 0, 5, 0, 6, -0.5), -2.5d));
        assertTrue(TMath.equals(TVec3d.dot(new TVec3d(2, 0, 5), new TVec3d(1, 6, -0.5)), -0.5d));

        TVec3d output = new TVec3d();
        TVec3d.cross(output, 50d, 12d, -560d, 84d, 4d, 6101d);
        assertTrue(TMath.equals(output.getX(), 75452d));
        assertTrue(TMath.equals(output.getY(), -352090d));
        assertTrue(TMath.equals(output.getZ(), -808d));


        vec.set(2.5, 3, 5);
        TVec3d vec2 = new TVec3d(10, 5, -1);
        assertTrue(TMath.equals(vec.dot(vec2), 35));
        assertTrue(TMath.equals(vec.dot(0, 5, -1), 10));

        assertTrue(TMath.equals(TVec3d.lengthSquared(new TVec3d(2.5, 1.5, 3.5)), 20.75d));
        assertTrue(TMath.equals(new TVec3d(2.5, 1.5, 3.5).length(), TMath.sqrt(20.75d)));
        assertTrue(TMath.equals(TVec3d.length(2.5, 1.5, 3.5), TMath.sqrt(20.75d)));
        assertTrue(TMath.equals(TVec3d.length(new TVec3d(2.5, 1.5, 3.5)), TMath.sqrt(20.75d)));

        vec = new TVec3d(1.1, 0.0, 0.0);
        vec2 = new TVec3d(0.0, 1.1, 0.0);
        assertTrue(TMath.equals(TVec3d.angleBetween(vec, vec2), TMath.PI_OVER_TWO));

        assertTrue(TVec3d.areOrthonormal(
                new TVec3d(1516156, 0, 0).unit(),
                new TVec3d(0, 987891, 0).unit(),
                new TVec3d(0, 0, -12556151).unit()
        ));
        assertTrue(TVec3d.areOrthonormal(
                new TVec3d(1, 0, 0),
                new TVec3d(0, 1, 0),
                new TVec3d(0, 0, 1)
        ));

    }


    @Test
    @DisplayName("someOldTest")
    void someOldTest() {
        TVec3d v1 = new TVec3d();
        TVec3d v2 = new TVec3d(10d, 20d, 30d);

        assertEquals(v1, v1);
        assertEquals(v1, v1.copy());
        assertEquals(v1, new TVec3d());
        assertNotEquals(v1, v2);

        assertEquals(v1.set(10d, 20d, 30d), v2);
        assertEquals(v1.setX(-10d).getX(), -10d);
        assertEquals(v1.setY(-20d).getY(), -20d);
        assertEquals(v1.setZ(-30d).getZ(), -30d);

        assertEquals(v1.set(10d, 20d, 30d).sub(v2), v2.add(-10d, -20d, -30d));
        assertEquals(v1.set(0d, 0d, 0d).sub(10d, 0d, 0d), v2.set(-5d, 0d, 0d).add(new TVec3d(-5d, 0d, 0d)));

        assertEquals(v1.set(1d, 2d, 3d).scale(10d), new TVec3d(10d, 20d, 30d));
        assertEquals(v1.set(1d, 2d, 3d).scale(10d, 0d, 2d), new TVec3d(10d, 0d, 6d));

        assertEquals(v1.set(0d, 3d, 4d).unit(), new TVec3d(0d, 3d/5d, 4d/5d));

        v1.set(50d, 12d, -560d);
        v2.set(84d, 4d, 6101d);
        assertEquals(v1.cross(v2), new TVec3d(75452d, -352090d, -808d));

        v1.set(0d, 1d, -2d);
        v2.set(10d, 20d, 30d);
        assertTrue(TMath.equals(v1.dot(v2), 20d - 60d));
        assertTrue(TMath.equals(v1.dot(0d, 0d, 1d), -2d));

        v1.set(0d, 1d, -2d);
        assertTrue(TMath.equals(v1.lengthSquared(), v1.length() * v1.length()));
        assertTrue(TMath.equals(TMath.SQRT5, v1.length()));

        assertEquals(new TVec3d(1, 1, 1).length(), TMath.SQRT3);
        assertEquals(new TVec3d(2, 2, 1).length(), 3);
        assertEquals(v1.set(0, 2, 7).length(), Math.sqrt(53));

        assertFalse(new TVec3d(0, 1, -1).isUnitVector());
        assertFalse(new TVec3d(0, 0, 0).isUnitVector());
        assertFalse(new TVec3d(0, 2, 7).isUnitVector());
        assertTrue(new TVec3d(0, 1, 0).isUnitVector());

        assertFalse(new TVec3d(0, 1, -1).isZeroVector());
        assertTrue(new TVec3d(0, 0, 0).isZeroVector());
        assertFalse(new TVec3d(0, 2, 7).isZeroVector());
    }

}
