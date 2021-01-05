package group03.project.design;

import com.structurizr.api.StructurizrClient;
import org.junit.jupiter.api.Test;

import com.structurizr.Workspace;
import com.structurizr.analysis.ComponentFinder;
import com.structurizr.analysis.ReferencedTypesSupportingTypesStrategy;
import com.structurizr.analysis.SpringComponentFinderStrategy;
import com.structurizr.model.*;
import com.structurizr.view.*;

public class GenerateModel {

    @Test
    public void generateModel() throws Exception {


        final int WORKSPACE_ID = 61767;

        final String API_KEY = "b578d34d-9b64-4c66-9fdd-5aa209d6cf51";

        final String API_SECRET = "dc29c3b5-7292-4230-90ab-a7e41c701a8d";

        // Workspace set up and attached to base model.
        Workspace workspace = new Workspace("Development Reflection Toolkit",
                "Spring Boot project for Group 3's implementation of the Development Reflection Toolkit");
        Model model = workspace.getModel();

        // Basic model creation
        SoftwareSystem reflectionToolkit = model.addSoftwareSystem("ReflectionToolkit", "Activity reflection system for lecturers");
        SoftwareSystem cesi = model.addSoftwareSystem("Centre for Education Support and Innovation", "home for learning and teaching at Cardiff University");
        // Categories of people that use model.
        Person siteUser = model.addPerson("Site User", "A person who adds reflections onto provided & custom activities of their learning journey");
        Person admin = model.addPerson("Administrator", "A person who can add official activities onto system, along with view of average datasets");
        siteUser.uses(reflectionToolkit, "Uses");
        admin.uses(reflectionToolkit, "Uses");
        reflectionToolkit.uses(cesi, "Uses");

        // Broad containers to cover the base levels of Model.
        Container webApp = reflectionToolkit.addContainer
                ("Spring Boot Application", "Toolkit web application", "Embedded web container. Tomcat 8.0");
        Container toolKitRDatabase = reflectionToolkit.addContainer
                ("toolKitDatabase", "Relational database that stores data relating to users, activities and participation records", "MySQL");

        //Confirmation of usage by both categories, along with webApp container.
        siteUser.uses(webApp, "Uses", "HTTP");
        admin.uses(webApp, "Uses", "HTTP");
        webApp.uses(cesi, "Uses", "JSON");
        webApp.uses(toolKitRDatabase, "Reads and writes data to Relational database", "JDBC/JPA, port 3306");


        ComponentFinder componentFinder = new ComponentFinder(
                webApp,
                "group03.project",
                new SpringComponentFinderStrategy(
                        new ReferencedTypesSupportingTypesStrategy()
                ));

        componentFinder.findComponents();

        // connects the siteUser Person to all mvc controller that they use.
        webApp.getComponents().stream()
                .filter(tech -> tech.getTechnology().equals(SpringComponentFinderStrategy.SPRING_MVC_CONTROLLER))
                .forEach(tech -> siteUser.uses(tech, "Uses", "HTTP"));
        // connects the siteUser Person to all mvc controller that they use.
        webApp.getComponents().stream()
                .filter(tech -> tech.getTechnology().equals(SpringComponentFinderStrategy.SPRING_MVC_CONTROLLER))
                .forEach(tech -> admin.uses(tech, "Uses", "HTTP"));
        // connects all repository component to toolKitDatabase.
        webApp.getComponents().stream()
                .filter(tech -> tech.getTechnology().equals(SpringComponentFinderStrategy.SPRING_REPOSITORY))
                .forEach(tech -> tech.uses(toolKitRDatabase, "Reads and writes data to Relational database", "JPA"));

        // finds all generated services stored within container
        Component tagService = webApp.getComponentOfType("group03.project.services.offered.TagService");
        Component siteUserService = webApp.getComponentOfType("group03.project.services.offered.SiteUserService");
        Component activityService = webApp.getComponentOfType("group03.project.services.offered.ActivityService");
        Component participationService = webApp.getComponentOfType("group03.project.services.offered.ParticipationService");
        Component reflectionService = webApp.getComponentOfType("group03.project.services.offered.ReflectionService");
        Component objectiveService = webApp.getComponentOfType("group03.project.services.offered.ObjectiveService");
        Component roleService = webApp.getComponentOfType("group03.project.services.offered.RoleService");

        // Login details


        Component loginDetailsService = webApp.getComponentOfType("group03.project.config.LoginDetailsService");
        Component homeController = webApp.getComponentOfType("group03.project.web.controllers.HomeController");

        // finds all generated repositories stored within container.
        Component tagRepo = webApp.getComponentOfType("group03.project.repositories.TagRepoJPA");
        Component siteUserRepo = webApp.getComponentOfType("group03.project.repositories.SiteUserRepoJPA");
        Component activityRepo = webApp.getComponentOfType("group03.project.repositories.ActivityRepoJPA");
        Component participationRepo = webApp.getComponentOfType("group03.project.repositories.ParticipationRepoJPA");
        Component reflectionRepo = webApp.getComponentOfType("group03.project.repositories.ReflectRepoJPA");
        Component objectiveRepo = webApp.getComponentOfType("group03.project.repositories.ObjectiveRepoJPA");
        Component roleRepo = webApp.getComponentOfType("group03.project.repositories.RoleRepoJPA");

        // creates components that cover overall look of application.
        webApp.addComponent("Application Style", "project application's base style", "HTTP");
        webApp.addComponent("BootStrap", "BootStrap implemenation onto web application", "CSS/JS");
        webApp.addComponent("CSS", "", "Custom styling sheets for web application", "CSS/HTTP");
        webApp.addComponent("JavaScript", "JavaScript functionality into web application", "JavaScript/HTTP");

        // finds generated styles within container
        Component appStyle = webApp.getComponentWithName("Application Style");
        Component bootstrap= webApp.getComponentWithName("BootStrap");
        Component css = webApp.getComponentWithName("CSS");
        Component javascript = webApp.getComponentWithName("JavaScript");

        // Application style comes from variety of different sources.
        appStyle.uses(bootstrap, "uses");
        appStyle.uses(css, "uses");
        appStyle.uses(javascript, "uses");

        // All services use a designated repository.
        tagService.uses(tagRepo, "uses");
        siteUserService.uses(siteUserRepo, "uses");
        activityService.uses(activityRepo, "uses");
        participationService.uses(participationRepo, "uses");
        reflectionService.uses(reflectionRepo, "uses");
        objectiveService.uses(objectiveRepo, "uses");
        roleService.uses(roleRepo, "uses");

        // Login has specific route.
        homeController.uses(loginDetailsService, "uses");
        loginDetailsService.uses(siteUserRepo, "uses");



        // Link model with the code within gitlab.
        for (Component component : webApp.getComponents()) {
            for (CodeElement codeElement : component.getCode()) {
                String srcPath = codeElement.getUrl();
                if (srcPath != null) {
                    codeElement.setUrl("https://git.cardiff.ac.uk/c1936922/group-03-staff-project/-/tree/master");
                }
            }
        }

        toolKitRDatabase.setUrl("https://git.cardiff.ac.uk/c1936922/group-03-staff-project/-/tree/master/src/main/resources");

        reflectionToolkit.addTags("Reflection Toolkit");
        webApp.getComponents().stream().filter(tech -> tech.getTechnology().equals(SpringComponentFinderStrategy.SPRING_MVC_CONTROLLER)).forEach(component -> component.addTags("Spring MVC Controller"));
        webApp.getComponents().stream().filter(tech -> tech.getTechnology().equals(SpringComponentFinderStrategy.SPRING_MVC_CONTROLLER)).forEach(component -> component.addTags("Spring REST Controller"));

        webApp.getComponents().stream().filter(tech -> tech.getTechnology().equals(SpringComponentFinderStrategy.SPRING_SERVICE)).forEach(component -> component.addTags("Spring Service"));
        webApp.getComponents().stream().filter(tech -> tech.getTechnology().equals(SpringComponentFinderStrategy.SPRING_REPOSITORY)).forEach(component -> component.addTags("Spring Repository"));
        toolKitRDatabase.addTags("Database");

        ViewSet viewSet = workspace.getViews();
        // Create top level context diagram
        SystemContextView contextView = viewSet.createSystemContextView(reflectionToolkit, "context", "System context diagram for the Reflection Toolkit system.");
        contextView.addAllSoftwareSystems();
        contextView.addAllPeople();
        // Create mid-level container diagram
        ContainerView containerView = viewSet.createContainerView(reflectionToolkit, "containers", "Containers diagram for the Reflection Toolkit system.");
        containerView.addAllSoftwareSystems();
        containerView.addAllPeople();
        containerView.addAllContainers();
        // Create mid-level container diagram
        ComponentView componentView = viewSet.createComponentView(webApp, "components", "The Components diagram for the Reflection Toolkit web application.");
        componentView.addAllComponents();
        componentView.addAllPeople();
        componentView.add(toolKitRDatabase);

        Styles styles = viewSet.getConfiguration().getStyles();
        styles.addElementStyle("Reflection Toolkit").background("#6CB33E").color("#ffffff");
        styles.addElementStyle(Tags.PERSON).background("#519823").color("#ffffff").shape(Shape.Person);
        styles.addElementStyle(Tags.CONTAINER).background("#91D366").color("#ffffff");
        styles.addElementStyle("Database").shape(Shape.Cylinder);

        styles.addElementStyle("Spring MVC Controller").background("#D4F3C0").color("#000000");
        styles.addElementStyle("Spring REST Controller").background("#D4FFC0").color("#000000");
        styles.addElementStyle("Spring Service").background("#6CB33E").color("#000000");
        styles.addElementStyle("Spring Repository").background("#95D46C").color("#000000");


        StructurizrClient client = new StructurizrClient(API_KEY, API_SECRET);

        client.putWorkspace(WORKSPACE_ID, workspace);

    }
}
