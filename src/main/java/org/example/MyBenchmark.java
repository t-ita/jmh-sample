/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.example;

import org.openjdk.jmh.annotations.*;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(time = 1)
@Measurement(time = 1)
@Fork(1)
public class MyBenchmark {

    @Benchmark
    public List<Integer> sortByListSortWithComparingInt() {
        List<Integer> intList = new Random().ints(100000L).boxed().collect(Collectors.toList());

        // コンパレータを使って、プリミティブな int 値を比較する方法
        intList.sort(Comparator.comparingInt(Integer::intValue));

        return intList;
    }

    @Benchmark
    public List<Integer> sortByListSortWithNaturalOrder() {
        List<Integer> intList = new Random().ints(100000L).boxed().collect(Collectors.toList());

        // コンパレータで、自然順序でラブ用に指定したソート方法
        intList.sort(Comparator.naturalOrder());

        return intList;
    }

    @Benchmark
    public List<Integer> sortByCollectionsSort() {
        List<Integer> intList = new Random().ints(100000L).boxed().collect(Collectors.toList());

        // Collections のスタティッククラスを使う方法
        Collections.sort(intList);

        return intList;
    }

    @Benchmark
    public List<Integer> sortByArrayParallelSort() {
        List<Integer> intList = new Random().ints(100000L).boxed().collect(Collectors.toList());

        // 配列のパラレルソートを使う方法
        Integer[] intArray = intList.toArray(new Integer[0]);
        Arrays.parallelSort(intArray);

        return Arrays.asList(intArray);
    }

    @Benchmark
    public List<Integer> sortBySortedSet() {
        List<Integer> intList = new Random().ints(100000L).boxed().collect(Collectors.toList());

        // SortedSet を利用して、赤黒木のアルゴリズムを使うソート方法（※重複がないこと前提）
        SortedSet<Integer> intSet = new TreeSet<>(intList);

        return new ArrayList<>(intSet);
    }

    @Benchmark
    public List<Integer> sortByStreamSort() {
        List<Integer> intList = new Random().ints(100000L).boxed().collect(Collectors.toList());

        // ストリームのソート機能を使う方法
        return intList.stream().sorted().collect(Collectors.toList());
    }

    @Benchmark
    public List<Integer> sortByParallelStreamSort() {
        List<Integer> intList = new Random().ints(100000L).boxed().collect(Collectors.toList());

        // パラレルストリームのソート機能を使う方法
        return intList.stream().parallel().sorted().collect(Collectors.toList());
    }

}
