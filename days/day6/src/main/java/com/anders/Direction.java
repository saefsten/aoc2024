package com.anders;

import java.util.Arrays;
import java.util.List;

public enum Direction {
    UP("^"), DOWN("v"), LEFT("<"), RIGHT(">");

    private final String symbol;

    Direction(String s) {
        symbol = s;
    }

    public static List<String> getSymbols() {
        return Arrays.stream(Direction.values()).map(v -> v.symbol).toList();
    }

    public static Direction getFromSymbol(String symbol) {
        for (Direction direction : Direction.values()) {
            if (direction.symbol.equals(symbol)) {
                return direction;
            }
        }
        return null;
    }

    public static Direction turnRight(Direction direction) {
        return switch (direction) {
            case UP -> RIGHT;
            case RIGHT -> DOWN;
            case DOWN -> LEFT;
            case LEFT -> UP;
        };
    }
}
