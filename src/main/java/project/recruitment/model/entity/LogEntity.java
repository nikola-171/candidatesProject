package project.recruitment.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "log")
public class LogEntity
{
    @Id
    @GeneratedValue
    Long id;

    String ip;

    ZonedDateTime date;

    String logInfo;

    String message;
}
