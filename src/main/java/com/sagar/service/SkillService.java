package com.sagar.service;

import org.acme.beans.SkillDTO;

import java.util.List;

public interface SkillService {
    public String createSkill(SkillDTO skillDTO);
    public SkillDTO updateSkill(String id, SkillDTO skillDTO);
    public String deleteSkill(String id);
    public List<SkillDTO> getAllSkills();
}

