
// Copyright 2024-2025 Oğuzhan Topaloğlu
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



package com.twistral.benchmarks;

import com.twistral.tephrium.core.functions.TMath;
import com.twistral.tephrium.prng.SplitMix64Random;
import com.twistral.tephrium.core.matrices.*;
import org.joml.*;
import org.junit.jupiter.api.*;


import static com.twistral.TephriumTestFramework.*;



public class MatrixVersus {

    private static double m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33;
    private static double det1, det2;

    private static SplitMix64Random rand = new SplitMix64Random();

    private static void beforeFunction() {
        m00 = rand.nextDouble(); m01 = rand.nextDouble(); m02 = rand.nextDouble(); m03 = rand.nextDouble();
        m10 = rand.nextDouble(); m11 = rand.nextDouble(); m12 = rand.nextDouble(); m13 = rand.nextDouble();
        m20 = rand.nextDouble(); m21 = rand.nextDouble(); m22 = rand.nextDouble(); m23 = rand.nextDouble();
        m30 = rand.nextDouble(); m31 = rand.nextDouble(); m32 = rand.nextDouble(); m33 = rand.nextDouble();
    }

    private static void afterFunction() {
        Assertions.assertTrue(TMath.equalsd(det1, det2));
    }


    @Test
    @DisplayName("mat2Benchmark")
    void mat2Benchmark() {
        setBenchmarkIterationCount(10_000_000);
        setBeforeEachIter(MatrixVersus::beforeFunction);
        setAfterEachIter(MatrixVersus::afterFunction);
        benchmark(
                "TMat2",
                () -> {
                    TMat2 tMat = new TMat2(m00, m01, m10, m11);
                    tMat.scale(5d).add(tMat).sub(new TMat2(m11, m01, m10, m00)).multiply(tMat);
                    det1 = tMat.determinant();
                    if(!TMath.equalsd(det1, 0d))
                        tMat.invert();
                },
                "JOML Matrix2d",
                () -> {
                    Matrix2d jMat = new Matrix2d(m00, m10, m01, m11);
                    jMat.scale(5d).add(jMat).sub(new Matrix2d(m11, m10, m01, m00)).mul(jMat);
                    det2 = jMat.determinant();
                    if(!TMath.equalsd(det2, 0d))
                        jMat.invert();
                }
        );
    }


    @Test
    @DisplayName("mat3Benchmark")
    void mat3Benchmark() {
        setBenchmarkIterationCount(10_000_000);
        setBeforeEachIter(MatrixVersus::beforeFunction);
        setAfterEachIter(MatrixVersus::afterFunction);

        benchmark("TMat3",
                () -> {
                    TMat3 tMat = new TMat3(m00, m01, m02, m10, m11, m12, m20, m21, m22);
                    tMat.scale(5d).add(tMat).sub(new TMat3(1, 4, 7, 2, 5, 8, 3, 6, 9)).multiply(tMat);
                    det1 = tMat.determinant();
                    if(!TMath.equalsd(det1, 0d))
                        tMat.invert();
                },
                "JOML Matrix3d",
                () -> {
                    Matrix3d jMat = new Matrix3d(m00, m10, m20, m01, m11, m21, m02, m12, m22);
                    jMat.scale(5d).add(jMat).sub(new Matrix3d(1, 2, 3, 4, 5, 6, 7, 8, 9)).mul(jMat);
                    det2 = jMat.determinant();
                    if(!TMath.equalsd(det2, 0d))
                        jMat.invert();
                }
        );
    }


    @Test
    @DisplayName("mat4Benchmark")
    void mat4Benchmark() {
        setBenchmarkIterationCount(10_000_000);
        setBeforeEachIter(MatrixVersus::beforeFunction);
        setAfterEachIter(MatrixVersus::afterFunction);

        benchmark(
                "TMat4",
                () -> {
                    TMat4 tMat = new TMat4(
                            m00, m01, m02, m03,
                            m10, m11, m12, m13,
                            m20, m21, m22, m23,
                            m30, m31, m32, m33
                    );
                    tMat.scale(5d);
                    tMat.add(tMat);
                    tMat.sub(new TMat4(
                            16, 27, 38, 40,
                            10, 29, 32, 40,
                            10, 26, 37, 48,
                            16, 27, 38, 41
                    ));
                    tMat.multiply(tMat);
                    det1 = tMat.determinant() / 1E10d;
                    if(!TMath.equalsd(det1, 0d))
                        tMat.invert();
                },
                "JOML Matrix4d",
                () -> {
                    Matrix4d jMat = new Matrix4d(
                            m00, m10, m20, m30,
                            m01, m11, m21, m31,
                            m02, m12, m22, m32,
                            m03, m13, m23, m33
                    );

                    // scale(double) only does XYZ so do the last column manually
                    jMat = jMat.scale(5d);
                    jMat.m30(jMat.m30() * 5d);
                    jMat.m31(jMat.m31() * 5d);
                    jMat.m32(jMat.m32() * 5d);
                    jMat.m33(jMat.m33() * 5d);

                    jMat = jMat.add(jMat);
                    jMat = jMat.sub(new Matrix4d(
                            16, 10, 10, 16,
                            27, 29, 26, 27,
                            38, 32, 37, 38,
                            40, 40, 48, 41
                    ));
                    jMat = jMat.mul(jMat);
                    det2 = jMat.determinant() / 1E10d;
                    if(!TMath.equalsd(det2, 0d))
                        jMat.invert();
                }
        );
    }


}
