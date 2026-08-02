package com.frbreeder.app.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "secondary_genes")
public class SecondaryGene {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    protected SecondaryGene() {
    }

    public SecondaryGene(final String name) {
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
