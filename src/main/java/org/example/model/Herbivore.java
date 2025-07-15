package org.example.model;

public abstract class Herbivore extends Animal {
    public Herbivore(double foodNeeded, int speed, int maxAmount, double weight) {
        super(foodNeeded, speed, maxAmount, weight);
    }

    @Override
    public void eat() {
        System.out.println("Herbivore eat");
    }
}
