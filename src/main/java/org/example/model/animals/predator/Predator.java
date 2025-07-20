package org.example.model.animals.predator;

import org.example.model.animals.Animal;

public abstract class Predator extends Animal {

    public Predator(int id, double foodNeeded, int speed, int maxAmount, double weight) {
        super(id, foodNeeded, speed, maxAmount, weight);
    }
}
