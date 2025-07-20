package org.example.model.animals.herbivore;

import org.example.model.animals.Animal;

public abstract class Herbivore extends Animal {
    public Herbivore(int id, double foodNeeded, int speed, int maxAmount, double weight) {
        super(id, foodNeeded, speed, maxAmount, weight);
    }
}
