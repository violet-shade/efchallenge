package com.ef.challenge;

import javafx.util.Pair;

import java.util.*;

public class RangeUtils {
    public static Pair generateRange(final int maxValue) {
        Random randomGenerator = new Random();
        int first = randomGenerator.nextInt(maxValue);
        int second = randomGenerator.nextInt(maxValue - first) + first + 1;
        Pair<Integer, Integer> range = new Pair<>(first, second);
        System.out.println("[ " + first + ", " + second + " ]");
        return range;
    }

    public static void addRangeToMap(Map<Integer, List<Integer>> ranges, final Pair<Integer, Integer> newRange) {
        if (!ranges.containsKey(newRange.getKey())) {
            List<Integer> ends = new ArrayList<>();
            ends.add(newRange.getValue());
            ranges.put(newRange.getKey(), ends);
        } else {
            List<Integer> valuesForKey = ranges.get(newRange.getKey());
            if (!valuesForKey.contains(newRange.getValue())) {
                valuesForKey.add(newRange.getValue());
                valuesForKey.sort(Comparator.reverseOrder());
            }
        }
    }

    public static int findRanges(final int number, final Map<Integer, List<Integer>> ranges) {
        int count = 0;
        for (int key : ranges.keySet()) {
            if (number > key) {
                for (int endOfRange : ranges.get(key)) {
                    if (number < endOfRange) {
                        count++;
                    } else {
                        break;
                    }
                }
            } else {
                break;
            }
        }
        return count;
    }
}
