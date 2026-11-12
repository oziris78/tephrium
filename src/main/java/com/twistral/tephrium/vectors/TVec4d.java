

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
 * A fast and mutable 4D double vector class with method chaining support.
 */
public class TVec4d {

    private double x, y, z, w;

    // Actual constructors

    public TVec4d(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public TVec4d() {
        this(0d, 0d, 0d, 0d);
    }

    // Copying constructors & functions

    public TVec4d(TVec4d other) {
        this(other.x, other.y, other.z, other.w);
    }

    public TVec4d copy() {
        return new TVec4d(this.x, this.y, this.z, this.w);
    }

    // Special static factory methods

    public static TVec4d zero()  { return new TVec4d(0d, 0d, 0d, 0d); }
    public static TVec4d one()   { return new TVec4d(1d, 1d, 1d, 1d); }
    public static TVec4d unitX() { return new TVec4d(1d, 0d, 0d, 0d); }
    public static TVec4d unitY() { return new TVec4d(0d, 1d, 0d, 0d); }
    public static TVec4d unitZ() { return new TVec4d(0d, 0d, 1d, 0d); }
    public static TVec4d unitW() { return new TVec4d(0d, 0d, 0d, 1d); }


    /*/////////////////////////////////////////////////////////////////*/
    /*////////////////////  ARITHMETIC OPERATIONS  ////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    // Addition

    public TVec4d add(double x, double y, double z, double w) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.w += w;
        return this;
    }

    public TVec4d add(TVec4d other) {
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
        this.w += other.w;
        return this;
    }

    public TVec4d addX(double x) { this.x += x; return this; }
    public TVec4d addY(double y) { this.y += y; return this; }
    public TVec4d addZ(double z) { this.z += z; return this; }
    public TVec4d addW(double w) { this.w += w; return this; }

    // Subtraction

    public TVec4d sub(double x, double y, double z, double w) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        this.w -= w;
        return this;
    }

    public TVec4d sub(TVec4d other) {
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
        this.w -= other.w;
        return this;
    }

    public TVec4d subX(double x) { this.x -= x; return this; }
    public TVec4d subY(double y) { this.y -= y; return this; }
    public TVec4d subZ(double z) { this.z -= z; return this; }
    public TVec4d subW(double w) { this.w -= w; return this; }

    // Scaling / Multiplication

    public TVec4d scale(double scalarX, double scalarY, double scalarZ, double scalarW) {
        this.x *= scalarX;
        this.y *= scalarY;
        this.z *= scalarZ;
        this.w *= scalarW;
        return this;
    }

    public TVec4d scale(TVec4d other) {
        this.x *= other.x;
        this.y *= other.y;
        this.z *= other.z;
        this.w *= other.w;
        return this;
    }

    public TVec4d scaleX(double scalarX) { this.x *= scalarX; return this; }
    public TVec4d scaleY(double scalarY) { this.y *= scalarY; return this; }
    public TVec4d scaleZ(double scalarZ) { this.z *= scalarZ; return this; }
    public TVec4d scaleW(double scalarW) { this.w *= scalarW; return this; }

    // Division

    public TVec4d div(double divisorX, double divisorY, double divisorZ, double divisorW) {
        this.x /= divisorX;
        this.y /= divisorY;
        this.z /= divisorZ;
        this.w /= divisorW;
        return this;
    }

    public TVec4d div(TVec4d other) {
        this.x /= other.x;
        this.y /= other.y;
        this.z /= other.z;
        this.w /= other.w;
        return this;
    }

    public TVec4d divX(double divisorX) { this.x /= divisorX; return this; }
    public TVec4d divY(double divisorY) { this.y /= divisorY; return this; }
    public TVec4d divZ(double divisorZ) { this.z /= divisorZ; return this; }
    public TVec4d divW(double divisorW) { this.w /= divisorW; return this; }


    /*/////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  METRICS  ///////////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    // Dot Product

    public static double dot(double x1, double y1, double z1, double w1,
                             double x2, double y2, double z2, double w2)
    {
        return x1 * x2 + y1 * y2 + z1 * z2 + w1 * w2;
    }

    public static double dot(TVec4d v1, TVec4d v2) {
        return dot(v1.x, v1.y, v1.z, v1.w, v2.x, v2.y, v2.z, v2.w);
    }

    public double dot(double otherX, double otherY, double otherZ, double otherW) {
        return this.x * otherX + this.y * otherY + this.z * otherZ + this.w * otherW;
    }

    public double dot(TVec4d other) { return this.dot(other.x, other.y, other.z, other.w); }

    // Length Squared

    public static double lengthSquared(double x, double y, double z, double w) {
        return x * x + y * y + z * z + w * w;
    }

    public static double lengthSquared(TVec4d vec) { return lengthSquared(vec.x, vec.y, vec.z, vec.w); }

    public double lengthSquared() {
        return x * x + y * y + z * z + w * w;
    }

    // Length

    public static double length(double x, double y, double z, double w) {
        return TMath.sqrt(x * x + y * y + z * z + w * w);
    }

    public static double length(TVec4d vec) { return length(vec.x, vec.y, vec.z, vec.w); }

    public double length() {
        return TMath.sqrt(x * x + y * y + z * z + w * w);
    }

    // Distance Squared

    public static double distanceSquared(double x1, double y1, double z1, double w1,
                                         double x2, double y2, double z2, double w2)
    {
        final double dx = x2 - x1;
        final double dy = y2 - y1;
        final double dz = z2 - z1;
        final double dw = w2 - w1;
        return dx * dx + dy * dy + dz * dz + dw * dw;
    }

    public static double distanceSquared(TVec4d v1, TVec4d v2) {
        final double dx = v2.x - v1.x;
        final double dy = v2.y - v1.y;
        final double dz = v2.z - v1.z;
        final double dw = v2.w - v1.w;
        return dx * dx + dy * dy + dz * dz + dw * dw;
    }

    public double distanceSquared(double otherX, double otherY, double otherZ, double otherW) {
        final double dx = otherX - this.x;
        final double dy = otherY - this.y;
        final double dz = otherZ - this.z;
        final double dw = otherW - this.w;
        return dx * dx + dy * dy + dz * dz + dw * dw;
    }

    public double distanceSquared(TVec4d other) {
        return this.distanceSquared(other.x, other.y, other.z, other.w);
    }

    // Distance

    public static double distance(double x1, double y1, double z1, double w1,
                                  double x2, double y2, double z2, double w2)
    {
        return TMath.sqrt(distanceSquared(x1, y1, z1, w1, x2, y2, z2, w2));
    }

    public static double distance(TVec4d v1, TVec4d v2) {
        return TMath.sqrt(distanceSquared(v1.x, v1.y, v1.z, v1.w, v2.x, v2.y, v2.z, v2.w));
    }

    public double distance(double otherX, double otherY, double otherZ, double otherW) {
        return TMath.sqrt(distanceSquared(x, y, z, w, otherX, otherY, otherZ, otherW));
    }

    public double distance(TVec4d other) {
        return TMath.sqrt(distanceSquared(x, y, z, w, other.x, other.y, other.z, other.w));
    }


    /*///////////////////////////////////////////////////////////////*/
    /*/////////////////////  GETTERS & SETTERS  /////////////////////*/
    /*///////////////////////////////////////////////////////////////*/


    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }
    public double getW() { return w; }

    public TVec4d setX(double x) { this.x = x; return this; }
    public TVec4d setY(double y) { this.y = y; return this; }
    public TVec4d setZ(double z) { this.z = z; return this; }
    public TVec4d setW(double w) { this.w = w; return this; }

    public TVec4d set(double x, double y, double z, double w) {
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
    public TVec4d unit() {
        final double lenSq = this.lengthSquared();
        // Ignore zero and already normalized vectors
        if (!TMath.equals(lenSq, 0d) && !TMath.equals(lenSq, 1d)) {
            final double len = TMath.sqrt(lenSq);
            this.x /= len;
            this.y /= len;
            this.z /= len;
            this.w /= len;
        }
        return this;
    }

    // Symmetry

    public TVec4d symmetricalVectorTo(TVec4d other) {
        this.x = 2d * other.x - this.x;
        this.y = 2d * other.y - this.y;
        this.z = 2d * other.z - this.z;
        this.w = 2d * other.w - this.w;
        return this;
    }

    public TVec4d symmetricalVectorTo(double otherX, double otherY, double otherZ, double otherW) {
        this.x = 2d * otherX - this.x;
        this.y = 2d * otherY - this.y;
        this.z = 2d * otherZ - this.z;
        this.w = 2d * otherW - this.w;
        return this;
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*//////////////////////  BOOLEAN FUNCTIONS  //////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    // Zero vectors

    public static boolean isZeroVector(double x, double y, double z, double w) {
        return TMath.equals(x, 0d) && TMath.equals(y, 0d) && TMath.equals(z, 0d) && TMath.equals(w, 0d);
    }

    public static boolean isZeroVector(TVec4d vec) { return isZeroVector(vec.x, vec.y, vec.z, vec.w); }
    public boolean isZeroVector() { return isZeroVector(this.x, this.y, this.z, this.w); }

    // Unit vectors

    public static boolean isUnitVector(double x, double y, double z, double w) {
        return TMath.equals(lengthSquared(x, y, z, w), 1d);
    }

    public static boolean isUnitVector(TVec4d vec) { return isUnitVector(vec.x, vec.y, vec.z, vec.w); }
    public boolean isUnitVector() { return isUnitVector(this.x, this.y, this.z, this.w); }

    // Orthogonal vectors

    public static boolean areOrthogonal(double x1, double y1, double z1, double w1,
                                        double x2, double y2, double z2, double w2)
    {
        return TMath.equals(dot(x1, y1, z1, w1, x2, y2, z2, w2), 0d);
    }

    public static boolean areOrthogonal(TVec4d v1, TVec4d v2) {
        return areOrthogonal(v1.x, v1.y, v1.z, v1.w, v2.x, v2.y, v2.z, v2.w);
    }

    public boolean isOrthogonalTo(TVec4d vector) {
        return areOrthogonal(this.x, this.y, this.z, this.w, vector.x, vector.y, vector.z, vector.w);
    }


    /*//////////////////////////////////////////////////////////////////*/
    /*////////////////////////  OBJECT METHODS  ////////////////////////*/
    /*//////////////////////////////////////////////////////////////////*/


    public boolean equals(double x, double y, double z, double w) {
        return TMath.equals(this.x, x) && TMath.equals(this.y, y) &&
               TMath.equals(this.z, z) && TMath.equals(this.w, w);
    }

    public boolean equals(TVec4d v) {
        if (v == null) return false;
        return equals(v.x, v.y, v.z, v.w);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TVec4d v = (TVec4d) o;
        return equals(v.x, v.y, v.z, v.w);
    }

    @Override
    public int hashCode() {
        int result = Double.hashCode(this.x);
        result = 31 * result + Double.hashCode(this.y);
        result = 31 * result + Double.hashCode(this.z);
        result = 31 * result + Double.hashCode(this.w);
        return result;
    }


}

