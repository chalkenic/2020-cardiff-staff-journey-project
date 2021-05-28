package group03.project.services.required;

import group03.project.domain.Activity;
import group03.project.domain.Reflection;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReflectionRepository {

    List<Reflection> findAll();

    Reflection save(Reflection theReflection);

    Optional<Reflection> findByReflectionID(Long id);

    Optional<Reflection> findByParticipationID(Long id);

    @Query(value = "select * from ref where activityID = (SELECT Activity_activityID FROM objective WHERE objectiveID = ?1)", nativeQuery = true)
    public Activity findActivityByObjectiveID(Long id);

    @Query(value = "select r.* from reflection as r inner join participation as p on r.Participation_participationID = p.participationID and p.siteUser_userID = ?1", nativeQuery = true)
    public List<Reflection> findUserReflections(Long id);

}
