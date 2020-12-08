package group03.project.web.controllers;

import group03.project.domain.Tag;

import group03.project.services.offered.TagService;
import group03.project.web.forms.TagCreationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for navigating to directories in application relating to tags.
 */
@Controller
@RequestMapping("user")
public class TagController {

    private TagService tagService;
    /**
     * Links up the tag method interface into controller for accessing database changes.
     * @param aService - TagService interface for performing JPA-accessible methods
     */
    @Autowired
    public TagController(TagService aService) {
        tagService = aService;
    }

    /**
     * Signifies navigation to "create-tag" html page, along with creating blank tag form
     * for appending.
     * @param model - html page used for parsing objects onto page.
     * @return - html page name
     */
    @GetMapping("/create-tag")
    public String showTagCreationForm(Model model) {
        TagCreationForm tagForm = new TagCreationForm();
        model.addAttribute("tag", tagForm);
        return "user-create-tag";
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
                tagService.createCustomTag(newTag);

                return "all-tags";

            } catch (NullPointerException e) {
                e.printStackTrace();
                System.out.println("failed to create tag");
                return "redirect:all-tags";
            }
        } else {
            return "redirect:all-tags";
        }
    }

    private Tag createTag(TagCreationForm tagForm) {

        Tag newTag;

        try {
            newTag = new Tag(
                    tagForm.getTagID(),
                    tagForm.getDescription(),
                    Boolean.parseBoolean(tagForm.getIsOfficial()));

        } catch (Exception ex) {
            return null;
        }
        return newTag;
    }

    @GetMapping("/all-tags")
    public String presentAllTags(Model model) {

        List<Tag> allTags = tagService.findAllTags();

        model.addAttribute("tags", allTags);

        return "all-tags";
    }

}
