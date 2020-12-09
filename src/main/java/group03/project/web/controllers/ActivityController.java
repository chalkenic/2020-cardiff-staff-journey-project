package group03.project.web.controllers;

import group03.project.domain.Participation;
import group03.project.domain.SiteUser;
import group03.project.services.implementation.ActivityService;
import group03.project.domain.Activity;
import group03.project.services.implementation.ParticipationService;
import group03.project.services.offered.SiteUserService;
import group03.project.services.offered.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("user")
public class ActivityController {

    String pageChoice = "user";

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ParticipationService participationService;

    @Autowired
    private SiteUserService siteUserService;

    //Page for adding an official activity as an administrator
    @GetMapping("/add_official_activity")
    public String addOfficialActivity(Model model) {
        Activity activity = new Activity();
        //activity.setUserID(1);
        model.addAttribute("activity", activity);
        return "Add_OActivity";
    }

    //Submit the activity to the database
    @PostMapping("/add_official_activity")
    public String submitOfficialActivity(@ModelAttribute("activity") Activity activity) {
        //activity.setActivityID(activityService.getActivityListSize());
        //activity.setUserID(1); //No login system yet - placeholder userID
        activity.setIsOfficial(true);
        activityService.save(activity);
        return "redirect:";
    }

    //Page for adding a custom activity as a user
    @GetMapping("/add_custom_activity")
    public String addCustomActivity(Model model) {
        Activity activity = new Activity();
        //activity.setUserID(1);
        model.addAttribute("activity", activity);
        return "Add_CActivity";
    }

    //Submit the activity to the database
    @PostMapping("/add_custom_activity")
    public String submitCustomActivity(@ModelAttribute("activity") Activity activity, Authentication authentication) {
        //activity.setActivityID(activityService.getActivityListSize());
        //activity.setUserID(1); //No login system yet - placeholder userID
        String inputName = activity.getName();
        activity.setName("[Custom] " + inputName);
        activity.setIsOfficial(false);

        activityService.save(activity);

        java.util.Date date = new java.util.Date();

        String currentUserName = ControllerSupport.getAuthenticatedUserName(authentication);
        Optional<SiteUser> currentUserOptional = siteUserService.findUserByUserName(currentUserName);
        SiteUser currentUser = currentUserOptional.get();
        Long currentUserID = currentUser.getUserID();
        Integer currentUserIDInt = currentUserID.intValue();

        Participation participation = new Participation(null, date, activity.getActivityID(), currentUserIDInt, "Participant");
        participationService.save(participation);
        return "redirect:";
    }

    @GetMapping("/all_activities")
    public String listActivities(Model model) {
        List<Activity> activities = activityService.findall();
        List<Activity> officialActivities = new ArrayList<>();
        for (int x = 0; x < activityService.getActivityListSize(); x++) {
            Activity currentActivity = activities.get(x);
            if(currentActivity.getIsOfficial() == true) {
                officialActivities.add(currentActivity);
            }
        }
        model.addAttribute("activities", officialActivities);
        return "all-activities";
    }
}
