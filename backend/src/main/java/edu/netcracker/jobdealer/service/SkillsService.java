package edu.netcracker.jobdealer.service;

import edu.netcracker.jobdealer.entity.Skills;

import java.util.List;

public interface SkillsService {
    List<Skills> getAll();

    List<Skills> getAllLike(String regex);
}
