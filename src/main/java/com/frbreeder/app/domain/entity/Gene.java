package com.frbreeder.app.domain.entity;

import com.frbreeder.app.domain.Rarity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "genes")
public class Gene {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String type;
    private String part;

    @Enumerated(value = EnumType.STRING)
    private Rarity rarity;

    protected Gene() {
    }

    public Gene(final Integer id, final String name, final String type, final String part, final Rarity rarity) {
        validateId(id);
        validateName(name);
        validateType(type);
        validatePart(part);
        validateRarity(rarity);

        this.id = id;
        this.name = name;
        this.type = type;
        this.part = part;
        this.rarity = rarity;
    }

    private void validateId(final Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id must be present.");
        }
        if (id < 1) {
            throw new IllegalArgumentException("Id must be 1 and higher.");
        }
    }

    private void validateName(final String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name can't be blank.");
        }
    }

    private void validateType(final String type) {
        if (type.isBlank()) {
            throw new IllegalArgumentException("Type can't be blank.");
        }
    }

    private void validatePart(final String part) {
        if (part.isBlank()) {
            throw new IllegalArgumentException("Part can't be blank.");
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

    public String getType() {
        return type;
    }

    public String getPart() {
        return part;
    }

    public Rarity getRarity() {
        return rarity;
    }

}
