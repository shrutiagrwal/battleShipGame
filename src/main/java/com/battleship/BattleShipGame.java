package com.battleship;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BattleShipGame {
    private final FireStrategy fireStrategy;
    private int n;
    private Player playerA;
    private Player playerB;
    private final Map<Coordinate, Ship> occupancy = new HashMap<>();
    private boolean started;

    public void initGame(int n) {
        if (n < 2 || n % 2 != 0) {
            System.out.println("N must be an even integer >= 2");
            return;
        }
        this.n = n;
        this.playerA = new Player("PlayerA", 0, n / 2);
        this.playerB = new Player("PlayerB", n / 2, n);
        this.occupancy.clear();
        this.started = false;
    }

    public void addShip(String id, int size, int xa, int ya, int xb, int yb) {
        if (playerA == null || started) {
            System.out.println("Initialize the game and add ships before startGame()");
            return;
        }
        if (id == null || id.isBlank() || playerA.hasShipId(id) || size <= 0) {
            System.out.println("Invalid ship id or size");
            return;
        }

        Set<Coordinate> cellsA = occupiedCells(new Coordinate(xa, ya), size);
        Set<Coordinate> cellsB = occupiedCells(new Coordinate(xb, yb), size);
        if (!canPlace(playerA, cellsA) || !canPlace(playerB, cellsB)) {
            return;
        }

        place(playerA, new Ship(id, "A-" + id, cellsA));
        place(playerB, new Ship(id, "B-" + id, cellsB));
    }

    public void viewBattleField() {
        if (playerA == null) {
            System.out.println("Call initGame(N) first");
            return;
        }
        for (int y = n - 1; y >= 0; y--) {
            StringBuilder row = new StringBuilder("y=" + y + " |");
            for (int x = 0; x < n; x++) {
                Ship ship = occupancy.get(new Coordinate(x, y));
                row.append(ship == null ? " .    " : String.format(" %-5s", ship.getLabel()));
            }
            System.out.println(row);
        }
    }

    public void startGame() {
        if (playerA == null || !playerA.hasShips() || started) {
            System.out.println("Initialize, add ships, then call startGame() once");
            return;
        }
        started = true;

        Player attacker = playerA;
        Player defender = playerB;
        while (defender.remainingShips() > 0) {
            Set<Coordinate> remaining = remainingTargets(attacker, defender);
            if (remaining.isEmpty()) {
                return;
            }
            Coordinate target = fireStrategy.nextTarget(remaining);
            attacker.markFired(target);

            Ship hit = occupancy.get(target);
            if (hit != null && !hit.isDestroyed()) {
                hit.destroy();
                for (Coordinate cell : hit.getCells()) {
                    occupancy.remove(cell);
                }
                printTurn(attacker, target, "\"Hit\" " + hit.getLabel() + " destroyed");
            } else {
                printTurn(attacker, target, "\"Miss\"");
            }

            if (defender.remainingShips() == 0) {
                System.out.println("GameOver. " + attacker.getName() + " wins.");
                return;
            }

            Player swap = attacker;
            attacker = defender;
            defender = swap;
        }
    }

    private Set<Coordinate> occupiedCells(Coordinate center, int size) {
        int left = center.getX() - size / 2;
        int bottom = center.getY() - size / 2;
        Set<Coordinate> cells = new HashSet<>();
        for (int x = left; x < left + size; x++) {
            for (int y = bottom; y < bottom + size; y++) {
                cells.add(new Coordinate(x, y));
            }
        }
        return cells;
    }

    private boolean canPlace(Player player, Set<Coordinate> cells) {
        for (Coordinate cell : cells) {
            if (cell.getY() < 0 || cell.getY() >= n || !player.owns(cell)) {
                System.out.println(player.getName() + " ship is outside their half at " + cell);
                return false;
            }
            if (occupancy.containsKey(cell)) {
                System.out.println("Ships overlap at " + cell);
                return false;
            }
        }
        return true;
    }

    private void place(Player player, Ship ship) {
        player.addShip(ship);
        for (Coordinate cell : ship.getCells()) {
            occupancy.put(cell, ship);
        }
    }

    private Set<Coordinate> remainingTargets(Player attacker, Player defender) {
        Set<Coordinate> remaining = new HashSet<>();
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                Coordinate cell = new Coordinate(x, y);
                if (defender.owns(cell) && !attacker.alreadyFired(cell)) {
                    remaining.add(cell);
                }
            }
        }
        return remaining;
    }

    private void printTurn(Player attacker, Coordinate target, String outcome) {
        System.out.println(attacker.getName()
                + "'s turn: Missile fired at "
                + target
                + " : "
                + outcome
                + " : Ships Remaining - PlayerA:"
                + playerA.remainingShips()
                + ", PlayerB:"
                + playerB.remainingShips());
    }
}
