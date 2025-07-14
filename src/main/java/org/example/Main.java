package org.example;

import org.example.engine.IslandEngine;
import org.example.map.IslandMap;
import org.example.model.Plant;
import org.example.model.Rabbit;
import org.example.model.Wolf;

public class Main {
    static int width = 100;
    static int height = 20;
    private static final IslandMap map = new IslandMap(width, height);

    public static void main(String[] args) {
        initialize();
        new IslandEngine(map).engineStart();
    }

    public static void initialize() {
        RandomUtil.getRandomCell(map, width, height).addAnimal(new Wolf());
        RandomUtil.getRandomCell(map, width, height).addAnimal(new Rabbit());
        RandomUtil.getRandomCell(map, width, height).addPlant(new Plant());

        RandomUtil.getRandomCell(map, width, height).addAnimal(new Wolf());
        RandomUtil.getRandomCell(map, width, height).addAnimal(new Rabbit());
        RandomUtil.getRandomCell(map, width, height).addPlant(new Plant());
    }
}
