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


package com.twistral.tephrium.strings;


import com.twistral.tephrium.core.functions.TMath;
import com.twistral.tephrium.prng.SplitMix64Random;
import com.twistral.tephrium.prng.TRandomGenerator;

import java.util.Locale;


public class TStringUtils {

    // Character Sets (CSs)
    public static final String CS_ASCII_LOWER = "abcdefghijklmnopqrstuvwxyz";
    public static final String CS_ASCII_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String CS_ASCII_ALL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static final String CS_VOWELS_LOWER = "aeiou";
    public static final String CS_VOWELS_UPPER = "AEIOU";
    public static final String CS_VOWELS_ALL = "aeiouAEIOU";
    public static final String CS_CONSONANTS_LOWER = "bcdfghjklmnpqrstvwxyz";
    public static final String CS_CONSONANTS_UPPER = "BCDFGHJKLMNPQRSTVWXYZ";
    public static final String CS_CONSONANTS_ALL = "bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ";
    public static final String CS_BINARY = "01";
    public static final String CS_OCTAL = "01234567";
    public static final String CS_DECIMAL = "0123456789";
    public static final String CS_HEXADECIMAL = "0123456789ABCDEF";
    public static final String CS_WHITESPACE = " \t\n\r\f";


    // No constructor
    private TStringUtils(){}


    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////  GENERAL METHODS  /////////////////////////////
    /////////////////////////////////////////////////////////////////////////////


    /**
     * A naive and potentially slow (~O(2 * {@code str} * {@code count})) implementation of String.repeat()
     * from future Java versions. (Tephrium is specifically written in SE8 for compatibility)
     * @param str any string
     * @param count an integer specifying how many times the string will be repeated
     * @return {@code str} repeated {@code count} times
     */
    public static String repeatedStringNaive(String str, int count) {
        final int strLen = str.length();
        final int repLen = strLen * count;
        char[] rep = new char[repLen];

        int repIndex = 0, strIndex = 0;
        while(repIndex < repLen) {
            rep[repIndex++] = str.charAt(strIndex++);
            strIndex %= strLen;
        }

        return new String(rep);
    }


    /**
     * Converts an integer in base 10 to another base and returns it as a string. <br>
     * This method is basically the inverse of {@link Integer#parseInt(String, int)} method. <br>
     * The {@link String#valueOf(int)} can convert integers in base 10 to string but it cant convert
     * integers in other bases. This function is meant to complete this missing part.
     * @param numberInBase10 any integer in base 10
     * @param newBase any integer, specifying the base that number will be converted to
     * @return the input in the specified base as a string
     */
    public static String parseNumberAsString(int numberInBase10, int newBase) {
        if(newBase == 10) return String.valueOf(numberInBase10);

        StringBuilder sb = new StringBuilder();
        int num = numberInBase10;
        int divided = TMath.floorFast(num / newBase);
        int rem = num - newBase * divided;

        sb.append((0 <= rem && rem <= 9) ? rem : ((char) (65 + rem - 10)));
        while(divided != 0) {
            num = divided;
            divided = TMath.floorFast(num / newBase);
            rem = num - newBase * divided;
            sb.append((0 <= rem && rem <= 9) ? rem : ((char) (65 + rem - 10)));
        }

        return sb.reverse().toString();
    }


    ///////////////////////////////////////////////////////////////////////////
    /////////////////////////////  CODEC METHODS  /////////////////////////////
    ///////////////////////////////////////////////////////////////////////////


    public static String caesarCipher(String text, int shift) {
        while (shift < 0) shift += 26;
        if(shift == 0) return text;

        String code = text.toUpperCase(Locale.ROOT);
        final int codeLen = code.length();
        StringBuilder sb = new StringBuilder();
        String curLetter;
        int index;

        for(int i = 0; i < codeLen; i++){
            curLetter = String.valueOf(code.charAt(i));
            index = CS_ASCII_UPPER.indexOf(curLetter);

            if(index == -1) {
                sb.append(Character.isLetter(curLetter.charAt(0)) ? " " : curLetter);
                continue;
            }

            index = index + shift;
            index = (index < 0) ? (index + 26) % 26 : index % 26;

            sb.append(Character.isUpperCase(text.charAt(i)) ?
                    Character.toUpperCase(CS_ASCII_UPPER.charAt(index)) :
                    Character.toLowerCase(CS_ASCII_UPPER.charAt(index))
            );
        }

        return sb.toString();
    }


    ///////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////  CHAR CHECKING METHODS  /////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////

    public static boolean isVowel(char c) { return CS_VOWELS_ALL.indexOf(c) != -1; }
    public static boolean isVowelLower(char c) { return CS_VOWELS_LOWER.indexOf(c) != -1; }
    public static boolean isVowelUpper(char c) { return CS_VOWELS_UPPER.indexOf(c) != -1; }

    public static boolean isConsonant(char c) { return CS_CONSONANTS_ALL.indexOf(c) != -1; }
    public static boolean isConsonantLower(char c) { return CS_CONSONANTS_LOWER.indexOf(c) != -1; }
    public static boolean isConsonantUpper(char c) { return CS_CONSONANTS_UPPER.indexOf(c) != -1; }

    public static boolean isAscii(char c) { return CS_ASCII_ALL.indexOf(c) != -1; }
    public static boolean isAsciiLower(char c) { return CS_ASCII_LOWER.indexOf(c) != -1; }
    public static boolean isAsciiUpper(char c) { return CS_ASCII_UPPER.indexOf(c) != -1; }

    //////////////////////////////////////////////////////////////////////////
    /////////////////////////////  CHAR METHODS  /////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    public static char getRandCharFrom(String text, TRandomGenerator random) {
        return text.charAt(random.nextInt(0, text.length()));
    }



}
