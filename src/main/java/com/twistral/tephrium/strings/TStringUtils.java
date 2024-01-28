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


package com.twistral.tephrium.strings;


import com.twistral.tephrium.core.functions.TMath;

import java.util.Locale;



public class TStringUtils {

    public static final String ENGLISH_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";


    // No constructor
    private TStringUtils(){}


    /////////////////////////////////////////////////////////////////////
    /////////////////////////////  METHODS  /////////////////////////////
    /////////////////////////////////////////////////////////////////////


    /**
     * Converts an integer in base 10 to another base and returns it as a string. <br>
     * This method is basically the inverse of {@link Integer#parseInt(String, int)} method. <br>
     * The {@link String#valueOf(int)} can convert integers in base 10 to string but it cant convert
     * integers in other bases. This function is meant to complete this missing part.
     * @param numberInBase10 any integer in base 10
     * @param newBase any integer, specifying the base that number will be converted to
     * @return the input in the specified base as a string
     */
    public static String parseNumberAsString(int numberInBase10, int newBase) {
        if(newBase == 10) return String.valueOf(numberInBase10);

        StringBuilder sb = new StringBuilder();
        int num = numberInBase10;
        int divided = TMath.floorFast(num / newBase);
        int rem = num - newBase * divided;

        sb.append((0 <= rem && rem <= 9) ? rem : ((char) (65 + rem - 10)));
        while(divided != 0) {
            num = divided;
            divided = TMath.floorFast(num / newBase);
            rem = num - newBase * divided;
            sb.append((0 <= rem && rem <= 9) ? rem : ((char) (65 + rem - 10)));
        }
        return sb.reverse().toString();
    }


    //////////////////////////////////////////////////////////////////////////
    /////////////////////////////  CRYPTOGRAPHY  /////////////////////////////
    //////////////////////////////////////////////////////////////////////////


    public static String caesarCipher(String text, int shift) {
        if(shift <= 0) {
            while (shift < 0) {
                shift += 26;
            }
        }

        if(shift == 0) return text;

        String code = text.toUpperCase(Locale.ROOT);
        final int codeLen = code.length();
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < codeLen; i++){
            String curLetter = String.valueOf(code.charAt(i));
            int index = ENGLISH_ALPHABET.indexOf(curLetter);

            if(index == -1) {
                sb.append(Character.isLetter(curLetter.charAt(0)) ? " " : curLetter);
                continue;
            }

            index = index + shift;
            index = (index < 0) ? (index + 26) % 26 : index % 26;

            sb.append(Character.isUpperCase(text.charAt(i)) ?
                    Character.toUpperCase(ENGLISH_ALPHABET.charAt(index)) :
                    Character.toLowerCase(ENGLISH_ALPHABET.charAt(index))
            );
        }

        return sb.toString();
    }


}
