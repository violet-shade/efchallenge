package com.ef.challenge;

import javafx.util.Pair;

import java.util.*;

public class Main {
    private static int DEFAULT_MAX_VALUE = 1000000;
    private static int MAX_VALUE = 1000000000;

    public static void main(String[] args) {
        System.out.println("Provide an integer number between 0 and 1,000,000,000.");
        Scanner scanner = new Scanner(System.in);
        int noOfIntervals = scanner.nextInt();
        if (noOfIntervals < 0 || noOfIntervals > MAX_VALUE) {
            System.out.println("You entered a value outside of the allowed interval [0; 1,000,000,000]. Will default to 1,000,000.");
            noOfIntervals = DEFAULT_MAX_VALUE;
        }
        System.out.println("You entered integer " + noOfIntervals + ". We are going to generate the intervals.");

        Random randomGenerator = new Random();
        Map<Integer, List<Integer>> groupedRanges = new TreeMap<>();
        for (int i = 0; i < noOfIntervals; i++) {
            Pair<Integer, Integer> range = generateRange(MAX_VALUE);
            addRangeToMap(groupedRanges, range);
        }
        groupedRanges.forEach((k, v) -> System.out.println("Min : " + k + " Max : " + v));
        System.out.println("Starting to generate random numbers. Press q to exit the program.");
        boolean quitKeyPressed = false;
        try {
            while (true) {
                int number = randomGenerator.nextInt(MAX_VALUE);
                System.out.println(number);
                findRanges(number, groupedRanges);
                Thread.sleep(2000);
                // quitKeyPressed = scanner.next().equalsIgnoreCase("q");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void addRangeToMap(Map<Integer, List<Integer>> ranges, final Pair<Integer, Integer> newRange) {
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

    private static void findRanges(final int number, final Map<Integer, List<Integer>> ranges) {
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
        System.out.println(number + " => Enclosed in " + count + " ranges.");
    }

    private static Pair generateRange(final int maxValue) {
        Random randomGenerator = new Random();
        int first = randomGenerator.nextInt(maxValue);
        int second = randomGenerator.nextInt(maxValue - first) + first + 1;
        Pair<Integer, Integer> range = new Pair<>(first, second);
        System.out.println("[ " + first + ", " + second + " ]");
        return range;
    }
}
