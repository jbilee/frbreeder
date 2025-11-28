package com.frbreeder.app.domain.entity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class SecondaryGeneTest {

    @Test
    void nameIsNotNull() {
        // Given
        String name = null;

        // When & Then
        assertThatThrownBy(() -> new SecondaryGene(name)).isInstanceOf(IllegalArgumentException.class);
    }

}
