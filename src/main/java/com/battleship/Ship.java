package com.battleship;

import java.util.Set;

public class Ship {
    private final String id;
    private final String label;
    private final Set<Coordinate> cells;
    private boolean destroyed;

    public Ship(String id, String label, Set<Coordinate> cells) {
        this.id = id;
        this.label = label;
        this.cells = cells;
    }

    public boolean hasId(String shipId) {
        return id.equals(shipId);
    }

    public String getLabel() {
        return label;
    }

    public Set<Coordinate> getCells() {
        return cells;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void destroy() {
        destroyed = true;
    }
}
