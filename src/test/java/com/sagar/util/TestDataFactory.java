package com.sagar.util;

import com.sagar.entity.*;
import org.acme.beans.*;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Shared factory methods used across all unit test classes.
 * Keeps test data creation DRY.
 */
public final class TestDataFactory {

    private TestDataFactory() {}

    // ── IDs ────────────────────────────────────────────────────────────────────
    public static final String VALID_ID   = new ObjectId().toHexString();
    public static final String INVALID_ID = "not-an-object-id";

    // ── About ──────────────────────────────────────────────────────────────────
    public static About aboutEntity() {
        About a = new About();
        a.id        = new ObjectId(VALID_ID);
        a.name      = "Sagar";
        a.profession= "Developer";
        a.email     = "sagar@example.com";
        a.degree    = "BE";
        a.role      = "Full Stack";
        a.location  = "India";
        a.about     = List.of("I build things");
        return a;
    }

    public static AboutDTO aboutDTO() {
        AboutDTO dto = new AboutDTO();
        dto.setName("Sagar");
        dto.setProfession("Developer");
        dto.setEmail("sagar@example.com");
        dto.setDegree("BE");
        dto.setRole("Full Stack");
        dto.setLocation("India");
        dto.setAbout(List.of("I build things"));
        return dto;
    }

    // ── Skill ──────────────────────────────────────────────────────────────────
    public static Skill skillEntity() {
        Skill s = new Skill();
        s.id       = new ObjectId(VALID_ID);
        s.name     = "Java";
        s.category = "Backend";
        s.stars    = 4;
        s.img      = "java.png";
        return s;
    }

    public static SkillDTO skillDTO() {
        SkillDTO dto = new SkillDTO();
        dto.setId(VALID_ID);
        dto.setName("Java");
        dto.setCategory("Backend");
        dto.setStars(4);
        dto.setImg("java.png");
        return dto;
    }

    // ── Project ────────────────────────────────────────────────────────────────
    public static Project projectEntity() {
        Project p = new Project();
        p.id          = new ObjectId(VALID_ID);
        p.name        = "Portfolio";
        p.category    = "Web";
        p.description = "My portfolio site";
        p.keyTech     = List.of("Java", "Quarkus");
        p.githubLink  = "https://github.com/sagar/portfolio";
        p.liveLink    = "https://sagar.dev";
        return p;
    }

    public static ProjectDTO projectDTO() {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(VALID_ID);
        dto.setName("Portfolio");
        dto.setCategory("Web");
        dto.setDescription("My portfolio site");
        dto.setKeyTech(List.of("Java", "Quarkus"));
        dto.setGithubLink("https://github.com/sagar/portfolio");
        dto.setLiveLink("https://sagar.dev");
        return dto;
    }

    // ── Certificate ────────────────────────────────────────────────────────────
    public static Certificate certificateEntity() {
        Certificate c = new Certificate();
        c.id    = new ObjectId(VALID_ID);
        c.name  = "AWS Certified";
        c.skill = "Cloud";
        c.link  = "https://aws.cert";
        return c;
    }

    public static CertificateDTO certificateDTO() {
        CertificateDTO dto = new CertificateDTO();
        dto.setId(VALID_ID);
        dto.setName("AWS Certified");
        dto.setSkill("Cloud");
        dto.setLink("https://aws.cert");
        return dto;
    }

    // ── SocialLink ─────────────────────────────────────────────────────────────
    public static SocialLink socialLinkEntity() {
        SocialLink s = new SocialLink();
        s.id   = new ObjectId(VALID_ID);
        s.name = "GitHub";
        s.link = "https://github.com/sagar";
        s.img  = "github.png";
        return s;
    }

    public static SocialDTO socialDTO() {
        SocialDTO dto = new SocialDTO();
        dto.setId(VALID_ID);
        dto.setName("GitHub");
        dto.setLink("https://github.com/sagar");
        dto.setImg("github.png");
        return dto;
    }

    // ── Experience ─────────────────────────────────────────────────────────────
    public static Experience experienceEntity() {
        Experience e = new Experience();
        e.id          = new ObjectId(VALID_ID);
        e.companyName = "Acme Corp";
        e.position    = "Developer";
        e.startDate   = "Jan-2023";
        e.endDate     = "Jun-2023";
        e.about       = List.of("Built stuff");
        return e;
    }

    public static ExperienceDTO experienceDTO() {
        ExperienceDTO dto = new ExperienceDTO();
        dto.setId(VALID_ID);
        dto.setCompanyName("Acme Corp");
        dto.setPosition("Developer");
        dto.setStartDate("Jan-2023");
        dto.setEndDate("Jun-2023");
        dto.setAbout(List.of("Built stuff"));
        return dto;
    }

    // ── Education ─────────────────────────────────────────────────────────────
    public static Education educationEntity() {
        Education e = new Education();
        e.id        = new ObjectId(VALID_ID);
        e.stream    = "Computer Science";
        e.clgName   = "MIT";
        e.startYear = 2018;
        e.endYear   = 2022;
        e.marks     = "9.0";
        e.about     = List.of("Graduated with honours");
        return e;
    }

    public static EducationDTO educationDTO() {
        EducationDTO dto = new EducationDTO();
        dto.setId(VALID_ID);
        dto.setStream("Computer Science");
        dto.setClgName("MIT");
        dto.setStartYear(2018);
        dto.setEndYear(2022);
        dto.setMarks("9.0");
        dto.setAbout(List.of("Graduated with honours"));
        return dto;
    }
}

