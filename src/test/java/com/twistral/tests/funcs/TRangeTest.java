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


package com.twistral.tests.funcs;

import com.twistral.tephrium.core.TephriumException;
import com.twistral.tephrium.core.functions.TRange;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TRangeTest {

    @Test
    @DisplayName("rangeTest")
    void rangeTest() {
        TRange r1 = new TRange(0, 10);
        TRange r2 = new TRange(0, 10);
        TRange r3 = new TRange(-1, 10);

        assertThrows(TephriumException.class, () -> {
            new TRange(0, 0);
        });
        assertThrows(TephriumException.class, () -> {
            new TRange(10, 0);
        });
        assertThrows(TephriumException.class, () -> {
            new TRange(-1, -1);
        });
        assertThrows(TephriumException.class, () -> {
            new TRange(1, 1);
        });
        assertDoesNotThrow(() -> {
            new TRange(0, 0.000000000000001);
        });

        assertEquals(r1, r2);
        assertNotEquals(r1, r3);
    }
}
