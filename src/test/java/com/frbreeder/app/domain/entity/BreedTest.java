package com.frbreeder.app.domain.entity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.frbreeder.app.domain.common.Rarity;
import org.junit.jupiter.api.Test;

class BreedTest {

    @Test
    void nameNotNullTest() {
        // Given
        String name = null;
        String type = "Modern";
        Rarity rarity = Rarity.COMMON;

        // When & Then
        assertThatThrownBy(() -> new Breed(name, type, rarity)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void typeNotNullTest() {
        // Given
        String name = "Kesler";
        String type = null;
        Rarity rarity = Rarity.COMMON;

        // When & Then
        assertThatThrownBy(() -> new Breed(name, type, rarity)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rarityNotNullTest() {
        // Given
        String name = "Kesler";
        String type = "Modern";
        Rarity rarity = null;

        // When & Then
        assertThatThrownBy(() -> new Breed(name, type, rarity)).isInstanceOf(IllegalArgumentException.class);
    }

}
