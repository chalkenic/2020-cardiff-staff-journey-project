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
                                Model model, BindingResult result) {


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

                     if (!accountService.checkIfNewUserExists(newUser.getUserName())) {

                         accountService.createAUser(newUser);
                    /*
                        On successful user creation, redirect back to main page.
                     */
                         String creationSuccessful = "accountCreated";

                         model.addAttribute("message", creationSuccessful);
                         model.addAttribute("account", newUser.getUserName());

                         return "redirect:/";

                     } else {
                         String alreadyExists = "User already exists! please choose another name";

                         model.addAttribute("message", alreadyExists);
                         return "/registration";
                     }

                } else {
                    return "redirect:register";
                }
                /**
                 * Catches incorrect entries added to JPA.
                 */
            } catch (Exception e) {

                String[] passwordIncorrectState = {
                        "Password must contain 3 out of 4 instances of the following: ",
                        "- minimum 1 uppercase character",
                        "- minimum 1 lowercase character",
                        "- minimum 1 number",
                        "- minimum 1 special character",
                        " ",
                        "No more than 2 equal characters can be in a row!"

                };

                model.addAttribute("message", passwordIncorrectState);

                return "/registration";
            }
        } else {
            System.out.println("Result has errors");

            return "redirect:register";
        }
    }


    private SiteUser createAccount(UserCreationForm accountForm,
                                   BindingResult result) {
        SiteUser newUser;

        /**
         * Regex pattern sourced from OWASP's Validation Regex Repository complex password. Available at:
         * https://owasp.org/www-community/OWASP_Validation_Regex_Repository
         */

        String regexPtrn = "(?:(?=.*\\d)" +
                "(?=.*[A-Z])" +
                "(?=.*[a-z])|(?=.*\\d)" +
                "(?=.*[^A-Za-z0-9])" +
                "(?=.*[a-z])|(?=.*[^A-Za-z0-9])" +
                "(?=.*[A-Z])" +
                "(?=.*[a-z])|(?=.*\\d)" +
                "(?=.*[A-Z])" +
                "(?=.*[^A-Za-z0-9]))" +
                "(?!.*(.)\\1{2,})[A-Za-z0-9!~<>,;:_=?*+#.\"&§%°()\\|\\[\\]\\-\\$\\^\\@\\/]{8,32}";

        if (accountForm.getPassword().matches(regexPtrn)) {

            try {
                newUser = new SiteUser(
                        accountForm.getEmailAddress(),

                        encoder.encode(accountForm.getPassword()),
                        accountForm.getUserName());
            } catch (Exception ex) {
                return null;
            }
            return newUser;
        } else {
            return null;
        }
    }

}
