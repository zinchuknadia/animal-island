package org.example.map;

import org.example.model.Animal;
import org.example.model.Plant;

import java.util.HashSet;
import java.util.Set;

public class Cell {
    private Set<Animal> animals = new HashSet<>();
    private Set<Plant> plants = new HashSet<>();


    public Set<Animal> getAnimals() {
        return animals;
    }

    public Set<Plant> getPlants() {
        return plants;
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public void addPlant(Plant plant) {
        plants.add(plant);
    }
}
