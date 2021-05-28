package group03.project.services.implementation;

import group03.project.domain.Activity;
import group03.project.domain.Objective;
import group03.project.domain.Participation;
import group03.project.services.offered.ObjectiveService;
import group03.project.services.required.ActivityRepository;
import group03.project.services.required.ObjectiveRepository;
import group03.project.services.required.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class ObjectiveServiceImpl implements ObjectiveService {

//    private JdbcTemplate template;
    final ObjectiveRepository objectiveRepository;
    final TagRepository tagRepository;
    final ActivityRepository activityRepository;

//    @Value("INSERT INTO objective( Activity_activityID, Tag_tagID) values (?,?)")
//    private String newObjectiveSQL;

    @Autowired
    public ObjectiveServiceImpl( ObjectiveRepository objRepo, TagRepository tagRepo,
    ActivityRepository actRepo) {
        objectiveRepository = objRepo;
        tagRepository = tagRepo;
        activityRepository = actRepo;
    }


    @Override
    public void createObjective(Objective theObjective) {


        objectiveRepository.save(theObjective);


    }

    @Override
    public Optional<Objective> findObjectivesByActivity(Activity theActivity) {
        return Optional.empty();
    }

    @Override
    public List<Objective> findObjectivesByTagID(Long theID) { return objectiveRepository.findByTag_tagID(theID); }

    @Override
    public List<Objective> findObjectivesByActivityID(Long theID) { return objectiveRepository.findByActivity_activityID(theID);
    }

    //gets list size of objectives list
    @Override
    public Integer getObjectiveListSize() {
        List<Objective> objective = new ArrayList<>();
        objectiveRepository.findAll().forEach(objective::add);
        return objective.size();
    }

    //gets all objectives
    @Override
    public List<Objective> getAllObjectives() {
        List<Objective> objectives = new ArrayList<>();
        objectiveRepository.findAll().forEach(objectives::add);
        return objectives;
    }


    @Override
    public Activity getActivityByObjective_ActivityId(Long id) { return activityRepository.findActivityByObjectiveID(id); }

}
