
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



package com.twistral.tests.strings;

import static com.twistral.tephrium.strings.TStringUtils.*;

import com.twistral.tephrium.fwg.GibberishFWG;
import com.twistral.tephrium.prng.SplitMix64Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class TStringUtilsTest {


    @Test
    @DisplayName("caesarCipherTest")
    void caesarCipherTest() {
        GibberishFWG fwg = new GibberishFWG();
        SplitMix64Random random = new SplitMix64Random();

        for (int i = 0; i < 20000; i++) {
            int shift = random.nextInt();
            String s1 = fwg.generateFakeWord(20);
            String s2 = caesarCipher(caesarCipher(s1, shift), -shift);
            assertEquals(s1, s2);
        }

        for (int i = 0; i < 200; i++) {
            Assertions.assertEquals(caesarCipher("zpsf kmkclr",  2 + (26 * i)), "bruh moment");
            Assertions.assertEquals(caesarCipher("bruh moment", -2 + (26 * i)), "zpsf kmkclr");
        }

        for (int i = 0; i < 200; i++) {
            // Get random text
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 20; j++) {
                char randChar = CS_ASCII_ALL.charAt((int)(Math.random() * CS_ASCII_ALL.length()));
                sb.append(randChar);
            }
            String text = sb.toString();

            // Try caesar
            int shift = (int) (Math.random() * 200000);
            String encrypted = caesarCipher(text, shift);
            String decrypted = caesarCipher(encrypted, -shift);
            Assertions.assertEquals(decrypted, text);
        }

        Assertions.assertEquals(caesarCipher("dtwj oqogpv", -2), "bruh moment");
        Assertions.assertEquals(caesarCipher("bruh moment", 2), "dtwj oqogpv");
    }


    @Test
    @DisplayName("compareVersionStringsTest")
    void compareVersionStringsTest() {
        assertEquals(compareVersionStrings("1.0.2", "1.0.3"), -1);
        assertEquals(compareVersionStrings("1.0.2", "1.0.4"), -1);
        assertEquals(compareVersionStrings("1.0.2", "1.0.10"), -1);
        assertEquals(compareVersionStrings("1.0.2", "1.0.1000"), -1);
        assertEquals(compareVersionStrings("1.0.2", "1.0.2"), 0);
        assertEquals(compareVersionStrings("1.0.2", "1.0.1"), 1);
        assertEquals(compareVersionStrings("1.0.2", "1.0"), 1);
    }

    
    @Test
    @DisplayName("getRandCharFromTest")
    void getRandCharFromTest() {
        SplitMix64Random random = new SplitMix64Random();
        GibberishFWG fwg = new GibberishFWG();
        for (int i = 0; i < 10000; i++) {
            String alphabet = fwg.generateFakeWord(30);
            char c = getRandCharFrom(alphabet, random);
            assertTrue(alphabet.indexOf(c) != -1);
        }
    }


    @Test
    @DisplayName("repeatedStringNaiveTest")
    void repeatedStringNaiveTest() {
        GibberishFWG fwg = new GibberishFWG();
        for (int i = 0; i < 1000; i++) {
            String s = fwg.generateFakeWord(30);
            String r1 = repeatedStringNaive(s, 10);
            String r2 = "";
            for (int j = 0; j < 10; j++) {
                r2 += s;
            }
            assertEquals(r1, r2);
        }
    }

    
    @Test
    @DisplayName("swapCharCaseTest")
    void swapCharCaseTest() {
        for(int i = 0; i < CS_ASCII_LOWER.length(); i++) {
            assertEquals(swapCharCase(CS_ASCII_LOWER.charAt(i)), CS_ASCII_UPPER.charAt(i));
            assertEquals(swapCharCase(CS_ASCII_UPPER.charAt(i)), CS_ASCII_LOWER.charAt(i));
        }
    }


    @Test
    @DisplayName("charBoolMethods")
    void charBoolMethods() {
        assertTrue(isVowel('a'));
        assertTrue(isVowelLower('a'));
        assertTrue(isVowelUpper('A'));
        assertTrue(isConsonant('x'));
        assertTrue(isConsonantLower('x'));
        assertTrue(isConsonantUpper('X'));
        assertTrue(isAscii('h'));
        assertTrue(isAsciiLower('x'));
        assertTrue(isAsciiUpper('G'));

        assertFalse(isVowel('x'));
        assertFalse(isVowelLower('A'));
        assertFalse(isVowelUpper('a'));
        assertFalse(isConsonant('e'));
        assertFalse(isConsonantLower('C'));
        assertFalse(isConsonantUpper('E'));
        assertFalse(isAscii('#'));
        assertFalse(isAsciiLower('E'));
        assertFalse(isAsciiUpper('2'));
    }

    
    @Test
    @DisplayName("parseNumberAsStringTest")
    void parseNumberAsStringTest() {
        assertEquals("1010", parseNumberAsString(10, 2));
        assertEquals("101", parseNumberAsString(10, 3));
        assertEquals("22", parseNumberAsString(10, 4));
    }


    @Test
    @DisplayName("codecTests")
    void codecTests() {
        assertEquals("ifmmp", functionalCodec("hello", x -> x+1));
        assertEquals("ofbhu", consowelShiftCodec("abxde", 3));
        assertEquals(".... . .-.. .-.. ---", convertToMorse("hello"));
    }

    
    @Test
    @DisplayName("shuffleStringTest")
    void shuffleStringTest() {
        GibberishFWG fwg = new GibberishFWG();
        SplitMix64Random random = new SplitMix64Random();

        for (int i = 0; i < 1000; i++) {
            String str = fwg.generateFakeWord(20);
            String shuffle1 = shuffleString(str, random);
            String shuffle2 = shuffleString(str);

            char[] arr1 = str.toCharArray();
            char[] arr2 = shuffle1.toCharArray();
            char[] arr3 = shuffle2.toCharArray();
            Arrays.sort(arr1);
            Arrays.sort(arr2);
            Arrays.sort(arr3);

            assertArrayEquals(arr1, arr2);
            assertArrayEquals(arr1, arr3);
            assertArrayEquals(arr2, arr3);
        }

    }




}
