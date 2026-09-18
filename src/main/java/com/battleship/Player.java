package com.battleship;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Player {
    private final String name;
    private final int minX;
    private final int maxX;
    private final List<Ship> ships = new ArrayList<>();
    private final Set<Coordinate> fired = new HashSet<>();

    public Player(String name, int minX, int maxX) {
        this.name = name;
        this.minX = minX;
        this.maxX = maxX;
    }

    public String getName() {
        return name;
    }

    public boolean owns(Coordinate cell) {
        return cell.getX() >= minX && cell.getX() < maxX;
    }

    public void addShip(Ship ship) {
        ships.add(ship);
    }

    public boolean hasShips() {
        return !ships.isEmpty();
    }

    public boolean hasShipId(String id) {
        for (Ship ship : ships) {
            if (ship.hasId(id)) {
                return true;
            }
        }
        return false;
    }

    public void markFired(Coordinate cell) {
        fired.add(cell);
    }

    public boolean alreadyFired(Coordinate cell) {
        return fired.contains(cell);
    }

    public int remainingShips() {
        int count = 0;
        for (Ship ship : ships) {
            if (!ship.isDestroyed()) {
                count++;
            }
        }
        return count;
    }
}
