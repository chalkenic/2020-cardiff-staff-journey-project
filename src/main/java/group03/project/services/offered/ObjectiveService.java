package group03.project.services.offered;


import group03.project.domain.Activity;
import group03.project.domain.Objective;
import group03.project.domain.Participation;
import group03.project.domain.Tag;

import java.util.List;
import java.util.Optional;

public interface ObjectiveService {

    /**
     * Creates an objective
     * @param theObjective - parsed objective.
     */
    public void createObjective(Objective theObjective);

    public Optional<Objective> findObjectivesByActivity(Activity theActivity);

    public List<Objective> findObjectivesByTagID(Long theID);

    public List<Objective> findObjectivesByActivityID(Long theID);

    public Integer getObjectiveListSize();

    List<Objective> getAllObjectives();

    public Activity getActivityByObjective_ActivityId(Long id);

}
