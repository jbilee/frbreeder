package com.frbreeder.app.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tertiary_genes")
public class TertiaryGene {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    protected TertiaryGene() {
    }

    public TertiaryGene(final String name) {
        validateName(name);

        this.name = name;
    }

    private void validateName(final String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name can't be blank.");
        }
    }

    public String getName() {
        return name;
    }

}
