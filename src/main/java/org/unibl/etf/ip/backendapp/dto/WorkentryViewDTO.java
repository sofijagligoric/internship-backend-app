package org.unibl.etf.ip.backendapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkentryViewDTO {
    private Integer id;
    private String activity;
    private LocalDate weekStart;
    private Integer internshipId;
    private Integer studentId;
    private String studentFirstName;
    private String studentLastName;
    private String internshipName;
}
