package group03.project.web.controllers.user;

import group03.project.domain.*;
import group03.project.services.implementation.ActivityServiceImpl;
import group03.project.services.implementation.ParticipationServiceImpl;
import group03.project.services.offered.*;
import group03.project.web.controllers.ControllerSupport;
import group03.project.web.forms.ReflectionButtonForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Controller
@RequestMapping("user")
public class ParticipationController {

    private final ParticipationService participationService;
    private final ActivityService activityService;
    private final SiteUserService siteUserService;
    private final ObjectiveService objectiveService;
    private final TagService tagService;

    @Autowired
    public ParticipationController(ActivityService AnActivityService, TagService theTagService,
                                   ParticipationService aParticipationService,
                                   SiteUserService aSiteUserService, ObjectiveService theObjectiveService) {
        activityService = AnActivityService;
        participationService = aParticipationService;
        siteUserService = aSiteUserService;
        objectiveService = theObjectiveService;
        tagService = theTagService;
    }

    //Lists all participations
    @GetMapping("/all-participations")
    public String listParticipations(Model model) {
        List<Participation> participations = participationService.findAllParticipations();
        model.addAttribute("participations", participations);
        return "all-participations";
    }

    //Return the user's participations
    @GetMapping("/all-my-participations")
    public String listMyParticipations(Model model, Authentication authentication) {
        
        Long currentID = getCurrentID(authentication);

        List<Activity>participatedActivities = activityService.getAllParticipatedActivities(currentID);
        List<Participation> userParticipations = participationService.getParticipationsByUserId(currentID);
        List<Tag[]> allTags = new ArrayList<>();
        List<Tag[]> allThoughts = new ArrayList<>();

        for (Activity activity : participatedActivities) {
            List<Objective> objectives = objectiveService.findObjectivesByActivityID(activity.getActivityID());
            Tag[] tags = objectives.stream().filter(x -> x.getTag().getIsOfficial()).map(Objective::getTag).toArray(size -> new Tag[objectives.size()]);
            Tag[] thoughts = objectives.stream().filter(x -> !x.getTag().getIsOfficial()).map(Objective::getTag).toArray(size -> new Tag[objectives.size()]);
            allTags.add(tags);
            allThoughts.add(thoughts);
        }
        model.addAttribute("activities", participatedActivities);
        model.addAttribute("participations", userParticipations);
        model.addAttribute("tags", allTags);
        model.addAttribute("thoughts", allThoughts);


        return "all-participations";
    }

    @PostMapping("/all-my-participations")
    public String addReflectionButton(@ModelAttribute("participation") @Valid ReflectionButtonForm editForm, Model model) {
        Reflection reflection = new Reflection();
        reflection.setParticipationID(Long.parseLong(editForm.getParticipationReflectID()));
        model.addAttribute("reflection", reflection);
        return "add-reflection-direct";
    }

    //Get the current user's ID
    Long getCurrentID(Authentication authentication) {
        String currentUserName = ControllerSupport.getAuthenticatedUserName(authentication);
        Optional<SiteUser> currentUserOptional = siteUserService.findUserByUserName(currentUserName);
        SiteUser currentUser = currentUserOptional.get();
        Long currentUserID = currentUser.getUserID();

        return currentUserID;
    }
}
