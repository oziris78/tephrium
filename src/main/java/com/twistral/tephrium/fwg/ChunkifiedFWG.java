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



package com.twistral.tephrium.fwg;


import static com.twistral.tephrium.core.TephriumException.*;
import com.twistral.tephrium.core.functions.TMath;
import com.twistral.tephrium.prng.SplitMix64Random;
import com.twistral.tephrium.prng.TRandomGenerator;

import java.util.*;
import java.util.stream.*;

import static com.twistral.tephrium.strings.TStringUtils.*;


/**
 * Uses 3-grams ("chunks") to generate words. This FWG is one of the most reliable FWG's out there
 * if and only if the given data (frequency array) is large (populated) enough.
 */
public class ChunkifiedFWG {

    // Static variables
    private static final int LETTER_COUNT = CS_ASCII_LOWER.length();
    private static final int LETTER_COUNT_2 = LETTER_COUNT * LETTER_COUNT;
    private static final int LETTER_COUNT_3 = LETTER_COUNT_2 * LETTER_COUNT;
    public static final int DEF_REWIND_LIMIT = 200;

    // Instance variables
    private final TRandomGenerator random;
    private final int[] frequencies;
    private final int rewindLimit;
    private char cret1, cret2, cret3;


    /*//////////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  CONSTRUCTORS  ///////////////////////////*/
    /*//////////////////////////////////////////////////////////////////////*/


    /**
     * Creates a {@link ChunkifiedFWG} instance, using the given frequency array, RNG and rewindLimit. <br>
     * To generate frequency arrays use one of these methods: {@link #freqFromBookContent(String)},
     * {@link #freqFromCorpus()}, {@link #freqFromWords(Stream)}.
     * @param frequencies frequency array that will be used to generate chunks
     * @param random any random number generator
     * @param rewindLimit an integer specifying how many times rewinding will happen (rewinding means how
     *                    many times fake word generation will be tried when the corpus is not large enough)
     */
    public ChunkifiedFWG(int[] frequencies, TRandomGenerator random, int rewindLimit) {
        this.rewindLimit = rewindLimit;
        this.random = random;
        this.frequencies = frequencies;
    }


    /** @see ChunkifiedFWG#ChunkifiedFWG(int[], TRandomGenerator, int) */
    public ChunkifiedFWG(int[] frequencies) {
        this(frequencies, new SplitMix64Random(), DEF_REWIND_LIMIT);
    }


    /** @see ChunkifiedFWG#ChunkifiedFWG(int[], TRandomGenerator, int) */
    public ChunkifiedFWG() {
        this(freqFromCorpus(), new SplitMix64Random(), DEF_REWIND_LIMIT);
    }


    /*/////////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  FREQ ARRAYS  ///////////////////////////*/
    /*/////////////////////////////////////////////////////////////////////*/


    private static int[] corpusFreqs = null;


    public static int[] freqFromCorpus() {
        if(corpusFreqs == null) {
            corpusFreqs = new int[LETTER_COUNT_3];
            for (String[] corpusPart : EnglishCorpus.ws) {
                Arrays.stream(corpusPart).forEach(word -> {
                    final int iterCount = word.length() - 3;
                    for (int i = 0; i <= iterCount; i++) {
                        int ci = CS_ASCII_LOWER.indexOf(word.charAt(i+0));
                        int cj = CS_ASCII_LOWER.indexOf(word.charAt(i+1));
                        int ck = CS_ASCII_LOWER.indexOf(word.charAt(i+2));
                        final int arri = ci * LETTER_COUNT_2 + cj * LETTER_COUNT + ck;
                        corpusFreqs[arri] += 1;
                    }
                });
            }
        }

        return corpusFreqs;
    }


    public static int[] freqFromWords(Stream<String> words) {
        int[] frequencies = new int[LETTER_COUNT_3];
        fillFrequencyArray(frequencies, words);
        return frequencies;
    }


    /**
     * Creates a frequency array using the given string. This string can be any long string. If non-ascii
     * or non-space characters exist, they will automatically be ignored. So just get some very long string
     * from any source and pass it into this function. Ideally, this string can be a public domain book content
     * to generate words.
     * @param bookContent any length of string with any included characters, all non-ascii and non-space characters
     *                    will automatically be ignored and wont break the function
     * @return frequency array that corresponds to the given bookContent
     */
    public static int[] freqFromBookContent(String bookContent) {
        int[] frequencies = new int[LETTER_COUNT_3];
        final int bookContentLen = bookContent.length();
        StringBuilder asciiBookContent = new StringBuilder(bookContentLen);

        for (int i = 0; i < bookContentLen; i++) {
            char c = bookContent.charAt(i);
            if (isAscii(c) || c == ' ') asciiBookContent.append(c);
        }

        final String[] words = asciiBookContent.toString().split(" ");
        fillFrequencyArray(frequencies, Arrays.stream(words));
        return frequencies;
    }


    private static void fillFrequencyArray(int[] frequencies, Stream<String> stream) {
        stream.forEach(word -> {
            if(word.length() < 3) return; // Word must be longer than 3 letters

            word = word.toLowerCase().trim();
            int iterCount = word.length() - 3;

            for (int i = 0; i <= iterCount; i++) {
                int ci = CS_ASCII_LOWER.indexOf(word.charAt(i+0));
                int cj = CS_ASCII_LOWER.indexOf(word.charAt(i+1));
                int ck = CS_ASCII_LOWER.indexOf(word.charAt(i+2));
                final int arri = ci * LETTER_COUNT_2 + cj * LETTER_COUNT + ck;
                frequencies[arri] += 1;
            }
        });
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  METHODS  ///////////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/


    /**
     * Generates and returns a fake word. Keep in mind that if your frequency array is not big enough,
     * this function may fail to generate a fake word and simply return {@code null}. <br>
     * For example if you generate a frequency array using the {@link #freqFromBookContent(String)} method
     * with a string that does not contain the letter 'x' in it, your frequency array will have 0 frequency
     * for all chunks that start with the letter 'x'. Later, if you try to use this function to generate a
     * fake word starting with the letter 'x', it will fail and return {@code null} after trying to generate
     * a fake word for {@link #rewindLimit} amount of times. You can set this limit in the constructors.
     * Initially it is set to {@link #DEF_REWIND_LIMIT} value.
     * @param length length of the generated fake word
     * @param charStart the initial character of the generated fake word
     * @return a generated fake word or null if the frequency array is not large enough
     */
    public String generateFakeWord(int length, char charStart) {
        if(length < 3) return "";

        final char[] result = new char[length];
        final int iterCount = length - 3;
        int rewindCounter = 0;

        while(rewindCounter < rewindLimit) {
            int resIndex = 0;

            if(!getRandomChunk(charStart, CNULL)) {
                rewindCounter++;
                continue;
            }
            result[resIndex++] = cret1;
            result[resIndex++] = cret2;
            result[resIndex++] = cret3;

            for (int i = 0; i < iterCount; i++) {
                if(!getRandomChunk(cret2, cret3)) {
                    rewindCounter++;
                    continue;
                }

                result[resIndex++] = cret3;
            }

            break;
        }

        if(rewindCounter < rewindLimit) {
            return new String(result);
        }

        // Corpus is too small, no word was generated
        return null;
    }


    /** @see #generateFakeWord(int, char) */
    public String generateFakeWord(int length) {
        return generateFakeWord(length, getRandCharFrom(CS_ASCII_LOWER, random));
    }


    private static final ArrayList<Integer> POSSIBLE_INDEXES = new ArrayList<>(LETTER_COUNT * 10);


    private boolean getRandomChunk(char c1, char c2) {
        POSSIBLE_INDEXES.clear();
        int totalFreq = 0;

        // The following loops are written in a way that will work like this:
        //   if c1 is given (not CNULL), the first while loop will only work once since we know c1
        //   if c2 is given (not CNULL), the second while loop will only work once since we know c2
        //   If any of them are missing, we will iterate inside the while loops
        int i = TMath.max(0, CS_ASCII_LOWER.indexOf(c1));
        int j = TMath.max(0, CS_ASCII_LOWER.indexOf(c2));
        while(i < LETTER_COUNT) {
            while(j < LETTER_COUNT) {
                for (int k = 0; k < LETTER_COUNT; k++) {
                    final int index = (i * LETTER_COUNT_2) + (j * LETTER_COUNT) + k;
                    final int freq = frequencies[index];
                    if(freq > 0) {
                        totalFreq += freq;
                        POSSIBLE_INDEXES.add(index);
                    }
                }

                if(c2 == CNULL) j++;
                else break;
            }
            if(c1 == CNULL) i++;
            else break;
        }

        // No matching chunk was found
        if(totalFreq == 0)
            return false;

        int randFreq = random.nextInt(0, totalFreq+1);
        for(int curIndex : POSSIBLE_INDEXES) {
            int curFreq = frequencies[curIndex];
            if(randFreq <= curFreq) {
                // Matching chunk was found!
                this.cret1 = CS_ASCII_LOWER.charAt(curIndex / (LETTER_COUNT * LETTER_COUNT));
                curIndex %= LETTER_COUNT * LETTER_COUNT;
                this.cret2 = CS_ASCII_LOWER.charAt(curIndex / LETTER_COUNT);
                this.cret3 = CS_ASCII_LOWER.charAt(curIndex % LETTER_COUNT);
                return true;
            }

            randFreq -= curFreq;
        }

        throw new UnreachableException();
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  GETTERS  ///////////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/


    public int getRewindLimit() { return rewindLimit; }
    public TRandomGenerator getRandom() { return random; }


    /*/////////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  OBJ METHODS  ///////////////////////////*/
    /*/////////////////////////////////////////////////////////////////////*/


    @Override
    public String toString() {
        return "ChunkifiedFWG{" + "random=" + random + ", frequencies=" + Arrays.toString(frequencies) +
            ", rewindLimit=" + rewindLimit + ", cret1=" + cret1 + ", cret2=" + cret2 + ", cret3=" + cret3 + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        ChunkifiedFWG that = (ChunkifiedFWG) o;
        return (rewindLimit == that.rewindLimit) && (cret1 == that.cret1) &&
            (cret2 == that.cret2) && (cret3 == that.cret3) &&
            Objects.equals(random, that.random) && Arrays.equals(frequencies, that.frequencies);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(random, rewindLimit, cret1, cret2, cret3);
        result = (31 * result) + Arrays.hashCode(frequencies);
        return result;
    }


}

