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


package com.twistral.tephrium.colors;


import com.twistral.tephrium.strings.TStringUtils;
import java.util.List;



/**
 * Helps you to export your color palettes no matter what library you are using. <br>
 * Just give your colors to this class' methods and get your palette contents as a string. <br>
 * After that, you can write that content string into a file depending on your platform.
 */
public class PaletteExporter {

    // No constructor
    private PaletteExporter() {}


    /////////////////////////////////////////////////////////////////////
    /////////////////////////////  METHODS  /////////////////////////////
    /////////////////////////////////////////////////////////////////////


    /**
     * Gives you the contents of a .gpl file as a string, you can write this string into
     * a .gpl file and then use that file in GIMP, Aseprite etc.
     * @param rgbColorValues a list of colors like so: {red1, green1, blue1, red2, ...}
     * @return the contents of a .gpl file as a string
     */
    public static String getFileContentForGIMP(List<Integer> rgbColorValues) {
        final int colorCount = rgbColorValues.size() / 3;

        StringBuilder sb = new StringBuilder();
        sb.append("GIMP Palette\n");
        sb.append("#Palette Name: ?\n");
        sb.append("#Description: Exported from Tephrium.\n");
        sb.append(String.format("#Colors: %s \n", colorCount));

        for(int i = 0; i < colorCount; i++) {
            int r = rgbColorValues.get(i*3 + 0);
            int g = rgbColorValues.get(i*3 + 1);
            int b = rgbColorValues.get(i*3 + 2);
            sb.append(String.format("%d\t%d\t%d\t%s", r, g, b,
                    TStringUtils.parseNumberAsString(r, 16) +
                    TStringUtils.parseNumberAsString(g, 16) +
                    TStringUtils.parseNumberAsString(b, 16) )
            );
            if(i+1 != colorCount) sb.append("\n");
        }

        return sb.toString();
    }


    /**
     * Gives you the contents of a .txt file as a string, you can write this string into
     * a file and then use that file in paint.net etc.
     * @param rgbaColorValues a list of colors like so: {red1, green1, blue1, alpha1, red2, ...}
     * @return the contents of a PDN palette .txt file as a string
     */
    public static String getFileContentForPDN(List<Integer> rgbaColorValues){
        StringBuilder sb = new StringBuilder("; paint.net Palette File\n");

        int colorCount = rgbaColorValues.size() / 4;
        for(int i = 0; i < colorCount; i++) {
            int r = rgbaColorValues.get(i*4 + 0);
            int g = rgbaColorValues.get(i*4 + 1);
            int b = rgbaColorValues.get(i*4 + 2);
            int a = rgbaColorValues.get(i*4 + 3);
            sb.append(
                    TStringUtils.parseNumberAsString(a, 16) +
                    TStringUtils.parseNumberAsString(r, 16) +
                    TStringUtils.parseNumberAsString(g, 16) +
                    TStringUtils.parseNumberAsString(b, 16)
            );
            if(i+1 != colorCount) sb.append("\n");
        }

        return sb.toString();
    }


}
