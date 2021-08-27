package project.recruitment.rest.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.recruitment.rest.LoggerController;
import project.recruitment.service.LogService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class LoggerControllerImpl implements LoggerController
{
    final LogService _logService;

    @Override
    public ResponseEntity<?> getAllLogs(Optional<Integer> currentPage, Optional<Integer> itemsPerPage)
    {
        return ResponseEntity.ok(_logService.getLogs(currentPage, itemsPerPage));
    }

    @Override
    public ResponseEntity<?> getLogsViaLogInfo(String logInfo)
    {
        return ResponseEntity.ok(_logService.getLogViaLogInfo(logInfo));
    }
}
