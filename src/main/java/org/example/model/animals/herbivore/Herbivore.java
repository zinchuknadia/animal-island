package org.example.model.animals.herbivore;

import org.example.map.Cell;
import org.example.model.animals.Animal;
import org.example.model.plants.Plant;
import org.example.statistics.EventTracker;

public abstract class Herbivore extends Animal {
    public Herbivore(int id, double foodNeeded, int speed, int maxAmount, double weight) {
        super(id, foodNeeded, speed, maxAmount, weight);
    }

    public void findAndEat(Cell cell, EventTracker tracker) {
        Plant plant = getRandomPlant(cell);
        if (plant != null) {
            this.eat(plant, tracker);
        } else {
            this.eat(getRandomPrey(cell, this), tracker);
        }
    }
}
