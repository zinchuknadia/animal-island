package org.example.engine;

import org.example.map.Cell;
import org.example.map.IslandMap;
import org.example.model.animals.Animal;
import org.example.model.plants.Plant;
import org.example.statistics.PopulationPrinter;
import org.example.statistics.EventTracker;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IslandEngine {
    private static final Logger logger = Logger.getLogger(IslandEngine.class.getName());

    private final IslandMap map;

    public IslandEngine(IslandMap map) {
        this.map = map;
    }

    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    PopulationPrinter populationPrinter = new PopulationPrinter();

    public void engineStart() {
        System.out.println("=== Initial statistics ===");
        populationPrinter.print(map);
        System.out.println();
        scheduledExecutorService.scheduleAtFixedRate(this::runCycle, 0, 1, TimeUnit.SECONDS);
    }

    public void runCycle() {
        EventTracker tracker = new EventTracker();
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (Cell cell : map.getAllCells()) {
            executor.submit(() -> {
                processAnimals(cell, tracker);
                processPlants(cell, tracker);
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tracker.printStats();
        populationPrinter.print(map);
        System.out.println();
    }

    public void processAnimals(Cell cell, EventTracker tracker) {
        for (Animal animal : cell.getAnimals()) {
            try {
                if (!animal.isAlive()) continue;
                animal.getHungry(tracker);

                if (!animal.isAlive()) continue;
                animal.findAndEat(cell, tracker);

                animal.reproduce(cell, tracker);

                animal.move(map);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error processing animal (" + animal.getClass().getSimpleName() + ") in cell: (" + cell.getX() + " ," + cell.getY() + ")", e);
            }
        }
    }

    public void processPlants(Cell cell, EventTracker tracker) {
        for (Plant plant : cell.getPlants()) {
            try {
                plant.age(tracker);
                plant.reproduce(cell, tracker);
                plant.spread(map, cell, tracker);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
