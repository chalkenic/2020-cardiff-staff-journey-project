package group03.project.services.required;

import group03.project.domain.Activity;
import group03.project.domain.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository {

    public List<Tag> findAll();

    public Optional<Tag> findByTagID(Long tagID);

    public Optional<Tag> findByTagName(String tagName);

    public List<Tag> findByIsOfficial(Boolean status);

    public Tag save(Tag theTag);

    public void deleteByTagID(Long theTag);


    @Query(value = "select t.tagid from tag as t " +
            "inner join objective as o on t.tagid = o.Tag_tagID " +
            "inner join activity as a on o.Activity_activityID = a.activityid " +
            "inner join participation as p on a.activityID = p.Activity_activityID and p.siteUser_userID = ?1", nativeQuery = true)
    public List<Long> findAllTagsForUser(Long id);

}