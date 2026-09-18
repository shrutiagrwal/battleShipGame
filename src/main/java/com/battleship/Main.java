package com.battleship;

public class Main {
    public static void main(String[] args) {
        BattleShipGame game = new BattleShipGame(new RandomFireStrategy());

        System.out.println(">> initGame(6)");
        game.initGame(6);

        System.out.println(">> addShip(\"SH1\", size = 2, 1, 5, 4, 4)");
        game.addShip("SH1", 2, 1, 5, 4, 4);

        System.out.println(">> viewBattleField()");
        game.viewBattleField();

        System.out.println(">> startGame()");
        game.startGame();
    }
}
