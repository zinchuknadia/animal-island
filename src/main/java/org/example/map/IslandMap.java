package org.example.map;

import java.util.Arrays;
import java.util.List;

public class IslandMap {
    private Cell[][] map;

    public IslandMap(int width, int height) {
        this.map = new Cell[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                this.map[x][y] = new Cell();
            }
        }
    }

    public List<Cell> getAllCells() {
        return Arrays.stream(map).flatMap(Arrays::stream).toList();
    }

    public Cell getCell(int x, int y) {
        return map[x][y];
    }
}
