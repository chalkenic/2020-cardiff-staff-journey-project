package group03.project.services.required;

import group03.project.domain.Activity;
import group03.project.domain.Participation;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ParticipationRepository {

    List<Participation> findAll();

    Participation save(Participation theParticipation);

    public Optional<Participation> findByParticipationID(Long id);

    @Query(value = "select * from participation where siteUser_userID = ?1", nativeQuery = true)
    public List<Participation> findParticipationsByUserId(Long id);

}