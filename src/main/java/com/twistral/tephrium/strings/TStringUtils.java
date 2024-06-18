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


import com.twistral.tephrium.collections.TCollections;
import com.twistral.tephrium.core.functions.TMath;
import com.twistral.tephrium.prng.SplitMix64Random;
import com.twistral.tephrium.prng.TRandomGenerator;
import java.util.HashMap;
import java.util.function.Function;


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

    // Other constants
    public static final char CNULL = '\u0000';

    // Private fields
    private static final TRandomGenerator RAND_SHUFFLE = new SplitMix64Random();

    // No constructor
    private TStringUtils() {}


    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////  STRING UTILITIES  ////////////////////////////
    ////////////////////////////////////////////////////////////////////////////


    public static String shuffleString(String str, TRandomGenerator random) {
        if(str == null) return "";
        if(str.length() <= 1) return str;

        final int size = str.length();
        char[] arr = str.toCharArray();

        for(int i = size; i > 1; i--) {
            int x = random.nextInt(0, i);
            char temp = arr[i-1];
            arr[i-1] = arr[x];
            arr[x] = temp;
        }

        return new String(arr);
    }

    public static String shuffleString(String str) { return shuffleString(str, RAND_SHUFFLE); }


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


    /**
     * This function returns a consowel-shifted version of the given string. <br>
     * The "Consowel Shifting Algorithm" is my (Oğuzhan Topaloğlu) original idea. <br>
     * It is equivelant to basically doing two different Caesar shifts on both vowels and consonants. <br>
     * Here's an example: for the string "hia" and shift=1, we will get the following:
     * the first consonant after h is j ("bcdfghjklmnpqrstvwxyz"), the first vowel after i is o ("aeiou"),
     * and the first vowel after a is e, so the result will be "joe".
     * @param str any string
     * @param shift any integer (if its not in range [0,26) it will be mapped to it)
     * @return consowel-shifted version of the given string
     */
    public static String consowelShiftCodec(String str, int shift) {
        char[] arr = str.toCharArray();
        final int size = arr.length;

        for (int i = 0; i < size; i++) {
            int cnsi = CS_CONSONANTS_ALL.indexOf(arr[i]);
            int vowi = CS_VOWELS_ALL.indexOf(arr[i]);

            if(cnsi == -1 && vowi == -1) continue; // not a letter

            String alphabet = (cnsi != -1) ?
                    (isConsonantLower(arr[i]) ? CS_CONSONANTS_LOWER : CS_CONSONANTS_UPPER) :
                    (isVowelLower(arr[i]) ? CS_VOWELS_LOWER : CS_VOWELS_UPPER);

            final int alphabetLen = alphabet.length();

            int newIndex = (TMath.max(cnsi, vowi) + shift) % alphabetLen;
            newIndex = (newIndex < 0) ? newIndex + alphabetLen : newIndex;
            arr[i] = alphabet.charAt(newIndex);
        }

        return new String(arr);
    }


    /**
     * Basically runs the given index-mapping function on all characters and returns the resulting string. <br>
     * Notice that caesar cipher is actually equivelant to {@code functionalCodec(str, x -> x + shift)}.
     * @param str any string
     * @param indexMapper any integer to integer function, please note that overflows are completely fine since
     *                    this function maps it to an appropriate value aka an int in range [0, 26).
     * @return the functionally coded string
     */
    public static String functionalCodec(String str, Function<Integer, Integer> indexMapper) {
        char[] arr = str.toCharArray();
        final int size = arr.length;

        for (int i = 0; i < size; i++) {
            int loweri = CS_ASCII_LOWER.indexOf(arr[i]);
            int upperi = CS_ASCII_UPPER.indexOf(arr[i]);

            if(loweri == -1 && upperi == -1) continue; // not a letter

            String alphabet = (loweri != -1) ? CS_ASCII_LOWER : CS_ASCII_UPPER;
            final int alphabetLen = alphabet.length();
            // I used max() because one of them is -1 and other is [0,26) so the valid one is always the max
            int newIndex = indexMapper.apply(TMath.max(loweri, upperi)) % alphabetLen;
            newIndex = (newIndex < 0) ? newIndex + alphabetLen : newIndex;
            arr[i] = alphabet.charAt(newIndex);
        }

        return new String(arr);
    }


    public static String caesarCipher(String str, int shift) {
        return functionalCodec(str, x -> x + shift);
    }


    ////////////////////////////////////////////////////////////////////////////
    /////////////////////////////  CHAR UTILITIES  /////////////////////////////
    ////////////////////////////////////////////////////////////////////////////


    public static char getRandCharFrom(String text, TRandomGenerator random) {
        return text.charAt(random.nextInt(0, text.length()));
    }

    public static String getRandCharAsStrFrom(String text, TRandomGenerator random) {
        return String.valueOf(getRandCharFrom(text, random));
    }

    public static char swapCharCase(char c) {
        return Character.isUpperCase(c) ? Character.toLowerCase(c) : Character.toUpperCase(c);
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


    /////////////////////////////////////////////////////////////////////////////
    /////////////////////////////  SPECIAL STRINGS  /////////////////////////////
    /////////////////////////////////////////////////////////////////////////////


    private static final HashMap<Character, String> mapCharToMorse = TCollections.newHashMap (
        new Character[] {
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r',
            's','t','u','v','w','x','y','z','1','2','3','4','5','6','7','8','9','0',
            ' '
        },
        ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-",
        ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-",
        ".--", "-..-", "-.--", "--..", ".----", "..---", "...--", "....-", ".....",
        "-....", "--...", "---..", "----.", "-----", "/"
    );


    public static String convertToMorse(String str) {
        final int strLen = str.length();
        str = str.toLowerCase();
        StringBuilder sb = new StringBuilder(strLen * 5);

        for(int i = 0; i < strLen; i++) {
            sb.append(mapCharToMorse.getOrDefault(str.charAt(i), "?"));
            if(i+1 != strLen) sb.append(' ');
        }

        return sb.toString();
    }


    /**
     * Compares two version strings that are separated by a dot, for example "1.0.2" and "1.0.3".
     * @param v1 any version string in form "MAJOR.MINOR.PATCH"
     * @param v2 any version string in form "MAJOR.MINOR.PATCH"
     * @return 0 if they are the same version, negative value if v1 is an older version that v2, positive
     *         value if v2 is an older version that v1
     */
    public static int compareVersionStrings(String v1, String v2) {
        final String[] parts1 = v1.split("\\.");
        final String[] parts2 = v2.split("\\.");
        final int len1 = parts1.length;
        final int len2 = parts2.length;
        final int minLen = TMath.min(len1, len2);

        for (int i = 0; i < minLen; i++) {
            int comparison = Integer.compare(Integer.parseInt(parts1[i]), Integer.parseInt(parts2[i]));
            if (comparison != 0) return comparison;
        }

        return Integer.compare(len1, len2);
    }


}

