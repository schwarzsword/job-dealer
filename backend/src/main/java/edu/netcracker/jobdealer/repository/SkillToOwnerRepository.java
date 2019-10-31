package edu.netcracker.jobdealer.repository;


import edu.netcracker.jobdealer.entity.Resume;
import edu.netcracker.jobdealer.entity.SkillToOwner;
import edu.netcracker.jobdealer.entity.Skills;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillToOwnerRepository extends JpaRepository<SkillToOwner, Integer> {
    List<SkillToOwner> findAllByOwner(Resume owner);

    List<SkillToOwner> findAllBySkill(Skills skill);

    List<SkillToOwner> findAllBySkillAndLevelGreaterThanEqual(Skills skill, int level);
}