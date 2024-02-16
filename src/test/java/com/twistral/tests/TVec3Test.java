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


package com.twistral.tests;

import com.twistral.tephrium.core.functions.TMath;
import com.twistral.tephrium.core.vectors.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TVec3Test {

    @Test
    @DisplayName("tvec3dTest")
    void tvec3DTest() {
        TVec3 v1 = new TVec3();
        TVec3 v2 = new TVec3(10d, 20d, 30d);

        assertEquals(v1, v1);
        assertEquals(v1, v1.copy());
        assertEquals(v1, new TVec3());
        assertNotEquals(v1, v2);

        assertEquals(v1.set(10d, 20d, 30d), v2);
        assertEquals(v1.setX(-10d).getX(), -10d);
        assertEquals(v1.setY(-20d).getY(), -20d);
        assertEquals(v1.setZ(-30d).getZ(), -30d);

        assertEquals(v1.set(10d, 20d, 30d).sub(v2), v2.add(-10d, -20d, -30d));
        assertEquals(v1.set(0d, 0d, 0d).sub(10d, 0d, 0d), v2.set(-5d, 0d, 0d).add(new TVec3(-5d, 0d, 0d)));

        assertEquals(v1.set(1d, 2d, 3d).scale(10d), new TVec3(10d, 20d, 30d));
        assertEquals(v1.set(1d, 2d, 3d).scale(10d, 0d, 2d), new TVec3(10d, 0d, 6d));

        assertEquals(v1.set(0d, 3d, 4d).unit(), new TVec3(0d, 3d/5d, 4d/5d));

        v1.set(50d, 12d, -560d);
        v2.set(84d, 4d, 6101d);
        assertEquals(v1.cross(v2), new TVec3(75452d, -352090d, -808d));

        v1.set(0d, 1d, -2d);
        v2.set(10d, 20d, 30d);
        assertTrue(TMath.equalsd(v1.dot(v2), 20d - 60d));
        assertTrue(TMath.equalsd(v1.dot(0d, 0d, 1d), -2d));

        v1.set(0d, 1d, -2d);
        assertTrue(TMath.equalsd(v1.lengthSquared(), v1.length() * v1.length()));
        assertTrue(TMath.equalsd(TMath.SQRT5, v1.length()));

        assertEquals(new TVec3(1, 1, 1).length(), TMath.SQRT3);
        assertEquals(new TVec3(2, 2, 1).length(), 3);
        assertEquals(v1.set(0, 2, 7).length(), Math.sqrt(53));

        assertFalse(new TVec3(0, 1, -1).isUnitVector());
        assertFalse(new TVec3(0, 0, 0).isUnitVector());
        assertFalse(new TVec3(0, 2, 7).isUnitVector());
        assertTrue(new TVec3(0, 1, 0).isUnitVector());

        assertFalse(new TVec3(0, 1, -1).isZeroVector());
        assertTrue(new TVec3(0, 0, 0).isZeroVector());
        assertFalse(new TVec3(0, 2, 7).isZeroVector());

    }



}
