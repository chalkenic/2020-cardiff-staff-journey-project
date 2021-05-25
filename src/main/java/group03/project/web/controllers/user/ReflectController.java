package group03.project.web.controllers.user;

import group03.project.domain.Activity;
import group03.project.domain.Participation;
import group03.project.domain.Reflection;
import group03.project.domain.SiteUser;
import group03.project.services.implementation.ActivityServiceImpl;
import group03.project.services.implementation.ParticipationServiceImpl;
import group03.project.services.implementation.ReflectionServiceImpl;
import group03.project.services.offered.ActivityService;
import group03.project.services.offered.SiteUserService;
import group03.project.web.controllers.ControllerSupport;
import group03.project.web.lists.ReflectList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("user")
public class ReflectController {

    @Autowired
    private ParticipationServiceImpl participationService;

    @Autowired
    private ReflectionServiceImpl reflectionServiceImpl;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private SiteUserService siteUserService;

    //Add a reflection
    @GetMapping("/add-reflection")
    public String addReflection(Model model) {
        Reflection reflection = new Reflection();
        model.addAttribute("reflection", reflection);

        List<Activity> possibleActivities = reflectionSetup();

        model.addAttribute("activities", possibleActivities);
        return "add-reflection";
    }

    @GetMapping("/add-reflection-direct")
    public String addReflectionDirect (Model model) {
        Reflection reflection = new Reflection();
        model.addAttribute("reflection", reflection);

        List<Activity> possibleActivities = reflectionSetup();

        model.addAttribute("activities", possibleActivities);
        return "add-reflection-direct";
    }

    //Submit the entry to the database
    @PostMapping("/add-reflection")
    public String submitReflection(RedirectAttributes redirectAttributes, @ModelAttribute("reflection") Reflection reflection) {

        if(reflection.getParticipationID() == null) {
            reflection.setParticipationID(participationService.getAllParticipations().get(1).getParticipationID());
        }

        System.out.println(reflection.getParticipationID());
        Long activityID = reflection.getParticipationID();
        Activity chosenActivity = new Activity();
        Long currentID = ControllerSupport.getUserName();
        List<Participation> participations = participationService.findAllParticipations();
        Participation chosenParticipation = new Participation();
        for (int x = 0; x < participationService.getParticipationListSize(); x++) {
            Participation currentPart = participations.get(x);
            if(currentPart.getUserID() == currentID) {
                if(currentPart.getActivityID() == activityID) {
                    chosenParticipation = currentPart;
                }
            }
        }

        reflection.setParticipationID(chosenParticipation.getParticipationID());
        reflection.setTagID(1L); //Placeholder until we assign tags to activities
        reflectionServiceImpl.saveReflection(reflection);
        redirectAttributes.addFlashAttribute("success",true);
        redirectAttributes.addFlashAttribute("type","addreflection");
        return "redirect:/dashboard";
    }

    @PostMapping("/add-reflection-direct")
    public String submitReflectionDirect(RedirectAttributes redirectAttributes, @ModelAttribute("reflection") Reflection reflection) {

        if(reflection.getParticipationID() == null) {
            reflection.setParticipationID(participationService.getAllParticipations().get(1).getParticipationID());
        }

        Long activityID = reflection.getParticipationID();
        Activity chosenActivity = new Activity();
        Long currentID = ControllerSupport.getUserName();
        List<Participation> participations = participationService.findAllParticipations();
        Participation chosenParticipation = new Participation();
        for (int x = 0; x < participationService.getParticipationListSize(); x++) {
            Participation currentPart = participations.get(x);
            if (currentPart.getUserID() == currentID) {
                if (currentPart.getActivityID() == activityID) {
                    chosenParticipation = currentPart;
                }
            }
        }

        reflection.setParticipationID(chosenParticipation.getParticipationID());
        reflection.setTagID(1L);
        reflectionServiceImpl.saveReflection(reflection);
        redirectAttributes.addFlashAttribute("success",true);
        redirectAttributes.addFlashAttribute("type","addreflection");
        return "redirect:/dashboard";
    }

    //Return the user's reflections in a user-friendly format
    @GetMapping("/all-my-reflections")
    public String listMyReflections(Model model) {
        List<Reflection> reflections = reflectionServiceImpl.findAllReflections();
        List<Reflection> myReflections = new ArrayList<>();
        Long currentID = ControllerSupport.getUserName();

        //Get a list of all participations the user can or has reflected on
        List<Participation> participations = participationService.findAllParticipations();
        List<Long> currentParticipations = new ArrayList<>();
        for (int y = 0; y < participationService.getParticipationListSize(); y++) {
            Participation currentPart = participations.get(y);
            if(currentPart.getUserID() == currentID) {
                currentParticipations.add(currentPart.getParticipationID());
            }
        }

        List<ReflectList> formattedReflections = new ArrayList<>();

        //Make a list of all the participations unique to the current user
        for (int z = 0; z < reflectionServiceImpl.findAllReflections().size(); z++) {
            Reflection reflection = reflections.get(z);
            if(currentParticipations.contains(reflection.getParticipationID())) {
                myReflections.add(reflection);
            }
        }


        //Return all reflections with activity and participation data in a user friendly format
        List<ReflectList> reflectLists = new ArrayList<>();
        for (int x = 0; x < myReflections.size(); x++) {
            Reflection currentReflection = myReflections.get(x);
            Participation currentParticipation = new Participation();
            for (int y = 0; y < currentParticipations.size(); y++) {
                Participation participation = participations.get(y);
                if(currentReflection.getParticipationID() == participation.getParticipationID()) {
                    currentParticipation = participation;
                }
            }
            Activity currentActivity = participationService.getRelatedActivity(currentParticipation);

            String privacy;
            if(currentReflection.getIsPublic() == true){
                privacy = "Public";
            } else{
                privacy = "Private";
            }

            String rating;
            if(currentReflection.getRating() == null){
                rating = "No Rating";
            } else{
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


    List<Activity> reflectionSetup() {

        List<Activity> activities = activityService.findAllActivities();
        List<Participation> participations = participationService.findAllParticipations();
        Long currentID = (ControllerSupport.getUserName());
        //Get a list of all activities the user is currently participating in
        List<Long> currentActivitiesIDs = new ArrayList<>();
        for (int x = 0; x < participationService.getParticipationListSize(); x++) {
            Participation currentPart = participations.get(x);
            if (currentPart.getUserID() == currentID) {
                currentActivitiesIDs.add(currentPart.getActivityID());
            }
        }
        //Make sure the user can only choose official activities
        List<Activity> possibleActivities = new ArrayList<>();
        for (int x = 0; x < activityService.getActivityListSize(); x++) {
            Activity currentActivity = activities.get(x);
            if (currentActivitiesIDs.contains(currentActivity.getActivityID()) == true) {
                possibleActivities.add(currentActivity);
            }
        }

        return possibleActivities;
    }

    //Lists all reflections
    @GetMapping("/all-public-reflections")
    public String listParticipations(Model model) {

        List<Reflection> reflections = reflectionServiceImpl.findAllReflections();

        List<Participation> participations = participationService.findAllParticipations();

        //Return all reflections with activity and participation data in a user friendly format
        List<ReflectList> reflectLists = new ArrayList<>();
        for (int x = 0; x < reflections.size(); x++) {

            Reflection currentReflection = reflections.get(x);
            Participation currentParticipation = new Participation();

            if(currentReflection.getIsPublic()) {

                for (int z = 0; z < participations.size(); z++) {
                    Participation participation = participations.get(z);
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
                if(currentReflection.getRating() == null){
                    rating = "No Rating";
                } else{
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
        }

        model.addAttribute("reflections", reflectLists);
        return "all-reflections-user";
    }
}
