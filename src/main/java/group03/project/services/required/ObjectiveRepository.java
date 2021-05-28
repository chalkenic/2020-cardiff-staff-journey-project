package group03.project.services.required;

import group03.project.domain.Activity;
import group03.project.domain.Objective;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ObjectiveRepository {

    List<Objective> findAll();

    List<Objective> findByActivity_activityID(Long ActivityID);



    List<Objective> findByTag_tagID(Long ActivityID);

//    Optional<Objective> Tag_tagID(Long ActivityID);

    Objective save(Objective theObjective);
}