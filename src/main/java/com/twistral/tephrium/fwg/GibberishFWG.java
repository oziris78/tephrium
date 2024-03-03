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


package com.twistral.tephrium.fwg;


import com.twistral.tephrium.core.functions.TMath;
import com.twistral.tephrium.prng.SplitMix64Random;
import com.twistral.tephrium.prng.TRandomGenerator;

import static com.twistral.tephrium.strings.TStringUtils.*;


/**
 * This FWG is very useful if you just want to generate some meaningless strings like "KJsafhdhhepmbvaxfhKLSD".
 */
public class GibberishFWG {

    private final TRandomGenerator random;

    public GibberishFWG(TRandomGenerator random) {
        this.random = random;
    }

    public GibberishFWG() {
        this(new SplitMix64Random());
    }


    /////////////////////////////////////////////////////////////////////
    /////////////////////////////  METHODS  /////////////////////////////
    /////////////////////////////////////////////////////////////////////


    /**
     * Generates any length of gibberish strings.
     * @param length the length of the generated string
     * @param uppercaseProbability a float in range [0, 1] specifying how much probability a character
     *                             has of being uppercase, if you only want lowercase characters either
     *                             set this parameter to 0f or simply use {@link #getRandomString(int)}.
     * @return a random and weird looking string of {@code length} length.
     */
    public String generateFakeWord(int length, float uppercaseProbability) {
        if(length <= 0) return "";
        uppercaseProbability = TMath.clamp(uppercaseProbability, 0f, 1f);

        char[] arr = new char[length];

        for (int i = 0; i < length; i++) {
            final String alphabet = (random.nextFloat() <= uppercaseProbability) ? CS_ASCII_UPPER : CS_ASCII_LOWER;
            arr[i] = getRandCharFrom(alphabet, random);
        }

        return new String(arr);
    }


    public String generateFakeWord(int length) {
        if(length <= 0) return "";

        char[] arr = new char[length];
        for (int i = 0; i < length; i++)
            arr[i] = getRandCharFrom(CS_ASCII_ALL, random);

        return new String(arr);
    }


}
