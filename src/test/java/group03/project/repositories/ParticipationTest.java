package group03.project.repositories;

import group03.project.domain.Activity;
import group03.project.domain.Participation;
import group03.project.services.required.ActivityRepository;
import group03.project.services.required.ParticipationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest
@ContextConfiguration
public class ParticipationTest {

    @Autowired
    ParticipationRepository participationRepository;

    @Autowired
    ActivityRepository activityRepository;


//    @Autowired
//    ParticipationService participationService;
//
//    @Autowired
//    ActivityService activityService;

    @Test
    @WithMockUser(username="admin", password="pass", roles = "ADMIN")
    @DisplayName("User can assign themselves to an activity.")
    public void addParticipationForOActivity() {

        Activity testActivity = new Activity(null, "Test Activity", "Test Url", "Test Desc", true);

        activityRepository.save(testActivity);

        java.util.Date date = new java.util.Date();

        Participation testParticipation = new Participation(null, testActivity.getActivityID(), date, "Participant",  1L );

        participationRepository.save(testParticipation);

        Optional<Participation> findTest = participationRepository.findByParticipationID(testParticipation.getParticipationID());

        assertEquals(date, findTest.get().getDate());
    }

    @Test
    @WithMockUser(username="admin", password="pass", roles = "ADMIN")
    @DisplayName("User creates a new participation within database when assigning themselves to an activity.")
    public void generateParticipationID() {

        Activity testActivity = new Activity(null, "Test Activity", "Test Url", "Test Desc", true);
        activityRepository.save(testActivity);

        java.util.Date date = new java.util.Date();
        Participation testParticipation = new Participation(null, testActivity.getActivityID(), date, "Participant",  1L );
        participationRepository.save(testParticipation);

        Optional<Participation> findTest = participationRepository.findByParticipationID(testParticipation.getParticipationID());

        assertNotEquals(null, findTest.get().getParticipationID());
    }
}
