package com.sagar.service;

import org.acme.beans.SocialDTO;

import java.util.List;

public interface SocialLinkService {
    String createSocialLink(SocialDTO socialDTO);
    SocialDTO updateSocialLink(String id, SocialDTO socialDTO);
    String deleteSocialLink(String id);
    List<SocialDTO> getAllSocialLink();
}
