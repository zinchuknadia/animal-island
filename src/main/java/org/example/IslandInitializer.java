package org.example;

import org.example.factory.EntityFactory;
import org.example.map.Cell;
import org.example.map.IslandMap;
import org.example.model.AnimalType;
import org.example.model.PlantType;
import org.example.util.RandomUtil;

public class IslandInitializer {
    private final IslandMap map;

    public IslandInitializer(IslandMap map) {
        this.map = map;
    }

    public void populate() {
        for (AnimalType type : AnimalType.values()) {
            spawnAnimals(type, type.getInitialPopulation());
        }
        for (PlantType type : PlantType.values()) {
            spawnPlants (type, type.getInitialAmount());
        }
    }

    public void spawnAnimals(AnimalType type, int count){
        for(int i = 0; i < count; i++){
            Cell cell = RandomUtil.getRandomCell(map, map.getWidth(), map.getHeight());
            cell.addAnimal(EntityFactory.createAnimal(type));
        }
    }

    public void spawnPlants(PlantType type, int count) {
        for(int i = 0; i < count; i++){
            Cell cell = RandomUtil.getRandomCell(map, map.getWidth(), map.getHeight());
            cell.addPlant(EntityFactory.createPlant(type));
        }
    }
}
