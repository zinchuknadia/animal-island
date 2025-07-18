package org.example.model;

public abstract class Herbivore extends Animal {
    public Herbivore(int id, double foodNeeded, int speed, int maxAmount, double weight) {
        super(id, foodNeeded, speed, maxAmount, weight);
    }
}
