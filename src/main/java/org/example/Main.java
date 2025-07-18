package org.example;

import org.example.engine.IslandEngine;
import org.example.map.IslandMap;
import org.example.model.Plant;
import org.example.model.Rabbit;
import org.example.model.Wolf;
import org.example.util.RandomUtil;

public class Main {
    private static final IslandMap map = new IslandMap(2, 2);

    public static void main(String[] args) {
        initialize();
        new IslandEngine(map).engineStart();
    }

    public static void initialize() {
        RandomUtil.getRandomCell(map, map.getWidth(), map.getHeight()).addAnimal(new Rabbit());
        RandomUtil.getRandomCell(map, map.getWidth(), map.getHeight()).addAnimal(new Rabbit());
        RandomUtil.getRandomCell(map, map.getWidth(), map.getHeight()).addAnimal(new Rabbit());
        RandomUtil.getRandomCell(map, map.getWidth(), map.getHeight()).addAnimal(new Rabbit());

        RandomUtil.getRandomCell(map, map.getWidth(), map.getHeight()).addAnimal(new Wolf());
        RandomUtil.getRandomCell(map, map.getWidth(), map.getHeight()).addAnimal(new Wolf());

        RandomUtil.getRandomCell(map, map.getWidth(), map.getHeight()).addPlant(new Plant());
        RandomUtil.getRandomCell(map, map.getWidth(), map.getHeight()).addPlant(new Plant());
        RandomUtil.getRandomCell(map, map.getWidth(), map.getHeight()).addPlant(new Plant());
        RandomUtil.getRandomCell(map, map.getWidth(), map.getHeight()).addPlant(new Plant());
        RandomUtil.getRandomCell(map, map.getWidth(), map.getHeight()).addPlant(new Plant());
    }
}
