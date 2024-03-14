
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



package com.twistral.tests.pairs;

import com.twistral.tephrium.fwg.GibberishFWG;
import com.twistral.tephrium.pairs.ObjPair;
import com.twistral.tephrium.prng.SplitMix64Random;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class ObjPairTest {


    @Test
    public void objMethodsTest() {
        String str = new GibberishFWG().generateFakeWord(10);
        int i = new SplitMix64Random().nextInt();

        ObjPair<Integer, String> pair0, pair1, pair2, pair3, pair4, pair5;

        pair0 = new ObjPair<>(i, str);
        assertEquals("("+ i + ", " + str + ")", pair0.toString());

        pair1 = new ObjPair<>(i, str);
        pair2 = new ObjPair<>(i, str);
        pair3 = new ObjPair<>(i*2 + 1, str);

        assertEquals(pair1, pair2);
        assertNotEquals(pair1, pair3);

        pair4 = new ObjPair<>(i, str);
        pair5 = new ObjPair<>(i, str);

        assertEquals(pair4.hashCode(), pair5.hashCode());
    }


}
