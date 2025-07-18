package org.example.model;

import org.example.map.Cell;
import org.example.map.IslandMap;
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
    public void reproduce(Cell cell) {
        if(!isAlive) return;
        if(new ArrayList<>(cell.getPlants()).size() < maxAmount) {
            if (RandomUtil.getRandomBoolean(1, 2)) {
                cell.addPlant(new Plant());
            }
//        System.out.println("Plant reproduced" + this);
        }
    }

    public void spread(IslandMap map, Cell cell) {
        if(!isAlive) return;
        if(RandomUtil.getRandomBoolean(1, 2)) {
            Cell randomCell = RandomUtil.getRandomCell(map, cell, 1);
            if(new ArrayList<>(randomCell.getPlants()).size() < maxAmount) {
                randomCell.addPlant(new Plant());
            }
        }
//        System.out.println("Plant spread");
    }

    @Override
    public void onEaten() {
        if(!isAlive) return;
        boolean removed = currentLocation.removePlant(this);
        if(removed) {
            isAlive = false;
        }
    }

    public void age() {
        lifespan--;
        if(lifespan <= 0) {
            if(!isAlive) return;
            System.out.println(this + " wilted and died.");
            boolean removed = currentLocation.removePlant(this);
            if(removed) {
                isAlive = false;
            }
        }
    }
}
