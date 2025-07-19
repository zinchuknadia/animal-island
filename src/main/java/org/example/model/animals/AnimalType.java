package org.example.model.animals;

public enum AnimalType {
    WOLF(3, "\uD83D\uDC3A"),
    BOA(5, "\uD83D\uDC0D"),
    FOX(5, "\uD83E\uDD8A"),
    BEAR(5, "\uD83D\uDC3B"),
    EAGLE(5, "\uD83E\uDD85"),
    HORSE(5, "\uD83D\uDC0E"),
    DEER(5, "\uD83E\uDD8C"),
    RABBIT(5, "\uD83D\uDC07"),
    MOUSE(5, "\uD83D\uDC01"),
    SHEEP(3, "\uD83D\uDC11");

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
