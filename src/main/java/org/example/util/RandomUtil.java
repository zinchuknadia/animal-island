package org.example.util;

import org.example.map.Cell;
import org.example.map.IslandMap;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {
    public static Cell getRandomCell(IslandMap map, int maxWidth, int maxHeight) {
        int x1 = ThreadLocalRandom.current().nextInt(0, maxWidth);
        int y1 = ThreadLocalRandom.current().nextInt(0, maxHeight);
        return map.getCell(x1, y1);
    }

    public static Cell getRandomCell(IslandMap map, Cell cell, int speed) {
        int x = cell.getX() + ThreadLocalRandom.current().nextInt(-speed, speed + 1);
        int y = cell.getY() + ThreadLocalRandom.current().nextInt(-speed, speed + 1);

        if (x < 0){
            x = 0;
        }else if(x > map.getWidth() - 1){
            x = map.getWidth() - 1;
        }

        if (y < 0){
            y = 0;
        } else if(y > map.getHeight() - 1){
            y = map.getHeight() - 1;
        }

        return map.getCell(x, y);
    }

    public static boolean getRandomBoolean(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1) == min;
    }

    public static int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
