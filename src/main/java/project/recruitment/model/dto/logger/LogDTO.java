package project.recruitment.model.dto.logger;

import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;

@Builder
@Value
public class LogDTO
{
    Long id;

    String ip;

    String date;

    String logInfo;

    String message;
}
