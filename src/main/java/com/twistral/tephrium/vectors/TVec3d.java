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
 * A fast and mutable 3D double vector class. Most methods return the vector instance for method chaining purposes.
 */
public class TVec3d {

    private double x, y, z;


    /*//////////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  CONSTRUCTORS  ///////////////////////////*/
    /*//////////////////////////////////////////////////////////////////////*/

    public TVec3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    /** Creates an origin vector. */
    public TVec3d(){
        this(0d, 0d, 0d);
    }


    public TVec3d copy(){
        return new TVec3d(this.x, this.y, this.z);
    }


    /*///////////////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  GETTERS & SETTERS  ///////////////////////////*/
    /*///////////////////////////////////////////////////////////////////////////*/


    public TVec3d set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }


    public TVec3d setX(double x) {
        this.x = x;
        return this;
    }


    public TVec3d setY(double y) {
        this.y = y;
        return this;
    }

    public TVec3d setZ(double z) {
        this.z = z;
        return this;
    }

    // ---------------- //

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getZ() {
        return z;
    }

    /*///////////////////////////////////////////////////////////////////////*/
    /*///////////////////////  ARITHMETIC OPERATIONS  ///////////////////////*/
    /*///////////////////////////////////////////////////////////////////////*/


    public TVec3d add(TVec3d other){
        return add(other.x, other.y, other.z);
    }


    public TVec3d add(double x, double y, double z){
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    // ---------------- //

    public TVec3d sub(TVec3d other){
        return sub(other.x, other.y, other.z);
    }


    public TVec3d sub(double x, double y, double z){
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    // ---------------- //

    public TVec3d scale(double scaleX, double scaleY, double scaleZ){
        this.x *= scaleX;
        this.y *= scaleY;
        this.z *= scaleZ;
        return this;
    }

    public TVec3d scale(double scaleXYZ){
        return scale(scaleXYZ, scaleXYZ, scaleXYZ);
    }


    public TVec3d scaleX(double scale){
        this.x *= scale;
        return this;
    }

    public TVec3d scaleY(double scale){
        this.y *= scale;
        return this;
    }

    public TVec3d scaleZ(double scale){
        this.z *= scale;
        return this;
    }



    // ---------------- //


    /**
     * Unitizes this vector by scaling it by 1 / len(vec)
     * @return this vector for method chaining purposes
     */
    public TVec3d unit(){
        if(!this.isZeroVector())
            scale(1d / this.length());

        return this;
    }



    public static void cross(TVec3d output, double x1, double y1, double z1, double x2, double y2, double z2) {
        output.set(
                y1 * z2 - z1 * y2,
                z1 * x2 - x1 * z2,
                x1 * y2 - y1 * x2
        );
    }



    public TVec3d cross(TVec3d other){
        cross(this, this.x, this.y, this.z, other.x, other.y, other.z);
        return this;
    }



    /*//////////////////////////////////////////////////////////////////////*/
    /*//////////////////////  IMPORTANT VALUE FUNCS  ///////////////////////*/
    /*//////////////////////////////////////////////////////////////////////*/


    public static double dot(double x1, double y1, double z1, double x2, double y2, double z2) {
        return x1 * x2 + y1 * y2 + z1 * z2;
    }

    public static double dot(TVec3d v1, TVec3d v2) {
        return dot(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z);
    }

    public double dot(TVec3d other) {
        return dot(this.x, this.y, this.z, other.x, other.y, other.z);
    }

    public double dot(double otherX, double otherY, double otherZ) {
        return dot(this.x, this.y, this.z, otherX, otherY, otherZ);
    }

    // ---------------- //

    public static double lengthSquared(double x, double y, double z) {
        return dot(x, y, z, x, y, z);
    }

    public static double lengthSquared(TVec3d vec) {
        return lengthSquared(vec.x, vec.y, vec.z);
    }

    public double lengthSquared() {
        return lengthSquared(this.x, this.y, this.z);
    }

    // ---------------- //

    public static double length(double x, double y, double z) {
        return TMath.sqrt(lengthSquared(x, y, z));
    }

    public static double length(TVec3d vec) {
        return length(vec.x, vec.y, vec.z);
    }

    public double length() {
        return length(this.x, this.y, this.z);
    }

    // ---------------- //

    public static double angleBetween(TVec3d vec1, TVec3d vec2) {
        return TMath.arccos(dot(vec1, vec2) / (vec1.length() * vec2.length()));
    }



    /*///////////////////////////////////////////////////////////////*/
    /*///////////////////////  BOOLEAN FUNCS  ///////////////////////*/
    /*///////////////////////////////////////////////////////////////*/



    public boolean isZeroVector() {
        return isZeroVector(this);
    }

    public static boolean isZeroVector(TVec3d vector) {
        return TMath.equals(vector.x, 0d) && TMath.equals(vector.y, 0d) && TMath.equals(vector.z, 0d);
    }


    public boolean isUnitVector() {
        return isUnitVector(this);
    }

    public static boolean isUnitVector(TVec3d vector) {
        return TMath.equals(length(vector), 1d);
    }


    public static boolean areOrthogonal(TVec3d vec1, TVec3d vec2, TVec3d vec3){
        return TMath.equals(vec1.dot(vec2), 0d)
            && TMath.equals(vec1.dot(vec3), 0d)
            && TMath.equals(vec2.dot(vec3), 0d);
    }


    public static boolean areOrthonormal(TVec3d vec1, TVec3d vec2, TVec3d vec3) {
        return areOrthogonal(vec1, vec2, vec3) && vec1.isUnitVector() && vec2.isUnitVector() && vec3.isUnitVector();
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*///////////////////////  OBJECT METHODS  ////////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/


    @Override
    public String toString() {
        return String.format("<%f, %f, %f>", this.x, this.y, this.z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        TVec3d other = (TVec3d) o;
        return TMath.equals(x, other.x) && TMath.equals(y, other.y) && TMath.equals(z, other.z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

}
