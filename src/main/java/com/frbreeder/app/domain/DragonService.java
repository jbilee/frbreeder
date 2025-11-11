package com.frbreeder.app.domain;

import com.frbreeder.app.domain.common.FrColor;
import com.frbreeder.app.domain.entity.Breed;
import com.frbreeder.app.domain.entity.Dragon;
import com.frbreeder.app.domain.entity.PrimaryGene;
import com.frbreeder.app.domain.entity.SecondaryGene;
import com.frbreeder.app.domain.entity.TertiaryGene;
import com.frbreeder.app.domain.entity.Workspace;
import com.frbreeder.app.infrastructure.BreedRepository;
import com.frbreeder.app.infrastructure.DragonRepository;
import com.frbreeder.app.infrastructure.PrimaryGeneRepository;
import com.frbreeder.app.infrastructure.SecondaryGeneRepository;
import com.frbreeder.app.infrastructure.TertiaryGeneRepository;
import com.frbreeder.app.ui.dto.RosterDragon;
import com.frbreeder.app.ui.dto.RosterDragons;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DragonService {

    private final DragonRepository dragonRepository;
    private final BreedRepository breedRepository;
    private final PrimaryGeneRepository primaryGeneRepository;
    private final SecondaryGeneRepository secondaryGeneRepository;
    private final TertiaryGeneRepository tertiaryGeneRepository;

    public DragonService(final DragonRepository dragonRepository, final BreedRepository breedRepository, final PrimaryGeneRepository primaryGeneRepository, final SecondaryGeneRepository secondaryGeneRepository, final TertiaryGeneRepository tertiaryGeneRepository) {
        this.dragonRepository = dragonRepository;
        this.breedRepository = breedRepository;
        this.primaryGeneRepository = primaryGeneRepository;
        this.secondaryGeneRepository = secondaryGeneRepository;
        this.tertiaryGeneRepository = tertiaryGeneRepository;
    }

    public RosterDragons getDragons() {
        List<Dragon> dragons = dragonRepository.findAll();
        return new RosterDragons(
                dragons.stream()
                        .map(this::getRosterDragon)
                        .toList()
        );
    }

    public RosterDragon addDragon(final Workspace workspace, final String name, final String scryUrl) {
        Dragon dragon = parseScryUrl(name, scryUrl, workspace);
        Dragon newDragon = dragonRepository.save(dragon);
        return getRosterDragon(newDragon);
    }

    private RosterDragon getRosterDragon(final Dragon dragon) {
        return new RosterDragon(
                dragon.getId(),
                dragon.getName(),
                dragon.getBreed().getName(),
                dragon.getGender(),
                dragon.getPrimaryGene().getName(),
                dragon.getSecondaryGene().getName(),
                dragon.getTertiaryGene().getName(),
                FrColor.findByFrId(dragon.getPrimaryColorId()).getName(),
                FrColor.findByFrId(dragon.getSecondaryColorId()).getName(),
                FrColor.findByFrId(dragon.getTertiaryColorId()).getName()
        );
    }

    private Dragon parseScryUrl(final String name, final String url, final Workspace workspace) {
        String queryParams = url.substring(url.indexOf("?") + 1);
        String[] queryPairs = queryParams.split("&");
        Map<String, Integer> queries = new HashMap<>();

        for (final String pair : queryPairs) {
            String[] keyValue = pair.split("=");
            int value = Integer.parseInt(keyValue[1]);
            queries.put(keyValue[0], value);
        }

        return new Dragon(
                name,
                getBreedFromScry(queries.get("breed")),
                queries.get("gender"),
                getPrimaryGeneFromScry(queries.get("bodygene")),
                getSecondaryGeneFromScry(queries.get("winggene")),
                getTertiaryGeneFromScry(queries.get("tertgene")),
                queries.get("body"),
                queries.get("wings"),
                queries.get("tert"),
                queries.get("element").toString(),
                workspace
        );
    }

    private Breed getBreedFromScry(final int breedId) {
        return breedRepository.findById(breedId)
                .orElseThrow();
    }

    private PrimaryGene getPrimaryGeneFromScry(final int geneId) {
        return primaryGeneRepository.findById(geneId)
                .orElseThrow();
    }

    private SecondaryGene getSecondaryGeneFromScry(final int geneId) {
        return secondaryGeneRepository.findById(geneId)
                .orElseThrow();
    }

    private TertiaryGene getTertiaryGeneFromScry(final int geneId) {
        return tertiaryGeneRepository.findById(geneId)
                .orElseThrow();
    }

}
