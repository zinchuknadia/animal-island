package org.example.statistics;

import org.example.map.IslandMap;
import org.example.map.Cell;
import org.example.model.Animal;
import org.example.model.Plant;

import java.util.HashMap;
import java.util.Map;

public class StatisticPrinter {

    public void print(IslandMap map) {
        Map<String, Integer> counts = new HashMap<>();
        for(Cell cell : map.getAllCells()){
            for(Animal animal : cell.getAnimals()){
                String name = animal.getClass().getSimpleName();
                counts.put(name, counts.getOrDefault(name, 0) + 1);
            }
            for(Plant plant : cell.getPlants()){
                String name = plant.getClass().getSimpleName();
                counts.put(name, counts.getOrDefault(name, 0) + 1);
            }
        }

        System.out.println("Statistics");
        System.out.println(counts);
    }
}
