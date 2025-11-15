package com.frbreeder.app.domain.entity;

import com.frbreeder.app.domain.common.Rarity;

public interface Gene {

    String getName();

    Rarity getRarity();

    default boolean isSameGene(final Gene other) {
        return getName().equals(other.getName());
    }

}
