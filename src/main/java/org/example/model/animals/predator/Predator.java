package org.example.model.animals.predator;

import org.example.map.Cell;
import org.example.model.animals.Animal;
import org.example.statistics.EventTracker;

public abstract class Predator extends Animal {
    public Predator(int id, double foodNeeded, int speed, int maxAmount, double weight) {
        super(id, foodNeeded, speed, maxAmount, weight);
    }

    public void findAndEat(Cell cell, EventTracker tracker) {
        this.eat(cell, getRandomPrey(cell, this), tracker);
    }
}
