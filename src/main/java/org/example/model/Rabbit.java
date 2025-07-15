package org.example.model;

import org.example.map.Cell;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Rabbit extends Herbivore {
    public Rabbit() {
        super(0.45, 2, 150, 2);
    }

    @Override
    public void reproduce(Cell cell) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (Animal animal : new ArrayList<>(cell.getAnimals())) {
            if (animal instanceof Rabbit && animal != this) {
                if (new ArrayList<>(cell.getAnimals()).stream()
                        .filter(a -> a instanceof Wolf)
                        .toList().size() < maxAmount) {
                    cell.addAnimal(this.getClass().getConstructor().newInstance());
                    System.out.println("Rabbit reproduced");
                }
            }
        }
    }

}
