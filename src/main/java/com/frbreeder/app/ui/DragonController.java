package com.frbreeder.app.ui;

import com.frbreeder.app.domain.DragonService;
import com.frbreeder.app.domain.entity.Dragon;
import com.frbreeder.app.ui.dto.NewDragonRequest;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping("/dragons")
public class DragonController {

    private final DragonService dragonService;

    public DragonController(final DragonService dragonService) {
        this.dragonService = dragonService;
    }

    @GetMapping
    public ResponseEntity<List<Dragon>> getAll() {
        return ResponseEntity.ok(dragonService.getDragons());
    }

    @PostMapping
    public ResponseEntity<Dragon> addNew(@RequestBody final NewDragonRequest newDragon, final UriComponentsBuilder ucb) {
        Dragon added = dragonService.addDragon(newDragon.name(), newDragon.scryUrl());
        URI uri = ucb.path("dragons/{id}").buildAndExpand(added.getId()).toUri();
        return ResponseEntity.created(uri).body(added);
    }

}
