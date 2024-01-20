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
        if(TMath.equalsf(a, b) != Precision.equals(a, b))
            fail(String.format("NOT THE SAME FOR a=%f, b=%f\n", a, b));
    }

    private static void cmpEqDoubleApache(double a, double b) {
        if(TMath.equalsd(a, b) != Precision.equals(a, b))
            fail(String.format("NOT THE SAME FOR a=%f, b=%f\n", a, b));
    }


}
