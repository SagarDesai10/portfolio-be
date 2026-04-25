package com.sagar.service;

import org.acme.beans.SocialDTO;

import java.util.List;

public interface SocialLinkService {
    public String createSocialLink(SocialDTO socialDTO);
    public SocialDTO updateSocialLink(String id,SocialDTO socialDTO);
    public String deleteSocialLink(String id);
    public List<SocialDTO> getAllSocialLink();
}
