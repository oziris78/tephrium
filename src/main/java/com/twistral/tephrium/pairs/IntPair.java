
// Copyright 2026 Oğuzhan Topaloğlu
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


package com.twistral.tephrium.pairs;


import java.util.Objects;

/**
 * A fast and mutable pair class for holding two integers.
 */
public class IntPair {

    private int a, b;

    public IntPair(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public IntPair() {
        this(0, 0);
    }

    // Copy constructor
    public IntPair(IntPair intPair) {
        this(intPair.a, intPair.b);
    }


    /*///////////////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  GETTERS & SETTERS  ///////////////////////////*/
    /*///////////////////////////////////////////////////////////////////////////*/

    public int getA() { return a; }
    public int getB() { return b; }


    public IntPair setA(int a) {
        this.a = a;
        return this;
    }

    public IntPair setB(int b) {
        this.b = b;
        return this;
    }

    public IntPair setAB(int a, int b) {
        this.a = a;
        this.b = b;
        return this;
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*/////////////////////////  OBJ METHODS  /////////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        IntPair intPair = (IntPair) o;
        return a == intPair.a && b == intPair.b;
    }

    @Override
    public int hashCode() {
        return 31*a + b;
    }

    @Override
    public String toString() {
        return "IntPair{" + "a=" + a + ", b=" + b + '}';
    }


}


