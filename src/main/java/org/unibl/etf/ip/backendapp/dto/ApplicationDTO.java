package org.unibl.etf.ip.backendapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {
    private Integer id;
    private String status;
    private LocalDate date;
    private Integer internshipId;
    private Integer studentId;
}
