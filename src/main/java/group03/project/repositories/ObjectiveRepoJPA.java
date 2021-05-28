package group03.project.repositories;

import group03.project.domain.Objective;
import group03.project.services.required.ObjectiveRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObjectiveRepoJPA extends JpaRepository<Objective, Integer>, ObjectiveRepository {
}
