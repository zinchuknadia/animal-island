package org.example.model.animals;

import org.example.map.Cell;
import org.example.map.IslandMap;
import org.example.model.Organism;
import org.example.model.plants.Plant;
import org.example.model.plants.PlantType;
import org.example.statistics.EventTracker;
import org.example.util.ConsoleColor;
import org.example.util.RandomUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public abstract class Animal extends Organism {
    protected int speed;
    protected double foodNeeded;
    protected double fedLevel;
    protected int reproductionChance;
    protected final int[][] eatChanceMatrix = new int[15][16];

    public Animal(int id, double foodNeeded, int speed, int maxAmount, double weight) {
        super(id, weight, maxAmount);
        this.foodNeeded = foodNeeded;
        this.fedLevel = foodNeeded;
        this.speed = speed;
        this.reproductionChance = 25;
        initEatChanceMatrix();
    }

    public void initEatChanceMatrix() {
        for (int i = 0; i < eatChanceMatrix.length; i++) {
            for (int j = 0; j < eatChanceMatrix[i].length; j++) {
                if (i == j) {
                    eatChanceMatrix[i][j] = 0;
                }else {
                    eatChanceMatrix[i][j] = RandomUtil.getRandomInt(0, 100);
                }
            }
        }
    }

    public void move(IslandMap map, EventTracker tracker) {
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
                    tracker.increment(this.getClass().getSimpleName() + AnimalType.valueOf(this.getClass().getSimpleName().toUpperCase()).getEmoji(),
                            ConsoleColor.BLUE + "moved" + ConsoleColor.WHITE + " form (" + currentLocation.getX() + "," + currentLocation.getY() + ") to (" + newLocation.getX() + "," + newLocation.getY() + ")");
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

    public void eat(Cell cell, Organism prey, EventTracker tracker) {
        if (prey == null) return;

        int randomChance = RandomUtil.getRandomInt(1, 100);
        int eatChance = getEatChance(this.getId(), prey.getId());

        if (randomChance > 0 && randomChance <= eatChance) {
            getFed(cell, prey, tracker);
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

    public void getFed(Cell cell, Organism prey, EventTracker tracker) {
        fedLevel += prey.getWeight();
        if (fedLevel > foodNeeded) {
            fedLevel = foodNeeded;
        }
        if (prey instanceof Animal) {
            tracker.increment(this.getClass().getSimpleName() + AnimalType.valueOf(this.getClass().getSimpleName().toUpperCase()).getEmoji(),
                    ConsoleColor.RED + "ate " + ConsoleColor.WHITE + prey.getClass().getSimpleName() + AnimalType.valueOf(prey.getClass().getSimpleName().toUpperCase()).getEmoji() +
                            " in cell (" + cell.getX() + "," + cell.getY() + ")");
        } else if (prey instanceof Plant) {
            tracker.increment(this.getClass().getSimpleName() + AnimalType.valueOf(this.getClass().getSimpleName().toUpperCase()).getEmoji(),
                    ConsoleColor.RED + "ate " + ConsoleColor.WHITE + prey.getClass().getSimpleName() + PlantType.valueOf(prey.getClass().getSimpleName().toUpperCase()).getEmoji() +
                            " in cell (" + cell.getX() + "," + cell.getY() + ")");
        }
    }

    public void getHungry(Cell cell, EventTracker tracker) {
        fedLevel -= foodNeeded * 0.15;
        if (fedLevel < 0) {
            this.die();
            tracker.increment(this.getClass().getSimpleName() + AnimalType.valueOf(this.getClass().getSimpleName().toUpperCase()).getEmoji(),
                    ConsoleColor.YELLOW + "starved" + ConsoleColor.WHITE + " in cell (" + cell.getX() + "," + cell.getY() + ")");
        }
    }

    @Override
    public void onEaten(EventTracker tracker) {
        this.die();
//        tracker.increment(this.getClass().getSimpleName() + AnimalType.valueOf(this.getClass().getSimpleName().toUpperCase()).getEmoji(), ConsoleColor.RED + "was eaten" + ConsoleColor.WHITE);
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
                        tracker.increment(this.getClass().getSimpleName() + AnimalType.valueOf(this.getClass().getSimpleName().toUpperCase()).getEmoji(),
                                ConsoleColor.GREEN + "reproduced" + ConsoleColor.WHITE + " in cell (" + cell.getX() + "," + cell.getY() + ")");
                    }
                }
                return;
            }
        }
    }
}
