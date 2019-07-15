package com.ef.challenge;

import javafx.util.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;

public class RangeUtilsTest {

    @Test
    public void generateRangeWhenMaxValueIsAPositiveIntegerValidRangeIsGenerated() {
        int maxValue = 9;
        Pair<Integer, Integer> result = RangeUtils.generateRange(maxValue);
        assertTrue(result.getKey() < result.getValue());
        assertTrue(result.getKey() < maxValue);
        assertTrue(result.getValue() <= maxValue);
    }

    @Test
    public void generateRangeWhenMaxValueEqualsZeroExceptionIsThrown() {
        int maxValue = 0;
        try {
            Pair<Integer, Integer> result = RangeUtils.generateRange(maxValue);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().equalsIgnoreCase("bound must be positive"));
        }
    }

    @Test
    public void addRangeToMapWhenKeyDoesNotExistInMapWillAddNewEntryToMap() {
        Map<Integer, List<Integer>> groupedRanges = new TreeMap<>();
        Pair<Integer, Integer> testRange = new Pair<>(5,9);

        RangeUtils.addRangeToMap(groupedRanges, testRange);

        assertTrue(groupedRanges.containsKey(testRange.getKey()));
        assertEquals(groupedRanges.get(testRange.getKey()).get(0),testRange.getValue());
    }

    @Test
    public void addRangeToMapWhenKeyExistsInMapWillAddNewEntryToListOfValuesInDescendingOrder() {
        Map<Integer, List<Integer>> groupedRanges = new TreeMap<>();
        Pair<Integer, Integer> firstRange = new Pair<>(5,9);
        Pair<Integer, Integer> secondRange = new Pair<>(5, 20);
        List<Integer> expectedValues = new ArrayList<>();
        expectedValues.add(20);
        expectedValues.add(9);

        RangeUtils.addRangeToMap(groupedRanges, firstRange);
        RangeUtils.addRangeToMap(groupedRanges, secondRange);

        assertEquals(groupedRanges.keySet().size(), 1);
        assertTrue(groupedRanges.containsKey(firstRange.getKey()));
        assertEquals(groupedRanges.get(firstRange.getKey()), expectedValues);
    }

    @Test
    public void findRangesWhenNumberIsInOneIntervalWillReturnOne() {
        Map<Integer, List<Integer>> ranges = new TreeMap<>();
        List<Integer> endOfRange = new ArrayList<>();
        endOfRange.add(9);
        ranges.put(1,endOfRange);

        int actualResult = RangeUtils.findRanges(6, ranges);

        assertEquals(1, actualResult);
    }

    @Test
    public void findRangesWhenNumberIsNotInRangeWillReturnZero() {
        Map<Integer, List<Integer>> ranges = new TreeMap<>();
        List<Integer> endOfRange = new ArrayList<>();
        endOfRange.add(9);
        ranges.put(1,endOfRange);

        int actualResult = RangeUtils.findRanges(10, ranges);

        assertEquals(0, actualResult);
    }

    @Test
    public void findRangesWhenNumberIsInThreeRangesWillReturnThree() {
        Map<Integer, List<Integer>> ranges = new TreeMap<>();
        List<Integer> endOfFirstRange = new ArrayList<>();
        endOfFirstRange.add(9);
        endOfFirstRange.add(8);
        List<Integer> endOfSecondRange = new ArrayList<>();
        endOfSecondRange.add(10);
        ranges.put(1,endOfFirstRange);
        ranges.put(5, endOfSecondRange);

        int actualResult = RangeUtils.findRanges(6, ranges);

        assertEquals(3, actualResult);
    }
}