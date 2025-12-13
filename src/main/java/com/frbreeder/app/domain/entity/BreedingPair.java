package com.frbreeder.app.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "breeding_pairs")
public class BreedingPair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne
    private Dragon male;

    @OneToOne
    private Dragon female;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;

    protected BreedingPair() {
    }

    public BreedingPair(final String name, final Dragon male, final Dragon female, final Workspace workspace) {
        validateObject(male, female, workspace);
        validateGender(male, female);

        this.name = name == null ? "New Pair" : name;
        this.male = male;
        this.female = female;
        this.workspace = workspace;
    }

    private void validateObject(final Dragon male, final Dragon female, final Workspace workspace) {
        if (male == null || female == null) {
            throw new IllegalArgumentException("Dragon must be present.");
        }
        if (workspace == null) {
            throw new IllegalArgumentException("Workspace must be present.");
        }
    }

    private void validateGender(final Dragon male, final Dragon female) {
        if (male.getGender().equals(female.getGender())) {
            throw new IllegalArgumentException("Dragons cannot be of the same gender.");
        }
        if (!male.getGender().equals("Male")) {
            throw new IllegalArgumentException("Must pass a male dragon.");
        }
        if (!female.getGender().equals("Female")) {
            throw new IllegalArgumentException("Must pass a female dragon.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Dragon getMale() {
        return male;
    }

    public Dragon getFemale() {
        return female;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final BreedingPair that = (BreedingPair) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
