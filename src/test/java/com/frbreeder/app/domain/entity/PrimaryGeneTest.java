package com.frbreeder.app.domain.entity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class PrimaryGeneTest {

    @Test
    void nameIsNotNull() {
        // Given
        String name = null;

        // When & Then
        assertThatThrownBy(() -> new PrimaryGene(name)).isInstanceOf(IllegalArgumentException.class);
    }

}
