package com.anders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    final static String XMAS = "XMAS";
    final static String SMAX = "SAMX";

    public static void main(String[] args) {
        System.out.println("Day4!");

        Path path = Paths.get("days/day4/src/main/resources/input.txt");
        List<String> lines;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("Failed to read file");
            throw new RuntimeException(e.getMessage());
        }

        List<List<String>> charMatrix = new ArrayList<>();
        for (String line : lines) {
            String[] chars = line.split("");
            List<String> charList = Arrays.asList(chars);
            charMatrix.add(charList);
        }

        int count1 = 0;
        int numOfRows = charMatrix.size();
        int numOfCols = charMatrix.getFirst().size();

        for (int r = 0; r < numOfRows; r++) {
            for (int c = 0; c < numOfCols; c++) {
                if (charMatrix.get(r).get(c).equals("X") || charMatrix.get(r).get(c).equals("S")) {
                    // Line forward
                    boolean okForward = c + 4 <= numOfCols;
                    boolean okBackward = c - 3 >= 0;
                    boolean okDownward = r + 4 <= numOfRows;
                    String forward = okForward ? String.join("", charMatrix.get(r).subList(c, c + 4)) : "";
                    String downward = okDownward ? charMatrix.get(r).get(c) + charMatrix.get(r + 1).get(c) + charMatrix.get(r + 2).get(c) + charMatrix.get(r + 3).get(c) : "";
                    String diagonalRight = okForward && okDownward ? charMatrix.get(r).get(c) + charMatrix.get(r + 1).get(c + 1) + charMatrix.get(r + 2).get(c + 2) + charMatrix.get(r + 3).get(c + 3) : "";
                    String diagonalLeft = okBackward && okDownward ? charMatrix.get(r).get(c) + charMatrix.get(r + 1).get(c - 1) + charMatrix.get(r + 2).get(c - 2) + charMatrix.get(r + 3).get(c - 3) : "";

                    count1 += countOccurrences(forward) + countOccurrences(downward) + countOccurrences(diagonalRight) + countOccurrences(diagonalLeft);
                }
            }
        }
        System.out.println("Total count part 1: " + count1);

        int count2 = 0;
        for (int r = 0; r < numOfRows; r++) {
            for (int c = 0; c < numOfCols; c++) {
                if (charMatrix.get(r).get(c).equals("A")) {
                    boolean okForward = c + 1 < numOfCols;
                    boolean okBackward = c - 1 >= 0;
                    boolean okDownward = r + 1 < numOfRows;
                    boolean okUpward = r - 1 >= 0;

                    if (!okForward || !okBackward || !okDownward || !okUpward) {
                        continue;
                    }

                    if (checkXMAS(charMatrix.get(r + 1).get(c + 1), charMatrix.get(r + 1).get(c - 1), charMatrix.get(r - 1).get(c + 1), charMatrix.get(r - 1).get(c - 1))) {
                        count2 += 1;
                    }

                }
            }
        }

        System.out.println("Total count part 2: " + count2);
        System.out.println("Done!");
    }

    private static int countOccurrences(String str) {
        int count = 0;
        int fromIndex = 0;

        while ((fromIndex = str.indexOf(XMAS, fromIndex)) != -1) {
            count++;
            fromIndex += XMAS.length();
        }

        fromIndex = 0;

        while ((fromIndex = str.indexOf(SMAX, fromIndex)) != -1) {
            count++;
            fromIndex += SMAX.length();
        }

        return count;
    }

    private static boolean checkXMAS(String ur, String ul, String lr, String ll) {
        return (ur.equals("M")  && ll.equals("S") || ur.equals("S") && ll.equals("M")) && (ul.equals("M") && lr.equals("S") || ul.equals("S") && lr.equals("M"));
    }
}