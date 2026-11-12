

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


package com.twistral.tephrium.vectors;


import com.twistral.tephrium.TMath;
import java.util.Objects;


/**
 * A fast and mutable 4D float vector class with method chaining support.
 */
public class TVec4f {

    private float x, y, z, w;

    // Actual constructors

    public TVec4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public TVec4f() {
        this(0f, 0f, 0f, 0f);
    }

    // Copying constructors & functions

    public TVec4f(TVec4f other) {
        this(other.x, other.y, other.z, other.w);
    }

    public TVec4f copy() {
        return new TVec4f(this.x, this.y, this.z, this.w);
    }

    // Special static factory methods

    public static TVec4f zero()  { return new TVec4f(0f, 0f, 0f, 0f); }
    public static TVec4f one()   { return new TVec4f(1f, 1f, 1f, 1f); }
    public static TVec4f unitX() { return new TVec4f(1f, 0f, 0f, 0f); }
    public static TVec4f unitY() { return new TVec4f(0f, 1f, 0f, 0f); }
    public static TVec4f unitZ() { return new TVec4f(0f, 0f, 1f, 0f); }
    public static TVec4f unitW() { return new TVec4f(0f, 0f, 0f, 1f); }


    /*/////////////////////////////////////////////////////////////////*/
    /*////////////////////  ARITHMETIC OPERATIONS  ////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    // Addition

    public TVec4f add(float x, float y, float z, float w) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.w += w;
        return this;
    }

    public TVec4f add(TVec4f other) {
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
        this.w += other.w;
        return this;
    }

    public TVec4f addX(float x) { this.x += x; return this; }
    public TVec4f addY(float y) { this.y += y; return this; }
    public TVec4f addZ(float z) { this.z += z; return this; }
    public TVec4f addW(float w) { this.w += w; return this; }

    // Subtraction

    public TVec4f sub(float x, float y, float z, float w) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        this.w -= w;
        return this;
    }

    public TVec4f sub(TVec4f other) {
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
        this.w -= other.w;
        return this;
    }

    public TVec4f subX(float x) { this.x -= x; return this; }
    public TVec4f subY(float y) { this.y -= y; return this; }
    public TVec4f subZ(float z) { this.z -= z; return this; }
    public TVec4f subW(float w) { this.w -= w; return this; }

    // Scaling / Multiplication

    public TVec4f scale(float scalarX, float scalarY, float scalarZ, float scalarW) {
        this.x *= scalarX;
        this.y *= scalarY;
        this.z *= scalarZ;
        this.w *= scalarW;
        return this;
    }

    public TVec4f scale(TVec4f other) {
        this.x *= other.x;
        this.y *= other.y;
        this.z *= other.z;
        this.w *= other.w;
        return this;
    }

    public TVec4f scaleX(float scalarX) { this.x *= scalarX; return this; }
    public TVec4f scaleY(float scalarY) { this.y *= scalarY; return this; }
    public TVec4f scaleZ(float scalarZ) { this.z *= scalarZ; return this; }
    public TVec4f scaleW(float scalarW) { this.w *= scalarW; return this; }

    // Division

    public TVec4f div(float divisorX, float divisorY, float divisorZ, float divisorW) {
        this.x /= divisorX;
        this.y /= divisorY;
        this.z /= divisorZ;
        this.w /= divisorW;
        return this;
    }

    public TVec4f div(TVec4f other) {
        this.x /= other.x;
        this.y /= other.y;
        this.z /= other.z;
        this.w /= other.w;
        return this;
    }

    public TVec4f divX(float divisorX) { this.x /= divisorX; return this; }
    public TVec4f divY(float divisorY) { this.y /= divisorY; return this; }
    public TVec4f divZ(float divisorZ) { this.z /= divisorZ; return this; }
    public TVec4f divW(float divisorW) { this.w /= divisorW; return this; }


    /*/////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  METRICS  ///////////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    // Dot Product

    public static float dot(float x1, float y1, float z1, float w1,
                             float x2, float y2, float z2, float w2)
    {
        return x1 * x2 + y1 * y2 + z1 * z2 + w1 * w2;
    }

    public static float dot(TVec4f v1, TVec4f v2) {
        return dot(v1.x, v1.y, v1.z, v1.w, v2.x, v2.y, v2.z, v2.w);
    }

    public float dot(float otherX, float otherY, float otherZ, float otherW) {
        return this.x * otherX + this.y * otherY + this.z * otherZ + this.w * otherW;
    }

    public float dot(TVec4f other) { return this.dot(other.x, other.y, other.z, other.w); }

    // Length Squared

    public static float lengthSquared(float x, float y, float z, float w) {
        return x * x + y * y + z * z + w * w;
    }

    public static float lengthSquared(TVec4f vec) { return lengthSquared(vec.x, vec.y, vec.z, vec.w); }

    public float lengthSquared() {
        return x * x + y * y + z * z + w * w;
    }

    // Length

    public static float length(float x, float y, float z, float w) {
        return TMath.sqrt(x * x + y * y + z * z + w * w);
    }

    public static float length(TVec4f vec) { return length(vec.x, vec.y, vec.z, vec.w); }

    public float length() {
        return TMath.sqrt(x * x + y * y + z * z + w * w);
    }

    // Distance Squared

    public static float distanceSquared(float x1, float y1, float z1, float w1,
                                         float x2, float y2, float z2, float w2)
    {
        final float dx = x2 - x1;
        final float dy = y2 - y1;
        final float dz = z2 - z1;
        final float dw = w2 - w1;
        return dx * dx + dy * dy + dz * dz + dw * dw;
    }

    public static float distanceSquared(TVec4f v1, TVec4f v2) {
        final float dx = v2.x - v1.x;
        final float dy = v2.y - v1.y;
        final float dz = v2.z - v1.z;
        final float dw = v2.w - v1.w;
        return dx * dx + dy * dy + dz * dz + dw * dw;
    }

    public float distanceSquared(float otherX, float otherY, float otherZ, float otherW) {
        final float dx = otherX - this.x;
        final float dy = otherY - this.y;
        final float dz = otherZ - this.z;
        final float dw = otherW - this.w;
        return dx * dx + dy * dy + dz * dz + dw * dw;
    }

    public float distanceSquared(TVec4f other) {
        return this.distanceSquared(other.x, other.y, other.z, other.w);
    }

    // Distance

    public static float distance(float x1, float y1, float z1, float w1,
                                 float x2, float y2, float z2, float w2)
    {
        return TMath.sqrt(distanceSquared(x1, y1, z1, w1, x2, y2, z2, w2));
    }

    public static float distance(TVec4f v1, TVec4f v2) {
        return TMath.sqrt(distanceSquared(v1.x, v1.y, v1.z, v1.w, v2.x, v2.y, v2.z, v2.w));
    }

    public float distance(float otherX, float otherY, float otherZ, float otherW) {
        return TMath.sqrt(distanceSquared(x, y, z, w, otherX, otherY, otherZ, otherW));
    }

    public float distance(TVec4f other) {
        return TMath.sqrt(distanceSquared(x, y, z, w, other.x, other.y, other.z, other.w));
    }


    /*///////////////////////////////////////////////////////////////*/
    /*/////////////////////  GETTERS & SETTERS  /////////////////////*/
    /*///////////////////////////////////////////////////////////////*/


    public float getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }
    public float getW() { return w; }

    public TVec4f setX(float x) { this.x = x; return this; }
    public TVec4f setY(float y) { this.y = y; return this; }
    public TVec4f setZ(float z) { this.z = z; return this; }
    public TVec4f setW(float w) { this.w = w; return this; }

    public TVec4f set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }


    /*////////////////////////////////////////////////////////////////*/
    /*////////////////////  GEOMETRIC OPERATIONS  ////////////////////*/
    /*////////////////////////////////////////////////////////////////*/

    // Normalization

    /**
     * If this vector is not a zero vector, normalizes it in place.
     * @return this vector for method chaining
     */
    public TVec4f unit() {
        final float lenSq = this.lengthSquared();
        // Ignore zero and already normalized vectors
        if (!TMath.equals(lenSq, 0f) && !TMath.equals(lenSq, 1f)) {
            final float len = TMath.sqrt(lenSq);
            this.x /= len;
            this.y /= len;
            this.z /= len;
            this.w /= len;
        }
        return this;
    }

    // Symmetry

    public TVec4f symmetricalVectorTo(TVec4f other) {
        this.x = 2f * other.x - this.x;
        this.y = 2f * other.y - this.y;
        this.z = 2f * other.z - this.z;
        this.w = 2f * other.w - this.w;
        return this;
    }

    public TVec4f symmetricalVectorTo(float otherX, float otherY, float otherZ, float otherW) {
        this.x = 2f * otherX - this.x;
        this.y = 2f * otherY - this.y;
        this.z = 2f * otherZ - this.z;
        this.w = 2f * otherW - this.w;
        return this;
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*//////////////////////  BOOLEAN FUNCTIONS  //////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    // Zero vectors

    public static boolean isZeroVector(float x, float y, float z, float w) {
        return TMath.equals(x, 0f) && TMath.equals(y, 0f) && TMath.equals(z, 0f) && TMath.equals(w, 0f);
    }

    public static boolean isZeroVector(TVec4f vec) { return isZeroVector(vec.x, vec.y, vec.z, vec.w); }
    public boolean isZeroVector() { return isZeroVector(this.x, this.y, this.z, this.w); }

    // Unit vectors

    public static boolean isUnitVector(float x, float y, float z, float w) {
        return TMath.equals(lengthSquared(x, y, z, w), 1f);
    }

    public static boolean isUnitVector(TVec4f vec) { return isUnitVector(vec.x, vec.y, vec.z, vec.w); }
    public boolean isUnitVector() { return isUnitVector(this.x, this.y, this.z, this.w); }

    // Orthogonal vectors

    public static boolean areOrthogonal(float x1, float y1, float z1, float w1,
                                        float x2, float y2, float z2, float w2)
    {
        return TMath.equals(dot(x1, y1, z1, w1, x2, y2, z2, w2), 0f);
    }

    public static boolean areOrthogonal(TVec4f v1, TVec4f v2) {
        return areOrthogonal(v1.x, v1.y, v1.z, v1.w, v2.x, v2.y, v2.z, v2.w);
    }

    public boolean isOrthogonalTo(TVec4f vector) {
        return areOrthogonal(this.x, this.y, this.z, this.w, vector.x, vector.y, vector.z, vector.w);
    }


    /*//////////////////////////////////////////////////////////////////*/
    /*////////////////////////  OBJECT METHODS  ////////////////////////*/
    /*//////////////////////////////////////////////////////////////////*/


    public boolean equals(float x, float y, float z, float w) {
        return TMath.equals(this.x, x) && TMath.equals(this.y, y) &&
               TMath.equals(this.z, z) && TMath.equals(this.w, w);
    }

    public boolean equals(TVec4f v) {
        if (v == null) return false;
        return equals(v.x, v.y, v.z, v.w);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TVec4f v = (TVec4f) o;
        return equals(v.x, v.y, v.z, v.w);
    }

    @Override
    public int hashCode() {
        int result = Float.hashCode(this.x);
        result = 31 * result + Float.hashCode(this.y);
        result = 31 * result + Float.hashCode(this.z);
        result = 31 * result + Float.hashCode(this.w);
        return result;
    }


}

