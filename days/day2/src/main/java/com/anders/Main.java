package com.anders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Day2");

        Path path = Paths.get("days/day2/src/main/resources/input.txt");
        List<String> lines;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file");
        }

        System.out.println("Read lines: " + lines.size());

        long safeReports = lines.stream().filter(Main::isSafeOrDampenerSafe).count();

        System.out.println("Safe reports: " + safeReports);
    }

    private static boolean isSafeOrDampenerSafe(String line) {
        List<Integer> numbers = getNumberArray(line);
        return isSafe(numbers) || isDampenerSafe(numbers);
    }

    private static boolean isSafe(final List<Integer> numbers) {
        if (numbers.isEmpty() || numbers.size() < 2) {
            return true;
        }

        boolean increasing = numbers.get(0) < numbers.get(1);
        for (int i = 0; i < numbers.size() - 1; i++) {
            if (!checkNumbers(numbers.get(i), numbers.get(i + 1), increasing)) {
                return false;
            }
        }

        return true;
    }

    private static boolean isDampenerSafe(final List<Integer> numbers) {
        for (int i = 0; i < numbers.size(); i++) {
            List<Integer> subset = new ArrayList<>(numbers);
            subset.remove(i);
            if (isSafe(subset)) return true;
        }

        return false;
    }

    private static boolean checkNumbers(int a, int b, boolean increasing) {
        if (increasing) {
            return b > a && (b - a) <= 3;
        } else {
            return b < a && (a - b) <= 3;
        }
    }

    private static List<Integer> getNumberArray(String line) {
        String[] numbers = line.split(" ");
        List<Integer> numberList = new ArrayList<>();

        try {
            for (String number : numbers) {
                numberList.add(Integer.parseInt(number));
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException("Failed to parse number");
        }

        return numberList;
    }
}