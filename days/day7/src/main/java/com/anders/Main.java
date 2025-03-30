package com.anders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class Main {
    public static void main(String[] args) {
        System.out.println("Day7!");

        Path path = Paths.get("days/day7/src/main/resources/input.txt");
        List<String> lines;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("Failed to read file");
            throw new RuntimeException(e.getMessage());
        }

        Map<Long, List<Long>> equations = new HashMap<>();
        lines.forEach(line -> {
            String[] parts = line.split(":");
            Long testValue = Long.parseLong(parts[0].trim());
            List<String> equationValuesStr = Arrays.asList(parts[1].trim().split(" "));
            List<Long> equationValues = equationValuesStr.stream().map(row -> Long.parseLong((row.trim()))).toList();
            equations.put(testValue, equationValues);
        });

        AtomicLong totalPart1 = new AtomicLong();
        equations.forEach((testValue, equationValues) -> {
            if (canBeCombined(testValue, equationValues, false)) {
                totalPart1.addAndGet(testValue);
            }
        });

        AtomicLong totalPart2 = new AtomicLong();
        equations.forEach((testValue, equationValues) -> {
            if (canBeCombined(testValue, equationValues, true)) {
                totalPart2.addAndGet(testValue);
            }
        });

        System.out.printf("Total part 1: %d\nTotal part 2: %d\n", totalPart1.get(), totalPart2.get());
    }

    private static boolean canBeCombined(Long testValue, List<Long> equationValues, boolean includeConcat) {
        List<Long> values = new ArrayList<>();
        values.add(equationValues.getFirst());

        for (int i = 1; i < equationValues.size(); i++) {
            long nextValue = equationValues.get(i);

            List<Long> updatedValues = new ArrayList<>();
            for (long value : values) {
                updatedValues.add(value + nextValue);
                updatedValues.add(value * nextValue);

                if (includeConcat) {
                    updatedValues.add(Long.parseLong(String.format("%d%d", value, nextValue)));
                }
            }

            values = updatedValues.stream().filter(value -> value <= testValue).toList();

            if (values.isEmpty()) {
                return false;
            }
        }

        return values.contains(testValue);
    }
}
