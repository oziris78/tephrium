

// Copyright 2020-2026 Oğuzhan Topaloğlu
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


import com.twistral.tephrium.*;
import com.twistral.tephrium.prng.*;
import java.util.*;
import java.util.function.*;


public final class TStringUtils {

    // Character sets
    public static final String CS_ASCII_LOWER = "abcdefghijklmnopqrstuvwxyz";
    public static final String CS_ASCII_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String CS_ASCII = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static final String CS_VOWELS_LOWER = "aeiou";
    public static final String CS_VOWELS_UPPER = "AEIOU";
    public static final String CS_VOWELS = "aeiouAEIOU";

    public static final String CS_CONSONANTS_LOWER = "bcdfghjklmnpqrstvwxyz";
    public static final String CS_CONSONANTS_UPPER = "BCDFGHJKLMNPQRSTVWXYZ";
    public static final String CS_CONSONANTS = "bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ";

    public static final String CS_BINARY = "01";
    public static final String CS_OCTAL = "01234567";
    public static final String CS_DECIMAL = "0123456789";
    public static final String CS_HEXADECIMAL = "0123456789ABCDEF";
    public static final String CS_WHITESPACE = " \t\n\r\f";

    // Private fields
    private static final TRandomGenerator DEF_RNG = new SplitMix64Random();

    // No constructor
    private TStringUtils() {}


    /*//////////////////////////////////////////////////////////////////////*/
    /*//////////////////////////  CHAR UTILITIES  //////////////////////////*/
    /*//////////////////////////////////////////////////////////////////////*/


    public static boolean isVowelLower(char c) { return c < 128 && IS_VOWEL_LOWER[c]; }
    public static boolean isVowelUpper(char c) { return c < 128 && IS_VOWEL_UPPER[c]; }
    public static boolean isVowel(char c) { return c < 128 && IS_VOWEL[c]; }

    public static boolean isConsonantLower(char c) { return c < 128 && IS_CONSONANT_LOWER[c]; }
    public static boolean isConsonantUpper(char c) { return c < 128 && IS_CONSONANT_UPPER[c]; }
    public static boolean isConsonant(char c) { return c < 128 && IS_CONSONANT[c]; }

    public static boolean isAsciiLower(char c) { return c < 128 && IS_ASCII_LOWER[c]; }
    public static boolean isAsciiUpper(char c) { return c < 128 && IS_ASCII_UPPER[c]; }
    public static boolean isAscii(char c) { return c < 128 && IS_ASCII[c]; }

    public static boolean isBinary(char c) { return c < 128 && IS_BINARY[c]; }
    public static boolean isOctal(char c) { return c < 128 && IS_OCTAL[c]; }
    public static boolean isDecimal(char c) { return c < 128 && IS_DECIMAL[c]; }
    public static boolean isHexadecimal(char c) { return c < 128 && IS_HEXADECIMAL[c]; }
    public static boolean isWhitespace(char c) { return c < 128 && IS_WHITESPACE[c]; }


    public static char getRandCharFrom(String text, TRandomGenerator random) {
        return text.charAt(random.nextInt(0, text.length()));
    }

    public static char getRandCharFrom(String text) {
        return getRandCharFrom(text, DEF_RNG);
    }

    public static char swapCase(char c) {
        return Character.isUpperCase(c) ? Character.toLowerCase(c) : Character.toUpperCase(c);
    }


    /*//////////////////////////////////////////////////////////////////////*/
    /*/////////////////////////  STRING UTILITIES  /////////////////////////*/
    /*//////////////////////////////////////////////////////////////////////*/


    public static String shuffleString(String str, TRandomGenerator random) {
        if (str == null) return "";
        if (str.length() <= 1) return str;

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


    public static String shuffleString(String str) {
        return shuffleString(str, DEF_RNG);
    }


    /**
     * Compares two version strings that are separated by dots, for example "1.0.2" and "1.0.3".
     *
     * @param v1 the first version string in form "MAJOR.MINOR.PATCH"
     * @param v2 the second version string in form "MAJOR.MINOR.PATCH"
     * @return 0 if they are the same version, a negative integer if v1 is older than v2,
     *         or a positive integer if v1 is newer than v2
     */
    public static int compareVersionStrings(String v1, String v2) {
        Objects.requireNonNull(v1, "the first version string cant be null");
        Objects.requireNonNull(v2, "the second version string cant be null");

        int i1 = 0, i2 = 0, num1 = 0, num2 = 0;
        final int len1 = v1.length(), len2 = v2.length();

        while (i1 < len1 || i2 < len2) {
            num1 = 0;
            while (i1 < len1) {
                char c = v1.charAt(i1++);
                if (c == '.') break;
                if (c >= '0' && c <= '9') {
                    num1 = num1 * 10 + (c - '0');
                }
            }

            num2 = 0;
            while (i2 < len2) {
                char c = v2.charAt(i2++);
                if (c == '.') break;
                if (c >= '0' && c <= '9') {
                    num2 = num2 * 10 + (c - '0');
                }
            }

            if (num1 != num2) {
                return (num1 < num2) ? -1 : 1;
            }
        }

        return 0;
    }


    /**
     * A slower implementation of String.repeat() from future Java versions.
     * (Tephrium is specifically written in SE8 for compatibility)
     *
     * @param str any string
     * @param count an integer specifying how many times the string will be repeated
     * @return the given string repeated given number of times, or an empty string for invalid inputs
     */
    public static String repeatedStringNaive(String str, int count) {
        if (str == null || count <= 0) return "";
        if (count == 1) return str;

        final int strLen = str.length();
        final int repLen = strLen * count;
        char[] rep = new char[repLen];

        for (int i = 0; i < count; i++)
            str.getChars(0, strLen, rep, i * strLen);

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
    public static String parseIntAsString(int numberInBase10, int newBase) {
        if(newBase == 10) return String.valueOf(numberInBase10);

        StringBuilder sb = new StringBuilder();
        int num = numberInBase10;

        do {
            int divided = TMath.floorToInt((double) num / (double) newBase);
            int rem = num - newBase * divided;
            sb.append((0 <= rem && rem <= 9) ? (char) ('0' + rem) : (char) ('A' + rem - 10));
            num = divided;
        } while (num != 0);

        return sb.reverse().toString();
    }


    /*////////////////////////////////////////////////////////////////////////*/
    /*///////////////////////  CIPHER & CODEC METHODS  ///////////////////////*/
    /*////////////////////////////////////////////////////////////////////////*/


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
    public static String consowelShiftCipher(String str, int shift) {
        if (str == null) return "";

        char[] arr = str.toCharArray();
        final int size = arr.length;

        for (int i = 0; i < size; i++) {
            int cnsi = CS_CONSONANTS.indexOf(arr[i]);
            int vowi = CS_VOWELS.indexOf(arr[i]);

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
     * Basically runs the given index-mapping function on all characters and returns the
     * resulting string. <br> Notice that caesar cipher is actually equivelant to
     * {@code functionalCodec(str, x -> x + shift)}.
     *
     * @param str any string
     * @param indexMapper any integer to integer function, please note that overflows
     *                    are completely fine since this function maps it to an appropriate
     *                    value aka an int in range [0, 26).
     * @return the functionally transformed/processed string
     */
    public static String functionalCodec(String str, Function<Integer, Integer> indexMapper) {
        char[] arr = str.toCharArray();

        for (int i = 0, arrLen = arr.length; i < arrLen; i++) {
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


    /**
     * Converts a single character to its Morse code representation.
     * @param c any character (case insensitive for letters)
     * @return the Morse code string, or "?" if unsupported
     */
    public static String morseCodec(char c) {
        if (isAsciiLower(c)) return MORSE_LETTERS[c - 'a'];
        if (isAsciiUpper(c)) return MORSE_LETTERS[c - 'A'];
        if (isDecimal(c)) return MORSE_DIGITS[c - '0'];
        if (c == ' ') return "/";
        return "?";
    }


    public static String morseCodec(String str) {
        if (str == null) return "";
        if (str.isEmpty()) return "";

        final int strLen = str.length();
        StringBuilder sb = new StringBuilder(strLen * 5);

        for (int i = 0; i < strLen; i++) {
            sb.append(morseCodec(str.charAt(i)));
            if (i + 1 != strLen) sb.append(' ');
        }

        return sb.toString();
    }


    /*////////////////////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  INTERNAL LOOKUP TABLES  ///////////////////////////*/
    /*////////////////////////////////////////////////////////////////////////////////*/


    private static final String[] MORSE_LETTERS = { // index 0-25 for a-z
        ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--",
        "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.."
    };

    private static final String[] MORSE_DIGITS = { // index 0-9 for 0-9
        "-----", ".----", "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----."
    };

    private static final boolean[] IS_VOWEL_LOWER = newLookupTable(CS_VOWELS_LOWER),
            IS_VOWEL_UPPER = newLookupTable(CS_VOWELS_UPPER),
            IS_VOWEL = newLookupTable(CS_VOWELS),
            IS_CONSONANT_LOWER = newLookupTable(CS_CONSONANTS_LOWER),
            IS_CONSONANT_UPPER = newLookupTable(CS_CONSONANTS_UPPER),
            IS_CONSONANT = newLookupTable(CS_CONSONANTS),
            IS_ASCII_LOWER = newLookupTable(CS_ASCII_LOWER),
            IS_ASCII_UPPER = newLookupTable(CS_ASCII_UPPER),
            IS_ASCII = newLookupTable(CS_ASCII),
            IS_BINARY = newLookupTable(CS_BINARY),
            IS_OCTAL = newLookupTable(CS_OCTAL),
            IS_DECIMAL = newLookupTable(CS_DECIMAL),
            IS_HEXADECIMAL = newLookupTable(CS_HEXADECIMAL),
            IS_WHITESPACE = newLookupTable(CS_WHITESPACE);


    // Generates precomputed O(1) lookup tables for chars
    private static boolean[] newLookupTable(String alphabet) {
        final boolean[] table = new boolean[128];

        for (int i = 0, alphabetLen = alphabet.length(); i < alphabetLen; i++)
            table[alphabet.charAt(i)] = true;

        return table;
    }


}

