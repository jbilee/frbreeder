package com.frbreeder.app.domain;

import com.frbreeder.app.domain.entity.Breed;
import com.frbreeder.app.domain.entity.Color;
import com.frbreeder.app.domain.entity.Dragon;
import com.frbreeder.app.domain.entity.Gene;
import com.frbreeder.app.infrastructure.BreedRepository;
import com.frbreeder.app.infrastructure.DragonRepository;
import com.frbreeder.app.infrastructure.GeneRepository;
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

    public List<Dragon> getDragons() {
        return dragonRepository.findAll();
    }

    public Dragon addDragon(final String name, final String scryUrl) {
        Dragon dragon = parseScryUrl(name, scryUrl);
        return dragonRepository.save(dragon);
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
                getColorFromScry(queries.get("body")),
                getColorFromScry(queries.get("wings")),
                getColorFromScry(queries.get("tert")),
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

    private Color getColorFromScry(final int colorId) {
        FrColor frColor = FrColor.findByFrId(colorId);
        return new Color(frColor.getFrId(), frColor.getName(), frColor.getGradientOrder());
    }

}
