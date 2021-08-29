package project.recruitment.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@RequestMapping("/logger")
public interface LoggerController
{
    @Operation(summary = "Get all logs by criteria")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("")
    @SecurityRequirement(name = "basicSecurity")
    ResponseEntity<?> getAllLogs(@RequestParam("currentPage") final Optional<Integer> currentPage,
                                 @RequestParam("itemsPerPage") final Optional<Integer> itemsPerPage);

    @Operation(summary = "Get logs by log info")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{logInfo}")
    @SecurityRequirement(name = "basicSecurity")
    ResponseEntity<?> getLogsViaLogInfo(@PathVariable final String logInfo);
}
