package org.example.map;

import org.example.model.animals.Animal;
import org.example.model.plants.Plant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cell {
    private final List<Animal> animals = Collections.synchronizedList(new ArrayList<>());
    private final List<Plant> plants = Collections.synchronizedList(new ArrayList<>());

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

    public synchronized List<Animal> getAnimals() {
        return animals;
    }

    public synchronized List<Plant> getPlants() {
        return plants;
    }

    public synchronized void addAnimal(Animal animal) {
        animals.add(animal);
        if(animal.getCurrentLocation() == null) {
            animal.setCurrentLocation(this);
        }
    }

    public synchronized boolean removeAnimal(Animal animal) {
         return animals.remove(animal);
    }

    public synchronized void addPlant(Plant plant) {
        plants.add(plant);
        if(plant.getCurrentLocation() == null) {
            plant.setCurrentLocation(this);
        }
    }

    public boolean removePlant(Plant plant) {
        return plants.remove(plant);
    }
}
