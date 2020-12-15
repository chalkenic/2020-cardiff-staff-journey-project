package group03.project.services.implementation;

import group03.project.domain.Activity;
import group03.project.services.offered.ActivityService;
import group03.project.services.required.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepo;

    //Returns a list of all activities in the database
    public List<Activity> findAllActivities() {
        List<Activity> activities = new ArrayList<>();
        activityRepo.findAll().forEach(activities::add);
        return activities;
    }

    //Adds an activity
    @Override
    public void saveActivity(Activity activity) {
        activityRepo.save(activity);
    }

    @Override
    public Optional<Activity> findAnActivityDescription(Long id) {return activityRepo.findByDescription(id); }

    //Returns the number of activities in the database
    @Override
    public int getActivityListSize() {
        List<Activity> activities = new ArrayList<>();
        activityRepo.findAll().forEach(activities::add);
        return activities.size();
    }

//    public List<Activity> findall() {
//        return activityRepo.findAll();
//    }

    @Override
    public Optional<Activity> findActivitiesByID(Long id) {
        return activityRepo.findByActivityID(id);
    }

    public Long findMostRecentActivity() { return activityRepo.findLastActivityID(); }
}
