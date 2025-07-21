package org.example.model.animals;

import org.example.map.Cell;
import org.example.map.IslandMap;
import org.example.model.Organism;
import org.example.model.plants.Plant;
import org.example.statistics.EventTracker;
import org.example.util.RandomUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public abstract class Animal extends Organism {
    protected int speed;
    protected double foodNeeded;
    protected double fedLevel;
    protected int reproductionChance;
    protected final int[][] eatChanceMatrix = {
            {0, 0, 0, 0, 0, 10, 15, 60, 80, 60, 70, 15, 10, 40, 0, 0},
            {0, 0, 15, 0, 0, 0, 0, 20, 40, 0, 0, 0, 0, 10, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 70, 90, 0, 0, 0, 0, 60, 40, 0},
            {0, 80, 0, 0, 0, 40, 80, 80, 90, 70, 70, 50, 20, 10, 0, 0},
            {0, 0, 10, 0, 0, 0, 0, 90, 90, 0, 0, 0, 0, 80, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 90, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 50, 0, 0, 0, 0, 0, 90, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 90, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100}
    };

    public Animal(int id, double foodNeeded, int speed, int maxAmount, double weight) {
        super(id, weight, maxAmount);
        this.foodNeeded = foodNeeded;
        this.fedLevel = foodNeeded;
        this.speed = speed;
        this.reproductionChance = 25;
    }

    public void move(IslandMap map) {
        Cell newLocation = findValidLocation(map);

        Cell first = currentLocation;
        Cell second = newLocation;
        if (System.identityHashCode(second) < System.identityHashCode(first)) {
            Cell temp = first;
            first = second;
            second = temp;
        }
        synchronized (first) {
            synchronized (second) {
                if (getAnimalCount(newLocation, this.getClass()) < maxAmount) {
                    newLocation.addAnimal(this);
                    currentLocation.removeAnimal(this);
                    currentLocation = newLocation;
                }
            }
        }
    }

    public Cell findValidLocation(IslandMap map) {
        Cell newLocation;
        int count = 0;
        do {
            newLocation = RandomUtil.getRandomCell(map, currentLocation, speed);
            count++;
        } while (getAnimalCount(newLocation, this.getClass()) >= maxAmount && count < 5);
        return newLocation;
    }

    public int getAnimalCount(Cell cell, Class<? extends Animal> clazz) {
        return (int) cell.getAnimals().stream()
                .filter(a -> a.getClass().equals(clazz))
                .count();
    }

    public abstract void findAndEat(Cell cell, EventTracker tracker);

    public void eat(Organism prey, EventTracker tracker) {
        if (prey == null) return;

        int randomChance = RandomUtil.getRandomInt(1, 100);
        int eatChance = getEatChance(this.getId(), prey.getId());

        if (randomChance > 0 && randomChance <= eatChance) {
            getFed(prey);
            prey.onEaten(tracker);
        }
    }

    public Animal getRandomPrey(Cell cell, Animal predator) {
        List<Animal> animals = cell.getAnimals();
        List<Animal> preys = new ArrayList<>();
        for (Animal animal : animals) {
            if (!animal.getClass().equals(predator.getClass())) {
                preys.add(animal);
            }
        }
        if (preys.size() <= 0) {
            return null;
        }
        int randomIndex = RandomUtil.getRandomInt(0, animals.size() - 1);
        return animals.get(randomIndex);
    }

    public Plant getRandomPlant(Cell cell) {
        List<Plant> plants = cell.getPlants();
        if (plants.size() <= 0) return null;
        int randomIndex = RandomUtil.getRandomInt(0, plants.size() - 1);
        return plants.get(randomIndex);
    }

    public int getEatChance(int predator, int prey) {
        return eatChanceMatrix[predator][prey];
    }

    public void getFed(Organism prey) {
        fedLevel += prey.getWeight();
        if (fedLevel > foodNeeded) {
            fedLevel = foodNeeded;
        }
    }

    public void getHungry(EventTracker tracker) {
        fedLevel -= foodNeeded * 0.15;
        if (fedLevel < 0) {
            this.die();
            tracker.increment(this.getClass().getSimpleName() + AnimalType.valueOf(this.getClass().getSimpleName().toUpperCase()).getEmoji(), "starved");
        }
    }

    @Override
    public void onEaten(EventTracker tracker) {
        this.die();
        tracker.increment(this.getClass().getSimpleName() + AnimalType.valueOf(this.getClass().getSimpleName().toUpperCase()).getEmoji(), "was eaten");
    }

    public void die() {
        if (!isAlive) return;
        boolean removed = currentLocation.removeAnimal(this);
        if (removed) {
            this.isAlive = false;
        }
    }

    @Override
    public void reproduce(Cell cell, EventTracker tracker) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (Animal animal : cell.getAnimals()) {
            if (animal.getClass() == this.getClass() && animal != this) {
                if (getAnimalCount(cell, this.getClass()) < maxAmount) {
                    if (RandomUtil.getRandomInt(1, 100) <= reproductionChance) {
                        cell.addAnimal(this.getClass().getConstructor().newInstance());
                        tracker.increment(this.getClass().getSimpleName() + AnimalType.valueOf(this.getClass().getSimpleName().toUpperCase()).getEmoji(), "reproduced");
                    }
                }
                return;
            }
        }
    }
}
