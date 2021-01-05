package group03.project.services.required;

import group03.project.domain.Reflection;

import java.util.List;
import java.util.Optional;

public interface ReflectionRepository {

    List<Reflection> findAll();

    Reflection save(Reflection theReflection);

    Optional<Reflection> findByReflectionID(Long id);

    Optional<Reflection> findByParticipationID(Long id);

}
