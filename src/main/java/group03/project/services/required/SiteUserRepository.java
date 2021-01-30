package group03.project.services.required;

import group03.project.domain.SiteUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SiteUserRepository {

     List<SiteUser> findAll();

     Optional<SiteUser> findById(Long id);

     Optional<SiteUser> findByEmailAddress(String email);

     Optional<SiteUser> findByUserName(String name);

     Optional<SiteUser> findByPermissions(String permissions);

     boolean existsByUserName(String username);

     SiteUser save(SiteUser aSiteUser);

     void deleteById(Long theID);
}
