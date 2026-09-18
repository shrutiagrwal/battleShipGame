package com.battleship;

import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Ship {
    private final String id;
    private final String label;
    private final Set<Coordinate> cells;
    private boolean destroyed;

    public boolean hasId(String shipId) {
        return id.equals(shipId);
    }

    public void destroy() {
        destroyed = true;
    }
}
