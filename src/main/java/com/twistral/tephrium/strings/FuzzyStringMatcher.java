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


package com.twistral.tephrium.strings;




public class FuzzyStringMatcher {

    // Temporary variables for speed
    private int[] top;
    private int[] btm;
    private char srcChar;


    //////////////////////////////////////////////////////////////////////////
    /////////////////////////////  CONSTRUCTORS  /////////////////////////////
    //////////////////////////////////////////////////////////////////////////


    /**
     * Initializes the fuzzy string matcher with the given capacity.
     * @param initialCapacity the biggest expected length of any
     *   string that will be fed into the algorithm
     */
    public FuzzyStringMatcher(int initialCapacity) {
        this.top = new int[initialCapacity];
        this.btm = new int[initialCapacity];
    }

    public FuzzyStringMatcher() {
        this(125); // 1 KB in total
    }


    /////////////////////////////////////////////////////////////////////
    /////////////////////////////  METHODS  /////////////////////////////
    /////////////////////////////////////////////////////////////////////


    /**
     * Calculates the <a href="https://en.wikipedia.org/wiki/Levenshtein_distance">Levenshtein distance</a>
     * between the two given strings. It uses the "Iterative with two matrix rows" method to optimize its results.
     * @param src any string
     * @param dst any string
     * @return the levenshtein distance between the two given strings,
     * or {@link Integer#MAX_VALUE} if any of them are null
     */
    public int levenshteinDistance(String src, String dst) {
        if(src == null || dst == null)
            return Integer.MAX_VALUE;

        final int dstLen = dst.length();
        final int srcLenp1 = src.length() + 1;
        final int dstLenp1 = dstLen + 1;

        // If the strings are same, the result will be 0
        if(srcLenp1 == dstLenp1) {
            if(src.equals(dst)) return 0;
        }

        // If the already allocated cache is too small, enlarge them
        // NOTE: only checking top.length is okay since they always have the same length
        if(top.length < dstLenp1) {
            int[] top2 = new int[dstLenp1];
            int[] btm2 = new int[dstLenp1];
            System.arraycopy(this.top, 0, top2, 0, this.top.length);
            System.arraycopy(this.btm, 0, btm2, 0, this.btm.length);
            this.top = top2;
            this.btm = btm2;
        }

        // Fill the initial top row, they're equal to i since we need to
        // add i letters to an empty string to get the substring of dst
        for (int i = 0; i < dstLenp1; i++) top[i] = i;

        // Start the iterative process using the two row vectors
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
                if(remove < add) {
                    if(remove < replace) btm[j] = remove + 1;
                    else btm[j] = replace + 1;
                }
                else {
                    if(add < replace) btm[j] = add + 1;
                    else btm[j] = replace + 1;
                }
            }

            // Swap rows to go to the next iteration
            int[] temp = top;
            top = btm;
            btm = temp;
        }

        // After the last swap, the results of btm are now in top
        return top[dstLen];
    }


}