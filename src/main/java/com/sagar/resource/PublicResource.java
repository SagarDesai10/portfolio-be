package com.sagar.resource;

import com.sagar.dto.ResponseDTO;
import com.sagar.service.*;
import com.sagar.util.AppConstants;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.beans.*;

import java.util.List;

@Resource
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
    public Uni<ResponseDTO<AboutDTO>> getAboutDetails() {
        return Uni.createFrom().deferred(() -> aboutService.getAbout())
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                .map(r -> buildResponse(AppConstants.ABOUT_FETCHED, AppConstants.STATUS_OK, r));
    }

    @GET
    @Path("/skills")
    public Uni<ResponseDTO<List<SkillDTO>>> getSkillDetails() {
        return Uni.createFrom().deferred(() -> skillService.getAllSkills())
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                .map(r -> buildResponse(AppConstants.SKILL_FETCHED, AppConstants.STATUS_OK, r));
    }

    @GET
    @Path("/projects")
    public Uni<ResponseDTO<List<ProjectDTO>>> getProjectDetails() {
        return Uni.createFrom().deferred(() -> projectService.getAllProjects())
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                .map(r -> buildResponse(AppConstants.PROJECT_FETCHED, AppConstants.STATUS_OK, r));
    }

    @GET
    @Path("/experience")
    public Uni<ResponseDTO<List<ExperienceDTO>>> getExperienceDetails() {
        return Uni.createFrom().deferred(() -> experienceService.getAllExperiences())
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                .map(r -> buildResponse(AppConstants.EXPERIENCE_FETCHED, AppConstants.STATUS_OK, r));
    }

    @GET
    @Path("/education")
    public Uni<ResponseDTO<List<EducationDTO>>> getEducationDetails() {
        return Uni.createFrom().deferred(() -> educationService.getAllEducations())
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                .map(r -> buildResponse(AppConstants.EDUCATION_FETCHED, AppConstants.STATUS_OK, r));
    }

    @GET
    @Path("/social-link")
    public Uni<ResponseDTO<List<SocialDTO>>> getSocialDetails() {
        return Uni.createFrom().deferred(() -> socialLinkService.getAllSocialLink())
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                .map(r -> buildResponse(AppConstants.SOCIAL_FETCHED, AppConstants.STATUS_OK, r));
    }

    @GET
    @Path("/certificate")
    public Uni<ResponseDTO<List<CertificateDTO>>> getCertificateDetails() {
        return Uni.createFrom().deferred(() -> certificateService.getAllCertificates())
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                .map(r -> buildResponse(AppConstants.CERTIFICATE_FETCHED, AppConstants.STATUS_OK, r));
    }
}
