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


import java.util.List;



/**
 * Helps you to export your color palettes no matter what library you are using. <br>
 * It doesn't matter if you are using LWJGL, libGDX, JavaFX, Swing etc. <br>
 * Just properly convert your colors into {@link TColor} objects and store them in a
 * {@link List} and then pass that list into a function from this class and get your
 * palette contents as a string. After that, you can write that content string into a file
 * depending on your platform (PC, Android, IOS etc).
 */
public final class PaletteExporter {


    /**
     * Gives you the contents of a .gpl file as a string, you can write this string into
     * a .gpl file and then use that file in GIMP or Aseprite.
     * @param colors the color list representing your palette
     * @return the contents of a .gpl file as a string
     */
    public static String getFileContentForGIMP(List<TColor> colors){
        int len = colors.size();

        StringBuilder content = new StringBuilder("GIMP Palette\n" +
                "#Palette Name: ?\n" +
                "#Description: Exported from Tephrium.\n" +
                String.format("#Colors: %s \n", len)
        );

        for(int i = 0; i < len; i++){
            TColor c = colors.get(i);
            content.append(String.format("%d\t%d\t%d\t%s", c.red, c.green,
                    c.blue, c.getRedAsHex() + c.getGreenAsHex() + c.getBlueAsHex()));
            if(i+1 != len) content.append("\n");
        }

        return content.toString();
    }


    /**
     * Gives you the contents of a .txt file as a string, you can write this string
     * into a file and use it in paint.net.
     * @param colors the color list representing your palette
     * @return the contents of a PDN palette .txt file as a string
     */
    public static String getFileContentForPDN(List<TColor> colors){
        StringBuilder content = new StringBuilder("; paint.net Palette File\n");

        int len = colors.size();
        for(int i = 0; i < len; i++){
            TColor c = colors.get(i);
            content.append(c.getAlphaAsHex() + c.getRedAsHex() + c.getGreenAsHex() + c.getBlueAsHex());
            if(i+1 != len) content.append("\n");
        }

        return content.toString();
    }


}
