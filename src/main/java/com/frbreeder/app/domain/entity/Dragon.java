package com.frbreeder.app.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "dragons")
public class Dragon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String breed;
    private String gender;
    private String primaryGene;
    private String secondaryGene;
    private String tertiaryGene;
    private String primaryColor;
    private String secondaryColor;
    private String tertiaryColor;
    private String eyeType;
    private String flight;

    protected Dragon() {
    }

    public Dragon(
            final String name,
            final String breed,
            final String gender,
            final String primaryGene,
            final String secondaryGene,
            final String tertiaryGene,
            final String primaryColor,
            final String secondaryColor,
            final String tertiaryColor,
            final String eyeType,
            final String flight
    ) {
        this.name = name;
        this.breed = breed;
        this.gender = gender.equals("0") ? "M" : "F";
        this.primaryGene = primaryGene;
        this.secondaryGene = secondaryGene;
        this.tertiaryGene = tertiaryGene;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.tertiaryColor = tertiaryColor;
        this.eyeType = eyeType;
        this.flight = flight;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public String getGender() {
        return gender;
    }

    public String getPrimaryGene() {
        return primaryGene;
    }

    public String getSecondaryGene() {
        return secondaryGene;
    }

    public String getTertiaryGene() {
        return tertiaryGene;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public String getTertiaryColor() {
        return tertiaryColor;
    }

    public String getEyeType() {
        return eyeType;
    }

    public String getFlight() {
        return flight;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final Dragon dragon = (Dragon) o;
        return Objects.equals(id, dragon.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
