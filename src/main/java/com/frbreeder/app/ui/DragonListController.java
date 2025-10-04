package com.frbreeder.app.ui;

import com.frbreeder.app.domain.Dragon;
import com.frbreeder.app.domain.DragonService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dragons")
public class DragonListController {

    private final DragonService dragonService;

    public DragonListController(final DragonService dragonService) {
        this.dragonService = dragonService;
    }

    @GetMapping
    public ResponseEntity<List<Dragon>> getAll() {
        return ResponseEntity.ok(dragonService.getDragons());
    }

}
