package org.example.model;

public abstract class Herbivore extends Animal {
    @Override
    public void move() {
        System.out.println("Herbivore move");
    }

    @Override
    public void eat() {
        System.out.println("Herbivore eat");
    }
}
