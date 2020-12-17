package group03.project.web.lists;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ActivityRating {

    private Long activityID;
    private Long participationID;
    private Integer ratingavg;

}
