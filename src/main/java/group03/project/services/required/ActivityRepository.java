package group03.project.services.required;

import group03.project.domain.Activity;
import group03.project.domain.Participation;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository {

    List<Activity> findAll();

    Activity save(Activity theActivity);

    Optional<Activity> findByActivityID(Long id);

//    @Query(value = "select MAX(ActivityID) from activity", nativeQuery = true)

    Optional<Activity> findByDescription(Long id);

    Optional<Activity> findByName(String name);

    @Query(value = "select max(activityID) from activity", nativeQuery = true)
    public Long findLastActivityID();

    @Query(value = "select * from developmenttoolkit.activity where isOfficial = 1", nativeQuery = true)
    public List<Activity> findOfficialActivities();

    @Query(value = "select * from developmenttoolkit.activity where isOfficial = 0", nativeQuery = true)
    public List<Activity> findCustomActivities();

//    @Query(value = "select * from activity a where a.activityid in " +
//            "(select * from participation p where p.siteUser_userID =?1)", nativeQuery = true)
    @Query(value = "select a.* from activity as a inner join participation as p on a.activityID = p.Activity_activityID and p.siteUser_userID = ?1", nativeQuery = true)
    public List<Activity> findParticipatedActivitiesByUserId(Long id);



}
