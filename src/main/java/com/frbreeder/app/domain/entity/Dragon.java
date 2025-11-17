package com.frbreeder.app.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "dragons")
public class Dragon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long frId;
    private String scryUrl;
    private String name;
    private String gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "primary_gene_id", nullable = false)
    private PrimaryGene primaryGene;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "secondary_gene_id", nullable = false)
    private SecondaryGene secondaryGene;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tertiary_gene_id", nullable = false)
    private TertiaryGene tertiaryGene;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "breed_id", nullable = false)
    private Breed breed;

    private Integer primaryColorId;
    private Integer secondaryColorId;
    private Integer tertiaryColorId;
    private String flight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;

    protected Dragon() {
    }

    public Dragon(
            final Long frId, final String scryUrl,
            final String name,
            final Breed breed,
            final int gender,
            final PrimaryGene primaryGene,
            final SecondaryGene secondaryGene,
            final TertiaryGene tertiaryGene,
            final int primaryColorId,
            final int secondaryColorId,
            final int tertiaryColorId,
            final String flight,
            final Workspace workspace
    ) {
        this.frId = frId;
        this.scryUrl = scryUrl;
        this.name = name;
        this.breed = breed;
        this.gender = gender == 0 ? "M" : "F";
        this.primaryGene = primaryGene;
        this.secondaryGene = secondaryGene;
        this.tertiaryGene = tertiaryGene;
        this.primaryColorId = primaryColorId;
        this.secondaryColorId = secondaryColorId;
        this.tertiaryColorId = tertiaryColorId;
        this.flight = flight;
        this.workspace = workspace;
    }

    public long getId() {
        return id;
    }

    public Long getFrId() {
        return frId;
    }

    public String getScryUrl() {
        return scryUrl;
    }

    public String getName() {
        return name;
    }

    public Breed getBreed() {
        return breed;
    }

    public String getGender() {
        return gender;
    }

    public PrimaryGene getPrimaryGene() {
        return primaryGene;
    }

    public SecondaryGene getSecondaryGene() {
        return secondaryGene;
    }

    public TertiaryGene getTertiaryGene() {
        return tertiaryGene;
    }

    public int getPrimaryColorId() {
        return primaryColorId;
    }

    public int getSecondaryColorId() {
        return secondaryColorId;
    }

    public int getTertiaryColorId() {
        return tertiaryColorId;
    }

    public String getFlight() {
        return flight;
    }

    public Workspace getWorkspace() {
        return workspace;
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
