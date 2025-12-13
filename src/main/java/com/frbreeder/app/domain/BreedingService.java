package com.frbreeder.app.domain;

import com.frbreeder.app.common.error.InvalidRequestException;
import com.frbreeder.app.common.error.NotFoundException;
import com.frbreeder.app.domain.common.FrColor;
import com.frbreeder.app.domain.common.Rarity;
import com.frbreeder.app.domain.entity.Breed;
import com.frbreeder.app.domain.entity.BreedingPair;
import com.frbreeder.app.domain.entity.Dragon;
import com.frbreeder.app.domain.entity.GeneRarity;
import com.frbreeder.app.domain.entity.Workspace;
import com.frbreeder.app.infrastructure.BreedingPairRepository;
import com.frbreeder.app.infrastructure.DragonRepository;
import com.frbreeder.app.infrastructure.GeneRarityRepository;
import com.frbreeder.app.ui.dto.BreedingResult;
import com.frbreeder.app.ui.dto.DragonPair;
import com.frbreeder.app.ui.dto.GeneProbability;
import com.frbreeder.app.ui.dto.NewPairRequest;
import com.frbreeder.app.ui.dto.PossibleColors;
import com.frbreeder.app.ui.dto.RosterDragon;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BreedingService {

    private final DragonRepository dragonRepository;
    private final BreedingPairRepository breedingPairRepository;
    private final GeneRarityRepository geneRarityRepository;

    public BreedingService(final DragonRepository dragonRepository, final BreedingPairRepository breedingPairRepository, final GeneRarityRepository geneRarityRepository) {
        this.dragonRepository = dragonRepository;
        this.breedingPairRepository = breedingPairRepository;
        this.geneRarityRepository = geneRarityRepository;
    }

    public BreedingResult getResult(final long parentAId, final long parentBId) {
        if (parentAId == parentBId) {
            throw new InvalidRequestException("Dragons can't be identical.");
        }

        Dragon parentA = dragonRepository.findById(parentAId)
                .orElseThrow(() -> new NotFoundException(String.format("The dragon by id %d does not exist.", parentAId)));
        Dragon parentB = dragonRepository.findById(parentBId)
                .orElseThrow(() -> new NotFoundException(String.format("The dragon by id %d does not exist.", parentBId)));

        if (parentA.getGender().equals(parentB.getGender())) {
            throw new InvalidRequestException("Dragons can't be of the same gender.");
        }

        String breedAType = parentA.getBreed().getType();
        String breedBType = parentB.getBreed().getType();
        if (!breedAType.equals(breedBType)) {
            throw new InvalidRequestException("Modern and ancient dragons cannot breed with each other.");
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
        String aName = a.getName();
        String bName = b.getName();

        if (aName.equals(bName)) {
            return List.of(new GeneProbability(a.getName(), 100));
        }

        Rarity rarityA = a.getRarity();
        Rarity rarityB = b.getRarity();
        int weightA = rarityA.findWeight(rarityB);
        int weightB = rarityB.findWeight(rarityA);
        return List.of(new GeneProbability(a.getName(), weightA), new GeneProbability(b.getName(), weightB));
    }

    private List<GeneProbability> calculateGeneProbability(final String geneNameA, final String geneNameB) {
        if (geneNameA.equals(geneNameB)) {
            return List.of(new GeneProbability(geneNameA, 100));
        }

        GeneRarity geneRarityA = geneRarityRepository.findByName(geneNameA)
                .orElseThrow();
        GeneRarity geneRarityB = geneRarityRepository.findByName(geneNameB)
                .orElseThrow();

        Rarity rarityA = geneRarityA.getRarity();
        Rarity rarityB = geneRarityB.getRarity();
        int weightA = rarityA.findWeight(rarityB);
        int weightB = rarityB.findWeight(rarityA);
        return List.of(new GeneProbability(geneNameA, weightA), new GeneProbability(geneNameB, weightB));
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
            throw new RuntimeException("Could not find the appropriate color range.");
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

    public List<DragonPair> getDragonPairs(final long workspaceId) {
        return breedingPairRepository.findAllByWorkspaceId(workspaceId).stream()
                .filter(pair -> pair.getMale() != null && pair.getFemale() != null)
                .map(pair -> new DragonPair(
                                pair.getId(),
                                pair.getName(),
                                getRosterDragon(pair.getMale()),
                                getRosterDragon(pair.getFemale())
                        )
                )
                .toList();
    }

    private RosterDragon getRosterDragon(final Dragon dragon) {
        if (dragon == null) {
            throw new NotFoundException("The dragon from this pair does not exist.");
        }
        return new RosterDragon(
                dragon.getId(),
                dragon.getFrId(),
                dragon.getName(),
                dragon.getBreed().getName(),
                dragon.getGender(),
                dragon.getPrimaryGene(),
                dragon.getSecondaryGene(),
                dragon.getTertiaryGene(),
                FrColor.findByFrId(dragon.getPrimaryColorId()).getName(),
                FrColor.findByFrId(dragon.getSecondaryColorId()).getName(),
                FrColor.findByFrId(dragon.getTertiaryColorId()).getName()
        );
    }

    @Transactional
    public void addBreedingPair(final NewPairRequest request, final Workspace workspace) {
        Dragon maleDragon = dragonRepository.findById(request.maleId())
                .orElseThrow(() -> new NotFoundException(String.format("The dragon by id %d does not exist.", request.maleId())));
        Dragon femaleDragon = dragonRepository.findById(request.femaleId())
                .orElseThrow(() -> new NotFoundException(String.format("The dragon by id %d does not exist.", request.femaleId())));

        String maleBreedType = maleDragon.getBreed().getType();
        String femaleBreedType = femaleDragon.getBreed().getType();
        if (!maleBreedType.equals(femaleBreedType)) {
            throw new InvalidRequestException("Modern and ancient dragons cannot breed with each other.");
        }

        BreedingPair breedingPair = new BreedingPair(request.name(), maleDragon, femaleDragon, workspace);

        breedingPairRepository.save(breedingPair);
    }

    public List<DragonPair> searchPairByGender(final String gender, final Long id, final Long workspaceId) {
        if (gender.equalsIgnoreCase("male")) {
            return breedingPairRepository.findByMaleIdAndWorkspaceId(id, workspaceId).stream()
                    .filter(pair -> pair.getMale() != null && pair.getFemale() != null)
                    .map(pair -> new DragonPair(
                                    pair.getId(),
                                    pair.getName(),
                                    getRosterDragon(pair.getMale()),
                                    getRosterDragon(pair.getFemale())
                            )
                    )
                    .toList();
        }
        if (gender.equalsIgnoreCase("female")) {
            return breedingPairRepository.findByFemaleIdAndWorkspaceId(id, workspaceId).stream()
                    .filter(pair -> pair.getMale() != null && pair.getFemale() != null)
                    .map(pair -> new DragonPair(
                                    pair.getId(),
                                    pair.getName(),
                                    getRosterDragon(pair.getMale()),
                                    getRosterDragon(pair.getFemale())
                            )
                    )
                    .toList();
        }
        throw new InvalidRequestException("Invalid search parameters.");
    }

    @Transactional
    public void deletePair(final Long id, final Long workspaceId) {
        breedingPairRepository.deleteByIdAndWorkspaceId(id, workspaceId);
    }

}
