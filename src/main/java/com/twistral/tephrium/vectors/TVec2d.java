

// Copyright 2020-2026 Oğuzhan Topaloğlu
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
 * A fast and mutable 2D double vector class with method chaining support.
 */
public class TVec2d {

    private double x, y;

    // Actual constructors

    public TVec2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public TVec2d() {
        this(0d, 0d);
    }

    // Copy constructors

    public TVec2d(TVec2d other) {
        this(other.x, other.y);
    }

    public TVec2d copy(){
        return new TVec2d(this.x, this.y);
    }

    // Special static factory methods

    public static TVec2d zero()  { return new TVec2d(0d, 0d); }
    public static TVec2d one()   { return new TVec2d(1d, 1d); }
    public static TVec2d unitX() { return new TVec2d(1d, 0d); }
    public static TVec2d unitY() { return new TVec2d(0d, 1d); }


    /*/////////////////////////////////////////////////////////////////*/
    /*////////////////////  ARITHMETIC OPERATIONS  ////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    // Addition

    public TVec2d add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public TVec2d add(TVec2d other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    public TVec2d addX(double x) { this.x += x; return this; }
    public TVec2d addY(double y) { this.y += y; return this; }

    // Subtraction

    public TVec2d sub(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public TVec2d sub(TVec2d other) {
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }

    public TVec2d subX(double x) { this.x -= x; return this; }
    public TVec2d subY(double y) { this.y -= y; return this; }

    // Scaling / Multiplication

    public TVec2d scale(double scalarX, double scalarY) {
        this.x *= scalarX;
        this.y *= scalarY;
        return this;
    }

    public TVec2d scale(TVec2d other) {
        this.x *= other.x;
        this.y *= other.y;
        return this;
    }

    public TVec2d scaleX(double scalarX) { this.x *= scalarX; return this; }
    public TVec2d scaleY(double scalarY) { this.y *= scalarY; return this; }

    // Division

    public TVec2d div(double divisorX, double divisorY) {
        this.x /= divisorX;
        this.y /= divisorY;
        return this;
    }

    public TVec2d div(TVec2d other) {
        this.x /= other.x;
        this.y /= other.y;
        return this;
    }

    public TVec2d divX(double divisorX) { this.x /= divisorX; return this; }
    public TVec2d divY(double divisorY) { this.y /= divisorY; return this; }


    /*/////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  METRICS  ///////////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    // Dot Product

    public static double dot(double x1, double y1, double x2, double y2) {
        return x1 * x2 + y1 * y2;
    }

    public static double dot(TVec2d v1, TVec2d v2) { return dot(v1.x, v1.y, v2.x, v2.y); }

    public double dot(double otherX, double otherY) {
        return this.x * otherX + this.y * otherY;
    }

    public double dot(TVec2d other) { return this.dot(other.x, other.y); }

    // Length Squared

    public static double lengthSquared(double x, double y) {
        return x * x + y * y;
    }

    public static double lengthSquared(TVec2d vec) { return lengthSquared(vec.x, vec.y); }

    public double lengthSquared() {
        return x * x + y * y;
    }

    // Length

    public static double length(double x, double y) {
        return TMath.sqrt(x * x + y * y);
    }

    public static double length(TVec2d vec) { return length(vec.x, vec.y); }

    public double length() {
        return TMath.sqrt(x * x + y * y);
    }

    // Distance Squared

    public static double distanceSquared(double x1, double y1, double x2, double y2) {
        final double dx = x2 - x1;
        final double dy = y2 - y1;
        return dx * dx + dy * dy;
    }

    public static double distanceSquared(TVec2d v1, TVec2d v2) {
        final double dx = v2.x - v1.x;
        final double dy = v2.y - v1.y;
        return dx * dx + dy * dy;
    }

    public double distanceSquared(double otherX, double otherY) {
        final double dx = otherX - this.x;
        final double dy = otherY - this.y;
        return dx * dx + dy * dy;
    }

    public double distanceSquared(TVec2d other) { return this.distanceSquared(other.x, other.y); }

    // Distance

    public static double distance(double x1, double y1, double x2, double y2) {
        return TMath.sqrt(distanceSquared(x1, y1, x2, y2));
    }

    public static double distance(TVec2d v1, TVec2d v2) {
        return TMath.sqrt(distanceSquared(v1.x, v1.y, v2.x, v2.y));
    }

    public double distance(double otherX, double otherY) {
        return TMath.sqrt(distanceSquared(x, y, otherX, otherY));
    }

    public double distance(TVec2d other) {
        return TMath.sqrt(distanceSquared(x, y, other.x, other.y));
    }


    // Cross product

    public static double cross(double x1, double y1, double x2, double y2) {
        return x1 * y2 - y1 * x2;
    }

    public static double cross(TVec2d v1, TVec2d v2) { return cross(v1.x, v1.y, v2.x, v2.y); }

    public double cross(double otherX, double otherY) {
        return this.x * otherY - this.y * otherX;
    }

    public double cross(TVec2d other) { return this.cross(other.x, other.y); }


    /*///////////////////////////////////////////////////////////////*/
    /*/////////////////////  GETTERS & SETTERS  /////////////////////*/
    /*///////////////////////////////////////////////////////////////*/


    public double getX() { return x; }
    public double getY() { return y; }

    public TVec2d setX(double x) { this.x = x; return this; }
    public TVec2d setY(double y) { this.y = y; return this; }

    public TVec2d set(double x, double y) {
        this.x = x;
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
    public TVec2d unit() {
        final double lenSq = this.lengthSquared();
        // Ignore zero and already normalized vectors
        if (!TMath.equals(lenSq, 0d) && !TMath.equals(lenSq, 1d)) {
            final double len = TMath.sqrt(lenSq);
            this.x /= len;
            this.y /= len;
        }
        return this;
    }

    // Rotation

    public TVec2d rotate(double cos, double sin) {
        final double nx = x * cos - y * sin;
        final double ny = x * sin + y * cos;
        this.x = nx;
        this.y = ny;
        return this;
    }

    public TVec2d rotateRad(double radians) {
        return this.rotate(TMath.cos(radians), TMath.sin(radians));
    }

    public TVec2d rotateDeg(double degrees) {
        return this.rotateRad(TMath.toRadians(degrees));
    }

    public TVec2d rotateAround(TVec2d reference, double cos, double sin) {
        return this.sub(reference).rotate(cos, sin).add(reference);
    }

    public TVec2d rotateAroundRad(TVec2d reference, double radians) {
        return this.sub(reference).rotateRad(radians).add(reference);
    }

    public TVec2d rotateAroundDeg(TVec2d reference, double degrees) {
        return this.sub(reference).rotateDeg(degrees).add(reference);
    }

    public TVec2d rotateAround(double refX, double refY, double cos, double sin) {
        return this.sub(refX, refY).rotate(cos, sin).add(refX, refY);
    }

    public TVec2d rotateAroundRad(double refX, double refY, double radians) {
        return this.sub(refX, refY).rotateRad(radians).add(refX, refY);
    }

    public TVec2d rotateAroundDeg(double refX, double refY, double degrees) {
        return this.sub(refX, refY).rotateDeg(degrees).add(refX, refY);
    }

    // Fast 90 / 180 Degree Rotations

    public TVec2d rotate180Deg() {
        this.x = -this.x;
        this.y = -this.y;
        return this;
    }

    public TVec2d rotate90DegClockwise() {
        final double temp = -this.x;
        this.x = this.y;
        this.y = temp;
        return this;
    }

    public TVec2d rotate90DegCounterClockwise() {
        final double temp = -this.y;
        this.y = this.x;
        this.x = temp;
        return this;
    }

    // Symmetry

    public TVec2d symmetricalVectorTo(TVec2d other) {
        this.x = 2d * other.x - this.x;
        this.y = 2d * other.y - this.y;
        return this;
    }

    public TVec2d symmetricalVectorTo(double otherX, double otherY) {
        this.x = 2d * otherX - this.x;
        this.y = 2d * otherY - this.y;
        return this;
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*//////////////////////  BOOLEAN FUNCTIONS  //////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    // Zero vectors

    public static boolean isZeroVector(double x, double y) {
        return TMath.equals(x, 0d) && TMath.equals(y, 0d);
    }

    public static boolean isZeroVector(TVec2d vector) { return isZeroVector(vector.x, vector.y); }
    public boolean isZeroVector() { return isZeroVector(this.x, this.y); }

    // Unit vectors

    public static boolean isUnitVector(double x, double y) {
        return TMath.equals(lengthSquared(x, y), 1d);
    }

    public static boolean isUnitVector(TVec2d vector) { return isUnitVector(vector.x, vector.y); }
    public boolean isUnitVector() { return isUnitVector(this.x, this.y); }

    // Orthogonal vectors

    public static boolean areOrthogonal(double x1, double y1, double x2, double y2) {
        return TMath.equals(dot(x1, y1, x2, y2), 0d);
    }

    public static boolean areOrthogonal(TVec2d v1, TVec2d v2) { return areOrthogonal(v1.x, v1.y, v2.x, v2.y); }
    public boolean isOrthogonalTo(TVec2d vector) { return areOrthogonal(this.x, this.y, vector.x, vector.y); }

    // Parallel vectors

    public static boolean areParallel(double x1, double y1, double x2, double y2) {
        return TMath.equals(cross(x1, y1, x2, y2), 0d);
    }

    public static boolean areParallel(TVec2d v1, TVec2d v2) { return areParallel(v1.x, v1.y, v2.x, v2.y); }
    public boolean isParallelTo(TVec2d vector) {return areParallel(this.x, this.y, vector.x, vector.y); }


    /*//////////////////////////////////////////////////////////////////*/
    /*////////////////////////  OBJECT METHODS  ////////////////////////*/
    /*//////////////////////////////////////////////////////////////////*/


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TVec2d v = (TVec2d) o;
        return TMath.equals(this.x, v.x) && TMath.equals(this.y, v.y);
    }

    public boolean equals(TVec2d v) {
        if (v == null) return false;
        return TMath.equals(this.x, v.x) && TMath.equals(this.y, v.y);
    }

    public boolean equals(double x, double y) {
        return TMath.equals(this.x, x) && TMath.equals(this.y, y);
    }

    @Override
    public int hashCode() {
        return 31 * Double.hashCode(this.x) + Double.hashCode(this.y);
    }

    @Override
    public String toString() {
        return "TVec2d{" + "x=" + this.x + ", y=" + this.y + '}';
    }


}

