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


package com.twistral.tests.colors;


import com.twistral.tephrium.colors.PaletteExporter;
import com.twistral.tephrium.colors.TColor;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ColorTest {


    public static void main(String[] args) throws IOException {
        List<TColor> colorList = new ArrayList<>();

        for(TColor c : new TColor[] {
                new TColor("#be4a2f"), new TColor("#d77643"), new TColor("#ead4aa"),
                new TColor("#e4a672"), new TColor("#b86f50"), new TColor("#733e39"),
                new TColor("#3e2731"), new TColor("#a22633"), new TColor("#e43b44"),
                new TColor("#f77622"), new TColor("#feae34"), new TColor("#fee761"),
                new TColor("#63c74d"), new TColor("#3e8948"), new TColor("#265c42"),
                new TColor("#193c3e"), new TColor("#124e89"), new TColor("#0099db"),
                new TColor("#2ce8f5"), new TColor("#ffffff"), new TColor("#c0cbdc"),
                new TColor("#8b9bb4"), new TColor("#5a6988"), new TColor("#3a4466"),
                new TColor("#262b44"), new TColor("#181425"), new TColor("#ff0044"),
                new TColor("#68386c"), new TColor("#b55088"), new TColor("#f6757a"),
                new TColor("#e8b796"), new TColor("#c28569")
        }) colorList.add(c);

        BufferedWriter bf = new BufferedWriter(new FileWriter("resources/gimpPalette.gpl"));
        bf.write(PaletteExporter.getFileContentForGIMP(colorList));
        bf.close();
    }

}
