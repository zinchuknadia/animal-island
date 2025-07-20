package org.example.factory;

import org.example.model.animals.*;
import org.example.model.animals.herbivore.*;
import org.example.model.animals.predator.*;
import org.example.model.plants.Grass;
import org.example.model.plants.Plant;
import org.example.model.plants.PlantType;

import java.util.Map;
import static java.util.Map.entry;
import java.util.function.Supplier;

public class EntityFactory {
    private static final Map<AnimalType, Supplier<Animal>> animalSupplier = Map.ofEntries(
            entry(AnimalType.WOLF, Wolf::new),
            entry(AnimalType.BOA, Boa::new),
            entry(AnimalType.FOX, Fox::new),
            entry(AnimalType.BEAR, Bear::new),
            entry(AnimalType.EAGLE, Eagle::new),
            entry(AnimalType.HORSE, Horse::new),
            entry(AnimalType.DEER, Deer::new),
            entry(AnimalType.RABBIT, Rabbit::new),
            entry(AnimalType.MOUSE, Mouse::new),
            entry(AnimalType.GOAT, Goat::new),
            entry(AnimalType.SHEEP, Sheep::new),
            entry(AnimalType.BOAR, Boar::new),
            entry(AnimalType.BUFFALO, Buffalo::new),
            entry(AnimalType.DUCK, Duck::new),
            entry(AnimalType.CATERPILLAR, Caterpillar::new)
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
