package org.example.statistics;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class StatisticTracker {
    private final Map<String, AtomicInteger> stats = new ConcurrentHashMap<>();

    public void increment(String species, String event) {
        String key = species + ":" + event;
        stats.computeIfAbsent(key, k -> new AtomicInteger()).incrementAndGet();
    }

    public void printStats() {
        System.out.println("=== Statistics for this cycle ===");
        stats.forEach((key, value) -> {
            String[] parts = key.split(":");
            String species = parts[0];
            String event = parts[1];
            System.out.printf("%s %s: %d%n", species, event, value.get());
        });
    }

    public void reset() {
        stats.clear();
    }
}
