package com.battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomFireStrategy implements FireStrategy {
    private final Random random = new Random();

    @Override
    public Coordinate nextTarget(Set<Coordinate> remainingTargets) {
        List<Coordinate> choices = new ArrayList<>(remainingTargets);
        return choices.get(random.nextInt(choices.size()));
    }
}
