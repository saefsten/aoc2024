package com.anders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final int blinks = 75;
    private static Map<Long, Long> stones = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Day11!");

        Path path = Paths.get("days/day11/src/main/resources/input.txt");
        String content;
        try {
            content = new String(Files.readAllBytes(path));
        } catch (IOException e) {
            System.out.println("Failed to read file");
            throw new RuntimeException(e.getMessage());
        }

        String[] stonesArr = content.split(" ");
        for (String stone : stonesArr) {
            stones.put(Long.parseLong(stone), stones.getOrDefault(Long.parseLong(stone), 0L) + 1);
        }

        for (int blink = 0; blink < blinks; blink++) {
            Map<Long, Long> blinkedStones = new HashMap<>();
            for (Map.Entry<Long, Long> entry : stones.entrySet()) {
                long numberOnStone = entry.getKey();
                long n_what = entry.getValue();
                if (numberOnStone == 0) {
                    blinkedStones.put(1L, n_what);
                } else if (String.valueOf(numberOnStone).length() % 2 == 0) {
                    String firstStone = String.valueOf(numberOnStone).substring(0, String.valueOf(numberOnStone).length() / 2);
                    String secondStone = String.valueOf(numberOnStone).substring(String.valueOf(numberOnStone).length() / 2);
                    blinkedStones.put(Long.valueOf(firstStone), blinkedStones.getOrDefault(Long.valueOf(firstStone), 0L) + n_what);
                    blinkedStones.put(Long.valueOf(secondStone), blinkedStones.getOrDefault(Long.valueOf(secondStone), 0L) + n_what);
                } else {
                    blinkedStones.put(numberOnStone * 2024, n_what);
                }
            }
            stones = blinkedStones;
        }

        long count = stones.values().stream().reduce(0L, Long::sum);

        System.out.printf("Stones: %d\n", count);
        System.out.println("Done");
    }
}