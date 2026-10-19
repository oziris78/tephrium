

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
 * A fast and mutable 3D double vector class with method chaining support.
 */
public class TVec3d {

    private double x, y, z;

    // Actual constructors

    public TVec3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public TVec3d() {
        this(0d, 0d, 0d);
    }

    // Copy constructors

    public TVec3d(TVec3d other) {
        this(other.x, other.y, other.z);
    }

    public TVec3d copy(){
        return new TVec3d(this.x, this.y, this.z);
    }

    // Special static factory methods

    public static TVec3d zero()  { return new TVec3d(0d, 0d, 0d); }
    public static TVec3d one()   { return new TVec3d(1d, 1d, 1d); }
    public static TVec3d unitX() { return new TVec3d(1d, 0d, 0d); }
    public static TVec3d unitY() { return new TVec3d(0d, 1d, 0d); }
    public static TVec3d unitZ() { return new TVec3d(0d, 0d, 1d); }


    /*/////////////////////////////////////////////////////////////////*/
    /*////////////////////  ARITHMETIC OPERATIONS  ////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    // Addition

    public TVec3d add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public TVec3d add(TVec3d other) {
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
        return this;
    }

    public TVec3d addX(double x) { this.x += x; return this; }
    public TVec3d addY(double y) { this.y += y; return this; }
    public TVec3d addZ(double z) { this.z += z; return this; }

    // Subtraction

    public TVec3d sub(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public TVec3d sub(TVec3d other) {
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
        return this;
    }

    public TVec3d subX(double x) { this.x -= x; return this; }
    public TVec3d subY(double y) { this.y -= y; return this; }
    public TVec3d subZ(double z) { this.z -= z; return this; }

    // Scaling / Multiplication

    public TVec3d scale(double scalarX, double scalarY, double scalarZ) {
        this.x *= scalarX;
        this.y *= scalarY;
        this.z *= scalarZ;
        return this;
    }

    public TVec3d scale(TVec3d other) {
        this.x *= other.x;
        this.y *= other.y;
        this.z *= other.z;
        return this;
    }

    public TVec3d scaleX(double scalarX) { this.x *= scalarX; return this; }
    public TVec3d scaleY(double scalarY) { this.y *= scalarY; return this; }
    public TVec3d scaleZ(double scalarZ) { this.z *= scalarZ; return this; }

    // Division

    public TVec3d div(double divisorX, double divisorY, double divisorZ) {
        this.x /= divisorX;
        this.y /= divisorY;
        this.z /= divisorZ;
        return this;
    }

    public TVec3d div(TVec3d other) {
        this.x /= other.x;
        this.y /= other.y;
        this.z /= other.z;
        return this;
    }

    public TVec3d divX(double divisorX) { this.x /= divisorX; return this; }
    public TVec3d divY(double divisorY) { this.y /= divisorY; return this; }
    public TVec3d divZ(double divisorZ) { this.z /= divisorZ; return this; }


    /*/////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  METRICS  ///////////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    // Dot Product

    public static double dot(double x1, double y1, double z1, double x2, double y2, double z2) {
        return x1 * x2 + y1 * y2 + z1 * z2;
    }

    public static double dot(TVec3d v1, TVec3d v2) { return dot(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z); }

    public double dot(double otherX, double otherY, double otherZ) {
        return this.x * otherX + this.y * otherY + this.z * otherZ;
    }

    public double dot(TVec3d other) { return this.dot(other.x, other.y, other.z); }

    // Length Squared

    public static double lengthSquared(double x, double y, double z) {
        return x * x + y * y + z * z;
    }

    public static double lengthSquared(TVec3d vec) { return lengthSquared(vec.x, vec.y, vec.z); }

    public double lengthSquared() {
        return x * x + y * y + z * z;
    }

    // Length

    public static double length(double x, double y, double z) {
        return TMath.sqrt(x * x + y * y + z * z);
    }

    public static double length(TVec3d vec) { return length(vec.x, vec.y, vec.z); }

    public double length() {
        return TMath.sqrt(x * x + y * y + z * z);
    }

    // Distance Squared

    public static double distanceSquared(double x1, double y1, double z1,
                                         double x2, double y2, double z2)
    {
        final double dx = x2 - x1;
        final double dy = y2 - y1;
        final double dz = z2 - z1;
        return dx * dx + dy * dy + dz * dz;
    }

    public static double distanceSquared(TVec3d v1, TVec3d v2) {
        final double dx = v2.x - v1.x;
        final double dy = v2.y - v1.y;
        final double dz = v2.z - v1.z;
        return dx * dx + dy * dy + dz * dz;
    }

    public double distanceSquared(double otherX, double otherY, double otherZ) {
        final double dx = otherX - this.x;
        final double dy = otherY - this.y;
        final double dz = otherZ - this.z;
        return dx * dx + dy * dy + dz * dz;
    }

    public double distanceSquared(TVec3d other) { return this.distanceSquared(other.x, other.y, other.z); }

    // Distance

    public static double distance(double x1, double y1, double z1, double x2, double y2, double z2) {
        return TMath.sqrt(distanceSquared(x1, y1, z1, x2, y2, z2));
    }

    public static double distance(TVec3d v1, TVec3d v2) {
        return TMath.sqrt(distanceSquared(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z));
    }

    public double distance(double otherX, double otherY, double otherZ) {
        return TMath.sqrt(distanceSquared(x, y, z, otherX, otherY, otherZ));
    }

    public double distance(TVec3d other) {
        return TMath.sqrt(distanceSquared(x, y, z, other.x, other.y, other.z));
    }

    // Cross product

    public static TVec3d cross(double x1, double y1, double z1, double x2, double y2, double z2) {
        final double crossX = y1 * z2 - z1 * y2;
        final double crossY = z1 * x2 - x1 * z2;
        final double crossZ = x1 * y2 - y1 * x2;
        return new TVec3d(crossX, crossY, crossZ);
    }

    public static TVec3d cross(TVec3d v1, TVec3d v2) { return cross(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z); }

    public TVec3d cross(double otherX, double otherY, double otherZ) {
        final double crossX = this.y * otherZ - this.z * otherY;
        final double crossY = this.z * otherX - this.x * otherZ;
        final double crossZ = this.x * otherY - this.y * otherX;
        this.x = crossX;
        this.y = crossY;
        this.z = crossZ;
        return this;
    }

    public TVec3d cross(TVec3d other) { return this.cross(other.x, other.y, other.z); }


    /*///////////////////////////////////////////////////////////////*/
    /*/////////////////////  GETTERS & SETTERS  /////////////////////*/
    /*///////////////////////////////////////////////////////////////*/


    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }

    public TVec3d setX(double x) { this.x = x; return this; }
    public TVec3d setY(double y) { this.y = y; return this; }
    public TVec3d setZ(double z) { this.z = z; return this; }

    public TVec3d set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
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
    public TVec3d unit() {
        final double lenSq = this.lengthSquared();
        // Ignore zero and already normalized vectors
        if (!TMath.equals(lenSq, 0d) && !TMath.equals(lenSq, 1d)) {
            final double len = TMath.sqrt(lenSq);
            this.x /= len;
            this.y /= len;
            this.z /= len;
        }
        return this;
    }

    // Rotation on one axis: X, Y, Z

    public TVec3d rotateX(double cos, double sin) {
        final double ny = y * cos - z * sin;
        final double nz = y * sin + z * cos;
        this.y = ny;
        this.z = nz;
        return this;
    }

    public TVec3d rotateXRad(double radians) {
        return this.rotateX(TMath.cos(radians), TMath.sin(radians));
    }

    public TVec3d rotateXDeg(double degrees) {
        final double radians = TMath.toRadians(degrees);
        return this.rotateX(TMath.cos(radians), TMath.sin(radians));
    }

    public TVec3d rotateY(double cos, double sin) {
        final double nx = x * cos + z * sin;
        final double nz = -x * sin + z * cos;
        this.x = nx;
        this.z = nz;
        return this;
    }

    public TVec3d rotateYRad(double radians) {
        return this.rotateY(TMath.cos(radians), TMath.sin(radians));
    }

    public TVec3d rotateYDeg(double degrees) {
        final double radians = TMath.toRadians(degrees);
        return this.rotateY(TMath.cos(radians), TMath.sin(radians));
    }

    public TVec3d rotateZ(double cos, double sin) {
        final double nx = x * cos - y * sin;
        final double ny = x * sin + y * cos;
        this.x = nx;
        this.y = ny;
        return this;
    }

    public TVec3d rotateZRad(double radians) {
        return this.rotateZ(TMath.cos(radians), TMath.sin(radians));
    }

    public TVec3d rotateZDeg(double degrees) {
        final double radians = TMath.toRadians(degrees);
        return this.rotateZ(TMath.cos(radians), TMath.sin(radians));
    }

    // Rotation with a custom axis

    public TVec3d rotate(double axisX, double axisY, double axisZ, double cos, double sin) {
        final double dotValue = x * axisX + y * axisY + z * axisZ;

        final double cx = axisY * z - axisZ * y;
        final double cy = axisZ * x - axisX * z;
        final double cz = axisX * y - axisY * x;

        this.x = x * cos + cx * sin + axisX * dotValue * (1d - cos);
        this.y = y * cos + cy * sin + axisY * dotValue * (1d - cos);
        this.z = z * cos + cz * sin + axisZ * dotValue * (1d - cos);
        return this;
    }

    public TVec3d rotateRad(double axisX, double axisY, double axisZ, double radians) {
        return this.rotate(axisX, axisY, axisZ, TMath.cos(radians), TMath.sin(radians));
    }

    public TVec3d rotateDeg(double axisX, double axisY, double axisZ, double degrees) {
        final double radians = TMath.toRadians(degrees);
        return this.rotate(axisX, axisY, axisZ, TMath.cos(radians), TMath.sin(radians));
    }

    public TVec3d rotate(TVec3d axis, double cos, double sin) {
        return this.rotate(axis.x, axis.y, axis.z, cos, sin);
    }

    public TVec3d rotateRad(TVec3d axis, double radians) {
        return this.rotate(axis.x, axis.y, axis.z, TMath.cos(radians), TMath.sin(radians));
    }

    public TVec3d rotateDeg(TVec3d axis, double degrees) {
        final double radians = TMath.toRadians(degrees);
        return this.rotate(axis.x, axis.y, axis.z, TMath.cos(radians), TMath.sin(radians));
    }

    // Rotation with a custom axis AND around a reference point

    public TVec3d rotateAround(double refX, double refY, double refZ,
                               double axisX, double axisY, double axisZ,
                               double cos, double sin)
    {
        this.x -= refX;
        this.y -= refY;
        this.z -= refZ;

        final double dotValue = x * axisX + y * axisY + z * axisZ;

        final double cx = axisY * z - axisZ * y;
        final double cy = axisZ * x - axisX * z;
        final double cz = axisX * y - axisY * x;

        this.x = x * cos + cx * sin + axisX * dotValue * (1d - cos);
        this.y = y * cos + cy * sin + axisY * dotValue * (1d - cos);
        this.z = z * cos + cz * sin + axisZ * dotValue * (1d - cos);

        this.x += refX;
        this.y += refY;
        this.z += refZ;
        return this;
    }

    public TVec3d rotateAroundRad(double refX, double refY, double refZ,
                                  double axisX, double axisY, double axisZ, double radians)
    {
        return this.rotateAround(
            refX, refY, refZ, axisX, axisY, axisZ,
            TMath.cos(radians), TMath.sin(radians)
        );
    }

    public TVec3d rotateAroundDeg(double refX, double refY, double refZ,
                                  double axisX, double axisY, double axisZ, double degrees)
    {
        final double radians = TMath.toRadians(degrees);
        return this.rotateAround(
            refX, refY, refZ, axisX, axisY, axisZ,
            TMath.cos(radians), TMath.sin(radians)
        );
    }


    public TVec3d rotateAround(double refX, double refY, double refZ,
                               TVec3d axis, double cos, double sin)
    {
        return this.rotateAround(refX, refY, refZ, axis.x, axis.y, axis.z, cos, sin);
    }

    public TVec3d rotateAroundRad(double refX, double refY, double refZ,
                                  TVec3d axis, double radians)
    {
        return this.rotateAround(
            refX, refY, refZ, axis.x, axis.y, axis.z,
            TMath.cos(radians), TMath.sin(radians)
        );
    }

    public TVec3d rotateAroundDeg(double refX, double refY, double refZ,
                                  TVec3d axis, double degrees) {
        final double radians = TMath.toRadians(degrees);
        return this.rotateAround(
            refX, refY, refZ, axis.x, axis.y, axis.z,
            TMath.cos(radians), TMath.sin(radians)
        );
    }

    public TVec3d rotateAround(TVec3d reference, double axisX, double axisY,
                               double axisZ, double cos, double sin)
    {
        return this.rotateAround(reference.x, reference.y, reference.z, axisX, axisY, axisZ, cos, sin);
    }

    public TVec3d rotateAroundRad(TVec3d reference, double axisX, double axisY,
                                  double axisZ, double radians) {
        return this.rotateAround(
            reference.x, reference.y, reference.z, axisX, axisY, axisZ,
            TMath.cos(radians), TMath.sin(radians)
        );
    }

    public TVec3d rotateAroundDeg(TVec3d reference, double axisX, double axisY,
                                  double axisZ, double degrees) {
        final double radians = TMath.toRadians(degrees);
        return this.rotateAround(
            reference.x, reference.y, reference.z, axisX, axisY, axisZ,
            TMath.cos(radians), TMath.sin(radians)
        );
    }

    public TVec3d rotateAround(TVec3d reference, TVec3d axis, double cos, double sin) {
        return this.rotateAround(reference.x, reference.y, reference.z, axis.x, axis.y, axis.z, cos, sin);
    }

    public TVec3d rotateAroundRad(TVec3d reference, TVec3d axis, double radians) {
        return this.rotateAround(
            reference.x, reference.y, reference.z, axis.x, axis.y, axis.z,
            TMath.cos(radians), TMath.sin(radians)
        );
    }

    public TVec3d rotateAroundDeg(TVec3d reference, TVec3d axis, double degrees) {
        final double radians = TMath.toRadians(degrees);
        return this.rotateAround(
            reference.x, reference.y, reference.z, axis.x, axis.y, axis.z,
            TMath.cos(radians), TMath.sin(radians)
        );
    }

    // Fast 180 Degree Rotations

    public TVec3d rotate180Deg() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        return this;
    }

    public TVec3d rotateX180Deg() {
        this.y = -this.y;
        this.z = -this.z;
        return this;
    }

    public TVec3d rotateY180Deg() {
        this.x = -this.x;
        this.z = -this.z;
        return this;
    }

    public TVec3d rotateZ180Deg() {
        this.x = -this.x;
        this.y = -this.y;
        return this;
    }

    // Symmetry

    public TVec3d symmetricalVectorTo(TVec3d other) {
        this.x = 2d * other.x - this.x;
        this.y = 2d * other.y - this.y;
        this.z = 2d * other.z - this.z;
        return this;
    }

    public TVec3d symmetricalVectorTo(double otherX, double otherY, double otherZ) {
        this.x = 2d * otherX - this.x;
        this.y = 2d * otherY - this.y;
        this.z = 2d * otherZ - this.z;
        return this;
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*//////////////////////  BOOLEAN FUNCTIONS  //////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    // Zero vectors

    public static boolean isZeroVector(double x, double y, double z) {
        return TMath.equals(x, 0d) && TMath.equals(y, 0d) && TMath.equals(z, 0d);
    }

    public static boolean isZeroVector(TVec3d vector) { return isZeroVector(vector.x, vector.y, vector.z); }
    public boolean isZeroVector() { return isZeroVector(this.x, this.y, this.z); }

    // Unit vectors

    public static boolean isUnitVector(double x, double y, double z) {
        return TMath.equals(lengthSquared(x, y, z), 1d);
    }

    public static boolean isUnitVector(TVec3d vector) { return isUnitVector(vector.x, vector.y, vector.z); }
    public boolean isUnitVector() { return isUnitVector(this.x, this.y, this.z); }

    // Orthogonal vectors

    public static boolean areOrthogonal(double x1, double y1, double z1,
                                        double x2, double y2, double z2)
    {
        return TMath.equals(dot(x1, y1, z1, x2, y2, z2), 0d);
    }

    public static boolean areOrthogonal(TVec3d v1, TVec3d v2) {
        return areOrthogonal(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z);
    }

    public boolean isOrthogonalTo(TVec3d vector) {
        return areOrthogonal(this.x, this.y, this.z, vector.x, vector.y, vector.z);
    }

    // Parallel vectors

    public static boolean areParallel(double x1, double y1, double z1,
                                      double x2, double y2, double z2)
    {
        final double crossX = y1 * z2 - z1 * y2;
        final double crossY = z1 * x2 - x1 * z2;
        final double crossZ = x1 * y2 - y1 * x2;
        return isZeroVector(crossX, crossY, crossZ);
    }

    public static boolean areParallel(TVec3d v1, TVec3d v2) {
        return areParallel(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z);
    }

    public boolean isParallelTo(TVec3d vector) {
        return areParallel(this.x, this.y, this.z, vector.x, vector.y, vector.z);
    }


    /*//////////////////////////////////////////////////////////////////*/
    /*////////////////////////  OBJECT METHODS  ////////////////////////*/
    /*//////////////////////////////////////////////////////////////////*/


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TVec3d v = (TVec3d) o;
        return TMath.equals(this.x, v.x) && TMath.equals(this.y, v.y) && TMath.equals(this.z, v.z);
    }

    public boolean equals(TVec3d v) {
        if (v == null) return false;
        return TMath.equals(this.x, v.x) && TMath.equals(this.y, v.y) && TMath.equals(this.z, v.z);
    }

    public boolean equals(double x, double y, double z) {
        return TMath.equals(this.x, x) && TMath.equals(this.y, y) && TMath.equals(this.z, z);
    }

    @Override
    public int hashCode() {
        int result = Double.hashCode(this.x);
        result = 31 * result + Double.hashCode(this.y);
        result = 31 * result + Double.hashCode(this.z);
        return result;
    }

    @Override
    public String toString() {
        return "TVec3d{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
    }


}

