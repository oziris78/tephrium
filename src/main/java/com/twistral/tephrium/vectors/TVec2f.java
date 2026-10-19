

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


/**
 * A fast and mutable 2D float vector class with method chaining support.
 */
public class TVec2f {

    private float x, y;

    // Actual constructors

    public TVec2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public TVec2f() {
        this(0f, 0f);
    }

    // Copy constructors

    public TVec2f(TVec2f other) {
        this(other.x, other.y);
    }

    public TVec2f copy(){
        return new TVec2f(this.x, this.y);
    }

    // Special static factory methods

    public static TVec2f zero()  { return new TVec2f(0f, 0f); }
    public static TVec2f one()   { return new TVec2f(1f, 1f); }
    public static TVec2f unitX() { return new TVec2f(1f, 0f); }
    public static TVec2f unitY() { return new TVec2f(0f, 1f); }


    /*/////////////////////////////////////////////////////////////////*/
    /*////////////////////  ARITHMETIC OPERATIONS  ////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    // Addition

    public TVec2f add(TVec2f other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    public TVec2f add(float x, float y) {
        this.x += x;
        this.y += y;
        return this;
    }

    // Subtraction

    public TVec2f sub(TVec2f other) {
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }

    public TVec2f sub(float x, float y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    // Scaling / Multiplication

    public TVec2f scale(float scalarX, float scalarY) {
        this.x *= scalarX;
        this.y *= scalarY;
        return this;
    }

    public TVec2f scale(TVec2f other) {
        this.x *= other.x;
        this.y *= other.y;
        return this;
    }

    public TVec2f scaleX(float scalarX) {
        this.x *= scalarX;
        return this;
    }

    public TVec2f scaleY(float scalarY) {
        this.y *= scalarY;
        return this;
    }

    public TVec2f scale(float scalarXY) {
        this.x *= scalarXY;
        this.y *= scalarXY;
        return this;
    }

    // Division

    public TVec2f div(float divisorX, float divisorY) {
        this.x /= divisorX;
        this.y /= divisorY;
        return this;
    }

    public TVec2f div(TVec2f other) {
        this.x /= other.x;
        this.y /= other.y;
        return this;
    }

    public TVec2f divX(float divisorX) {
        this.x /= divisorX;
        return this;
    }

    public TVec2f divY(float divisorY) {
        this.y /= divisorY;
        return this;
    }

    public TVec2f div(float divisorXY) {
        this.x /= divisorXY;
        this.y /= divisorXY;
        return this;
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  METRICS  ///////////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    // Dot Product

    public static float dot(float x1, float y1, float x2, float y2) {
        return x1 * x2 + y1 * y2;
    }

    public static float dot(TVec2f v1, TVec2f v2) {
        return v1.x * v2.x + v1.y * v2.y;
    }

    public float dot(TVec2f other) {
        return this.x * other.x + this.y * other.y;
    }

    public float dot(float otherX, float otherY) {
        return this.x * otherX + this.y * otherY;
    }

    // Length Squared

    public static float lengthSquared(float x, float y) {
        return x * x + y * y;
    }

    public static float lengthSquared(TVec2f vec) {
        return vec.x * vec.x + vec.y * vec.y;
    }

    public float lengthSquared() {
        return this.x * this.x + this.y * this.y;
    }

    // Length

    public static float length(float x, float y) {
        return TMath.sqrt(x * x + y * y);
    }

    public static float length(TVec2f vec) {
        return TMath.sqrt(vec.x * vec.x + vec.y * vec.y);
    }

    public float length() {
        return TMath.sqrt(this.x * this.x + this.y * this.y);
    }

    // Distance Squared

    public static float distanceSquared(float x1, float y1, float x2, float y2) {
        final float dx = x2 - x1;
        final float dy = y2 - y1;
        return dx * dx + dy * dy;
    }

    public static float distanceSquared(TVec2f v1, TVec2f v2) {
        final float dx = v2.x - v1.x;
        final float dy = v2.y - v1.y;
        return dx * dx + dy * dy;
    }

    public float distanceSquared(TVec2f other) {
        final float dx = other.x - this.x;
        final float dy = other.y - this.y;
        return dx * dx + dy * dy;
    }

    public float distanceSquared(float otherX, float otherY) {
        final float dx = otherX - this.x;
        final float dy = otherY - this.y;
        return dx * dx + dy * dy;
    }

    // Distance

    public static float distance(float x1, float y1, float x2, float y2) {
        final float dx = x2 - x1;
        final float dy = y2 - y1;
        return TMath.sqrt(dx * dx + dy * dy);
    }

    public static float distance(TVec2f v1, TVec2f v2) {
        final float dx = v2.x - v1.x;
        final float dy = v2.y - v1.y;
        return TMath.sqrt(dx * dx + dy * dy);
    }

    public float distance(TVec2f other) {
        final float dx = other.x - this.x;
        final float dy = other.y - this.y;
        return TMath.sqrt(dx * dx + dy * dy);
    }

    public float distance(float otherX, float otherY) {
        final float dx = otherX - this.x;
        final float dy = otherY - this.y;
        return TMath.sqrt(dx * dx + dy * dy);
    }


    /*///////////////////////////////////////////////////////////////*/
    /*/////////////////////  GETTERS & SETTERS  /////////////////////*/
    /*///////////////////////////////////////////////////////////////*/

    // Getters

    public float getX() { return x; }
    public float getY() { return y; }

    // Setters

    public TVec2f set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }


    public TVec2f setX(float x) {
        this.x = x;
        return this;
    }


    public TVec2f setY(float y) {
        this.y = y;
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
    public TVec2f unit() {
        final float lenSq = x * x + y * y;
        // Ignore zero and already normalized vectors
        if (!TMath.equals(lenSq, 0f) && !TMath.equals(lenSq, 1f)) {
            final float len = TMath.sqrt(lenSq);
            this.x /= len;
            this.y /= len;
        }
        return this;
    }

    // Rotation

    public TVec2f rotate(float cos, float sin) {
        final float nx = x * cos - y * sin;
        final float ny = x * sin + y * cos;
        this.x = nx;
        this.y = ny;
        return this;
    }

    public TVec2f rotateRad(float radians) {
        final float cos = TMath.cos(radians);
        final float sin = TMath.sin(radians);

        final float nx = x * cos - y * sin;
        final float ny = x * sin + y * cos;
        this.x = nx;
        this.y = ny;
        return this;
    }

    public TVec2f rotateDeg(float degrees) {
        return this.rotateRad(TMath.toRadians(degrees));
    }

    public TVec2f rotateAround(TVec2f reference, float cos, float sin) {
        return this.sub(reference).rotate(cos, sin).add(reference);
    }

    public TVec2f rotateAroundRad(TVec2f reference, float radians) {
        return this.sub(reference).rotateRad(radians).add(reference);
    }

    public TVec2f rotateAroundDeg(TVec2f reference, float degrees) {
        return this.sub(reference).rotateDeg(degrees).add(reference);
    }

    public TVec2f rotateAround(float refX, float refY, float cos, float sin) {
        return this.sub(refX, refY).rotate(cos, sin).add(refX, refY);
    }

    public TVec2f rotateAroundRad(float refX, float refY, float radians) {
        return this.sub(refX, refY).rotateRad(radians).add(refX, refY);
    }

    public TVec2f rotateAroundDeg(float refX, float refY, float degrees) {
        return this.sub(refX, refY).rotateDeg(degrees).add(refX, refY);
    }

    // Fast 90 / 180 Degree Rotations

    public TVec2f rotate180Deg() {
        this.x = -this.x;
        this.y = -this.y;
        return this;
    }

    public TVec2f rotate90DegClockwise() {
        final float temp = -this.x;
        this.x = this.y;
        this.y = temp;
        return this;
    }

    public TVec2f rotate90DegCounterClockwise() {
        final float temp = -this.y;
        this.y = this.x;
        this.x = temp;
        return this;
    }

    // Symmetry

    public TVec2f symmetricalVectorTo(TVec2f other) {
        this.x = 2f * other.x - this.x;
        this.y = 2f * other.y - this.y;
        return this;
    }

    public TVec2f symmetricalVectorTo(float otherX, float otherY) {
        this.x = 2f * otherX - this.x;
        this.y = 2f * otherY - this.y;
        return this;
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*//////////////////////  BOOLEAN FUNCTIONS  //////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    // Zero vectors

    public static boolean isZeroVector(TVec2f vector) {
        return TMath.equals(vector.x, 0f) && TMath.equals(vector.y, 0f);
    }

    public static boolean isZeroVector(float x, float y) {
        return TMath.equals(x, 0f) && TMath.equals(y, 0f);
    }

    public boolean isZeroVector() {
        return TMath.equals(this.x, 0f) && TMath.equals(this.y, 0f);
    }

    // Unit vectors

    public static boolean isUnitVector(TVec2f vector) {
        return TMath.equals(vector.x * vector.x + vector.y * vector.y, 1f);
    }

    public static boolean isUnitVector(float x, float y) {
        return TMath.equals(x * x + y * y, 1f);
    }

    public boolean isUnitVector() {
        return TMath.equals(this.x * this.x + this.y * this.y, 1f);
    }

    // Orthogonal vectors

    public static boolean areOrthogonal(TVec2f v1, TVec2f v2) {
        final float dot = v1.x * v2.x + v1.y * v2.y;
        return TMath.equals(dot, 0f);
    }

    public boolean isOrthogonalTo(TVec2f vector) {
        final float dot = this.x * vector.x + this.y * vector.y;
        return TMath.equals(dot, 0f);
    }

    // Parallel vectors

    public static boolean areParallel(TVec2f v1, TVec2f v2) {
        final float crossProduct = v1.x * v2.y - v1.y * v2.x;
        return TMath.equals(crossProduct, 0f);
    }

    public boolean isParallelTo(TVec2f vector) {
        final float crossProduct = this.x * vector.y - this.y * vector.x;
        return TMath.equals(crossProduct, 0f);
    }


    /*//////////////////////////////////////////////////////////////////*/
    /*////////////////////////  OBJECT METHODS  ////////////////////////*/
    /*//////////////////////////////////////////////////////////////////*/


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TVec2f v = (TVec2f) o;
        return TMath.equals(this.x, v.x) && TMath.equals(this.y, v.y);
    }

    public boolean equals(TVec2f v) {
        if (v == null) return false;
        return TMath.equals(this.x, v.x) && TMath.equals(this.y, v.y);
    }

    public boolean equals(float x, float y) {
        return TMath.equals(this.x, x) && TMath.equals(this.y, y);
    }

    @Override
    public int hashCode() {
        return 31 * Float.hashCode(this.x) + Float.hashCode(this.y);
    }

    @Override
    public String toString() {
        return "TVec2f{" + "x=" + this.x + ", y=" + this.y + '}';
    }


}

