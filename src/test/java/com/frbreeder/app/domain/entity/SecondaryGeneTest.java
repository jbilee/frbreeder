package com.frbreeder.app.domain.entity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.frbreeder.app.domain.common.Rarity;
import org.junit.jupiter.api.Test;

class SecondaryGeneTest {

    @Test
    void nameIsNotNull() {
        // Given
        String name = null;
        String type = "Modern";
        Rarity rarity = Rarity.COMMON;

        // When & Then
        assertThatThrownBy(() -> new SecondaryGene(name, type, rarity)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void typeIsNotNull() {
        // Given
        String name = "Chess";
        String type = null;
        Rarity rarity = Rarity.COMMON;

        // When & Then
        assertThatThrownBy(() -> new SecondaryGene(name, type, rarity)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rarityIsNotNull() {
        // Given
        String name = "Chess";
        String type = "Modern";
        Rarity rarity = null;

        // When & Then
        assertThatThrownBy(() -> new SecondaryGene(name, type, rarity)).isInstanceOf(IllegalArgumentException.class);
    }

}
