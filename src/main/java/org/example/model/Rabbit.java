package org.example.model;

import org.example.map.Cell;
import org.example.statistics.StatisticTracker;
import org.example.util.RandomUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Rabbit extends Herbivore {
    public Rabbit() {
        super(7, 0.45, 2, 150, 2);
    }

    @Override
    public void reproduce(Cell cell, StatisticTracker tracker) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (Animal animal : new ArrayList<>(cell.getAnimals())) {
            if (animal instanceof Rabbit && animal != this) {
                if (new ArrayList<>(cell.getAnimals()).stream()
                        .filter(a -> a instanceof Rabbit)
                        .toList().size() < maxAmount) {
                    if(RandomUtil.getRandomBoolean(1, 4)) {
                        cell.addAnimal(this.getClass().getConstructor().newInstance());
                        tracker.increment(this.getClass().getSimpleName(), "reproduced");
//                        System.out.println("Rabbit reproduced");
                    }
                    return;
                }
            }
        }
    }

}
