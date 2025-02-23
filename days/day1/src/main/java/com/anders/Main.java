package com.anders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        System.out.println("Day1");

        Path path = Paths.get("days/day1/src/main/resources/input.txt");
        List<String> lines;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file");
        }

        List<Long> left = new ArrayList<>();
        List<Long> right = new ArrayList<>();

        try {
            lines.forEach(line -> {
                String[] lineSplit = line.split("\t");
                left.add(Long.parseLong(lineSplit[0]));
                right.add(Long.parseLong(lineSplit[1]));
            });
        } catch (NumberFormatException e) {
            throw new RuntimeException("Failed to parse input");
        }

        List<Long> sortedLeft = left.stream().sorted().toList();
        List<Long> sortedRight = right.stream().sorted().toList();

        System.out.println("Read file");

        int difference = 0;

        for (int i = 0; i < sortedLeft.size(); i++) {
            long diff = Math.abs(sortedLeft.get(i) - sortedRight.get(i));
            difference += diff;
        }

        System.out.println("Total difference: " + difference);

        List<Long> similarityScores = new ArrayList<>();

        left.forEach(leftNum -> {
            long count = right.stream().filter(rightNum -> Objects.equals(rightNum, leftNum)).count();
            similarityScores.add(count * leftNum);
        });

        long similarityScore = similarityScores.stream().reduce(0L, Long::sum);

        System.out.println("Similarity score: " + similarityScore);
    }

}