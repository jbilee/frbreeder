package com.frbreeder.app.domain;

import com.frbreeder.app.domain.entity.Breed;
import com.frbreeder.app.domain.entity.Dragon;
import com.frbreeder.app.domain.entity.Gene;
import com.frbreeder.app.infrastructure.BreedRepository;
import com.frbreeder.app.infrastructure.DragonRepository;
import com.frbreeder.app.infrastructure.GeneRepository;
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
    private final GeneRepository geneRepository;

    public DragonService(final DragonRepository dragonRepository, final BreedRepository breedRepository, final GeneRepository geneRepository) {
        this.dragonRepository = dragonRepository;
        this.breedRepository = breedRepository;
        this.geneRepository = geneRepository;
    }

    public RosterDragons getDragons() {
        List<Dragon> dragons = dragonRepository.findAll();
        return new RosterDragons(
                dragons.stream()
                        .map(this::getRosterDragon)
                        .toList()
        );
    }

    public RosterDragon addDragon(final String name, final String scryUrl) {
        Dragon dragon = parseScryUrl(name, scryUrl);
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

    private Dragon parseScryUrl(final String name, final String url) {
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
                getGeneFromScry(queries.get("bodygene")),
                getGeneFromScry(queries.get("winggene")),
                getGeneFromScry(queries.get("tertgene")),
                queries.get("body"),
                queries.get("wings"),
                queries.get("tert"),
                queries.get("element").toString()
        );
    }

    private Breed getBreedFromScry(final int breedId) {
        return breedRepository.findById(breedId)
                .orElseThrow();
    }

    private Gene getGeneFromScry(final int geneId) {
        return geneRepository.findById(geneId)
                .orElseThrow();
    }

}
