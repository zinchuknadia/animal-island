package org.example.model;

import org.example.map.Cell;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Wolf extends Predator {


    public Wolf() {
        super(8, 3, 30, 50);
    }

    @Override
    public void eat() {
        System.out.println("Wolf eat");
    }

    @Override
    public void reproduce(Cell cell) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (Animal animal : new ArrayList<>(cell.getAnimals())) {
            if (animal instanceof Wolf && animal != this) {
                if (new ArrayList<>(cell.getAnimals()).stream()
                        .filter(a -> a instanceof Wolf)
                        .toList().size() < maxAmount) {
                    cell.addAnimal(this.getClass().getConstructor().newInstance());
                    System.out.println("Wolf reproduced");
                }

            }
        }
    }
}
