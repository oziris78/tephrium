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
 * A fast and mutable 2D double vector class. Most methods return the vector instance for method chaining purposes.
 */
public class TVec2d {

    private double x, y;


    /*//////////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  CONSTRUCTORS  ///////////////////////////*/
    /*//////////////////////////////////////////////////////////////////////*/


    public TVec2d(double x, double y) {
        this.x = x;
        this.y = y;
    }


    /** Creates an origin vector. */
    public TVec2d(){
        this(0d, 0d);
    }
    

    public TVec2d copy(){
        return new TVec2d(this.x, this.y);
    }


    /*///////////////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  GETTERS & SETTERS  ///////////////////////////*/
    /*///////////////////////////////////////////////////////////////////////////*/


    public TVec2d set(double x, double y){
        this.x = x;
        this.y = y;
        return this;
    }


    public TVec2d setX(double x){
        this.x = x;
        return this;
    }


    public TVec2d setY(double y){
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


    public TVec2d add(TVec2d other){
        return add(other.x, other.y);
    }


    public TVec2d add(double x, double y){
        this.x += x;
        this.y += y;
        return this;
    }

    // ---------------- //

    public TVec2d sub(TVec2d other){
        return sub(other.x, other.y);
    }


    public TVec2d sub(double x, double y){
        this.x -= x;
        this.y -= y;
        return this;
    }

    // ---------------- //

    public TVec2d scale(double scaleXY){
        this.x *= scaleXY;
        this.y *= scaleXY;
        return this;
    }

    public TVec2d scale(double scaleX, double scaleY){
        this.x *= scaleX;
        this.y *= scaleY;
        return this;
    }

    public TVec2d scaleX(double scale){
        this.x *= scale;
        return this;
    }

    public TVec2d scaleY(double scale){
        this.y *= scale;
        return this;
    }

    // ---------------- //


    /**
     * Unitizes this vector by scaling it by 1 / len(vec)
     * @return this vector for method chaining purposes
     */
    public TVec2d unit(){
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


    public TVec2d rotate90DegClockwise(){
        double temp = -this.x;
        this.x = this.y;
        this.y = temp;
        return this;
    }


    public TVec2d rotate180DegClockwise(){
        this.x = -this.x;
        this.y = -this.y;
        return this;
    }


    public TVec2d rotate90DegAntiClockwise(){
        double temp = -this.y;
        this.y = this.x;
        this.x = temp;
        return this;
    }


    public TVec2d symmetricalVectorTo(TVec2d other){
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

    public static double dot(TVec2d v1, TVec2d v2) {
        return dot(v1.x, v1.y, v2.x, v2.y);
    }

    public double dot(TVec2d other) {
        return dot(this.x, this.y, other.x, other.y);
    }

    public double dot(double otherX, double otherY) {
        return dot(this.x, this.y, otherX, otherY);
    }


    // ---------------- //

    public static double lengthSquared(double x, double y) {
        return dot(x, y, x, y);
    }

    public static double lengthSquared(TVec2d vec) {
        return lengthSquared(vec.x, vec.y);
    }

    public double lengthSquared() {
        return lengthSquared(x, y);
    }

    // ---------------- //

    public static double length(double x, double y) {
        return TMath.sqrt(lengthSquared(x, y));
    }

    public static double length(TVec2d vec) {
        return length(vec.x, vec.y);
    }

    public double length() {
        return length(x, y);
    }

    // ---------------- //

    public static double angleBetween(TVec2d vec1, TVec2d vec2) {
        return TMath.arccos(dot(vec1, vec2) / (vec1.length() * vec2.length()));
    }


    /*///////////////////////////////////////////////////////////////////////////*/
    /*///////////////////////////  BOOLEAN FUNCTIONS  ///////////////////////////*/
    /*///////////////////////////////////////////////////////////////////////////*/


    public boolean isZeroVector() {
        return isZeroVector(this);
    }

    public static boolean isZeroVector(TVec2d vector) {
        return TMath.equals(vector.x, 0d) && TMath.equals(vector.y, 0d);
    }


    public boolean isUnitVector() {
        return isUnitVector(this);
    }

    public static boolean isUnitVector(TVec2d vector) {
        return TMath.equals(length(vector), 1d);
    }


    public static boolean areOrthogonal(TVec2d vec1, TVec2d vec2, TVec2d vec3){
        return TMath.equals(vec1.dot(vec2), 0d) && TMath.equals(vec1.dot(vec3), 0d)
                && TMath.equals(vec2.dot(vec3), 0d);
    }


    public static boolean areOrthonormal(TVec2d vec1, TVec2d vec2, TVec2d vec3) {
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
        TVec2d other = (TVec2d) o;
        return TMath.equals(other.x, x) && TMath.equals(other.y, y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
