package com.frbreeder.app.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "goals")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gender;

    @ManyToOne(fetch = FetchType.LAZY)
    private Breed breed;

    @ManyToOne(fetch = FetchType.LAZY)
    private Gene primaryGene;

    @ManyToOne(fetch = FetchType.LAZY)
    private Gene secondaryGene;

    @ManyToOne(fetch = FetchType.LAZY)
    private Gene tertiaryGene;

    @ManyToOne(fetch = FetchType.LAZY)
    private Color primaryColor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Color secondaryColor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Color tertiaryColor;

    private String flight;

    protected Goal() {
    }

    public Goal(
            final Breed breed,
            final int gender,
            final Gene primaryGene,
            final Gene secondaryGene,
            final Gene tertiaryGene,
            final Color primaryColor,
            final Color secondaryColor,
            final Color tertiaryColor,
            final String flight
    ) {
        this.breed = breed;
        this.gender = gender == 0 ? "M" : "F";
        this.primaryGene = primaryGene;
        this.secondaryGene = secondaryGene;
        this.tertiaryGene = tertiaryGene;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.tertiaryColor = tertiaryColor;
        this.flight = flight;
    }

    public long getId() {
        return id;
    }

    public Breed getBreed() {
        return breed;
    }

    public String getGender() {
        return gender;
    }

    public Gene getPrimaryGene() {
        return primaryGene;
    }

    public Gene getSecondaryGene() {
        return secondaryGene;
    }

    public Gene getTertiaryGene() {
        return tertiaryGene;
    }

    public Color getPrimaryColor() {
        return primaryColor;
    }

    public Color getSecondaryColor() {
        return secondaryColor;
    }

    public Color getTertiaryColor() {
        return tertiaryColor;
    }

    public String getFlight() {
        return flight;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final Goal goal = (Goal) o;
        return Objects.equals(id, goal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
