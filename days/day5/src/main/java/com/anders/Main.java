package com.anders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {

    private static List<Rule> rules = null;

    public static void main(String[] args) {
        System.out.println("Day5!");

        Path path = Paths.get("days/day5/src/main/resources/input.txt");
        List<String> inputLines;
        try {
            inputLines = Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("Failed to read file");
            throw new RuntimeException(e.getMessage());
        }

        Path pathRules = Paths.get("days/day5/src/main/resources/rules.txt");
        List<String> rulesLines;
        try {
            rulesLines = Files.readAllLines(pathRules);
        } catch (IOException e) {
            System.out.println("Failed to read file");
            throw new RuntimeException(e.getMessage());
        }

        rules = new ArrayList<>();
        rulesLines.forEach(line -> {
            String[] rule = line.split("\\|");
            rules.add(new Rule(rule[0], rule[1]));
        });

        List<List<String>> input = inputLines.stream().map(line -> Arrays.asList(line.split(","))).toList();
        List<List<String>> linesInorder = new ArrayList<>();
        List<List<String>> linesNotInorder = new ArrayList<>();

        for (var inputLine : input) {
            boolean rowInOrder = true;
            for (int i = 1; i < inputLine.size(); i++) {
                String num = inputLine.get(i);
                List<Rule> relevantRules = rules.stream().filter(rule -> rule.before.equals(num)).toList();
                if (!relevantRules.isEmpty()) {
                    for (Rule rule : relevantRules) {
                        if (inputLine.subList(0, i).contains(rule.after)) {
                            rowInOrder = false;
                            break;
                        }
                    }
                }
            }
            if (rowInOrder) {
                linesInorder.add(inputLine);
            } else {
                linesNotInorder.add(inputLine);
            }
        }

        int sum = linesInorder.stream().map(line -> Integer.parseInt(line.get(line.size()/2))).reduce(0, Integer::sum);
        System.out.println("Sum middle numbers part 1: " + sum);

        int sum2 = 0;
        for (List<String> line : linesNotInorder) {
            line.sort(new RuleSorter<String>() {});
            int middleNumber = Integer.parseInt(line.get(line.size() / 2));
            sum2 += middleNumber;
        }

        System.out.println("Sum middle numbers part 2: " + sum2);
        System.out.println("Done!");
    }

    private record Rule (String before, String after) {}

    public static class RuleSorter<S> implements Comparator<String> {

        @Override
        public int compare(String num1, String num2) {
            if (num1.equals(num2)) {
                return 0;
            }

            List<Rule> rulesNum2Before = rules.stream().filter(rule -> rule.before.equals(num2)).toList();
            if (!rulesNum2Before.isEmpty()) {
                return rulesNum2Before.stream().anyMatch(rule -> rule.after.equals(num1)) ? -1 : 0;
            }

            List<Rule> rulesNum1Before = rules.stream().filter(rule -> rule.before.equals(num1)).toList();
            if (!rulesNum1Before.isEmpty()) {
                return rulesNum1Before.stream().anyMatch(rule -> rule.after.equals(num2)) ? 1 : 0;
            }
            return 0;
        }
    }
}