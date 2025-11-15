package com.frbreeder.app.domain.entity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class WorkspaceTest {

    @Test
    void nameIsNotNull() {
        // Given
        String name = null;
        String password = "abcdefg";
        String secret = "samplesecret";

        // When & Then
        assertThatThrownBy(() -> new Workspace(name, password, secret)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void nameIsTooShort() {
        // Given
        String name = "a";
        String password = "abcdefg";
        String secret = "samplesecret";

        // When & Then
        assertThatThrownBy(() -> new Workspace(name, password, secret)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void nameIsTooLong() {
        // Given
        String name = "abcdefghijklmnopqrstuvwxyz1234567890";
        String password = "abcdefg";
        String secret = "samplesecret";

        // When & Then
        assertThatThrownBy(() -> new Workspace(name, password, secret)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void passwordIsNotNull() {
        // Given
        String name = "my-workspace";
        String password = null;
        String secret = "samplesecret";

        // When & Then
        assertThatThrownBy(() -> new Workspace(name, password, secret)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void passwordIsTooShort() {
        // Given
        String name = "my-workspace";
        String password = "a";
        String secret = "samplesecret";

        // When & Then
        assertThatThrownBy(() -> new Workspace(name, password, secret)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void passwordIsTooLong() {
        // Given
        String name = "my-workspace";
        String password = "abcdefghijklmnopqrstuvwxyz1234567890abcdefghijklmnopqrstuvwxyz1234567890";
        String secret = "samplesecret";

        // When & Then
        assertThatThrownBy(() -> new Workspace(name, password, secret)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void secretIsNotNull() {
        // Given
        String name = "my-workspace";
        String password = "abcdefg";
        String secret = null;

        // When & Then
        assertThatThrownBy(() -> new Workspace(name, password, secret)).isInstanceOf(IllegalArgumentException.class);
    }

}
