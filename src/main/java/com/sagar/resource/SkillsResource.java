package com.sagar.resource;

import com.sagar.dto.ResponseDTO;
import com.sagar.service.SkillService;
import com.sagar.util.AppConstants;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.beans.SkillDTO;

@Path("/admin/skill")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SkillsResource extends CommonResource {

    @Inject
    private SkillService skillService;

    @POST
    public ResponseDTO<String> createSkills(@Valid SkillDTO skillDTO) {
        return buildResponse(AppConstants.SKILL_CREATED, AppConstants.STATUS_CREATED, skillService.createSkill(skillDTO));
    }

    @PATCH
    @Path("/{id}")
    public ResponseDTO<SkillDTO> updateSkills(@PathParam("id") String id, @Valid SkillDTO skillDTO) {
        return buildResponse(AppConstants.SKILL_UPDATED, AppConstants.STATUS_OK, skillService.updateSkill(id, skillDTO));
    }

    @DELETE
    @Path("/{id}")
    public ResponseDTO<String> deleteSkills(@PathParam("id") String id) {
        return buildResponse(AppConstants.SKILL_DELETED, AppConstants.STATUS_OK, skillService.deleteSkill(id));
    }
}
