package com.ef.challenge;

import javafx.util.Pair;

import java.util.*;

import static com.ef.challenge.RangeUtils.generateRange;
import static com.ef.challenge.RangeUtils.addRangeToMap;
import static com.ef.challenge.RangeUtils.findRanges;

public class Main implements Runnable {
    private static int DEFAULT_MAX_VALUE = 1000000;
    private static int MAX_VALUE = 1000000000;
    private static Map<Integer, List<Integer>> groupedRanges = new TreeMap<>();
    volatile boolean keepRunning = true;

    public static void main(String[] args) {
        System.out.println("Provide an integer number between 0 and 1,000,000,000.");
        Scanner scanner = new Scanner(System.in);
        int noOfIntervals = scanner.nextInt();
        if (noOfIntervals < 0 || noOfIntervals > MAX_VALUE) {
            System.out.println("You entered a value outside of the allowed interval [0; 1,000,000,000]. Will default to 1,000,000.");
            noOfIntervals = DEFAULT_MAX_VALUE;
        }
        System.out.println("You entered integer " + noOfIntervals + ". We are going to generate the intervals.");

        for (int i = 0; i < noOfIntervals; i++) {
            Pair<Integer, Integer> range = generateRange(MAX_VALUE);
            addRangeToMap(groupedRanges, range);
        }
        groupedRanges.forEach((k, v) -> System.out.println("Min : " + k + " Max : " + v));

        System.out.println("Starting to generate random numbers. Press q and ENTER to exit the program.");
        Main application = new Main();
        Thread t = new Thread(application);
        t.start();

        Scanner userStop = new Scanner(System.in);
        while (!userStop.nextLine().equalsIgnoreCase("q")) ;
        application.keepRunning = false;
        t.interrupt();
    }

    public void run() {
        Random randomGenerator = new Random();
        while (keepRunning) {
            int number = randomGenerator.nextInt(MAX_VALUE);
            System.out.println(number);
            int count = findRanges(number, groupedRanges);
            System.out.println(number + " => Enclosed in " + count + " ranges.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }
}
