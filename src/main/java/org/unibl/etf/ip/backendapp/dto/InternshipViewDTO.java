package org.unibl.etf.ip.backendapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternshipViewDTO {
    private Integer id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean available;
    private String about;
    private String technologies;
    private String skills;
    private Integer companyId;
    private String requirements;
    private String companyName;
}
