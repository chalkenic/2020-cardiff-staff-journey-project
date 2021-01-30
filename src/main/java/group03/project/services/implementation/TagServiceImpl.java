package group03.project.services.implementation;

import group03.project.domain.Tag;
import group03.project.services.offered.TagService;
import group03.project.services.required.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    final TagRepository tagJPAConnector;

    @Autowired
    public TagServiceImpl(TagRepository theJPATagConnector) {
        tagJPAConnector = theJPATagConnector; };

    @Override
    public List<Tag> findAllTags() {
        return tagJPAConnector.findAll();
    }

    @Override
    public void createCustomTag(Tag theTag) { tagJPAConnector.save(theTag); }

    @Override
    public void createOfficialTag(Tag theTag) {

        theTag.setIsOfficial(true);
        tagJPAConnector.save(theTag);
    }

    @Override
    public Optional<Tag> findATagByID(Long id) { return tagJPAConnector.findByTagID(id); }

    @Override
    public List<Tag> findTagsIfOfficial() {
        return tagJPAConnector.findByIsOfficial(true);
    }

    @Override
    public List<Tag> findTagsIfCustom() {
        return tagJPAConnector.findByIsOfficial(false);
    }

    @Override
    public void deleteSelectedTag(Long theTagID) { tagJPAConnector.deleteByTagID(theTagID); }

    @Override
    public Optional<Tag> findATagByName(String theName) { return tagJPAConnector.findByTagName(theName); }

}
