package com.anders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sun.tools.jconsole.JConsoleContext;

public class Main {
    public static void main(String[] args) {
        System.out.println("Day9!");

        Path path = Paths.get("days/day9/src/main/resources/input.txt");
        String content;
        try {
            content = new String(Files.readAllBytes(path));
        } catch (IOException e) {
            System.out.println("Failed to read file");
            throw new RuntimeException(e.getMessage());
        }

        String[] diskMap = content.split("");
        List<String> convertedMap = new ArrayList<>();
        int id = 0;
        for (int i = 0; i < diskMap.length; i++) {
            int num  = Integer.parseInt(diskMap[i]);
            String[] adds = new String[num];
            if (i % 2 == 0) {
                Arrays.fill(adds, String.valueOf(id));
                id++;
            } else {
                Arrays.fill(adds, ".");
            }
            convertedMap.addAll(Arrays.asList(adds));
        }

        String[] convertedDiskMap = convertedMap.toArray(new String[convertedMap.size()]);

        part1(convertedDiskMap);
        part2(convertedDiskMap);

        System.out.println("Done!");
    }

    public static void part1(String[] convertedDiskMap) {
        for (int i = 0; i < convertedDiskMap.length; i++) {
            if (convertedDiskMap[i].equals(".")) {
                for (int j = convertedDiskMap.length - 1; j > i; j--) {
                    if (!convertedDiskMap[j].equals(".")) {
                        convertedDiskMap[i] = convertedDiskMap[j];
                        convertedDiskMap[j] = ".";
                        break;
                    }
                }
            }
        }

        System.out.println(getCheckSum(convertedDiskMap));
    }

    public static void part2(String[] convertedDiskMap) {
        int fileSize = 0;
        String current = null;
        Set<String> checkedNumbers = new HashSet<>();

        for (int i = convertedDiskMap.length - 1; i > 0; i--) {
            boolean stop = false;
            if (current == null && convertedDiskMap[i].equals(".")) {
                continue;
            } else if (current == null) {
                if (checkedNumbers.contains(convertedDiskMap[i])) {
                    continue;
                }
                current = convertedDiskMap[i];
                checkedNumbers.add(current);
                fileSize++;
                if (!convertedDiskMap[i - 1].equals(current)) {
                    stop = true;
                }
            } else if (convertedDiskMap[i].equals(current)) {
                fileSize++;
                if (!convertedDiskMap[i - 1].equals(current)) {
                    stop = true;
                }
            }

            if (stop) {
                int emptySize = 0;
                int positionWithSpace = -1;
                for (int j = 0; j < i; j++) {
                    if (convertedDiskMap[j].equals(".")) {
                        emptySize++;
                    } else {
                        emptySize = 0;
                    }
                    if (emptySize == fileSize) {
                        positionWithSpace = j;
                        break;
                    }

                }
                if (positionWithSpace > 0) {
                    for (int k = positionWithSpace; k > positionWithSpace - fileSize; k--) {
                        convertedDiskMap[k] = current;
                    }

                    for (int k = i; k < i + fileSize; k++) {
                        convertedDiskMap[k] = ".";
                    }
                }

                current = null;
                fileSize = 0;
            }
        }

        System.out.println(getCheckSum(convertedDiskMap));
    }

    private static void printDiskMap(String[] diskMap) {
        System.out.println(Arrays.stream(diskMap).reduce("", (a, b) -> a + b));
        System.out.println();
    }

    private static long getCheckSum(String[] convertedDiskMap) {
        long checkSum = 0;

        for (int i = 0; i < convertedDiskMap.length; i++) {
            try {
                long sum = i * Long.parseLong(convertedDiskMap[i]);
                checkSum += sum;
            } catch (NumberFormatException e) {}
        }

        return checkSum;
    }
}
