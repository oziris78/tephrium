// Copyright 2024 Oğuzhan Topaloğlu
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
import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.math3.util.Precision;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class TMathAreEqualsTest {

    @Test
    @DisplayName("testEqualsFloatingTypes")
    void testEqualsFloatingTypes() {
        // Doubles
        assertTrue(TMath.areEqual(0.1 + 0.2, 0.3));
        assertTrue(TMath.areEqual(0.1 + 0.2, 0.3 + 1e-11));
        assertTrue(TMath.areEqual(Double.MAX_VALUE, Double.MAX_VALUE));
        assertTrue(TMath.areEqual(Double.MIN_VALUE, Double.MIN_VALUE));
        assertTrue(TMath.areEqual(Double.MIN_VALUE, Double.longBitsToDouble(0x1)));
        assertTrue(TMath.areEqual(Double.longBitsToDouble(0x1), Double.MIN_VALUE));
        assertTrue(TMath.areEqual(Double.MAX_VALUE, Double.longBitsToDouble(0x7fefffffffffffffL)));
        assertTrue(TMath.areEqual(Double.longBitsToDouble(0x7fefffffffffffffL), Double.MAX_VALUE));
        assertTrue(TMath.areEqual(0.1 + 0.2, 0.3));
        assertFalse(TMath.areEqual(-0.1, 0.1));
        assertTrue(TMath.areEqual(Double.NaN, Double.NaN));
        assertTrue(TMath.areEqual(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
        assertTrue(TMath.areEqual(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY));
        assertFalse(TMath.areEqual(0.1 + 0.2, 0.4));
        assertFalse(TMath.areEqual(Double.MAX_VALUE, Double.MIN_VALUE));
        assertFalse(TMath.areEqual(Double.NaN, Double.POSITIVE_INFINITY));
        assertTrue(TMath.areEqual(1.0, 1.0));
        assertFalse(TMath.areEqual(1.0, 2.0));
        assertTrue(TMath.areEqual(Double.NaN, Double.NaN));
        assertFalse(TMath.areEqual(Double.NaN, 1.0));
        assertTrue(TMath.areEqual(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
        assertFalse(TMath.areEqual(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY));
        assertTrue(TMath.areEqual(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY));
        assertTrue(TMath.areEqual(-0.0, 0.0));
        assertTrue(TMath.areEqual(-0.0, -0.0));
        assertFalse(TMath.areEqual(-0.0, 1.0));

        // Floats
        assertTrue(TMath.areEqual(0.1f + 0.2f, 0.3f));
        assertTrue(TMath.areEqual(0.1f + 0.2f, 0.3f + 1e-11f));
        assertTrue(TMath.areEqual(Float.MAX_VALUE, Float.MAX_VALUE));
        assertTrue(TMath.areEqual(Float.MIN_VALUE, Float.MIN_VALUE));
        assertTrue(TMath.areEqual(Float.MIN_VALUE, Float.intBitsToFloat(0x1)));
        assertTrue(TMath.areEqual(Float.intBitsToFloat(0x1), Float.MIN_VALUE));
        assertTrue(TMath.areEqual(Float.MAX_VALUE, Float.intBitsToFloat(0x7f7fffff)));
        assertTrue(TMath.areEqual(Float.intBitsToFloat(0x7f7fffff), Float.MAX_VALUE));
        assertTrue(TMath.areEqual(0.1f + 0.2f, 0.3f));
        assertFalse(TMath.areEqual(-0.1f, 0.1f));
        assertFalse(TMath.areEqual(0.1f + 0.2f, 0.4f));
        assertFalse(TMath.areEqual(Float.MAX_VALUE, Float.MIN_VALUE));
        assertTrue(TMath.areEqual(1.0f, 1.0f));
        assertFalse(TMath.areEqual(1.0f, 2.0f));
        assertTrue(TMath.areEqual(Float.NaN, Float.NaN));
        assertFalse(TMath.areEqual(Float.NaN, 1.0f));
        assertTrue(TMath.areEqual(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY));
        assertFalse(TMath.areEqual(Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY));
        assertTrue(TMath.areEqual(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY));
        assertTrue(TMath.areEqual(-0.0f, 0.0f));
        assertTrue(TMath.areEqual(-0.0f, -0.0f));
        assertFalse(TMath.areEqual(-0.0f, 1.0f));

    }

    @Test
    @DisplayName("testEqualsNonFloatingTypes")
    void testEqualsNonFloatingTypes() {
        assertTrue(TMath.areEqual(50, 50));
        assertTrue(TMath.areEqual(50, 10 + 40));
        assertTrue(TMath.areEqual(20 + 30, 10 + 40));
        assertTrue(TMath.areEqual(Integer.MAX_VALUE, Integer.MAX_VALUE));
        assertTrue(TMath.areEqual(Integer.MIN_VALUE, Integer.MIN_VALUE));
        assertTrue(TMath.areEqual(Integer.MIN_VALUE, 0x80000000));
        assertTrue(TMath.areEqual(0x80000000, Integer.MIN_VALUE));
        assertTrue(TMath.areEqual(Integer.MAX_VALUE, 0x7fffffff));
        assertTrue(TMath.areEqual(0x7fffffff, Integer.MAX_VALUE));
        assertTrue(TMath.areEqual(50, 50));
        assertFalse(TMath.areEqual(-50, 50));
        assertTrue(TMath.areEqual(0, -0));

        assertTrue(TMath.areEqual((byte) 5, (byte) 5));
        assertTrue(TMath.areEqual((byte) 5, (byte) (2 + 3)));
        assertTrue(TMath.areEqual((byte) 20, (byte) (10 + 10)));
        assertTrue(TMath.areEqual(Byte.MAX_VALUE, Byte.MAX_VALUE));
        assertTrue(TMath.areEqual(Byte.MIN_VALUE, Byte.MIN_VALUE));
        assertTrue(TMath.areEqual(Byte.MIN_VALUE, (byte) 0x80));
        assertTrue(TMath.areEqual((byte) 0x80, Byte.MIN_VALUE));
        assertTrue(TMath.areEqual(Byte.MAX_VALUE, (byte) 0x7f));
        assertTrue(TMath.areEqual((byte) 0x7f, Byte.MAX_VALUE));
        assertTrue(TMath.areEqual((byte) 5, (byte) 5));
        assertFalse(TMath.areEqual((byte) -5, (byte) 5));
        assertTrue(TMath.areEqual((byte) 0, (byte) -0));

        assertTrue(TMath.areEqual('A', 'A'));
        assertFalse(TMath.areEqual('A', 'A' - 32));
        assertTrue(TMath.areEqual('a' + 1, 'a' + 2 - 1));
        assertTrue(TMath.areEqual(Character.MAX_VALUE, Character.MAX_VALUE));
        assertTrue(TMath.areEqual(Character.MIN_VALUE, Character.MIN_VALUE));
        assertTrue(TMath.areEqual(Character.MIN_VALUE, 0));
        assertTrue(TMath.areEqual(0, Character.MIN_VALUE));
        assertTrue(TMath.areEqual(Character.MAX_VALUE, 0xffff));
        assertTrue(TMath.areEqual(0xffff, Character.MAX_VALUE));
        assertTrue(TMath.areEqual('A', 'A'));
        assertFalse(TMath.areEqual('A', 'a'));

        assertTrue(TMath.areEqual(50L, 50L));
        assertTrue(TMath.areEqual(50L, 10L + 40L));
        assertTrue(TMath.areEqual(20L + 30L, 10L + 40L));
        assertTrue(TMath.areEqual(Long.MAX_VALUE, Long.MAX_VALUE));
        assertTrue(TMath.areEqual(Long.MIN_VALUE, Long.MIN_VALUE));
        assertTrue(TMath.areEqual(Long.MIN_VALUE, 0x8000000000000000L));
        assertTrue(TMath.areEqual(0x8000000000000000L, Long.MIN_VALUE));
        assertTrue(TMath.areEqual(Long.MAX_VALUE, 0x7fffffffffffffffL));
        assertTrue(TMath.areEqual(0x7fffffffffffffffL, Long.MAX_VALUE));
        assertTrue(TMath.areEqual(50L, 50L));
        assertFalse(TMath.areEqual(-50L, 50L));
        assertTrue(TMath.areEqual(0L, -0L));

        assertTrue(TMath.areEqual((short) 5, (short) 5));
        assertTrue(TMath.areEqual((short) 5, (short) (2 + 3)));
        assertTrue(TMath.areEqual((short) 20, (short) (10 + 10)));
        assertTrue(TMath.areEqual(Short.MAX_VALUE, Short.MAX_VALUE));
        assertTrue(TMath.areEqual(Short.MIN_VALUE, Short.MIN_VALUE));
        assertTrue(TMath.areEqual(Short.MIN_VALUE, (short) 0x8000));
        assertTrue(TMath.areEqual((short) 0x8000, Short.MIN_VALUE));
        assertTrue(TMath.areEqual(Short.MAX_VALUE, (short) 0x7fff));
        assertTrue(TMath.areEqual((short) 0x7fff, Short.MAX_VALUE));
        assertTrue(TMath.areEqual((short) 5, (short) 5));
        assertFalse(TMath.areEqual((short) -5, (short) 5));
        assertTrue(TMath.areEqual((short) 0, (short) -0));
    }

    @Test
    @DisplayName("wontWorkButSoWontApacheCommons")
    void wontWorkButSoWontApacheCommons() {
        // The following values wont work properly since they're way too small
        // This test basically checks that, even though they dont work they still
        // get the same result as Apache Commons Math :P

        cmpEqFloatApache(Float.MAX_VALUE, Float.intBitsToFloat(0x7f7ffffe));
        cmpEqFloatApache(Float.MIN_VALUE, 0.0f);
        cmpEqFloatApache(0.0f, 0.0f + Float.MIN_VALUE);
        cmpEqFloatApache(Float.intBitsToFloat(0x1), Float.intBitsToFloat(0x2));

        cmpEqDoubleApache(Double.MAX_VALUE, Double.longBitsToDouble(0x7feffffffffffffeL));
        cmpEqDoubleApache(Double.MIN_VALUE, 0.0d);
        cmpEqDoubleApache(0.0d, 0.0d + Double.MIN_VALUE);
        cmpEqDoubleApache(Double.longBitsToDouble(0x1), Double.longBitsToDouble(0x2));
    }

    private static void cmpEqFloatApache(float a, float b) {
        if(TMath.areEqual(a, b) != Precision.equals(a, b))
            fail(String.format("NOT THE SAME FOR a=%f, b=%f\n", a, b));
    }

    private static void cmpEqDoubleApache(double a, double b) {
        if(TMath.areEqual(a, b) != Precision.equals(a, b))
            fail(String.format("NOT THE SAME FOR a=%f, b=%f\n", a, b));
    }


}
