package org.example.model.animals;

public enum AnimalType {
    WOLF(5, "\uD83D\uDC3A"),
    BOA(5, "\uD83D\uDC0D"),
    FOX(5, "\uD83E\uDD8A"),
    BEAR(5, "\uD83D\uDC3B"),
    EAGLE(5, "\uD83E\uDD85"),
    HORSE(4, "\uD83D\uDC0E"),
    DEER(3, "\uD83E\uDD8C"),
    RABBIT(4, "\uD83D\uDC07"),
    MOUSE(4, "\uD83D\uDC01"),
    GOAT(3, "\uD83D\uDC10"),
    SHEEP(3, "\uD83D\uDC11"),
    BOAR(3, "\uD83D\uDC17"),
    BUFFALO(4, "\uD83D\uDC03"),
    DUCK(4, "\uD83E\uDD86"),
    CATERPILLAR(4, "\uD83D\uDC1B");

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
