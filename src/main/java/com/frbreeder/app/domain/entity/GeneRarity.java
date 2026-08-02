package com.frbreeder.app.domain.entity;

import com.frbreeder.app.domain.common.Rarity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "gene_rarities")
public class GeneRarity {

    @Id
    private String name;

    @Enumerated(value = EnumType.STRING)
    private Rarity rarity;

    protected GeneRarity() {
    }

    public String getName() {
        return name;
    }

    public Rarity getRarity() {
        return rarity;
    }

}
