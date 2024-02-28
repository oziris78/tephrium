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


import com.twistral.tephrium.prng.SplitMix64Random;
import com.twistral.tephrium.prng.TRandomGenerator;

import java.util.ArrayList;
import java.util.List;



public class CombineFWG {

    private final TRandomGenerator random;
    private final List<String> generated;

    public CombineFWG(TRandomGenerator random) {
        this.random = random;
        generated = new ArrayList<>(512);
    }

    public CombineFWG() {
        this(new SplitMix64Random());
    }


    /////////////////////////////////////////////////////////////////////
    /////////////////////////////  METHODS  /////////////////////////////
    /////////////////////////////////////////////////////////////////////


    public List<String> getFakeWords(int wordCount) {
        String[] arr = new String[wordCount];
        for (int i = 0; i < wordCount; i++) {
            arr[i] = EnglishCorpus.randomWord(random);
        }
        return getFakeWords(arr);
    }


    public List<String> getFakeWords(String... words) {
        generated.clear();

        final int wordsLen = words.length;

        for (int i = 0; i < wordsLen; i++) {
            for (int j = 0; j < wordsLen; j++) {
                if(i == j) continue;

                final String s1 = words[i];
                final String s2 = words[j];
                final int s1Len = s1.length();
                final int s2Len = s2.length();

                for (int e1 = 1; e1 <= s1Len; e1++) {
                    for (int b2 = 0; b2 < s2Len; b2++) {
                        final int arrLen = e1 + s2Len - b2;
                        char[] arr = new char[arrLen];

                        for (int k = 0; k < e1; k++)
                            arr[k] = s1.charAt(k);

                        for (int k = e1, m = b2; k < arrLen; k++, m++)
                            arr[k] = s2.charAt(m);

                        generated.add(new String(arr));
                    }
                }
            }
        }

        return generated;
    }


}
