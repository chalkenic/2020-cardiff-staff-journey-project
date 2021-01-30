package group03.project.services.offered;

import group03.project.domain.SiteUser;
import group03.project.domain.Tag;

import java.util.List;
import java.util.Optional;

/**
 * Interface forwarding communication from controllers to Service layer, connecting to database
 * for site users.
 */
public interface TagService {

    public List<Tag> findAllTags();

    /**
     * Method that creates an unofficial tag inside database
     * @param theTag - created tag object from controller.
     */
    public void createCustomTag(Tag theTag);

    /**
     * Method that creates official tag inside database
     * @param theTag - created tag object from controller.
     */
    public void createOfficialTag(Tag theTag);


    /**
     * Method finds tag by id given
     * @param id
     * @return tag matching given id.
     */
    public Optional<Tag> findATagByID(Long id);

    /**
     * finds all tags marked as official.
     * @return all official tags in database.
     */
    public List<Tag> findTagsIfOfficial();

    /**
     * finds all tags marked as unofficial.
     * @return all custom tags on database.
     */
    public List<Tag> findTagsIfCustom();

    /**
     * Delete tag in system by id
     * @param tagID the tag's id in database.
     */
    public void deleteSelectedTag(Long tagID);

    public Optional<Tag> findATagByName(String theName);
}
