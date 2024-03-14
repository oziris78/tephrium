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


import com.twistral.tephrium.core.TephriumException;
import com.twistral.tephrium.prng.SplitMix64Random;
import com.twistral.tephrium.prng.TRandomGenerator;
import com.twistral.tephrium.strings.TStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.twistral.tephrium.core.TephriumException.UnreachableException;
import static com.twistral.tephrium.strings.TStringUtils.CS_ASCII_LOWER;
import static com.twistral.tephrium.fwg.EnglishCorpusData.*;



public class EnglishCorpus {

    // No constructor
    private EnglishCorpus() {}


    static final String[][] ws = new String[][] {
            CP_A.W, CP_B.W, CP_C.W, CP_D.W, CP_E.W, CP_F.W, CP_G.W, CP_H.W, CP_I.W,
            CP_J.W, CP_K.W, CP_L.W, CP_M.W, CP_N.W, CP_O.W, CP_P.W, CP_Q.W, CP_R.W,
            CP_S.W, CP_T.W, CP_U.W, CP_V.W, CP_W.W, CP_X.W, CP_Y.W, CP_Z.W
    };

    private static final int[] ls = new int[] {
            CP_A.L, CP_B.L, CP_C.L, CP_D.L, CP_E.L, CP_F.L, CP_G.L, CP_H.L, CP_I.L,
            CP_J.L, CP_K.L, CP_L.L, CP_M.L, CP_N.L, CP_O.L, CP_P.L, CP_Q.L, CP_R.L,
            CP_S.L, CP_T.L, CP_U.L, CP_V.L, CP_W.L, CP_X.L, CP_Y.L, CP_Z.L
    };


    public static String randomWordStartingWith(char c, TRandomGenerator random) {
        final int alphabetIndex = CS_ASCII_LOWER.indexOf(Character.toLowerCase(c));
        if(alphabetIndex == -1) return "";
        return ws[alphabetIndex][random.nextInt(0, ls[alphabetIndex])];
    }


    public static String randomWord(TRandomGenerator random) {
        int randIndex = random.nextInt(0, TOTAL_WORD_COUNT);
        for (int alphaIndex = 0; alphaIndex < ls.length; alphaIndex++) {
            if(randIndex < ls[alphaIndex])
                return ws[alphaIndex][randIndex];
            randIndex -= ls[alphaIndex];
        }
        throw new UnreachableException();
    }

}


