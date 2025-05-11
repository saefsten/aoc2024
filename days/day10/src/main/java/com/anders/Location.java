package com.anders;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {
    int y;
    int x;

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return y == location.y && x == location.x;
    }

    @Override
    public int hashCode() {
        return Objects.hash(y, x);
    }
}
