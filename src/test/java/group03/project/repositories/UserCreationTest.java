package group03.project.repositories;

import group03.project.domain.SiteUser;
import group03.project.services.required.SiteUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class UserCreationTest {

    @Autowired
    public SiteUserRepository siteUserRepository;

    @Test
    @DisplayName("Additional users can be created, and are visible on search.")
    public void addTwoAdditionalTestUsersAndCountTotal() throws Exception {

        SiteUser greg = new SiteUser("greg@hotmail.co.uk", "password", "Greg");
        SiteUser tammy = new SiteUser("tammster@outlook.com", "123pass5", "Tammy");

        siteUserRepository.save(greg);
        siteUserRepository.save(tammy);

        List<SiteUser> allUsers = siteUserRepository.findAll();

        assertEquals(6, allUsers.size());
    }

    @Test
    @DisplayName("Created users are searchable via email address.")
    public void findNewCreatedUserInDatabaseByEmailAndId() throws Exception {

        SiteUser andrew = new SiteUser("andrew@gmail.co.uk", "andypandy", "Andy");

        siteUserRepository.save(andrew);

        Optional<SiteUser> userId = siteUserRepository.findById(5L);
        SiteUser convertedAndrew = userId.get();

        assertEquals("andrew@gmail.co.uk", convertedAndrew.getEmailAddress());
    }
}
