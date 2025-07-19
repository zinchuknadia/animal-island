package org.example.model;

public enum PlantType {
    GRASS(6, "\uD83C\uDF31");

    private final int initialAmount;
    private final String emoji;

    PlantType(int initialAmount, String emoji) {
        this.initialAmount = initialAmount;
        this.emoji = emoji;
    }

    public int getInitialAmount() {
        return initialAmount;
    }

    public String getEmoji() {
        return emoji;
    }
}
