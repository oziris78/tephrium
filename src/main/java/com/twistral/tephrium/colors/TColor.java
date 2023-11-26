// Copyright 2020-2023 Oğuzhan Topaloğlu
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


package com.twistral.tephrium.colors;


import com.twistral.tephrium.strings.TStringUtils;

import java.util.Objects;


public class TColor {

    /** Values in range [0, 255] */
    public final int red, green, blue, alpha;


    ////////////////////
    /*  CONSTRUCTORS  */
    ////////////////////


    /**
     * Constructs a {@link TColor} object using 4 integer values in range [0, 255].
     * @param red an integer in range [0,255]
     * @param green an integer in range [0,255]
     * @param blue an integer in range [0,255]
     * @param alpha an integer in range [0,255]
     */
    public TColor(int red, int green, int blue, int alpha){
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }


    public TColor(int red, int green, int blue){
        this(red, green, blue, 255);
    }


    /**
     * Creates a {@link TColor} object using the hexadecimal string.
     * @param hexString a string like "#ffaacc" with an hashtag in the beginning.
     */
    public TColor(String hexString){
        this(
                Integer.valueOf( hexString.substring( 1, 3 ), 16 ),   /* r */
                Integer.valueOf( hexString.substring( 3, 5 ), 16 ),   /* g */
                Integer.valueOf( hexString.substring( 5, 7 ), 16 ),   /* b */
                255                                                   /* a */
        );
    }


    ///////////////
    /*  METHODS  */
    ///////////////


    public double brightness() {
        float rPerc = this.red / 255f;    // [0, 1]
        float gPerc = this.green / 255f;  // [0, 1]
        float bPerc = this.blue / 255f;   // [0, 1]
        return Math.sqrt(0.299f * Math.pow(rPerc, 2) + 0.587f * Math.pow(gPerc, 2) + 0.114f * Math.pow(bPerc, 2));
    }


    public TColor inverse() {
        return new TColor(255 - this.red, 255 - this.green, 255 - this.blue, this.alpha);
    }


    public String getRedAsHex() { return TStringUtils.intToHexString(this.red); }
    public String getGreenAsHex() { return TStringUtils.intToHexString(this.green); }
    public String getBlueAsHex() { return TStringUtils.intToHexString(this.blue); }
    public String getAlphaAsHex() { return TStringUtils.intToHexString(this.alpha); }


    //////////////////////
    /*  OBJECT METHODS  */
    //////////////////////


    @Override
    public String toString() {
        return "TColor{" + "red=" + red + ", green=" + green + ", blue=" + blue + ", alpha=" + alpha + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        TColor tColor = (TColor) o;
        return (red == tColor.red) && (green == tColor.green) && (blue == tColor.blue) && (alpha == tColor.alpha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(red, green, blue, alpha);
    }


}
