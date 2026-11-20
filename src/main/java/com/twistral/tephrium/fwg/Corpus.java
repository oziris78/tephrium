

// Copyright 2024-2026 Oğuzhan Topaloğlu
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


import com.twistral.tephrium.*;
import com.twistral.tephrium.prng.*;
import static com.twistral.tephrium.fwg.CorpusData.*;
import static com.twistral.tephrium.strings.TStringUtils.*;


/**
 * An English Corpus class that can be used to obtain random words from the English dictionary.
 * This class simply exposes {@link CorpusData}'s internal data.
 */
public class Corpus {

    // No constructor
    private Corpus() {}

    private static final TRandomGenerator DEF_RNG = new SplitMix64Random();


    /**
     * Returns a random word from the English corpus that starts with the given letter.
     *
     * @param random prng instance to use for index generation
     * @param initialChar first letter of the word
     * @return a random word starting with the given letter
     */
    public static String randomWord(TRandomGenerator random, char initialChar) {
        final int li = indexOfAsciiLower(initialChar);
        final int alphabetIndex = (li != -1) ? li : indexOfAsciiUpper(initialChar);
        if (alphabetIndex == -1) return "";
        final int randIndex = random.nextInt(0, ls[alphabetIndex]);
        return ws[alphabetIndex][randIndex];
    }


    /**
     * Returns a random word from the English corpus that starts with the given letter.
     *
     * @param initialChar first letter of the word
     * @return a random word starting with the given letter
     */
    public static String randomWord(char initialChar) {
        return randomWord(DEF_RNG, initialChar);
    }


    /**
     * Returns a random word from the English corpus.
     *
     * @param random prng instance to use for index generation
     * @return a random word
     */
    public static String randomWord(TRandomGenerator random) {
        int randIndex = random.nextInt(0, TOTAL_WORD_COUNT);
        for (int alphaIndex = 0; alphaIndex < ls.length; alphaIndex++) {
            if(randIndex < ls[alphaIndex])
                return ws[alphaIndex][randIndex];
            randIndex -= ls[alphaIndex];
        }
        throw new UnreachableException();
    }


    /**
     * Returns a random word from the English corpus.
     *
     * @return a random word
     */
    public static String randomWord() {
        return randomWord(DEF_RNG);
    }


}

