package org.unibl.etf.ip.backendapp.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class InternshipDTO {
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
}
