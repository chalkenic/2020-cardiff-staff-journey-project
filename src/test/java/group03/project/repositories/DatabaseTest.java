package group03.project.repositories;

import group03.project.domain.Role;
import group03.project.domain.SiteUser;
import group03.project.services.required.RoleRepository;
import group03.project.services.required.SiteUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ContextConfiguration
public class DatabaseTest {

    @Autowired
    private SiteUserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    @DisplayName("User can check user count.")
    public void shouldReturnFourUsers() throws Exception {

        List<SiteUser> siteUsers = userRepository.findAll();

        assertEquals(4, siteUsers.size());
    }

    @Test
    @DisplayName("User can check a site user's role in an activity.")
    public void shouldReturnParticipantDescription() throws Exception {

        Role role = roleRepository.findById("Participant").get();
        assertEquals("participating in activity", role.getDescription());
    }
}
