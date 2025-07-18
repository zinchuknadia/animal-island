package org.example.model;

import org.example.map.Cell;
import org.example.map.IslandMap;
import org.example.statistics.StatisticTracker;
import org.example.util.RandomUtil;

import java.util.ArrayList;

public class Plant extends Organism {
    private int lifespan;

    public Plant() {
        super(15, 1, 200);
        this.lifespan = 10;
    }

    public int getLifespan() {
        return lifespan;
    }

    @Override
    public void reproduce(Cell cell, StatisticTracker tracker) {
        if(!isAlive) return;
        if(new ArrayList<>(cell.getPlants()).size() < maxAmount) {
            if (RandomUtil.getRandomBoolean(1, 2)) {
                cell.addPlant(new Plant());
                tracker.increment(this.getClass().getSimpleName(), "reproduced");
            }
//        System.out.println("Plant reproduced" + this);
        }
    }

    public void spread(IslandMap map, Cell cell, StatisticTracker tracker) {
        if(!isAlive) return;
        if(RandomUtil.getRandomBoolean(1, 2)) {
            Cell randomCell = RandomUtil.getRandomCell(map, cell, 1);
            if(new ArrayList<>(randomCell.getPlants()).size() < maxAmount) {
                randomCell.addPlant(new Plant());
                tracker.increment(this.getClass().getSimpleName(), "spread");
            }
        }
//        System.out.println("Plant spread");
    }

    @Override
    public void onEaten(StatisticTracker tracker) {
        if(!isAlive) return;
        boolean removed = currentLocation.removePlant(this);
        if(removed) {
            isAlive = false;
            tracker.increment(this.getClass().getSimpleName(), "was eaten");
        }
    }

    public void age(StatisticTracker tracker) {
        lifespan--;
        if(lifespan <= 0) {
            if(!isAlive) return;
            boolean removed = currentLocation.removePlant(this);
            if(removed) {
                isAlive = false;
                tracker.increment(this.getClass().getSimpleName(), "wilted and died");
            }
//            System.out.println(this + " wilted and died.");
        }
    }
}
