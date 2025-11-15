package com.frbreeder.app.domain.entity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.frbreeder.app.domain.common.Rarity;
import org.junit.jupiter.api.Test;

class TertiaryGeneTest {

    @Test
    void nameIsNotNull() {
        // Given
        String name = null;
        String type = "Modern";
        Rarity rarity = Rarity.COMMON;

        // When & Then
        assertThatThrownBy(() -> new TertiaryGene(name, type, rarity)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void typeIsNotNull() {
        // Given
        String name = "Contour";
        String type = null;
        Rarity rarity = Rarity.COMMON;

        // When & Then
        assertThatThrownBy(() -> new TertiaryGene(name, type, rarity)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rarityIsNotNull() {
        // Given
        String name = "Contour";
        String type = "Modern";
        Rarity rarity = null;

        // When & Then
        assertThatThrownBy(() -> new TertiaryGene(name, type, rarity)).isInstanceOf(IllegalArgumentException.class);
    }

}
