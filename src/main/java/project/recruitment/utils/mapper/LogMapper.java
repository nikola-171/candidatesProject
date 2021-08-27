package project.recruitment.utils.mapper;

import project.recruitment.model.dto.logger.LogDTO;
import project.recruitment.model.entity.LogEntity;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class LogMapper
{
    public static LogDTO logToDTO(final LogEntity entity)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm:ss");

        return LogDTO.builder()
                .ip(entity.getIp())
                .date(entity.getDate().format(formatter))
                .logInfo(entity.getLogInfo())
                .id(entity.getId())
                .message(entity.getMessage())
                .build();
    }

    public static LogDTO logToDTO(final Optional<LogEntity> entityOptional)
    {
        if(entityOptional.isEmpty())
        {
            return LogDTO.builder().build();
        }
        else
        {
            LogEntity entity = entityOptional.get();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm:ss");

            return LogDTO.builder()
                    .ip(entity.getIp())
                    .date(entity.getDate().format(formatter))
                    .logInfo(entity.getLogInfo())
                    .id(entity.getId())
                    .message(entity.getMessage())
                    .build();
        }
    }
}
