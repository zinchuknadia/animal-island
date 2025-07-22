package org.example.statistics;

import java.util.concurrent.ConcurrentLinkedQueue;

public class EventTracker {

    private final ConcurrentLinkedQueue<String> events = new ConcurrentLinkedQueue<>();

    public void increment(String species, String event) {
        String key = species + " " + event;
        events.add(key);

    }

    public void printStats() {
        System.out.println("=== Statistics for this cycle ===");
        events.forEach(System.out::println);
    }
}
