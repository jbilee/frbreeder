package com.frbreeder.app.domain;

import com.frbreeder.app.infrastructure.DragonRepository;
import java.util.List;
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

}
