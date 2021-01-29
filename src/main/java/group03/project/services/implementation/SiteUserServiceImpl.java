package group03.project.services.implementation;

import group03.project.domain.SiteUser;
import group03.project.services.offered.SiteUserService;
import group03.project.services.required.SiteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SiteUserServiceImpl implements SiteUserService {

    final SiteUserRepository userRepoJPA;

    @Autowired
    public SiteUserServiceImpl(SiteUserRepository aUserRepoJPA) { userRepoJPA = aUserRepoJPA; };

    /**
     * Updates user in database with provided SiteUser object.
     * @param user - SiteUser object with data differing to current database object.
     */
    @Override
    public void updateUser(SiteUser user) {

        Optional<SiteUser> userToAmend = userRepoJPA.findById(user.getUserID());

        // Stream updates all of object's fields due to covering all types of account amending.
        userToAmend.ifPresent(currentUser -> {
            currentUser.setEmailAddress(user.getEmailAddress());
            currentUser.setPassword(user.getPassword());
            currentUser.setUserName(user.getUserName());
        });
        userRepoJPA.save(userToAmend.get());
    }

    @Override
    public List<SiteUser> findAllUsers() { return userRepoJPA.findAll(); }

    @Override
    public Optional<SiteUser> findUserById(Long id) {
        return userRepoJPA.findById(id);
    }

    @Override
    public Optional<SiteUser> findUserByEmail(String email) { return userRepoJPA.findByEmailAddress(email); }

    @Override
    public Optional<SiteUser> findUserByUserName(String userName) { return userRepoJPA.findByUserName(userName); }




    @Override
    public void createAUser(SiteUser aSiteuser) { userRepoJPA.save(aSiteuser); }

    @Override
    public void deleteSelectedUser(Long userID) { userRepoJPA.deleteById(userID); }

    @Override
    public boolean checkIfNewUserExists(String username) {return userRepoJPA.existsByUserName(username); }

}
