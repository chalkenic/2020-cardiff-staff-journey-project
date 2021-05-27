package group03.project.web.controllers;


import group03.project.domain.*;
import group03.project.services.implementation.ParticipationServiceImpl;
import group03.project.services.offered.ActivityService;
import group03.project.services.offered.ObjectiveService;
import group03.project.services.offered.SiteUserService;
import group03.project.services.offered.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controller handles basic application navigation (login/logout)
 */
@Controller
public class HomeController {
    @Autowired
    private ParticipationServiceImpl participationService;

    @Autowired
    private SiteUserService siteUserService;

    @Autowired
    private ActivityService activityService;


    @Autowired
    private ObjectiveService objService;

    @Autowired
    private TagService tagService;
    /**
     * Default navigation mapping to access localhost:8080
     * @return login form for localhost;
     */
    @GetMapping("/")
    public String navigateToRootPage(Model model) {

        String message = "";

        model.addAttribute("message", message);

        return "login";
    }

    /**
     * Ronan implementation of adding about page - moved to controller controlling home.
     * @return about.html template page.
     */
    @GetMapping("/about")
    public String NavigateToAbout(){
        return "about";
    }

    @GetMapping("/dashboard")
    public String navigateToDashboard(@ModelAttribute("user") String user, Model model, Authentication authentication) {

        String theUser = ControllerSupport.getAuthenticatedUserName(authentication);
        Long currentId = getCurrentID(authentication);
        /*
          String parsed onto page as attribute for Thymeleaf
         */
        Long currentID = getCurrentID(authentication);

//            List<Participation> participations = participationService.findAllParticipations();
        List<Participation> userParticipations = participationService.getParticipationsByUserId(currentId);
        List<Activity> userActivities = activityService.getAllParticipatedActivities(currentId);



//            //Make a list of all the participations unique to the current user
//            for (int z = 0; z < participationService.getParticipationListSize(); z++) {
//                Participation participation = participations.get(z);
//                if(participation.getUserID() == currentID) {
//                    myParticipations.add(participation);
//                }
//            }



            List<Participation> allParticipations = participationService.findAllParticipations();

            List<Participation> t_myParticipations = new ArrayList<>();
            List<Activity> t_myActivities = new ArrayList<>();
            List<Long> t_tagList = new ArrayList<>();
            //Make a list of all the tags that the current user has
//            for (int partlist = 0; partlist < allParticipations.size(); partlist++) {
//                Participation participation = allParticipations.get(partlist);
//                if(participation.getUserID() == currentID) {
//                    t_myParticipations.add(participation);
//                }
//            }

//        for (Participation mypart : myParticipations) {
//            t_myActivities.add(participationService.getRelatedActivity(mypart));
//        }

//        for (Object obj: objService.findObjectivesByActivityID())
//                for (Participation mypart : t_myParticipations) {
//                    t_myActivities.add(participationService.getRelatedActivity(mypart));
//                }

        for (Activity myact : userActivities){
            for (Objective obj : objService.getAllObjectives()) {
                if (objService.getAssociatedActivity(obj) == myact) {
                    //System.out.println("Objective " + obj.getObjectiveID() + " matches " + myact.getActivityID()
                    // + ", objective tag " + obj.getTag().getTagName() + " being added to list of tags.");
                    t_tagList.add(tagService.findATagByID(obj.getTag().getTagID()).get().getTagID());
                    //System.out.println("Current tag list by id: " + t_tagList);
                }
            }
        }
//                for (Activity myact : t_myActivities){
//                        for (Objective obj : objService.getAllObjectives()) {
//                            if (objService.getAssociatedActivity(obj) == myact) {
//                                //System.out.println("Objective " + obj.getObjectiveID() + " matches " + myact.getActivityID()
//                                // + ", objective tag " + obj.getTag().getTagName() + " being added to list of tags.");
//                                t_tagList.add(tagService.findATagByID(obj.getTag().getTagID()).get().getTagID());
//                                //System.out.println("Current tag list by id: " + t_tagList);
//                            }
//                        }
//                    }


                //new

//        List<Participation> a_myParticipations = new ArrayList<>();
//        List<Activity> a_myActivities = new ArrayList<>();
        List<Long> a_tagList = new ArrayList<>();
        //Make a list of all the tags that the current user has
//        for (int partlist = 0; partlist < allParticipations.size(); partlist++) {
//            Participation participation = allParticipations.get(partlist);
//            a_myParticipations.add(participation);
//        }
//        for (Participation mypart : a_myParticipations) {
//            a_myActivities.add(participationService.getRelatedActivity(mypart));
//        }
//        for (Activity myact : a_myActivities){
//            for (Objective obj : objService.getAllObjectives()) {
//                if (objService.getAssociatedActivity(obj) == myact) {
//                    a_tagList.add(tagService.findATagByID(obj.getTag().getTagID()).get().getTagID());
//                }
//            }
//        }

        for (Activity myact : userActivities){
            for (Objective obj : objService.getAllObjectives()) {
                if (objService.getAssociatedActivity(obj) == myact) {
                    a_tagList.add(tagService.findATagByID(obj.getTag().getTagID()).get().getTagID());
                }
            }
        }

        List<String> a_tagNames_all = new ArrayList<>();
        List<String> a_tagNames_A = new ArrayList<>();
        List<String> a_tagNames_D = new ArrayList<>();
        List<String> a_tagNames_K = new ArrayList<>();
        List<String> a_tagNames_V = new ArrayList<>();

        for (Long tagID : a_tagList)
        {
            if (tagService.findATagByID(tagID).get().getIsOfficial()) {
                if (tagService.findATagByID(tagID).get().getTagName().contains("A")){
                    a_tagNames_A.add(tagService.findATagByID(tagID).get().getTagName());
                    a_tagNames_all.add(tagService.findATagByID(tagID).get().getTagName());

                }
                else if (tagService.findATagByID(tagID).get().getTagName().contains("D")){
                    a_tagNames_D.add(tagService.findATagByID(tagID).get().getTagName());
                    a_tagNames_all.add(tagService.findATagByID(tagID).get().getTagName());

                }
                else if (tagService.findATagByID(tagID).get().getTagName().contains("K")){
                    a_tagNames_K.add(tagService.findATagByID(tagID).get().getTagName());
                    a_tagNames_all.add(tagService.findATagByID(tagID).get().getTagName());

                }
                else if (tagService.findATagByID(tagID).get().getTagName().contains("V")){
                    a_tagNames_V.add(tagService.findATagByID(tagID).get().getTagName());
                    a_tagNames_all.add(tagService.findATagByID(tagID).get().getTagName());

                }
                else{
                    System.out.println("Official tag did not match criteria. Tag name: "+tagService.findATagByID(tagID).get().getTagName());

                }
            }
        }

        /* THIS IS FOR USE WITH SEPARATE CHARTS, DATA IS GOING TO BE COLLATED INTO SINGLE
           CHART SO NO NEED FOR THEM RIGHT NOW.
        model.addAttribute("otagNamesA", a_tagNames_A);
        model.addAttribute("otagNamesV", a_tagNames_V);
        model.addAttribute("otagNamesK", a_tagNames_K);
        model.addAttribute("otagNamesD", a_tagNames_D);
         */
//        model.addAttribute("participations", myParticipations);
        model.addAttribute("participations", userParticipations);

        model.addAttribute("otagNamesAll",a_tagNames_all);

        /* These print statements are to test that the tag names are added to list
        System.out.println("a" + a_tagNames_A);
        System.out.println("v" + a_tagNames_V);
        System.out.println("k" + a_tagNames_K);
        System.out.println("d" + a_tagNames_D);
        */


        List<Tag> allTags = tagService.findAllTags();

        Integer amountOfOfficialTags = tagService.findTagsIfOfficial().size();



        List<String> tagNames = new ArrayList<>();
        for (Long tagID : t_tagList)
        {
            if (tagService.findATagByID(tagID).get().getIsOfficial()) {
                tagNames.add(tagService.findATagByID(tagID).get().getTagName());
            }
        }

        List<String> incompleteTagNames = new ArrayList<>();
        for (Tag thetag : allTags){
            if (!tagNames.contains(thetag.getTagName())){
                if(thetag.getIsOfficial()) {
                    incompleteTagNames.add(thetag.getTagName());
                }
            }
        }


        model.addAttribute("user", theUser);
        model.addAttribute("userstags",tagNames);
        model.addAttribute("incompleteTags",incompleteTagNames);
        model.addAttribute("tags", allTags);
        model.addAttribute("totaltagsamt",amountOfOfficialTags);
                /*
          Redirects user object based upon authority set, streaming into 2 different dashboard pages.
         */
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return "dashboard_a";
        } else {
            return "dashboard";
        }
    }



    /**
     * Handles method for logging out of application.
     * @param request - object that handles a method to read html body ( getInputStream() );
     * @param response - handles response headers.
     * @return redirection to localhost:8080 page
     */
    @GetMapping("/logout")
    public String HandleLogout(HttpServletRequest request, HttpServletResponse response, RedirectAttributes model) {
        /*
          Collects current authentication found in session.
         */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        /*
          Performs static logout method that expires all parsed session attributes.
         */
        new SecurityContextLogoutHandler().logout(request, response, authentication);
        /*
          Redirect user back to initial localhost:8080.
         */
        /*
          Provide message to front-end indicating logout successful
         */
        return "redirect:/";
    }

    /**
     * Mapping for failed logins dictated by SecurityConfig class route.
     * @return redirection back to application root.
     */
    @PostMapping("/failed-login")
    public String handleFailedLogin(HttpServletRequest request, Model model) {

            String loginFailure = "failedLogin";

            model.addAttribute("message", loginFailure);

            return "login";


//            return "redirect:" + request.getHeader("referer");
//        }
//        /**
//         * returns user back to initial localhost:8080
//         */
//
//        return "redirect:";
    }
    Long getCurrentID(Authentication authentication) {
        String currentUserName = ControllerSupport.getAuthenticatedUserName(authentication);
        Optional<SiteUser> currentUserOptional = siteUserService.findUserByUserName(currentUserName);
        SiteUser currentUser = currentUserOptional.get();
        Long currentUserID = currentUser.getUserID();

        return currentUserID;
    }

    @GetMapping("/403-error")
    public String handleUnauthorizedAccess(Model model) {
        return "403-error";
    }
}
