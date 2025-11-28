package com.frbreeder.app.domain.entity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class TertiaryGeneTest {

    @Test
    void nameIsNotNull() {
        // Given
        String name = null;

        // When & Then
        assertThatThrownBy(() -> new TertiaryGene(name)).isInstanceOf(IllegalArgumentException.class);
    }

}
