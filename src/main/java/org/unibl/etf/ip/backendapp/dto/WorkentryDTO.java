package org.unibl.etf.ip.backendapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkentryDTO {
    private Integer id;
    private String activity;
    private LocalDate weekStart;
    private Integer internshipId;
    private Integer studentId;
}
