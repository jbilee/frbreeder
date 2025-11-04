package com.frbreeder.app.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "colors")
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private Integer gradientOrder;

    protected Color() {
    }

    public Color(final Integer id, final String name, final Integer gradientOrder) {
        validateId(id);
        validateName(name);
        validateGradientOrder(gradientOrder);

        this.id = id;
        this.name = name;
        this.gradientOrder = gradientOrder;
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

    private void validateGradientOrder(final Integer gradientOrder) {
        if (gradientOrder == null) {
            throw new IllegalArgumentException("Gradient order must be present.");
        }
        if (gradientOrder < 1) {
            throw new IllegalArgumentException("Gradient order must be 1 and higher.");
        }
    }

    public Integer getId() {
        return id;
    }

    public Integer getGradientOrder() {
        return gradientOrder;
    }

}
