package com.waltmann.waltmann_api.controller;

import com.waltmann.waltmann_api.domain.tech.Tech;
import com.waltmann.waltmann_api.service.tech.TechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tech")
public class TechController {
    @Autowired
    private TechService techService;

    @GetMapping
    public ResponseEntity<List<Tech>> retrieves() {
        List<Tech> techs = techService.retrieves();

        return ResponseEntity.ok(techs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tech> retrievesOne(@PathVariable UUID id) {
        Tech tech = techService.retrievesOne(id);

        return ResponseEntity.ok(tech);

    }

    @PostMapping
    public ResponseEntity<Tech> create(
        @RequestParam String name,
        @RequestParam String linkName,
        @RequestParam String linkKey,
        @RequestParam MultipartFile file
        ) {
        Tech tech = techService.create(name, linkName, linkKey, file);

        return ResponseEntity.ok(tech);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tech> update(
        @PathVariable UUID id,
        @RequestParam String name,
        @RequestParam String linkName,
        @RequestParam String linkKey,
        @RequestParam MultipartFile file
    ) {
        Tech tech = techService.update(id, name, linkName, linkKey, file);

        return ResponseEntity.ok(tech);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
        techService.delete(id);

        return ResponseEntity.ok().build();
    }
}


