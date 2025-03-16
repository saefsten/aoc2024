package com.anders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Position {
    private int col;
    private int row;
    private Direction direction;

    public void move() {
        switch (direction) {
            case UP:
                row -= 1;
                break;
            case DOWN:
                row += 1;
                break;
            case LEFT:
                col -= 1;
                break;
            case RIGHT:
                col += 1;
                break;
        }
    }

    public Position lookAhead() {
        return switch (direction) {
            case UP -> new Position(col, row - 1, direction);
            case DOWN -> new Position(col, row + 1, direction);
            case LEFT -> new Position(col - 1, row, direction);
            case RIGHT -> new Position(col + 1, row, direction);
        };
    }

    public void turnRight() {
        direction = Direction.turnRight(direction);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return this.row == ((Position) o).row &&
                this.col == ((Position) o).col &&
                this.direction == ((Position) o).direction;
    }
}
