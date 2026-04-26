package com.sagar.service;

import io.smallrye.mutiny.Uni;
import org.acme.beans.SkillDTO;

import java.util.List;

public interface SkillService {
    Uni<String> createSkill(SkillDTO skillDTO);
    Uni<SkillDTO> updateSkill(String id, SkillDTO skillDTO);
    Uni<String> deleteSkill(String id);
    Uni<List<SkillDTO>> getAllSkills();
}
