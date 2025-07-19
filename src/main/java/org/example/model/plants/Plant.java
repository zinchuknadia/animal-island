package org.example.model.plants;

import org.example.map.Cell;
import org.example.map.IslandMap;
import org.example.model.Organism;
import org.example.statistics.StatisticTracker;
import org.example.util.RandomUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public abstract class Plant extends Organism {
    protected int lifespan;

    public Plant(int id, double weight, int maxAmount) {
        super(id, weight, maxAmount);
        this.lifespan = 10;
    }

    public int getLifespan() {
        return lifespan;
    }

    @Override
    public void reproduce(Cell cell, StatisticTracker tracker) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if(!isAlive) return;
        if(getPlantCount(cell, this.getClass()) < maxAmount) {
            if (RandomUtil.getRandomBoolean(1, 2)) {
                cell.addPlant(this.getClass().getConstructor().newInstance());
                tracker.increment(this.getClass().getSimpleName() + PlantType.valueOf(this.getClass().getSimpleName().toUpperCase()).getEmoji(), "reproduced");
            }
        }
    }

    public void spread(IslandMap map, Cell cell, StatisticTracker tracker) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if(!isAlive) return;
        if(RandomUtil.getRandomBoolean(1, 2)) {
            Cell randomCell = RandomUtil.getRandomCell(map, cell, 1);
            if(getPlantCount(randomCell, this.getClass()) < maxAmount) {
                randomCell.addPlant(this.getClass().getConstructor().newInstance());
                tracker.increment(this.getClass().getSimpleName() + PlantType.valueOf(this.getClass().getSimpleName().toUpperCase()).getEmoji(), "spread");
            }
        }
    }

    public int getPlantCount(Cell cell, Class<? extends Plant> clazz) {
        return (int) new ArrayList<>(cell.getPlants()).stream()
                .filter(a -> a.getClass().equals(clazz))
                .count();
    }

    @Override
    public void onEaten(StatisticTracker tracker) {
        if(!isAlive) return;
        boolean removed = currentLocation.removePlant(this);
        if(removed) {
            isAlive = false;
            tracker.increment(this.getClass().getSimpleName() + PlantType.valueOf(this.getClass().getSimpleName().toUpperCase()).getEmoji(), "was eaten");
        }
    }

    public void age(StatisticTracker tracker) {
        lifespan--;
        if(lifespan <= 0) {
            if(!isAlive) return;
            boolean removed = currentLocation.removePlant(this);
            if(removed) {
                isAlive = false;
                tracker.increment(this.getClass().getSimpleName() + PlantType.valueOf(this.getClass().getSimpleName().toUpperCase()).getEmoji(), "wilted and died");
            }
        }
    }
}
