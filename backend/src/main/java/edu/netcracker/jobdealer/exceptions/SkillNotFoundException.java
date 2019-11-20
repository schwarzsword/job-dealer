package edu.netcracker.jobdealer.exceptions;

public class SkillNotFoundException extends NotFoundException {
    public SkillNotFoundException(String skillName) {
        super("Skill " + skillName + " not found");
    }

    public SkillNotFoundException() {
        super("Skill not found");
    }
}
