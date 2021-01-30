package group03.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "objective")
public class Objective {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "objectiveID", nullable = false)
    private Long objectiveID;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "Activity_activityID", nullable = false)
    private Activity activity;

    @ManyToOne
    @JoinColumn(name = "Tag_tagID")
    private Tag tag;

    public Objective(Activity theActivity, Tag theTag) {
        this(null, theActivity, theTag);
        }
    }

