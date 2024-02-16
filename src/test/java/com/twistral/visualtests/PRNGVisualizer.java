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


package com.twistral.visualtests;


import com.twistral.tephrium.prng.SplitMix64Random;
import com.twistral.tephrium.prng.TRandomGenerator;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;


public class PRNGVisualizer {

    private static final int IMAGE_SIZE = 300;
    private static int SCALE = 2;


    public static void main(String[] args) {
        visualizeAlgorithm(SplitMix64Random.class);
    }



    private static void visualizeAlgorithm(Class<? extends TRandomGenerator> randomClass) {
        TRandomGenerator random = getRandomFromNoParamConstructor(randomClass);

        JFrame frame = new JFrame();
        frame.setTitle(randomClass.getSimpleName());
        JLabel label = new JLabel(new ImageIcon());
        frame.getContentPane().add(label);

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyChar() == 'n')
                    regenerateImage(frame, label, random);
            }

            @Override public void keyTyped(KeyEvent e) {}
            @Override public void keyReleased(KeyEvent e) {}
        });

        regenerateImage(frame, label, random);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void regenerateImage(JFrame frame, JLabel label, TRandomGenerator random) {
        BufferedImage image = new BufferedImage(IMAGE_SIZE * SCALE, IMAGE_SIZE * SCALE, BufferedImage.TYPE_4BYTE_ABGR);
        for (int row = 0; row < IMAGE_SIZE * SCALE; row += SCALE) {
            for (int col = 0; col < IMAGE_SIZE * SCALE; col += SCALE) {
                int v = random.nextInt(0, 256);
                int color = RGBAIntsToARGB(v, v, v, 255);
                for (int i = 0; i < SCALE; i++) {
                    for (int j = 0; j < SCALE; j++) {
                        image.setRGB(row + i, col + j, color);
                    }
                }
            }
        }

        label.setIcon(new ImageIcon(image));
        frame.pack();
        label.repaint();
    }

    private static int RGBAIntsToARGB(int r, int g, int b, int a) {
        return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF);
    }

    private static TRandomGenerator getRandomFromNoParamConstructor(Class<? extends TRandomGenerator> randomClass) {
        try {
            return randomClass.getConstructor().newInstance();
        }
        catch (NoSuchMethodException e) {
            throw new RuntimeException(randomClass + " doesnt contain an empty constructor!");
        }
        catch (InvocationTargetException e) {
            throw new RuntimeException(randomClass + "'s no-parameter constructor threw an exception!");
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(randomClass + "'s no-parameter constructor is not accessible!");
        }
        catch (InstantiationException e) {
            throw new RuntimeException("Unreachable.");
        }
    }

}



