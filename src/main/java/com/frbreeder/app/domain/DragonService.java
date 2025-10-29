package com.frbreeder.app.domain;

import com.frbreeder.app.domain.entity.Color;
import com.frbreeder.app.domain.entity.Dragon;
import com.frbreeder.app.infrastructure.DragonRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DragonService {

    private final DragonRepository dragonRepository;

    public DragonService(final DragonRepository dragonRepository) {
        this.dragonRepository = dragonRepository;
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
        Map<String, String> queries = new HashMap<>();

        for (final String pair : queryPairs) {
            String[] keyValue = pair.split("=");
            queries.put(keyValue[0], keyValue[1]);
        }

        return new Dragon(
                name,
                queries.get("breed"),
                queries.get("gender"),
                queries.get("bodygene"),
                queries.get("winggene"),
                queries.get("tertgene"),
                getColorFromScry(queries.get("body")),
                getColorFromScry(queries.get("wings")),
                getColorFromScry(queries.get("tert")),
                queries.get("element")
        );
    }

    private Color getColorFromScry(final String colorId) {
        FrColor frColor = FrColor.findByFrId(Integer.parseInt(colorId));
        return new Color(frColor.getFrId(), frColor.getName(), frColor.getGradientOrder());
    }

}
