
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



package com.twistral.tests.core.functions;


import com.twistral.tephrium.core.functions.TMath;
import com.twistral.tephrium.core.functions.TRange;
import org.apache.commons.math3.util.Precision;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class TMathTest {

    @Test
    @DisplayName("equalsMethodsTest")
    void equalsMethodsTest() {
        // Doubles
        assertTrue(TMath.equalsd(0.1 + 0.2, 0.3));
        assertTrue(TMath.equalsd(0.1 + 0.2, 0.3 + 1e-11));
        assertTrue(TMath.equalsd(Double.MAX_VALUE, Double.MAX_VALUE));
        assertTrue(TMath.equalsd(Double.MIN_VALUE, Double.MIN_VALUE));
        assertTrue(TMath.equalsd(Double.MIN_VALUE, Double.longBitsToDouble(0x1)));
        assertTrue(TMath.equalsd(Double.longBitsToDouble(0x1), Double.MIN_VALUE));
        assertTrue(TMath.equalsd(Double.MAX_VALUE, Double.longBitsToDouble(0x7fefffffffffffffL)));
        assertTrue(TMath.equalsd(Double.longBitsToDouble(0x7fefffffffffffffL), Double.MAX_VALUE));
        assertTrue(TMath.equalsd(0.1 + 0.2, 0.3));
        assertFalse(TMath.equalsd(-0.1, 0.1));
        assertTrue(TMath.equalsd(Double.NaN, Double.NaN));
        assertTrue(TMath.equalsd(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
        assertTrue(TMath.equalsd(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY));
        assertFalse(TMath.equalsd(0.1 + 0.2, 0.4));
        assertFalse(TMath.equalsd(Double.MAX_VALUE, Double.MIN_VALUE));
        assertFalse(TMath.equalsd(Double.NaN, Double.POSITIVE_INFINITY));
        assertTrue(TMath.equalsd(1.0, 1.0));
        assertFalse(TMath.equalsd(1.0, 2.0));
        assertTrue(TMath.equalsd(Double.NaN, Double.NaN));
        assertFalse(TMath.equalsd(Double.NaN, 1.0));
        assertTrue(TMath.equalsd(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
        assertFalse(TMath.equalsd(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY));
        assertTrue(TMath.equalsd(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY));
        assertTrue(TMath.equalsd(-0.0, 0.0));
        assertTrue(TMath.equalsd(-0.0, -0.0));
        assertFalse(TMath.equalsd(-0.0, 1.0));

        // Floats
        assertTrue(TMath.equalsf(0.1f + 0.2f, 0.3f));
        assertTrue(TMath.equalsf(0.1f + 0.2f, 0.3f + 1e-11f));
        assertTrue(TMath.equalsf(Float.MAX_VALUE, Float.MAX_VALUE));
        assertTrue(TMath.equalsf(Float.MIN_VALUE, Float.MIN_VALUE));
        assertTrue(TMath.equalsf(Float.MIN_VALUE, Float.intBitsToFloat(0x1)));
        assertTrue(TMath.equalsf(Float.intBitsToFloat(0x1), Float.MIN_VALUE));
        assertTrue(TMath.equalsf(Float.MAX_VALUE, Float.intBitsToFloat(0x7f7fffff)));
        assertTrue(TMath.equalsf(Float.intBitsToFloat(0x7f7fffff), Float.MAX_VALUE));
        assertTrue(TMath.equalsf(0.1f + 0.2f, 0.3f));
        assertFalse(TMath.equalsf(-0.1f, 0.1f));
        assertFalse(TMath.equalsf(0.1f + 0.2f, 0.4f));
        assertFalse(TMath.equalsf(Float.MAX_VALUE, Float.MIN_VALUE));
        assertTrue(TMath.equalsf(1.0f, 1.0f));
        assertFalse(TMath.equalsf(1.0f, 2.0f));
        assertTrue(TMath.equalsf(Float.NaN, Float.NaN));
        assertFalse(TMath.equalsf(Float.NaN, 1.0f));
        assertTrue(TMath.equalsf(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY));
        assertFalse(TMath.equalsf(Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY));
        assertTrue(TMath.equalsf(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY));
        assertTrue(TMath.equalsf(-0.0f, 0.0f));
        assertTrue(TMath.equalsf(-0.0f, -0.0f));
        assertFalse(TMath.equalsf(-0.0f, 1.0f));

    }


    @Test
    @DisplayName("equalsFailWithApacheTest")
    void equalsFailWithApacheTest() {
        // The following values wont work properly since they're way too small
        // This test basically checks that, even though they dont work they still
        // get the same result as Apache Commons Math :P

        cmpfWithApache(Float.MAX_VALUE, Float.intBitsToFloat(0x7f7ffffe));
        cmpfWithApache(Float.MIN_VALUE, 0.0f);
        cmpfWithApache(0.0f, 0.0f + Float.MIN_VALUE);
        cmpfWithApache(Float.intBitsToFloat(0x1), Float.intBitsToFloat(0x2));

        cmpdWithApache(Double.MAX_VALUE, Double.longBitsToDouble(0x7feffffffffffffeL));
        cmpdWithApache(Double.MIN_VALUE, 0.0d);
        cmpdWithApache(0.0d, 0.0d + Double.MIN_VALUE);
        cmpdWithApache(Double.longBitsToDouble(0x1), Double.longBitsToDouble(0x2));
    }


    @Test
    @DisplayName("veryBasicMathFuncsTest")
    void veryBasicMathFuncsTest() {
        // MIN
        assertEquals(2, TMath.min(2, 3));
        assertEquals(-5L, TMath.min(-5L, 10L));
        assertTrue(TMath.equalsd(2.5d, TMath.min(2.5d, 3.7d)));
        assertTrue(TMath.equalsf(2.5f, TMath.min(2.5f, 3.7f)));
        assertTrue(TMath.equalsf(2.5f, TMath.min(2.5f, 3.7f, 4.8f)));
        assertTrue(TMath.equalsd(2.5d, TMath.min(2.5d, 3.7d, 4.8d)));
        assertEquals(2, TMath.min(2, 3, 4));
        assertEquals(2L, TMath.min(2L, 3L, 4L));

        // MAX
        assertEquals(3, TMath.max(2, 3));
        assertEquals(10L, TMath.max(-5L, 10L));
        assertTrue(TMath.equalsd(3.7d, TMath.max(2.5d, 3.7d)));
        assertTrue(TMath.equalsf(3.7f, TMath.max(2.5f, 3.7f)));
        assertTrue(TMath.equalsf(4.8f, TMath.max(2.5f, 3.7f, 4.8f)));
        assertTrue(TMath.equalsd(4.8d, TMath.max(2.5d, 3.7d, 4.8d)));
        assertEquals(4, TMath.max(2, 3, 4));
        assertEquals(4L, TMath.max(2L, 3L, 4L));

        // ABS
        assertTrue(TMath.equalsd(5.5d, TMath.abs(-5.5d)));
        assertTrue(TMath.equalsd(10.8d, TMath.abs(10.8d)));
        assertTrue(TMath.equalsf(5.5f, TMath.abs(-5.5f)));
        assertTrue(TMath.equalsf(10.8f, TMath.abs(10.8f)));
        assertEquals(5, TMath.abs(-5));
        assertEquals(10, TMath.abs(10));
        assertEquals(5L, TMath.abs(-5L));
        assertEquals(10L, TMath.abs(10L));

        // SUM
        assertTrue(TMath.equalsd(10.8d, TMath.sum(2.5d, 3.7d, 4.6d)));
        assertTrue(TMath.equalsd(25.3d, TMath.sum(-5.5d, 10.8d, 20.0d)));
        assertTrue(TMath.equalsf(10.1f, TMath.sum(2f, 3.1f, 5f)));
        assertTrue(TMath.equalsf(24.1f, TMath.sum(-5.9f, 10f, 20f)));
        assertEquals(10L, TMath.sum(2L, 3L, 5L));
        assertEquals(25L, TMath.sum(-5L, 10L, 20L));
        assertEquals(10, TMath.sum(2, 3, 5));
        assertEquals(15, TMath.sum(-5, 0, 20));

        // CLAMP
        assertEquals(3L, TMath.clamp(2L, 3L, 5L));
        assertEquals(10L, TMath.clamp(15L, 3L, 10L));
        assertEquals(5L, TMath.clamp(5L, 3L, 10L));
        assertEquals(3, TMath.clamp(2, 3, 5));
        assertEquals(10, TMath.clamp(15, 3, 10));
        assertEquals(5, TMath.clamp(5, 3, 10));
        assertTrue(TMath.equalsd(3.5d, TMath.clamp(2.5d, 3.5d, 5.5d)));
        assertTrue(TMath.equalsd(10.8d, TMath.clamp(15.8d, 3.6d, 10.8d)));
        assertTrue(TMath.equalsd(5.5d, TMath.clamp(5.5d, 3.5d, 10.5d)));
        assertTrue(TMath.equalsf(3.5f, TMath.clamp(2.5f, 3.5f, 5.5f)));
        assertTrue(TMath.equalsf(10.8f, TMath.clamp(15.8f, 3.6f, 10.8f)));
        assertTrue(TMath.equalsf(5.5f, TMath.clamp(5.5f, 3.5f, 10.5f)));
    }


    @Test
    @DisplayName("floorFastTest")
    void floorFastTest() {
        for (int i = 0; i < 100; i++) {
            double x = (Math.random() - 0.5d) * 500_000;
            assertEquals(Math.floor(x), TMath.floorFast(x));
        }
    }


    @Test
    @DisplayName("rangeMappingTest")
    void rangeMappingTest() {
        assertTrue(TMath.equalsd(50.0d, TMath.mapRange(0.0d, 10.0d, 0.0d, 100.0d, 5.0d)));
        assertTrue(TMath.equalsd(50.0d, TMath.mapRange(-10.0d, 10.0d, 0.0d, 100.0d, 0.0d)));
        assertTrue(TMath.equalsd(100.0d, TMath.mapRange(-10.0d, 10.0d, 0.0d, 100.0d, 10.0d)));
        assertTrue(TMath.equalsd(50.0d, TMath.mapRange(-5.0d, 5.0d, 0.0d, 100.0d, 0.0d)));
        assertTrue(TMath.equalsd(0.0d, TMath.mapRange(-1.0d, 1.0d, 0.0d, 1.0d, -1.0d)));
        assertTrue(TMath.equalsd(1.0d, TMath.mapRange(-1.0d, 1.0d, 0.0d, 1.0d, 1.0d)));

        TRange oldRange = new TRange(-10.0d, 10.0d);
        TRange newRange = new TRange(0.0d, 100.0d);
        assertTrue(TMath.equalsd(75.0d, TMath.mapRange(oldRange, newRange, 5.0d)));
        assertTrue(TMath.equalsd(50.0d, TMath.mapRange(oldRange, newRange, 0.0d)));
        assertTrue(TMath.equalsd(100.0d, TMath.mapRange(oldRange, newRange, 10.0d)));
        assertTrue(TMath.equalsd(50d, TMath.mapRange(new TRange(-5.0d, 5.0d), new TRange(0.0d, 100.0d), 0.0d)));

        assertTrue(TMath.equalsd(0.0d, TMath.mapRange(-1.0d)));
        assertTrue(TMath.equalsd(0.5d, TMath.mapRange(0.0d)));
        assertTrue(TMath.equalsd(1.0d, TMath.mapRange(1.0d)));
    }



    private static void cmpfWithApache(float a, float b) {
        if(TMath.equalsf(a, b) != Precision.equals(a, b))
            fail(String.format("NOT THE SAME FOR a=%f, b=%f\n", a, b));
    }

    private static void cmpdWithApache(double a, double b) {
        if(TMath.equalsd(a, b) != Precision.equals(a, b))
            fail(String.format("NOT THE SAME FOR a=%f, b=%f\n", a, b));
    }


}
