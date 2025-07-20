package org.example.model.animals;

public enum AnimalType {
    WOLF(20, "\uD83D\uDC3A"),
    BOA(20, "\uD83D\uDC0D"),
    FOX(20, "\uD83E\uDD8A"),
    BEAR(20, "\uD83D\uDC3B"),
    EAGLE(20, "\uD83E\uDD85"),
    HORSE(20, "\uD83D\uDC0E"),
    DEER(20, "\uD83E\uDD8C"),
    RABBIT(20, "\uD83D\uDC07"),
    MOUSE(20, "\uD83D\uDC01"),
    GOAT(20, "\uD83D\uDC10"),
    SHEEP(20, "\uD83D\uDC11"),
    BOAR(20, "\uD83D\uDC17"),
    BUFFALO(20, "\uD83D\uDC03"),
    DUCK(20, "\uD83E\uDD86"),
    CATERPILLAR(20, "\uD83D\uDC1B");

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
