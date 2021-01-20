package group03.project.web.controllers.user;

import group03.project.domain.SiteUser;
import group03.project.services.offered.SiteUserService;
import group03.project.web.controllers.ControllerSupport;
import group03.project.web.forms.EditForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("user")
public class InfoController {

    private final SiteUserService userUpdateService;
    private final PasswordEncoder encoder;


    @Autowired
    public InfoController(group03.project.services.offered.SiteUserService anUpdateService,
                          PasswordEncoder theEncoder) {
        userUpdateService = anUpdateService;
        encoder = theEncoder;

    }

    @GetMapping("/account")
    public String userAccountDetails( Model model, Authentication authentication) {

        String name = ControllerSupport.getAuthenticatedUserName(authentication);

        Optional<SiteUser> aSiteUser = userUpdateService.findUserByUserName(name);
        if (aSiteUser.isPresent()) {
            EditForm editForm = new EditForm();

            SiteUser selectedUser = aSiteUser.get();
            model.addAttribute("siteuser", selectedUser);
            model.addAttribute("editForm", editForm);
            return "user-selected-account";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/change-name")
    public String changeAccountName(@ModelAttribute("editForm") @Valid EditForm nameForm,
                                    BindingResult result) {

        if(!result.hasErrors()) {
            SiteUser selectedUser = userUpdateService.findUserById(Long.parseLong(nameForm.getId())).get();
            selectedUser.setUserName(nameForm.getEdit());
            userUpdateService.updateUser(selectedUser);
        }
        return "redirect:/logout";
    }

    @PostMapping("/change-email")
    public String changeEmailAddress(@ModelAttribute("editForm") @Valid EditForm nameForm,
                                     BindingResult result) {


        if(!result.hasErrors()) {
            try {
                SiteUser selectedUser = userUpdateService.findUserById(Long.parseLong(nameForm.getId())).get();
                selectedUser.setEmailAddress(nameForm.getEdit());
                userUpdateService.updateUser(selectedUser);
                return "redirect:/user/account";

                /*
                Catches any email additions with are incorrectly formatted

                 */
            } catch (TransactionSystemException  e)  {
                return "redirect:/user/account";
            }
        } else {
            return "redirect:/user/account";
        }

    }

    @PostMapping("/change-password")
    public String changePassword(@ModelAttribute("editForm") @Valid EditForm nameForm,
                                 BindingResult result) {

        if(!result.hasErrors()) {

            SiteUser selectedUser = userUpdateService.findUserById(Long.parseLong(nameForm.getId())).get();
            selectedUser.setPassword(encoder.encode(nameForm.getEdit()));
            userUpdateService.updateUser(selectedUser);
        }
        return "redirect:/user/account";
    }
}
