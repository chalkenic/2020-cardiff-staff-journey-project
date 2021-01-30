package group03.project.web;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Informing test which components are within the test's scope (what you're testing!)
 */
//@ContextConfiguration(classes = {PageController.class, InfoAdminController.class})
/**
 * Configures a mock mvc environment
 */

//@MockBean(SiteUserService.class)
//@MockBean(SiteUserAuditor.class)
//@MockBean(SiteUserJPAService.class)
//@MockBean(InfoAdminController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NavigationTesting {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser
    public void shouldLoadLoginPage() throws Exception {
        this.mvc
                .perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("login")));
    }
    @Test
    @WithMockUser
    public void shouldLoadAboutPage() throws Exception {
        this.mvc
                .perform(get("/about"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Professional Development Tracker")));
    }

    @Test
    @WithMockUser
    (username = "user", password = "password1", roles = "USER")
    public void shouldLoadDashboardPage() throws Exception {
        this.mvc
                .perform(get("/dashboard"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("View my")));
    }

    @Test
    @WithMockUser(username = "user", password = "password1", roles = "USER")
    public void shouldLoadAllPublicReflections() throws Exception {
        this.mvc
                .perform(get("/user/all-public-reflections"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("All Public Reflections")));
    }

    @Test
    @WithMockUser(username = "admin", password = "password1", roles = "ADMIN")
    public void shouldLoadAdminPages() throws Exception {
        this.mvc
                .perform(get("/admin/all-accounts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("All Users on Record")));

    }
}
