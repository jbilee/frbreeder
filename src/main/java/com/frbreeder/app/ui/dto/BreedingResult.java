package com.frbreeder.app.ui.dto;

import java.util.List;

public record BreedingResult(
        List<GeneProbability> breed,
        List<GeneProbability> primaryGene,
        PossibleColors primaryColors,
        List<GeneProbability> secondaryGene,
        PossibleColors secondaryColors,
        List<GeneProbability> tertiaryGene,
        PossibleColors tertiaryColors
) {
}
