package org.example.model;

import org.example.map.Cell;
import org.example.util.RandomUtil;

import java.lang.reflect.InvocationTargetException;

public abstract class Organism {
    public abstract void reproduce(Cell cell) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
}
