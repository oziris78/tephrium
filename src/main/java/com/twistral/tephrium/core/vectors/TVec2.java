// Copyright 2020-2025 Oğuzhan Topaloğlu
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
import com.twistral.tephrium.core.functions.TMath.*;
import java.util.Objects;



/**
 * A fast and mutable 2D double vector class. Most methods return the vector instance for method chaining purposes.
 */
public class TVec2 {

    private double x, y;


    /*//////////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  CONSTRUCTORS  ///////////////////////////*/
    /*//////////////////////////////////////////////////////////////////////*/


    public TVec2(double x, double y) {
        this.x = x;
        this.y = y;
    }


    /** Creates an origin vector. */
    public TVec2(){
        this(0d, 0d);
    }
    

    public TVec2 copy(){
        return new TVec2(this.x, this.y);
    }


    /*///////////////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  GETTERS & SETTERS  ///////////////////////////*/
    /*///////////////////////////////////////////////////////////////////////////*/


    public TVec2 set(double x, double y){
        this.x = x;
        this.y = y;
        return this;
    }


    public TVec2 setX(double x){
        this.x = x;
        return this;
    }


    public TVec2 setY(double y){
        this.y = y;
        return this;
    }

    // ---------------- //


    public double getX() {
        return x;
    }


    public double getY() {
        return y;
    }

    
    
    /*///////////////////////////////////////////////////////////////////////*/
    /*///////////////////////  ARITHMETIC OPERATIONS  ///////////////////////*/
    /*///////////////////////////////////////////////////////////////////////*/


    public TVec2 add(TVec2 other){
        return add(other.x, other.y);
    }


    public TVec2 add(double x, double y){
        this.x += x;
        this.y += y;
        return this;
    }

    // ---------------- //

    public TVec2 sub(TVec2 other){
        return sub(other.x, other.y);
    }


    public TVec2 sub(double x, double y){
        this.x -= x;
        this.y -= y;
        return this;
    }

    // ---------------- //

    public TVec2 scale(double scaleXY){
        this.x *= scaleXY;
        this.y *= scaleXY;
        return this;
    }

    public TVec2 scale(double scaleX, double scaleY){
        this.x *= scaleX;
        this.y *= scaleY;
        return this;
    }

    public TVec2 scaleX(double scale){
        this.x *= scale;
        return this;
    }

    public TVec2 scaleY(double scale){
        this.y *= scale;
        return this;
    }

    // ---------------- //


    /**
     * Unitizes this vector by scaling it by 1 / len(vec)
     * @return this vector for method chaining purposes
     */
    public TVec2 unit(){
        if(!this.isZeroVector()) {
            double len = this.length();
            this.x /= len;
            this.y /= len;
        }
        return this;
    }


    /*//////////////////////////////////////////////////////////////////////*/
    /*///////////////////////  GEOMETRIC OPERATIONS  ///////////////////////*/
    /*//////////////////////////////////////////////////////////////////////*/


    public TVec2 rotate90DegClockwise(){
        double temp = -this.x;
        this.x = this.y;
        this.y = temp;
        return this;
    }


    public TVec2 rotate180DegClockwise(){
        this.x = -this.x;
        this.y = -this.y;
        return this;
    }


    public TVec2 rotate90DegAntiClockwise(){
        double temp = -this.y;
        this.y = this.x;
        this.x = temp;
        return this;
    }


    public TVec2 symmetricalVectorTo(TVec2 other){
        this.x = 2d * other.x - this.x;
        this.y = 2d * other.y - this.y;
        return this;
    }


    /*///////////////////////////////////////////////////////////////////////*/
    /*///////////////////////  IMPORTANT VALUE FUNCS  ///////////////////////*/
    /*///////////////////////////////////////////////////////////////////////*/


    public static double dot(double x1, double y1, double x2, double y2) {
        return x1 * x2 + y1 * y2;
    }

    public static double dot(TVec2 v1, TVec2 v2) {
        return dot(v1.x, v1.y, v2.x, v2.y);
    }

    public double dot(TVec2 other) {
        return dot(this.x, this.y, other.x, other.y);
    }

    public double dot(double otherX, double otherY) {
        return dot(this.x, this.y, otherX, otherY);
    }


    // ---------------- //

    public static double lengthSquared(double x, double y) {
        return dot(x, y, x, y);
    }

    public static double lengthSquared(TVec2 vec) {
        return lengthSquared(vec.x, vec.y);
    }

    public double lengthSquared() {
        return lengthSquared(x, y);
    }

    // ---------------- //

    public static double length(double x, double y) {
        return TMath.sqrt(lengthSquared(x, y));
    }

    public static double length(TVec2 vec) {
        return length(vec.x, vec.y);
    }

    public double length() {
        return length(x, y);
    }

    // ---------------- //

    public static double angleBetween(TVec2 vec1, TVec2 vec2) {
        return TMath.arccos(dot(vec1, vec2) / (vec1.length() * vec2.length()));
    }


    /*///////////////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  BOOLEAN FUNCTIONS  ///////////////////////////*/
    /*///////////////////////////////////////////////////////////////////////////*/


    public boolean isZeroVector() {
        return isZeroVector(this);
    }

    public static boolean isZeroVector(TVec2 vector) {
        return TMath.equalsd(vector.x, 0d) && TMath.equalsd(vector.y, 0d);
    }


    public boolean isUnitVector() {
        return isUnitVector(this);
    }

    public static boolean isUnitVector(TVec2 vector) {
        return TMath.equalsd(length(vector), 1d);
    }


    public static boolean areOrthogonal(TVec2 vec1, TVec2 vec2, TVec2 vec3){
        return TMath.equalsd(vec1.dot(vec2), 0d) && TMath.equalsd(vec1.dot(vec3), 0d)
                && TMath.equalsd(vec2.dot(vec3), 0d);
    }


    public static boolean areOrthonormal(TVec2 vec1, TVec2 vec2, TVec2 vec3) {
        return areOrthogonal(vec1, vec2, vec3) && vec1.isUnitVector() && vec2.isUnitVector() && vec3.isUnitVector();
    }


    /*/////////////////////////////////////////////////////////////////*/
    /*///////////////////////  OBJECT METHODS  ////////////////////////*/
    /*/////////////////////////////////////////////////////////////////*/


    @Override
    public String toString() {
        return String.format("<%f, %f>", this.x, this.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        TVec2 other = (TVec2) o;
        return TMath.equalsd(other.x, x) && TMath.equalsd(other.y, y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
