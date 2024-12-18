package com.waltmann.waltmann_api.controller;

import com.waltmann.waltmann_api.domain.Tech;
import com.waltmann.waltmann_api.service.TechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/techs")
public class TechController {
    @Autowired
    private TechService techService;

    @GetMapping
    public String retrieves() {
        return techService.retrieves();
    }

    @GetMapping("/{id}")
    public String retrievesOne(@PathVariable String id) {
        return techService.retrievesOne(id);
    }

    @PostMapping
    public String create(@RequestBody Tech tech) {
        return techService.create(tech);
    }

    @PutMapping("/{id}")
    public String update(@PathVariable String id, @RequestBody Tech tech) {
        return techService.update(id, tech);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        return techService.delete(id);
    }
}


