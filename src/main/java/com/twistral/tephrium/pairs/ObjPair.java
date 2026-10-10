
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


package com.twistral.tephrium.pairs;


import java.util.Objects;


/**
 * A fast and mutable pair class holding two objects of any types.
 *
 * @param <A> type of the first object
 * @param <B> type of the second object
 */
public class ObjPair<A, B> {

    private A a;
    private B b;

    public ObjPair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public ObjPair() {
        this(null, null);
    }

    public ObjPair<A, B> setA(A a) {
        this.a = a;
        return this;
    }

    public ObjPair<A, B> setB(B b) {
        this.b = b;
        return this;
    }

    public ObjPair<A, B> setAB(A a, B b) {
        this.a = a;
        this.b = b;
        return this;
    }

    public A getA() { return a; }
    public B getB() { return b; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ObjPair<?, ?> objPair = (ObjPair<?, ?>) o;
        return Objects.equals(a, objPair.a) && Objects.equals(b, objPair.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }

    @Override
    public String toString() {
        return "ObjPair{" + "a=" + a + ", b=" + b + '}';
    }

}


