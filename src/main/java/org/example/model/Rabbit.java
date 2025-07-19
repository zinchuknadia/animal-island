package org.example.model;

import org.example.map.Cell;
import org.example.statistics.StatisticTracker;
import org.example.util.RandomUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Rabbit extends Herbivore {
    public Rabbit() {
        super(7, 0.45, 2, 150, 2);
    }
}
