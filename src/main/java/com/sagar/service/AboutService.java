package com.sagar.service;

import io.smallrye.mutiny.Uni;
import org.acme.beans.AboutDTO;

public interface AboutService {
    Uni<String> createAbout(AboutDTO aboutDTO);
    Uni<AboutDTO> updateAbout(String id, AboutDTO aboutDTO);
    Uni<String> deleteAbout(String id);
    Uni<AboutDTO> getAbout();
}
