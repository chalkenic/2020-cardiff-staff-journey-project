package group03.project.repositories;

import group03.project.domain.Activity;
import group03.project.services.required.ActivityRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepoJPA extends JpaRepository<Activity, Integer>, ActivityRepository {

}
