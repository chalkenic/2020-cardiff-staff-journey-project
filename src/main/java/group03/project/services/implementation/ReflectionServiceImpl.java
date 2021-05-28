package group03.project.services.implementation;

import group03.project.domain.Reflection;
import group03.project.services.offered.ReflectionService;
import group03.project.services.required.ReflectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReflectionServiceImpl implements ReflectionService {

    @Autowired
    private ReflectionRepository reflectionRepository;

    public List<Reflection> findAllReflections() {
        return reflectionRepository.findAll();
    }

    //Adds a reflection
    public void saveReflection(Reflection reflection) {
        reflectionRepository.save(reflection);
    }

    @Override
    public List<Reflection> findUserReflectionsById(Long id) { return reflectionRepository.findUserReflections(id);
    }

}
