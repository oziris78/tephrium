

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
 * A fast and mutable 3D float vector class with method chaining support.
 */
public class TVec3f {

    private float x, y, z;

    // Actual constructors

    public TVec3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public TVec3f() {
        this(0f, 0f, 0f);
    }

    // Copy constructors

    public TVec3f(TVec3f other) {
        this(other.x, other.y, other.z);
    }

    public TVec3f copy(){
        return new TVec3f(this.x, this.y, this.z);
    }

    // Special static factory methods

    public static TVec3f zero()  { return new TVec3f(0f, 0f, 0f); }
    public static TVec3f one()   { return new TVec3f(1f, 1f, 1f); }
    public static TVec3f unitX() { return new TVec3f(1f, 0f, 0f); }
    public static TVec3f unitY() { return new TVec3f(0f, 1f, 0f); }
    public static TVec3f unitZ() { return new TVec3f(0f, 0f, 1f); }


    /*/////////////////////////////////////////////////////////////////*/
    /*////////////////////  ARITHMETIC OPERATIONS  ////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    // Addition

    public TVec3f add(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public TVec3f add(TVec3f other) {
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
        return this;
    }

    public TVec3f addX(float x) { this.x += x; return this; }
    public TVec3f addY(float y) { this.y += y; return this; }
    public TVec3f addZ(float z) { this.z += z; return this; }

    // Subtraction

    public TVec3f sub(float x, float y, float z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public TVec3f sub(TVec3f other) {
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
        return this;
    }

    public TVec3f subX(float x) { this.x -= x; return this; }
    public TVec3f subY(float y) { this.y -= y; return this; }
    public TVec3f subZ(float z) { this.z -= z; return this; }

    // Scaling / Multiplication

    public TVec3f scale(float scalarX, float scalarY, float scalarZ) {
        this.x *= scalarX;
        this.y *= scalarY;
        this.z *= scalarZ;
        return this;
    }

    public TVec3f scale(TVec3f other) {
        this.x *= other.x;
        this.y *= other.y;
        this.z *= other.z;
        return this;
    }

    public TVec3f scaleX(float scalarX) { this.x *= scalarX; return this; }
    public TVec3f scaleY(float scalarY) { this.y *= scalarY; return this; }
    public TVec3f scaleZ(float scalarZ) { this.z *= scalarZ; return this; }

    // Division

    public TVec3f div(float divisorX, float divisorY, float divisorZ) {
        this.x /= divisorX;
        this.y /= divisorY;
        this.z /= divisorZ;
        return this;
    }

    public TVec3f div(TVec3f other) {
        this.x /= other.x;
        this.y /= other.y;
        this.z /= other.z;
        return this;
    }

    public TVec3f divX(float divisorX) { this.x /= divisorX; return this; }
    public TVec3f divY(float divisorY) { this.y /= divisorY; return this; }
    public TVec3f divZ(float divisorZ) { this.z /= divisorZ; return this; }


    /*/////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  METRICS  ///////////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    // Dot Product

    public static float dot(float x1, float y1, float z1, float x2, float y2, float z2) {
        return x1 * x2 + y1 * y2 + z1 * z2;
    }

    public static float dot(TVec3f v1, TVec3f v2) { return dot(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z); }

    public float dot(float otherX, float otherY, float otherZ) {
        return this.x * otherX + this.y * otherY + this.z * otherZ;
    }

    public float dot(TVec3f other) { return this.dot(other.x, other.y, other.z); }

    // Length Squared

    public static float lengthSquared(float x, float y, float z) {
        return x * x + y * y + z * z;
    }

    public static float lengthSquared(TVec3f vec) { return lengthSquared(vec.x, vec.y, vec.z); }

    public float lengthSquared() {
        return x * x + y * y + z * z;
    }

    // Length

    public static float length(float x, float y, float z) {
        return TMath.sqrt(x * x + y * y + z * z);
    }

    public static float length(TVec3f vec) { return length(vec.x, vec.y, vec.z); }

    public float length() {
        return TMath.sqrt(x * x + y * y + z * z);
    }

    // Distance Squared

    public static float distanceSquared(float x1, float y1, float z1,
                                        float x2, float y2, float z2)
    {
        final float dx = x2 - x1;
        final float dy = y2 - y1;
        final float dz = z2 - z1;
        return dx * dx + dy * dy + dz * dz;
    }

    public static float distanceSquared(TVec3f v1, TVec3f v2) {
        final float dx = v2.x - v1.x;
        final float dy = v2.y - v1.y;
        final float dz = v2.z - v1.z;
        return dx * dx + dy * dy + dz * dz;
    }

    public float distanceSquared(float otherX, float otherY, float otherZ) {
        final float dx = otherX - this.x;
        final float dy = otherY - this.y;
        final float dz = otherZ - this.z;
        return dx * dx + dy * dy + dz * dz;
    }

    public float distanceSquared(TVec3f other) { return this.distanceSquared(other.x, other.y, other.z); }

    // Distance

    public static float distance(float x1, float y1, float z1, float x2, float y2, float z2) {
        return TMath.sqrt(distanceSquared(x1, y1, z1, x2, y2, z2));
    }

    public static float distance(TVec3f v1, TVec3f v2) {
        return TMath.sqrt(distanceSquared(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z));
    }

    public float distance(float otherX, float otherY, float otherZ) {
        return TMath.sqrt(distanceSquared(x, y, z, otherX, otherY, otherZ));
    }

    public float distance(TVec3f other) {
        return TMath.sqrt(distanceSquared(x, y, z, other.x, other.y, other.z));
    }

    // Cross product

    public static TVec3f cross(float x1, float y1, float z1, float x2, float y2, float z2) {
        final float crossX = y1 * z2 - z1 * y2;
        final float crossY = z1 * x2 - x1 * z2;
        final float crossZ = x1 * y2 - y1 * x2;
        return new TVec3f(crossX, crossY, crossZ);
    }

    public static TVec3f cross(TVec3f v1, TVec3f v2) { return cross(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z); }

    public TVec3f cross(float otherX, float otherY, float otherZ) {
        final float crossX = this.y * otherZ - this.z * otherY;
        final float crossY = this.z * otherX - this.x * otherZ;
        final float crossZ = this.x * otherY - this.y * otherX;
        this.x = crossX;
        this.y = crossY;
        this.z = crossZ;
        return this;
    }

    public TVec3f cross(TVec3f other) { return this.cross(other.x, other.y, other.z); }


    /*///////////////////////////////////////////////////////////////*/
    /*/////////////////////  GETTERS & SETTERS  /////////////////////*/
    /*///////////////////////////////////////////////////////////////*/


    public float getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }

    public TVec3f setX(float x) { this.x = x; return this; }
    public TVec3f setY(float y) { this.y = y; return this; }
    public TVec3f setZ(float z) { this.z = z; return this; }

    public TVec3f set(float x, float y, float z) {
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
    public TVec3f unit() {
        final float lenSq = this.lengthSquared();
        // Ignore zero and already normalized vectors
        if (!TMath.equals(lenSq, 0f) && !TMath.equals(lenSq, 1f)) {
            final float len = TMath.sqrt(lenSq);
            this.x /= len;
            this.y /= len;
            this.z /= len;
        }
        return this;
    }

    // Rotation on one axis: X, Y, Z

    public TVec3f rotateX(float cos, float sin) {
        final float ny = y * cos - z * sin;
        final float nz = y * sin + z * cos;
        this.y = ny;
        this.z = nz;
        return this;
    }

    public TVec3f rotateXRad(float radians) {
        return this.rotateX(TMath.cos(radians), TMath.sin(radians));
    }

    public TVec3f rotateXDeg(float degrees) {
        final float radians = TMath.toRadians(degrees);
        return this.rotateX(TMath.cos(radians), TMath.sin(radians));
    }

    public TVec3f rotateY(float cos, float sin) {
        final float nx = x * cos + z * sin;
        final float nz = -x * sin + z * cos;
        this.x = nx;
        this.z = nz;
        return this;
    }

    public TVec3f rotateYRad(float radians) {
        return this.rotateY(TMath.cos(radians), TMath.sin(radians));
    }

    public TVec3f rotateYDeg(float degrees) {
        final float radians = TMath.toRadians(degrees);
        return this.rotateY(TMath.cos(radians), TMath.sin(radians));
    }

    public TVec3f rotateZ(float cos, float sin) {
        final float nx = x * cos - y * sin;
        final float ny = x * sin + y * cos;
        this.x = nx;
        this.y = ny;
        return this;
    }

    public TVec3f rotateZRad(float radians) {
        return this.rotateZ(TMath.cos(radians), TMath.sin(radians));
    }

    public TVec3f rotateZDeg(float degrees) {
        final float radians = TMath.toRadians(degrees);
        return this.rotateZ(TMath.cos(radians), TMath.sin(radians));
    }

    // Rotation with a custom axis

    public TVec3f rotate(float axisX, float axisY, float axisZ, float cos, float sin) {
        final float dotValue = x * axisX + y * axisY + z * axisZ;

        final float cx = axisY * z - axisZ * y;
        final float cy = axisZ * x - axisX * z;
        final float cz = axisX * y - axisY * x;

        this.x = x * cos + cx * sin + axisX * dotValue * (1f - cos);
        this.y = y * cos + cy * sin + axisY * dotValue * (1f - cos);
        this.z = z * cos + cz * sin + axisZ * dotValue * (1f - cos);
        return this;
    }

    public TVec3f rotateRad(float axisX, float axisY, float axisZ, float radians) {
        return this.rotate(axisX, axisY, axisZ, TMath.cos(radians), TMath.sin(radians));
    }

    public TVec3f rotateDeg(float axisX, float axisY, float axisZ, float degrees) {
        final float radians = TMath.toRadians(degrees);
        return this.rotate(axisX, axisY, axisZ, TMath.cos(radians), TMath.sin(radians));
    }

    public TVec3f rotate(TVec3f axis, float cos, float sin) {
        return this.rotate(axis.x, axis.y, axis.z, cos, sin);
    }

    public TVec3f rotateRad(TVec3f axis, float radians) {
        return this.rotate(axis.x, axis.y, axis.z, TMath.cos(radians), TMath.sin(radians));
    }

    public TVec3f rotateDeg(TVec3f axis, float degrees) {
        final float radians = TMath.toRadians(degrees);
        return this.rotate(axis.x, axis.y, axis.z, TMath.cos(radians), TMath.sin(radians));
    }

    // Rotation with a custom axis AND around a reference point

    public TVec3f rotateAround(float refX, float refY, float refZ,
                               float axisX, float axisY, float axisZ,
                               float cos, float sin)
    {
        this.x -= refX;
        this.y -= refY;
        this.z -= refZ;

        final float dotValue = x * axisX + y * axisY + z * axisZ;

        final float cx = axisY * z - axisZ * y;
        final float cy = axisZ * x - axisX * z;
        final float cz = axisX * y - axisY * x;

        this.x = x * cos + cx * sin + axisX * dotValue * (1f - cos);
        this.y = y * cos + cy * sin + axisY * dotValue * (1f - cos);
        this.z = z * cos + cz * sin + axisZ * dotValue * (1f - cos);

        this.x += refX;
        this.y += refY;
        this.z += refZ;
        return this;
    }

    public TVec3f rotateAroundRad(float refX, float refY, float refZ,
                                  float axisX, float axisY, float axisZ, float radians)
    {
        return this.rotateAround(
                refX, refY, refZ, axisX, axisY, axisZ,
                TMath.cos(radians), TMath.sin(radians)
        );
    }

    public TVec3f rotateAroundDeg(float refX, float refY, float refZ,
                                  float axisX, float axisY, float axisZ, float degrees)
    {
        final float radians = TMath.toRadians(degrees);
        return this.rotateAround(
                refX, refY, refZ, axisX, axisY, axisZ,
                TMath.cos(radians), TMath.sin(radians)
        );
    }


    public TVec3f rotateAround(float refX, float refY, float refZ,
                               TVec3f axis, float cos, float sin)
    {
        return this.rotateAround(refX, refY, refZ, axis.x, axis.y, axis.z, cos, sin);
    }

    public TVec3f rotateAroundRad(float refX, float refY, float refZ,
                                  TVec3f axis, float radians)
    {
        return this.rotateAround(
                refX, refY, refZ, axis.x, axis.y, axis.z,
                TMath.cos(radians), TMath.sin(radians)
        );
    }

    public TVec3f rotateAroundDeg(float refX, float refY, float refZ,
                                  TVec3f axis, float degrees) {
        final float radians = TMath.toRadians(degrees);
        return this.rotateAround(
                refX, refY, refZ, axis.x, axis.y, axis.z,
                TMath.cos(radians), TMath.sin(radians)
        );
    }

    public TVec3f rotateAround(TVec3f reference, float axisX, float axisY,
                               float axisZ, float cos, float sin)
    {
        return this.rotateAround(reference.x, reference.y, reference.z, axisX, axisY, axisZ, cos, sin);
    }

    public TVec3f rotateAroundRad(TVec3f reference, float axisX, float axisY,
                                  float axisZ, float radians) {
        return this.rotateAround(
                reference.x, reference.y, reference.z, axisX, axisY, axisZ,
                TMath.cos(radians), TMath.sin(radians)
        );
    }

    public TVec3f rotateAroundDeg(TVec3f reference, float axisX, float axisY,
                                  float axisZ, float degrees) {
        final float radians = TMath.toRadians(degrees);
        return this.rotateAround(
                reference.x, reference.y, reference.z, axisX, axisY, axisZ,
                TMath.cos(radians), TMath.sin(radians)
        );
    }

    public TVec3f rotateAround(TVec3f reference, TVec3f axis, float cos, float sin) {
        return this.rotateAround(reference.x, reference.y, reference.z, axis.x, axis.y, axis.z, cos, sin);
    }

    public TVec3f rotateAroundRad(TVec3f reference, TVec3f axis, float radians) {
        return this.rotateAround(
                reference.x, reference.y, reference.z, axis.x, axis.y, axis.z,
                TMath.cos(radians), TMath.sin(radians)
        );
    }

    public TVec3f rotateAroundDeg(TVec3f reference, TVec3f axis, float degrees) {
        final float radians = TMath.toRadians(degrees);
        return this.rotateAround(
                reference.x, reference.y, reference.z, axis.x, axis.y, axis.z,
                TMath.cos(radians), TMath.sin(radians)
        );
    }

    // Fast 180 Degree Rotations

    public TVec3f rotate180Deg() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        return this;
    }

    public TVec3f rotateX180Deg() {
        this.y = -this.y;
        this.z = -this.z;
        return this;
    }

    public TVec3f rotateY180Deg() {
        this.x = -this.x;
        this.z = -this.z;
        return this;
    }

    public TVec3f rotateZ180Deg() {
        this.x = -this.x;
        this.y = -this.y;
        return this;
    }

    // Symmetry

    public TVec3f symmetricalVectorTo(TVec3f other) {
        this.x = 2f * other.x - this.x;
        this.y = 2f * other.y - this.y;
        this.z = 2f * other.z - this.z;
        return this;
    }

    public TVec3f symmetricalVectorTo(float otherX, float otherY, float otherZ) {
        this.x = 2f * otherX - this.x;
        this.y = 2f * otherY - this.y;
        this.z = 2f * otherZ - this.z;
        return this;
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*//////////////////////  BOOLEAN FUNCTIONS  //////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/

    // Zero vectors

    public static boolean isZeroVector(float x, float y, float z) {
        return TMath.equals(x, 0f) && TMath.equals(y, 0f) && TMath.equals(z, 0f);
    }

    public static boolean isZeroVector(TVec3f vector) { return isZeroVector(vector.x, vector.y, vector.z); }
    public boolean isZeroVector() { return isZeroVector(this.x, this.y, this.z); }

    // Unit vectors

    public static boolean isUnitVector(float x, float y, float z) {
        return TMath.equals(lengthSquared(x, y, z), 1f);
    }

    public static boolean isUnitVector(TVec3f vector) { return isUnitVector(vector.x, vector.y, vector.z); }
    public boolean isUnitVector() { return isUnitVector(this.x, this.y, this.z); }

    // Orthogonal vectors

    public static boolean areOrthogonal(float x1, float y1, float z1,
                                        float x2, float y2, float z2)
    {
        return TMath.equals(dot(x1, y1, z1, x2, y2, z2), 0f);
    }

    public static boolean areOrthogonal(TVec3f v1, TVec3f v2) {
        return areOrthogonal(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z);
    }

    public boolean isOrthogonalTo(TVec3f vector) {
        return areOrthogonal(this.x, this.y, this.z, vector.x, vector.y, vector.z);
    }

    // Parallel vectors

    public static boolean areParallel(float x1, float y1, float z1,
                                      float x2, float y2, float z2)
    {
        final float crossX = y1 * z2 - z1 * y2;
        final float crossY = z1 * x2 - x1 * z2;
        final float crossZ = x1 * y2 - y1 * x2;
        return isZeroVector(crossX, crossY, crossZ);
    }

    public static boolean areParallel(TVec3f v1, TVec3f v2) {
        return areParallel(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z);
    }

    public boolean isParallelTo(TVec3f vector) {
        return areParallel(this.x, this.y, this.z, vector.x, vector.y, vector.z);
    }


    /*//////////////////////////////////////////////////////////////////*/
    /*////////////////////////  OBJECT METHODS  ////////////////////////*/
    /*//////////////////////////////////////////////////////////////////*/


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TVec3f v = (TVec3f) o;
        return TMath.equals(this.x, v.x) && TMath.equals(this.y, v.y) && TMath.equals(this.z, v.z);
    }

    public boolean equals(TVec3f v) {
        if (v == null) return false;
        return TMath.equals(this.x, v.x) && TMath.equals(this.y, v.y) && TMath.equals(this.z, v.z);
    }

    public boolean equals(float x, float y, float z) {
        return TMath.equals(this.x, x) && TMath.equals(this.y, y) && TMath.equals(this.z, z);
    }

    @Override
    public int hashCode() {
        int result = Float.hashCode(this.x);
        result = 31 * result + Float.hashCode(this.y);
        result = 31 * result + Float.hashCode(this.z);
        return result;
    }

    @Override
    public String toString() {
        return "TVec3f{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
    }


}

