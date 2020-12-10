package group03.project.repositories;

import group03.project.domain.Reflection;
import group03.project.repositories.ReflectRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReflectRepoJPA extends JpaRepository<Reflection, Integer>, ReflectRepository {

}
