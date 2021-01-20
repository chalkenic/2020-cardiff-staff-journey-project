package group03.project.web.controllers;

import group03.project.domain.SiteUser;
import group03.project.services.offered.SiteUserService;
import group03.project.web.forms.UserCreationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;

@Controller
public class RegistrationController {

    private final SiteUserService accountService;
    private final PasswordEncoder encoder;
    /**
     *
     * @param aService - Selected application service for controller usage.
     * @param theEncoder - BCrypt password encoder bean defined within SecurityConfig class
     */
    @Autowired
    public RegistrationController(SiteUserService aService, PasswordEncoder theEncoder) {
        encoder = theEncoder;
        accountService = aService;
    }

    @GetMapping("/register")
    public String ShowAccountCreationForm(Model model) {
        UserCreationForm newAccountForm = new UserCreationForm();
        model.addAttribute("newUser", newAccountForm);
        return "registration";

    }

    @PostMapping("/register-user")
    public String createNewUser(@ModelAttribute("newUser") @Valid UserCreationForm accountForm,
                                BindingResult result) {


        if(!result.hasErrors()) {

            try {
                /*
                Ensure that both passwords entered match
                 */
                if (accountForm.getPassword().equals(accountForm.getMatchingPassword())) {

                    /*
                        Create a new siteUser object & apply form data into it.
                     */
                    SiteUser newUser;
                    newUser = createAccount(accountForm, result);
                    accountService.createAUser(newUser);

                    /*
                        On successful user creation, redirect back to main page.
                     */
                    return "redirect:/";

                } else {
                    return "redirect:register";
                }
                /**
                 * Catches any errors made when appending to database via JPA.
                 */
            } catch (Exception e) {
                System.out.println("That username is taken; please try again");

                return "redirect:register";
            }
        } else {
            System.out.println("Result has errors");

            return "redirect:register";
        }
    }


    private SiteUser createAccount(UserCreationForm accountForm,
                                   BindingResult result) {
        SiteUser newUser;

        try {
         newUser = new SiteUser(
                 accountForm.getEmailAddress(),
                 encoder.encode(accountForm.getPassword()),
                 accountForm.getUserName());
        } catch (Exception ex) {
            return null;
        }
        return newUser;
    }

}
