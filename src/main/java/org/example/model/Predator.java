package org.example.model;

public abstract class Predator extends Animal{
    @Override
    public void move() {
        System.out.println("Predator move");
    }

    @Override
    public void eat() {
        System.out.println("Predator eat");
    }
}
