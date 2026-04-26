package com.sagar.service;

import io.smallrye.mutiny.Uni;
import org.acme.beans.SocialDTO;

import java.util.List;

public interface SocialLinkService {
    Uni<String> createSocialLink(SocialDTO socialDTO);
    Uni<SocialDTO> updateSocialLink(String id, SocialDTO socialDTO);
    Uni<String> deleteSocialLink(String id);
    Uni<List<SocialDTO>> getAllSocialLink();
}
