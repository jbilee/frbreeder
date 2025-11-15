package com.frbreeder.app.domain.entity;

import com.frbreeder.app.domain.common.Rarity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "breeds")
public class Breed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String type;

    @Enumerated(value = EnumType.STRING)
    private Rarity rarity;

    protected Breed() {
    }

    public Breed(final String name, final String type, final Rarity rarity) {
        validateName(name);
        validateType(type);
        validateRarity(rarity);

        this.name = name;
        this.type = type;
        this.rarity = rarity;
    }

    private void validateName(final String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name can't be blank.");
        }
    }

    private void validateType(final String type) {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Type can't be blank.");
        }
    }

    private void validateRarity(final Rarity rarity) {
        if (rarity == null) {
            throw new IllegalArgumentException("Rarity must be present.");
        }
    }

    public String getName() {
        return name;
    }

    public Rarity getRarity() {
        return rarity;
    }

}
