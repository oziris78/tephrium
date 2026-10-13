

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
 * A fast and mutable pair class holding an integer and an object of any type
 *
 * @param <B> type of the second object
 */
public class IntObjPair<B> {

    private int a;
    private B b;

    public IntObjPair(int a, B b) {
        this.a = a;
        this.b = b;
    }

    public IntObjPair() {
        this(0, null);
    }

    /*///////////////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  GETTERS & SETTERS  ///////////////////////////*/
    /*///////////////////////////////////////////////////////////////////////////*/

    public int getA() { return a; }
    public B getB() { return b; }


    public IntObjPair<B> setA(int a) {
        this.a = a;
        return this;
    }

    public IntObjPair<B> setB(B b) {
        this.b = b;
        return this;
    }

    public IntObjPair<B> setAB(int a, B b) {
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
        IntObjPair<?> that = (IntObjPair<?>) o;
        return a == that.a && Objects.equals(b, that.b);
    }

    @Override
    public int hashCode() {
        return (31 * a) + Objects.hashCode(b);
    }

    @Override
    public String toString() {
        return "IntObjPair{" + "a=" + a + ", b=" + b + '}';
    }


}


