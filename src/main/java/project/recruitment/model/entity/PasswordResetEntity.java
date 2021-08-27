package project.recruitment.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "passwordReset")
public class PasswordResetEntity
{
    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false)
    Long userId;

    @Column(nullable = false)
    String userToken;

    @Builder.Default
    ZonedDateTime requestDate = ZonedDateTime.now();

    @Builder.Default
    Boolean submit = false;
}
