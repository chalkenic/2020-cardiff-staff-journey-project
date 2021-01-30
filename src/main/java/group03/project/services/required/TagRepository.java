package group03.project.services.required;

import group03.project.domain.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository {

    public List<Tag> findAll();

    public Optional<Tag> findByTagID(Long tagID);

    public Optional<Tag> findByTagName(String tagName);

    public List<Tag> findByIsOfficial(Boolean status);

    public Tag save (Tag theTag);

    public void deleteByTagID(Long theTag);
}
