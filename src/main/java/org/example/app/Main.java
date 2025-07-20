package org.example.app;

import org.example.engine.IslandEngine;
import org.example.map.IslandMap;

public class Main {
    private static final IslandMap map = new IslandMap(10, 10);

    public static void main(String[] args) {
        new IslandInitializer(map).populate();
        new IslandEngine(map).engineStart();
    }
}
