package org.example.model;

public enum AnimalType {
    RABBIT(5, "\uD83D\uDC07"),
    WOLF(3, "\uD83D\uDC3A");

    private final int initialPopulation;
    private final String emoji;

    AnimalType(int initialPopulation, String emoji) {
        this.initialPopulation = initialPopulation;
        this.emoji = emoji;
    }
    public int getInitialPopulation() {
        return initialPopulation;
    }

    public String getEmoji() {
        return emoji;
    }
}
