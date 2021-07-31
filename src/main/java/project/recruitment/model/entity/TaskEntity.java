package project.recruitment.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tasks")
public class TaskEntity
{
    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String description;

    @Column(nullable = false)
    String language;

    @Builder.Default
    String solution = "";

    ZonedDateTime startDate;

    @Builder.Default
    ZonedDateTime finishDate = null;

    @Builder.Default
    Long rating = 0L;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    @JsonBackReference
    CandidateEntity candidate;

}
