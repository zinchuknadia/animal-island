package org.example.engine;

import org.example.map.Cell;
import org.example.map.IslandMap;
import org.example.model.Animal;
import org.example.model.Plant;
import org.example.statistics.StatisticPrinter;

import java.util.ArrayList;
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
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (Cell cell : map.getAllCells()) {
            executor.submit(() -> {
                for (Animal animal : new ArrayList<>(cell.getAnimals())) {
                    animal.eat();
                    try {
                        animal.reproduce(cell);
                    }catch(Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        animal.move(map);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (Plant plant : new ArrayList<>(cell.getPlants())) {
                    plant.reproduce(cell);
                    plant.spread(map, cell);
                }
            });
        }

        new StatisticPrinter().print(map);
        System.out.println();
    }
}
