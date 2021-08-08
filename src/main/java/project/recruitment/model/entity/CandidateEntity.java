package project.recruitment.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "candidates", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class CandidateEntity
{
    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false, length = 50)
    String username;

    @Column(nullable = false)
    String password;

    @Column(nullable = false, length = 50)
    String firstName;

    @Column(nullable = false, length = 50)
    String lastName;

    @Column(nullable = false, length = 50)
    String email;

    @Column(nullable = false, length = 20)
    String contactNumber;

    @Column(nullable = false)
    LocalDate dateOfBirth;

    @Column(nullable = false, length = 50)
    String cityOfLiving;

    @Builder.Default
    Boolean active = true;

    @Builder.Default
    ZonedDateTime deleteDate = null;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate")
    @Builder.Default
    List<TaskEntity> tasks = new ArrayList<>();


}
