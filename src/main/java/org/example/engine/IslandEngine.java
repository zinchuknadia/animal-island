package org.example.engine;

import org.example.map.Cell;
import org.example.map.IslandMap;
import org.example.model.Animal;
import org.example.model.Herbivore;
import org.example.model.Plant;
import org.example.model.Predator;
import org.example.statistics.StatisticPrinter;
import org.example.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class IslandEngine {
    private final IslandMap map;

    public IslandEngine(IslandMap map) {
        this.map = map;
    }

    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    public void engineStart() {
        scheduledExecutorService.scheduleAtFixedRate(this::runCycle, 0, 1, TimeUnit.SECONDS);
    }

    public void runCycle() {
//        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        ExecutorService executor = Executors.newFixedThreadPool(1);
        for (Cell cell : map.getAllCells()) {
            executor.submit(() -> {
                processAnimals(cell);
                processPlants(cell);
            });
        }
        new StatisticPrinter().print(map);
        System.out.println();
    }

    public void processAnimals(Cell cell) {
        for (Animal animal : new ArrayList<>(cell.getAnimals())) {
            try {
                if (!animal.isAlive()) continue;
                animal.getHungry();
                if (!animal.isAlive()) continue;
                if(animal instanceof Herbivore){
                    Plant plant = getRandomPlant(cell);
                    if(plant != null){
                        animal.eat(plant);
                    } else {
                        animal.eat(getRandomPrey(cell, animal));
                    }
                }else if(animal instanceof Predator){
                    animal.eat(getRandomPrey(cell, animal));
                }
                animal.reproduce(cell);
                animal.move(map, animal.getClass());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void processPlants(Cell cell) {
        for (Plant plant : new ArrayList<>(cell.getPlants())) {
            plant.age();
            plant.reproduce(cell);
            plant.spread(map, cell);
        }
    }

    public Animal getRandomPrey(Cell cell, Animal predator) {
        List<Animal> animals = new ArrayList<>(cell.getAnimals());
        List<Animal> preys = new ArrayList<>();
        for (Animal animal : animals) {
            if (!animal.getClass().equals(predator.getClass())) {
                preys.add(animal);
            }
        }
        if (preys.size() <= 0) {
            return null;
        }
        int randomIndex = RandomUtil.getRandomInt(0, animals.size() - 1);
        return animals.get(randomIndex);
    }

    public Plant getRandomPlant(Cell cell){
        List<Plant> plants = new ArrayList<>(cell.getPlants());
        if (plants.size() <= 0) return null;
        int randomIndex = RandomUtil.getRandomInt(0, plants.size() - 1);
        return plants.get(randomIndex);
    }
}
