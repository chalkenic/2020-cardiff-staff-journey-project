package group03.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="participation")
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="participationID", nullable = false, unique = true)
    private Long participationID;

    @NonNull
    @Column(name="Activity_activityID", nullable = false)
    private Long activityID;

    @DateTimeFormat
    @Column(name="date")
    private Date date;

    @Column(name="Role_roleID")
    private String roleID;

    @NonNull
    @Column(name="siteUser_userID", nullable = false)
    private Long userID;

}
