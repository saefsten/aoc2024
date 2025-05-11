package com.anders;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class Explorer {

    private final int yMax;
    private final int xMax;
    private int rating;
    private final int[][] map;
    private Set<Location> endpoints;

    public Explorer(int[][] map) {
        this.map = map;
        this.rating = 0;
        this.endpoints = new HashSet<>();
        this.yMax = map.length;
        this.xMax = map[0].length;
    }

    public void collect(int y, int x) {
        if (map[y][x] == 9) {
            endpoints.add(new Location(y, x));
            rating++;
        } else {
            if (canGoUp(y, x)) {
                collect(y - 1, x);
            }
            if (canGoDown(y, x)) {
                collect(y + 1, x);
            }
            if (canGoLeft(y, x)) {
                collect(y, x - 1);
            }
            if (canGoRight(y, x)) {
                collect(y, x + 1);
            }
        }
    }

    boolean canGoUp(int y, int x) {
        return y > 0 && map[y - 1][x] == (map[y][x] + 1);
    }

    boolean canGoDown(int y, int x) {
        return y < (yMax - 1) && map[y + 1][x] == (map[y][x] + 1);
    }

    boolean canGoLeft(int y, int x) {
        return x > 0 && map[y][x - 1] == (map[y][x] + 1);
    }

    boolean canGoRight(int y, int x) {
        return x < (xMax - 1) && map[y][x + 1] == (map[y][x] + 1);
    }
}
