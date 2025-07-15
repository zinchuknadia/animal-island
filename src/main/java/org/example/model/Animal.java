package org.example.model;

import org.example.map.Cell;
import org.example.map.IslandMap;
import org.example.util.RandomUtil;

public abstract class Animal extends Organism {
    protected double weight;
    protected int maxAmount;
    protected int speed;
    protected double foodNeeded;

    public Animal(double foodNeeded, int speed, int maxAmount, double weight) {
        this.foodNeeded = foodNeeded;
        this.speed = speed;
        this.maxAmount = maxAmount;
        this.weight = weight;
    }

    Cell currentLocation;

    public void setCurrentLocation(Cell currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void move(IslandMap map) {
        Cell newLocation;
        int count = 0;
        do{
            newLocation = RandomUtil.getRandomCell(map, currentLocation, speed);
            count++;
        }while(newLocation.getAnimals().size() > maxAmount || count <= 5);

        if(newLocation.getAnimals().size() < maxAmount) {
            newLocation.addAnimal(this);
            currentLocation.getAnimals().remove(this);
            currentLocation = newLocation;
            System.out.println(this.getClass().getSimpleName() + " moved " + currentLocation);
        }
    }

    public abstract void eat();
}
