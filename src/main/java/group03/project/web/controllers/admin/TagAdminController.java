package group03.project.web.controllers.admin;

import group03.project.domain.Tag;
import group03.project.services.implementation.TagServiceImpl;
import group03.project.services.offered.TagService;
import group03.project.web.forms.EditForm;
import group03.project.web.forms.TagCreationForm;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("admin")
public class TagAdminController{

    private TagService tagService;
    /**
     * Links up the tag method interface into controller for accessing database changes.
     *
     * @param aService
     */
    public TagAdminController(TagService aService) {
        tagService = aService;

    }

    @GetMapping("/all-tags")
    public String presentAllTags(Model model) {

        List<Tag> allTags = tagService.findAllTags();

        model.addAttribute("tags", allTags);

        return "admin-all-tags";
    }

    @GetMapping("/create-tag")
    public String showAdminTagCreationModel(Model model) {
        TagCreationForm tagForm = new TagCreationForm();
        model.addAttribute("tag", tagForm);
        return "admin-create-tag";
    }

    /**
     * Builds a new tag object from html form & parses result into database via service.
     * @param tagForm - form object built from html entries.
     * @param result - result of validation on entries into form fields.
     * @return page redirection depending on success/failure of tag creation
     */
    @PostMapping("/tag-build")
    public String createNewTag(@ModelAttribute("tag") @Valid TagCreationForm tagForm,
                               BindingResult result) {

        if(!result.hasErrors()) {


            Tag newTag = createTag(tagForm);

            try {
                if (newTag.getIsOfficial()) {
                    tagService.createOfficialTag(newTag);
                } else {

                    tagService.createCustomTag(newTag);
                }

                return "redirect:/admin/all-tags";
            } catch (NullPointerException e) {
                e.printStackTrace();
                System.out.println("failed to create tag");
                return "all-tags";
            }
        } else {
            return "all-tags";
        }
    }

    private Tag createTag(TagCreationForm tagForm) {

        Tag newTag;

        try {
            newTag = new Tag(
                    tagForm.getTagName(),
                    tagForm.getDescription(),
                    Boolean.parseBoolean(tagForm.getIsOfficial()));

        } catch (Exception ex) {
            return null;
        }
        return newTag;
    }

    @PostMapping("/delete-tag")
    @Transactional
    public String deleteTag(RedirectAttributes redirectAttributes, @ModelAttribute("tag") @Valid EditForm editForm, BindingResult result) {

        if(!result.hasErrors()) {

            tagService.deleteSelectedTag(Long.parseLong(editForm.getId()));

        }
        redirectAttributes.addFlashAttribute("success",true);
        redirectAttributes.addFlashAttribute("type","deletetagadmin");
        return "redirect:/admin/all-tags";
    }
    @ExceptionHandler(SQLException.class)
    public String exceptionMessage(RedirectAttributes redirectAttributes) {
        String message ="addition/deletion could not be run. Reverting change.";
        System.out.println(message);
        redirectAttributes.addFlashAttribute("failure",false);
        redirectAttributes.addFlashAttribute("type","deletetagadmin");
        return "redirect:/dashboard";
    }
}
