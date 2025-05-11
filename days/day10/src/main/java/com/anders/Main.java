package com.anders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Main {

    static int[][] map;
    static int yMax;
    static int xMax;

    public static void main(String[] args) {
        System.out.println("Day10!");

        Path path = Paths.get("days/day10/src/main/resources/input.txt");
        List<String> lines;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("Failed to read file");
            throw new RuntimeException(e.getMessage());
        }

        xMax = lines.getFirst().length();
        yMax  = lines.size();
        map = new int[yMax][xMax];


        for (int y1 = 0; y1 < yMax; y1++) {
            List<String> row = Arrays.stream(lines.get(y1).split("")).toList();
            for (int x1 = 0; x1 < xMax; x1++) {
                map[y1][x1] = Integer.parseInt(row.get(x1));
            }
        }

        int totalPoints = 0;
        int totalRating = 0;
        for (int y = 0; y < yMax; y++) {
            for (int x = 0; x < xMax; x++) {
                if (map[y][x] != 0) {
                    continue;
                }

                Explorer explorer = new Explorer(map);
                explorer.collect(y, x);
                totalPoints += explorer.getEndpoints().size();
                totalRating += explorer.getRating();
            }
        }

        System.out.println("Part 1: " + totalPoints);
        System.out.println("Part 2: " + totalRating);
    }
}