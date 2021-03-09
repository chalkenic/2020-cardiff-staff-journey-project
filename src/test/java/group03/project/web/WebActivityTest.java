package group03.project.web;


import group03.project.domain.Activity;
import group03.project.services.offered.ActivityService;
import group03.project.services.required.ActivityRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class WebActivityTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ActivityRepository activityRepository;

    @Test
    @WithMockUser(username = "admin", password = "password1", roles = "ADMIN")
    @DisplayName("User with admin credentials can access official activity creation page.")
    public void shouldLoadAddOfficialActivityPage() throws Exception {

        this.mvc
                .perform(get("/admin/add-official-activity"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Official Activity Registration")));
    }

    @Test
    @DisplayName("User with user credentials can access official activity creation page.")
    @WithMockUser(username = "user", password = "password1", roles = "USER")
    public void shouldLoadAddCustomActivityPage() throws Exception {

        this.mvc
                .perform(get("/user/add-custom-activity"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Custom Activity Registration")));
    }

    @Test
    @WithMockUser(username = "user", password = "password1", roles = "USER")
    @DisplayName("Create 2 activities via API method call & page form addition.")
    public void shouldAddActivity() throws Exception {

        List<Activity> activities = new ArrayList<>(Arrays.asList(
                new Activity(null, "Example Name 1", "Example File", "Example Desc", false),
                new Activity(null, "Example Name 2", "Example File", "Example Desc", false)
        ));

        //Adding via service without errors
        activityRepository.save(activities.get(0));

        //Adding via post request without errors
        MvcResult result = mvc.perform(get("/user/add-custom-activity")
                .contentType(APPLICATION_FORM_URLENCODED) //from MediaType
                .param("name", activities.get(0).getName())
                .param("file", activities.get(0).getFile())
                .param("description", activities.get(0).getDescription()))
                .andExpect(status().isOk())
                .andReturn();
    }

}
