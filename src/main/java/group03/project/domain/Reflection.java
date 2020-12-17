package group03.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reflection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reflectionID", nullable = false, unique = true)
    private Long reflectionID;

    @Column(name="Participation_participationID", nullable = false)
    private Long participationID;

    @Column(name="Tag_tagID")
    private Long tagID;

    @Length(max = 200)
    @Column(name="Reflect_what")
    private String reflect_what;

    @Length(max = 200)
    @Column(name="Reflect_prompt")
    private String reflect_prompt;

    @Length(max = 200)
    @Column(name="Reflect_happen")
    private String reflect_happen;

    @Length(max = 200)
    @Column(name="Reflect_eval")
    private String reflect_eval;

    @Length(max = 200)
    @Column(name="Reflect_diff")
    private String reflect_diff;

    @Length(max = 200)
    @Column(name="Reflect_lp")
    private String reflect_lp;

    @Column(name="isPublic")
    private Boolean isPublic;

    @Range(min = 1, max = 5)
    @Column(name="rating")
    private Long rating;

}
