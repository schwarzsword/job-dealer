package edu.netcracker.jobdealer.controller;

import edu.netcracker.jobdealer.entity.Skills;
import edu.netcracker.jobdealer.service.SkillsService;
import org.dozer.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SkillsController {

    private final Mapper mapper;
    private final SkillsService skillsSevice;

    public SkillsController(Mapper mapper, SkillsService skillsSevice) {
        this.mapper = mapper;
        this.skillsSevice = skillsSevice;
    }

    @GetMapping(value = "/skills")
    public ResponseEntity<?> getAll() {
        List<Skills> all = skillsSevice.getAll();
        List<String> skills = all.stream()
                .map(Skills::getName)
                .collect(Collectors.toList());
        return ResponseEntity.ok(skills);
    }

}
