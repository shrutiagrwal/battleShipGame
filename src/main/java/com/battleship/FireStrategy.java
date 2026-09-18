package com.battleship;

import java.util.Set;

public interface FireStrategy {
    Coordinate nextTarget(Set<Coordinate> remainingTargets);
}
