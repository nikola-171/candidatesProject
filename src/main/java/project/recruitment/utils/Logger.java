package project.recruitment.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import project.recruitment.model.LogLevel;
import project.recruitment.model.UserDetailsImpl;
import project.recruitment.model.entity.LogEntity;
import project.recruitment.service.LogService;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class Logger
{
    final LogService _logService;

    public void logError(String message)
    {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        _logService.AddLog(LogEntity.builder()
                .ip(request.getRemoteAddr())
                .logInfo(LogLevel.ERROR.name())
                .date(ZonedDateTime.now())
                .message(message)
                .build());
    }

    public void logInfo(String message)
    {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        _logService.AddLog(LogEntity.builder()
                .ip(request.getRemoteAddr())
                .logInfo(LogLevel.INFO.name())
                .date(ZonedDateTime.now())
                .message(message)
                .build());
    }

    public void logDanger(String message)
    {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        _logService.AddLog(LogEntity.builder()
                .ip(request.getRemoteAddr())
                .logInfo(LogLevel.DANGER.name())
                .date(ZonedDateTime.now())
                .message(message)
                .build());
    }
}
