package group03.project.web.controllers.admin;

import group03.project.domain.*;
import group03.project.services.offered.*;
import group03.project.web.forms.ActivityCreationForm;
import group03.project.web.forms.ActivityJoinForm;
import group03.project.web.lists.ActivityRating;
import group03.project.web.lists.ReflectList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin")
public class ActivityAdminController {

    private ActivityService activityService;

    private TagService tagService;

    private ObjectiveService objService;

    private ParticipationService participationService;

    private ReflectionService reflectionService;

    private SiteUserService siteUserService;

    @Autowired
    public ActivityAdminController(ActivityService aService, TagService tService, ObjectiveService oService,
                                   ParticipationService pService, ReflectionService rService,
                                   SiteUserService sService) {
        activityService = aService;
        tagService = tService;
        objService = oService;
        participationService = pService;
        reflectionService = rService;
        siteUserService = sService;
    }



    //Page for adding an official activity as an administrator
    @GetMapping("/add-official-activity")
    public String addOfficialActivity(Model model) {
        ActivityCreationForm activity = new ActivityCreationForm();
        List<Tag> allTags = tagService.findTagsIfOfficial();
        List<Tag> allThoughts = tagService.findTagsIfCustom();
        model.addAttribute("activity", activity);
        model.addAttribute("tags", allTags);
        model.addAttribute("thoughts", allThoughts);
        return "add-oactivity";
    }
    //Submit the activity to the database
    @PostMapping("/add-official-activity")
    public String submitOfficialActivity(@ModelAttribute("activity") @Valid ActivityCreationForm activity,
                                         BindingResult result) {

        activity.allOfficialTags();


            Activity latestActivity = createActivity(activity, result);

            String[] customTags = new String[0];

            if(activity.getCustomTags() != null) {
                customTags = activity.getCustomTags().split(",");
            }

            for (Map.Entry<String, Boolean> tag : activity.allOfficialTags().entrySet()) {
                if (tag.getValue()) {
                    Tag theTag = tagService.findATagByName(tag.getKey()).get();
                    Objective newObj = new Objective(latestActivity, theTag);
                    objService.createObjective(newObj);

                }
             }
            if (customTags != null) {
                for (String customTag : customTags) {
                    Tag theTag = tagService.findATagByID(Long.valueOf(customTag)).get();
                    Objective newObj = new Objective(latestActivity, theTag);
                    objService.createObjective(newObj);

                }
            }
        return "redirect:/dashboard";
    }

    //List all activities the user can add themselves too
    @GetMapping("/activities-signup-list")
    public String listActivities(Model model, Authentication authentication) {
        List<Activity> activities = activityService.findAllActivities();
        List<Participation> participations = participationService.findAllParticipations();
        List<Reflection> reflections = reflectionService.findAllReflections();
        List<Participation> allParticipations = participationService.findAllParticipations();
        List<SiteUser> allUsers = siteUserService.findAllUsers();

        List<Double> tempPartCount = new ArrayList<>();
        List<Double> participationPercentage = new ArrayList<>();
        List<Integer> participationCount = new ArrayList<>();


        for (Activity activity : activities) {
            Double partCounter = 0.0;
            DecimalFormat df = new DecimalFormat("0");

            for (Participation participation : allParticipations) {
                if (participation.getActivityID().equals(activity.getActivityID())) {
                    partCounter++;

                }

            }
            tempPartCount.add(partCounter);
        }

        System.out.println(allUsers.size());

        DecimalFormat dformat = new DecimalFormat("#.##");
        dformat.setRoundingMode(RoundingMode.UP);

        for (Double  part : tempPartCount) {
            Double percentage = (part / allUsers.size()) * 100;
            participationPercentage.add(Double.valueOf(dformat.format(percentage)));
        }

        List<Activity> officialActivities = new ArrayList<>();
        List<Activity> customActivities = new ArrayList<>();
        //Make sure the user can only sign up for official activities they are not already doing
        for (int x = 0; x < activityService.getActivityListSize(); x++) {
            Activity currentActivity = activities.get(x);
            if(currentActivity.getIsOfficial()) {
                    officialActivities.add(currentActivity);

            }
        }

        //Make sure the user can only sign up for custom activities they are not already doing
        for (int x = 0; x < activityService.getActivityListSize(); x++) {
            Activity currentActivity = activities.get(x);
            if(!currentActivity.getIsOfficial()) {
                    customActivities.add(currentActivity
                    );
                }
            }


        List<ActivityRating> activityRatings = new ArrayList<>();
        List<Long> activityIDList = new ArrayList<>();
        List<Long> participationIDList = new ArrayList<>();

        for(int i = 0; i < activities.size(); i++) {

            Activity currentActivity = activities.get(i);
            ActivityRating currentRating = new ActivityRating();
            currentRating.setActivityID(currentActivity.getActivityID());

            for(int x = 0; x < participations.size(); x++) {

                Participation currentParticipation = participations.get(x);

                if(currentParticipation.getActivityID() == currentActivity.getActivityID()) {

                    currentRating.setParticipationID(currentParticipation.getParticipationID());

                    for(int y = 0; y < reflections.size(); y++) {

                        Reflection currentReflection = reflections.get(y);

                        if(currentReflection.getParticipationID() == currentParticipation.getParticipationID()) {
                            Integer oldValue = currentRating.getRatingavg();
                            Integer newValue;
                            if(currentReflection.getRating() != null) {
                                if(oldValue == null) {
                                    newValue = currentReflection.getRating().intValue();
                                } else {
                                    newValue = (oldValue + currentReflection.getRating().intValue()) / 2;
                                }
                                currentRating.setRatingavg(newValue);
                                activityRatings.add(currentRating);
                            }
                        }
                    }
                }
            }
        }

        System.out.println(activityRatings);

        for (Double value : tempPartCount) {
            participationCount.add(value.intValue());
        }

        ActivityJoinForm editForm = new ActivityJoinForm();
        model.addAttribute("officialActivities", officialActivities);
        model.addAttribute("customActivities", customActivities);
        model.addAttribute("totalParticipants", participationCount);
        model.addAttribute("partPercentages", participationPercentage);
        model.addAttribute("allActivities", activities);
        model.addAttribute("ratings", activityRatings);
        return "admin-all-activities";
    }


    //Lists all reflections
    @GetMapping("/all-public-reflections")
    public String listParticipations(Model model) {

        List<Reflection> reflections = reflectionService.findAllReflections();

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
        return "all-reflections-admin";
    }

    private Activity createActivity(ActivityCreationForm activityForm,
                                    BindingResult result) {
        Activity newActivity;

        try {
            newActivity = new Activity(
                    activityForm.getName(),
                    activityForm.getDescription());

            newActivity.setIsOfficial(true);

            activityService.saveActivity(newActivity);

        } catch (Exception ex) {
            return null;
        }
        return newActivity;
//        return activityService.findMostRecentActivity();
    }
}
