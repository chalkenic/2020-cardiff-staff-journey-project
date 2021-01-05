package group03.project.repositories;

import group03.project.domain.Activity;
import group03.project.domain.Objective;
import group03.project.domain.Tag;
import group03.project.services.offered.ActivityService;
import group03.project.services.offered.ObjectiveService;
import group03.project.services.offered.TagService;
import group03.project.services.required.ActivityRepository;
import group03.project.services.required.ObjectiveRepository;
import group03.project.services.required.TagRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase
public class ActivityTest {

    @Autowired
    public ActivityRepository activityService;
    @Autowired
    public TagRepository tagService;
    @Autowired
    public ObjectiveRepository objectiveRepository;



    @Test
    @WithMockUser(username = "user", password = "pass", roles = "admin")
    public void shouldHaveObjectiveBeLinkedToActivity() throws Exception {

        Activity firstActivity = new Activity("activityTest", "an activity test");
        firstActivity.setIsOfficial(true);

        activityService.save(firstActivity);

        Objective newObj = new Objective(activityService.findByName("activityTest").get(), tagService.findByTagID(1L).get());
        objectiveRepository.save(newObj);

        Objective specifiedObjective = objectiveRepository
                .findByActivity_activityID(activityService.findByName("activityTest")
                .get()
                .getActivityID()).get(0);

        Long activityInObjective = specifiedObjective.getActivity().getActivityID();

//        Long activityInObjectives = objectiveRepository
//                .findByActivity_activityID(activityService.findByName("activityTest")
//                        .get()
//                        .getActivityID());

        Activity savedActivity = activityService.findByActivityID(activityInObjective).get();

        assertEquals("an activity test", savedActivity.getDescription());
    }

    @Test
    @WithMockUser(username = "user", password = "pass", roles = "USER")
    public void shouldAddCustomThoughtToActivity() throws Exception {

        Activity customActivity = new Activity("activityTest", "an activity test");

        activityService.save(customActivity);
        tagService.save(new Tag(null, "Motivational"));

        Activity savedActivity = activityService.findByName("activityTest").get();

        for (Tag tag: tagService.findAll()) {
            System.out.println(tag);
        }


        Objective newObj = new Objective(savedActivity, tagService.findByTagName("Motivational").get());

        objectiveRepository.save(newObj);

        Objective specifiedActObjective = objectiveRepository
                .findByActivity_activityID(activityService.findByName("activityTest")
                        .get()
                        .getActivityID()).get(0);

        Long activityInObjective = specifiedActObjective.getActivity().getActivityID();

        Objective specifiedTagObjective = objectiveRepository
                .findByTag_tagID(tagService.findByTagName("Motivational")
                        .get()
                        .getTagID()).get(0);

        Long tagInObjective = specifiedTagObjective.getTag().getTagID();

        Activity finalActivity = activityService.findByActivityID(activityInObjective).get();

        assertEquals("activityTest", activityService.findByActivityID(activityInObjective).get().getName());

        assertEquals("Motivational", tagService.findByTagID(tagInObjective).get().getTagName());

    }

}
