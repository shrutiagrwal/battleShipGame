package com.battleship;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Player {
    @Getter
    private final String name;
    private final int minX;
    private final int maxX;
    private final List<Ship> ships = new ArrayList<>();
    private final Set<Coordinate> fired = new HashSet<>();

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
        return ships.stream().anyMatch(ship -> ship.hasId(id));
    }

    public void markFired(Coordinate cell) {
        fired.add(cell);
    }

    public boolean alreadyFired(Coordinate cell) {
        return fired.contains(cell);
    }

    public int remainingShips() {
        return (int) ships.stream().filter(ship -> !ship.isDestroyed()).count();
    }
}
