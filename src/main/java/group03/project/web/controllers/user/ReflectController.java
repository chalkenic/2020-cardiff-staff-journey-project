package group03.project.web.controllers.user;

import group03.project.domain.Activity;
import group03.project.domain.Participation;
import group03.project.domain.Reflection;
import group03.project.domain.SiteUser;
import group03.project.services.implementation.ActivityServiceImpl;
import group03.project.services.implementation.ParticipationServiceImpl;
import group03.project.services.implementation.ReflectionServiceImpl;
import group03.project.services.offered.ActivityService;
import group03.project.services.offered.ReflectionService;
import group03.project.services.offered.SiteUserService;
import group03.project.web.controllers.ControllerSupport;
import group03.project.web.lists.ReflectList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Part;
import javax.validation.ValidationException;
import java.sql.Ref;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("user")
public class ReflectController {

    @Autowired
    private ParticipationServiceImpl participationService;

    @Autowired
    private ReflectionService reflectionService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private SiteUserService siteUserService;

    //Add a reflection
    @GetMapping("/add-reflection")
    public String addReflection(Model model, Authentication authentication) {
        Reflection reflection = new Reflection();
        model.addAttribute("reflection", reflection);

        List<Activity> possibleActivities = reflectionSetup(authentication);

        model.addAttribute("activities", possibleActivities);
        return "add-reflection";
    }

    @GetMapping("/add-reflection-direct")
    public String addReflectionDirect (Model model, Authentication authentication) {
        Reflection reflection = new Reflection();
        model.addAttribute("reflection", reflection);

        List<Activity> possibleActivities = reflectionSetup(authentication);

        model.addAttribute("activities", possibleActivities);
        return "add-reflection-direct";
    }

    //Submit the entry to the database
    @PostMapping("/add-reflection")
    public String submitReflection(RedirectAttributes redirectAttributes, @ModelAttribute("reflection") Reflection reflection, Authentication authentication) {

        if(reflection.getParticipationID() == null) {
            throw new ValidationException("No valid assigned activity - cannot retrieve related activity and participation if no activity is present");
        }

        System.out.println(reflection.getParticipationID());
        Long activityID = reflection.getParticipationID();
        Activity chosenActivity = new Activity();
        Long currentID = getCurrentID(authentication);
        List<Participation> participations = participationService.findAllParticipations();
        Participation chosenParticipation = new Participation();
        for (Participation currentPart : participations) {
            if(currentPart.getUserID() == currentID) {
                if(currentPart.getActivityID() == activityID) {
                    chosenParticipation = currentPart;
                }
            }
        }

        reflection.setParticipationID(chosenParticipation.getParticipationID());
        reflection.setTagID(1L); //Placeholder until we assign tags to activities
        reflectionService.saveReflection(reflection);
        redirectAttributes.addFlashAttribute("success",true);
        redirectAttributes.addFlashAttribute("type","addreflection");
        return "redirect:/dashboard";
    }

    @PostMapping("/add-reflection-direct")
    public String submitReflectionDirect(RedirectAttributes redirectAttributes, @ModelAttribute("reflection") Reflection reflection, Authentication authentication) {

        if(reflection.getParticipationID() == null) {
            throw new ValidationException("No valid assigned activity - cannot retrieve related activity and participation if no activity is present");
        }

        Long activityID = reflection.getParticipationID();
        Activity chosenActivity = new Activity();
        Long currentID = getCurrentID(authentication);
        List<Participation> participations = participationService.findAllParticipations();
        Participation chosenParticipation = new Participation();
        for (Participation currentPart : participations ) {
            if (currentPart.getUserID() == currentID) {
                if (currentPart.getActivityID() == activityID) {
                    chosenParticipation = currentPart;
                }
            }
        }

        reflection.setParticipationID(chosenParticipation.getParticipationID());
        reflection.setTagID(1L);
        reflectionService.saveReflection(reflection);
        redirectAttributes.addFlashAttribute("success",true);
        redirectAttributes.addFlashAttribute("type","addreflection");
        return "redirect:/dashboard";
    }

    //Return the user's reflections in a user-friendly format
    @GetMapping("/all-my-reflections")
    public String listMyReflections(Model model, Authentication authentication) {
        List<Reflection> reflections = reflectionService.findAllReflections();
//        List<Reflection> myReflections = new ArrayList<>();
        Long currentID = getCurrentID(authentication);

//        //Get a list of all participations the user can or has reflected on
//        List<Participation> participations = participationService.findAllParticipations();
//        List<Long> currentParticipations = new ArrayList<>();
//        for (int y = 0; y < participationService.getParticipationListSize(); y++) {
//            Participation currentPart = participations.get(y);
//            if(currentPart.getUserID() == currentID) {
//                currentParticipations.add(currentPart.getParticipationID());
//            }
//        }

        List<Participation> userParticipations = participationService.getParticipationsByUserId(currentID);
        List<Reflection> userReflections = reflectionService.findUserReflectionsById(currentID);



        List<ReflectList> formattedReflections = new ArrayList<>();

        //Make a list of all the participations unique to the current user
//        for (int z = 0; z < reflectionService.findAllReflections().size(); z++) {
//            Reflection reflection = reflections.get(z);
//            if(currentParticipations.contains(reflection.getParticipationID())) {
//                myReflections.add(reflection);
//            }
//        }


        //Return all reflections with activity and participation data in a user friendly format
        List<ReflectList> reflectLists = new ArrayList<>();
        for (Reflection currentReflection : userReflections) {
            Participation currentParticipation = new Participation();
            for (Participation participation : userParticipations) {
                if (currentReflection.getParticipationID() == participation.getParticipationID()) {
                    currentParticipation = participation;
                }
            }
            Activity currentActivity = participationService.getRelatedActivity(currentParticipation);

            String privacy;
            if (currentReflection.getIsPublic()) {
                privacy = "Public";
            } else {
                privacy = "Private";
            }

            String rating;
            if (currentReflection.getRating() == null) {
                rating = "No Rating";
            } else {
                rating = currentReflection.getRating().toString();
            }
            ReflectList currentReflectList = new ReflectList(
                    currentActivity.getName(),
                    currentParticipation.getDate(),
                    currentActivity.getIsOfficial(),
                    currentReflection.getReflect_what(),
                    currentReflection.getReflect_prompt(),
                    currentReflection.getReflect_happen(),
                    currentReflection.getReflect_eval(),
                    currentReflection.getReflect_diff(),
                    currentReflection.getReflect_lp(),
                    privacy,
                    rating
            );

            reflectLists.add(currentReflectList);
        }

        model.addAttribute("reflections", reflectLists);
        return "all-reflections";
    }

    //Get the current user's ID
    Long getCurrentID(Authentication authentication) {
        String currentUserName = ControllerSupport.getAuthenticatedUserName(authentication);
        Optional<SiteUser> currentUserOptional = siteUserService.findUserByUserName(currentUserName);
        SiteUser currentUser = currentUserOptional.get();
        Long currentUserID = currentUser.getUserID();

        return currentUserID;
    }

    List<Activity> reflectionSetup(Authentication authentication) {

        Long currentID = getCurrentID(authentication);


        return activityService.getAllParticipatedActivities(currentID);
    }

    //Lists all reflections
    @GetMapping("/all-public-reflections")
    public String listParticipations(Model model) {

        List<Reflection> reflections = reflectionService.findAllReflections();

        List<Participation> participations = participationService.findAllParticipations();

        //Return all reflections with activity and participation data in a user friendly format
        List<ReflectList> reflectLists = new ArrayList<>();

        for (Reflection reflection : reflections) {


            Participation currentParticipation = new Participation();

            if (reflection.getIsPublic()) {

                for (Participation participation : participations) {
                    if (reflection.getParticipationID().equals(participation.getParticipationID())) {
                        currentParticipation = participation;
                    }
                }
                Activity currentActivity = participationService.getRelatedActivity(currentParticipation);

                String privacy;
                if (reflection.getIsPublic()) {
                    privacy = "Public";
                } else {
                    privacy = "Private";
                }

                String rating;
                if (reflection.getRating() == null) {
                    rating = "No Rating";
                } else {
                    rating = reflection.getRating().toString();
                }
                ReflectList currentReflectList = new ReflectList(
                        currentActivity.getName(),
                        currentParticipation.getDate(),
                        currentActivity.getIsOfficial(),
                        reflection.getReflect_what(),
                        reflection.getReflect_prompt(),
                        reflection.getReflect_happen(),
                        reflection.getReflect_eval(),
                        reflection.getReflect_diff(),
                        reflection.getReflect_lp(),
                        privacy,
                        rating
                );

                reflectLists.add(currentReflectList);
            }
        }


        model.addAttribute("reflections", reflectLists);
        return "all-reflections-user";
    }
}
