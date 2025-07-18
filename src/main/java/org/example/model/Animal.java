package org.example.model;

import org.example.map.Cell;
import org.example.map.IslandMap;
import org.example.statistics.StatisticTracker;
import org.example.util.RandomUtil;

import java.util.ArrayList;

public abstract class Animal extends Organism {
    protected int speed;
    protected double foodNeeded;
    protected double fedLevel;
    protected final int[][] matrix = {
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
    }

    public void move(IslandMap map, Class<? extends Animal> clazz) {
        Cell newLocation = findValidLocation(map, clazz);

        Cell first = currentLocation;
        Cell second = newLocation;
        if(System.identityHashCode(second) < System.identityHashCode(first)){
            Cell temp = first;
            first = second;
            second = temp;
        }
        synchronized(first){
            synchronized(second) {
                if (getAnimalCount(newLocation, clazz) < maxAmount) {
                    newLocation.addAnimal(this);
                    currentLocation.removeAnimal(this);
                    currentLocation = newLocation;
//                    System.out.println(this.getClass().getSimpleName() + " moved " + currentLocation);
                }
            }
        }
    }

    public Cell findValidLocation(IslandMap map, Class<? extends Animal> clazz) {
        Cell newLocation;
        int count = 0;
        do {
            newLocation = RandomUtil.getRandomCell(map, currentLocation, speed);
            count++;
        } while (getAnimalCount(newLocation, clazz) >= maxAmount && count < 5);
        return newLocation;
    }

    public int getAnimalCount(Cell cell, Class<? extends Animal> clazz) {
        return (int) new ArrayList<>(cell.getAnimals()).stream()
                .filter(a -> a.getClass().equals(clazz))
                .count();
    }

    public void eat(Organism prey, StatisticTracker tracker) {
        if(prey == null) return;

        int randomChance = RandomUtil.getRandomInt(1, 100);
        int eatChance = getEatChance(this.getId(), prey.getId());

        if (randomChance > 0 && randomChance <= eatChance) {
//            System.out.println(this.getClass().getSimpleName() + " eating " + prey.getClass().getSimpleName());
            getFed(prey);
            prey.onEaten(tracker);
        }
    }

    public int getEatChance(int predator, int prey) {
        return matrix[predator][prey];
    }

    public void getFed(Organism prey){
        fedLevel += prey.getWeight();
        if (fedLevel > foodNeeded) {
            fedLevel = foodNeeded;
        }
    }

    public void getHungry(StatisticTracker tracker){
        fedLevel -= foodNeeded * 0.15;
        if (fedLevel < 0) {
//            System.out.println(this.getClass().getSimpleName() + " died from starvation");
            this.die();
            tracker.increment(this.getClass().getSimpleName(), "starved");
        }
    }

    @Override
    public void onEaten(StatisticTracker tracker){
        die();
        tracker.increment(this.getClass().getSimpleName(), "was eaten");
    }

    public void die() {
        if(!isAlive) return;
        boolean removed = currentLocation.removeAnimal(this);
        if(removed) {
            this.isAlive = false;
//            System.out.println(this + " died. Removed from cell? true");
        }else{
//            System.out.println(this + " tried to die, but was not in cell!");
        }
    }
}
