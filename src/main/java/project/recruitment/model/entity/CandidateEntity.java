package project.recruitment.model.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "candidates")
public class CandidateEntity
{
    @Id
    @GeneratedValue
    Long id;

    String firstName;

    String lastName;

    String email;

    String contactNumber;

    LocalDate dateOfBirth;

    @Builder.Default
    Boolean active = true;

    @Builder.Default
    ZonedDateTime deleteDate = null;

    String cityOfLiving;

}
