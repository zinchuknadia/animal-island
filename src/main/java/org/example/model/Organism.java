package org.example.model;

import org.example.map.Cell;
import org.example.util.RandomUtil;

import java.lang.reflect.InvocationTargetException;

public abstract class Organism {
    protected double weight;
    protected int maxAmount;
    protected boolean isAlive;
    protected final int id;

    Cell currentLocation;

    public Organism(int id, double weight, int maxAmount) {
        this.id = id;
        this.weight = weight;
        this.maxAmount = maxAmount;
        this.isAlive = true;
    }

    public void setCurrentLocation(Cell currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Cell getCurrentLocation() {
        return currentLocation;
    }

    public int getId() {
        return id;
    }

    public double getWeight() {
        return weight;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public abstract void onEaten();

    public abstract void reproduce(Cell cell) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
}
