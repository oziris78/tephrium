

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


package com.twistral.tephrium.strings;


import com.twistral.tephrium.TMath;
import com.twistral.tephrium.pairs.IntObjPair;

import java.util.*;
import java.util.stream.Stream;


/**
 * A very fast fuzzy string matching implementation using the Levenshtein distance algorithm. <br>
 * This class uses 2 pre allocated row buffers and a cached max heap for optimization, making
 * the whole class is pretty much zero allocation.
 */
public class FuzzyStringMatcher {

    public static final int DEF_MAX_WORD_COUNT = 8;
    public static final int DEF_MAX_DIST = 12;

    // Pre allocated row buffers for caching
    private int[] top;
    private int[] btm;

    // Max heap is ordered descending by dist so that the worst match is easily removable
    private PriorityQueue<IntObjPair<String>> maxHeap;


    /**
     * Initializes the fuzzy string matcher with the given capacity.
     * @param initCapacity the biggest expected length of any string that will be matched
     */
    public FuzzyStringMatcher(int initCapacity) {
        this.top = new int[initCapacity];
        this.btm = new int[initCapacity];
        this.maxHeap = new PriorityQueue<>(64, (x, y) -> Integer.compare(y.getA(), x.getA()));
    }


    public FuzzyStringMatcher() {
        this(125); // 125*2(array)*4(int) = 1000 bytes = 1 KiB
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  METHODS  ///////////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/


    /**
     * Calculates the <a href="https://en.wikipedia.org/wiki/Levenshtein_distance">Levenshtein
     * distance</a> between the two strings. It uses the "Iterative with two matrix rows"
     * method to optimize its results.
     * @param src the starting point string
     * @param dst the ending point string
     * @return the levenshtein distance between the two given strings
     *         or {@link Integer#MAX_VALUE} if any of them are null
     */
    public int levenshteinDistance(String src, String dst) {
        if(src == null || dst == null)
            return Integer.MAX_VALUE;

        final int dstLen = dst.length();
        final int dstLenp1 = dstLen + 1;

        final int srcLenp1 = src.length() + 1;

        // If the strings are same, the result will be 0
        if(srcLenp1 == dstLenp1) {
            if(src.equals(dst)) return 0;
        }

        // If the already allocated cache is too small, enlarge them
        // NOTE: only checking top.length is okay since they always have the same length
        if (top.length < dstLenp1) {
            // NOTE: the previously stored values are NOT copied to the top array since
            //       that array will be completely filled in the following for loop
            this.top = new int[dstLenp1];

            int[] btm2 = new int[dstLenp1];
            System.arraycopy(this.btm, 0, btm2, 0, this.btm.length);
            this.btm = btm2;
        }

        // Fill the initial top row, they're equal to i since we need to
        // add i letters to an empty string to get the substring of dst
        for (int i = 0; i < dstLenp1; i++) {
            top[i] = i;
        }

        // Start the iterative process using the two row vectors
        char srcChar;
        for (int i = 1; i < srcLenp1; i++) {
            btm[0] = i; // need to remove i letters to get empty string

            srcChar = src.charAt(i-1);
            for (int j = 1; j < dstLenp1; j++) {
                if(srcChar == dst.charAt(j-1)) {
                    btm[j] = top[j-1];
                    continue; // 1) IGNORE ACTION
                }

                int remove = top[j];     // 2) REMOVE ACTION
                int add = btm[j-1];      // 3) ADD ACTION
                int replace = top[j-1];  // 4) REPLACE ACTION

                // Get the minimum of these values and add one
                btm[j] = TMath.min(remove, add, replace) + 1;
            }

            // Swap rows to go to the next iteration
            int[] temp = top;
            top = btm;
            btm = temp;
        }

        // After the last swap, the results of btm are now in top
        return top[dstLen];
    }


    /**
     * Orders and returns the given words from the closest (most familiar) to the farthest
     * (least familiar) based on Levenshtein distance.
     *
     * @param words the candidate words to search through
     * @param input the target input string to match against
     * @param maxWordCount the maximum number of closest words that will be returned, <b>keep in mind
     *                     that the resulting array CAN have less elements than this value if your
     *                     maximum allowed distance is too low or if there arent enough words</b>
     * @param maxDist the maximum allowed distance threshold for the Levenshtein algorithm, matches
     *                that have a distance bigger than this value will be immediately discarded
     * @return an array of strings containing the closest words to the input string
     */
    public String[] getClosestWords(Stream<String> words, String input, int maxWordCount, int maxDist) {
        // Validate parameters
        if (words == null || input == null || maxWordCount <= 0)
            return new String[0];

        maxHeap.clear(); // Reset the cached queue for reuse

        words.forEach(s -> {
            int dist = levenshteinDistance(input, s);
            if (dist <= maxDist) {
                maxHeap.offer(new IntObjPair<>(dist, s));
                if (maxHeap.size() > maxWordCount) {
                    maxHeap.poll(); // Drop the worst match
                }
            }
        });

        // Extract the results in ascending order of distance into an array
        final int size = maxHeap.size();
        String[] closestWords = new String[size];
        for (int i = size - 1; i >= 0; i--) {
            closestWords[i] = maxHeap.poll().getB();
        }
        return closestWords;
    }


    public String[] getClosestWords(String[] words, String input, int maxWordCount, int maxDist) {
        if (words == null) return new String[0];
        if (words.length <= 0) return new String[0];
        return getClosestWords(Arrays.stream(words), input, maxWordCount, maxDist);
    }


    public String[] getClosestWords(Collection<String> words, String input, int maxWordCount, int maxDist) {
        if (words == null) return new String[0];
        if (words.size() <= 0) return new String[0];
        return getClosestWords(words.stream(), input, maxWordCount, maxDist);
    }


    public String[] getClosestWords(Stream<String> words, String input) {
        return getClosestWords(words, input, DEF_MAX_WORD_COUNT, DEF_MAX_DIST);
    }


    public String[] getClosestWords(String[] words, String input) {
        return getClosestWords(words, input, DEF_MAX_WORD_COUNT, DEF_MAX_DIST);
    }


    public String[] getClosestWords(Collection<String> words, String input) {
        return getClosestWords(words, input, DEF_MAX_WORD_COUNT, DEF_MAX_DIST);
    }


}

