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

public class Main {

    private static String[][] map;

    public static void main(String[] args) {
        System.out.println("Day6!");

        Path path = Paths.get("days/day6/src/main/resources/input.txt");
        List<String> lines;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("Failed to read file");
            throw new RuntimeException(e.getMessage());
        }

        map = new String[lines.size()][lines.getFirst().split("").length];
        for (int i = 0; i < lines.size(); i++) {
            String[] chars = lines.get(i).split("");
            map[i] = Arrays.copyOf(chars, chars.length);
        }

        final Position startPosition = new Position();

        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if (Direction.getSymbols().contains(map[row][col])) {
                    startPosition.setCol(col);
                    startPosition.setRow(row);
                    startPosition.setDirection(Objects.requireNonNull(Direction.getFromSymbol(map[row][col])));
                }
            }
        }
        List<Location> visitedLocations =  part1(new Position(startPosition.getCol(), startPosition.getRow(), startPosition.getDirection()));
        part2(new Position(startPosition.getCol(), startPosition.getRow(), startPosition.getDirection()), visitedLocations);

        System.out.println("Done!");
        //printMap();
    }

    private record Location(int col, int row) {}

    private static List<Location> part1(Position position) {
        List<Location> locations = new ArrayList<>();
        while (true) {
            if (!map[position.getRow()][position.getCol()].equals("X")) {
                map[position.getRow()][position.getCol()] = "X";
                locations.add(new Location(position.getCol(), position.getRow()));
            }
            Position next = position.lookAhead();
            if (!onMap(next.getCol(), next.getRow())) {
                break;
            }

            if (map[next.getRow()][next.getCol()].equals("#")) {
                position.turnRight();
                continue;
            }

            position.move();
        }

        System.out.printf("Part 1: distinct positions visited: %d\n", locations.size());

        return locations;
    }

    private static void part2(final Position position, final List<Location> locations) {
        int possibleObstacleLocations = 0;

        for (Location location : locations) {
            if (location.col == position.getCol() && location.row == position.getRow()) {
                continue;
            }

            if (obstacleCausesLoop(new Position(position.getCol(), position.getRow(), position.getDirection()), location.row, location.col)) {
                possibleObstacleLocations++;
            }
        }

        System.out.printf("Part 2: Number of possible locations for obstacle: %d\n", possibleObstacleLocations);
    }

    private static boolean obstacleCausesLoop(Position position, int obstacleRow, int obstacleCol) {
        map[obstacleRow][obstacleCol] = "#";
        Set<Position> visitedPositions = new HashSet<>();
        while (true) {
            if (visitedPositions.stream().anyMatch(vP -> vP.equals(position))) {
                map[obstacleRow][obstacleCol] = ".";
                return true;
            }

            visitedPositions.add(new Position(position.getCol(), position.getRow(), position.getDirection()));

            Position next = position.lookAhead();
            if (!onMap(next.getCol(), next.getRow())) {
                map[obstacleRow][obstacleCol] = ".";
                return false;
            }

            if (map[next.getRow()][next.getCol()].equals("#")) {
                position.turnRight();
                continue;
            }

            position.move();
        }
    }

    private static boolean onMap(int col, int row) {
        return col >= 0 && row >= 0 && row < map.length && col < map[0].length;
    }

    private static void printMap() {
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                System.out.print(map[row][col]);
            }
            System.out.println();
        }
    }
}