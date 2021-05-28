package group03.project.services.offered;

import group03.project.domain.Reflection;

import java.sql.Ref;
import java.util.List;

public interface ReflectionService {

    public List<Reflection> findAllReflections();

    public void saveReflection(Reflection reflection);

    public List<Reflection> findUserReflectionsById(Long id);
}
