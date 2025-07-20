package org.example.statistics;

import org.example.map.IslandMap;
import org.example.map.Cell;
import org.example.model.animals.Animal;
import org.example.model.plants.Plant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PopulationPrinter {

    public void print(IslandMap map) {
        Map<String, Integer> counts = new HashMap<>();
        for (Cell cell : map.getAllCells()) {
            for (Animal animal : cell.getAnimals()) {
                if (!animal.isAlive()) continue;
                String name = animal.getClass().getSimpleName();
                counts.put(name, counts.getOrDefault(name, 0) + 1);
            }
            for (Plant plant : cell.getPlants()) {
                if (plant.getLifespan() <= 0 || !plant.isAlive()) continue;
                String name = plant.getClass().getSimpleName();
                counts.put(name, counts.getOrDefault(name, 0) + 1);
            }
        }

        System.out.println(counts);
    }
}
