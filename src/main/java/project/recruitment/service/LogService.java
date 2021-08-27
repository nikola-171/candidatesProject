package project.recruitment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import project.recruitment.model.LogLevel;
import project.recruitment.model.dto.logger.LogDTO;
import project.recruitment.model.entity.LogEntity;
import project.recruitment.repository.LogRepository;
import project.recruitment.utils.mapper.LogMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LogService
{
    final LogRepository _logRepository;

    public void AddLog(LogEntity log)
    {
        _logRepository.save(log);
    }

    public List<LogDTO> getLogs(final Optional<Integer> page, final Optional<Integer> items)
    {
        int currentPage = 0;
        int itemsPerPage = 5;

        if(page.isPresent())
        {
            currentPage = page.get();
        }
        if(items.isPresent())
        {
            itemsPerPage = items.get();
        }

        PageRequest request = PageRequest.of(currentPage, itemsPerPage, Sort.by(Sort.Direction.DESC, "date"));

        return _logRepository.findAll(request).stream().map(LogMapper::logToDTO).collect(Collectors.toList());
    }

    public List<LogDTO> getLogViaLogInfo(final String logInfo)
    {
        List<LogDTO> returnData = new ArrayList<>();
        Iterable<LogEntity> data = null;
        if(logInfo.equalsIgnoreCase("INFO"))
        {
            data = _logRepository.findByLogInfo(LogLevel.INFO.name());
        }
        else if(logInfo.equalsIgnoreCase("DANGER"))
        {
            data = _logRepository.findByLogInfo(LogLevel.DANGER.name());
        }
        else
        {
            data = _logRepository.findByLogInfo(LogLevel.ERROR.name());
        }

        for (LogEntity item : data)
        {
            returnData.add(LogMapper.logToDTO(item));
        }
        return returnData;
    }
}
