package org.example.map;

import org.example.model.Animal;
import org.example.model.Plant;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    //TODO:synchronize lists to prevent maxAmount overleak
    private List<Animal> animals = new ArrayList<>();
    private List<Plant> plants = new ArrayList<>();

    private int x;
    private int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public synchronized void addAnimal(Animal animal) {
        animals.add(animal);
        animal.setCurrentLocation(Cell.this);
    }

    public synchronized void addPlant(Plant plant) {
        plants.add(plant);
    }
}
