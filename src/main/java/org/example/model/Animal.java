package org.example.model;

public abstract class Animal extends Organism {
    protected double weight;
    protected int maxAmount;
    protected int speed;
    protected double foodNeeded;

    public abstract void move();

    public abstract void eat();
}
