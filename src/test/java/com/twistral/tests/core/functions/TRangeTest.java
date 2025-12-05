
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


import com.twistral.tephrium.core.TephriumException;
import com.twistral.tephrium.core.functions.TMath;
import com.twistral.tephrium.core.functions.TRange;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TRangeTest {


    @Test
    @DisplayName("cnsAndEquals")
    void cnsAndEquals() {
        TRange r1 = new TRange(0, 10);
        TRange r2 = new TRange(0, 10);
        TRange r3 = new TRange(-1, 10);

        assertThrows(TephriumException.class, () -> new TRange(0, 0)   );
        assertThrows(TephriumException.class, () -> new TRange(10, 0)  );
        assertThrows(TephriumException.class, () -> new TRange(-1, -1) );
        assertThrows(TephriumException.class, () -> new TRange(1, 1)   );
        assertDoesNotThrow(() -> { new TRange(0, 0.000000000000001); });

        assertEquals(r1, r2);
        assertNotEquals(r1, r3);
    }


    @Test
    @DisplayName("otherMethodsTest")
    void otherMethodsTest() {
        TRange range = new TRange(5, 100);
        TRange biggerRange = new TRange(-5, 100);
        assertEquals(TRange.getBiggerRange(range, biggerRange), biggerRange);

        range = new TRange(2, 4);
        assertTrue(range.isInRange(3));
        assertFalse(range.isInRange(5));

        range = new TRange(2, 5);
        assertTrue(TMath.equalsd(3, range.size()));

        range = new TRange(1, 3);
        TRange scaledRange = range.scale(2);
        assertTrue(TMath.equalsd(2, scaledRange.left));
        assertTrue(TMath.equalsd(6, scaledRange.right));

        range = new TRange(0, 4);
        TRange sqrtRange = range.sqrt();
        assertTrue(TMath.equalsd(0, sqrtRange.left));
        assertTrue(TMath.equalsd(2, sqrtRange.right));

        range = new TRange(2, 4);
        TRange shiftedRange = range.shift(3);
        assertTrue(TMath.equalsd(5, shiftedRange.left));
        assertTrue(TMath.equalsd(7, shiftedRange.right));
    }




}
