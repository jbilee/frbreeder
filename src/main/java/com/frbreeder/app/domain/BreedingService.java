package com.frbreeder.app.domain;

import com.frbreeder.app.domain.entity.Breed;
import com.frbreeder.app.domain.entity.Dragon;
import com.frbreeder.app.domain.entity.Gene;
import com.frbreeder.app.infrastructure.DragonRepository;
import com.frbreeder.app.ui.dto.BreedingResult;
import com.frbreeder.app.ui.dto.GeneProbability;
import com.frbreeder.app.ui.dto.PossibleColors;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BreedingService {

    private final DragonRepository dragonRepository;

    public BreedingService(final DragonRepository dragonRepository) {
        this.dragonRepository = dragonRepository;
    }

    public BreedingResult getResult(final long parentAId, final long parentBId) {
        if (parentAId == parentBId) {
            throw new IllegalArgumentException("Can't be identical.");
        }

        Dragon parentA = dragonRepository.findById(parentAId).orElseThrow();
        Dragon parentB = dragonRepository.findById(parentBId).orElseThrow();

        if (parentA.getGender().equals(parentB.getGender())) {
            throw new IllegalArgumentException("Can't be of the same gender.");
        }

        // TODO: Validate lineage

        return new BreedingResult(
                calculateBreedProbability(parentA.getBreed(), parentB.getBreed()),
                calculateGeneProbability(parentA.getPrimaryGene(), parentB.getPrimaryGene()),
                getColorRange(parentA.getPrimaryColorId(), parentB.getPrimaryColorId()),
                calculateGeneProbability(parentA.getSecondaryGene(), parentB.getSecondaryGene()),
                getColorRange(parentA.getSecondaryColorId(), parentB.getSecondaryColorId()),
                calculateGeneProbability(parentA.getTertiaryGene(), parentB.getTertiaryGene()),
                getColorRange(parentA.getTertiaryColorId(), parentB.getTertiaryColorId())
        );
    }

    private List<GeneProbability> calculateBreedProbability(final Breed a, final Breed b) {
        Rarity rarityA = a.getRarity();
        Rarity rarityB = b.getRarity();
        int weightA = rarityA.findWeight(rarityB);
        int weightB = rarityB.findWeight(rarityA);
        return List.of(new GeneProbability(a.getName(), weightA), new GeneProbability(b.getName(), weightB));
    }

    private List<GeneProbability> calculateGeneProbability(final Gene a, final Gene b) {
        Rarity rarityA = a.getRarity();
        Rarity rarityB = b.getRarity();
        int weightA = rarityA.findWeight(rarityB);
        int weightB = rarityB.findWeight(rarityA);
        return List.of(new GeneProbability(a.getName(), weightA), new GeneProbability(b.getName(), weightB));
    }

    private PossibleColors getColorRange(final int aId, final int bId) {
        if (aId == bId) {
            return new PossibleColors(List.of(FrColor.findByFrId(aId).getName()));
        }

        int aGradientOrder = FrColor.findByFrId(aId).getGradientOrder();
        int bGradientOrder = FrColor.findByFrId(bId).getGradientOrder();

        int distance = getDistance(aGradientOrder, bGradientOrder);
        int wrapDistance = getWrapDistance(distance);

        if (distance == wrapDistance) {
            // TODO: Replace with appropriate exception
            throw new RuntimeException("This isn't supposed to happen");
        }

        int start = Math.min(aGradientOrder, bGradientOrder);
        int end = Math.max(aGradientOrder, bGradientOrder);

        if (distance > wrapDistance) {
            return new PossibleColors(FrColor.getOuterColors(start, end));
        }
        return new PossibleColors(FrColor.getInnerColors(start, end));
    }

    private int getDistance(final int aGradientOrder, final int bGradientOrder) {
        return Math.abs(aGradientOrder - bGradientOrder);
    }

    private int getWrapDistance(final int distance) {
        return FrColor.TOTAL_COLORS - distance;
    }

}
