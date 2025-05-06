package com.anders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import lombok.AllArgsConstructor;

public class Main {
    private static final List<List<String>> map = new ArrayList<>();
    private static final Set<String> frequencies = new HashSet<>();
    private static final Set<Coordinates> antiNodes1 = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Day8!");

        Path path = Paths.get("days/day8/src/main/resources/input.txt");
        List<String> lines;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("Failed to read file");
            throw new RuntimeException(e.getMessage());
        }

        lines.forEach(line -> {
            List<String> split = Arrays.asList(line.split(""));
            split.stream().distinct().forEach(frequencies::add);
            map.add(split);
        });
        frequencies.remove(".");
        frequencies.forEach(Main::part1);
        System.out.println(antiNodes1.size());

        Set<Coordinates> antiNodes2 = new HashSet<>();
        frequencies.forEach(f -> antiNodes2.addAll(part2(f)));
        System.out.println(antiNodes2.size());

        System.out.println("Done!");
    }

    private static void part1(String frequency) {
        List<Coordinates> antennas = getCoordinates(frequency);

        for (int i = 0; i < antennas.size(); i++) {
            for (int j = i + 1; j < antennas.size(); j++) {
                int dx = antennas.get(i).x - antennas.get(j).x;
                int dy = antennas.get(i).y - antennas.get(j).y;
                Coordinates antiNode1 = new Coordinates(antennas.get(i).x + dx, antennas.get(i).y + dy);
                if (verifyCoordinates(antiNode1)) {
                    antiNodes1.add(antiNode1);
                }
                Coordinates antiNode2 = new Coordinates(antennas.get(j).x - dx, antennas.get(j).y - dy);
                if (verifyCoordinates(antiNode2)) {
                    antiNodes1.add(antiNode2);
                }
            }
        }
    }

    private static Set<Coordinates> part2(String frequency) {
        List<Coordinates> antennas = getCoordinates(frequency);
        Set<Coordinates> antiNodes = new HashSet<>();

        for (int i = 0; i < antennas.size(); i++) {
            for (int j = i + 1; j < antennas.size(); j++) {
                int dx = antennas.get(i).x - antennas.get(j).x;
                int dy = antennas.get(i).y - antennas.get(j).y;

                int x1 = antennas.get(i).x + dx;
                int y1 = antennas.get(i).y + dy;
                while (verifyCoordinates(new Coordinates(x1, y1))) {
                    antiNodes.add(new Coordinates(x1, y1));
                    x1 += dx;
                    y1 += dy;
                }

                int x2 = antennas.get(i).x - dx;
                int y2 = antennas.get(i).y - dy;
                while (verifyCoordinates(new Coordinates(x2, y2))) {
                    antiNodes.add(new Coordinates(x2, y2));
                    x2 -= dx;
                    y2 -= dy;
                }
            }
            antiNodes.addAll(antennas);
        }

        return antiNodes;
    }

    private static boolean verifyCoordinates(Coordinates coordinates) {
        return coordinates.x >= 0 && coordinates.x < map.getFirst().size() && coordinates.y >= 0 && coordinates.y < map.size();
    }

    private static List<Coordinates> getCoordinates(String frequency) {
        List<Coordinates> coordinates = new ArrayList<>();

        for (int i = 0; i < map.getFirst().size(); i++) {
            for (int j = 0; j < map.size(); j++) {
                if (map.get(j).get(i).equals(frequency)) {
                    coordinates.add(new Coordinates(i, j));
                }
            }
        }

        return coordinates;
    }

    @AllArgsConstructor
    private static class Coordinates {
        private int x;
        private int y;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o instanceof Coordinates that) {
                return x == that.x && y == that.y;
            }

            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return String.format("(%d, %d)", x, y);
        }
    }
}