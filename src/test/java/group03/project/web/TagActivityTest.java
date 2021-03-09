package group03.project.web;

import group03.project.domain.Activity;
import group03.project.domain.Objective;
import group03.project.domain.Tag;
import group03.project.services.offered.ActivityService;
import group03.project.services.offered.ObjectiveService;
import group03.project.services.offered.TagService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class TagActivityTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ActivityService actService;

    @Autowired
    private TagService tagService;

    @Autowired
    private ObjectiveService objectiveService;

    @Test
    @WithMockUser(value = "user", password = "password", roles = "USER")
    @DisplayName("User is presented with custom thoughts on page open.")
    public void shouldHaveCustomThoughtsPresent() throws Exception {

        tagService.createCustomTag(new Tag(null, "Motivational", false));

        this.mvc
                .perform(get("/user/add-custom-activity"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Initial thoughts")))
                .andExpect(content().string(containsString("Motivational")));
    }

    @Test
    @WithMockUser(username="admin", password="pass", roles = "ADMIN")
    @DisplayName("Activity can be found within database via link stored with objective.")
    public void shouldFindCustomTagInDatabaseRelatingToObjective() throws Exception {

        tagService.createCustomTag(new Tag("Motivational", "is Motivational", false));
        Activity anActivity =  new Activity( "Example Name 1",  "Example Desc" );
        Activity theTestActivity =  new Activity( "Test Activity",  "A better activity" );
        Objective relatedObjective = new Objective(theTestActivity, tagService.findATagByName("Motivational").get());
        /*
        Save all objects to database.
         */
        actService.saveActivity(anActivity);
        actService.saveActivity(theTestActivity);
        objectiveService.createObjective(relatedObjective);

        /*
        Assertion confirms link in database to tag object stored inside objective
         */

        assertEquals("Motivational", tagService.findATagByName(relatedObjective.getTag().getTagName()).get().getTagName());
        /*
        Assertions confirms link in database by calling database for specific tag and matching related Tag's name against expected.
         */
        assertEquals("Motivational", tagService.findATagByName
                (objectiveService.findObjectivesByTagID(tagService.findATagByName("Motivational").get().getTagID()).get(0).getTag().getTagName()).get().getTagName());

                /*
        Assertion confirms link in database to Activity object stored inside objective
         */
        assertEquals(2L, actService.findActivitiesByID(relatedObjective.getActivity().getActivityID()).get().getActivityID());
        /*
        Assertion confirms first activity saved does not have the created objective link.
         */
        assertNotEquals(1L, actService.findActivitiesByID(relatedObjective.getActivity().getActivityID()).get().getActivityID());
    }

}
