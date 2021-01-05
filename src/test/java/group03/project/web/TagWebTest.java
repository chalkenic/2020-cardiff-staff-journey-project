package group03.project.web;


import group03.project.TestSupport;
import group03.project.config.LoginDetailsService;
import group03.project.domain.Tag;
import group03.project.services.required.TagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class TagWebTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TagRepository repository;

    @MockBean
    private LoginDetailsService loginService;

    @Test
    @DisplayName("Admin is presented all tags from hardcoded datasource")
    @WithMockUser(username="user", password = "password1", roles = "ADMIN")
    public void shouldPresentAdminWithAllTags() throws Exception {

        mvc.perform(get("/user/all-tags"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("A1")))
                .andExpect(content().string(containsString("Develop effective learning environments and approaches to student support and guidance")));
    }

    @Test
    @DisplayName("User is presented custom tags from hardcoded datasource")
    @WithMockUser(username="user", password = "password1", roles = "ADMIN")
    public void shouldPresentUserWithCustomTags() throws Exception {

        repository.save(new Tag(null, "custom tag", "a custom test tag", false));

        mvc.perform(get("/user/all-tags"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("custom tag")))
                .andExpect(content().string(containsString("a custom test tag")));
    }

    @Test
    @DisplayName("User adds a custom tag, and can see it on page")
    @WithMockUser(username="user", password = "password1", roles = "USER")
    public void shouldSeeCustomTagOnPageWhenCreated() throws Exception {

        repository.save(new Tag(null, "test", "tester tag", false));

        mvc.perform(get("/user/all-tags"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test")))
                .andExpect(content().string(containsString("tester tag")));
    }

    @Test
    @WithMockUser(username="user", password = "password1", roles = "USER")
    public void shouldNotSeeDeleteButtonAsUser() throws Exception {

        repository.save(new Tag(null, "test", "tester tag", false));

        mvc.perform(get("/user/all-tags"))
                .andExpect(status().isOk())
                .andExpect(content().string(TestSupport.doesNotContainString("Remove")));

    }

    @Test
    @WithMockUser(username="user", password = "password1", roles = "ADMIN")
    public void shouldSeeDeleteButtonAsAdmin() throws Exception {

        repository.save(new Tag(null, "test", "tester tag", false));

        mvc.perform(get("/admin/all-tags"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Remove Tag")));

    }
    
    @Test
    @Transactional
    @WithMockUser(username="user", password = "password1", roles = "ADMIN")
    public void shouldCreateAViewableCustomTagAndDelete() throws Exception {

        Tag newTag = new Tag(1L, "a new tag test", "tester tag", false);

        repository.save(newTag);

        mvc.perform(get("/admin/all-tags"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("a new tag test")));

        repository.deleteByTagID(1L);

        mvc.perform(get("/user/all-tags"))
                .andExpect(status().isOk())
                .andExpect(content().string(TestSupport.doesNotContainString("a new tag test")));

    }


}
