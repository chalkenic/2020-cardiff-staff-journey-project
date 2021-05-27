package group03.project.services.offered;

import group03.project.domain.Activity;
import group03.project.domain.SiteUser;

import java.util.List;
import java.util.Optional;

public interface ActivityService {

    public List<Activity> findAllActivities();

    public Optional<Activity> findActivityByName(String name);

    public Optional<Activity> findActivitiesByID(Long id);

    public void saveActivity(Activity theActivity);

    public Optional<Activity> findAnActivityDescription(Long id);

    int getActivityListSize();

    public Long findMostRecentActivity();

    public List<Activity> getAllOfficialActivities();

    public List<Activity> getAllCustomActivities();

}
