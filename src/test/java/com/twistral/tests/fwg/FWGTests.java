
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



package com.twistral.tests.fwg;


import com.twistral.tephrium.fwg.ChunkifiedFWG;
import com.twistral.tephrium.fwg.EnglishCorpus;
import com.twistral.tephrium.fwg.FrommatFWG;
import com.twistral.tephrium.fwg.GibberishFWG;
import com.twistral.tephrium.prng.JavaSplittableRandom;
import com.twistral.tephrium.prng.SplitMix64Random;
import com.twistral.tephrium.strings.TStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class FWGTests {


    @Test
    @DisplayName("englishCorpusTest")
    void englishCorpusTest() {
        SplitMix64Random rand = new SplitMix64Random();
        final char[] chrarr = TStringUtils.CS_ASCII_LOWER.toCharArray();
        for (char c : chrarr)
            for (int i = 0; i < 100_000; i++)
                assertEquals(EnglishCorpus.randomWordStartingWith(c, rand).charAt(0), c);

        for (int i = 0; i < 100_000; i++)
            assertDoesNotThrow(() -> EnglishCorpus.randomWord(rand));

    }


    @Test
    @DisplayName("chunkifiedFWGTest")
    void chunkifiedFwgTest() {
        ChunkifiedFWG fwg = new ChunkifiedFWG();
        SplitMix64Random rand = new SplitMix64Random();

        for (int i = 0; i < 500_000; i++) {
            final int len = rand.nextInt(3, 30);
            String fw = fwg.generateFakeWord(len);
            // The default corpus must always be large enough!
            assertNotEquals(fw, null);
            // The given length must be correct
            assertEquals(fw.length(), len);
        }

        final char[] chrarr = TStringUtils.CS_ASCII_LOWER.toCharArray();
        for(char c : chrarr) {
            for (int i = 0; i < 20_000; i++) {
                final int len = rand.nextInt(3, 30);
                assertTrue(fwg.generateFakeWord(len, c).startsWith(String.valueOf(c)));
            }
        }
    }


    @Test
    @DisplayName("chunkifiedFWGTest2")
    void chunkifiedFwgTest2() {
        String[] words = new String[] {
                "aXbc", "aexa", "akgx", "akex", "aaax", "  alkdfghklandf", " addefkoe"
        };

        String bookContent = "aXbc aexa akgx akex !!aaax!! alkdfghklandf addefkoe 168484651651123651126531###@!!'";

        ChunkifiedFWG[] fwgs = new ChunkifiedFWG[] {
                new ChunkifiedFWG(ChunkifiedFWG.freqFromWords(Arrays.stream(words))),
                new ChunkifiedFWG(ChunkifiedFWG.freqFromBookContent(bookContent),
                        new JavaSplittableRandom(), 30)
        };

        assertEquals(fwgs[1].getRewindLimit(), 30);

        for (ChunkifiedFWG fwg : fwgs) {
            for (int i = 0; i < 100_000; i++) {
                // The letter 'u' doesnt exist in either one of them
                assertEquals(fwg.generateFakeWord(20, 'u'), null);
            }
        }
    }


    @Test
    @DisplayName("frommatFWGTest")
    void frommatFWGTest() {
        FrommatFWG fwg = new FrommatFWG();
        for (int i = 0; i < 100_000; i++) {
            String fw = fwg.generateFakeWord("!!![!][aa][al][au][vl][vu]");
            assertEquals(fw.charAt(0), '!');
            assertEquals(fw.charAt(1), '[');
            assertEquals(fw.charAt(2), ']');

            assertEquals(fw.length(), 8);
        }
    }


    @Test
    @DisplayName("gibberishFWGTest")
    void gibberishFWGTest() {
        SplitMix64Random rand = new SplitMix64Random();
        GibberishFWG fwg = new GibberishFWG(new SplitMix64Random());
        GibberishFWG fwg2 = new GibberishFWG();

        for (int i = 0; i < 100_000; i++) {
            // Length test
            final int len = rand.nextInt(1, 1000);
            assertEquals(fwg2.generateFakeWord(len).length(), len);

            // All lowercase
            String s1 = fwg.generateFakeWord(50, 0f);
            assertEquals(s1, s1.toLowerCase());

            // All uppercase
            String s2 = fwg.generateFakeWord(50, 1f);
            assertEquals(s2, s2.toUpperCase());
        }

    }


}
