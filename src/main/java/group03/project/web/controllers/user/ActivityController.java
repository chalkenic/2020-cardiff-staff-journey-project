package group03.project.web.controllers.user;

import group03.project.domain.*;
import group03.project.services.offered.*;
import group03.project.web.controllers.ControllerSupport;
import group03.project.web.forms.ActivityJoinForm;
import group03.project.web.forms.ActivityCreationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("user")
public class ActivityController {

    private ActivityService activityService;
    private ParticipationService participationService;
    private SiteUserService siteUserService;
    private TagService tagService;
    private ObjectiveService objService;

    @Autowired
    public ActivityController(ActivityService actService, ParticipationService partService,
                              SiteUserService userService, TagService theTagService,
                              ObjectiveService objectiveService) {
        activityService = actService;
        participationService = partService;
        siteUserService = userService;
        tagService = theTagService;
        objService = objectiveService;
    }

    //Page for adding a custom activity as a user
    @GetMapping("/add-custom-activity")
    public String addCustomActivity(Model model) {
        ActivityCreationForm activity = new ActivityCreationForm();
//        Activity activity = new Activity();
        List<Tag> allTags = tagService.findTagsIfCustom();
        model.addAttribute("activity", activity);
        model.addAttribute("tags", allTags);
        return "add-cactivity";
    }

    //Submit the activity to the database
    @PostMapping("/add-custom-activity")
    public String submitCustomActivity(RedirectAttributes redirectAttributes, @ModelAttribute("activity")
            @Valid ActivityCreationForm activity, Authentication authentication, BindingResult result) {
        /*
        Creates activity via basic information parsed from form.
         */
        Activity newCustomActivity = createActivity(activity, result);


        String[] customTags = new String[0];

        if(activity.getCustomTags() != null) {
            customTags = activity.getCustomTags().split(",");
        }

            if (customTags.length > 0) {
                for (String customTag : customTags) {
                    Tag theTag = tagService.findATagByID(Long.valueOf(customTag)).get();
                    Objective newObj = new Objective(newCustomActivity, theTag);
                    objService.createObjective(newObj);
                }
            }
        /*
        Date must be entered into subsequent participation addition.
         */
        java.util.Date date = new java.util.Date();
        /*
        Source user who created activity from authentication.
         */
        Long currentUserID = getCurrentID(authentication);
        /*
        Create participation from data created prior.
         */
        Participation participation = new Participation(null, activityService.findMostRecentActivity(), date, "Participant", currentUserID );
        /*
        Add participation to database.
         */
        if(participation.getActivityID() == null) {
            throw new ValidationException("Activity ID can't be null");
        }
        participationService.createParticipation(participation);
        redirectAttributes.addFlashAttribute("success",true);
        redirectAttributes.addFlashAttribute("type","cactivity");
        return "redirect:/dashboard";
    }

    //List all activities the user can add themselves too
    @GetMapping("/activities-signup-list")
    public String listActivities(Model model, Authentication authentication) {
        List<Activity> activities = activityService.findAllActivities();
        List<Participation> participations = participationService.findAllParticipations();
        Long currentID = getCurrentID(authentication);
        //Get a list of all activities the user is currently participating in
        List<Long> currentActivitiesIDs = new ArrayList<>();
        for (int y = 0; y < participationService.getParticipationListSize(); y++) {
            Participation currentPart = participations.get(y);
            if(currentPart.getUserID() == currentID) {
                currentActivitiesIDs.add(currentPart.getActivityID());
            }
        }

        List<Activity> officialActivities = new ArrayList<>();
        List<Activity> customActivities = new ArrayList<>();
        //Make sure the user can only sign up for official activities they are not already doing
        for (int x = 0; x < activityService.getActivityListSize(); x++) {
            Activity currentActivity = activities.get(x);
            if(currentActivity.getIsOfficial()) {
                if(!currentActivitiesIDs.contains(currentActivity.getActivityID())) {
                    officialActivities.add(currentActivity);
                }
            }
        }

        //Make sure the user can only sign up for custom activities they are not already doing
        for (int x = 0; x < activityService.getActivityListSize(); x++) {
            Activity currentActivity = activities.get(x);
            if(!currentActivity.getIsOfficial()) {
                if(!currentActivitiesIDs.contains(currentActivity.getActivityID())) {
                    customActivities.add(currentActivity);
                }
            }
        }

        ActivityJoinForm editForm = new ActivityJoinForm();
        model.addAttribute("editForm", editForm);
        model.addAttribute("officialActivities", officialActivities);
        model.addAttribute("customActivities", customActivities);
        return "all-activities";
    }
    //Add a participation for the official activity the user has just signed up to
    @PostMapping("/activities-signup-list")
    public String joinActivity(@ModelAttribute("activity") @Valid ActivityJoinForm editForm, Authentication authentication) {
        if(editForm.getActivityJoinID() == null) {
            throw new ValidationException("Activity ID can't be null");
        }
        java.util.Date date = new java.util.Date();
        Long currentUserID = getCurrentID(authentication);
        Participation participation = new Participation(null,  Long.parseLong(editForm.getActivityJoinID()),date, "Participant", currentUserID);
        participationService.createParticipation(participation);
        return "redirect:/dashboard";
    }

    //Get the current user's ID
    Long getCurrentID(Authentication authentication) {
        String currentUserName = ControllerSupport.getAuthenticatedUserName(authentication);
        Optional<SiteUser> currentUserOptional = siteUserService.findUserByUserName(currentUserName);
        SiteUser currentUser = currentUserOptional.get();
        Long currentUserID = currentUser.getUserID();
//        Integer currentUserIDInt = currentUserID.intValue();
        return currentUserID;
    }

    private Activity createActivity(ActivityCreationForm activityForm,
                                    BindingResult result) {
        Activity newActivity;

        try {
            newActivity = new Activity(
                    activityForm.getName(),
                    activityForm.getDescription());

            activityService.saveActivity(newActivity);

        } catch (Exception ex) {
            return null;
        }
        return newActivity;
    }

}
