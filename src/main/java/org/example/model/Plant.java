package org.example.model;

import org.example.map.Cell;
import org.example.map.IslandMap;
import org.example.util.RandomUtil;

public class Plant extends Organism {

    @Override
    public void reproduce(Cell cell) {
        if(RandomUtil.getRandomBoolean(1, 2)) {
            cell.addPlant(new Plant());
        }
//        System.out.println("Plant reproduced" + this);
    }

    public void spread(IslandMap map, Cell cell) {
        if(RandomUtil.getRandomBoolean(1, 3)) {
            RandomUtil.getRandomCell(map, cell, 1).addPlant(new Plant());
        }
//        System.out.println("Plant spread");
    }
}
