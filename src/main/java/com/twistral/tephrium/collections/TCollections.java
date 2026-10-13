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


package com.twistral.tephrium.collections;


import com.twistral.tephrium.prng.*;
import java.util.*;


public final class TCollections {

    // See https://docs.oracle.com/javase/specs/jls/se21/html/jls-4.html#jls-4.12.5
    private static final double DOUBLE_DEF_VALUE = 0.0d;
    private static final float FLOAT_DEF_VALUE = 0.0f;
    private static final int INT_DEF_VALUE = 0;
    private static final long LONG_DEF_VALUE = 0L;

    private static final TRandomGenerator DEF_RNG = new SplitMix64Random();

    // No Constructor
    private TCollections() {}


    /*//////////////////////////////////////////////////////////////////*/
    /*////////////////////////  ARRAY CREATION  ////////////////////////*/
    /*//////////////////////////////////////////////////////////////////*/

    public static int[] newArray(int size, int fillValue) {
        if(fillValue == INT_DEF_VALUE) return new int[size];
        int[] arr = new int[size];
        Arrays.fill(arr, fillValue);
        return arr;
    }

    public static float[] newArray(int size, float fillValue) {
        if(fillValue == FLOAT_DEF_VALUE) return new float[size];
        float[] arr = new float[size];
        Arrays.fill(arr, fillValue);
        return arr;
    }

    public static double[] newArray(int size, double fillValue) {
        if(fillValue == DOUBLE_DEF_VALUE) return new double[size];
        double[] arr = new double[size];
        Arrays.fill(arr, fillValue);
        return arr;
    }

    public static long[] newArray(int size, long fillValue) {
        if(fillValue == LONG_DEF_VALUE) return new long[size];
        long[] arr = new long[size];
        Arrays.fill(arr, fillValue);
        return arr;
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*/////////////////////  COLLECTION CREATION  /////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    public static <T> ArrayList<T> newArrayList(T... elements) {
        final int len = elements.length;
        ArrayList<T> list = new ArrayList<>(len);
        for (int i = 0; i < len; i++) list.add(elements[i]);
        return list;
    }

    public static <T> LinkedList<T> newLinkedList(T... elements) {
        final int len = elements.length;
        LinkedList<T> list = new LinkedList<>();
        for (int i = 0; i < len; i++) list.add(elements[i]);
        return list;
    }

    public static <T> HashSet<T> newHashSet(T... elements) {
        final int len = elements.length;
        HashSet<T> set = new HashSet<>(len);
        for (int i = 0; i < len; i++) set.add(elements[i]);
        return set;
    }

    public static <T,K> HashMap<T, K> newHashMap(T[] ts, K... ks) {
        if(ts.length != ks.length) return null;
        final int len = ts.length;
        HashMap<T, K> map = new HashMap<>(len);
        for (int i = 0; i < len; i++) map.put(ts[i], ks[i]);
        return map;
    }


    /*////////////////////////////////////////////////////////////////*/
    /*/////////////////////  COLLECTION METHODS  /////////////////////*/
    /*////////////////////////////////////////////////////////////////*/


    /**
     * Shuffles any given list with a {@link TRandomGenerator} instance. <br>
     * This method is same as {@link Collections#shuffle(List, Random)} but it
     * works with {@link TRandomGenerator} instead.
     *
     * @param list any list to shuffle
     * @param random any rng
     */
    public static void shuffle(List<?> list, TRandomGenerator random) {
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
        for (int i = 0; i < arrLen; i++) {
            it.next();
            it.set(arr[i]);
        }
    }


    /**
     * Shuffles any given list using the internal {@link TRandomGenerator} instance.
     *
     * @param list any list to shuffle
     */
    public static void shuffle(List<?> list) {
        shuffle(list, DEF_RNG);
    }


}

