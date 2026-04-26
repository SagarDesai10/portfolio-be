package com.sagar.resource;

import com.sagar.dto.ResponseDTO;
import com.sagar.service.SkillService;
import com.sagar.util.AppConstants;
import io.smallrye.mutiny.Uni;
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
    public Uni<ResponseDTO<String>> createSkills(@Valid SkillDTO skillDTO) {
        return skillService.createSkill(skillDTO)
                .map(r -> buildResponse(AppConstants.SKILL_CREATED, AppConstants.STATUS_CREATED, r));
    }

    @PATCH
    @Path("/{id}")
    public Uni<ResponseDTO<SkillDTO>> updateSkills(@PathParam("id") String id, @Valid SkillDTO skillDTO) {
        return skillService.updateSkill(id, skillDTO)
                .map(r -> buildResponse(AppConstants.SKILL_UPDATED, AppConstants.STATUS_OK, r));
    }

    @DELETE
    @Path("/{id}")
    public Uni<ResponseDTO<String>> deleteSkills(@PathParam("id") String id) {
        return skillService.deleteSkill(id)
                .map(r -> buildResponse(AppConstants.SKILL_DELETED, AppConstants.STATUS_OK, r));
    }
}
