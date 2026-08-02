package com.frbreeder.app.domain;

import com.frbreeder.app.common.error.DataConstraintException;
import com.frbreeder.app.common.error.NotFoundException;
import com.frbreeder.app.domain.common.FrColor;
import com.frbreeder.app.domain.entity.Breed;
import com.frbreeder.app.domain.entity.Dragon;
import com.frbreeder.app.domain.entity.Workspace;
import com.frbreeder.app.infrastructure.BreedRepository;
import com.frbreeder.app.infrastructure.BreedingPairRepository;
import com.frbreeder.app.infrastructure.DragonRepository;
import com.frbreeder.app.infrastructure.PrimaryGeneRepository;
import com.frbreeder.app.infrastructure.SecondaryGeneRepository;
import com.frbreeder.app.infrastructure.TertiaryGeneRepository;
import com.frbreeder.app.ui.dto.NewDragon;
import com.frbreeder.app.ui.dto.NewDragonRequest;
import com.frbreeder.app.ui.dto.RegisteredDragon;
import com.frbreeder.app.ui.dto.RosterDragon;
import com.frbreeder.app.ui.dto.RosterDragons;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DragonService {

    private static final Logger log = LoggerFactory.getLogger(DragonService.class);

    private final DragonRepository dragonRepository;
    private final BreedRepository breedRepository;
    private final PrimaryGeneRepository primaryGeneRepository;
    private final SecondaryGeneRepository secondaryGeneRepository;
    private final TertiaryGeneRepository tertiaryGeneRepository;
    private final BreedingPairRepository breedingPairRepository;

    public DragonService(final DragonRepository dragonRepository, final BreedRepository breedRepository,
                         final PrimaryGeneRepository primaryGeneRepository, final SecondaryGeneRepository secondaryGeneRepository,
                         final TertiaryGeneRepository tertiaryGeneRepository, final BreedingPairRepository breedingPairRepository
    ) {
        this.dragonRepository = dragonRepository;
        this.breedRepository = breedRepository;
        this.primaryGeneRepository = primaryGeneRepository;
        this.secondaryGeneRepository = secondaryGeneRepository;
        this.tertiaryGeneRepository = tertiaryGeneRepository;
        this.breedingPairRepository = breedingPairRepository;
    }

    @Transactional(readOnly = true)
    public RosterDragons getDragons(final Long workspaceId) {
        List<Dragon> dragons = dragonRepository.findAllByWorkspaceId(workspaceId);
        return new RosterDragons(dragons
                .stream()
                .map(this::getRosterDragon)
                .toList()
        );
    }

    private RosterDragon getRosterDragon(final Dragon dragon) {
        return new RosterDragon(
                dragon.getId(),
                dragon.getFrId(),
                dragon.getName(),
                dragon.getBreed().getName(),
                dragon.getGender(),
                dragon.getPrimaryGene(),
                dragon.getSecondaryGene(),
                dragon.getTertiaryGene(),
                getColorName(dragon.getPrimaryColorId()),
                getColorName(dragon.getSecondaryColorId()),
                getColorName(dragon.getTertiaryColorId())
        );
    }

    private String getColorName(final int id) {
        return FrColor.findByFrId(id).getName();
    }

    @Transactional
    public RosterDragons addDragons(final Workspace workspace, final NewDragonRequest request) {
        List<RosterDragon> rosterDragons = new ArrayList<>();
        for (NewDragon requestDragon : request.dragons()) {
            log.info("[SERVICE] workspaceId: {}, frId: {}, scryUrl: {}", workspace.getId(), requestDragon.frId(), requestDragon.scryUrl());

            Map<String, Integer> scryDetails = parseScryUrl(requestDragon.scryUrl());
            Dragon dragon = createDragonFromScryDetails(scryDetails, requestDragon.scryUrl(), requestDragon.frId(),
                    requestDragon.name(), workspace
            );
            Dragon newDragon = dragonRepository.save(dragon);
            rosterDragons.add(getRosterDragon(newDragon));
        }
        return new RosterDragons(rosterDragons);
    }

    private Map<String, Integer> parseScryUrl(final String url) {
        String queryParams = url.substring(url.indexOf("?") + 1);
        String[] queryPairs = queryParams.split("&");
        Map<String, Integer> scryDetails = new HashMap<>();

        for (String pair : queryPairs) {
            String[] keyValue = pair.split("=");
            int value = Integer.parseInt(keyValue[1]);
            scryDetails.put(keyValue[0], value);
        }

        return scryDetails;
    }

    private Dragon createDragonFromScryDetails(final Map<String, Integer> scryDetails, final String url, final Long frId,
                                               final String name, final Workspace workspace
    ) {
        return new Dragon(
                frId,
                url,
                name,
                getBreedFromScry(scryDetails.get("breed")),
                scryDetails.get("gender"),
                getPrimaryGeneFromScry(scryDetails.get("bodygene")),
                getSecondaryGeneFromScry(scryDetails.get("winggene")),
                getTertiaryGeneFromScry(scryDetails.get("tertgene")),
                scryDetails.get("body"),
                scryDetails.get("wings"),
                scryDetails.get("tert"),
                scryDetails.get("element").toString(),
                workspace
        );
    }

    private Breed getBreedFromScry(final int breedId) {
        return breedRepository.findById(breedId)
                .orElseThrow(() -> new NotFoundException("This breed is not in the database."));
    }

    private String getPrimaryGeneFromScry(final int geneId) {
        return primaryGeneRepository.findById(geneId)
                .orElseThrow(() -> new NotFoundException("This primary gene is not in the database."))
                .getName();
    }

    private String getSecondaryGeneFromScry(final int geneId) {
        return secondaryGeneRepository.findById(geneId)
                .orElseThrow(() -> new NotFoundException("This secondary gene is not in the database."))
                .getName();
    }

    private String getTertiaryGeneFromScry(final int geneId) {
        return tertiaryGeneRepository.findById(geneId)
                .orElseThrow(() -> new NotFoundException("This tertiary gene is not in the database."))
                .getName();
    }

    public RegisteredDragon getRegisteredDragon(final Long id, final Long workspaceId) {
        Dragon dragon = dragonRepository.findByIdAndWorkspaceId(id, workspaceId)
                .orElseThrow(() -> new NotFoundException(String.format("The dragon by id %d does not exist.", id)));
        return new RegisteredDragon(id, dragon.getScryUrl(), dragon.getName(), dragon.getFrId());
    }

    public RosterDragon getDragon(final Long id, final Long workspaceId) {
        Dragon dragon = dragonRepository.findByIdAndWorkspaceId(id, workspaceId)
                .orElseThrow(() -> new NotFoundException(String.format("The dragon by id %d does not exist.", id)));
        return getRosterDragon(dragon);
    }

    @Transactional
    public void deleteDragon(final Long id, final Long workspaceId) {
        Dragon dragon = dragonRepository.findByIdAndWorkspaceId(id, workspaceId)
                .orElseThrow(() -> new NotFoundException(String.format("The dragon by id %d does not exist.", id)));

        String gender = dragon.getGender();
        boolean pairExists;
        if (gender.equals("Male")) {
            pairExists = breedingPairRepository.existsByMaleIdAndWorkspaceId(dragon.getId(), workspaceId);
        } else {
            pairExists = breedingPairRepository.existsByFemaleIdAndWorkspaceId(dragon.getId(), workspaceId);
        }
        if (pairExists) {
            throw new DataConstraintException("This dragon is tied to a breeding pair.");
        }

        dragonRepository.deleteById(dragon.getId());
    }

}
