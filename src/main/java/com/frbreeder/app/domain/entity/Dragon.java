package com.frbreeder.app.domain.entity;

import com.frbreeder.app.common.error.NotFoundException;
import com.frbreeder.app.domain.common.FrColor;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "dragons")
@SQLDelete(sql = "UPDATE dragons SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Dragon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long frId;
    private String scryUrl;
    private String name;
    private String gender;
    private String primaryGene;
    private String secondaryGene;
    private String tertiaryGene;
    private Integer primaryColorId;
    private Integer secondaryColorId;
    private Integer tertiaryColorId;
    private String flight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "breed_id", nullable = false)
    private Breed breed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;

    protected Dragon() {
    }

    public Dragon(final Long frId, final String scryUrl, final String name, final Breed breed, final int gender,
                  final String primaryGene, final String secondaryGene, final String tertiaryGene, final int primaryColorId,
                  final int secondaryColorId, final int tertiaryColorId, final String flight, final Workspace workspace
    ) {
        validateGender(gender);
        validateColors(primaryColorId, secondaryColorId, tertiaryColorId);

        this.frId = frId;
        this.scryUrl = scryUrl;
        this.name = name;
        this.breed = breed;
        this.gender = gender == 0 ? "Male" : "Female";
        this.primaryGene = primaryGene;
        this.secondaryGene = secondaryGene;
        this.tertiaryGene = tertiaryGene;
        this.primaryColorId = primaryColorId;
        this.secondaryColorId = secondaryColorId;
        this.tertiaryColorId = tertiaryColorId;
        this.flight = flight;
        this.workspace = workspace;
    }

    private void validateGender(final int gender) {
        if (gender < 0 || gender > 1) {
            throw new IllegalArgumentException("Gender value must be either 0 or 1.");
        }
    }

    private void validateColors(final int... colorIds) {
        for (int colorId : colorIds) {
            if (!FrColor.hasId(colorId)) {
                throw new NotFoundException("This color is not in the database.");
            }
        }
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

    public String getPrimaryGene() {
        return primaryGene;
    }

    public String getSecondaryGene() {
        return secondaryGene;
    }

    public String getTertiaryGene() {
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
