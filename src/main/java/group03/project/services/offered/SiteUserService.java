package group03.project.services.offered;

import group03.project.domain.SiteUser;

import java.util.List;
import java.util.Optional;

/**
 * Interface allowing communication by controllers to service methods connecting to database
 * for site users.
 */
public interface SiteUserService {
    /**
     * Creates a user
     * @param aSiteuser - parsed site user.
     */
    public void createAUser(SiteUser aSiteuser);

    /**
     * updates site user.
     * @param aSiteuser
     */
    public void updateUser(SiteUser aSiteuser);

    /**
     * Find a user based upon their database userID
     * @param id - the parsed ID
     * @return optional list of all users with the id
     */
    public Optional<SiteUser> findUserById(Long id);

    /**
     * find all users on database
     * @return a list of all users
     */
    public List<SiteUser> findAllUsers();

    /**
     * find a user depending on the email entry string.
     * @param email - user's email
     * @return optional list containing a user if matching database entry
     */
    public Optional<SiteUser> findUserByEmail(String email);

    /**
     * find a user depending on name entry
     * @param name - user's saved name
     * @return optional list containing user(s) if matching database entry.
     */
    public Optional<SiteUser> findUserByUserName(String name);

    /**
     * Delete user in system by id
     * @param userID the tag's id in database.
     */
    public void deleteSelectedUser(Long userID);

    /**
     * Check if user exists in system
     * @param username - user name to check
     * @return confirmation if exists already.
     */
    public boolean checkIfNewUserExists(String username);

}
