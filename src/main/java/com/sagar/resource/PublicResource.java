package com.sagar.resource;

import com.sagar.dto.ResponseDTO;
import com.sagar.service.*;
import com.sagar.util.AppConstants;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.beans.*;

import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PublicResource extends CommonResource {

    @Inject
    private AboutService aboutService;

    @Inject
    private SocialLinkService socialLinkService;

    @Inject
    private CertificateService certificateService;

    @Inject
    private EducationService educationService;

    @Inject
    private ProjectService projectService;

    @Inject
    private SkillService skillService;

    @Inject
    private ExperienceService experienceService;

    @GET
    @Path("/about")
    public ResponseDTO<AboutDTO> getAboutDetails() {
        return buildResponse(AppConstants.ABOUT_FETCHED, AppConstants.STATUS_OK, aboutService.getAbout());
    }

    @GET
    @Path("/skills")
    public ResponseDTO<List<SkillDTO>> getSkillDetails() {
        return buildResponse(AppConstants.SKILL_FETCHED, AppConstants.STATUS_OK, skillService.getAllSkills());
    }

    @GET
    @Path("/projects")
    public ResponseDTO<List<ProjectDTO>> getProjectDetails() {
        return buildResponse(AppConstants.PROJECT_FETCHED, AppConstants.STATUS_OK, projectService.getAllProjects());
    }

    @GET
    @Path("/experience")
    public ResponseDTO<List<ExperienceDTO>> getExperienceDetails() {
        return buildResponse(AppConstants.EXPERIENCE_FETCHED, AppConstants.STATUS_OK, experienceService.getAllExperiences());
    }

    @GET
    @Path("/education")
    public ResponseDTO<List<EducationDTO>> getEducationDetails() {
        return buildResponse(AppConstants.EDUCATION_FETCHED, AppConstants.STATUS_OK, educationService.getAllEducations());
    }

    @GET
    @Path("/social-link")
    public ResponseDTO<List<SocialDTO>> getSocialDetails() {
        return buildResponse(AppConstants.SOCIAL_FETCHED, AppConstants.STATUS_OK, socialLinkService.getAllSocialLink());
    }

    @GET
    @Path("/certificate")
    public ResponseDTO<List<CertificateDTO>> getCertificateDetails() {
        return buildResponse(AppConstants.CERTIFICATE_FETCHED, AppConstants.STATUS_OK, certificateService.getAllCertificates());
    }
}
