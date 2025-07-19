package org.example.model;

import org.example.map.Cell;
import org.example.statistics.StatisticTracker;
import org.example.util.RandomUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Wolf extends Predator {


    public Wolf() {
        super(0, 8, 3, 30, 50);
    }

    @Override
    public void reproduce(Cell cell, StatisticTracker tracker) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (Animal animal : new ArrayList<>(cell.getAnimals())) {
            if (animal instanceof Wolf && animal != this) {
                if (new ArrayList<>(cell.getAnimals()).stream()
                        .filter(a -> a instanceof Wolf)
                        .toList().size() < maxAmount) {
                    if(RandomUtil.getRandomBoolean(1, 4)) {
                        cell.addAnimal(this.getClass().getConstructor().newInstance());
//                        System.out.println("Wolf reproduced");
                        tracker.increment(this.getClass().getSimpleName() + AnimalType.valueOf(this.getClass().getSimpleName().toUpperCase()).getEmoji(), "reproduced");
                    }
                    return;
                }
            }
        }
    }
}
