package org.example.model.animals;

public abstract class Predator extends Animal {

    public Predator(int id, double foodNeeded, int speed, int maxAmount, double weight) {
        super(id, foodNeeded, speed, maxAmount, weight);
    }
}
