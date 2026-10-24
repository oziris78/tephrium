

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


package com.twistral.tephrium.colors;


import com.twistral.tephrium.strings.TStringUtils;


/**
 * Utility class for exporting color palettes into GIMP, Aseprite and Paint.net color palette files. <br>
 * Just give your colors to this class' methods and get your palette contents as a string. <br>
 * After that, you can write that content string into a file depending on your platform.
 */
public class PaletteExporter {

    // No constructor
    private PaletteExporter() {}


    /**
     * Generates the contents of a GPL file as a string. <br>
     * You can write this string into a file and then use that file in GIMP or Aseprite.
     *
     * @param rgbColors a list of colors like so: {red1, green1, blue1, red2, green2, ...}
     * @return the contents of a .gpl file as a string
     */
    public static String generateGPLFileContent(int... rgbColors) {
        final int colorCount = rgbColors.length / 3;

        StringBuilder sb = new StringBuilder(colorCount * 40);
        sb.append("GIMP Palette\n");
        sb.append("#Palette Name: ?\n");
        sb.append("#Description: Exported from Tephrium.\n");
        sb.append(String.format("#Colors: %s \n", colorCount));

        for (int i = 0; i < colorCount; i++) {
            final int r = rgbColors[i * 3 + 0];
            final int g = rgbColors[i * 3 + 1];
            final int b = rgbColors[i * 3 + 2];

            final String rStr = TStringUtils.parseIntAsString(r, 16);
            final String gStr = TStringUtils.parseIntAsString(g, 16);
            final String bStr = TStringUtils.parseIntAsString(b, 16);

            sb.append(String.format("%d\t%d\t%d\t%s%s%s", r, g, b, rStr, gStr, bStr));
            if (i+1 != colorCount) sb.append("\n");
        }

        return sb.toString();
    }


    /**
     * Generates the contents of a Paint.net TXT file as a string. <br>
     * You can write this string into a file and then use that file in Paint.net.
     *
     * @param rgbaColors a list of colors like so: {red1, green1, blue1, alpha1, red2, ...}
     * @return the contents of a PDN palette .txt file as a string
     */
    public static String generatePDNFileContent(int... rgbaColors) {
        final int colorCount = rgbaColors.length / 4;

        StringBuilder sb = new StringBuilder(colorCount * 40);
        sb.append("; paint.net Palette File\n");

        for (int i = 0; i < colorCount; i++) {
            final int r = rgbaColors[i * 4 + 0];
            final int g = rgbaColors[i * 4 + 1];
            final int b = rgbaColors[i * 4 + 2];
            final int a = rgbaColors[i * 4 + 3];

            final String aStr = TStringUtils.parseIntAsString(a, 16);
            final String rStr = TStringUtils.parseIntAsString(r, 16);
            final String gStr = TStringUtils.parseIntAsString(g, 16);
            final String bStr = TStringUtils.parseIntAsString(b, 16);

            sb.append(String.format("%s%s%s%s", aStr, rStr, gStr, bStr));
            if (i+1 != colorCount) sb.append("\n");
        }

        return sb.toString();
    }


}

