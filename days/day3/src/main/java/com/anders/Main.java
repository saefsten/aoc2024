package com.anders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Data;

public class Main {
    public static void main(String[] args) {
        System.out.println("Day3!");

        Path path = Paths.get("days/day3/src/main/resources/input.txt");
        String content;
        try {
            content = new String(Files.readAllBytes(path));
        } catch (IOException e) {
            System.out.println("Failed to read file");
            throw new RuntimeException(e.getMessage());
        }

        List<Match> matches1 = getMatches(content);

        int sum1 = matches1.stream().map(Match::getProduct).reduce(0, Integer::sum);

        System.out.println("Part 1: " + sum1);

        Pattern p = Pattern.compile("mul\\((\\d+),(\\d+)\\)|(do|don't)\\(\\)");

        boolean enabled = true;
        int sum2 = 0;

        Matcher m = p.matcher(content);
        while (m.find()) {
            if (m.group(0).startsWith("don't")) {
                enabled = false;
                continue;
            } else if (m.group(0).startsWith("do()")) {
                enabled = true;
            }
            if (m.group(0).startsWith("mul") && enabled) {
                int product = Integer.parseInt(m.group(1)) * Integer.parseInt(m.group(2));
                sum2 += product;
            }
        }

        /*String regex = "do\\(\\)(.*?)don't\\(\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(String.format("do()%sdon't()", content));
        List<Match> matchesEnabled = new ArrayList<>();
        while (matcher.find()) {
            List<Match> matchesInSection = getMatches(matcher.group());
            matchesEnabled.addAll(matchesInSection);
        }

        List<Match> matches2 = getMatches(enabledContent);

        int sum2 = matches2.stream().map(Match::getProduct).reduce(0, Integer::sum);*/
        System.out.println("Part 2: " + sum2);
    }

    private static List<Match> getMatches(String content) {
        // Get mul(x,y)
        String regex = "mul\\((\\d+),(\\d+)\\)";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        List<Match> matches = new ArrayList<>();

        while (matcher.find()) {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            matches.add(new Match(matcher.group(), x, y));
        }

        return matches;
    }

    @Data
    private static class Match {
        private String match;
        private final int x;
        private final int y;
        private int product;

        public Match(String match, int x, int y) {
            this.match = match;
            this.x = x;
            this.y = y;
            this.product = x * y;
        }

    }
}