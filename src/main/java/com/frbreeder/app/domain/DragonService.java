package com.frbreeder.app.domain;

import com.frbreeder.app.common.error.NotFoundException;
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

    public DragonService(final DragonRepository dragonRepository, final BreedRepository breedRepository,
                         final PrimaryGeneRepository primaryGeneRepository, final SecondaryGeneRepository secondaryGeneRepository,
                         final TertiaryGeneRepository tertiaryGeneRepository) {
        this.dragonRepository = dragonRepository;
        this.breedRepository = breedRepository;
        this.primaryGeneRepository = primaryGeneRepository;
        this.secondaryGeneRepository = secondaryGeneRepository;
        this.tertiaryGeneRepository = tertiaryGeneRepository;
    }

    @Transactional(readOnly = true)
    public RosterDragons getDragons(final Long workspaceId) {
        List<Dragon> dragons = dragonRepository.findAllByWorkspaceId(workspaceId);
        return new RosterDragons(
                dragons.stream()
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
                dragon.getPrimaryGene().getName(),
                dragon.getSecondaryGene().getName(),
                dragon.getTertiaryGene().getName(),
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
            Dragon dragon = parseScryUrl(requestDragon.frId(), requestDragon.name(), requestDragon.scryUrl(), workspace);
            Dragon newDragon = dragonRepository.save(dragon);
            rosterDragons.add(getRosterDragon(newDragon));
        }
        return new RosterDragons(rosterDragons);
    }

    private Dragon parseScryUrl(final Long frId, final String name, final String url, final Workspace workspace) {
        log.info("[SERVICE] workspaceId: {}, frId: {}, scryUrl: {}", workspace.getId(), frId, url);

        String queryParams = url.substring(url.indexOf("?") + 1);
        String[] queryPairs = queryParams.split("&");
        Map<String, Integer> queries = new HashMap<>();

        for (final String pair : queryPairs) {
            String[] keyValue = pair.split("=");
            int value = Integer.parseInt(keyValue[1]);
            queries.put(keyValue[0], value);
        }

        return new Dragon(
                frId,
                url,
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
                .orElseThrow(() -> new NotFoundException("This breed is not in the database."));
    }

    private PrimaryGene getPrimaryGeneFromScry(final int geneId) {
        return primaryGeneRepository.findById(geneId)
                .orElseThrow(() -> new NotFoundException("This primary gene is not in the database."));
    }

    private SecondaryGene getSecondaryGeneFromScry(final int geneId) {
        return secondaryGeneRepository.findById(geneId)
                .orElseThrow(() -> new NotFoundException("This secondary gene is not in the database."));
    }

    private TertiaryGene getTertiaryGeneFromScry(final int geneId) {
        return tertiaryGeneRepository.findById(geneId)
                .orElseThrow(() -> new NotFoundException("This tertiary gene is not in the database."));
    }

    @Transactional(readOnly = true)
    public RegisteredDragon getDragon(final Long id, final Long workspaceId) {
        Dragon dragon = dragonRepository.findByIdAndWorkspaceId(id, workspaceId)
                .orElseThrow(() -> new NotFoundException("The dragon by that id does not exist."));
        return new RegisteredDragon(dragon.getScryUrl(), dragon.getName(), id);
    }

    @Transactional
    public void deleteDragon(final Long id, final Long workspaceId) {
        Dragon dragon = dragonRepository.findByIdAndWorkspaceId(id, workspaceId)
                .orElseThrow(() -> new NotFoundException("The dragon by that id does not exist."));
        dragonRepository.deleteById(dragon.getId());
    }

}
