package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.entity.Skills;
import edu.netcracker.jobdealer.repository.SkillsRepository;
import edu.netcracker.jobdealer.service.SkillsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SkillsServiceImpl implements SkillsService {

    private final SkillsRepository skillsRepository;

    public SkillsServiceImpl(SkillsRepository skillsRepository) {
        this.skillsRepository = skillsRepository;
    }

    @Override
    public List<Skills> getAll() {
        return skillsRepository.findAll();
    }

    @Override
    public List<Skills> getAllLike(String regex) {
        return skillsRepository.findAllByNameContaining(regex);
    }
}
