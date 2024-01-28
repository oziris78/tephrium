// Copyright 2020-2024 Oğuzhan Topaloğlu
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


package com.twistral.tephrium.core.vectors;


import com.twistral.tephrium.core.functions.TMath;
import java.util.Objects;



/**
 * A fast and mutable 3D double vector class. Most methods return the vector instance for method chaining purposes.
 */
public class TVec3 {

    private double x, y, z;


    //////////////////////////////////////////////////////////////////////////
    /////////////////////////////  CONSTRUCTORS  /////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    public TVec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    /** Creates an origin vector. */
    public TVec3(){
        this(0d, 0d, 0d);
    }


    public TVec3 copy(){
        return new TVec3(this.x, this.y, this.z);
    }


    ///////////////////////////////////////////////////////////////////////////////
    /////////////////////////////  GETTERS & SETTERS  /////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////


    public TVec3 set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }


    public TVec3 setX(double x) {
        this.x = x;
        return this;
    }


    public TVec3 setY(double y) {
        this.y = y;
        return this;
    }

    public TVec3 setZ(double z) {
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

    ///////////////////////////////////////////////////////////////////////////
    /////////////////////////  ARITHMETIC OPERATIONS  /////////////////////////
    ///////////////////////////////////////////////////////////////////////////


    public TVec3 add(TVec3 other){
        return add(other.x, other.y, other.z);
    }


    public TVec3 add(double x, double y, double z){
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    // ---------------- //

    public TVec3 sub(TVec3 other){
        return sub(other.x, other.y, other.z);
    }


    public TVec3 sub(double x, double y, double z){
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    // ---------------- //

    public TVec3 scale(double scaleX, double scaleY, double scaleZ){
        this.x *= scaleX;
        this.y *= scaleY;
        this.z *= scaleZ;
        return this;
    }

    public TVec3 scale(double scaleXYZ){
        return scale(scaleXYZ, scaleXYZ, scaleXYZ);
    }


    public TVec3 scaleX(double scale){
        this.x *= scale;
        return this;
    }

    public TVec3 scaleY(double scale){
        this.y *= scale;
        return this;
    }

    public TVec3 scaleZ(double scale){
        this.z *= scale;
        return this;
    }



    // ---------------- //


    /**
     * Unitizes this vector by scaling it by 1 / len(vec)
     * @return this vector for method chaining purposes
     */
    public TVec3 unit(){
        if(!this.isZeroVector())
            scale(1d / this.length());

        return this;
    }



    public static void cross(TVec3 output, double x1, double y1, double z1, double x2, double y2, double z2) {
        output.set(
                y1 * z2 - z1 * y2,
                z1 * x2 - x1 * z2,
                x1 * y2 - y1 * x2
        );
    }



    public TVec3 cross(TVec3 other){
        cross(this, this.x, this.y, this.z, other.x, other.y, other.z);
        return this;
    }



    ///////////////////////////////////////////////////////////////////////////
    /////////////////////////  IMPORTANT VALUE FUNCS  /////////////////////////
    ///////////////////////////////////////////////////////////////////////////


    public static double dot(double x1, double y1, double z1, double x2, double y2, double z2) {
        return x1 * x2 + y1 * y2 + z1 * z2;
    }

    public static double dot(TVec3 v1, TVec3 v2) {
        return dot(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z);
    }

    public double dot(TVec3 other) {
        return dot(this.x, this.y, this.z, other.x, other.y, other.z);
    }

    public double dot(double otherX, double otherY, double otherZ) {
        return dot(this.x, this.y, this.z, otherX, otherY, otherZ);
    }

    // ---------------- //

    public static double lengthSquared(double x, double y, double z) {
        return dot(x, y, z, x, y, z);
    }

    public static double lengthSquared(TVec3 vec) {
        return lengthSquared(vec.x, vec.y, vec.z);
    }

    public double lengthSquared() {
        return lengthSquared(this.x, this.y, this.z);
    }

    // ---------------- //

    public static double length(double x, double y, double z) {
        return TMath.sqrt(lengthSquared(x, y, z));
    }

    public static double length(TVec3 vec) {
        return length(vec.x, vec.y, vec.z);
    }

    public double length() {
        return length(this.x, this.y, this.z);
    }

    // ---------------- //

    public static double angleBetween(TVec3 vec1, TVec3 vec2) {
        return TMath.arccos(dot(vec1, vec2) / (vec1.length() * vec2.length()));
    }



    ///////////////////////////////////////////////////////////////////////////////
    /////////////////////////////  BOOLEAN FUNCTIONS  /////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////


    public boolean isZeroVector() {
        return isZeroVector(this);
    }

    public static boolean isZeroVector(TVec3 vector) {
        return TMath.equalsd(vector.x, 0d) && TMath.equalsd(vector.y, 0d) && TMath.equalsd(vector.z, 0d);
    }


    public boolean isUnitVector() {
        return isUnitVector(this);
    }

    public static boolean isUnitVector(TVec3 vector) {
        return TMath.equalsd(length(vector), 1d);
    }


    public static boolean areOrthogonal(TVec3 vec1, TVec3 vec2, TVec3 vec3){
        return TMath.equalsd(vec1.dot(vec2), 0d) && TMath.equalsd(vec1.dot(vec3), 0d)
                && TMath.equalsd(vec2.dot(vec3), 0d);
    }


    public static boolean areOrthonormal(TVec3 vec1, TVec3 vec2, TVec3 vec3) {
        return areOrthogonal(vec1, vec2, vec3) && vec1.isUnitVector() && vec2.isUnitVector() && vec3.isUnitVector();
    }


    /////////////////////////////////////////////////////////////////////
    /////////////////////////  OBJECT METHODS  //////////////////////////
    /////////////////////////////////////////////////////////////////////


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
        TVec3 other = (TVec3) o;
        return TMath.equalsd(x, other.x) && TMath.equalsd(y, other.y) && TMath.equalsd(z, other.z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

}
