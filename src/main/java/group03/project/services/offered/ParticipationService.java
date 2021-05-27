package group03.project.services.offered;

import group03.project.domain.Activity;
import group03.project.domain.Participation;

import java.util.List;

public interface ParticipationService {

    List<Participation> findAllParticipations();

    List<Participation> getAllParticipations();

    public void createParticipation(Participation participation);

    public Integer getParticipationListSize();

    public List<Participation> getParticipationsByUserId(Long id);

    public Activity getRelatedActivity(Participation participation);


}
