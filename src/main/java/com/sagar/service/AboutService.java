package com.sagar.service;

import org.acme.beans.AboutDTO;

public interface AboutService {
    String createAbout(AboutDTO aboutDTO);
    AboutDTO updateAbout(String id, AboutDTO aboutDTO);
    String deleteAbout(String id);
    AboutDTO getAbout();
}
