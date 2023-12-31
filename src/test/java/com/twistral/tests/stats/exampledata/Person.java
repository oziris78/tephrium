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


package com.twistral.tests.stats.exampledata;


public class Person {

    public String name;
    public int age, height;

    public Person(String name, int age, int height){
        this.name = name;
        this.age = age;
        this.height = height;
    }

    @Override
    public String toString() {
        return String.format("(%s, %d, %d)", name, age, height);
    }

    public static Person[] createPopulation() {
        String alphabet = "USPXMEJYRZQACGHLFKWNVODITB";

        int[] ages = new int[]{
                12, 15, 21, 18, 19, 30, 50, 70, 50, 55, 34, 21, 12, 13,
                18, 18, 18, 18, 18, 17, 18, 65, 75, 32, 24, 25
        };
        int[] heights = new int[]{
                160, 172, 162, 176, 180, 176, 182, 176, 176, 166, 158, 183, 165, 188,
                177, 178, 170, 180, 170, 180, 190, 173, 192, 167, 203, 171
        };
        Person[] population = new Person[alphabet.length()];
        for (int i = 0; i < population.length; i++) {
            String name = String.valueOf(alphabet.charAt(i));
            name = name.replace(name, name + name + name + name + name);
            population[i] = new Person(name, ages[i], heights[i]);
        }
        return population;
    }

}
