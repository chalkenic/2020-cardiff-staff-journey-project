package group03.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tagID", nullable = false, unique = true)
    private Long tagID;

    @Column(name = "tagName")
    private String tagName;

    @Column(name = "description")
    private String description;

    @Column(name = "isOfficial")
    private Boolean isOfficial;

    public Tag(String tagName, String theDescription, Boolean isOfficial) {
        this(null, tagName, theDescription, isOfficial);
    }
}
