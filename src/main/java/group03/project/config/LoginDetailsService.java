package group03.project.config;


import group03.project.domain.SiteUser;
import group03.project.services.required.SiteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for handling spring security's UserDetailService method interface
 */
@Service
public class LoginDetailsService implements UserDetailsService {
    /**
     * Service requires usage of JPA "find" methods for extracting the user.
     */
    @Autowired
    SiteUserRepository siteUserRepo;

    /**
     * UserDetailsService method that casts instanced stored SiteUser into a
     * @param userName - username provided on login form.
     * @return new instance of SiteUserPrincipal, derived from UserDetails interface.
     * @throws UsernameNotFoundException - no user with provided name found in system.
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Optional<SiteUser> user = siteUserRepo.findByUserName(userName);
        /*
          Exception thrown if no user successfully stored inside Optional object.
         */
        user.orElseThrow(() -> new UsernameNotFoundException(userName + " cannot be found on system"));

        /*
          Creates new UserDetails interface object & maps user for constructor usage.
         */
        return user.map(SiteUserPrincipal::new).get();


    }
}
