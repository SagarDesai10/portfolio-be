package com.sagar.util;

/**
 * Central place for all application-wide string constants and HTTP status codes.
 * Use these instead of hard-coded literals throughout the project.
 */
public final class AppConstants {

    private AppConstants() {}

    // ── HTTP Status codes ──────────────────────────────────────────────────────
    public static final int STATUS_OK          = 200;
    public static final int STATUS_CREATED     = 201;
    public static final int STATUS_BAD_REQUEST  = 400;
    public static final int STATUS_UNAUTHORIZED = 401;
    public static final int STATUS_NOT_FOUND    = 404;
    public static final int STATUS_CONFLICT    = 409;

    // ── Generic operation results ──────────────────────────────────────────────
    public static final String CREATED_SUCCESSFULLY = "Created Successfully";
    public static final String DELETED_SUCCESSFULLY = "Deleted Successfully";
    public static final String INVALID_ID           = "Invalid Id";

    // ── About ──────────────────────────────────────────────────────────────────
    public static final String ABOUT_CREATED           = "About details created";
    public static final String ABOUT_FETCHED           = "About details fetched successfully";
    public static final String ABOUT_UPDATED           = "About details updated successfully";
    public static final String ABOUT_DELETED           = "About details deleted successfully";
    public static final String ABOUT_ALREADY_EXISTS    = "About record already exists. Only one record is allowed. Use update instead.";
    public static final String ABOUT_NOT_FOUND         = "No About record found";
    public static final String ABOUT_NOT_FOUND_BY_ID   = "About record not found for the given id";

    // ── Skill ──────────────────────────────────────────────────────────────────
    public static final String SKILL_CREATED   = "Skill details created successfully";
    public static final String SKILL_FETCHED   = "Skill details fetched successfully";
    public static final String SKILL_UPDATED   = "Skill details updated successfully";
    public static final String SKILL_DELETED   = "Skill details deleted successfully";
    public static final String SKILL_NOT_FOUND = "Skill not found for the given id";

    // ── Project ────────────────────────────────────────────────────────────────
    public static final String PROJECT_CREATED   = "Project details created successfully";
    public static final String PROJECT_FETCHED   = "Project details fetched successfully";
    public static final String PROJECT_UPDATED   = "Project details updated successfully";
    public static final String PROJECT_DELETED   = "Project details deleted successfully";
    public static final String PROJECT_NOT_FOUND = "Project not found for the given id";

    // ── Experience ─────────────────────────────────────────────────────────────
    public static final String EXPERIENCE_CREATED   = "Experience details created successfully";
    public static final String EXPERIENCE_FETCHED   = "Experience details fetched successfully";
    public static final String EXPERIENCE_UPDATED   = "Experience details updated successfully";
    public static final String EXPERIENCE_DELETED   = "Experience details deleted successfully";
    public static final String EXPERIENCE_NOT_FOUND = "Experience not found for the given id";

    // ── Education ──────────────────────────────────────────────────────────────
    public static final String EDUCATION_CREATED   = "Education details created successfully";
    public static final String EDUCATION_FETCHED   = "Education details fetched successfully";
    public static final String EDUCATION_UPDATED   = "Education details updated successfully";
    public static final String EDUCATION_DELETED   = "Education details deleted successfully";
    public static final String EDUCATION_NOT_FOUND = "Education not found for the given id";

    // ── Certificate ────────────────────────────────────────────────────────────
    public static final String CERTIFICATE_CREATED   = "Certificate details created successfully";
    public static final String CERTIFICATE_FETCHED   = "Certificate details fetched successfully";
    public static final String CERTIFICATE_UPDATED   = "Certificate details updated successfully";
    public static final String CERTIFICATE_DELETED   = "Certificate details deleted successfully";
    public static final String CERTIFICATE_NOT_FOUND = "Certificate not found for the given id";

    // ── Auth ───────────────────────────────────────────────────────────────────────
    public static final String JWT_ISSUER               = "portfolio-be";
    public static final String JWT_ROLE_ADMIN           = "admin";
    public static final long   JWT_EXPIRY_MINUTES       = 30L;

    public static final String AUTH_INVALID_CREDENTIALS = "Invalid username or password";
    public static final String AUTH_LOGIN_SUCCESS       = "Login successful";

    // ── Social Link ────────────────────────────────────────────────────────────────
    public static final String SOCIAL_CREATED   = "Social link details created successfully";
    public static final String SOCIAL_FETCHED   = "Social details fetched successfully";
    public static final String SOCIAL_UPDATED   = "Social link details updated successfully";
    public static final String SOCIAL_DELETED   = "Social link details deleted successfully";
    public static final String SOCIAL_NOT_FOUND = "Social link not found for the given id";
}

