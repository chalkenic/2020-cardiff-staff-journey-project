package group03.project.web.controllers.admin;

import group03.project.domain.SiteUser;
import group03.project.services.offered.SiteUserService;
import group03.project.web.forms.EditForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin")
public class InfoAdminController {

//    private final SiteUserUpdateService userUpdateService;
//    private final SiteUserAuditor userAuditor;
private final SiteUserService userService;
    private final PasswordEncoder encoder;

    @Autowired
    public InfoAdminController(SiteUserService aService, PasswordEncoder theEncoder) {
        encoder = theEncoder;
        userService = aService;

    }

    @GetMapping("all-accounts")
    public String allUsers( Model model) {

        List<SiteUser> users = userService.findAllUsers();
        EditForm editForm = new EditForm();

        model.addAttribute("users", users);
        model.addAttribute("editForm", editForm);

        return "all-accounts";
//
    }

    @GetMapping("/account/{userName}")
    public String AdminAccountDetails(@PathVariable("userName") String username, Model model) {

//        String name = ControllerSupport.getAuthenticatedUserName(authentication);

        Optional<SiteUser> aSiteUser = userService.findUserByUserName(username);
        if (aSiteUser.isPresent()) {
            EditForm editForm = new EditForm();

            SiteUser selectedUser = aSiteUser.get();
            model.addAttribute("siteuser", selectedUser);
            model.addAttribute("editForm", editForm);
            return "admin-selected-account";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/change-name")
    public String changeAccountName(@ModelAttribute("editForm") @Valid EditForm nameForm,
                                    BindingResult result) {

        if(!result.hasErrors()) {
            SiteUser selectedUser = userService.findUserById(Long.parseLong(nameForm.getId())).get();
            selectedUser.setUserName(nameForm.getEdit());
            userService.updateUser(selectedUser);
            return "redirect:/admin/account/" + selectedUser.getUserName();
        } else {
            return "all-accounts";
        }
    }

    @PostMapping("/change-email")
    public String changeEmailAddress(@ModelAttribute("editForm") @Valid EditForm nameForm,
                                     BindingResult result) {


        if(!result.hasErrors()) {
            try {
                SiteUser selectedUser = userService.findUserById(Long.parseLong(nameForm.getId())).get();
                selectedUser.setEmailAddress(nameForm.getEdit());
                userService.updateUser(selectedUser);
                return "redirect:/admin/account/" + selectedUser.getUserName();

                /*
                Catches any email additions with are incorrectly formatted
                 */
            } catch (TransactionSystemException e)  {
                return "all-accounts";
            }
        } else {
            return "all-accounts";
        }

    }

    @PostMapping("/reset-password")
    public String changePassword(@ModelAttribute("editForm") @Valid EditForm nameForm,
                                 BindingResult result) {

        if(!result.hasErrors()) {

            SiteUser selectedUser = userService.findUserById(Long.parseLong(nameForm.getId())).get();
            selectedUser.setPassword(encoder.encode("p"));
            userService.updateUser(selectedUser);

            return "redirect:/admin/account/" + selectedUser.getUserName();
         } else {
            return "all-accounts";
        }

    }

    @PostMapping("deleteAccount")
    public String deleteAccount(RedirectAttributes redirectAttributes, @ModelAttribute("editForm") @Valid EditForm userForm,
                                BindingResult result) {
        if(!result.hasErrors()) {

            SiteUser selectedUser = userService.findUserById(Long.parseLong(userForm.getId())).get();

            userService.deleteSelectedUser(selectedUser.getUserID());

        }

        redirectAttributes.addFlashAttribute("success",true);
        redirectAttributes.addFlashAttribute("type","admindeluser");
        return "redirect:/admin/all-accounts";
    }
}
