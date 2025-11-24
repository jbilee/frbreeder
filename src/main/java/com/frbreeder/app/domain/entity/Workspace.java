package com.frbreeder.app.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "workspaces")
@SQLDelete(sql = "UPDATE workspaces SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Workspace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String password;
    private String secret;

    protected Workspace() {
    }

    public Workspace(final String name, final String password, final String secret) {
        validateName(name);
        validatePassword(password);
        validateSecret(secret);

        this.name = name;
        this.password = password;
        this.secret = secret;
    }

    private void validateName(final String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name can't be blank.");
        }
        if (name.length() < 4) {
            throw new IllegalArgumentException("Name should be 4 characters or longer.");
        }
        if (name.length() > 32) {
            throw new IllegalArgumentException("Name cannot be longer than 32 characters.");
        }
    }

    private void validatePassword(final String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password can't be blank.");
        }
        if (password.length() < 5) {
            throw new IllegalArgumentException("Password should be 5 characters or longer.");
        }
        if (password.length() > 64) {
            throw new IllegalArgumentException("Password cannot be longer than 64 characters.");
        }
    }

    private void validateSecret(final String secret) {
        if (secret == null || secret.isBlank()) {
            throw new IllegalArgumentException("Secret can't be blank.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSecret() {
        return secret;
    }

}
