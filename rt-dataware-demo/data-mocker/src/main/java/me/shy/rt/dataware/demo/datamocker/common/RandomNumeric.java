/**
 * @Date        : 2021-04-10 22:33:37
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : A tool for random generated numerics.
 */

package me.shy.rt.dataware.demo.datamocker.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomNumeric {
    public static int nextInteger(int start, int stop) {
        return nextInteger(start, stop, new Random().nextLong());
    }

    public static int nextInteger(int start, int stop, Long seed) {
        if (stop - start <= 0) {
            throw new IllegalArgumentException(
                    String.format("Invalid range with a start %s and stop %s.", start, stop));
        }
        return start + new Random(seed).nextInt(stop - start + 1);
    }

    public static String nextString(int start, int stop, int length, String delimiter, boolean canRepeat) {
        Collection<Integer> integers = null;
        if (canRepeat) {
            integers = new ArrayList<>();
            while (integers.size() < length) {
                integers.add(nextInteger(start, stop));
            }
        } else {
            integers = new HashSet<>();
            while (integers.size() < length) {
                integers.add(nextInteger(start, stop));
            }
        }

        return String.join(delimiter, integers.stream().map(x -> x.toString()).collect(Collectors.toList()));
    }

    public static String nextString(int start, int stop, int length, String delimiter) {
        return nextString(start, stop, length, delimiter, true);
    }
}
