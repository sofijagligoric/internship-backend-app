package org.unibl.etf.ip.backendapp.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WorkentryDTO {
    private Integer id;
    private String activity;
    private LocalDate weekStart;
    private Integer internshipId;
    private Integer studentId;
}
