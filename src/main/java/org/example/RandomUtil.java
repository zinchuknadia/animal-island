package org.example;

import org.example.map.Cell;
import org.example.map.IslandMap;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {
    public static Cell getRandomCell(IslandMap map, int x, int y) {
        int x1 = ThreadLocalRandom.current().nextInt(0, x);
        int y1 = ThreadLocalRandom.current().nextInt(0, y);
        return map.getCell(x1, y1);
    }
}
