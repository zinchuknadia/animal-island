package org.example.factory;

import org.example.model.animals.Animal;
import org.example.model.animals.AnimalType;
import org.example.model.animals.Rabbit;
import org.example.model.animals.Wolf;
import org.example.model.plants.Grass;
import org.example.model.plants.Plant;
import org.example.model.plants.PlantType;

import java.util.Map;
import java.util.function.Supplier;

public class EntityFactory {
    private static final Map<AnimalType, Supplier<Animal>> animalSupplier = Map.of(
            AnimalType.RABBIT, Rabbit::new,
            AnimalType.WOLF, Wolf::new
    );

    private static final Map<PlantType, Supplier<Plant>> plantSupplier = Map.of(
            PlantType.GRASS, Grass::new
    );

    public static Animal createAnimal(AnimalType type) {
        Supplier<Animal> supplier = animalSupplier.get(type);
        if(supplier == null) {
            throw new IllegalArgumentException("Unknown animal type: " + type);
        }
        return supplier.get();
    }

    public static Plant createPlant(PlantType type) {
        Supplier<Plant> supplier = plantSupplier.get(type);
        if(supplier == null) {
            throw new IllegalArgumentException("Unknown plant type: " + type);
        }
        return supplier.get();
    }
}
