package com.battleship;

import lombok.Value;

@Value
public class Coordinate {
    int x;
    int y;

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
