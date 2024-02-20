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


package com.twistral.tests;

import com.sun.javafx.binding.StringConstant;
import com.twistral.tephrium.strings.TStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class CryptoTest {

    @Test
    @DisplayName("cryptoTest")
    void cryptoTest() {
        for (int i = 0; i < 200; i++) {
            Assertions.assertEquals(TStringUtils.caesarCipher("zpsf kmkclr",  2 + (26 * i)), "bruh moment");
            Assertions.assertEquals(TStringUtils.caesarCipher("bruh moment", -2 + (26 * i)), "zpsf kmkclr");
        }

        for (int i = 0; i < 200; i++) {
            // Get random text
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 20; j++) {
                char randChar = TStringUtils.CS_ASCII_UPPER.charAt( (int) (Math.random() * 26) );
                sb.append(randChar);
            }
            String text = sb.toString();

            // Try caesar
            int shift = (int) (Math.random() * 200000);
            String encrypted = TStringUtils.caesarCipher(text, shift);
            String decrypted = TStringUtils.caesarCipher(encrypted, -shift);
            Assertions.assertEquals(decrypted, text);
            System.out.println(text + " => " + encrypted + " => " + decrypted);
        }

        Assertions.assertEquals(TStringUtils.caesarCipher("dtwj oqogpv", -2), "bruh moment");
        Assertions.assertEquals(TStringUtils.caesarCipher("bruh moment", 2), "dtwj oqogpv");
    }

}
