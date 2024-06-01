
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


package com.twistral.manual;

import com.twistral.tephrium.core.functions.TRange;
import com.twistral.tephrium.fwg.ChunkifiedFWG;
import com.twistral.tephrium.prng.TRandomGenerator;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashSet;
import java.util.stream.Collectors;


public class GenerateFakeWords {

    private static final int LEN_RANGE_LEFT = 4, LEN_RANGE_RIGHT = 8, WORD_COUNT = 4000;
    private static final String SHITTY_ABSOLUTE_PATH_LOL = "C:\\Users\\oguzh\\Desktop\\test2.txt";


    public static void main(String[] args) {
        ChunkifiedFWG fwg = new ChunkifiedFWG();

        HashSet<String> set = new HashSet<>();
        for (int len = LEN_RANGE_LEFT; len < LEN_RANGE_RIGHT; len++) {
            for (int i = 0; i < WORD_COUNT; i++) {
                String fw = fwg.generateFakeWord(len);
                fw = Character.toUpperCase(fw.charAt(0)) + fw.substring(1);
                set.add(fw);
            }
        }

        try {
            Files.write(
                Paths.get(SHITTY_ABSOLUTE_PATH_LOL),
                set.stream().collect(Collectors.toList())
                   .stream().sorted(Comparator.comparingInt(String::length).thenComparing(a -> a))
                   .collect(Collectors.toList())
            );
        }
        catch (IOException e) {}

    }

}
