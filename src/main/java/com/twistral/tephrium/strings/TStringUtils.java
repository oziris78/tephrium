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




public class TStringUtils {


    /////////////////
    /*  CONSTANTS  */
    /////////////////

    public static final String ENGLISH_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";


    /* No constructor */
    private TStringUtils(){}



    ///////////////
    /*  METHODS  */
    ///////////////


    public static int numberStringToInt(String numberStrInAnyBase, int baseOfTheNumber){
        return Integer.parseInt(numberStrInAnyBase, baseOfTheNumber);
    }


    public static String intToNumberString(int intInBase10, int base){
        if(base == 10)
            return String.valueOf(intInBase10);
        StringBuilder sb = new StringBuilder();
        int num = intInBase10;
        int divided = (int) Math.floor( num / base );
        int rem = num - base * divided;

        String s = (0 <= rem && rem <= 9) ? String.valueOf(rem) : String.valueOf((char) (65 + rem - 10));
        sb.append(s);
        while(divided != 0){
            num = divided;
            divided = (int) Math.floor( num / base );
            rem = num - base * divided;
            s = (0 <= rem && rem <= 9) ? String.valueOf(rem) : String.valueOf((char) (65 + rem - 10));
            sb.append(s);
        }
        return sb.reverse().toString();
    }


    /**
     * Returns value as a hexadecimal string while making sure that it is atleast 2 characters wide. <br>
     * For example, if value=10 thne it will return "0A" instead of "A". <br>
     * If the input is already equal or longer than 2 characters, it wont add anything to the beginning of it.
     * @param value any integer value
     * @return input as a hexadecimal string
     */
    public static String intToHexString(int value){
        String valueAsHex = Integer.toHexString(value);
        return (valueAsHex.length() == 1) ? "0" + valueAsHex : valueAsHex;
    }



}
