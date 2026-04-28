package com.sagar.service;

import org.acme.beans.SkillDTO;

import java.util.List;

public interface SkillService {
    String createSkill(SkillDTO skillDTO);
    SkillDTO updateSkill(String id, SkillDTO skillDTO);
    String deleteSkill(String id);
    List<SkillDTO> getAllSkills();
}
