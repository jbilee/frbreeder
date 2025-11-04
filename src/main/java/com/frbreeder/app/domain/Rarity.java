package com.frbreeder.app.domain;

import java.util.Map;

public enum Rarity {

    PLENTIFUL("Plentiful",
            Map.of(
                    "Plentiful", 50,
                    "Common", 70,
                    "Uncommon", 85,
                    "Limited", 97,
                    "Rare", 99
            )
    ),
    COMMON("Common",
            Map.of(
                    "Plentiful", 30,
                    "Common", 50,
                    "Uncommon", 75,
                    "Limited", 90,
                    "Rare", 99
            )
    ),
    UNCOMMON("Uncommon",
            Map.of(
                    "Plentiful", 15,
                    "Common", 25,
                    "Uncommon", 50,
                    "Limited", 85,
                    "Rare", 98
            )
    ),
    LIMITED("Limited",
            Map.of(
                    "Plentiful", 3,
                    "Common", 10,
                    "Uncommon", 15,
                    "Limited", 50,
                    "Rare", 97
            )
    ),
    RARE("Rare",
            Map.of(
                    "Plentiful", 1,
                    "Common", 1,
                    "Uncommon", 2,
                    "Limited", 3,
                    "Rare", 50
            )
    );

    private final String name;
    private final Map<String, Integer> weights;

    Rarity(final String name, final Map<String, Integer> weights) {
        this.name = name;
        this.weights = weights;
    }

    public int findWeight(final Rarity other) {
        return weights.get(other.name);
    }

    public String getName() {
        return name;
    }

}
