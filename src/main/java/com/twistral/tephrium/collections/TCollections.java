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


package com.twistral.tephrium.collections;


import com.twistral.tephrium.prng.SplitMix64Random;
import com.twistral.tephrium.prng.TRandomGenerator;

import java.util.*;


public final class TCollections {

    // No Constructor
    private TCollections() {}


    private static final TRandomGenerator RAND_SHUFFLE = new SplitMix64Random();

    public static void shuffle(List<?> list, TRandomGenerator random) {
        // Same as Collections.shuffle but works with TRandomGenerator
        final int size = list.size();
        final List l = list;
        int x;

        if(size < 5 || list instanceof RandomAccess) {
            for (int i = size; i > 1; i--) {
                x = random.nextInt(0, i);
                l.set(x, l.set(i-1, l.get(x)));
            }
            return;
        }


        for(int i = size; i > 1; i--) {
            x = random.nextInt(0, i);
            l.set(x, l.set(i-1, l.get(x)));
        }

        final Object[] arr = list.toArray();
        final int arrLen = arr.length;
        ListIterator it = list.listIterator();
        for(int i = 0; i < arrLen; i++) {
            it.next();
            it.set(arr[i]);
        }

    }

    public static void shuffle(List<?> list) {
        shuffle(list, RAND_SHUFFLE);
    }


    public static <T,K> HashMap<T, K> newHashMap(T[] ts, K... ks) {
        if(ts.length != ks.length) return null;

        final int elemCount = ts.length;
        HashMap<T, K> map = new HashMap<>(elemCount);
        for (int i = 0; i < elemCount; i++)
            map.put(ts[i], ks[i]);
        return map;
    }

}
