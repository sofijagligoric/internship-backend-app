package org.unibl.etf.ip.backendapp.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ApplicationDTO {
    private Integer id;
    private String status;
    private LocalDate date;
    private Integer internshipId;
    private Integer studentId;
}
