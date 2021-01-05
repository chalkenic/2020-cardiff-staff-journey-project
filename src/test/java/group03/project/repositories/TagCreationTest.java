package group03.project.repositories;

import group03.project.domain.Tag;
import group03.project.services.required.TagRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class TagCreationTest {
    @Autowired
    public TagRepository tagRepository;

    @Test
    public void AddOneTagToListOfTags() throws Exception {

        tagRepository.save(new Tag(null, "b12", "a new tag", true));

        Optional<Tag> theTag = tagRepository.findByTagName("b12");
        System.out.println(theTag);

        assertEquals("b12", theTag.get().getTagName());
    }

    @Test
    public void addThreeTagsAndSearchByOfficialStatus() throws Exception {

        tagRepository.save(new Tag(null, "b12", "a new tag", true));
        tagRepository.save(new Tag(null, "c8", "a newer tag", false));
        tagRepository.save(new Tag(null, "A6", "the newest tag", true));

        List<Tag> allOfficialTags = tagRepository.findByIsOfficial(true);
        List<Tag> allCustomTags = tagRepository.findByIsOfficial(false);
        // Correct with H2 database - all official tags are entered by default.
        assertEquals(18, allOfficialTags.size());
        // Correct with fresh database - no custom tags created outside of test.
        assertEquals(1, allCustomTags.size());
    }
}
